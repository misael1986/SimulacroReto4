DROP TABLE estudiantes;--BORRAR LA BD si eso queremos

create database if not exists "db_estudiantes";--comando por si llega a ser necesario

CREATE TABLE IF NOT EXISTS "db_estudiantes"."estudiantes" (
	"idEstudiante"	INTEGER NOT NULL,
	"nombre"	TEXT NOT NULL,
	"apellido"	TEXT NOT NULL,
	"email"	TEXT NOT NULL UNIQUE,
	"edad"	INTEGER,
	"carrera" TEXT,
	PRIMARY KEY("idEstudiante" AUTOINCREMENT)
);--CREA LA TABLA SI NO EXISTE


insert into estudiantes(nombre,apellido,edad,email,carrera) values 
('Misael','Perilla',35,'perillamisael@unbosque.edu.co','Sistemas'),
('Jon','Snow',17,'jon@got.com','Derecho'),
('Mark','Grayson',17,'invinsible@gmail.com','Derecho');--LLENADO EXPRESS
--TOCA REVISAR LA PARTE DEL .csv PORQUE NO ES CLARO AÚN PORQUE SE NECESITA
-- O COMO %#"&(@ SE VA A IMPLEMENTAR.

select * from estudiantes;

select * from estudiantes where email='jon@got.com';--MODULO 1

select * from estudiantes where apellido='Perilla';--MODULO 2

select nombre,apellido from estudiantes where carrera='Mecánica';--MODULO 3

select count(edad) as cantidad from estudiantes where carrera='Mecanica';--MODULO 4

select count(edad) as cantidad from estudiantes;

select max(idEstudiante) as maximo FROM estudiantes;

select * from estudiantes where edad=17;--MODULO 5

select nombre,carrera from estudiantes where apellido='Ramirez';--MODULO 6

delete from estudiantes where email='misael@gmail.com';

insert into estudiantes(edad, nombre, apellido, email,carrera) 
values(27,'Janis','Joplin','janis@joplin.com','Musica');

insert into alumnos(nombre,apellido,fijo,celular,programa,fechanac) values 
('Gabriel','Garcia',7890152,3104567890,'Literatura','6-03-1927'),
('Ernest','Hemingway',4784512,3004567845,'Literatura','21-07-1899');

select * from alumnos;

select * from alumnos where fechanac='6-03-1986';

insert into estudiantes(nombre,apellido,edad,email,carrera) values 
('Jonh','McClane',58,'jonh@diehard.com','Ciencias');


