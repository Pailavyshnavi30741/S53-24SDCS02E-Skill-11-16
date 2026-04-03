import React, { useEffect, useState } from "react";

function UserList() {
  const [users, setUsers] = useState([]);
  const [loading, setLoading] = useState(true);
  const [error, setError] = useState("");

  useEffect(() => {
    fetch("https://jsonplaceholder.typicode.com/users")
      .then((res) => {
        if (!res.ok) throw new Error("API Error");
        return res.json();
      })
      .then((data) => {

        // 👇 Modify names here
        const updatedUsers = data.map((user, index) => {
          const newNames = [
            "Vyshnavi Paila",
            "Tulasi Gogisetty",
            "Sarah Sukerthana"
          ];

          return {
            ...user,
            name: newNames[index] || user.name
          };
        });

        setUsers(updatedUsers);
        setLoading(false);
      })
      .catch(() => {
        setError("Error fetching API users");
        setLoading(false);
      });
  }, []);

  if (loading) return <h3> Loading...</h3>;
  if (error) return <h3> {error}</h3>;

  return (
    <div>
      <h2> API Users (Modified)</h2>
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

export default UserList;