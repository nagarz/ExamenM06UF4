#La conexió amb hibernate esta feta amb l''usuari examen amb contrasenya examen, a continuació esta la comanda per crear-lo i assignar-li els permisos

#create USER 'examen'@'localhost' identified by 'examen';
#grant all privileges on Examen.* to 'examen'@'localhost';


drop database if exists Examen;
create database Examen;

use Examen;

create table Empresa(
	cif varchar(9) PRIMARY KEY,
	nom varchar(40),
	ong boolean,
	telefon varchar(10),
	establiment date	
) engine = InnoDB;

create table Director(
	dni varchar(9),
	nom varchar(20),
	cognoms varchar(40),
	telefon varchar(10),
	email varchar(40),
	cifEmpresa varchar(9) PRIMARY KEY,
	salari double(8,2)
) engine = InnoDB;

create table Empleat(
	dni varchar(9) PRIMARY KEY,
	nom varchar(20),
	cognoms varchar(40),
	telefon varchar(10),
	email varchar(40)
) engine = InnoDB;

create table Oficina(
	id int PRIMARY KEY auto_increment,
	direccio varchar(40),
	ciutat varchar(20),
	provincia varchar(20),
	dniResponsable varchar(9),
	cifEmpresa varchar(9)
) engine = InnoDB;

create table Treball(
	dniEmpleat varchar(9),
	cifEmpresa varchar(9),
	PRIMARY KEY(dniEmpleat, cifEmpresa)
) engine = InnoDB;

alter table Director add constraint fk_director_cifEmpresa foreign key (cifEmpresa) references Empresa (cif);
alter table Oficina add constraint fk_oficina_dniResponsable foreign key (dniResponsable) references Empleat (dni);
alter table Oficina add constraint fk_oficina_cifEmpresa foreign key (cifEmpresa) references Empresa (cif);
alter table Treball add constraint fk_treball_cifEmpresa foreign key (cifEmpresa) references Empresa (cif);
alter table Treball add constraint fk_treball_dniEmpleat foreign key (dniEmpleat) references Empleat (dni);

insert into Empresa values ("A01234567","Empresa 1", true, "9999999999", "2015-1-20");
insert into Empresa values ("A12345678","Empresa 2", false, "8888888888", "2015-5-13");

insert into Director values ("12345678A","Josep", "García Fuentes", "4444444444", "jgarcia@gmail.com", "A01234567", 465000.50);
insert into Director values ("23456789B","Llogari", "Cases i Torres", "5555555555", "Llogari76@gmail.com", "A12345678", 522547.13);

insert into Empleat values ("45454545A", "David", "Sola Pérez", "5476545678", "comomolo84@hotmail.com");
insert into Empleat values ("45876325F", "Juan", "Tunero Sorbete", "4568795499", "jtuneros@hotmail.com");
insert into Empleat values ("78541258H", "Ivan", "Roca Pérez", "9878787544", "irocaperez@hotmail.com");
insert into Empleat values ("75412336B", "Maria", "Martos Sanchez", "3256555555", "mariamartos@hotmail.com");
insert into Empleat values ("12354747F", "Martí", "Bellot Ninou", "1212121242", "marti1234@hotmail.com");
insert into Empleat values ("96875478B", "Anna", "Ramírez Hernández", "0124578554", "annetarh@hotmail.com");

insert into Oficina values (1, "hafjaehfjkahj", "Terrassa", "Barcelona", "45454545A", "A01234567");
insert into Oficina values (2, "ajgpoaejpoa", "Terrassa", "Barcelona", "45876325F", "A01234567");
insert into Oficina values (3, "fhaebfeafae", "Sabadell", "Barcelona", "75412336B", "A12345678");

insert into Treball values ("45454545A", "A01234567");
insert into Treball values ("45454545A", "A12345678");
insert into Treball values ("45876325F", "A01234567");
insert into Treball values ("78541258H", "A01234567");
insert into Treball values ("75412336B", "A12345678");
insert into Treball values ("12354747F", "A12345678");
insert into Treball values ("96875478B", "A12345678");

select * from Empresa;
select * from Director;
select * from Empleat;
select * from Oficina;
select * from Treball;

	
