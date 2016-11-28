 create table endereco (
        codigo bigint not null auto_increment,
        bairro varchar(100) not null,
        cep varchar(8),
        cidade varchar(100),
        estado varchar(50),
        numero varchar(8) not null,
        rua varchar(255) not null,
        codigo_cliente bigint,
        primary key (codigo)
    )engine=InnoDB;