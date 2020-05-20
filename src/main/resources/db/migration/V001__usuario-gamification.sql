create table usuario(
	id integer not null primary key AUTO_INCREMENT,
	login varchar(60) not null,
	senha varchar(100) not null,
	admin boolean not null,
	unique index login_unique (login ASC)
);

create table aluno(
	id integer not null primary key AUTO_INCREMENT,
	nome varchar(100) not null,
	matricula integer not null,
	email varchar(100) not null,
	telefone varchar(20) not null,
	usuario_id integer references usuario (id),
	unique index matricula_unique (matricula ASC),
	unique index email_unique (email ASC)
);

create table professor(
	id integer not null primary key AUTO_INCREMENT,
	nome varchar(100) not null,
	email varchar(100) not null,
	telefone varchar(20) not null,
	usuario_id integer references usuario (id),
	unique index email_unique (email ASC)
);