# Capstone

# The Backend:

# http folder
-Board.http is the movement calls for our application. Due to time constraints, this has been left unimplemented in favor of using a chess library. 

-Match.http is the match calls for updating and adding a match. 

-Player.http is the player profile calls for when a player creates a profile. 

# sql folder

-This contains our schema for player profiles and for match updates

# src folder

# controllers folder

-AuthController, has calls to create account and add authentication

-BoardController(unimplemented), has calls to get new board, get the current state of the board, and to update the board

-ErrorResponse

-GlobalErrorHandler

-MatchController - to add and update matches and their statistics

-PlayerController - to add, update, and remove player profiles

# data folder

-AppUserTemplateRepository

-AppUserRespository interface

-DataAccessException

-MatchJdbcTemplateRepository

-MatchRepository interface

-PlayerJdbcTemplateRepository

-PlayerRepository

# domain folder

-AppUserService

-MatchService

-PlayerService

-Result

-ResultType enum

# mappers folder

-AppUserMapper

-MatchMapper

-PlayerProfileMapper

-PlayerStatsGlobalMapper

-PlayerStatsLossesMapper

-PlayerStatsMapper

-PlayerStatsTiesMapper

-PlayerStatesWinsMapper

# model folder

-AppUser

-Board(Unimplemented)

-ComputerPlayer(unimplemented)

-Match

-Pieces enum (unimplemented)

-PlayerProfile

-PlayerStats

# security folder

-JwtConverter

-JwtRequestFilter

-SecurityConfig

# Front-End:

# src folder

# components folder

# PlayerProfileUI
-Matches

-Profile

-ProfileMatches

-BoardPVP - The board starts the game

-BoardJoin - The board that joins the game

-Error

-Errors

-Home

-Login

-Nav

-pieceRenderer(unimplemented)

-PlayerListing

-Register

-RegisterAccountInfo

-TopFive

# context folder

-AuthContext

# images folder

-images of pieces
-pieceImages

# services folder

-BoardAPI

-MatchesAPI

-PlayersAPI



