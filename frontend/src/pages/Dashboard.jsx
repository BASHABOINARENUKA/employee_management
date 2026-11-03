import {useEffect, useState} from "react";

import SummaryCard from "../components/SummaryCard";

import {getEmployees} from "../services/employeeService";
import {getDepartments} from "../services/departmentService";

function Dashboard({token, role, onNavigate}) {

    const isAdmin = role === "ADMIN";

    const [employeeCount, setEmployeeCount] = useState(0);
    const [departmentCount, setDepartmentCount] = useState(0);

    const [loading, setLoading] = useState(true);
    const [error, setError] = useState("");

    // Fetch dashboard data from Spring Boot
    useEffect(() => {

        async function fetchDashboardData() {

            try {
                setLoading(true);
                setError("");

                const [employees, departments] = await Promise.all([getEmployees(token), getDepartments(token)]);

                setEmployeeCount(Array.isArray(employees) ? employees.length : 0);

                setDepartmentCount(Array.isArray(departments) ? departments.length : 0);

            } catch (err) {
                setError(err.message || "Unable to load dashboard information.");
            } finally {
                setLoading(false);
            }
        }

        if (token) {
            fetchDashboardData();
        }

    }, [token]);

    return (<div className="dashboard">

            {/* Dashboard Heading */}
            <div className="page-heading">
                <div>
                    <h1>
                        {isAdmin ? "Admin Dashboard" : "User Dashboard"}
                    </h1>

                    <p>
                        {isAdmin ? "Manage employees and departments." : "View employee and department information."}
                    </p>
                </div>
            </div>

            {/* Error Message */}
            {error && (<div className="error-message">
                    {error}
                </div>)}

            {/* Summary Cards */}
            <div className="summary-grid">

                <SummaryCard
                    title="Total Employees"
                    value={employeeCount}
                    description="Employees registered in the system"
                    loading={loading}
                />

                <SummaryCard
                    title="Total Departments"
                    value={departmentCount}
                    description="Departments registered in the system"
                    loading={loading}
                />

                <SummaryCard
                    title="Your Access"
                    value={isAdmin ? "Full access" : "Read only"}
                    description={isAdmin ? "Create, update and delete records" : "View records without modifying them"}
                />

            </div>

            {/* Quick Actions */}
            <div className="quick-actions">

                <h2>Quick Actions</h2>

                <div className="quick-action-buttons">

                    <button
                        className="action-button"
                        onClick={() => onNavigate("Employees")}
                    >
                        View Employees
                    </button>

                    <button
                        className="action-button"
                        onClick={() => onNavigate("Departments")}
                    >
                        View Departments
                    </button>

                </div>

                <p className="quick-action-description">
                    {isAdmin ? "You can create, update and delete records from the management pages." : "You have permission to view employee and department records."}
                </p>

            </div>

        </div>);
}

export default Dashboard;
