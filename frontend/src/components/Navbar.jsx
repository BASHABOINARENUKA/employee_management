
function Navbar({ title, role, onLogout }) {
    return (
        <header className="navbar">
            <div>
                <h2>{title}</h2>
                <p>Employee Management System</p>
            </div>

            <div className="profile">
                <div className="avatar">
                    {role === "ADMIN" ? "A" : "U"}
                </div>

                <div className="profile-details">
                    <strong>{role === "ADMIN" ? "Admin" : "User"}</strong>
                    <span>{role}</span>
                </div>

                <button
                    className="logout-button"
                    onClick={onLogout}
                >
                    Logout
                </button>
            </div>
        </header>
    );
}

export default Navbar;
