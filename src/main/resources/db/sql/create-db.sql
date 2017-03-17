CREATE TABLE ideas (
  id INTEGER identity PRIMARY KEY,
  text VARCHAR(255)
);

CREATE TABLE subscribers (
  id INTEGER PRIMARY KEY,
  name VARCHAR(255)
);