import React, { useState, useEffect } from 'react'
import axios from 'axios'

const API_URL = 'http://localhost:8080/students'

function StudentList({ refresh }) {
  const [students, setStudents] = useState([])
  const [editId, setEditId] = useState(null)
  const [editData, setEditData] = useState({ name: '', email: '', course: '' })

  useEffect(() => {
    fetchStudents()
  }, [refresh])

  const fetchStudents = async () => {
    try {
      const response = await axios.get(API_URL)
      setStudents(response.data)
    } catch (error) {
      console.error('Error fetching students:', error)
    }
  }

  const handleDelete = async (id) => {
    if (!window.confirm('Delete this student?')) return
    try {
      await axios.delete(`${API_URL}/${id}`)
      fetchStudents()
    } catch (error) {
      alert('Error deleting student.')
    }
  }

  const handleEditClick = (student) => {
    setEditId(student.id)
    setEditData({ name: student.name, email: student.email, course: student.course })
  }

  const handleEditChange = (e) => {
    setEditData({ ...editData, [e.target.name]: e.target.value })
  }

  const handleUpdate = async (id) => {
    try {
      await axios.put(`${API_URL}/${id}`, editData)
      setEditId(null)
      fetchStudents()
    } catch (error) {
      alert('Error updating student.')
    }
  }

  const thStyle = { backgroundColor: '#2c3e50', color: 'white', padding: '10px 14px', textAlign: 'left' }
  const tdStyle = { padding: '10px 14px', borderBottom: '1px solid #eee' }
  const inputStyle = { padding: '5px 8px', border: '1px solid #ccc', borderRadius: '4px', fontSize: '13px', width: '120px' }

  return (
    <div>
      <h2 style={{ color: '#2c3e50' }}>All Students ({students.length})</h2>
      {students.length === 0 ? (
        <p style={{ color: '#888' }}>No students found. Add one above!</p>
      ) : (
        <table style={{ width: '100%', borderCollapse: 'collapse', fontSize: '14px' }}>
          <thead>
            <tr>
              <th style={thStyle}>ID</th>
              <th style={thStyle}>Name</th>
              <th style={thStyle}>Email</th>
              <th style={thStyle}>Course</th>
              <th style={thStyle}>Actions</th>
            </tr>
          </thead>
          <tbody>
            {students.map((student) => (
              <tr key={student.id} style={{ backgroundColor: editId === student.id ? '#fffbe6' : 'white' }}>
                <td style={tdStyle}>{student.id}</td>
                {editId === student.id ? (
                  <>
                    <td style={tdStyle}><input style={inputStyle} name="name" value={editData.name} onChange={handleEditChange} /></td>
                    <td style={tdStyle}><input style={inputStyle} name="email" value={editData.email} onChange={handleEditChange} /></td>
                    <td style={tdStyle}><input style={inputStyle} name="course" value={editData.course} onChange={handleEditChange} /></td>
                    <td style={tdStyle}>
                      <button onClick={() => handleUpdate(student.id)} style={{ padding: '5px 12px', backgroundColor: '#27ae60', color: 'white', border: 'none', borderRadius: '4px', cursor: 'pointer', marginRight: '6px' }}>Save</button>
                      <button onClick={() => setEditId(null)} style={{ padding: '5px 12px', backgroundColor: '#95a5a6', color: 'white', border: 'none', borderRadius: '4px', cursor: 'pointer' }}>Cancel</button>
                    </td>
                  </>
                ) : (
                  <>
                    <td style={tdStyle}>{student.name}</td>
                    <td style={tdStyle}>{student.email}</td>
                    <td style={tdStyle}>{student.course}</td>
                    <td style={tdStyle}>
                      <button onClick={() => handleEditClick(student)} style={{ padding: '5px 12px', backgroundColor: '#2980b9', color: 'white', border: 'none', borderRadius: '4px', cursor: 'pointer', marginRight: '6px' }}>Update</button>
                      <button onClick={() => handleDelete(student.id)} style={{ padding: '5px 12px', backgroundColor: '#e74c3c', color: 'white', border: 'none', borderRadius: '4px', cursor: 'pointer' }}>Delete</button>
                    </td>
                  </>
                )}
              </tr>
            ))}
          </tbody>
        </table>
      )}
    </div>
  )
}

export default StudentList
