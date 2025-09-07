CREATE EXTENSION IF NOT EXISTS citext;
CREATE EXTENSION IF NOT EXISTS pgcrypto;

CREATE TABLE IF NOT EXISTS user_status (
    code            TEXT PRIMARY KEY,
    display_key     TEXT NOT NULL,
    description_key TEXT
);

ALTER TABLE user_status
    DROP CONSTRAINT IF EXISTS chk_user_status_code_upper,
    ADD CONSTRAINT chk_user_status_code_upper CHECK (code = UPPER(code));

-- Semillas de estados
INSERT INTO user_status (code, display_key, description_key) VALUES
    ('ACTIVE',   'USER_STATUS.ACTIVE',   'USER_STATUS.ACTIVE.DESC'),
    ('INACTIVE', 'USER_STATUS.INACTIVE', 'USER_STATUS.INACTIVE.DESC'),
    ('LOCKED',   'USER_STATUS.LOCKED',   'USER_STATUS.LOCKED.DESC')
ON CONFLICT (code) DO NOTHING;

