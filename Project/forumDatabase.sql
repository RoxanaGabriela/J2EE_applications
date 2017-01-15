DROP DATABASE forumdatabase;
CREATE DATABASE IF NOT EXISTS forumdatabase;
USE forumdatabase;

CREATE TABLE IF NOT EXISTS client (
id INT(11) PRIMARY KEY AUTO_INCREMENT,
username VARCHAR(256) NOT NULL,
password VARCHAR(256) NOT NULL,
type VARCHAR(256) DEFAULT 'client' CHECK (type IN ('admin','client'))
);

CREATE TABLE IF NOT EXISTS topic (
id INT(11) PRIMARY KEY AUTO_INCREMENT,
title VARCHAR(256) NOT NULL,
description VARCHAR(1000) DEFAULT NULL
);

CREATE TABLE IF NOT EXISTS post (
id INT(11) PRIMARY KEY AUTO_INCREMENT,
title VARCHAR(256) NOT NULL,
description VARCHAR(1000) NOT NULL,
post_date DATE NOT NULL,
post_time TIME NOT NULL,
client_id INT(11) NOT NULL,
banned int DEFAULT '0',
FOREIGN KEY (client_id) REFERENCES client(id) ON UPDATE CASCADE
);

CREATE TABLE IF NOT EXISTS topic_post (
id INT(11) PRIMARY KEY AUTO_INCREMENT,
topic_id INT(11) NOT NULL,
post_id INT(11) NOT NULL,
FOREIGN KEY (topic_id) REFERENCES topic(id) ON UPDATE CASCADE,
FOREIGN KEY (post_id) REFERENCES post(id) ON UPDATE CASCADE
);

CREATE TABLE IF NOT EXISTS post_comment (
id INT(11) PRIMARY KEY AUTO_INCREMENT,
description VARCHAR(1000) NOT NULL,
post_date DATE NOT NULL,
post_time TIME NOT NULL,
client_id INT(11) NOT NULL,
post_id INT(11) NOT NULL,
banned INT DEFAULT '0',
FOREIGN KEY (client_id) REFERENCES client(id) ON UPDATE CASCADE,
FOREIGN KEY (post_id) REFERENCES post(id) ON UPDATE CASCADE
);

insert into client(username, password, type) values('roxana', '-', 'admin');

insert into client(username, password) values('andrei', '-');
insert into client(username, password) values('ana', '-');

insert into post(title, description, post_date, post_time, client_id)
values('how to play pokemon?', 'I downloaded pokemon. Can someone please tell me how to play it?', '2017-01-15', '03:47:07', '2');

insert into post_comment(description, post_date, post_time, client_id, post_id)
values('You open the app and walk around searching for pokemons', '2017-01-15', '04:12:00', '3', '1');

insert into topic(title, description) values('games', '-');
insert into topic(title, description) values('mobile', '-');
insert into topic(title, description) values('desktop', '-');
insert into topic_post(topic_id, post_id) values('1', '1');
insert into topic_post(topic_id, post_id) values('2', '1');