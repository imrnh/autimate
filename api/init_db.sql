-- users and parent.

CREATE TABLE users (
    id SERIAL PRIMARY KEY,
    email VARCHAR(255) NOT NULL UNIQUE,
    password VARCHAR(255) NOT NULL,
    name VARCHAR(255) NOT NULL,
    creation_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    type int NOT NULL default 1 -- 1 -> user. 2 -> admin
);

Create table child(
    id SERIAL PRIMARY KEY,
    name varchar(255) NOT NULL,
    parent_id int NOT NULL,
    dob TIMESTAMP NOT NULL,
    gender INT NOT NULL DEFAULT 3, -- 1-> male, 2-> female, 3-> other
    FOREIGN KEY (parent_id) REFERENCES users(id)
);

CREATE TABLE parent_child (
    id SERIAL PRIMARY KEY,
    parent_id INT NOT NULL,
    child_id INT NOT NULL,
    FOREIGN KEY (parent_id) REFERENCES users(id),
    FOREIGN KEY (child_id) REFERENCES child(id)
);


-- Test
CREATE TABLE question_answers(
    id SERIAL PRIMARY KEY,
    a1 int NOT NULL,
    a2 int NOT NULL,
    a3 int NOT NULL,
    a4 int NOT NULL,
    a5 int NOT NULL,
    a6 int NOT NULL,
    a7 int NOT NULL,
    a8 int NOT NULL,
    a9 int NOT NULL,
    a10 int NOT NULL
);

CREATE TABLE test(
    id SERIAL PRIMARY KEY,
    child_id INT NOT NULL,
    quest_ans int NOT NULL, --save what was answered in the test.
    autism_in_family BOOLEAN NOT NULL,
    test_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (child_id) REFERENCES child(id),
    FOREIGN KEY (quest_ans) REFERENCES question_answers(id)
);

CREATE TABLE children_test(
    id SERIAL PRIMARY KEY,
    child_id INT NOT NULL,
    test_id INT NOT NULL ,
    FOREIGN KEY (child_id) REFERENCES child(id),
    FOREIGN KEY (test_id) REFERENCES test(id)
);


-- CREATE TABLE call(
--     id SERIAL PRIMARY KEY ,
--     is_ongoing BOOLEAN DEFAULT FALSE, --when true, anyone can join the call if public.
--
-- );