CREATE TABLE categories (
  id integer PRIMARY KEY,
  name varchar,
  points1 integer,
  points2 integer,
  points3 integer
);

CREATE TABLE nominees (
  id serial PRIMARY KEY,
  category integer REFERENCES categories,
  name varchar
);

CREATE TABLE winners (
 category integer REFERENCES categories,
 winner integer REFERENCES nominees
);

CREATE TABLE players (
  id serial PRIMARY KEY,
  name varchar(200)
);

CREATE TABLE playerpicks (
  player integer REFERENCES players,
  category integer REFERENCES categories,
  toppick integer REFERENCES nominees,
  midpick integer REFERENCES nominees,
  botpick integer REFERENCES nominees
);

