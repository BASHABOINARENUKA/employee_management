export function getRoleFromToken(token) {
    try {
        const payloadPart = token.split(".")[1];

        if (!payloadPart) {
            return null;
        }

        const base64 = payloadPart
            .replace(/-/g, "+")
            .replace(/_/g, "/");

        const padded = base64.padEnd(Math.ceil(base64.length / 4) * 4, "=");

        const payload = JSON.parse(atob(padded));

        return payload.role || null;
    } catch {
        return null;
    }
}