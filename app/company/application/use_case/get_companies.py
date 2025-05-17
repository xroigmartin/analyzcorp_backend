
from typing import List

from app.company.domain.models.company import Company
from app.company.domain.repository.company_repository import CompanyRepository

class GetCompaniesUseCase:
    def __init__(self, repository: CompanyRepository):
        self.repository = repository
        
    def execute(self) -> List[Company]:
        return self.repository.get_all_companies()