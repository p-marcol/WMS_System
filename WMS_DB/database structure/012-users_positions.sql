create table users_positions
(
    user_id      bigint not null,
    positions_id bigint not null,
    primary key (user_id, positions_id),
    constraint UKk2l2qi90liwn4bjghtrg4qtyj
        unique (positions_id),
    constraint FKd4rxhbclw0isrj2pnpp17lqhw
        foreign key (positions_id) references positions (id),
    constraint FKswfoyb20dpk738rb4g40hbo1a
        foreign key (user_id) references users (id)
);

