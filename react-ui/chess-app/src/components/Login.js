import { useState , useContext } from "react";
import { Link, useHistory } from "react-router-dom";
import AuthContext from "../context/AuthContext";
import Errors from "./MainUI/Errors";

function Login(){

  const [username, setUsername] = useState('');
  const [password, setPassword] = useState('');
  const [errors, setErrors] = useState([]);

  const auth = useContext(AuthContext);

  const history = useHistory();

  const usernameOnChangeHandler = (event) => {
    setUsername(event.target.value);
  };

  const passwordOnChangeHandler = (event) => {
    setPassword(event.target.value);
  };

  const formSubmitHandler = (event) => {
    event.preventDefault();

    const authAttempt = {
      username,
      password
    };

    const init = {
      method: 'POST', // GET by default
      headers: {
        'Content-Type': 'application/json'
      },
      body: JSON.stringify(authAttempt)
    };

    fetch('http://localhost:8080/authenticate', init)
      .then(response => {
        if (response.status === 200) {
          return response.json();
        } else if (response.status === 403) {
          return null;
        }
        return Promise.reject('Something unexpected went wrong :)');
      })
      .then(data => {
        if (data) {
          auth.login(data.jwt_token);
          history.push('/');
        } else {
          setErrors(['login failure']);
        }
      })
      .catch(error => console.log(error));
  };

  return(
    <div className="container">
      <h1>Login</h1>
      <Errors errors={errors} />
      <form onSubmit={formSubmitHandler}>
        <div className="mb-2">
            <label htmlFor="username" className="form-label">Username</label>
            <input type="text" id="username" name="username" className="form-control"
                value={username} onChange={usernameOnChangeHandler} />
        </div>

        <div className="mb-2">
            <label htmlFor="password" className="form-label">Password</label>
            <input type="password" id="password" name="password" className="form-control"
                value={password} onChange={passwordOnChangeHandler} />
        </div>

        <div>
            <Link to="/" className="btn btn-secondary me-2">Cancel</Link>
            <button type="submit" className="btn btn-primary">Log In</button>
        </div>
      </form>
    </div>
  )
}

export default Login;