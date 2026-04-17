import React, { useState } from 'react'
import { useNavigate, Link } from 'react-router-dom'
import { loginUser } from '../services/api'
import '../styles/Auth.css'

function Login() {
  const [form, setForm] = useState({ username: '', password: '' })
  const [error, setError] = useState('')
  const navigate = useNavigate()

  const handleChange = (e) => {
    setForm({ ...form, [e.target.name]: e.target.value })
  }

  const handleSubmit = async (e) => {
    e.preventDefault()
    setError('')
    try {
      const res = await loginUser(form)
      localStorage.setItem('loggedUser', JSON.stringify(res.data))
      navigate('/home')
    } catch (err) {
      setError(err.response?.data?.message || 'Invalid username or password.')
    }
  }

  return (
    <div className="auth-container">
      <div className="auth-card">
        <div className="auth-icon">🔐</div>
        <h2>Welcome Back</h2>
        <p className="auth-subtitle">Sign in to your account</p>
        {error && <div className="alert error">{error}</div>}
        <form onSubmit={handleSubmit}>
          <div className="form-group">
            <label>Username</label>
            <input type="text" name="username" placeholder="Enter username"
              value={form.username} onChange={handleChange} required />
          </div>
          <div className="form-group">
            <label>Password</label>
            <input type="password" name="password" placeholder="Enter password"
              value={form.password} onChange={handleChange} required />
          </div>
          <button type="submit" className="btn-primary">Login</button>
        </form>
        <p className="auth-link">Don't have an account? <Link to="/register">Register here</Link></p>
      </div>
    </div>
  )
}

export default Login
 