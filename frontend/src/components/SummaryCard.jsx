
function SummaryCard({ title, value, description, loading = false }) {
    return (
        <div className="summary-card">
            <p className="card-title">{title}</p>

            <h2>
                {loading ? "Loading..." : value}
            </h2>

            <p className="card-description">
                {description}
            </p>
        </div>
    );
}

export default SummaryCard;
