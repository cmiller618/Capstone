import { Link } from "react-router-dom";
import { useContext } from "react";
import AuthContext from "../context/AuthContext";

function Nav(){
  const auth = useContext(AuthContext);

  return(
    <nav className="nav">
      <h1 className="col">The Amazing World Of Online Chess</h1>
      <div className="d-grid gap-2 d-md-block">
        {auth.credentials ? <button className="btn btn-dark" onClick={auth.logout} data-bs-toggle="button">Log Out</button> :
        <Link to="/login" className="btn btn-secondary" data-bs-toggle="button">Login</Link>}
      </div>
      {auth.credentials ? <strong>User: {auth.credentials.username}</strong> : null}
    </nav>
  );
}

export default Nav;