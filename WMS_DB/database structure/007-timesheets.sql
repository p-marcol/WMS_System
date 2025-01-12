create table timesheets
(
    id            bigint auto_increment
        primary key,
    approved      tinyint(1) default 0 not null,
    approved_date date                 null,
    date          date                 not null,
    hours         double               not null,
    description   varchar(256)         not null,
    approved_by   bigint               null,
    unit_id       bigint               null,
    user_id       bigint               null,
    rejected      tinyint(1) default 0 not null,
    constraint FK2tred3eu9w7b1orap81htsr1t
        foreign key (user_id) references users (id),
    constraint FKkqwcmjpyebnva163p4yycdlhv
        foreign key (unit_id) references units (id),
    constraint FKl8i5kv1obilw617gm9fhkllta
        foreign key (approved_by) references users (id)
);

