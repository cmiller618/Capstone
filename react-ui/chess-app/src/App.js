import {BrowserRouter as Router, Route, Switch, Redirect} from "react-router-dom";
import { useEffect, useState } from "react";
import AuthContext from "./context/AuthContext";
import Home from "./components/Home";
import Nav from "./components/Nav";
import Login from "./components/Login"
import PvP from "./components/PvP";
import PvC from "./components/PvC";
import './App.css';

const CREDENTIALS_KEY = "chess-credentials";

function App() {

  const[credentials, setCredentials] = useState();

  useEffect(()=> {
    const creds = localStorage.getItem(CREDENTIALS_KEY);
    if (creds){
      setCredentials(JSON.parse(creds));
    }
  }, []);

  const login = (candidate) => {
    let creds;

    if(candidate.username === "user" && candidate.password === "user"){
      creds = {
        username: candidate.username,
        role: "USER"
      }
    }else if(candidate.username === "admin" && candidate.password === "admin"){
      creds = {
        username: candidate.username,
        role: "ADMIN"
      }
    }

    if(creds){
      setCredentials(creds);
      localStorage.setItem(CREDENTIALS_KEY, JSON.stringify(creds));
      return true;
    }

    return false;
  };

  const logout = () => {
    setCredentials();
    localStorage.removeItem(CREDENTIALS_KEY);
  }

  const auth ={
    credentials,
    login: login,
    logout: logout
  };

  return(
    <AuthContext.Provider value={auth}>
      <Router>
        <div className="header">
          <div className="container">
            <div className="row pt-2">
              <div className="col align-self-center">
                <Nav />
              </div>
            </div>
          </div>
        </div>
        <Switch>
          <Route exact path="/">
            <Home />
          </Route>

          <Route path="/login">
            <Login />
          </Route>

          <Route path="/game/pvp">
            <PvP />
          </Route>

          <Route path="/game/pvc">
            <PvC />
          </Route>

        </Switch>
      </Router>
    </AuthContext.Provider>
  );
}

export default App;
