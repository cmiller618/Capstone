import { Link } from "react-router-dom";
import { useContext } from "react";

function TopFive({topPlayer}){

  return(
    <ul className="list-group list-group-horizontal">
      <li className="list-group-item flex-fill"><strong>Player:</strong> {topPlayer.playerName} </li>
      <li className="list-group-item flex-fill"><strong>Wins:</strong>{topPlayer.wins}</li>
      <li className="list-group-item flex-fill"><strong>Losses:</strong>{topPlayer.losses}</li>
      <li className="list-group-item flex-fill"><strong>Ties:</strong>{topPlayer.ties}</li>
  </ul>
  );
}

export default TopFive;