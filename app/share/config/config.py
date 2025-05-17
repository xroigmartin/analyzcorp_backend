from dotenv import load_dotenv
import os

load_dotenv()

class Config:
    ENV: str = os.getenv("APP_ENV", "dev").lower()
    APP_NAME: str = os.getenv("APP_NAME", "AnalyzCorp")
    
    SEC_API_COMPANIES_URL: str = os.getenv("SEC_API_COMPANIES_URL", "")
    SEC_API_USER_AGENT: str = os.getenv("SEC_USER_AGENT", "")
    
    LOG_LEVEL: str = os.getenv("LOG_LEVEL", "DEBUG").upper()