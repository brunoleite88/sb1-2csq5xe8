import React from 'react';
import { BrowserRouter as Router, Routes, Route, Navigate } from 'react-router-dom';
import { AuthProvider } from './contexts/AuthContext';
import Layout from './components/Layout';
import Login from './pages/Login';
import Dashboard from './pages/Dashboard';
import StrategicPlanning from './pages/StrategicPlanning';
import PestalAnalysis from './pages/PestalAnalysis';
import SwotAnalysis from './pages/SwotAnalysis';
import Scenarios from './pages/Scenarios';
import BSCObjectives from './pages/BSCObjectives';
import OKRsKPIs from './pages/OKRsKPIs';
import PerformanceMonitoring from './pages/PerformanceMonitoring';
import Reports from './pages/Reports';
import UserManagement from './pages/UserManagement';
import ProtectedRoute from './components/ProtectedRoute';

function App() {
  return (
    <AuthProvider>
      <Router>
        <Routes>
          <Route path="/login" element={<Login />} />
          <Route path="/" element={<ProtectedRoute><Layout /></ProtectedRoute>}>
            <Route index element={<Navigate to="/dashboard" replace />} />
            <Route path="dashboard" element={<Dashboard />} />
            <Route path="strategic-planning" element={<StrategicPlanning />} />
            <Route path="pestalanalysis" element={<PestalAnalysis />} />
            <Route path="swotanalysis" element={<SwotAnalysis />} />
            <Route path="scenarios" element={<Scenarios />} />
            <Route path="bscobjectives" element={<BSCObjectives />} />
            <Route path="okrskpis" element={<OKRsKPIs />} />
            <Route path="performance" element={<PerformanceMonitoring />} />
            <Route path="reports" element={<Reports />} />
            <Route path="users" element={<UserManagement />} />
          </Route>
        </Routes>
      </Router>
    </AuthProvider>
  );
}

export default App;