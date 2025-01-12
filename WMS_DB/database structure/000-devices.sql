create table devices
(
    id              bigint auto_increment
        primary key,
    description     varchar(255) null,
    last_connection date         not null,
    mac_address     varchar(255) not null,
    symbol          varchar(255) not null
);

