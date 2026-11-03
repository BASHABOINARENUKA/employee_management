function Sidebar({activePage, onNavigate}) {
    const menuItems = ["Dashboard", "Employees", "Departments"];
    return (<aside className="sidebar">
            <h2 className="brand">EmployeeHub</h2>
            <p className="menu-label">MAIN MENU</p>
            <nav>
                {menuItems.map((item) => (<button
                        key={item}
                        className={`nav-item ${activePage === item ? "active" : ""}`}
                        onClick={() => onNavigate(item)}
                    >
                        {item}
                    </button>))}
            </nav>
            <div className="sidebar-footer">
                Employee Management System
            </div>
        </aside>);
}

export default Sidebar;
