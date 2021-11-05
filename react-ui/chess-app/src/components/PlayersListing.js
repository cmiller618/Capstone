import { useState , useEffect } from "react";
import { findTopFive } from "../service/MatchesAPI"
import TopFive from "./TopFive";

function PlayersListing(){

  const[topPlayers, setTopPlayers] = useState([]);

  useEffect(() => {
    findTopFive().then((data) => setTopPlayers(data));
  },[]);

  return(
    <div classNam="container">
      {topPlayers.map(topPlayer =>(<TopFive key={topPlayer.playerProfileId} topPlayer={topPlayer}/>))}  
    </div>
  );
}

export default PlayersListing;