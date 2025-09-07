CREATE TABLE IF NOT EXISTS role_permission (
    role_id        BIGINT NOT NULL
       REFERENCES role(id)
           ON UPDATE CASCADE ON DELETE CASCADE,
    permission_id  BIGINT NOT NULL
       REFERENCES permission(id)
           ON UPDATE CASCADE ON DELETE RESTRICT,
    granted_at     TIMESTAMPTZ NOT NULL DEFAULT now(),
    granted_by     BIGINT,

    PRIMARY KEY (role_id, permission_id)
);

ALTER TABLE role_permission
    ADD CONSTRAINT fk_role_permission_granted_by
        FOREIGN KEY (granted_by) REFERENCES app_user(id)
            ON UPDATE CASCADE ON DELETE SET NULL DEFERRABLE INITIALLY DEFERRED;

CREATE INDEX IF NOT EXISTS idx_role_permission_permission_id ON role_permission(permission_id);
CREATE INDEX IF NOT EXISTS idx_role_permission_granted_by ON role_permission(granted_by);