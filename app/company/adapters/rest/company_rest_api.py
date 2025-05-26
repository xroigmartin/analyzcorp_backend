from typing import Optional
from fastapi import APIRouter, HTTPException, Query

from app.company.adapters.cache.cache import get_cached_companies, refresh_companies_cache
from app.company.adapters.dto.company_dto import CompanyDTO
from app.company.domain.models.company import Company
from app.share.infrastructure.dto.paginated_response_dto import PaginatedResponseDTO

router = APIRouter()

@router.get("/companies", response_model=PaginatedResponseDTO[CompanyDTO])
def get_companies(
    name: Optional[str] = Query(None),
    cik: Optional[str] = Query(None),
    ticker: Optional[str] = Query(None),
    limit: int = Query(50, ge=1, le=200),
    offset: int = Query(0, ge=0)
):
    companies: list[Company] = list(get_cached_companies())
    
    if name:
        companies = [c for c in companies if name.lower() in c.name.lower()]
    if ticker: 
        companies = [c for c in companies if ticker.lower() in c.ticker.lower()]
    if cik:
        companies = [c for c in companies if cik.lower() in c.cik.lower()]
        
    total = len(companies)
    paged_companies = companies[offset: offset + limit]
    
    return PaginatedResponseDTO[CompanyDTO](
        total = total,
        limit = limit,
        offset = offset,
        items = [
            CompanyDTO(cik=c.cik, name=c.name, ticker=c.ticker)
            for c in paged_companies
        ])
    
@router.get("/companies/refresh", response_model=list[CompanyDTO])
def refresh_companies():
    try:
        companies: list[Company] = list(refresh_companies_cache())
    except Exception as e:
        raise HTTPException(status_code=500, detail=str(e))
    
    return [
        CompanyDTO(cik=c.cik, name=c.name, ticker=c.ticker)
        for c in companies[:50]
    ]