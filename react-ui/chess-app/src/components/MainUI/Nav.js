import { Link } from "react-router-dom";
import { useContext } from "react";

import './Nav.css'

import AuthContext from "../../context/AuthContext";

function Nav(){
  const auth = useContext(AuthContext);

  return(
    <nav className="nav">
      <h1 className="col font-weight-bold" id="title">ChessMaster</h1>
      
      <div className="d-grid gap-2 d-md-block">
        
        {!auth.user && (
          <div>
          <Link to="/" className="btn btn-primary ms-1">Home</Link>
          <Link to="/register" className="btn btn-info ms-1">Register</Link>
          <Link to="/login" className="btn btn-success ms-1">Login</Link> 
          <Link to="/game/boardCPU" className="btn btn-success ms-1">Play vs CPU</Link>
          </div>
        )}

        {auth.user && (
          <div>
            <p id="welcomeMessage"><strong>Welcome {auth.user.username}!</strong></p>
            <Link to="/" className="btn btn-primary ms-1">Home</Link>
            <Link to={`/profile/${auth.user.id}`} className="btn btn-dark ms-1">View Profile</Link>
            <button onClick={() => auth.logout()} className="btn btn-danger ms-1">Logout</button>
            <Link to="/game/boardCPU" className="btn btn-success ms-1">Play vs CPU</Link>
          </div>
        )}

      </div>
    </nav>
  );
}

export default Nav;