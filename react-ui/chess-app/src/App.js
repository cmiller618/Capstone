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

  const login = (creds) => {
    setCredentials(creds);
   


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
            {credentials ? <PvP /> : <Redirect to="/login" />}
          </Route>

          <Route path="/game/pvc">
            {credentials ? <PvC /> : <Redirect to="/login" />}
          </Route>

        </Switch>
      </Router>
    </AuthContext.Provider>
  );
}

export default App;
