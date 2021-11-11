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

export async function findMatchesByProfileId(profileId, auth){

  const init = {
    headers: {
      'Authorization' : `Bearer ${auth.user.token}`
    }
  }

  const response = await fetch(`${APIURL}/${profileId}`, init);

  if(response.status !== 200){
    return Promise.reject("response is not 200 OK")
  }

  return response.json();
}

export async function addMatch(match, auth){
  const init = {
    method: "POST",
    headers: {
      "Content-type": "application/json",
      'Authorization': `Bearer ${auth.user.token}`
    },
    body: JSON.stringify(match),
  };

  const response = await fetch(APIURL, init);

  if(response.status !== 201){
    return Promise.reject("response not 201 CREATED");
  }

  

  if(response.json() && response.json().matchId !== null){
    return response.json();
  }
}

export async function updateMatch(match, auth){
  const init = {
    method: "PUT",
    headers: {
      "Content-Type": "application/json",
      'Authorization': `Bearer ${auth.user.token}`
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