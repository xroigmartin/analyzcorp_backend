from abc import ABC, abstractmethod
from typing import List

from app.company.domain.models.company import Company

class CompanyRepository(ABC):
    @abstractmethod
    def get_all_companies(self) -> List[Company]:
        pass