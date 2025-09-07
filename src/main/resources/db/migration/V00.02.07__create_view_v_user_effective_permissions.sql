CREATE OR REPLACE VIEW v_user_effective_permissions AS
SELECT
    u.id           AS user_id,
    u.public_id    AS user_public_id,
    u.username,
    r.code         AS role_code,
    p.code         AS permission_code
FROM app_user u
         JOIN user_role ur          ON ur.user_id = u.id
         JOIN role r                ON r.id = ur.role_id AND r.deleted_at IS NULL
         JOIN role_permission rp    ON rp.role_id = r.id
         JOIN permission p          ON p.id = rp.permission_id AND p.deleted_at IS NULL
WHERE u.deleted_at IS NULL;
