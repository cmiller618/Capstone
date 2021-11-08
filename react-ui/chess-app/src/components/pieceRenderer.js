import './Board.css'

import { pieces } from '../images/pieceImages'

function pieceRenderer(props){

    const {piece} = props;

    return(
        piece ? 
        <img className="pieces" src={pieces[piece]} alt="piece"/>
        :<div></div>
    );
}

export default pieceRenderer;