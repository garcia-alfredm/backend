
INSERT INTO users ( username, password, email, firstname, lastname) VALUES ('alincoln', 'password', 'alincoln@email.com', 'Abraham', 'Lincoln');
INSERT INTO posts (content, pictureLink, picture, user) VALUES ("Test post", "dummylink.com/picutre", null, users);
INSERT INTO posts (content, pictureLink, picture, user) VALUES ("2nd post", "dummylink.com/picutre2", null, users);
INSERT INTO posts (content, pictureLink, picture, user) VALUES ("3rd post", "dummylink.com/picutre3", null, users);
INSERT INTO posts (content, pictureLink, picture, user) VALUES ("4th post", "dummylink.com/picutre4", null, users);
INSERT INTO posts (content, pictureLink, picture, user) VALUES ("5th post", "dummylink.com/picutre5", null, users);

--Post post1 = new Post(1, timestamp, "this is a test", "dummylink.com/picture", null, user);
