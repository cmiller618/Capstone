import { useContext, useState } from 'react';
import { Link, useHistory } from 'react-router-dom';
import { useForm } from "react-hook-form";

import AuthContext from '../context/AuthContext';
import Errors from './MainUI/Errors';

function Register(){

  const { register, handleSubmit, formState: { errors } } = useForm();
  const [username, setUsername] = useState('');
  const [password, setPassword] = useState('');
  const [confirmPassword, setConfirmPassword] = useState('');
  const [firstName, setFirstName] = useState('');
  const [lastName, setLastName] = useState('');
  const [email, setEmail] = useState('');
  const [error, setError] = useState([]);

  const auth = useContext(AuthContext);

  const history = useHistory();

  const usernameOnChangeHandler = (event) => {
    setUsername(event.target.value);
  };

  const passwordOnChangeHandler = (event) => {
    setPassword(event.target.value);
  };

  const confirmPasswordOnChangeHandler = (event) => {
    setConfirmPassword(event.target.value);
  };

  const firstNameOnChangeHandler = (event) => {
    setFirstName(event.target.value);
  };

  const lastNameOnChangeHandler = (event) => {
    setLastName(event.target.value);
  };

  const emailOnChangeHandler = (event) => {
    setEmail(event.target.value);
  };

  const formSubmitHandler = (event) => {
   
    setError([]);

    if (password !== confirmPassword) {
      setError(['password and confirm password don\'t match']);
      return;
    }

    const newUser = {
      username,
      password,
      firstName,
      lastName,
      email
    };

    const init = {
      method: 'POST',
      headers: {
        'Content-Type': 'application/json'
      },
      body: JSON.stringify(newUser)
    };

    fetch('http://localhost:8080/create_account', init)
      .then(response => {

        if (response.status === 201 || response.status === 400) {
          return response.json();
        }
        return Promise.reject('Something unexpected went wrong :)');
      })
      .then(data => {

        if (data.appUserId) {
          const init = {
            method: 'POST',
            headers: {
              'Content-Type': 'application/json'
            },
            body: JSON.stringify(newUser)
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
                history.push(`/`);
              } else {
                setError(['login failure']);
              }
            })
            .catch(error => console.log(error));

        } else {
          setError(data);
        }
      })
      .catch(error => console.log(error));
  };

  return (
    <div className="container p-3">
      <h2 className="my-4">Register</h2>
      <form onSubmit={handleSubmit(formSubmitHandler)}>

        <div className="form-group mb-3 row">
          <label htmlFor="username" className="col-sm-2 col-form-label"><strong>Username:</strong></label>
          <div className="col-sm-10">
          <input className="form-control" type="text" id="username" name="username" 
            value={username} onChange={usernameOnChangeHandler} {...register("username", { onChange: (event) => setUsername(event.target.value) ,
              required: "A username is required to register an account", maxLength: { value: 50 , message: "username must be less than 50 characters" }})}/>
          </div>
        </div>

        <div className="form-group mb-3 row">
          <label htmlFor="firstName" className="col-sm-2 col-form-label"><strong>First Name:</strong></label>
          <div className="col-sm-10">
          <input className="form-control" type="text" id="firstName" name="firstName" 
            value={firstName} onChange={firstNameOnChangeHandler} {...register("firstName", { onChange: (event) => setFirstName(event.target.value) ,
              required: "A first name is required to register an account", maxLength: { value: 75, message: "First name must be less than 75 characters" }})}/>
          </div>
        </div>

        <div className="form-group mb-3 row">
          <label htmlFor="lastName" className="col-sm-2 col-form-label"><strong>Last Name:</strong></label>
          <div className="col-sm-10">
          <input className="form-control" type="text" id="lastName" name="lastName" 
            value={lastName} onChange={lastNameOnChangeHandler} {...register("lastName", { onChange: (event) => setLastName(event.target.value) ,
              required: "A last name is required to register an account", maxLength: { value: 75, message: "Last name must be less than 75 characters" }})}/>
          </div>
        </div>

        <div className="form-group mb-3 row">
          <label htmlFor="email" className="col-sm-2 col-form-label"><strong>Email:</strong></label>
          <div className="col-sm-10">
          <input className="form-control" type="text" id="email" name="email" 
            value={email} onChange={emailOnChangeHandler} {...register("email", { onChange: (event) => setEmail(event.target.value) ,
              required: "A email is required to register an account", maxLength: { value: "250", message: "Email must be less than 250 characters"}, pattern: {value: /^[a-zA-Z0-9.!#$%&’*+/=?^_`{|}~-]+@[a-zA-Z0-9-]+(?:\.[a-zA-Z0-9-]+)*$/, message: "Email must be valid"} })}/>
              </div>
        </div>

        <div className="form-group mb-3 row">
          <label htmlFor="password" className="col-sm-2 col-form-label"><strong>Password:</strong></label>
          <div className="col-sm-10">
          <input className="form-control" type="password" id="password" name="password" 
            value={password} onChange={passwordOnChangeHandler} {...register("password", { onChange: (event) => setPassword(event.target.value),
              required: "A password is required", minLength: {value: 8, message: "password must be 8 characters or greater"}, pattern: {value: /^(?=.*[a-z])(?=.*[A-Z])(?=.*[0-9])(?=.*[!@#$%^&*])(?=.{8,})/, message: "Password must contain at least 1 lowercase alphabetical character, at least 1 uppercase alphabetical character, at least 1 numeric character, must contain at least one special character"}
            })}/>
            </div>
        </div>

        <div className="form-group mb-3 row">
          <label htmlFor="confirmPassword" className="col-sm-2 col-form-label"><strong>Confirm Password:</strong></label>
          <div className="col-sm-10">
          <input className="form-control" type="password" id="confirmPassword" name="confirmPassword" 
            value={confirmPassword} onChange={confirmPasswordOnChangeHandler} />
          </div>
        </div>

        <div className="mt-5">
          <button className="btn btn-success" type="submit">
            <i className="bi bi-plus-circle-fill"></i> Register</button>
          <Link to="/" className="btn btn-warning ml-2">
            <i className="bi bi-x"></i> Cancel
          </Link>
        </div>

        {errors.username || errors.firstName || errors.lastName || errors.email || errors.password ? 
        <div className="alert alert-danger mt-3" role="alert">
          {errors.username && <span><p>{errors.username.message}</p></span>}
          {errors.firstName && <span><p>{errors.firstName.message}</p></span>}
          {errors.lastName && <span><p>{errors.lastName.message}</p></span>}
          {errors.email && <span><p>{errors.email.message}</p></span>}
          {errors.password && <span><p>{errors.password.message}</p></span>}
        </div> : null}
        <Errors errors={error} />

      </form>
    </div>
  );
}

export default Register;