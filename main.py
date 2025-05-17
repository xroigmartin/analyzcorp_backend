from fastapi import FastAPI

from app.company.adapters.rest import company_rest_api


app = FastAPI()

app.include_router(company_rest_api.router)