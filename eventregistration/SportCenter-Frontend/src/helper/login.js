export function getUserId() {
    return localStorage.getItem("user_id");
}

export function getUserRole() {
    return localStorage.getItem("user_role");
}

export function getUserEmail() {
    localStorage.getItem("account_email");
}

export function getUser() {
    return {
        id: getUserId(),
        email: getUserEmail(),
        role: getUserRole(),
    }
}

export function logout() {
    localStorage.removeItem("user_id");
    localStorage.removeItem("user_role");
    localStorage.removeItem("account_email");
}