import React, { useState } from 'react'
import { Link, useNavigate } from 'react-router-dom'
import { toast } from 'react-toastify'
import { assets } from '../../assets/assets'
import './Auth.css'
import api from '../../config/api'

const Signup = () => {
  const navigate = useNavigate()
  
const [role,setRole]=useState("customer");
const [name,setName]=useState("");
const [email,setEmail]=useState("");
const [phone,setPhone]=useState("");
const [location,setLocation]=useState("");
const [password,setPassword]=useState("");
const [status,setStatus]=useState("online");
const [errors, setErrors] = useState({});


const validateForm = () => {
  const newErrors = {};

  
  if (!name.trim()) {
    newErrors.name = 'Name is required';
  } else if (!/^[a-zA-Z ]+$/.test(name.trim())) {
    newErrors.name = 'Name must contain only alphabets and spaces';
  }

  if (!email.trim()) {
    newErrors.email = 'Email is required';
  } else if (!/^[^\s@]+@[^\s@]+\.[^\s@]+$/.test(email)) {
    newErrors.email = 'Email id is invalid format';
  }

  if (!phone.trim()) {
    newErrors.phone = 'Phone number is required';
  } else if (!/^[0-9]{10}$/.test(phone)) {
    newErrors.phone = 'Phone number must be 10 digits';
  }

  if (!location.trim()) {
    newErrors.location = 'Location is required';
  }
  else if (!/^[a-zA-Z]+$/.test(location.trim())) {
    newErrors.location = 'location must contain only alphabets and no spaces';
  }

if (!password) {
    newErrors.password = 'Password is required';
  } else if (password.length < 6 || /\s/.test(password)) {
    newErrors.password = 'Password must be at least 6 characters and Space are not Allowed';
  }

  setErrors(newErrors);
  return Object.keys(newErrors).length === 0;
}

const handleSubmit = async (e) => {
  e.preventDefault();
  

  if (!validateForm()) {
    toast.error('Please fix the validation errors');
    return;
  }

  try {
    const endpoint = role === "delivery-partner" 
      ? '/app1/api/v1/users/signup/partner' 
      : '/app1/api/v1/users/signup/customer';
    
    const payload = {
      name,
      email,
      phno: phone,
      location,
      password
    };
    
    const res = await api.post(endpoint, payload);
    
    if (res.status === 201 || res.status === 200) {
      toast.success('Account created successfully!');
      navigate('/login');
    }
  } catch (err) {
  
    console.error('Signup failed:', err);
    toast.error(err.response?.data?.message || 'Signup failed. Please try again.');
  }
}
 

  return (
    
    <div 
      className="auth-container"
      style={{
        backgroundImage: `linear-gradient(rgba(0, 0, 0, 0.5), rgba(0, 0, 0, 0.5)), url(${assets.landing_img})`,
        backgroundSize: 'cover',
        backgroundPosition: 'center',
        backgroundRepeat: 'no-repeat'
      }}
    >
      <div className="auth-card fade-in">
        <div className="auth-header">
          <h1>QuickBite</h1>
          <h2>Create your account</h2>
        </div>
        
        <form className="auth-form" onSubmit={handleSubmit}>
          <div className="form-group">
            <label htmlFor="role">Select Role</label>
            <select
              id="role"
              name="role"
              value={role}
              onChange={(e) => setRole(e.target.value)}
              required
            >
              <option value="customer">Customer</option>
              
              <option value="delivery-partner">Delivery Partner</option>
            </select>
          </div>

          <div className="form-group">
            <label htmlFor="name">Full Name</label>
            <input
              type="text"
              id="name"
              name="name"
              value={name}
              onChange={(e) => {
                setName(e.target.value);
                if (errors.name) setErrors({...errors, name: ''});
              }}
              className={errors.name ? 'input-error' : ''}
              placeholder="Enter your name"
             
            />
            {errors.name && <span className="error-message">{errors.name}</span>}
          </div>

          <div className="form-group">
            <label htmlFor="email">Email</label>
            <input
              type="email"
              id="email"
              name="email"
              value={email}
              onChange={(e) => {
                setEmail(e.target.value);
                if (errors.email) setErrors({...errors, email: ''});
              }}
              className={errors.email ? 'input-error' : ''}
              placeholder="Enter your email"
            />
            {errors.email && <span className="error-message">{errors.email}</span>}
          </div>

          {(
            <div className="form-group">
              <label htmlFor="phone">Phone Number</label>
              <input
                type="tel"
                id="phone"
                name="phone"
                value={phone}
                onChange={(e) => {
                  setPhone(e.target.value);
                  if (errors.phone) setErrors({...errors, phone: ''});
                }}
                className={errors.phone ? 'input-error' : ''}
                placeholder="Enter 10-digit phone number"
                maxLength={10}
              />
              {errors.phone && <span className="error-message">{errors.phone}</span>}
            </div>
          )}

          {(
            <div className="form-group">
              <label htmlFor="location">Location</label>
              <input
                type="text"
                id="location"
                name="location"
                value={location}
                onChange={(e) => {
                  setLocation(e.target.value);
                  if (errors.location) setErrors({...errors, location: ''});
                }}
                className={errors.location ? 'input-error' : ''}
                placeholder="Enter your location"
              />
              {errors.location && <span className="error-message">{errors.location}</span>}
            </div>
          )}

          <div className="form-group">
            <label htmlFor="password">Password</label>
            <input
              type="password"
              id="password"
              name="password"
              value={password}
              onChange={(e) => {
                setPassword(e.target.value);
                if (errors.password) setErrors({...errors, password: ''});
              }}
              className={errors.password ? 'input-error' : ''}
              placeholder="Enter password (min 6 characters)"
            />
            {errors.password && <span className="error-message">{errors.password}</span>}
          </div>

          <button type="submit" className="auth-button">Submit </button>
        </form>

        <div className="auth-footer">
          <button 
            type="button" 
            className="back-button" 
            onClick={() => navigate('/')}
          >
            ← Back to Home
          </button>
          <p>Already have an account? <Link to="/login">Login</Link></p>
          <p><Link to="/">Back to Landing Page</Link></p>
        </div>
      </div>
      
    </div>
  )
}

export default Signup
