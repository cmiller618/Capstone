const APIURL = "http://localhost:8080/game/players";

export async function findAll(){
  const response = await fetch(APIURL);

  if(response.status !== 200){
    return Promise.reject("response is not 200 OK");
  }

  return response.json();
}

export async function findPlayerByProfileId(profileId){
  const response = await fetch(`${APIURL}/${profileId}`);

  if(response.status !== 200){
    return Promise.reject("response is not 200 OK")
  }

  return response.json();
}

export async function addPlayer(player){
  const init = {
    method: "POST",
    headers: {
      "Content-type": "application/json",
    },
    body: JSON.stringify(player),
  };

  const response = await fetch(APIURL, init);

  if(response.status !== 201){
    return Promise.reject("response not 201 CREATED");
  }
}

export async function updatePlayer(player){
  const init = {
    method: "PUT",
    headers: {
      "Content-Type": "application/json",
    },
    body: JSON.stringify(player),
  };

  const response = await fetch(`${APIURL}/${player.profileId}`, init);

  if(response.status !== 204) {
    return Promise.reject("response not 204 NO CONTENT");
  }
}

export async function save(player){
  if(player.profileId > 0){
    await updatePlayer(player);
  }else{
    await addPlayer(player);
  }
  
  return findAll();
}

export async function deletePlayer(profileId){
  const init = {
    method: "DELETE"
  };

  const response = await fetch(`${APIURL}/${profileId}`, init);

  if(response.status !== 204){
    return Promise.reject("response not 204 NO CONTENT");
  }

  return findAll();
}