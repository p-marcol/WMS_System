create table schedules
(
    id            bigint auto_increment
        primary key,
    created_at    timestamp default CURRENT_TIMESTAMP not null,
    end_at        date                                null,
    start_at      date                                not null,
    created_by_id bigint                              null,
    unit_id       bigint                              null,
    user_id       bigint                              null,
    constraint FK1w5f2u5qno53i5x1riv8wuoea
        foreign key (unit_id) references units (id),
    constraint FKd4y4xekwahv9boo6lc8gfl3jv
        foreign key (user_id) references users (id),
    constraint FKqctla2g4e8uxljtw8m29blc1
        foreign key (created_by_id) references users (id)
);

