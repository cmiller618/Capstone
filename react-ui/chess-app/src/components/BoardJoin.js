import { useRef, useState } from 'react';
import { Chessboard } from 'react-chessboard';
import React, { useContext, useEffect } from 'react';
import { Link } from "react-router-dom"
import AuthContext from "../context/AuthContext";
import Chess from 'chess.js';
import './Board.css'

const socketUrl = "ws:localhost:8080/messages"

export default function BoardJoin({ boardWidth }) {
  const myColor = 'w';
  const auth = useContext(AuthContext);

  const chessboardRef = useRef();
  const [game, setGame] = useState(new Chess());
  const [matchId, setMatchId] = useState();
  const [gameOver, setGameOver] = useState();
  const [player1Id, setPlayer1Id] = useState();
  const [player1Name, setPlayer1Name] = useState();


  const wsRef = useRef(null);

  useEffect(() => {
    wsRef.current = new WebSocket(socketUrl);
    const ws = wsRef.current;

    ws.onopen = () => {
      console.log("websocket successfully connected.")
      ws.send(JSON.stringify({
        type: "userInfo",
        id: auth.user.id,
        username: auth.user.username
      }))
    }

    ws.onclose = () => {
      console.log("websocket closed.")
    };

    ws.onmessage = function (message) {
      if(message.data.substring(0,9) === 'game over'){
        setGameOver(true)
        wsRef.current.close();
        return;
      }

      const dataFromServer = JSON.parse(message.data);
      console.log(dataFromServer);
      if(dataFromServer.type === "message"){
        setGame(new Chess(dataFromServer.fen));
        setMatchId(dataFromServer.matchId);
        setPlayer1Id(dataFromServer.id);
        setPlayer1Name(dataFromServer.username);
        if(dataFromServer.gameOver){
          console.log("game is over");
          setGameOver(dataFromServer.gameOver);
          ws.close();
        }
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
    wsRef.current.send("game over," + matchId + "," + player1Id);
    setGameOver(true);
    console.log(gameOver);
    wsRef.current.close();
  }

  return (
    <div className="card text-center">
      {gameOver ? <h3>Game Over</h3> :
        <div id="blackBoard" className="card-body">
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
          <h5 className="YouAreBlack">Me: {auth.user.username} <br/>Opponent: {player1Name ? player1Name : "????"}</h5>
          <h2 className="YouAreBlack"><strong>Your pieces are: Black</strong></h2>
          <Link to="/" onClick={EndGameButton} className="btn btn-warning" >Forfeit</Link>
          </div>
        </div>
      }
    </div>
  );
}