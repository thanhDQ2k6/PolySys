create table user
(
    Id       varchar(20)      not null
        primary key,
    Password varchar(50)      not null,
    FullName varchar(50)      not null,
    Email    varchar(50)      not null,
    Admin    bit default b'0' not null,
    constraint Email
        unique (Email)
);

create table video
(
    Id     char(11)         not null
        primary key,
    Title  varchar(255)     not null,
    Poster varchar(255)     not null,
    `Desc` longtext         null,
    Active bit default b'0' not null,
    Views  int default 0    not null,
    Link   varchar(255)     not null
);

create table favorite
(
    Id       bigint auto_increment
        primary key,
    VideoId  char(11)    not null,
    UserId   varchar(20) not null,
    LikeDate date        not null,
    constraint unique_favorite
        unique (VideoId, UserId),
    constraint favorite_ibfk_1
        foreign key (VideoId) references video (Id)
            on delete cascade,
    constraint favorite_ibfk_2
        foreign key (UserId) references user (Id)
            on delete cascade
);

create index UserId
    on favorite (UserId);

create table share
(
    Id        bigint auto_increment
        primary key,
    VideoId   char(11)    not null,
    UserId    varchar(20) not null,
    Emails    varchar(50) not null,
    ShareDate date        not null,
    constraint share_ibfk_1
        foreign key (VideoId) references video (Id)
            on delete cascade,
    constraint share_ibfk_2
        foreign key (UserId) references user (Id)
            on delete cascade
);

create index UserId
    on share (UserId);

create index VideoId
    on share (VideoId);

