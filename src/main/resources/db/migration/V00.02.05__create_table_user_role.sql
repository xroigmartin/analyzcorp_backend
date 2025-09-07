CREATE TABLE IF NOT EXISTS user_role (
    user_id     BIGINT NOT NULL
     REFERENCES app_user(id)
         ON UPDATE CASCADE ON DELETE CASCADE,
    role_id     BIGINT NOT NULL
     REFERENCES role(id)
         ON UPDATE CASCADE ON DELETE RESTRICT,
    assigned_at TIMESTAMPTZ NOT NULL DEFAULT now(),
    assigned_by BIGINT,

    PRIMARY KEY (user_id, role_id)
);

ALTER TABLE user_role
    ADD CONSTRAINT fk_user_role_assigned_by
        FOREIGN KEY (assigned_by) REFERENCES app_user(id)
            ON UPDATE CASCADE ON DELETE SET NULL DEFERRABLE INITIALLY DEFERRED;

CREATE INDEX IF NOT EXISTS idx_user_role_role_id ON user_role(role_id);
CREATE INDEX IF NOT EXISTS idx_user_role_assigned_by ON user_role(assigned_by);