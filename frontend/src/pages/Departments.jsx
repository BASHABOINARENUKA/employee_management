import {useEffect, useState} from "react";

import {
    getDepartments, createDepartment, updateDepartment, deleteDepartment,
} from "../services/departmentService";

const emptyForm = {
    name: "", location: "",
};

function Departments({token, role}) {
    const isAdmin = role === "ADMIN";

    const [departments, setDepartments] = useState([]);
    const [form, setForm] = useState(emptyForm);
    const [editingId, setEditingId] = useState(null);

    const [showForm, setShowForm] = useState(false);
    const [search, setSearch] = useState("");

    const [loading, setLoading] = useState(true);
    const [saving, setSaving] = useState(false);
    const [error, setError] = useState("");
    const [success, setSuccess] = useState("");

    // Fetch all departments
    async function loadDepartments() {
        setLoading(true);
        setError("");

        try {
            const data = await getDepartments(token);

            setDepartments(Array.isArray(data) ? data : []);
        } catch (err) {
            setError(err.message || "Unable to load departments.");
        } finally {
            setLoading(false);
        }
    }

    useEffect(() => {
        loadDepartments();
    }, [token]);

    // Handle input changes
    function handleChange(event) {
        const {name, value} = event.target;

        setForm((previous) => ({
            ...previous, [name]: value,
        }));
    }

    // Open Add Department form
    function handleAdd() {
        setForm(emptyForm);
        setEditingId(null);
        setShowForm(true);
        setError("");
        setSuccess("");
    }

    // Open Edit Department form
    function handleEdit(department) {
        setForm({
            name: department.name ?? "", location: department.location ?? "",
        });

        setEditingId(department.id);
        setShowForm(true);
        setError("");
        setSuccess("");
    }

    // Create or update department
    async function handleSubmit(event) {
        event.preventDefault();

        if (!isAdmin) return;

        setSaving(true);
        setError("");
        setSuccess("");

        const department = {
            name: form.name.trim(), location: form.location.trim(),
        };
        try {
            if (editingId !== null) {
                const updatedDepartment = await updateDepartment(
                    token,
                    editingId,
                    department
                );

                setDepartments((previousDepartments) =>
                    previousDepartments.map((item) =>
                        item.id === editingId ? updatedDepartment : item
                    )
                );

                setSuccess("Department updated successfully.");
                setShowForm(false);
                setForm(emptyForm);
                setEditingId(null);
            }
        } catch (err) {
            setError(err.message || "Unable to save department.");
        } finally {
            setSaving(false);
        }
    }

    // Delete department
    async function handleDelete(department) {
        if (!isAdmin) return;

        const confirmed = window.confirm(`Are you sure you want to delete ${department.name}?`);

        if (!confirmed) return;

        setError("");
        setSuccess("");

        try {
            await deleteDepartment(token, department.id);

            setSuccess("Department deleted successfully.");

            await loadDepartments();
        } catch (err) {
            setError(err.message || "Unable to delete department.");
        }
    }

    // Search departments
    const filteredDepartments = departments.filter((department) => {
        const searchText = `${department.name} ${department.location}`.toLowerCase();

        return searchText.includes(search.toLowerCase());
    });

    return (<div className="dashboard">

            {/* Page heading */}
            <div className="page-heading">
                <div>
                    <h1>Department Management</h1>

                    <p>
                        {isAdmin ? "Create, update and manage departments." : "View department information."}
                    </p>
                </div>

                {isAdmin && (<button
                        className="primary-button"
                        onClick={handleAdd}
                    >
                        + Add Department
                    </button>)}
            </div>

            {/* Search */}
            <div className="employee-toolbar">
                <input
                    className="search-input"
                    type="search"
                    placeholder="Search departments..."
                    value={search}
                    onChange={(event) => setSearch(event.target.value)}
                />

                <span>
          {filteredDepartments.length} departments
        </span>
            </div>

            {/* Messages */}
            {error && (<p className="error-message">{error}</p>)}

            {success && (<p className="success-message">{success}</p>)}

            {/* Add/Edit form */}
            {showForm && isAdmin && (<form
                    className="employee-form"
                    onSubmit={handleSubmit}
                >
                    <h2>
                        {editingId !== null ? "Edit Department" : "Add Department"}
                    </h2>

                    <div className="form-grid">

                        <label>
                            Department Name

                            <input
                                type="text"
                                name="name"
                                value={form.name}
                                onChange={handleChange}
                                placeholder="Enter department name"
                                required
                            />
                        </label>

                        <label>
                            Location

                            <input
                                type="text"
                                name="location"
                                value={form.location}
                                onChange={handleChange}
                                placeholder="Enter department location"
                                required
                            />
                        </label>

                    </div>

                    <div className="form-actions">
                        <button
                            type="submit"
                            className="primary-button"
                            disabled={saving}
                        >
                            {saving ? "Saving..." : editingId !== null ? "Save Changes" : "Create Department"}
                        </button>

                        <button
                            type="button"
                            className="action-button"
                            onClick={() => {
                                setShowForm(false);
                                setForm(emptyForm);
                                setEditingId(null);
                            }}
                        >
                            Cancel
                        </button>
                    </div>
                </form>)}

            {/* Department table */}
            <section className="table-section">
                <h2>All Departments</h2>

                {loading ? (<p>Loading departments...</p>) : filteredDepartments.length === 0 ? (
                    <p>No departments found.</p>) : (<div className="table-wrapper">

                        <table>
                            <thead>
                            <tr>
                                <th>ID</th>
                                <th>Department Name</th>
                                <th>Location</th>

                                {isAdmin && <th>Actions</th>}
                            </tr>
                            </thead>

                            <tbody>
                            {filteredDepartments.map((department) => (<tr key={department.id}>
                                    <td>{department.id}</td>

                                    <td>
                                        <strong>{department.name}</strong>
                                    </td>

                                    <td>{department.location}</td>

                                    {isAdmin && (<td>
                                            <div className="table-actions">

                                                <button
                                                    className="action-button"
                                                    onClick={() => handleEdit(department)}
                                                >
                                                    Edit
                                                </button>

                                                <button
                                                    className="delete-button"
                                                    onClick={() => handleDelete(department)}
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

export default Departments;
