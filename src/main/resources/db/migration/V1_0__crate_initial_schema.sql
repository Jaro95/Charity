CREATE TABLE user (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    email VARCHAR(50) NOT NULL UNIQUE,
    name VARCHAR(30) NOT NULL,
    last_name VARCHAR(30) NOT NULL,
    password VARCHAR(255) NOT NULL,
    enabled BIT,
    token varchar(255),
    created_account datetime DEFAULT NOW()
);

CREATE TABLE role (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(50) NOT NULL
);

CREATE TABLE user_role (
    user_id BIGINT NOT NULL,
    role_id BIGINT NOT NULL,
    FOREIGN KEY (user_id) REFERENCES user(id),
    FOREIGN KEY (role_id) REFERENCES role(id)
);

CREATE TABLE category (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(255) NOT NULL
);

CREATE TABLE institution (
  id BIGINT AUTO_INCREMENT PRIMARY KEY,
  name varchar(255) NOT NULL,
  description varchar(255) NOT NULL
);

CREATE TABLE donation (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    quantity INT NOT NULL,
    institution_id BIGINT NOT NULL,
    street VARCHAR(100) NOT NULL,
    city VARCHAR(100) NOT NULL,
    zip_code VARCHAR(50) NOT NULL,
    phone_number BIGINT UNSIGNED NOT NULL,
    pick_up_date DATE NOT NULL,
    pick_up_time TIME NOT NULL,
    receive BIT NOT NULL,
    created_date DATE NOT NULL,
    created_time TIME NOT NULL,
    pick_up_comment VARCHAR(500),
    user_id BIGINT,
    FOREIGN KEY (institution_id) REFERENCES institution(id),
    FOREIGN KEY (user_id) REFERENCES user(id)
);

CREATE TABLE donation_category (
    donation_id BIGINT NOT NULL,
    category_id BIGINT NOT NULL,
    FOREIGN KEY (donation_id) REFERENCES donation(id),
    FOREIGN KEY (category_id) REFERENCES category(id)
);

CREATE TABLE recovery_password (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    email varchar(50) NOT NULL,
    token_recovery_password varchar(255) NOT NULL,
    local_date_time DATETIME NOT NULL
)




