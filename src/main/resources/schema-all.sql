DROP TABLE IF EXISTS pet;

CREATE TABLE IF NOT EXISTS pet (
    id                  UUID            NOT NULL,
    name                VARCHAR(250)    NOT NULL,
    status              VARCHAR(30)     NOT NULL,
    species             VARCHAR(250)    NOT NULL,
    PRIMARY KEY (id),
    CONSTRAINT name_unique UNIQUE (name)
);