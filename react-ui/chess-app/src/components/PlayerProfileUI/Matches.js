function Matches({match}){
  return(
    <ul className="list-group list-group-horizontal">
      <li className="list-group-item flex-fill"><strong>Player1:</strong> {match.player1Id} </li>
      <li className="list-group-item flex-fill"><strong>Player2:</strong>{match.player2Id}</li>
      <li className="list-group-item flex-fill"><strong>Match Winner:</strong>{match.playerWinnerId}</li>
      <li className="list-group-item flex-fill"><strong>Match Start Time:</strong>{match.startTime}</li>
      <li className="list-group-item flex-fill"><strong>Match End Time:</strong>{match.endTime}</li>
      <li className="list-group-item flex-fill"><strong>Match Duration:</strong>{match.endTime - match.startTime}</li>
  </ul> 
  );
}

export default Matches;