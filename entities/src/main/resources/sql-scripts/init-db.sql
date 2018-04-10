INSERT INTO year (year) VALUES (2013);
INSERT INTO year (year) VALUES (2014);
INSERT INTO year (year) VALUES (2015);
INSERT INTO year (year) VALUES (2016);
INSERT INTO year (year) VALUES (2017);

INSERT INTO program (title) VALUES ('Računalništvo in informatika UNI') RETURNING id;
INSERT INTO program (title) VALUES ('Računalništvo in matematika UNI');
INSERT INTO program (title) VALUES ('Računalništvo in informatika VS');

INSERT INTO lecturer (name, surname) VALUES ('Viljan', 'Mahnič');
INSERT INTO lecturer (name, surname) VALUES ('Igor', 'Kononenko');
INSERT INTO lecturer (name, surname) VALUES ('Borut', 'Robič');
INSERT INTO lecturer (name, surname) VALUES ('Boštjan', 'Slivnik');
INSERT INTO lecturer (name, surname) VALUES ('Branko', 'Šter');
INSERT INTO lecturer (name, surname) VALUES ('Uroš', 'Lotrič');
INSERT INTO lecturer (name, surname) VALUES ('Gašper', 'Fijavž');
INSERT INTO lecturer (name, surname) VALUES ('Tomaž', 'Hovelja');
INSERT INTO lecturer (name, surname) VALUES ('Danijel', 'Skočaj');
INSERT INTO lecturer (name, surname) VALUES ('Polona', 'Oblak');
INSERT INTO lecturer (name, surname) VALUES ('Zoran', 'Bosnić');
INSERT INTO lecturer (name, surname) VALUES ('Dejan', 'Lavbič');

INSERT INTO course (name, ects) VALUES ('Diskretne strukture', 6);
INSERT INTO course (name, ects) VALUES ('Programiranje 1', 6);
INSERT INTO course (name, ects) VALUES ('Programiranje 2', 6);
INSERT INTO course (name, ects) VALUES ('Osnove matematične analize', 6);
INSERT INTO course (name, ects) VALUES ('Organizacija računalniških sistemov', 6);
INSERT INTO course (name, ects) VALUES ('Arhitektura računalniških sistemov', 6);
INSERT INTO course (name, ects) VALUES ('Teorija informacijskih sistemov', 6);
INSERT INTO course (name, ects) VALUES ('Linearna algebra', 6);
INSERT INTO course (name, ects) VALUES ('Verjetnost in statistika', 6);
INSERT INTO course (name, ects) VALUES ('Izračunljivost in računska zahtevnost', 6);
INSERT INTO course (name, ects) VALUES ('Algoritmi in podatkovne strukture 1', 6);
INSERT INTO course (name, ects) VALUES ('Algoritmi in podatkovne strukture 2', 6);
INSERT INTO course (name, ects) VALUES ('Osnove informacijskih sistemov', 6);