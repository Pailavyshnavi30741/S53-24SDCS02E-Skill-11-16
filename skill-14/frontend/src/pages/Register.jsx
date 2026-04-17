import React, { useState } from 'react'
import { useNavigate, Link } from 'react-router-dom'
import { registerUser } from '../services/api'
import '../styles/Auth.css'

function Register() {
  const [form, setForm] = useState({ username: '', email: '', password: '' })
  const [message, setMessage] = useState('')
  const [error, setError] = useState('')
  const navigate = useNavigate()

  const handleChange = (e) => {
    setForm({ ...form, [e.target.name]: e.target.value })
  }

  const handleSubmit = async (e) => {
    e.preventDefault()
    setError(''); setMessage('')
    try {
      await registerUser(form)
      setMessage('✅ Registration successful! Redirecting...')
      setTimeout(() => navigate('/login'), 1500)
    } catch (err) {
      setError(err.response?.data?.message || 'Registration failed. Please try again.')
    }
  }

  return (
    <div className="auth-container">
      <div className="auth-card">
        <div className="auth-icon">👤</div>
        <h2>Create Account</h2>
        <p className="auth-subtitle">Register to get started</p>
        {message && <div className="alert success">{message}</div>}
        {error && <div className="alert error">{error}</div>}
        <form onSubmit={handleSubmit}>
          <div className="form-group">
            <label>Username</label>
            <input type="text" name="username" placeholder="Enter username"
              value={form.username} onChange={handleChange} required />
          </div>
          <div className="form-group">
            <label>Email</label>
            <input type="email" name="email" placeholder="Enter email"
              value={form.email} onChange={handleChange} required />
          </div>
          <div className="form-group">
            <label>Password</label>
            <input type="password" name="password" placeholder="Enter password"
              value={form.password} onChange={handleChange} required />
          </div>
          <button type="submit" className="btn-primary">Register</button>
        </form>
        <p className="auth-link">Already have an account? <Link to="/login">Login here</Link></p>
      </div>
    </div>
  )
}

export default Register
