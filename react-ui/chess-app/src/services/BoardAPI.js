const APIURL = "http://localhost:8080/game/board";

export async function getNewBoard(){
  const response = await fetch(APIURL);

  if(response.status !== 200){
    return Promise.reject("response is not 200 OK");
  }

  return response.json();
}

export async function getCurrentBoard(){
  const response = await fetch(`${APIURL}`/"currentboard");

  if(response.status !== 200){
    return Promise.reject("response is not 200 OK");
  }

  return response.json();

}

export async function updatePlayerMove(board){
    const init = {
        method: "PUT",
        headers: {
          "Content-Type": "application/json",
        },
        body: JSON.stringify(board),
      };
    
      const response = await fetch(`${APIURL}`, init);
    
      if(response.status !== 204) {
        return Promise.reject("response not 204 NO CONTENT");
      }

}