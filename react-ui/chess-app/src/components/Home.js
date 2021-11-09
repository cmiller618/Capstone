import { useContext } from "react";
import { Link } from "react-router-dom";

import './Home.css';

import PlayersListing from "./PlayersListing";
import AuthContext from "../context/AuthContext";

function Home(){
  const auth = useContext(AuthContext);

  return(
    <>
    <div className="container">
      <h2 id="pageHeader">Welcome To Chess Online by Team "Oops, we pushed to master"</h2>
      <h4 id="rankHeader">Global Player Ranking</h4>
      <table className="table table-dark table-striped">
        <thead>
          <tr>
            <th scope="col"><strong>Player Username</strong></th>
            <th scope="col"><strong>Wins</strong></th>
            <th scope="col"><strong>Losses</strong></th>
            <th scope="col"><strong>Ties</strong></th>
          </tr>
        </thead>
        <PlayersListing />
      </table>
    </div>
    {auth.credentials ? 
      <div className="container">
        <h3>Play A Game</h3>
      </div> : <div id ="signInMessage" className="container mt-3"><p>In order to play a game online you must login or create an account!</p></div>}
    </>
  );
}

export default Home;