function TopFive({topPlayer}){

  return(
    <tr>
      <th scope="row">{topPlayer.playerName}</th>
      <td>{topPlayer.wins}</td>
      <td>{topPlayer.losses}</td>
      <td>{topPlayer.ties}</td>
    </tr>
  );
}

export default TopFive;