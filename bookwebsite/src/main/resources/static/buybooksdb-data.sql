USE BuyBooksDB_GG;
-- USE BuyBooksDBTest_GG;

-- Insert data into the author table
INSERT INTO author (authorFirstName, authorLastName, authorDescription)
VALUES
    ('J.K.', 'Rowling', 'British author best known for the Harry Potter series.'),
    ('George R.R.', 'Martin', 'American novelist and short story writer, known for A Song of Ice and Fire series.'),
    ('Jane', 'Austen', 'English novelist known for her romantic fiction.');

-- Insert data into the category table
INSERT INTO category (categoryName)
VALUES
    ('Fantasy'),
    ('Science Fiction'),
    ('Romance'),
    ('Mystery'),
    ('Horror');

-- Insert data into the book table
INSERT INTO book (title, price, AuthorId)
VALUES
    ('Harry Potter and the Sorcerer''s Stone', '19.99', 1),
    ('A Game of Thrones', '24.99', 2),
    ('Pride and Prejudice', '14.99', 3),
    ('The Hobbit', '18.99', 1),
    ('Dune', '22.99', 2),
    ('Sense and Sensibility', '13.99', 3);

-- Insert data into the book_category table (to establish many-to-many relationship)
INSERT INTO book_category (BookId, CategoryId)
VALUES
    (1, 1), -- Harry Potter and the Sorcerer's Stone - Fantasy
    (1, 3), -- Harry Potter and the Sorcerer's Stone - Romance
    (2, 1), -- A Game of Thrones - Fantasy
    (2, 2), -- A Game of Thrones - Science Fiction
    (3, 3), -- Pride and Prejudice - Romance
    (4, 1), -- The Hobbit - Fantasy
    (4, 2), -- The Hobbit - Science Fiction
    (5, 1), -- Dune - Fantasy
    (5, 2), -- Dune - Science Fiction
    (6, 3); -- Sense and Sensibility - Romance

-- Insert data into the user table
INSERT INTO `user` (userFirstName, userLastName, userAddress, userPhone)
VALUES
    ('John', 'Doe', '123 Main St, Anytown, USA', '555-123-4567'),
    ('Jane', 'Smith', '456 Oak Ave, Sometown, USA', '555-987-6543'),
    ('Bob', 'Johnson', '789 Elm Rd, Anycity, USA', '555-555-5555');

-- Insert data into the order table (linking orders to users and books)
INSERT INTO `order` (orderDate, total, isPaid, UserId, BookId)
VALUES
    ('2023-07-27 10:15:00', '19.99', 1, 1, 1), -- John Doe orders "Harry Potter and the Sorcerer's Stone"
    ('2023-07-27 12:30:00', '24.99', 0, 2, 2), -- Jane Smith orders "A Game of Thrones"
    ('2023-07-27 15:45:00', '14.99', 1, 1, 3), -- John Doe orders "Pride and Prejudice"
    ('2023-07-27 16:00:00', '18.99', 1, 3, 4), -- Bob Johnson orders "The Hobbit"
    ('2023-07-27 17:30:00', '22.99', 0, 2, 5); -- Jane Smith orders "Dune"


USE BuyBooksDB_GG;
SELECT * FROM author;
SELECT * FROM category;
SELECT * FROM book;
SELECT * FROM book_category;
SELECT * FROM `user`;
SELECT * FROM `order`;

SELECT * FROM `order` WHERE OrderId = ?;

SELECT b.* FROM Book b INNER JOIN `order` o ON b.BookId = o.BookId WHERE o.OrderId = ?;

SELECT u.* FROM `user` u INNER JOIN `order` o ON u.UserId = o.UserId WHERE o.OrderId = ?;

SELECT u.* FROM `user` u INNER JOIN `order` o ON o.UserId = u.UserId WHERE o.BookId = ?;

SELECT b.* FROM book b INNER JOIN `order` o ON b.BookId = o.BookId WHERE o.UserId = ?;
