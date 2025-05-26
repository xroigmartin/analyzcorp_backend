from functools import lru_cache

from app.company.infrastructure.repository.sec_company_client import SECCompanyClient


@lru_cache(maxsize=1)
def get_cached_companies():
    client = SECCompanyClient()
    return tuple(client.get_all_companies())

def refresh_companies_cache():
    get_cached_companies.cache_clear()
    return get_cached_companies()