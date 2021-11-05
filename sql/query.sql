use chess_game;


select 
match_id, 
match_player1_id, 
match_player2_id, 
match_winner, 
match_start_time, 
match_end_time 
from `match` 
where match_player1_id = 1 or match_player2_id = 1;

insert into `match`(match_player1_id, match_player2_id, match_winner, match_start_time, match_end_time) values
	(3,2,null,"15:45:12",null);
    
select * from `match`;    

update `match` set
	match_winner = 3,
    match_end_time = "18:12:55"
    where match_id = 4;