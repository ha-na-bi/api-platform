CREATE TABLE app_info (
                          id INTEGER PRIMARY KEY AUTOINCREMENT,
                          code TEXT NOT NULL,
                          summary TEXT,
                          service_url TEXT NOT NULL,
                          created_time TEXT,
                          updated_time TEXT
);
