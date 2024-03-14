CREATE TABLE app_info
(
    id           INTEGER PRIMARY KEY AUTOINCREMENT,
    code         TEXT    NOT NULL,
    name         TEXT    NOT NULL,
    summary      TEXT,
    url          TEXT    NOT NULL,
    status       INTEGER NOT NULL,
    created_time TEXT,
    updated_time TEXT
);
