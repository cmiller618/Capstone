import { useRef, useState } from 'react';
import { w3cwebsocket as W3CWebSocket } from "websocket";
import { Chessboard } from 'react-chessboard';
import React, { useContext, useEffect } from 'react';

import AuthContext from "../context/AuthContext";
import Chess from 'chess.js';

const client = new W3CWebSocket("ws://127.0.0.1:8000");


export default function BoardPvP({ boardWidth }) {
  const chessboardRef = useRef();
  const [game, setGame] = useState(new Chess());

  const auth = useContext(AuthContext);

  useEffect(() => {

    client.onopen = () => {
        console.log("Web socket client connected.");
    }

    client.onmessage = (message) => {
        const dataFromServer = JSON.parse(message.data);
        console.log('got reply! ', dataFromServer);
        if (dataFromServer.type === "message") {
          safeGameMutate((game) => {
            game.undo();
          });
        }
    };

  }, []);

  function safeGameMutate(modify) {
    setGame((g) => {
      const update = { ...g };
      modify(update);
      return update;
    });
  }

  function onDrop(sourceSquare, targetSquare) {
    const gameCopy = { ...game };
    const move = gameCopy.move({
      from: sourceSquare,
      to: targetSquare,
      promotion: 'q' // always promote to a queen for example simplicity
    });

    if(move){
      client.send(JSON.stringify({
        type: "message",
        move: move,
        user: auth.user.username
    }));
    }

    setGame(gameCopy);
    return move;
  }

  return (
    <div>
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
      <button
        className="rc-button"
        onClick={() => {
          safeGameMutate((game) => {
            game.reset();
          });
          chessboardRef.current.clearPremoves();
        }}
      >
        reset
      </button>
      <button
        className="rc-button"
        onClick={() => {
          safeGameMutate((game) => {
            game.undo();
          });
          chessboardRef.current.clearPremoves();
        }}
      >
        undo
      </button>
    </div>
  );
}