drop database chess_game;
create database  chess_game;

use chess_game;

create table player_profile (
	player_profile_id int primary key auto_increment,
    player_profile_name varchar(150) not null,
    player_profile_email varchar(250) not null
);

create table `match` (
	match_id int primary key auto_increment,
    match_winner_id int not null,
    match_loser_id int not null,
    match_start_time date not null,
    match_end_time date not null
);

create table matches (
	matches_id int primary key auto_increment,
    match_id int not null,
    constraint fk_matches_match_id
		foreign key (match_id)
        references `match`(match_id)
);

create table ratios (
	ratios_id int primary key auto_increment,
    ratios_wins int null,
    ratios_loses int null,
    ratios_ties int null
);

create table stats (
	player_profile_id int primary key,
    matches_id int not null,
    ratios_id int not null,
    constraint fk_stats_matches_id
        foreign key (matches_id)
        references matches(matches_id),
    constraint fk_stats_ratios_id
        foreign key (ratios_id)
        references ratios(ratios_id),
    constraint fk_stats_player_profile_id
        foreign key (player_profile_id)
        references player_profile(player_profile_id)    
);    

