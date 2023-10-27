# DROP DATABASE IF EXISTS spring_security_db;
# CREATE DATABASE IF NOT EXISTS spring_security_db;
USE spring_security_db;
CREATE TABLE IF NOT EXISTS certificates
(
    id               INTEGER PRIMARY KEY AUTO_INCREMENT,
    name             VARCHAR(255) NOT NULL,
    price            INTEGER      NOT NULL,
    description      VARCHAR(255) NOT NULL,
    duration         INTEGER      NOT NULL,
    create_date      DATE         NOT NULL,
    last_update_date DATE         NOT NULL
);

CREATE TABLE IF NOT EXISTS tags
(
    id   INTEGER PRIMARY KEY AUTO_INCREMENT,
    name VARCHAR(255) NOT NULL UNIQUE
);

CREATE TABLE IF NOT EXISTS certificate_tag
(
    certificate_id INTEGER NOT NULL,
    tag_id         INTEGER NOT NULL,
    PRIMARY KEY (certificate_id, tag_id),
    FOREIGN KEY (certificate_id) REFERENCES certificates (id) ON DELETE CASCADE,
    FOREIGN KEY (tag_id) REFERENCES tags (id) ON DELETE CASCADE
);

CREATE TABLE IF NOT EXISTS orders
(
    id             INTEGER PRIMARY KEY AUTO_INCREMENT,
    certificate_id INTEGER NOT NULL,
    order_date     DATE    NOT NULL,
    order_price    INTEGER NOT NULL,
    user_id        INTEGER NOT NULL,
    FOREIGN KEY (certificate_id) REFERENCES certificates (id) ON DELETE CASCADE,
    FOREIGN KEY (user_id) REFERENCES users (id) ON DELETE CASCADE
);