CREATE OR REPLACE FUNCTION fn_prevent_delete_protected_user()
    RETURNS trigger AS $$
DECLARE
    v_override TEXT := current_setting('app.allow_protected_delete', true);
BEGIN
    IF OLD.is_protected AND COALESCE(v_override, '') <> 'on' THEN
        RAISE EXCEPTION
            USING
                ERRCODE = 'P0001',
                MESSAGE = format('Cannot delete protected user "%s".', OLD.username),
                HINT = 'This user is protected. Set app.allow_protected_delete=on in this transaction to override.';
    END IF;
    RETURN OLD;
END;
$$ LANGUAGE plpgsql;

DROP TRIGGER IF EXISTS trg_prevent_delete_protected_user ON app_user;
CREATE TRIGGER trg_prevent_delete_protected_user
    BEFORE DELETE ON app_user
    FOR EACH ROW
EXECUTE FUNCTION fn_prevent_delete_protected_user();