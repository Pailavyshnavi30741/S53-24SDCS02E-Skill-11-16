import React, { useEffect, useState } from "react";

function LocalUserList() {
  const [users, setUsers] = useState([]);
  const [loading, setLoading] = useState(true);
  const [error, setError] = useState("");

  useEffect(() => {
    fetch("/users.json")
      .then((res) => {
        if (!res.ok) throw new Error("Failed to load");
        return res.json();
      })
      .then((data) => {
        setUsers(data);
        setLoading(false);
      })
      .catch(() => {
        setError("Error fetching local users");
        setLoading(false);
      });
  }, []);

  if (loading) return <h3> Loading...</h3>;
  if (error) return <h3> {error}</h3>;

  return (
    <div>
      <h2> Local Users</h2>
      {users.map((user) => (
        <div key={user.id} style={{ borderBottom: "1px solid #ccc" }}>
          <p><b>Name:</b> {user.name}</p>
          <p><b>Email:</b> {user.email}</p>
          <p><b>Phone:</b> {user.phone}</p>
        </div>
      ))}
    </div>
  );
}

export default LocalUserList;