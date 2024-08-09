CREATE TABLE tasks
(
    id          BIGSERIAL PRIMARY KEY,
    title       VARCHAR(255) NOT NULL,
    description TEXT,
    status      VARCHAR(50),
    priority    VARCHAR(50),
    author_id   BIGINT,
    assignee_id BIGINT,
    created_at  TIMESTAMP,
    updated_at  TIMESTAMP
);

CREATE TABLE users
(
    id         BIGSERIAL PRIMARY KEY,
    user_name   VARCHAR(100)        NOT NULL,
    email      VARCHAR(100) unique NOT NULL,
    password   VARCHAR(100)        NOT NULL,
    role       VARCHAR(20),
    created_at TIMESTAMP
);

CREATE TABLE comments
(
    id         BIGSERIAL PRIMARY KEY,
    task_id    BIGINT NOT NULL,
    user_id    BIGINT,
    content    TEXT,
    created_at TIMESTAMP
);

ALTER TABLE comments
    ADD CONSTRAINT fk_comment_task
        FOREIGN KEY (task_id)
            REFERENCES tasks (id);

ALTER TABLE comments
    ADD CONSTRAINT fk_comment_user
        FOREIGN KEY (user_id)
            REFERENCES users (id);