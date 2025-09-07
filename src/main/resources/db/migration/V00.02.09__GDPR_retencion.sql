ALTER TABLE app_user
    ADD COLUMN IF NOT EXISTS last_login_at TIMESTAMPTZ,
    ADD COLUMN IF NOT EXISTS last_activity_at TIMESTAMPTZ;

CREATE INDEX IF NOT EXISTS idx_app_user_last_login_at ON app_user(last_login_at);
CREATE INDEX IF NOT EXISTS idx_app_user_last_activity_at ON app_user(last_activity_at);

CREATE OR REPLACE FUNCTION fn_delete_inactive_users(cutoff TIMESTAMPTZ)
    RETURNS INTEGER AS $$
DECLARE
    v_deleted INTEGER;
BEGIN
    DELETE FROM app_user u
    WHERE u.is_protected = FALSE
      AND (
        COALESCE(u.last_activity_at, u.last_login_at, u.updated_at, u.created_at) < cutoff
            OR (u.deleted_at IS NOT NULL AND u.deleted_at < cutoff)
        );

    GET DIAGNOSTICS v_deleted = ROW_COUNT;
    RETURN v_deleted;
END;
$$ LANGUAGE plpgsql;