create table schedule_blocks
(
    id          bigint auto_increment
        primary key,
    end_day     smallint not null,
    end_hour    time(6)  not null,
    start_day   smallint not null,
    start_hour  time(6)  not null,
    schedule_id bigint   not null,
    unit_id     bigint   null,
    constraint FKdmxc426cbr7qb9wqy0rhaydol
        foreign key (unit_id) references units (id),
    constraint FKibta8fpqxh6ru7f140jstgjy5
        foreign key (schedule_id) references schedules (id)
);

