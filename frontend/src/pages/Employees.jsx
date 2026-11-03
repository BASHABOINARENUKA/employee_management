import {useEffect, useState} from "react";
import {
    getEmployees, createEmployee, updateEmployee, deleteEmployee,
} from "../services/employeeService";

const emptyForm = {
    firstName: "", lastName: "", email: "", phoneNumber: "", salary: "", designation: "", departmentId: "",
};

function Employees({token, role}) {
    const isAdmin = role === "ADMIN";

    const [employees, setEmployees] = useState([]);
    const [search, setSearch] = useState("");
    const [form, setForm] = useState(emptyForm);
    const [editingId, setEditingId] = useState(null);
    const [showForm, setShowForm] = useState(false);
    const [loading, setLoading] = useState(true);
    const [error, setError] = useState("");
    const [saving, setSaving] = useState(false);

    async function loadEmployees() {
        setLoading(true);
        setError("");

        try {
            const data = await getEmployees(token);
            setEmployees(Array.isArray(data) ? data : []);
        } catch (err) {
            setError(err.message || "Unable to load employees.");
        } finally {
            setLoading(false);
        }
    }

    useEffect(() => {
        loadEmployees();
    }, [token]);

    function updateField(event) {
        const {name, value} = event.target;

        setForm((previous) => ({
            ...previous, [name]: value,
        }));
    }

    function startAdd() {
        setEditingId(null);
        setForm(emptyForm);
        setShowForm(true);
        setError("");
    }

    function startEdit(employee) {
        setEditingId(employee.id);

        setForm({
            firstName: employee.firstName ?? "",
            lastName: employee.lastName ?? "",
            email: employee.email ?? "",
            phoneNumber: employee.phoneNumber ?? "",
            salary: employee.salary ?? "",
            designation: employee.designation ?? "",
            departmentId: employee.departmentId ?? "",
        });

        setShowForm(true);
        setError("");
    }

    async function handleSubmit(event) {
        event.preventDefault();

        if (!isAdmin) return;

        setSaving(true);
        setError("");

        const employee = {
            firstName: form.firstName,
            lastName: form.lastName,
            email: form.email,
            phoneNumber: form.phoneNumber,
            salary: Number(form.salary),
            designation: form.designation,
            departmentId: Number(form.departmentId)
        };

        try {
            if (editingId !== null) {
                await updateEmployee(token, editingId, employee);
            } else {
                await createEmployee(token, employee);
            }

            setShowForm(false);
            setForm(emptyForm);
            setEditingId(null);
            await loadEmployees();
        } catch (err) {
            setError(err.message || "Unable to save employee.");
        } finally {
            setSaving(false);
        }
    }

    async function handleDelete(employee) {
        if (!isAdmin) return;

        const confirmed = window.confirm(`Delete ${employee.firstName} ${employee.lastName}?`);

        if (!confirmed) return;

        setError("");

        try {
            await deleteEmployee(token, employee.id);
            await loadEmployees();
        } catch (err) {
            setError(err.message || "Unable to delete employee.");
        }
    }

    const filteredEmployees = employees.filter((employee) => {
        const text = [employee.firstName, employee.lastName, employee.email, employee.designation, employee.department?.name,]
            .filter(Boolean)
            .join(" ")
            .toLowerCase();

        return text.includes(search.toLowerCase());
    });

    return (<div className="dashboard">
        <div className="page-heading">
            <div>
                <h1>Employee Management</h1>
                <p>
                    {isAdmin ? "Create, update and manage employee records." : "View employee records."}
                </p>
            </div>

            {isAdmin && (<button className="primary-button" onClick={startAdd}>
                + Add Employee
            </button>)}
        </div>

        <div className="employee-toolbar">
            <input
                className="search-input"
                type="search"
                placeholder="Search employees..."
                value={search}
                onChange={(event) => setSearch(event.target.value)}
            />

            <span>{filteredEmployees.length} employees</span>
        </div>

        {error && <p className="error-message">{error}</p>}

        {showForm && isAdmin && (<form className="employee-form" onSubmit={handleSubmit}>
            <h2>{editingId !== null ? "Edit Employee" : "Add Employee"}</h2>

            <div className="form-grid">
                <label>
                    First name
                    <input
                        name="firstName"
                        value={form.firstName}
                        onChange={updateField}
                        required
                    />
                </label>

                <label>
                    Last name
                    <input
                        name="lastName"
                        value={form.lastName}
                        onChange={updateField}
                        required
                    />
                </label>

                <label>
                    Email
                    <input
                        name="email"
                        type="email"
                        value={form.email}
                        onChange={updateField}
                        required
                    />
                </label>

                <label>
                    Phone number
                    <input
                        name="phoneNumber"
                        value={form.phoneNumber}
                        onChange={updateField}
                        required
                    />
                </label>

                <label>
                    Salary
                    <input
                        name="salary"
                        type="number"
                        min="0"
                        value={form.salary}
                        onChange={updateField}
                        required
                    />
                </label>

                <label>
                    Designation
                    <input
                        name="designation"
                        value={form.designation}
                        onChange={updateField}
                        required
                    />
                </label>

                <label>
                    Department ID
                    <input
                        name="departmentId"
                        type="number"
                        min="1"
                        value={form.departmentId}
                        onChange={updateField}
                        placeholder="Enter department ID"
                        required
                    />
                </label>
            </div>

            <div className="form-actions">
                <button
                    className="primary-button"
                    type="submit"
                    disabled={saving}
                >
                    {saving ? "Saving..." : editingId !== null ? "Save Changes" : "Create Employee"}
                </button>

                <button
                    type="button"
                    className="action-button"
                    onClick={() => setShowForm(false)}
                >
                    Cancel
                </button>
            </div>
        </form>)}

        <section className="table-section">
            <h2>All Employees</h2>

            {loading ? (<p>Loading employees...</p>) : filteredEmployees.length === 0 ? (<p>No employees found.</p>) : (
                <div className="table-wrapper">
                    <table>
                        <thead>
                        <tr>
                            <th>ID</th>
                            <th>Employee</th>
                            <th>Email</th>
                            <th>Department</th>
                            <th>Designation</th>
                            <th>Salary</th>
                            {isAdmin && <th>Actions</th>}
                        </tr>
                        </thead>

                        <tbody>
                        {filteredEmployees.map((employee) => (<tr key={employee.id}>
                            <td>{employee.id}</td>
                            <td>
                                {employee.firstName} {employee.lastName}
                            </td>
                            <td>{employee.email}</td>
                            <td>{employee.departmentName ?? "—"}</td>
                            <td>{employee.designation}</td>
                            <td>
                                {employee.salary == null ? "—" : Number(employee.salary).toLocaleString("en-IN", {
                                    style: "currency", currency: "INR", maximumFractionDigits: 2,
                                })}
                            </td>

                            {isAdmin && (<td>
                                <div className="table-actions">
                                    <button
                                        className="action-button"
                                        onClick={() => startEdit(employee)}
                                    >
                                        Edit
                                    </button>

                                    <button
                                        className="delete-button"
                                        onClick={() => handleDelete(employee)}
                                    >
                                        Delete
                                    </button>
                                </div>
                            </td>)}
                        </tr>))}
                        </tbody>
                    </table>
                </div>)}
        </section>
    </div>);
}

export default Employees;
