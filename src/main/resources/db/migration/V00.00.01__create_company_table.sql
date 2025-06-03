CREATE EXTENSION IF NOT EXISTS pg_trgm;

CREATE TABLE companies (
   cik TEXT PRIMARY KEY CHECK (char_length(cik) = 10),
   ticker VARCHAR(10) NOT NULL,
   name TEXT NOT NULL,

   created_at TIMESTAMP WITH TIME ZONE NOT NULL DEFAULT now(),
   created_by TEXT NOT NULL,
   updated_at TIMESTAMP WITH TIME ZONE,
   updated_by TEXT
);

-- Índices GIN para búsquedas aproximadas
CREATE INDEX idx_companies_ticker_trgm ON companies
    USING GIN (ticker gin_trgm_ops);

CREATE INDEX idx_companies_name_trgm ON companies
    USING GIN (name gin_trgm_ops);