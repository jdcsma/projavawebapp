CREATE DATABASE AdvancedMappingsMappedSuperclass DEFAULT CHARACTER SET 'utf8'
  DEFAULT COLLATE 'utf8_unicode_ci';

USE AdvancedMappingsMappedSuperclass;

CREATE TABLE news_articles (
  id BIGINT UNSIGNED NOT NULL AUTO_INCREMENT PRIMARY KEY,
  title VARCHAR(128) NOT NULL,
  content VARCHAR(1024) NOT NULL,

) ENGINE = InnoDB;