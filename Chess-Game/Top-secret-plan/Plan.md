## BackEnd
- Data:
    - PlayerRepository Interface
      - addPlayer
      - deletePlayer
      - updatePlayer
      - findPlayerById
      - findAll
    - GameStatRepository Interface
      - findPlayerStats 
    - GlobalStatRepository Interface
      - findAll
    - PlayerJdbcTemplateRepository
    - GameStatJdbcTemplateRepository
    - GlobalStatJdbcTemplateRepository
- Mappers:
  - PlayerMapper
  - GameStatMapper
  - GlobalStatMapper
- Domain:
    - PlayerService
    - GameStatService
    - GlobalStatService
- Models:
    - Pieces (enum)
    - Board (to track moves)
    - Result (?)
    - ResultType (enum)
    - ComputerPlayer
- Controllers:
  - GlobalExceptionHandler
  - ErrorResponse
  - PlayerController
  - GameStatController
  - GlobalStatController
  - AuthController
  - AppUserController
- Security:
  - JwtConverter
  - JwtRequestFilter
  - SecurityConfig
  - SecurityUserService
- Minimax: (if we implement it ourselves)
  - MinimaxAlphaBeta



## Database
- Tables: 
  - player_stats
    - player_stats_id int primary key auto_increment
    - wins int null
    - loses int null
    - ties int null
    - games_played int null
    - profile_id int not null
    - constraint fk_player_stats_profiles_id
        foreign key (profile_id)
        references profiles(profile_id)
    
  - profiles
    - profile_id not null auto_increment
    - first_name varchar(50) not null
    - last_name varchar(50) not null
    - email varchar(256) not null
    
  - global_stats
    - global_stats_id int primary key auto_increment
    - ranking int not null

  - app_user
    - app_user_id int primary key auto_increment
    - username varchar(256) not null
    - password_hash varchar(2048) not null

  - app_user_role
    - app_user_id int not null
    - app_role_id int not null
    - constraint pk_app_user_role
        primary key (app_user_id, app_role_id),
    - constraint fk_app_user_role_user_id
        foreign key (app_user_id)
    - references app_user(app_user_id),
        constraint fk_app_user_role_role_id
    - foreign key (app_role_id)
        references app_role(app_role_id)

  - app_roles
    - app_role_id int primary key auto_increment,
    - name varchar(50) not null unique
  
  
## UI
- Home Page
    - Create User
    - If Logged in
        - Will display Play Online Button
          -Create Room
          -Enter Room
        - Display Play vs Computer Button
        - Manage User (Only if admin)
        - Log Out
    - Logged out
        - Will Display Global Ranking
        - Login Button
- User Details (Only if logged in)
    - User Name
    - Email Address
    - First Name
    - Last Name
    - Home Page Button
    - Delete User (Users can only delete self)
- Manage Users (Admin can only access this page)
    - Will have List of users. 
    - Will only be able to delete every user. 
    - Can grant admin permission to user. 
    - Delete and Cancel Buttons
- Global Ranking Page
    - Will display usernames 
    - Rank in the world
- User's Ranking Page
    - Display the player's rank
    - Your Score, 
    - Wins, 
    - Losses, 
- Login Page
    - Username
    - Password
    - Login and Cancel Button
- Game Page
    - Creating a randomly generated code to give to people to connect to room.
    - User creating room will go into game automatically
    - Will display waiting for other player. 
    - Other Player will have to enter code to connect. Will display enter code. 
    - Will display game with pieces. 
    - Will also display pieces captured.
- Creating a User Page
    - Username
    - First Name
    - Last Name
    - Email
    - Password
    - Enter Password again 
    - Submit and Cancel Button