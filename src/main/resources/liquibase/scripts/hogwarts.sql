-- liquibase formatted for sql

-- changeset sunsin
CREATE INDEX full_name_idx ON students (name, surname);

-- changeset sunsin
CREATE INDEX name_color_ind ON faculties (name, color);