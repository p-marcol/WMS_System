create table users
(
    id                       bigint auto_increment
        primary key,
    date_of_birth            date                 null,
    email                    varchar(128)         not null,
    first_name               varchar(64)          null,
    last_name                varchar(64)          null,
    password                 varchar(255)         null,
    phone                    varchar(15)          null,
    username                 varchar(64)          not null,
    is_archived              tinyint(1) default 0 not null,
    authority_id             bigint               null,
    creator_id               bigint               null,
    last_password_reset_date date                 null,
    constraint UKr43af9ap4edm43mmtq01oddj6
        unique (username),
    constraint FK2hyqc5fxrvpi614h8vpdvca1t
        foreign key (authority_id) references dict_authorities (id),
    constraint FKtgicgmqj2jc0o3m6y3lyuue7k
        foreign key (creator_id) references users (id)
);

