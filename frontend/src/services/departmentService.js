import API_BASE_URL from "./api";

async function request(path, token, options = {}) {
    const response = await fetch(`${API_BASE_URL}${path}`, {
        ...options, headers: {
            Authorization: `Bearer ${token}`, ...(options.body ? {"Content-Type": "application/json"} : {}), ...options.headers,
        },
    });

    const text = await response.text();

    if (!response.ok) {
        throw new Error(text || `Request failed: ${response.status}`);
    }

    if (!text) return null;

    try {
        return JSON.parse(text);
    } catch {
        return text;
    }
}

export function getDepartments(token) {
    return request("/departments", token);
}

export function createDepartment(token, department) {
    return request("/departments", token, {
        method: "POST", body: JSON.stringify(department),
    });
}

export function updateDepartment(token, id, department) {
    return request(`/departments/${id}`, token, {
        method: "PUT", body: JSON.stringify(department),
    });
}

export function deleteDepartment(token, id) {
    return request(`/departments/${id}`, token, {
        method: "DELETE",
    });
}
