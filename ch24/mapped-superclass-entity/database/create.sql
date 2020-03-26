CREATE DATABASE MappedSuperclassEntity DEFAULT CHARACTER SET 'utf8'
  DEFAULT COLLATE 'utf8_unicode_ci';

USE MappedSuperclassEntity;

CREATE TABLE news_articles (
  article_id BIGINT UNSIGNED NOT NULL AUTO_INCREMENT PRIMARY KEY,
  revision BIGINT UNSIGNED NOT NULL,
  date_created TIMESTAMP NULL,
  date_modified TIMESTAMP NULL,
  title VARCHAR(128) NOT NULL,
  content VARCHAR(1024) NOT NULL
) ENGINE = InnoDB;