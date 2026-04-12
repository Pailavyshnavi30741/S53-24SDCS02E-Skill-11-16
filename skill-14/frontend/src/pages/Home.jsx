import React from 'react'
import Navbar from '../components/Navbar'
import '../styles/Home.css'

function Home() {
  const user = JSON.parse(localStorage.getItem('loggedUser'))

  return (
    <div>
      <Navbar />
      <div className="home-container">
        <div className="welcome-card">
          <div className="avatar">{user?.username?.charAt(0).toUpperCase()}</div>
          <h1>Welcome, <span>{user?.username}</span>! 👋</h1>
          <p className="welcome-sub">You are successfully logged in.</p>
          <div className="info-grid">
            <div className="info-item">
              <span className="info-label">🆔 User ID</span>
              <span className="info-value">#{user?.id}</span>
            </div>
            <div className="info-item">
              <span className="info-label">👤 Username</span>
              <span className="info-value">{user?.username}</span>
            </div>
            <div className="info-item">
              <span className="info-label">📧 Email</span>
              <span className="info-value">{user?.email}</span>
            </div>
          </div>
        </div>
      </div>
    </div>
  )
}

export default Home
