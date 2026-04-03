import React, { useEffect, useState } from "react";
import axios from "axios";

function FakePostList() {
  const [posts, setPosts] = useState([]);
  const [filter, setFilter] = useState("");

  const fetchPosts = () => {
    axios
      .get("https://dummyjson.com/posts")
      .then((res) => setPosts(res.data.posts))
      .catch(() => alert("Error fetching posts"));
  };

  useEffect(() => {
    fetchPosts();
  }, []);

  const filteredPosts = filter
    ? posts.filter((post) => post.userId === Number(filter))
    : posts;

  return (
    <div>
      <h2> Fake API Posts</h2>

      <button onClick={fetchPosts}> Refresh</button>

      <br /><br />

      <label>Filter by User: </label>
      <select onChange={(e) => setFilter(e.target.value)}>
        <option value="">All</option>
        <option value="1">User 1</option>
        <option value="2">User 2</option>
        <option value="3">User 3</option>
      </select>

      <br /><br />

      {filteredPosts.map((post) => (
        <div key={post.id} style={{ borderBottom: "1px solid #ccc" }}>
          <h4>{post.title}</h4>
          <p>{post.body}</p>
          <small>User ID: {post.userId}</small>
        </div>
      ))}
    </div>
  );
}

export default FakePostList;