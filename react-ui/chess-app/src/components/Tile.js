import pieceRenderer from "./pieceRenderer";
import './Board.css';


function Tile({number, board, image}){

    if (number % 2 === 0) {
        return (
          <div className="dark-square">
            {<pieceRenderer className="chess-pieces"></pieceRenderer>}
          </div>
        );
      } else {
        return (
          <div className="light-square">
            {<pieceRenderer className="chess-pieces"></pieceRenderer>}
          </div>
        );
      }

}

export default Tile;