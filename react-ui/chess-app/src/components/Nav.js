import { Link } from "react-router-dom";
import { useContext } from "react";
import AuthContext from "../context/AuthContext";

function Nav(){
  const auth = useContext(AuthContext);

  return(
    <nav className="nav">
      <h1 className="col">The Amazing World Of Online Chess</h1>
      
      <div className="d-grid gap-2 d-md-block">
        
        {!auth.user && (
          <>
          <Link to="/register" className="btn btn-info">Create Account</Link>
          <Link to="/login" className="btn btn-secondary">Login</Link> 
          </>
        )}

        {auth.user && (
          <div>
            <p><strong>Welcome {auth.user.username}!</strong></p>
            <button onClick={() => auth.logout()} className="btn btn-danger">Logout</button>
          </div>
        )}
      </div>
    </nav>
  );
}

export default Nav;