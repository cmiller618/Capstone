import React, { useContext } from "react";
import './Board.css'
import { pieces } from "../images/pieceImages";
import AuthContext from "../context/AuthContext";
import { useHistory } from "react-router";
import { getCurrentBoard, getNewBoard } from "../services/BoardAPI";
import { useState } from "react";

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
  
return{

};

}

export default Board;