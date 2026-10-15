create table planos(
             id               int             auto_increment primary key,
             nome             varchar(150)    not null unique,
             valor_mensal     double          not null
);

insert into planos(nome, valor_mensal)
values
    ('Plano comum', 39.90),
    ('Plano preferencial', 59.90),
    ('Plano preferencial com dependente', 99.90),
    ('Plano vip', 159.90);

select * from planos;

create table clientes(
                         id               int             auto_increment primary key,
                         nome             varchar(150)    not null,
                         email            varchar(100)    not null,
                         cpf              char(11)        not null,
                         datahoracadastro datetime        default now(),
                         ativo            int             default 1,
                         plano_id         int             not null,
                         foreign key(plano_id) references planos(id));