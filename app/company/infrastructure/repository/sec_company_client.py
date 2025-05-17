import httpx
from app.company.domain.exceptions.sec_api_exception import SECAPIException
from app.company.domain.models.company import Company
from app.company.domain.repository.company_repository import CompanyRepository
from app.share.config.config import Config
from app.share.logger.logger import logger

class SECCompanyClient(CompanyRepository):
    def __init__(self):
        super().__init__()
        self.url = Config.SEC_API_COMPANIES_URL
        self.headers = {
            "User-Agent": Config.SEC_API_USER_AGENT
        }
    
    def get_all_companies(self) -> list[Company]:
        try:
            logger.info("Requesting company data from SEC API...")
            response = httpx.get(self.url, headers=self.headers)
            response.raise_for_status()
        except httpx.HTTPStatusError as e:
            logger.error(f"HTTP error from SEC API: status {e.response.status_code}")
            raise SECAPIException(f"Failed to fetch data from SEC (status {e.response.status_code})")
        except httpx.RequestError as e:
            logger.error(f"Network error while accessing SEC API: {e}")
            raise SECAPIException("Network error while accessing SEC API")
        except Exception as e:
            logger.exception("Unexpected error while retrieving company data")
            raise SECAPIException("Unexpected error occurred")
        
        data = response.json()
        companies = []
        
        logger.info("Company data successfully retrieved from SEC")
        for item in data.values():
            cik = str(item["cik_str"]).zfill(10)
            
            companies.append(
                Company(
                    cik = cik,
                    name = item["title"].strip(),
                    ticker= item["ticker"].strip()
                )
            )
        
        return companies