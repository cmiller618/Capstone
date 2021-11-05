const APIURL = "http://localhost:8080/game/players/matches";

export async function findAll(){
  const response = await fetch(APIURL);

  if(response.status !== 200){
    return Promise.reject("response is not 200 OK");
  }

  return response.json();
}

export async function findTopFive(){
  const response = await fetch(`${APIURL}/ranking`);

  if(response.status !== 200){
    return Promise.reject("response is not 200 OK");
  }

  return response.json();
}

export async function findMatchesByProfileId(profileId){
  const response = await fetch(`${APIURL}/${profileId}`);

  if(response.status !== 200){
    return Promise.reject("response is not 200 OK")
  }

  return response.json();
}

export async function addMatch(match){
  const init = {
    method: "POST",
    headers: {
      "Content-type": "application/json",
    },
    body: JSON.stringify(match),
  };

  const response = await fetch(APIURL, init);

  if(response.status !== 201){
    return Promise.reject("response not 201 CREATED");
  }
}

export async function updateMatch(match){
  const init = {
    method: "PUT",
    headers: {
      "Content-Type": "application/json",
    },
    body: JSON.stringify(match),
  };

  const response = await fetch(`${APIURL}/${match.matchId}`, init);

  if(response.status !== 204) {
    return Promise.reject("response not 204 NO CONTENT");
  }
}

export async function save(match){
  if(match.matchId > 0){
    await updateMatch(match);
  }else{
    await addMatch(match);
  }
  
  return findAll();
}