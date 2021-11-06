import { useState, useEffect } from "react";
import { useParams } from "react-router-dom"
import { findMatchesByProfileId } from "../../services/MatchesAPI"
import Matches from "./Matches";

function ProfileMatches(){

  const[playerMatches, setPlayerMatches] = useState([]);

  const { id } = useParams();

  useEffect(() => {
    if(id) {
      findMatchesByProfileId(id).then((data) => {
        setPlayerMatches(data);
      });  
    }
  },[id]);

  return(
    <div classNam="container">
      {playerMatches.map(match =>(<Matches key={match.matchId} match={match}/>))}  
    </div>
  )

}

export default ProfileMatches;