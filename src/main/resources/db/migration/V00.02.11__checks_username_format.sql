ALTER TABLE app_user
    DROP CONSTRAINT IF EXISTS chk_app_user_username_format,
    ADD CONSTRAINT chk_app_user_username_format
        CHECK (username ~ '^[a-z0-9._-]{3,50}$');