import React, { useState } from "react";
import LocalUserList from "./LocalUserList";
import UserList from "./UserList";
import FakePostList from "./FakePostList";

function Dashboard() {
  const [view, setView] = useState("");

  return (
    <div>
      <h1> React API Integration Dashboard</h1>

      <button onClick={() => setView("local")}>Local Users</button>
      <button onClick={() => setView("api")}>Users API</button>
      <button onClick={() => setView("fake")}>Fake API Posts</button>

      <hr />

      {view === "local" && <LocalUserList />}
      {view === "api" && <UserList />}
      {view === "fake" && <FakePostList />}
    </div>
  );
}

export default Dashboard;