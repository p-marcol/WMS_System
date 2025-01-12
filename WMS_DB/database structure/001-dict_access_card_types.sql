create table dict_access_card_types
(
    id   bigint auto_increment
        primary key,
    type varchar(64) not null,
    constraint UKnpmlofx9j4wcsdcqx4winkujq
        unique (type)
);

