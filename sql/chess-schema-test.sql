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
    player_profile_username varchar(100) null,
    player_profile_first_name varchar(75) null,
    player_profile_last_name varchar(75) null,
    player_profile_email varchar(250) null,
    player_profile_password varchar(2048) not null default '$2a$10$ntB7CsRKQzuLoKY3rfoAQen5nNyiC/U60wBsWnnYrtQQi8Z3IZzQa',
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
    delete from player_profile_role;
    delete from player_role;
    alter table player_role auto_increment = 1;
	delete from player_profile;
	alter table player_profile auto_increment = 1;
    
    -- passwords are set to "P@ssw0rd!"
	insert into player_profile(player_profile_username, player_profile_first_name, player_profile_last_name, player_profile_email, player_profile_password) values
		('SuperMario', "Mario", "Ortega",  'supermario@gmail.com', "$2a$10$ntB7CsRKQzuLoKY3rfoAQen5nNyiC/U60wBsWnnYrtQQi8Z3IZzQa"),
		('SuperCaroline', "Caroline", "Wilcox", 'supercaroline@yahoo.com', "$2a$10$ntB7CsRKQzuLoKY3rfoAQen5nNyiC/U60wBsWnnYrtQQi8Z3IZzQa"),
		('SuperChirs', "Chris", "Miller", 'superchris@hotmail.com', "$2a$10$ntB7CsRKQzuLoKY3rfoAQen5nNyiC/U60wBsWnnYrtQQi8Z3IZzQa"),
		('SuperCorbin', "Corbin", "March", 'superdave@gmail.com', "$2a$10$ntB7CsRKQzuLoKY3rfoAQen5nNyiC/U60wBsWnnYrtQQi8Z3IZzQa"),
		('SuperBrandon', "Brandon", "Washburn", 'superbrandon@yahoo.com', "$2a$10$ntB7CsRKQzuLoKY3rfoAQen5nNyiC/U60wBsWnnYrtQQi8Z3IZzQa"),
		('SuperMiguel', "Miguel", "Palacios", 'supermiguel@hotmail.com', "$2a$10$ntB7CsRKQzuLoKY3rfoAQen5nNyiC/U60wBsWnnYrtQQi8Z3IZzQa");

	insert into `match`(match_player1_id, match_player2_id, match_winner,match_start_time, match_end_time) values
		(6,2,6,"2:00:00","3:25:00"),
		(2,3,3,"3:30:00","4:55:00"),
		(3,6,3,"5:00:00","6:45:00"),
		(3,2,0,"7:30:13", "8:33:15"),
		(3,6,0,"9:00:00",null),
		(4,2,2,"10:00:00","10:25:00"),
		(5,6,5,"10:30:00","10:55:00"),
		(6,2,2,"11:00:00","11:45:00"),
		(3,2,3,"12:30:13", "13:33:15"),
		(4,2,2,"14:12:11", "14:56:09"),
		(6,5,5,"14:59:55", "15:29:21"),
		(5,2,2,"15:33:33", "15:50:00"),
		(4,6,4,"16:00:09", "16:30:00");

	insert into player_role(`name`) values
		("USER"),
		("ADMIN");

	insert into player_profile_role(player_profile_id, player_role_id) values
		(1,2),
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
