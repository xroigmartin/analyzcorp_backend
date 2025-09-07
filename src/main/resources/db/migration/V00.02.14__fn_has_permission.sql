CREATE OR REPLACE FUNCTION has_permission(p_user_id BIGINT, p_perm_code TEXT)
    RETURNS BOOLEAN STABLE AS $$
SELECT EXISTS (
    SELECT 1
    FROM user_role ur
             JOIN role_permission rp ON rp.role_id = ur.role_id
             JOIN permission p ON p.id = rp.permission_id
             JOIN app_user u ON u.id = ur.user_id
    WHERE ur.user_id = p_user_id
      AND p.code = UPPER(p_perm_code)
      AND u.deleted_at IS NULL
);
$$ LANGUAGE sql;
