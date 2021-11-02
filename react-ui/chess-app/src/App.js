import {BrowserRouter as Router, Route, Switch, Redirect} from "react-router-dom";
import { useEffect, useState } from "react";
import AuthContext from "./context/AuthContext";
import Home from "./components/Home";
import Nav from "./components/Nav";
import './App.css';

function App() {

  const[username, setUsername] = useState();

  const auth ={
    username: username,
    login: (loginName) => setUsername(loginName),
    logout: () => setUsername()
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
        </Switch>
      </Router>
    </AuthContext.Provider>
  );
}

export default App;
