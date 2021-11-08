import React from "react";
import { useForm } from "react-hook-form";
import { useContext } from 'react';
import AuthContext from "../context/AuthContext";
import { updatePlayer }  from "../services/PlayersAPI"

function RegisterAccountInfo() {
  const { register, handleSubmit, formState: { errors } } = useForm();
  const auth = useContext(AuthContext);

  const onSubmit = (data) => {
    const profile = {
      "profileId": auth.user.id,
      "username": auth.user.username,
      "firstName": data.firstName,
      "lastName": data.lastName,
      "email": data.email,
      "playerStats": null
    };

    updatePlayer(profile,auth);
  };

  console.log(errors);
   
  return (
    <div className="container p-3">
    <h1>Account Information</h1>
      <form onSubmit={handleSubmit(onSubmit)}>

          <div className="mb-3 row">
            <label htmlFor="firstName" className="col-sm-2 col-form-label"><strong>First Name:</strong></label>
            <div className="col-sm-10">
              <input placeholder="enter your first name" type="firstName" className="form-control" id="firstName" {...register("firstName", { required: "A first name is required!", maxLength:{ value: 75, message: "First name cannot exceed 75 characters" } })}/>
            </div>
          </div>

          <div className="mb-3 row">
            <label htmlFor="lastName" className="col-sm-2 col-form-label"><strong>Last Name:</strong></label>
            <div className="col-sm-10">
              <input placeholder="enter your last name" type="lastName" className="form-control" id="lastName" {...register("lastName", { required: "A last name is required!", maxLength: {
                value: 75, message: "Last name cannot exceed 75 characters" } })}/>
            </div>
          </div>

          <div className="mb-3 row">
            <label htmlFor="Email" className="col-sm-2 col-form-label"><strong>Email:</strong></label>
            <div className="col-sm-10">
              <input placeholder="enter a email" type="email" className="form-control" id="email" {...register("email", { required: "A Email is required!", maxLength: { value: 250 , message: "The maximum length for a email is 250 characters!"}, 
              pattern: { value: /^(([^<>()\[\]\\.,;:\s@"]+(\.[^<>()\[\]\\.,;:\s@"]+)*)|(".+"))@((\[[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\])|(([a-zA-Z\-0-9]+\.)+[a-zA-Z]{2,}))$/ , 
                message: "Please enter a valid email!" } })}/>
            </div>
          </div>

          

          <div>
          {errors.firstName && <span><p>{errors.firstName.message}</p></span>}
          </div>
          <div>
          {errors.lastName && <span><p>{errors.lastName.message}</p></span>}
          </div>
          <div>
          {errors.email && <span><p>{errors.email.message}</p></span>}
          </div>

        <input type="submit" className="btn btn-success me-2" />
      </form>
    </div>
  );
}

export default RegisterAccountInfo;