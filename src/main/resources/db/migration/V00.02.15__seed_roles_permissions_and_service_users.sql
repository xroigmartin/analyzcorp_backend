INSERT INTO role (code, display_key, description_key)
VALUES
    ('ADMIN', 'ROLE.ADMIN', 'ROLE.ADMIN.DESC'),
    ('USER',  'ROLE.USER',  'ROLE.USER.DESC')
ON CONFLICT (code) DO NOTHING;

INSERT INTO permission (code, display_key, description_key)
VALUES
    ('ACCOUNT_READ',   'PERMISSION.ACCOUNT.READ',   'PERMISSION.ACCOUNT.READ.DESC'),
    ('ACCOUNT_CREATE', 'PERMISSION.ACCOUNT.CREATE', 'PERMISSION.ACCOUNT.CREATE.DESC'),
    ('ACCOUNT_UPDATE', 'PERMISSION.ACCOUNT.UPDATE', 'PERMISSION.ACCOUNT.UPDATE.DESC'),
    ('ACCOUNT_DELETE', 'PERMISSION.ACCOUNT.DELETE', 'PERMISSION.ACCOUNT.DELETE.DESC')
ON CONFLICT (code) DO NOTHING;

INSERT INTO role_permission (role_id, permission_id, granted_by)
SELECT r.id, p.id, NULL
FROM role r
         JOIN permission p ON TRUE
WHERE r.code = 'ADMIN'
ON CONFLICT DO NOTHING;

INSERT INTO app_user (public_id, username, email, password_hash, status, email_verified, is_service, can_login, is_protected)
SELECT
    '0f9a3b84-3b3a-4ad7-b1b7-9f4f8f1e9a10'::uuid,            -- system_public_id
    'svc-' || encode(gen_random_bytes(8), 'hex'),
    NULL,
    '!' || encode(gen_random_bytes(24), 'hex'),
    'LOCKED',
    FALSE,
    TRUE,
    FALSE,
    TRUE
ON CONFLICT (public_id) DO NOTHING;

INSERT INTO app_user (public_id, username, email, password_hash, status, email_verified, is_service, can_login, is_protected)
SELECT
    '5d7c2a6e-2cbb-4f73-a8a3-2c1bc4a0f2e1'::uuid,            -- batch_public_id
    'svc-' || encode(gen_random_bytes(8), 'hex'),
    NULL,
    '!' || encode(gen_random_bytes(24), 'hex'),
    'LOCKED',
    FALSE,
    TRUE,
    FALSE,
    TRUE
ON CONFLICT (public_id) DO NOTHING;

UPDATE app_user u
SET created_by = u.id, updated_by = u.id
WHERE u.public_id IN (
                      '0f9a3b84-3b3a-4ad7-b1b7-9f4f8f1e9a10'::uuid,
                      '5d7c2a6e-2cbb-4f73-a8a3-2c1bc4a0f2e1'::uuid
    )
AND (u.created_by IS NULL OR u.updated_by IS NULL);
