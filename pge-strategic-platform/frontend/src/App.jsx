import React from 'react';
import { Routes, Route, Navigate } from 'react-router-dom';
import Login from './pages/Login';
import Dashboard from './pages/Dashboard';
import PlanejamentoEstrategico from './pages/PlanejamentoEstrategico';
import GestaoPessoas from './pages/GestaoPessoas';
import Inovação from './pages/Inovacao';
import RiscosIntegridade from './pages/RiscosIntegridade';
import GestaoContratual from './pages/GestaoContratual';
import MemoriaInstitucional from './pages/MemoriaInstitucional';
import PrivateRoute from './components/PrivateRoute';

function App() {
  return (
    <Routes>
      <Route path="/login" element={<Login />} />
      <Route path="/" element={<PrivateRoute><Dashboard /></PrivateRoute>} />
      <Route path="/planejamento" element={<PrivateRoute><PlanejamentoEstrategico /></PrivateRoute>} />
      <Route path="/pessoas" element={<PrivateRoute><GestaoPessoas /></PrivateRoute>} />
      <Route path="/inovacao" element={<PrivateRoute><Inovação /></PrivateRoute>} />
      <Route path="/riscos" element={<PrivateRoute><RiscosIntegridade /></PrivateRoute>} />
      <Route path="/contratos" element={<PrivateRoute><GestaoContratual /></PrivateRoute>} />
      <Route path="/memoria" element={<PrivateRoute><MemoriaInstitucional /></PrivateRoute>} />
      <Route path="*" element={<Navigate to="/" replace />} />
    </Routes>
  );
}

export default App;
