import { useState, useEffect, useContext } from "react";
import { useParams } from "react-router-dom"
import { findMatchesByProfileId } from "../../services/MatchesAPI"
import AuthContext from "../../context/AuthContext";
import Matches from "./Matches";

function ProfileMatches(){

  
  const auth = useContext(AuthContext);

  const[playerMatches, setPlayerMatches] = useState([]);

  const { id } = useParams();

  useEffect(() => {
    if(id) {
      findMatchesByProfileId(id, auth).then((data) => {
        setPlayerMatches(data);
      });  
    }
  },[id, auth]);

  return(
    <>
    <div class="card-footer text-muted">
      <h2 class="card-subtitle mb-2 text-muted">Games History</h2>
    </div> 
    <div className="card-body">
      <table className="table table-dark table-striped">
        <thead>
          <tr>
            <th scope="col"><strong>Player 1 Username</strong></th>
            <th scope="col"><strong>Player 2 Username</strong></th>
            <th scope="col"><strong>Winner</strong></th>
            <th scope="col"><strong>Match Start Time</strong></th>
            <th scope="col"><strong>Match End Time</strong></th>
          </tr>
        </thead>
        <tbody>
        {playerMatches.map(match =>(<Matches key={match.matchId} match={match}/>))}  
        </tbody>
      </table>  
    </div>
    </>
  )

}

export default ProfileMatches;