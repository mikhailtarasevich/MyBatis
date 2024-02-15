create table regions
(
    id   integer primary key autoincrement,
    name varchar(50) unique,
    abbr varchar(20)
);