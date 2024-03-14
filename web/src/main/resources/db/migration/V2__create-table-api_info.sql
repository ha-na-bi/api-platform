CREATE TABLE api_info
(
    id           INTEGER PRIMARY KEY AUTOINCREMENT,
    app_id       INTEGER NOT NULL,
    name         TEXT    NOT NULL,
    code         TEXT    NOT NULL,
    url          TEXT    NOT NULL,
    method       TEXT    NOT NULL,
    header       TEXT,
    parameter    TEXT,
    timeout      INTEGER,
    status       INTEGER NOT NULL,
    summary      TEXT,
    created_time TEXT,
    updated_time TEXT
);
