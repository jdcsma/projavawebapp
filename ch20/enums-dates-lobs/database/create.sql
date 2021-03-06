CREATE DATABASE EnumsDatesLobs DEFAULT CHARACTER SET 'utf8'
  DEFAULT COLLATE 'utf8_unicode_ci';

USE EnumsDatesLobs;

CREATE TABLE Publishers (
  PublisherId BIGINT UNSIGNED NOT NULL PRIMARY KEY,
  PublisherName VARCHAR(100) NOT NULL,
  Address VARCHAR(1024) NOT NULL,
  DateFounded DATE NOT NULL,
  INDEX Publishers_Names (PublisherName)
) ENGINE = InnoDB;

CREATE TABLE Authors (
  AuthorId BIGINT UNSIGNED NOT NULL PRIMARY KEY,
  AuthorName VARCHAR(100) NOT NULL,
  EmailAddress VARCHAR(255) NOT NULL,
  Gender ENUM('MALE', 'FEMALE', 'UNSPECIFIED') NULL,
  INDEX Publishers_Names (AuthorName)
) ENGINE = InnoDB;

CREATE TABLE Books (
  Id BIGINT UNSIGNED NOT NULL PRIMARY KEY AUTO_INCREMENT,
  Isbn VARCHAR(13) NOT NULL,
  Title VARCHAR(255) NOT NULL,
  Author VARCHAR(100) NOT NULL,
  Price DECIMAL(6,2) NOT NULL,
  Publisher VARCHAR(100) NOT NULL,
  PreviewPdf MEDIUMBLOB NULL,
  UNIQUE KEY Books_ISBNs (Isbn),
  INDEX Books_Titles (Title)
) ENGINE = InnoDB;

CREATE TABLE SurrogateKeys (
  TableName VARCHAR(64) NOT NULL PRIMARY KEY,
  KeyValue BIGINT UNSIGNED NOT NULL,
  INDEX SurrogateKeys_Table_Values (TableName, KeyValue)
) ENGINE = InnoDB;

INSERT INTO `SurrogateKeys` (`TableName`,`KeyValue`) VALUES ("Publishers","1");
INSERT INTO `SurrogateKeys` (`TableName`,`KeyValue`) VALUES ("Authors","1");