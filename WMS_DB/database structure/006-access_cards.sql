create table access_cards
(
    id           bigint auto_increment
        primary key,
    card_uid     varchar(64)      null,
    user_id      bigint           not null,
    description  varchar(255)     null,
    card_type_id bigint           null,
    active       bit default b'1' not null,
    constraint FK9t5od4c0jtdxluafl73ipmysv
        foreign key (card_type_id) references dict_access_card_types (id),
    constraint FKf52rjj2j4eoei6740qad7ntr1
        foreign key (user_id) references users (id)
);

