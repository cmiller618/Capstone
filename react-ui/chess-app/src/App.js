import {BrowserRouter as Router, Route, Switch, Redirect} from "react-router-dom";
import { useEffect, useState } from "react";
import jwt_decode from 'jwt-decode';
import AuthContext from "./context/AuthContext";
import Home from "./components/MainUI/Home";
import Nav from "./components/MainUI/Nav";
import Login from "./components/Login";
import BoardHost from "./components/BoardHost";
import BoardJoin from "./components/BoardJoin";
import Register from "./components/Register"
import Profile from "./components/PlayerProfileUI/Profile";
import BoardCPU from "./components/BoardCPU";
import './App.css';

const TOKEN_KEY = "chess-api-token";

function App() {

  const [user, setUser] = useState(null);
  const [initialized, setInitialized] = useState(false);

  useEffect(() => {
    const token = localStorage.getItem(TOKEN_KEY);

    if(token){
      login(token);
    }

    setInitialized(true);
  }, []);


  const login = (token) => {
    localStorage.setItem(TOKEN_KEY, token);

    const { id, sub: username, roles: rolesString } = jwt_decode(token);
    const roles = rolesString.split(',');
    const user = {
      id,
      username,
      roles,
      token,
      hasRole(role){
        return this.roles.includes(role);
      }
    };

    console.log(user);
    setUser(user);
    return user;
  }


  const logout = () => {
    localStorage.removeItem(TOKEN_KEY);
    setUser(null);
  };

  const auth = {
    user: user ? {...user} : null,
    login,
    logout
  };

  if(!initialized){
    return null;
  }

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

          <Route path="/register">
            <Register />
          </Route>

          <Route path="/profile/:id">
            {user ? <Profile /> : <Redirect to="/login" />}
          </Route>

          <Route path="/game/board">
            {user ? <BoardHost /> : <Redirect to="/login" />}
          </Route>
          <Route path="/game/boardJoin">
            {user ? <BoardJoin /> : <Redirect to="/login" />}
          </Route>
          <Route path="/game/boardCPU">
            <BoardCPU />
          </Route>
        </Switch>

      </Router>
    </AuthContext.Provider>
  );
}

export default App;
