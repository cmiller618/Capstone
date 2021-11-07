import { useEffect, useState } from "react";
import { Link, useParams } from "react-router-dom"
import { findPlayerByProfileId } from "../../services/PlayersAPI"
import ProfileMatches from "./ProfileMatches";

function Profile(){

  const[player, setPlayer] = useState({
    "profileId": 0,
    "name": "",
    "password": "",
    "email": "",
    "playerMatch": {
      "playerProfileId": 0,
      "wins": 0,
      "losses": 0,
      "ties": 0,
      "playerName": ""
    }
  });

  const { id } = useParams();

  useEffect(() => {
    if(id) {
      findPlayerByProfileId(id).then((data) => {
        setPlayer(data);
      });
    }
  }, [id]);


  return(
    <div className="container">
      <div className="card h-100">
        <div className="card-body">
          <h1 className="card-title">{player.name.toUpperCase} Profile Page</h1>
          <div className="row"><strong>Player Email: {player.email}</strong></div>
          <div className="row">
            <ProfileMatches />
          </div>
        </div> 
      </div>
    </div>
  );
}