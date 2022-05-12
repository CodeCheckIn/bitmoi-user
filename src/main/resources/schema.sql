DROP TABLE IF EXISTS user;

CREATE TABLE USER (
    user_id INT PRIMARY KEY AUTO_INCREMENT NOT NULL,
    id VARCHAR(50) NOT NULL,
    name VARCHAR(50) NOT NULL,
    password VARCHAR(20) NOT NULL,
    phone VARCHAR(20), 
    created_at VARCHAR(50) NOT NULL ,
    updated_at VARCHAR(50) NOT NULL 
);
CREATE TABLE COIN (
    coin_id INT PRIMARY KEY AUTO_INCREMENT NOT NULL,
    name VARCHAR(50) NOT NULL,
    price FLOAT NOT NULL,
    updated_at VARCHAR(50) NOT NULL 
);
CREATE TABLE WALLET (
    wallet_id INT PRIMARY KEY AUTO_INCREMENT NOT NULL,
    user_id INT NOT NULL,
    coin_id INT NOT NULL,
    quantity FLOAT NOT NULL,
    avg_price FLOAT NOT NULL,
    created_at VARCHAR(50) NOT NULL,
    updated_at VARCHAR(50) NOT NULL,
    FOREIGN KEY (user_id)
    REFERENCES USER(user_id) ON UPDATE CASCADE,
    FOREIGN KEY (coin_id)
    REFERENCES COIN(coin_id) ON UPDATE CASCADE
);

INSERT INTO USER(
    name,id,password,phone,created_at,updated_at
) VALUES 
('김코드', '김코드@google.com', 'password', '010-0000-0000','2022-01-01 10:00:00' ,'2022-01-01 10:00:00');

INSERT INTO COIN(
    name,price,updated_at
) VALUES 
('BTC', '20022.12' ,'2022-01-01 10:00:00');
INSERT INTO COIN(
    name,price,updated_at
) VALUES 
('KRW', '100000' ,'2022-01-01 10:00:00');
INSERT INTO COIN(
    name,price,updated_at
) VALUES 
('ETH', '0' ,'2022-01-01 10:00:00');

-- INSERT INTO user VALUES (45, 2, '왕코드', '왕코드@google.com', 'password', 'address', '010-0000-0000');
-- INSERT INTO user VALUES (873153, 9, '금코드', '금코드@google.com', 'password', 'address', '010-0000-0000');