import React, { useState } from 'react'
import axios from 'axios'

const API_URL = 'http://localhost:8080/students'

function AddStudent({ onStudentAdded }) {
  const [formData, setFormData] = useState({ name: '', email: '', course: '' })
  const [message, setMessage] = useState('')

  const handleChange = (e) => {
    setFormData({ ...formData, [e.target.name]: e.target.value })
  }

  const handleSubmit = async (e) => {
    e.preventDefault()
    if (!formData.name || !formData.email || !formData.course) {
      setMessage('All fields are required!')
      return
    }
    try {
      await axios.post(API_URL, formData)
      setMessage('Student added successfully!')
      setFormData({ name: '', email: '', course: '' })
      onStudentAdded()
    } catch (error) {
      setMessage('Error adding student. Please try again.')
    }
  }

  const inputStyle = {
    padding: '8px 12px', marginRight: '10px', border: '1px solid #ccc',
    borderRadius: '4px', fontSize: '14px', width: '180px'
  }

  return (
    <div style={{ background: '#f9f9f9', padding: '20px', borderRadius: '8px', marginBottom: '30px', border: '1px solid #ddd' }}>
      <h2 style={{ marginTop: 0, color: '#2c3e50' }}>Add New Student</h2>
      <form onSubmit={handleSubmit}>
        <input style={inputStyle} type="text" name="name" placeholder="Student Name" value={formData.name} onChange={handleChange} />
        <input style={inputStyle} type="email" name="email" placeholder="Email Address" value={formData.email} onChange={handleChange} />
        <input style={inputStyle} type="text" name="course" placeholder="Course" value={formData.course} onChange={handleChange} />
        <button type="submit" style={{ padding: '8px 20px', backgroundColor: '#27ae60', color: 'white', border: 'none', borderRadius: '4px', cursor: 'pointer', fontSize: '14px' }}>
          Add Student
        </button>
      </form>
      {message && (
        <p style={{ marginTop: '10px', color: message.includes('Error') ? 'red' : 'green' }}>{message}</p>
      )}
    </div>
  )
}

export default AddStudent
