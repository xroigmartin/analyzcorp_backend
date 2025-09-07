CREATE OR REPLACE FUNCTION fn_touch_updated_at() RETURNS trigger AS $$
BEGIN
    NEW.updated_at := now();
    RETURN NEW;
END;
$$ LANGUAGE plpgsql;

-- app_user
DROP TRIGGER IF EXISTS trg_touch_updated_at_app_user ON app_user;
CREATE TRIGGER trg_touch_updated_at_app_user
    BEFORE UPDATE ON app_user
    FOR EACH ROW EXECUTE FUNCTION fn_touch_updated_at();

-- role
DROP TRIGGER IF EXISTS trg_touch_updated_at_role ON role;
CREATE TRIGGER trg_touch_updated_at_role
    BEFORE UPDATE ON role
    FOR EACH ROW EXECUTE FUNCTION fn_touch_updated_at();

-- permission
DROP TRIGGER IF EXISTS trg_touch_updated_at_permission ON permission;
CREATE TRIGGER trg_touch_updated_at_permission
    BEFORE UPDATE ON permission
    FOR EACH ROW EXECUTE FUNCTION fn_touch_updated_at();
