-- noinspection SqlWithoutWhereForFile

DELETE FROM BOOK_RECORDS;

DELETE FROM users;
INSERT INTO users(name, password) VALUES
     ('admin', '$2a$10$AjHGc4x3Nez/p4ZpvFDWeO6FGxee/cVqj5KHHnHfuLnIOzC5ag4fm'),
     ('vasiliy', '$2a$10$AjHGc4x3Nez/p4ZpvFDWeO6FGxee/cVqj5KHHnHfuLnIOzC5ag4fm');

DELETE FROM devices;
INSERT INTO devices(name) VALUES
    ('Samsung Galaxy S9'),
    ('Samsung Galaxy S8'),
    ('Samsung Galaxy S8'),
    ('Motorola Nexus 6'),
    ('Oneplus 9'),
    ('Apple iPhone 13'),
    ('Apple iPhone 12'),
    ('Apple iPhone 11'),
    ('iPhone X'),
    ('Nokia 3310');
