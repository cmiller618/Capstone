import React, { useContext } from "react";
import './Board.css'
import { pieces } from "../images/pieceImages";
import AuthContext from "../context/AuthContext";
import { useHistory } from "react-router";
import { getCurrentBoard, getNewBoard } from "../services/BoardAPI";
import { useState } from "react";
import pieceRenderer from "./pieceRenderer";

function Board(){

  const [board, setBoard] = useState([]);
  const authContext = useContext(AuthContext);
  const history = useHistory();

  let turns = 1; 

  if(turns === 1){
    getNewBoard()
    .then(result => setBoard(result))
    .catch((error) =>{
      if(error === 403) {
        authContext.logout();
        history.push("/login");
      } else{
        history.push("/error", )
      }
    }, [authContext, history]);
  }else{
    getCurrentBoard()
    .then(result => setBoard(result))
    .catch((error) =>{
      if(error === 403) {
        authContext.logout();
        history.push("/login");
      } else{
        history.push("/error", )
      }
    }, [authContext, history]);
  }
  
  let count = 0;
  for(let i = 0; i < 8; i++){
    for(let j = 0; j < 8; j++){
      if(count % 2 === 0){
        return(
          <div className="dark-square">
            <pieceRenderer {...pieceRenderer(board[i][j])}></pieceRenderer>
            </div>
        )
      }else{
        return(
          <div className="light-square">
            <pieceRenderer {...pieceRenderer(board[i][j])}></pieceRenderer>
            </div>
        )
      }
    }
  }

}

export default Board;