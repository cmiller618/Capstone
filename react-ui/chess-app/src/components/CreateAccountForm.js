import React from "react";
import { useForm } from "react-hook-form";
import { Link, useHistory } from "react-router-dom";


function CreateAccountForm() {
  const { register, handleSubmit, formState: { errors } } = useForm();
  const onSubmit = (data) => {
    alert(JSON.stringify(data));
  };
  console.log(errors);
   
  return (
    <div className="container p-3">
    <h1>Create Account</h1>
      <form onSubmit={handleSubmit(onSubmit)}>

          <div className="mb-3 row">
            <label for="username" className="col-sm-2 col-form-label"><strong>Username</strong></label>
            <div className="col-sm-10">
              <input placeholder="enter a username" type="username" className="form-control" id="accountUsername" {...register("username", { required: true, maxLength: 45 })} />
            </div>
          </div>

          <div className="mb-3 row">
            <label for="email" className="col-sm-2 col-form-label"><strong>Email</strong></label>
            <div className="col-sm-10">
              <input placeholder="enter a email" type="email" className="form-control" id="accountEmail" {...register("email", { required: "this is the message", maxLength: 150, pattern: /^(([^<>()\[\]\\.,;:\s@"]+(\.[^<>()\[\]\\.,;:\s@"]+)*)|(".+"))@((\[[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\])|(([a-zA-Z\-0-9]+\.)+[a-zA-Z]{2,}))$/ })} />
            </div>
          </div>

          <div className="mb-3 row">
            <label for="password" className="col-sm-2 col-form-label"><strong>Password</strong></label>
            <div className="col-sm-10">
              <input placeholder="enter a password" type="password" className="form-control" id="accountPassword" {...register("password", { required: true, maxLength: 50, minLength: 8, pattern: /^(?=.*[a-z])(?=.*[A-Z])(?=.*[0-9])(?=.*[!@#\$%\^&\*])(?=.{8,})/ })}/>
            </div>
          </div>

          <div>
          {errors.username && <span><p>The Username field is required and the length should not exceed 45 characters</p></span>}
          </div>

          <div>
          {errors.email && <span><p>{errors.email.message}</p></span>}
          </div>

          <div>
          {errors.password && <span>
            <p>The Password field is required and must follow all of the fields:</p>
            <p> - Contain at least 1 lowercase alphabetical character</p>
            <p> - Contain at least 1 uppercase alphabetical character</p>
            <p> - Contain at least 1 numeric character</p>
            <p> - Contain at least one special character</p>
            <p> - Must be eight characters or longer not to exceed 50 characters</p>
            </span>
          }
          </div>

        <input type="submit" className="btn btn-success me-2" />
        <Link to="/" className="btn btn-secondary me-2">Cancel</Link>
      </form>
    </div>
  );
}

export default CreateAccountForm;