create table user_access
(
    id             bigint auto_increment
        primary key,
    access_granted tinyint(1) default 0 not null,
    accessed_at    datetime(6)          not null,
    access_card_id bigint               null,
    device_id      bigint               null,
    user_id        bigint               null,
    constraint FK3pnc797k9enha0td21jk7mjvt
        foreign key (user_id) references users (id),
    constraint FK79xfcqt5xtvvye0yb64da9m9u
        foreign key (device_id) references devices (id),
    constraint FKmkf5b7m7gmtwq1bhgrxbodbm4
        foreign key (access_card_id) references access_cards (id)
);

