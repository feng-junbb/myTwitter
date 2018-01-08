insert into user (id, firstname, lastname, email, password)
values
	(10001, 'feng1', 'wang1', 'feng.wang1@myTwitter.com', 'password1'),
	(10002, 'feng2', 'wang2', 'feng.wang2@myTwitter.com', 'password2'),
	(10003, 'feng3', 'wang3', 'feng.wang3@myTwitter.com', 'password3');

insert into role (id, name) 
values
	(10001, 'USER'),
	(10002, 'ADMIN');

insert into user_role (user_id, role_id)
values
	(10001, 10001),
	(10001, 10002),
	(10002, 10001),
	(10003, 10001);

insert into user_follower (user_id, follower_id)
values
	(10001, 10002),
	(10003, 10001),
	(10003, 10002);

insert into message (user_id, message)
values
	(10001, 'feng1 first message.'),
	(10002, 'feng2 first message.'),
	(10003, 'feng3 first message.'),
	(10003, 'feng3 second message.');
