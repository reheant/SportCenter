export function getUserId() {
    return localStorage.getItem("user_id");
}

export function getUserRole() {
    return localStorage.getItem("user_role");
}

export function getUser() {
    return {
        id: getUserId(),
        role: getUserRole(),
    }
}

export function logout() {
    localStorage.removeItem("user_id");
    localStorage.removeItem("user_role");
}