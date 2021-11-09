import {Chessboard} from 'react-chessboard'
import React from 'react';
import ReactDOM from 'react-dom';
import {Knight} from 'react-chessboard'
import { getNewBoard } from '../services/BoardAPI';
import { Link } from 'react-router-dom';

function Board(){

  
  
  return(
    <div>
    <Chessboard id="BasicBoard"></Chessboard>
  <button type="submit" className="btn btn-success">End Turn</button>
  <Link to="/" className="btn btn-warning">End Game</Link>
  </div>
  
  
  );
  
}

export default Board;
