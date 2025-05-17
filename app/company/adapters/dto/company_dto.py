from pydantic import BaseModel

class CompanyDTO(BaseModel):
    cik: str
    name: str
    ticker: str