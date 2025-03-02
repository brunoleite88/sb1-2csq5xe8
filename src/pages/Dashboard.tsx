import React from 'react';
import { 
  BarChart3, 
  TrendingUp, 
  Target, 
  AlertTriangle, 
  CheckCircle,
  Clock
} from 'lucide-react';
import { useAuth } from '../contexts/AuthContext';
import { 
  Chart as ChartJS, 
  CategoryScale, 
  LinearScale, 
  BarElement, 
  Title, 
  Tooltip, 
  Legend,
  PointElement,
  LineElement,
  ArcElement
} from 'chart.js';
import { Bar, Doughnut } from 'react-chartjs-2';

// Register ChartJS components
ChartJS.register(
  CategoryScale,
  LinearScale,
  BarElement,
  PointElement,
  LineElement,
  ArcElement,
  Title,
  Tooltip,
  Legend
);

const Dashboard: React.FC = () => {
  const { currentUser } = useAuth();

  // Sample data for charts
  const performanceData = {
    labels: ['Financial', 'Customer', 'Internal Processes', 'Learning & Growth'],
    datasets: [
      {
        label: 'Current Performance (%)',
        data: [78, 65, 82, 70],
        backgroundColor: 'rgba(59, 130, 246, 0.6)',
        borderColor: 'rgb(59, 130, 246)',
        borderWidth: 1,
      },
      {
        label: 'Target (%)',
        data: [90, 85, 95, 80],
        backgroundColor: 'rgba(16, 185, 129, 0.2)',
        borderColor: 'rgb(16, 185, 129)',
        borderWidth: 1,
        type: 'line' as const,
      }
    ],
  };

  const statusData = {
    labels: ['Completed', 'In Progress', 'At Risk', 'Not Started'],
    datasets: [
      {
        data: [12, 8, 3, 5],
        backgroundColor: [
          'rgba(16, 185, 129, 0.6)',
          'rgba(59, 130, 246, 0.6)',
          'rgba(245, 158, 11, 0.6)',
          'rgba(239, 68, 68, 0.6)',
        ],
        borderColor: [
          'rgb(16, 185, 129)',
          'rgb(59, 130, 246)',
          'rgb(245, 158, 11)',
          'rgb(239, 68, 68)',
        ],
        borderWidth: 1,
      },
    ],
  };

  // Sample KPIs
  const kpis = [
    { name: 'Budget Execution', value: '78%', target: '90%', status: 'warning' },
    { name: 'Citizen Satisfaction', value: '65%', target: '85%', status: 'warning' },
    { name: 'Process Efficiency', value: '82%', target: '95%', status: 'success' },
    { name: 'Employee Training', value: '70%', target: '80%', status: 'warning' },
  ];

  // Sample recent activities
  const recentActivities = [
    { id: 1, action: 'Updated KPI values for Q2', user: 'Maria Silva', time: '2 hours ago' },
    { id: 2, action: 'Added new strategic objective', user: 'João Santos', time: '1 day ago' },
    { id: 3, action: 'Completed SWOT analysis', user: 'Ana Oliveira', time: '2 days ago' },
    { id: 4, action: 'Generated quarterly report', user: 'Carlos Mendes', time: '3 days ago' },
  ];

  return (
    <div className="space-y-6">
      <div className="flex justify-between items-center">
        <h1 className="text-2xl font-bold text-gray-800">Dashboard</h1>
        <div className="text-sm text-gray-600">
          Last updated: {new Date().toLocaleDateString()}
        </div>
      </div>

      {/* Welcome message */}
      <div className="bg-white rounded-lg shadow p-6">
        <h2 className="text-lg font-medium text-gray-800">
          Welcome back, {currentUser?.name}!
        </h2>
        <p className="mt-1 text-gray-600">
          Here's an overview of your organization's strategic performance.
        </p>
      </div>

      {/* KPI summary cards */}
      <div className="grid grid-cols-1 md:grid-cols-2 lg:grid-cols-4 gap-4">
        {kpis.map((kpi, index) => (
          <div key={index} className="bg-white rounded-lg shadow p-4">
            <div className="flex justify-between items-start">
              <div>
                <p className="text-sm text-gray-600">{kpi.name}</p>
                <p className="text-2xl font-bold mt-1">{kpi.value}</p>
                <p className="text-xs text-gray-500">Target: {kpi.target}</p>
              </div>
              <div className={`p-2 rounded-full ${
                kpi.status === 'success' ? 'bg-green-100 text-green-600' : 
                kpi.status === 'warning' ? 'bg-yellow-100 text-yellow-600' : 
                'bg-red-100 text-red-600'
              }`}>
                {kpi.status === 'success' ? <CheckCircle size={20} /> : 
                 kpi.status === 'warning' ? <AlertTriangle size={20} /> : 
                 <AlertTriangle size={20} />}
              </div>
            </div>
          </div>
        ))}
      </div>

      {/* Charts */}
      <div className="grid grid-cols-1 lg:grid-cols-2 gap-6">
        <div className="bg-white rounded-lg shadow p-4">
          <h2 className="text-lg font-medium text-gray-800 mb-4">BSC Performance by Perspective</h2>
          <div className="h-64">
            <Bar 
              data={performanceData} 
              options={{
                responsive: true,
                maintainAspectRatio: false,
                scales: {
                  y: {
                    beginAtZero: true,
                    max: 100,
                    ticks: {
                      callback: function(value) {
                        return value + '%';
                      }
                    }
                  }
                }
              }} 
            />
          </div>
        </div>

        <div className="bg-white rounded-lg shadow p-4">
          <h2 className="text-lg font-medium text-gray-800 mb-4">Initiatives Status</h2>
          <div className="h-64 flex items-center justify-center">
            <div className="w-3/4 h-full">
              <Doughnut 
                data={statusData} 
                options={{
                  responsive: true,
                  maintainAspectRatio: false,
                  plugins: {
                    legend: {
                      position: 'right',
                    }
                  }
                }} 
              />
            </div>
          </div>
        </div>
      </div>

      {/* Recent activity */}
      <div className="bg-white rounded-lg shadow p-4">
        <h2 className="text-lg font-medium text-gray-800 mb-4">Recent Activity</h2>
        <div className="space-y-4">
          {recentActivities.map((activity) => (
            <div key={activity.id} className="flex items-start">
              <div className="p-2 bg-blue-100 rounded-full text-blue-600 mr-3">
                <Clock size={16} />
              </div>
              <div>
                <p className="text-sm font-medium">{activity.action}</p>
                <p className="text-xs text-gray-500">
                  {activity.user} • {activity.time}
                </p>
              </div>
            </div>
          ))}
        </div>
      </div>
    </div>
  );
};

export default Dashboard;