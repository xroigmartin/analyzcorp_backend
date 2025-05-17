from logging.handlers import RotatingFileHandler
from dotenv import load_dotenv
import logging
import os
import sys

from app.share.config.config import Config

logger = logging.getLogger("sec_data_api")
logger.setLevel(getattr(logging, Config.LOG_LEVEL, logging.DEBUG))

formatter = logging.Formatter(
    "%(asctime)s | %(levelname)s | %(name)s | %(message)s", "%Y-%m-%d %H:%M:%S"
)

if Config.ENV == "dev":
    console_handler = logging.StreamHandler(sys.stdout)
    console_handler.setFormatter(formatter)
    logger.addHandler(console_handler)
    
log_dir = "logs"
os.makedirs(log_dir, exist_ok=True)
log_file_path = os.path.join(log_dir, f"{Config.ENV}.log")

file_handler = RotatingFileHandler(
    log_file_path, maxBytes= 5 * 1024 * 1024, backupCount=3
)

file_handler.setFormatter(formatter)
    
logger.addHandler(file_handler)