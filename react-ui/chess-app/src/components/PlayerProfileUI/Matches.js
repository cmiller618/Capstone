import { useState, useEffect, useContext } from "react";
import { findPlayerByProfileId } from "../../services/PlayersAPI"
import AuthContext from "../../context/AuthContext";

function Matches({match}){

  const [player1Username, setPlayer1Username] = useState('');
  const [player2Username, setPlayer2Username] = useState('');
  const [winnerUsername, setWinnerUsername] = useState('');

  const auth = useContext(AuthContext);

  useEffect(() => {
    if(match.player1Id && match.player2Id) {
      findPlayerByProfileId(match.player1Id, auth).then((data) => {
        setPlayer1Username(data.username);
      }) 
      findPlayerByProfileId(match.player2Id, auth).then((data) => {
        setPlayer2Username(data.username); 
      });
      if(match.playerWinnerId !== 0) {
        findPlayerByProfileId(match.playerWinnerId, auth).then((data) => {
          setWinnerUsername(data.username);
        });
      }else{
        setWinnerUsername("Tie");
      }
    }

  },[match.player1Id, match.player2Id, match.playerWinnerId, auth]);

  return(
    <tr>
      <th scope="row">{player1Username}</th>
      <td>{player2Username}</td>
      <td>{winnerUsername}</td>
      <td>{match.startTime}</td>
      <td>{match.endTime}</td>
    </tr>
  );
}

export default Matches;