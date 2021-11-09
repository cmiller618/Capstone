import React, { Component, useContext, useEffect } from "react";
import { w3cwebsocket as W3CWebSocket } from "websocket";
import AuthContext from "../context/AuthContext";


const client = new W3CWebSocket("ws://127.0.0.1:8000");
const USER_KEY = "user-key"


function WebsocketTest() {

    const auth = useContext(AuthContext);


    useEffect(() => {


        client.onopen = () => {
            console.log("Web socket client connected.");
        }
        client.onmessage = (message) => {
            const dataFromServer = JSON.parse(message.data);
            console.log('got reply! ', dataFromServer);
            if (dataFromServer.type === "message") {
                // this.setState((state) =>
                // ({
                //     messages: [...state.messages,
                //     {
                //         message: dataFromServer.message,
                //         user: dataFromServer.user
                //     }]
                // })
                // );
            }
        };

    //     if (appUser) {
    //         login(appUser);
    //     }

    //     setInitialized(true);
    }, []);


    const state = {
        username: "",
        gameId: "",
        pieceColor: "",
        isTurn: false,
        messages: []

    }

    const onButtonClicked = () => {
        client.send(JSON.stringify({
            type: "message",
            message: "hello",
            user: auth.user.username
        }));
    }

    //send player gameid and start/end position
    //validate gameid, and turn before sending the json object 

    return (
        <div>
            <button onClick={onButtonClicked}>Send Message</button>
        </div>

    )
}

export default WebsocketTest;




