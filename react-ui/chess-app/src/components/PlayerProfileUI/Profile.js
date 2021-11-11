import { useEffect, useState, useContext} from "react";
import { useParams } from "react-router-dom"

import AuthContext from "../../context/AuthContext";
import ProfileMatches from "./ProfileMatches";

function Profile(){

  const [username, setUsername] = useState('');
  const [firstName, setFirstName] = useState('');
  const [lastName, setLastName] = useState('');
  const [email, setEmail] = useState('');
  const [playerStats, setPlayerStats] = useState({});

  const auth = useContext(AuthContext);

  const { id } = useParams();

  useEffect(() => {
    
    const init = {
      headers: {
        'Authorization': `Bearer ${auth.user.token}`
      }
    }

    fetch(`http://localhost:8080/game/players/${id}`, init)
      // Response object
      .then(response => { 
        if (response.status === 404) {
          return Promise.reject(`Received 404 Not Found for Profile ID: ${id}`);
        }
        return response.json();
      })
      .then(data => {
        setUsername(data.username);
        setFirstName(data.firstName);
        setLastName(data.lastName);
        setEmail(data.email);
        setPlayerStats(data.playerStats)
      })
      .catch(error => console.log(error));
  }, [id, auth.user.token]);

  return(
      <div className="card text-center">
        <div className="card-header">
          <h1 id="usernameTitle" className="card-title">{username} Profile</h1>
        </div>
        <div class="card-footer text-muted">
          <h2 id=""class="card-subtitle mb-2 text-muted">Account Information</h2>
        </div>  
        <div className="card-body">
          <div className="row">
            <strong>First Name: {firstName}</strong>
            <strong>Last Name: {lastName}</strong>
            <strong>Email: {email}</strong>
          </div>
        </div> 
        <div class="card-footer text-muted">
          <h2 class="card-subtitle mb-2 text-muted">Games Summary</h2>
        </div>  
        <div className="card-body">
          <div className="row">
            <strong>Total Wins: {playerStats.wins}</strong>
            <strong>Total Losses: {playerStats.losses}</strong>
            <strong>Total Ties: {playerStats.ties}</strong>
          </div>
        </div>  
        <ProfileMatches /> 
      </div>
  );
}

export default Profile;