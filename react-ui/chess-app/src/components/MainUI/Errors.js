function Errors({ errors }) {
  if (errors.length === 0) {
    return null;
  }

  return (
    <div className="alert alert-danger mt-3">
      <ul>
        {errors.map(error => (
          <li key={error}>{error}</li>
        ))}
      </ul>
    </div>
  );
}

export default Errors;