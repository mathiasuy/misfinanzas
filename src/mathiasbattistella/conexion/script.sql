-- drop database micasa;
 -- create database micasa;

use micasa;

-- drop database micasa;
 -- create database micasa;

 drop table MovimientoMensual;
 drop  table MovimientoRapido;
 drop table Categoria;

create table Categoria(
	nombre varchar(20) primary key unique not null,
    descripcion varchar(200)
    );
    
    -- alter table categoria modify column nombre varchar(20);
    -- alter table categoria modify column descripcion varchar(150);


create table MovimientoMensual(
	id int auto_increment primary key,
	nombre varchar(50) not null,
    descripcion varchar(150),
    monto double,
    categoria varchar(20) not null,
    es_ingreso boolean,
    fecha_pagado datetime,
    pago boolean,
    fecha_de_vencimiento datetime not null,
    recargo int,
    foreign key (Categoria) references Categoria(nombre)
);
-- alter table movimientomensual modify column descripcion varchar(150);
-- alter table movimientomensual modify column categoria varchar(20);

create table MovimientoRapido(
	id int auto_increment primary key,
	nombre varchar(50) not null,
    descripcion varchar(150),
    monto double,
    categoria varchar(20) not null,
    es_ingreso boolean,
    fecha_pagado datetime not null,

    foreign key (categoria) references Categoria(nombre)
);

insert into Categoria values 
("Salud", "Por ejemplo, gastos en sociedad médica, etc"),
("Tecnología","Televisores, radios, computadoras, etc"),
("Servicios","Agua, Luz, Teléfono, TV Cable, etc"),
("Devoto","Alimentos, bebidas, etc"),
("Alimentos","Alimentos, bebidas, etc"),
("Supermercado 2","Alimentos, bebidas, etc"),
("Salario","Movimientos relacionados con el salario, sueldo"),
("Educación","Colegio, Libros, juegos didácticos"),
("Entretenimiento","Juegos, Viajes, etc"),
("Transporte","Boletos de estudiante, boletos comunes, urbanos, suburbanos, metropolotanos, interdepartamentales, combustible");

insert into MovimientoRapido values
(0,"Devoto","Compra en el supermercado ",200.05,"Alimentos",false,"2016-09-08"),
(0,"Supermercado 2","Compra en el supermercado ",250.5,"Supermercado 2",false,"2016-09-10"),
(0,"La casa del Notebook","Compra Notebook",9840,"Tecnología",false,"2016-08-01"),
(0,"La casa del Notebook","Compra Monitor",3000,"Tecnología",false,"2016-09-01"),
(0,"La casa del Notebook","Compra Teclado",250,"Tecnología",false,"2016-09-10"),
(0,"La casa del Notebook","Compra Auriculares",550,"Tecnología",false,"2016-08-11"),
(0,"Kiosco del cei","Compra Materiales para facultad",350,"Educación",false,"2016-09-04"),
(0,"Almacen Rodriguez","Compra 200g de café",30,"Alimentos",false,"2016-08-04"),
(0,"Carnicería el ganado","30 huevos",90,"Alimentos",false,"2016-05-06"),
(0,"Carnicería el ganado","isque ets n\nunc lorem ipsum dolor sit \namet, consectetur aond metusor a enim r\nhoncus porttito",50,"Alimentos",false,"2016-08-06"),
(0,"La casa de la impresora","reparacion de impresora brother",900,"Servicios",false,"2016-09-06"),
(0,"Pizzería","compra",90,"Alimentos",false,"2016-09-06"),
(0,"Medica","ticket",90,"Salud",false,"2016-06-06"),
(0,"Medica","ticket",90,"Salud",false,"2016-07-06"),
(0,"Trabajo","sueldo",30090,"Salario",true,"2016-03-06"),
(0,"Trabajo","sueldo",30090,"Salario",true,"2016-04-06"),
(0,"Trabajo","sueldo",30090,"Salario",true,"2016-05-06"),
(0,"Trabajo","sueldo",30090,"Salario",true,"2016-06-06"),
(0,"Trabajo","sueldo",30090,"Salario",true,"2016-07-06"),
(0,"Trabajo","sueldo",30090,"Salario",true,"2016-08-06"),
(0,"Trabajo","sueldo",30090,"Salario",true,"2016-09-06"),
(0,"BIOS","curso",21090,"Entretenimiento",false,"2016-05-06"),
(0,"Gimnasio","cuota",30090,"Entretenimiento",false,"2016-06-06"),
(0,"Defensor","cuota",1800,"Entretenimiento",false,"2016-08-06"),
(0,"Defensor","cuota",1800,"Entretenimiento",false,"2016-09-06"),
(0,"Boletos","100 boletos cat B",1400,"Transporte",false,"2016-07-06"),
(0,"Boletos","100 boletos cat B",1400,"Transporte",false,"2016-08-06"),
(0,"Boletos","100 boletos cat B",1900,"Transporte",false,"2016-09-06"),
(0,"ANTEL","Recarga celular",100,"Transporte",false,"2016-08-06"),
(0,"ANTEL","Recarga celular",100,"Entretenimiento",false,"2016-09-01"),
(0,"ANTEL","Recarga celular",100,"Entretenimiento",false,"2016-09-15"),
(0,"Librería cei","Libro de Álgebra",120,"Educación",false,"2016-08-07");

insert into MovimientoMensual values 
(0,"ANTEL","Internet",790,"Servicios",true,"2016-08-07",true,"2016-08-08",3),
(0,"UTE","Luz",2500,"Servicios",false,null,false,"2016-09-10",2),
(0,"ANTEL","Teléfono fijo",350,"Servicios",false,"2016-09-10",true,"2016-09-06",3),
(0,"UTE","Lorem ipsum dolor sit amet, consectetur adipis\ncing elit. Aenem turpis consequat non. N\nunc ornare tincidunt.",2700,"Servicios",false,"2016-05-06",true,"2016-05-04",2),
(0,"Colegio","Proin bibendum vestibulum ante commodo rhoncus.\n massa, tincidunt ut viverra quis, orna\nre ac elit. Sed grav",8000,"Educacion",false,null,false,"2016-05-06",1),
(0,"Gastos comunes","algo",400,"Servicios",false,"2016-05-06",true,"2016-05-06",2),
(0,"TV Cable","TV por Cable",990,"Entretenimiento",false,"2016-02-07",true,"2016-02-13",2),
(0,"Sueldo","Trabajo",30000,"Salario",true,"2016-02-13",true,"2016-02-15",2),
(0,"Algo","Lorem ipsum dolor sit amet, consectetl enim, vit\nae miam, sit amet pretium turpis consequat\n non ornare tincidunt.",500,"Educacion",true,"2016-05-06",true,"2016-05-06",2),
(0,"Sueldo","Luz",20000,"Salario",true,"2016-01-06",true,"2015-12-16",2);



select * from Categoria;
select * from MovimientoMensual;
select * from MovimientoRapido;







