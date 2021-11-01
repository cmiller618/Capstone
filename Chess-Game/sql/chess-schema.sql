drop database if exists chess_game;
create database  chess_game;

use  chess_game;

create table app_user (
    app_user_id int primary key auto_increment,
    username varchar(50) not null unique,
    password_hash varchar(2048) not null
);

create table app_role (
    app_role_id int primary key auto_increment,
    `name` varchar(50) not null unique
);

create table app_user_role (
    app_user_id int not null,
    app_role_id int not null,
    constraint pk_app_user_role
        primary key (app_user_id, app_role_id),
    constraint fk_app_user_role_user_id
        foreign key (app_user_id)
        references app_user(app_user_id),
    constraint fk_app_user_role_role_id
        foreign key (app_role_id)
        references app_role(app_role_id)
);

create table player_stats (
	player_stats_id int primary key auto_increment,
    wins int null,
    loses int null,
    ties int null,
    games_played int null
);

create table profile (
	profile_id int primary key auto_increment,
    first_name varchar(50) not null,
    last_name varchar(50) not null,
    email varchar(256) not null,
    app_user_id int not null,
    player_stats_id int not null,
    constraint fk_profile_player_stats_id
        foreign key (player_stats_id)
        references player_stats(player_stats_id),
    constraint fk_profile_app_user_id
        foreign key (app_user_id)
        references app_user(app_user_id)
);

