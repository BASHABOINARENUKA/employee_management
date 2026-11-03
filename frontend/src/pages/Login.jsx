
import { useState } from "react";
import API_BASE_URL from "../services/api";
function Login({ onLogin }) {
    const [username, setUsername] = useState("");
    const [password, setPassword] = useState("");
    const [error, setError] = useState("");
    const [loading, setLoading] = useState(false);

    async function handleSubmit(event) {
        event.preventDefault();
        setError("");
        setLoading(true);

        try {
            const params = new URLSearchParams({
                username,
                password,
            });

            const response = await fetch(
                `${API_BASE_URL}/auth/login?${params.toString()}`,
                {
                    method: "POST",
                }
            );

            if (!response.ok) {
                throw new Error("Invalid username or password");
            }

            const token = await response.text();

            // Your backend returns the JWT as a String.
            const cleanToken = token.trim().replace(/^"|"$/g, "");

            if (!cleanToken) {
                throw new Error("The server returned an empty token");
            }

            onLogin(cleanToken);
        } catch (err) {
            setError(err.message || "Login failed. Please try again.");
        } finally {
            setLoading(false);
        }
    }

    return (
        <div className="login-page">
            <form className="login-card" onSubmit={handleSubmit}>
                <h1>EmployeeHub</h1>
                <p>Sign in to your account</p>

                <label htmlFor="username">Username</label>
                <input
                    id="username"
                    type="text"
                    value={username}
                    onChange={(event) => setUsername(event.target.value)}
                    placeholder="Enter your username"
                    autoComplete="username"
                    required
                />

                <label htmlFor="password">Password</label>
                <input
                    id="password"
                    type="password"
                    value={password}
                    onChange={(event) => setPassword(event.target.value)}
                    placeholder="Enter your password"
                    autoComplete="current-password"
                    required
                />

                {error && <p className="login-error">{error}</p>}

                <button
                    className="primary-button"
                    type="submit"
                    disabled={loading}
                >
                    {loading ? "Signing in..." : "Login"}
                </button>
            </form>
        </div>
    );
}

export default Login;
