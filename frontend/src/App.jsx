import { useEffect, useState } from 'react'
import api from './api'

const blank = { amount: '', category: 'Food', expenseDate: new Date().toISOString().slice(0, 10), description: '' }

export default function App() {
  const [user, setUser] = useState(() => JSON.parse(localStorage.getItem('user') || 'null'))
  const [authMode, setAuthMode] = useState('login')
  const [auth, setAuth] = useState({ name: '', email: '', password: '' })
  const [expenses, setExpenses] = useState([])
  const [summary, setSummary] = useState({ count: 0, total: 0 })
  const [form, setForm] = useState(blank)
  const [editingId, setEditingId] = useState(null)
  const [message, setMessage] = useState('')

  const load = async () => {
    try {
      const [e, s] = await Promise.all([api.get('/expenses'), api.get('/expenses/summary')])
      setExpenses(e.data); setSummary(s.data)
    } catch { setMessage('Please login again or start the backend server.') }
  }

  useEffect(() => { if (user) load() }, [user])

  const submitAuth = async e => {
    e.preventDefault()
    try {
      const endpoint = authMode === 'login' ? '/auth/login' : '/auth/register'
      const { data } = await api.post(endpoint, auth)
      localStorage.setItem('token', data.token)
      localStorage.setItem('user', JSON.stringify({ name: data.name, email: data.email }))
      setUser({ name: data.name, email: data.email })
      setMessage('Welcome to Centa!')
    } catch (err) { setMessage(err.response?.data?.message || 'Authentication failed') }
  }

  const submitExpense = async e => {
    e.preventDefault()
    try {
      if (editingId) await api.put(`/expenses/${editingId}`, form)
      else await api.post('/expenses', form)
      setForm(blank); setEditingId(null); await load()
      setMessage('Expense saved successfully.')
    } catch (err) { setMessage(err.response?.data?.message || 'Could not save expense') }
  }

  const remove = async id => {
    await api.delete(`/expenses/${id}`)
    await load()
  }

  const logout = () => {
    localStorage.clear(); setUser(null); setExpenses([])
  }

  if (!user) return (
    <main className="auth-page">
      <section className="auth-card">
        <h1>Centa</h1>
        <p className="muted">Smart personal expense tracking</p>
        <div className="tabs">
          <button className={authMode === 'login' ? 'active' : ''} onClick={() => setAuthMode('login')}>Login</button>
          <button className={authMode === 'register' ? 'active' : ''} onClick={() => setAuthMode('register')}>Register</button>
        </div>
        <form onSubmit={submitAuth}>
          {authMode === 'register' && <input placeholder="Full name" value={auth.name} onChange={e => setAuth({...auth, name:e.target.value})} required />}
          <input type="email" placeholder="Email" value={auth.email} onChange={e => setAuth({...auth, email:e.target.value})} required />
          <input type="password" placeholder="Password (8+ characters)" value={auth.password} onChange={e => setAuth({...auth, password:e.target.value})} required />
          <button className="primary">{authMode === 'login' ? 'Login' : 'Create account'}</button>
        </form>
        {message && <p className="message">{message}</p>}
      </section>
    </main>
  )

  return (
    <main className="app">
      <header className="topbar">
        <div><strong>Centa</strong><span> Expense Tracker</span></div>
        <div className="user"><span>{user.name}</span><button onClick={logout}>Logout</button></div>
      </header>

      <section className="content">
        <h2>Dashboard</h2>
        <div className="stats">
          <article><small>Total expenses</small><strong>₹{Number(summary.total).toFixed(2)}</strong></article>
          <article><small>Transactions</small><strong>{summary.count}</strong></article>
          <article><small>Account</small><strong>{user.email}</strong></article>
        </div>

        <div className="grid">
          <section className="card">
            <h3>{editingId ? 'Edit Expense' : 'Add Expense'}</h3>
            <form onSubmit={submitExpense}>
              <label>Amount<input type="number" min="0.01" step="0.01" value={form.amount} onChange={e=>setForm({...form, amount:e.target.value})} required /></label>
              <label>Category<select value={form.category} onChange={e=>setForm({...form, category:e.target.value})}>
                {['Food','Travel','Shopping','Bills','Health','Education','Entertainment','Other'].map(x=><option key={x}>{x}</option>)}
              </select></label>
              <label>Date<input type="date" value={form.expenseDate} onChange={e=>setForm({...form, expenseDate:e.target.value})} required /></label>
              <label>Description<input value={form.description} onChange={e=>setForm({...form, description:e.target.value})} placeholder="Optional note" /></label>
              <div className="actions"><button className="primary">{editingId ? 'Update' : 'Add expense'}</button>{editingId && <button type="button" onClick={()=>{setEditingId(null);setForm(blank)}}>Cancel</button>}</div>
            </form>
          </section>

          <section className="card">
            <div className="list-head"><h3>Recent expenses</h3><span>{expenses.length} records</span></div>
            {expenses.length === 0 ? <p className="muted">No expenses yet. Add your first expense.</p> :
              <div className="expense-list">{expenses.map(x => (
                <div className="expense" key={x.id}>
                  <div><strong>{x.category}</strong><small>{x.expenseDate} · {x.description || 'No description'}</small></div>
                  <div><b>₹{Number(x.amount).toFixed(2)}</b><div><button onClick={()=>{setEditingId(x.id);setForm({...x, amount:String(x.amount)})}}>Edit</button><button onClick={()=>remove(x.id)}>Delete</button></div></div>
                </div>
              ))}</div>
            }
          </section>
        </div>
        {message && <div className="toast">{message}</div>}
      </section>
    </main>
  )
}
