drop database chess_game_test;
create database  chess_game_test;

use chess_game_test;

-- create tables and relationships


create table player_profile (
	player_profile_id int primary key auto_increment,
    player_profile_name varchar(150) not null,
    player_password varchar(2048) not null,
    player_profile_email varchar(250) not null,
    player_profile_delete boolean not null default false
);

create table `match` (
	match_id int primary key auto_increment,
    match_player1_id int not null,
    match_player2_id int not null,
    match_winner int not null default 0,
    match_start_time time not null,
    match_end_time time null,
    constraint fk_match_player_profile_id1
		foreign key (match_player1_id)
        references player_profile(player_profile_id),
    constraint fk_match_player_profile_id2
		foreign key (match_player2_id)
        references player_profile(player_profile_id)    
);

-- #######################################################

delimiter //
create procedure set_known_good_state()
begin

	delete from `match`;
    alter table `match` auto_increment = 1;
	delete from player_profile;
	alter table player_profile auto_increment = 1;
    
    insert into player_profile(player_profile_name, player_password, player_profile_email) values
	('SuperMario', "P@ssw0rd!", 'supermario@gmail.com'),
    ('SuperCaroline', "P@ssw1rd!", 'supercaroline@yahoo.com'),
    ('SuperChirs', "P@ssw2rd!",'superchris@hotmail.com');
    

	insert into `match`(match_player1_id, match_player2_id, match_winner,match_start_time, match_end_time) values
		(1,2,2,"8:00:00","10:25:00"),
		(2,3,2,"10:30:00","10:55:00"),
		(3,1,0,"11:00:00",null);
        
end //
delimiter ;        

-- #######################################################

select * from `match`;

select * from player_profile;
