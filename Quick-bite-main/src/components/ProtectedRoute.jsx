import React from 'react';
import { Navigate } from 'react-router-dom';
import { toast } from 'react-toastify';

const ProtectedRoute = ({ children, allowedRoles }) => {
  const token = localStorage.getItem('jwtToken');
  const currentUser = localStorage.getItem('currentUser');


  if (!token || !currentUser) {
    toast.error('Please login to access this page');
    return <Navigate to="/login" replace />;
  }

  try {
    const user = JSON.parse(currentUser);
    const userRole = user.role?.toLowerCase();

    
    if (allowedRoles && allowedRoles.length > 0) {
      const hasAccess = allowedRoles.some(
        role => role.toLowerCase() === userRole
      );

      if (!hasAccess) {
        toast.error('Access denied. You do not have permission to view this page.');
   
        if (userRole === 'admin') {
          return <Navigate to="/admin/MenuManagement" replace />;
        } else if (userRole === 'deliverypartner' || userRole === 'delivery_partner') {
          return <Navigate to="/delivery-partner" replace />;
        } else if (userRole === 'customer') {
          return <Navigate to="/user" replace />;
        } else {
          return <Navigate to="/" replace />;
        }
      }
    }

    return children;
  } catch (error) {
    console.error('Error parsing user data:', error);
    toast.error('Invalid session. Please login again.');
    localStorage.clear();
    return <Navigate to="/login" replace />;
  }
};

export default ProtectedRoute;
