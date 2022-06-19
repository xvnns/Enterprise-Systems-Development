INSERT INTO role (id, name) VALUES (1, 'ADMIN');
INSERT INTO role (id, name) VALUES (2, 'USER');

INSERT INTO user_ (id, login, password, role_id) VALUES (1, 'katya', '$2y$10$GMUSWriBCefgiFSp9BeS9.cL8oxb2dfO0Brb4nWMg1z4nEyRQUZ9y', 2);
INSERT INTO user_ (id, login, password, role_id) VALUES (2, 'admin', '$2a$10$s6LlURcK8La80s1.SeyP2eldwtLjTFJdruUysDG5zCJNw0tcFyUl.', 1);

INSERT INTO book (id, author, book_name, price) VALUES (1, 'Bjarne Stroustrup', 'The C++ Programming', 54);
INSERT INTO book (id, author, book_name, price) VALUES (2, 'Wallace Wang', 'Beginning Programming For Dummies', 5);
INSERT INTO book (id, author, book_name, price) VALUES (3, 'Mark Lutz', 'Learning Python', 59);
INSERT INTO book (id, author, book_name, price) VALUES (4, 'Thomas H. Cormen', 'Introduction to Algorithms', 95);

SELECT setval('user__id_seq', 2);
SELECT setval('role_id_seq', 2);
SELECT setval('book_id_seq', 4);