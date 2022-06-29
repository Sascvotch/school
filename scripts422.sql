
CREATE TABLE cars
(
    id SERIAL primary key ,
    brand    TEXT NOT NULL,
    model    TEXT NOT NULL,
    price numeric
);

CREATE TABLE people
(
    id SERIAL primary key ,
    name    TEXT NOT NULL,
    age     integer check ( age > 0 ),
    license boolean default (false),
    car integer REFERENCES cars (id)
);

DROP table cars, people;