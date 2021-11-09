import React, { Component } from "react";
import { w3cwebsocket as W3CWebSocket} from "websocket"

const client = new W3CWebSocket ("ws://127.0.0.1:8000");

export default class WebSocketTest extends React.Component {

    constructor(props) {
        super(props);
        this.textArea = React.createRef();
    }

     state = {
        username: "",
        gameId: "",
        pieceColor: "",
        isTurn: false

    }

      onButtonClicked = (value) => {
        client.send(JSON.stringify({
            type: "message",
            message: value
        }));
    }
    

      componentDidMount() {
        client.onopen = () => {
            console.log("Web socket client connected.")

        }
        client.onmessage = (message) => {
            const dataFromServer = JSON.parse(message.data)
            console.log('got reply! ', dataFromServer);
        }
    }

    render () {

    return (
        <div>
            <button onClick={() =>
            this.onButtonClicked("hello")}>Send Message</button>
        </div>
    )

    }

} 





