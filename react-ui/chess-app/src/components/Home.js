

function Home(){
  return(
    <>
    <div className="container">
      <h2>Welcome To Chess Online</h2>
      <p>To continue please login or create an account</p>
      <h4>Global Player Ranking</h4>
      <ul className="list-group list-group-horizontal">
        <li className="list-group-item flex-fill">player 1</li>
        <li className="list-group-item flex-fill">wins</li>
        <li className="list-group-item flex-fill">loses</li>
      </ul>
      <ul className="list-group list-group-horizontal">
        <li className="list-group-item flex-fill">player 2</li>
        <li className="list-group-item flex-fill">wins</li>
        <li className="list-group-item flex-fill">loses</li>
      </ul>
      <ul className="list-group list-group-horizontal">
        <li className="list-group-item flex-fill">player 3</li>
        <li className="list-group-item flex-fill">wins</li>
        <li className="list-group-item flex-fill">loses</li>
      </ul>
      <ul className="list-group list-group-horizontal">
        <li className="list-group-item flex-fill">player 4</li>
        <li className="list-group-item flex-fill">wins</li>
        <li className="list-group-item flex-fill">loses</li>
      </ul>
      <ul className="list-group list-group-horizontal">
        <li className="list-group-item flex-fill">player 5</li>
        <li className="list-group-item flex-fill">wins</li>
        <li className="list-group-item flex-fill">loses</li>
      </ul>
    </div>
    </>
  );
}

export default Home;