// src/App.js
import React, { useState, useEffect } from 'react';
import { checkHealth, getProducts } from './services/api';
import './App.css';

function App() {
  const [healthStatus, setHealthStatus] = useState('Checking...');
  const [products, setProducts] = useState([]);
  const [loading, setLoading] = useState(true);
  const [error, setError] = useState(null);

  useEffect(() => {
    // Check backend health
    checkHealth()
      .then((res) => setHealthStatus(`✅ ${res.data}`))
      .catch(() => setHealthStatus('❌ Backend not reachable'));

    // Fetch products
    getProducts()
      .then((res) => {
        setProducts(res.data);
        setLoading(false);
      })
      .catch((err) => {
        setError('Failed to load products. Is the backend running?');
        setLoading(false);
      });
  }, []);

  return (
    <div className="App">
      <header className="App-header">
        <h1>🚀 {process.env.REACT_APP_APP_NAME}</h1>
        <p className="version">v{process.env.REACT_APP_VERSION}</p>
      </header>

      <main className="App-main">
        {/* Health Check Section */}
        <section className="card">
          <h2>Backend Health Check</h2>
          <p className="status">{healthStatus}</p>
          <p className="api-url">
            API URL: <code>{process.env.REACT_APP_API_URL}</code>
          </p>
        </section>

        {/* Products Section */}
        <section className="card">
          <h2>Products from API</h2>
          {loading && <p>Loading products...</p>}
          {error && <p className="error">{error}</p>}
          {!loading && !error && (
            <table className="products-table">
              <thead>
                <tr>
                  <th>ID</th>
                  <th>Name</th>
                  <th>Price (₹)</th>
                  <th>Description</th>
                </tr>
              </thead>
              <tbody>
                {products.map((p) => (
                  <tr key={p.id}>
                    <td>{p.id}</td>
                    <td>{p.name}</td>
                    <td>₹{p.price.toLocaleString()}</td>
                    <td>{p.description}</td>
                  </tr>
                ))}
              </tbody>
            </table>
          )}
        </section>
      </main>

      <footer className="App-footer">
        <p>SKILL-13: Full-Stack Deployment (Spring Boot + React)</p>
      </footer>
    </div>
  );
}

export default App;
