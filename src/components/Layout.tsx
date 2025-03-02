import React from 'react';
import { Outlet, Link, useLocation, useNavigate } from 'react-router-dom';
import { 
  BarChart3, 
  FileText, 
  Users, 
  LogOut, 
  PieChart, 
  Target, 
  TrendingUp, 
  Compass, 
  BarChart, 
  LineChart,
  Activity,
  Menu,
  X
} from 'lucide-react';
import { useAuth } from '../contexts/AuthContext';

const Layout: React.FC = () => {
  const { currentUser, logout, isAdmin } = useAuth();
  const location = useLocation();
  const navigate = useNavigate();
  const [sidebarOpen, setSidebarOpen] = React.useState(false);

  const handleLogout = () => {
    logout();
    navigate('/login');
  };

  const menuItems = [
    { path: '/dashboard', name: 'Dashboard', icon: <BarChart3 size={20} /> },
    { path: '/strategic-planning', name: 'Strategic Planning', icon: <Compass size={20} /> },
    { path: '/pestalanalysis', name: 'PESTAL Analysis', icon: <BarChart size={20} /> },
    { path: '/swotanalysis', name: 'SWOT Analysis', icon: <PieChart size={20} /> },
    { path: '/scenarios', name: 'Scenarios', icon: <TrendingUp size={20} /> },
    { path: '/bscobjectives', name: 'BSC Objectives', icon: <Target size={20} /> },
    { path: '/okrskpis', name: 'OKRs & KPIs', icon: <Activity size={20} /> },
    { path: '/performance', name: 'Performance', icon: <LineChart size={20} /> },
    { path: '/reports', name: 'Reports', icon: <FileText size={20} /> },
  ];

  // Only show user management for admins
  if (isAdmin) {
    menuItems.push({ path: '/users', name: 'User Management', icon: <Users size={20} /> });
  }

  return (
    <div className="flex h-screen bg-gray-100">
      {/* Mobile menu button */}
      <div className="lg:hidden fixed top-4 left-4 z-50">
        <button
          onClick={() => setSidebarOpen(!sidebarOpen)}
          className="p-2 rounded-md bg-blue-600 text-white"
        >
          {sidebarOpen ? <X size={24} /> : <Menu size={24} />}
        </button>
      </div>

      {/* Sidebar */}
      <div 
        className={`fixed inset-y-0 left-0 transform ${
          sidebarOpen ? 'translate-x-0' : '-translate-x-full'
        } lg:translate-x-0 transition duration-200 ease-in-out lg:relative lg:flex z-40 bg-blue-700 text-white w-64 flex-shrink-0 flex-col`}
      >
        <div className="flex flex-col h-full">
          <div className="p-4 border-b border-blue-800">
            <h1 className="text-xl font-bold">Strategic Planning</h1>
            <p className="text-sm text-blue-200">Public Organizations</p>
          </div>
          
          <div className="flex-grow overflow-y-auto">
            <nav className="mt-5 px-2">
              {menuItems.map((item) => (
                <Link
                  key={item.path}
                  to={item.path}
                  className={`mt-1 group flex items-center px-2 py-2 text-sm font-medium rounded-md ${
                    location.pathname === item.path
                      ? 'bg-blue-800 text-white'
                      : 'text-blue-100 hover:bg-blue-600'
                  }`}
                  onClick={() => setSidebarOpen(false)}
                >
                  <span className="mr-3">{item.icon}</span>
                  {item.name}
                </Link>
              ))}
            </nav>
          </div>
          
          <div className="p-4 border-t border-blue-800">
            <div className="flex items-center">
              <div className="flex-shrink-0">
                <div className="h-8 w-8 rounded-full bg-blue-500 flex items-center justify-center">
                  {currentUser?.name.charAt(0)}
                </div>
              </div>
              <div className="ml-3">
                <p className="text-sm font-medium text-white">{currentUser?.name}</p>
                <p className="text-xs text-blue-300 capitalize">{currentUser?.role}</p>
              </div>
            </div>
            <button
              onClick={handleLogout}
              className="mt-3 w-full flex items-center px-2 py-2 text-sm font-medium rounded-md text-blue-100 hover:bg-blue-600"
            >
              <LogOut size={18} className="mr-2" />
              Logout
            </button>
          </div>
        </div>
      </div>

      {/* Main content */}
      <div className="flex-1 flex flex-col overflow-hidden">
        <main className="flex-1 overflow-y-auto bg-gray-100 p-4 md:p-6">
          <Outlet />
        </main>
      </div>
    </div>
  );
};

export default Layout;