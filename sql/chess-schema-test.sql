drop database chess_game_test;
create database  chess_game_test;

use chess_game_test;

-- create tables and relationships

create table player_role (
    player_role_id int primary key auto_increment,
    `name` varchar(50) not null unique
);

create table player_profile (
	player_profile_id int primary key auto_increment,
    player_profile_name varchar(150) not null,
    player_password varchar(2048) not null,
    player_profile_email varchar(250) not null,
    player_profile_delete boolean not null default false
);

create table player_profile_role (
    player_profile_id int not null,
    player_role_id int not null,
    constraint pk_player_profile_player_role
        primary key (player_profile_id, player_role_id),
    constraint fk_player_profile_role_player_profile_id
        foreign key (player_profile_id)
        references player_profile(player_profile_id),
    constraint fk_player_profile_role_player_role_id
        foreign key (player_role_id)
        references player_role(player_role_id)
);

create table `match` (
	match_id int primary key auto_increment,
    match_player1_id int not null,
    match_player2_id int not null,
    match_winner int null,
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
    delete from player_role;
    alter table player_role auto_increment = 1;
	delete from player_profile;
	alter table player_profile auto_increment = 1;
    delete from player_profile_role;
    
	insert into player_profile(player_profile_name, player_password, player_profile_email) values
		('Mario', "P@ssw1rd!", 'supermario@gmail.com'),
		('Caroline', "P@ssw2rd!", 'supercaroline@yahoo.com'),
		('Chirs', "P@ssw3rd!",'superchris@hotmail.com'),
		('Dave', "P@ssw4rd!", 'superdave@gmail.com'),
		('Brandon', "P@ssw5rd!", 'superbrandon@yahoo.com'),
		('Miguel', "P@ssw6rd!",'supermiguel@hotmail.com');
    

	insert into `match`(match_player1_id, match_player2_id, match_winner,match_start_time, match_end_time) values
		(1,2,2,"2:00:00","3:25:00"),
		(2,3,2,"3:30:00","4:55:00"),
		(3,1,3,"5:00:00","6:45:00"),
		(3,2,0,"7:30:13", "8:33:15"),
		(3,1,0,"9:00:00",null),
		(4,2,2,"10:00:00","10:25:00"),
		(5,1,5,"10:30:00","10:55:00"),
		(6,1,1,"11:00:00","11:45:00"),
		(1,6,1,"12:30:13", "13:33:15");
        
    insert into player_role(`name`) values
		("USER"),
		("ADMIN");
        
	insert into player_profile_role(player_profile_id, player_role_id) values
		(1,1),
		(2,1),
		(3,1),
		(4,1),
		(5,1),
		(6,1);    
        
end //
delimiter ;        

-- #######################################################

select * from `match`;

select * from player_profile;

select * from player_role;

select * from player_profile_role;

-- #######################################################
