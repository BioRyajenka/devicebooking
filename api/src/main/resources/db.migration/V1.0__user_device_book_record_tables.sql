CREATE TABLE users(
    id          UUID        NOT NULL    DEFAULT RANDOM_UUID()   PRIMARY KEY,
    name        TEXT        NOT NULL,
    password    TEXT        NOT NULL
);

CREATE TABLE devices(
    id          UUID        NOT NULL    DEFAULT RANDOM_UUID()   PRIMARY KEY,
    name        TEXT        NOT NULL
);

CREATE TABLE book_records(
    id          UUID        NOT NULL    DEFAULT RANDOM_UUID()   PRIMARY KEY,
    user_id     UUID        NOT NULL,
    book_date   TIMESTAMP   NOT NULL
);

CREATE INDEX bookings_user_id_idx ON book_records(user_id);
