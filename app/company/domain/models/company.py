from dataclasses import dataclass

@dataclass(frozen=True)
class Company:
    cik: str
    name: str
    ticker: str