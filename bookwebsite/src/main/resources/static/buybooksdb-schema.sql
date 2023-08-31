DROP DATABASE IF EXISTS BuyBooksDB_GG;
CREATE DATABASE BuyBooksDB_GG;
USE BuyBooksDB_GG;

-- Create Author table
DROP TABLE IF EXISTS author;
CREATE TABLE author (
	AuthorId INT PRIMARY KEY AUTO_INCREMENT,
    authorFirstName VARCHAR(50) NOT NULL,
    authorLastName VARCHAR(50) NOT NULL,
    authorDescription VARCHAR(255) NOT NULL
);

-- Create Cateogry table
DROP TABLE IF EXISTS category;
CREATE TABLE category (
	CategoryId INT PRIMARY KEY AUTO_INCREMENT,
    categoryName VARCHAR(100) NOT NULL
);

-- Create Book table
DROP TABLE IF EXISTS book;
CREATE TABLE book (
	BookId INT PRIMARY KEY AUTO_INCREMENT,
    title VARCHAR(100) NOT NULL,
    price VARCHAR(50) NOT NULL,
    AuthorId INT NOT NULL,
    FOREIGN KEY (AuthorId) REFERENCES author(AuthorId)
);

-- Create Book_Category table (Many-to-Many Relationship)
DROP TABLE IF EXISTS book_category;
CREATE TABLE book_category (
	BookId INT NOT NULL,
    CategoryId INT NOT NULL,
    PRIMARY KEY (BookId, CategoryId),
    FOREIGN KEY (BookId) REFERENCES book(BookId),
    FOREIGN KEY (CategoryId) REFERENCES category(CategoryId)
);

-- Create User table
DROP TABLE IF EXISTS `user`;
CREATE TABLE `user` (
	UserId INT PRIMARY KEY AUTO_INCREMENT,
    userFirstName VARCHAR(50) NOT NULL,
    userLastName VARCHAR(50) NOT NULL,
    userAddress VARCHAR(100) NOT NULL,
    userPhone VARCHAR(50) NOT NULL
);

-- Create Order table (One-to-Many Relationship with User)
DROP TABLE IF EXISTS `order`;
CREATE TABLE `order` (
	OrderId INT PRIMARY KEY AUTO_INCREMENT,
    orderDate DATETIME NOT NULL,
    total VARCHAR(100) NOT NULL,
    isPaid BOOLEAN NOT NULL,
    UserId INT NOT NULL,
    BookId INT NOT NULL,
    FOREIGN KEY (UserId) REFERENCES `user`(UserId),
    FOREIGN KEY (BookId) REFERENCES book(BookId)
);