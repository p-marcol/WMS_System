create table user_payoff_data
(
    user_id bigint      not null
        primary key,
    iban    varchar(34) not null,
    swift   varchar(11) not null,
    constraint FK5m5vit0f67rmwn5hwqt13yinh
        foreign key (user_id) references users (id)
);

