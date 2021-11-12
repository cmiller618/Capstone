import { useState , useEffect } from "react";
import { findTopFive } from "../../services/MatchesAPI"
import TopFive from "./TopFive";

function PlayersListing(){

  const[topPlayers, setTopPlayers] = useState([]);

  useEffect(() => {
    findTopFive().then((data) => setTopPlayers(data));
  },[]);

  return(
    <tbody>
      {topPlayers.map(topPlayer =>(<TopFive key={topPlayer.playerProfileId} topPlayer={topPlayer}/>))}  
    </tbody>
  );
}

export default PlayersListing;