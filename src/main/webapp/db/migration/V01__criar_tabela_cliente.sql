create table cliente (
        codigo bigint not null auto_increment,
        celular varchar(14) not null,
        cpf varchar(14) not null,
        email varchar(255),
        nome varchar(100) not null,
        telefone varchar(14) not null,
        primary key (codigo)
    )engine=InnoDB;
