CREATE TABLE task (
                      id BIGINT PRIMARY KEY NOT NULL,
                      title VARCHAR(255) NOT NULL,
                      description TEXT,
                      status VARCHAR(50),
                      priority VARCHAR(50),
                      author_id BIGINT,
                      assignee_id BIGINT,
                      created_at TIMESTAMP,
                      updated_at TIMESTAMP
);

CREATE TABLE "user" (
                        id BIGINT PRIMARY KEY NOT NULL,
                        email VARCHAR(255) NOT NULL,
                        password VARCHAR(255) NOT NULL,
                        role VARCHAR(50),
                        created_at TIMESTAMP
);

CREATE TABLE comment (
                         id BIGINT PRIMARY KEY NOT NULL,
                         task_id BIGINT NOT NULL,
                         user_id BIGINT,
                         content TEXT,
                         created_at TIMESTAMP
);

ALTER TABLE comment
    ADD CONSTRAINT fk_comment_task
        FOREIGN KEY (task_id)
            REFERENCES task(id);

ALTER TABLE comment
    ADD CONSTRAINT fk_comment_user
        FOREIGN KEY (user_id)
            REFERENCES "user"(id);