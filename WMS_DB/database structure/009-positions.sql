create table positions
(
    id       bigint auto_increment
        primary key,
    name_id  bigint not null,
    unit_id  bigint not null,
    user_id  bigint not null,
    end_at   date   null,
    start_at date   not null,
    constraint FK4os2qjwsigymsutcplpw1ejur
        foreign key (unit_id) references units (id),
    constraint FK6330vxpwpi2c9ow76w5yarx9x
        foreign key (user_id) references users (id),
    constraint FKh89f26l2i0twthquxmbjw2h5y
        foreign key (name_id) references dict_position_names (id)
);

