import {Chessboard} from 'react-chessboard'
import React, { useState, useRef } from 'react';
import Chess from 'chess.js';
import { updateMove } from '../services/BoardAPI';

function Board(){

  let isBlack = false;
  
  const [game, setGame] = useState(new Chess());
  

  function onDrop(sourceSquare, targetSquare) {
    console.log(sourceSquare)
    const gameCopy = { ...game };
    const move = () => gameCopy.move({
      from: sourceSquare,
      to: targetSquare,
      promotion: 'q'
    });
    updateMove(isBlack, sourceSquare, targetSquare, false);
    setGame(gameCopy);
    return move;
  }

  return(
    <Chessboard
        id="BasicBoard"
        onPieceDrop={onDrop}
      />
  );
}

export default Board;
