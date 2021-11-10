import { Chessboard } from 'react-chessboard'
import { Link } from 'react-router-dom';
import { w3cwebsocket as W3CWebSocket } from "websocket";

import React, { useContext, useEffect } from 'react';
import AuthContext from "../context/AuthContext";

const client = new W3CWebSocket("ws://127.0.0.1:8000");
const KEY_MOVE = "move";

function Board(){

  const auth = useContext(AuthContext);

  useEffect(() => {

    client.onopen = () => {
        console.log("Web socket client connected.");
    }

    client.onmessage = (message) => {
        const dataFromServer = JSON.parse(message.data);
        console.log('got reply! ', dataFromServer);
        if (dataFromServer.type === "message") {
        }
    };

  }, []);

  const onButtonClicked = () => {
    const move = localStorage.getItem(KEY_MOVE).split(",");
    console.log(move);

    client.send(JSON.stringify({
        type: "message",
        sourceSq: move[0],
        targetSq: move[1],
        piece: move[2],
        user: auth.user.username
    }));
  }

  const onEndGameClick = () => {
    console.log('Game Over. closing')
    client.close();
  }

  return(
    <div className="container center">
      <Chessboard id="BasicBoard"></Chessboard>
      <button onClick={onButtonClicked} className="btn btn-success">End Turn</button>
      <Link to="/" onClick={onEndGameClick} className="btn btn-warning">End Game</Link>
    </div>
  );
  
}

export default Board;
