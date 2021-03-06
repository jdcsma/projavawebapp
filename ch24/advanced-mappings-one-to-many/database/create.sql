CREATE DATABASE AdvancedMappingsOneToMany DEFAULT CHARACTER SET 'utf8'
  DEFAULT COLLATE 'utf8_unicode_ci';

USE AdvancedMappingsOneToMany;

CREATE TABLE posts (
  id BIGINT UNSIGNED NOT NULL AUTO_INCREMENT PRIMARY KEY,
  name VARCHAR(128) NOT NULL
) ENGINE = InnoDB;

CREATE TABLE post_comments (
  id BIGINT UNSIGNED NOT NULL AUTO_INCREMENT PRIMARY KEY,
  post_id BIGINT UNSIGNED NOT NULL,
  review VARCHAR(1024) NOT NULL,
  created_on DATETIME NOT NULL,
  CONSTRAINT fk_post_id FOREIGN KEY (post_id)
    REFERENCES posts (id) ON DELETE CASCADE
) ENGINE = InnoDB;
