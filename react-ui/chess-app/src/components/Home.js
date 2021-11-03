import PlayersListing from "./PlayersListing";
import AuthContext from "../context/AuthContext";
import { useContext } from "react";
import { Link } from "react-router-dom";

function Home(){
  const auth = useContext(AuthContext);

  return(
    <>
    <div className="container">
      <h2>Welcome To Chess Online</h2>
      <p>To play online please login or create an account!</p>
      <h4>Global Player Ranking</h4>
      <PlayersListing />
    </div>
    {auth.credentials ? <div className="container">
        <h3>Play A Game</h3>
        <Link to="/game/pvp" className="btn btn-success">PvP Match Making</Link>
        <Link to="/game/pvc" className="btn btn-warning">PvC Match Making</Link>
    </div> : <div></div>}
    </>
  );
}

export default Home;