import React, { useState, useEffect } from 'react'
import Navbar from '../components/Navbar'
import { getUserById } from '../services/api'
import '../styles/Profile.css'

function Profile() {
  const [profile, setProfile] = useState(null)
  const [error, setError] = useState('')
  const [loading, setLoading] = useState(true)

  useEffect(() => {
    const stored = JSON.parse(localStorage.getItem('loggedUser'))
    if (stored?.id) {
      getUserById(stored.id)
        .then(res => { setProfile(res.data); setLoading(false) })
        .catch(() => { setError('Failed to fetch profile.'); setLoading(false) })
    }
  }, [])

  return (
    <div>
      <Navbar />
      <div className="profile-container">
        {loading && !error && <div className="loading-spinner">Loading profile...</div>}
        {error && <div className="alert error">{error}</div>}
        {profile && (
          <div className="profile-card">
            <div className="profile-avatar">{profile.username?.charAt(0).toUpperCase()}</div>
            <h2>{profile.username}</h2>
            <p className="profile-email">{profile.email}</p>
            <div className="profile-details">
              <div className="detail-row">
                <span className="detail-label">🆔 User ID</span>
                <span className="detail-value">{profile.id}</span>
              </div>
              <div className="detail-row">
                <span className="detail-label">👤 Username</span>
                <span className="detail-value">{profile.username}</span>
              </div>
              <div className="detail-row">
                <span className="detail-label">📧 Email</span>
                <span className="detail-value">{profile.email}</span>
              </div>
            </div>
          </div>
        )}
      </div>
    </div>
  )
}

export default Profile
