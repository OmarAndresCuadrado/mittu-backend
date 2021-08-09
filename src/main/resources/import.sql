INSERT INTO `roles` (nombre) VALUES ('ROLE_STUDENT');
INSERT INTO `roles` (nombre) VALUES ('ROLE_TEACHER');
INSERT INTO `roles` (nombre) VALUES ('ROLE_ADMIN');

insert into `usuarios` (email,enabled,last_name,name,password,username) values ("administradorDaniel@gmail.com", 1, "Daniel", "Daiel", "$2a$10$AVuS5YB5BTMzyPDLOuSoPO.whn3qxuHxTgc2dsaw9FrVE8AFgySE6" , "administradorDaniel@gmail.com");
insert into `usuarios_roles` values (1,3);
insert into `payments` (cost, name, fecha_de_creacion) values (10000, "1 hora", "2019-02-05");
insert into `payments` (cost, name, fecha_de_creacion) values (20000, "2 horas", "2019-02-05");
insert into `payments` (cost, name, fecha_de_creacion) values (30000, "3 horas", "2019-02-05");
insert into `payments` (cost, name, fecha_de_creacion) values (40000, "4 horas", "2019-02-05");
insert into `payments` (cost, name, fecha_de_creacion) values (50000, "5 horas", "2019-02-05");
insert into `payments` (cost, name, fecha_de_creacion) values (60000, "6 horas", "2019-02-05");
insert into `payments` (cost, name, fecha_de_creacion) values (70000, "7 horas", "2019-02-05");
insert into `payments` (cost, name, fecha_de_creacion) values (80000, "8 horas", "2019-02-05");
insert into `payments` (cost, name, fecha_de_creacion) values (90000, "9 horas", "2019-02-05");
insert into `payments` (cost, name, fecha_de_creacion) values (100000, "10 horas", "2019-02-05");
