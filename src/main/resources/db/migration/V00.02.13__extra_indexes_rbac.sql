CREATE INDEX IF NOT EXISTS idx_user_role_by_role_user ON user_role(role_id, user_id);
CREATE INDEX IF NOT EXISTS idx_role_perm_by_perm_role ON role_permission(permission_id, role_id);
