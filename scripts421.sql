ALTER TABLE student
    ADD CONSTRAINT age_constraint CHECK (age > 0);

ALTER TABLE student
    DROP CONSTRAINT age_constraint;

ALTER TABLE student
    ALTER COLUMN name SET NOT NULL,
    ADD CONSTRAINT nickname_unique UNIQUE (name);


ALTER TABLE student
    DROP CONSTRAINT nickname_unique,
    ALTER COLUMN name SET NOT NULL;

ALTER TABLE faculty
    ADD CONSTRAINT nickname_unique UNIQUE (name, color);

ALTER TABLE faculty
    DROP CONSTRAINT nickname_unique;

ALTER TABLE student
    ALTER COLUMN age SET default (20);