import React, { useState } from 'react'
import StudentList from './components/StudentList'
import AddStudent from './components/AddStudent'

function App() {
  const [refresh, setRefresh] = useState(false)
  const triggerRefresh = () => setRefresh(prev => !prev)

  return (
    <div style={{ fontFamily: 'Arial, sans-serif', maxWidth: '900px', margin: '30px auto', padding: '0 20px' }}>
      <h1 style={{ textAlign: 'center', color: '#2c3e50' }}>Student Management System</h1>
      <AddStudent onStudentAdded={triggerRefresh} />
      <StudentList refresh={refresh} />
    </div>
  )
}

export default App
