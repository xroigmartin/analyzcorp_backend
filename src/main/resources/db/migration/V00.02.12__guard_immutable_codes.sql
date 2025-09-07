CREATE OR REPLACE FUNCTION fn_forbid_code_update() RETURNS trigger AS $$
BEGIN
    IF TG_OP = 'UPDATE' AND NEW.code IS DISTINCT FROM OLD.code THEN
        RAISE EXCEPTION 'Code is immutable for %. Use a new row instead.', TG_TABLE_NAME;
    END IF;
    RETURN NEW;
END;
$$ LANGUAGE plpgsql;

DROP TRIGGER IF EXISTS trg_forbid_role_code_update ON role;
CREATE TRIGGER trg_forbid_role_code_update
    BEFORE UPDATE ON role
    FOR EACH ROW EXECUTE FUNCTION fn_forbid_code_update();

DROP TRIGGER IF EXISTS trg_forbid_permission_code_update ON permission;
CREATE TRIGGER trg_forbid_permission_code_update
    BEFORE UPDATE ON permission
    FOR EACH ROW EXECUTE FUNCTION fn_forbid_code_update();

DROP TRIGGER IF EXISTS trg_forbid_user_status_code_update ON user_status;
CREATE TRIGGER trg_forbid_user_status_code_update
    BEFORE UPDATE ON user_status
    FOR EACH ROW EXECUTE FUNCTION fn_forbid_code_update();