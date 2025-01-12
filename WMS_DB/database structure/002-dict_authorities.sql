create table dict_authorities
(
    id        bigint auto_increment
        primary key,
    authority varchar(64) not null,
    constraint UKq3j68d5usqjmotc7jsc2rg46y
        unique (authority)
);

