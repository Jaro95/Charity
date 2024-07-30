CREATE TABLE token
(
    id            BIGINT AUTO_INCREMENT PRIMARY KEY,
    access_token  VARCHAR(255) NOT NULL,
    refresh_token VARCHAR(255) NOT NULL,
    logged_out    BIT,
    user_id       BIGINT,
    FOREIGN KEY (user_id) REFERENCES user (id) ON DELETE CASCADE
);