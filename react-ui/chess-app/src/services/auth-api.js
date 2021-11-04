const baseUrl = "http://localhost:8080";
export const CREDENTIALS_KEY = "chess-credentials";

function decode(jwt) {
    const tokens = jwt.split(".");
    const decoded = atob(tokens[1]);
    return JSON.parse(decoded);
}

export async function login(credentials) {
    const init = {
        method: "POST",
        headers: {
            "Content-Type": "application/json",
            "Accept": "application/json"
        },
        body: JSON.stringify(credentials)
    }

    const response = await fetch(`${baseUrl}/authenticate`, init);
    if(response.status === 200) {

        const body = await response.json();
        let auth = decode(body.jwt_token);
        auth.jwt = body.jwt_token;

        localStorage.setItem(CREDENTIALS_KEY, JSON.stringify(auth));
        return Promise.resolve(auth);



    }
    return Promise.reject("bad credentials");
}