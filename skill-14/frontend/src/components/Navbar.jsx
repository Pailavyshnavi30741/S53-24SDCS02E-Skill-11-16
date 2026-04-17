import React from 'react'
import { Link, useNavigate } from 'react-router-dom'
import '../styles/Navbar.css'

function Navbar() {
  const navigate = useNavigate()

  const handleLogout = () => {
    localStorage.removeItem('loggedUser')
    navigate('/login')
  }

  return (
    <nav className="navbar">
      <div className="nav-brand">🔐 AuthApp</div>
      <div className="nav-links">
        <Link to="/home">Home</Link>
        <Link to="/profile">Profile</Link>
        <button onClick={handleLogout} className="logout-btn">Logout</button>
      </div>
    </nav>
  )
}

export default Navbar
