import {useState, useEffect} from "react";

import Sidebar from "./components/Sidebar";
import Navbar from "./components/Navbar";

import Dashboard from "./pages/Dashboard";
import Employees from "./pages/Employees";
import Departments from "./pages/Departments";
import Login from "./pages/Login";

import {getRoleFromToken} from "./services/authService";

import "./App.css";

function getSavedToken() {
    const savedToken = sessionStorage.getItem("token");

    if (!savedToken) {
        return null;
    }

    try {
        const payload = JSON.parse(atob(savedToken.split(".")[1]
            .replace(/-/g, "+")
            .replace(/_/g, "/")
            .padEnd(Math.ceil(savedToken.split(".")[1].length / 4) * 4, "=")));

        if (!payload.exp || payload.exp * 1000 <= Date.now()) {
            sessionStorage.removeItem("token");
            return null;
        }

        return savedToken;

    } catch {
        sessionStorage.removeItem("token");
        return null;
    }
}

function App() {

    const [token, setToken] = useState(getSavedToken);
    const [activePage, setActivePage] = useState("Dashboard");

    function handleLogin(newToken) {
        sessionStorage.setItem("token", newToken);
        setToken(newToken);
        setActivePage("Dashboard");
    }

    function handleLogout() {
        sessionStorage.removeItem("token");
        setToken(null);
        setActivePage("Dashboard");
    }

    const role = token ? getRoleFromToken(token) : null;

    const validRole = role === "ADMIN" || role === "USER";

    // Clear invalid sessions outside the render phase.
    useEffect(() => {
        if (token && !validRole) {
            handleLogout();
        }
    }, [token, validRole]);

    if (!token || !validRole) {
        return <Login onLogin={handleLogin}/>;
    }

    return (<div className="app-layout">

            <Sidebar
                activePage={activePage}
                onNavigate={setActivePage}
            />

            <main className="main-content">

                <Navbar
                    title={activePage}
                    role={role}
                    onLogout={handleLogout}
                />

                {activePage !== "Dashboard" && (<button
                        className="back-button"
                        onClick={() => setActivePage("Dashboard")}
                    >
                        ← Back to Dashboard
                    </button>)}

                {activePage === "Dashboard" && (<Dashboard
                        token={token}
                        role={role}
                        onNavigate={setActivePage}
                    />)}

                {activePage === "Employees" && (<Employees
                        token={token}
                        role={role}
                    />)}

                {activePage === "Departments" && (<Departments
                        token={token}
                        role={role}
                    />)}

            </main>
        </div>);
}

export default App;
