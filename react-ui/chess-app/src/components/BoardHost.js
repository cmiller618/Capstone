import { useRef, useState } from 'react';
import { Chessboard } from 'react-chessboard';
import React, { useContext, useEffect } from 'react';
import { Link } from "react-router-dom"
import AuthContext from "../context/AuthContext";
import Chess from 'chess.js';
import './Board.css'

const socketUrl = "ws:localhost:8080/messages"

export default function BoardHost({ boardWidth }) {
  const myColor = 'b';
  const auth = useContext(AuthContext);

  const chessboardRef = useRef();
  const [game, setGame] = useState(new Chess());
  const [matchId, setMatchId] = useState();
  const [gameOver, setGameOver] = useState();
  const [player2Id, setPlayer2Id] = useState();
  const [player2Name, setPlayer2Name] = useState();

  const wsRef = useRef(null);

  useEffect(() => {
    wsRef.current = new WebSocket(socketUrl);
    const ws = wsRef.current;

    ws.onopen = () => {
      console.log("websocket successfully connected.")
      ws.send(JSON.stringify({
        id: auth.user.id,
        username: auth.user.username
      }))
    };

    ws.onclose = () => {
      console.log("websocket closed.")
    };

    ws.onmessage = function (message) {
      if(message.data.substring(0,9) === 'game over'){
        setGameOver(true);
        wsRef.current.close();
        return;
      }

      const dataFromServer = JSON.parse(message.data);      
      if(dataFromServer.type === "message"){
        setGame(new Chess(dataFromServer.fen));
        setPlayer2Id(dataFromServer.id);
        setPlayer2Name(dataFromServer.username);
        if(dataFromServer.gameOver){
          console.log("game is over");
          setGameOver(dataFromServer.gameOver);
          ws.close();
        }
      }

      if(dataFromServer.type === "userInfo"){
        setPlayer2Id(dataFromServer.id);
        const newMatch = {
          "matchId": 0,
          "player1Id": auth.user.id,
          "player2Id": dataFromServer.id,
          "playerWinnerId": 0,
          "startTime": null,
          "endTime": null
        }

        const init = {
          method: "POST",
          headers: {
            "Content-type": "application/json",
            'Authorization': `Bearer ${auth.user.token}`
          },
          body: JSON.stringify(newMatch),
        };

        fetch("http://localhost:8080/game/players/matches", init).then(response => {
          if(response.status === 201){
            return response.json();
          }else{
            return Promise.reject("response not 201 CREATED"); 
          }}).then(data => {
            if(data.matchId){
              setMatchId(data.matchId);
            }
          });

      }
    };

  }, [auth.user]);

  function onDrop(sourceSquare, targetSquare) {
    const ws = wsRef.current;
    const gameCopy = { ...game };
    const move = gameCopy.move({
      from: sourceSquare,
      to: targetSquare,
      promotion: 'q' 
    });
    
    if(gameCopy.turn() === myColor){
      if(move){
        ws.send(JSON.stringify({
          type:"message",
          fen: game.fen(),
          gameOver: game.game_over(),
          matchId: matchId,
          id: auth.user.id,
          username: auth.user.username
        }))
      }

      if(gameCopy.game_over()){
        console.log("game is over with game info");
        ws.send("game over," + matchId + "," + auth.user.id);
        setGameOver(true);
        ws.close();
      }

      setGame(gameCopy);
      return move;
    }else{
      return false;
    }
  }

  const EndGameButton = () => {
    wsRef.current.send("game over," + matchId + "," + player2Id);
    setGameOver(true);
    wsRef.current.close();
  }

  return (
    <div className="card text-center">
      {gameOver ? <h3>Game Over</h3> :
        <div id="whiteBoard" className="card-body">
        <div className="row">
        <div id="container">
          <Chessboard
            id="PlayVsPlay"
            animationDuration={200}
            boardWidth={boardWidth}
            position={game.fen()}
            onPieceDrop={onDrop}
            customBoardStyle={{
              borderRadius: '4px',
              boxShadow: '0 5px 15px rgba(0, 0, 0, 0.5)'
            }}
            ref={chessboardRef}
          />
          </div>
          <h5 className="YouAreWhite">Me: {auth.user.username} <br/>Opponent: {player2Name ? player2Name : "????"}</h5>
          <h2 className="YouAreWhite"><strong>Your pieces are: White</strong></h2>
          <Link to="/" onClick={EndGameButton} className="btn btn-warning" >Forfeit</Link>
          </div>
        </div>
      }
    </div>
  );
}