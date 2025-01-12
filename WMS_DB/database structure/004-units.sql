create table units
(
    id             bigint auto_increment
        primary key,
    description    varchar(128)                         not null,
    name           varchar(64)                          not null,
    parent_unit_id bigint                               null,
    work_ended     tinyint(1) default 0                 not null,
    created_at     timestamp  default CURRENT_TIMESTAMP not null,
    constraint FKs3jhnwq0elcxxwxkjoo91m8vd
        foreign key (parent_unit_id) references units (id)
);

