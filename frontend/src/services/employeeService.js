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

    if (!text) {
        return null;
    }

    // Parse JSON only when the response is JSON.
    try {
        return JSON.parse(text);
    } catch {
        // Plain-text response, for example:
        // "Employee deleted successfully"
        return text;
    }
}

export function getEmployees(token) {
    return request("/employees", token);
}

export function createEmployee(token, employee) {
    return request("/employees", token, {
        method: "POST", body: JSON.stringify(employee),
    });
}

export function updateEmployee(token, id, employee) {
    return request(`/employees/${id}`, token, {
        method: "PUT", body: JSON.stringify(employee),
    });
}

export function deleteEmployee(token, id) {
    return request(`/employees/${id}`, token, {
        method: "DELETE",
    });
}
