ALTER TABLE book_records ADD COLUMN device_id UUID NOT NULL;
ALTER TABLE book_records ADD FOREIGN KEY (device_id) REFERENCES devices(id);

ALTER TABLE book_records RENAME COLUMN book_date TO date;

ALTER TABLE book_records ADD COLUMN status_change TEXT NOT NULL;
