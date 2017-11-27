CREATE TABLE user_group (
	id INT AUTO_INCREMENT,
	name VARCHAR(255),
	PRIMARY KEY(id)
);

CREATE TABLE users(
	id BIGINT(20) AUTO_INCREMENT,
	username VARCHAR(255),
	email VARCHAR(255) UNIQUE,
	password VARCHAR(245),
	user_group_id INT,
	PRIMARY KEY(id),
	FOREIGN KEY(user_group_id) REFERENCES user_group(id)
	);
	
	
CREATE TABLE excercises (
	id INT AUTO_INCREMENT,
	title VARCHAR(255),
	description VARCHAR(255),
	PRIMARY KEY(id)
);

CREATE TABLE solutions (
	id INT AUTO_INCREMENT,
	created DATETIME,
	updated DATETIME,
	description TEXT,
	excercises_id INT,
	users_id BIGINT(20),
	PRIMARY KEY(id),
	FOREIGN KEY(excercises_id) REFERENCES excercises(id),
	FOREIGN KEY(users_id) REFERENCES users(id),
	);