import React, { useState } from 'react';
import { 
  BarChart, 
  LineChart, 
  PieChart, 
  ArrowUp, 
  ArrowDown, 
  Minus,
  Calendar,
  Filter,
  Download
} from 'lucide-react';
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
import { Bar, Line, Doughnut } from 'react-chartjs-2';
import { useAuth } from '../contexts/AuthContext';

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
); interface PerformanceData {
  id: string;
  objective: string;
  perspective: 'financial' | 'customer' | 'internal' | 'learning';
  keyResults: {
    id: string;
    name: string;
    target: number;
    unit: string;
    periods: {
      period: string;
      value: number;
    }[];
  }[];
}

const PerformanceMonitoring: React.FC = () => {
  const { isManager, isAdmin } = useAuth();
  const canEdit = isManager || isAdmin;
  
  const [selectedPerspective, setSelectedPerspective] = useState<string>('all');
  const [selectedPeriod, setSelectedPeriod] = useState<string>('Q2 2025');
  const [selectedView, setSelectedView] = useState<string>('table');
  
  // Sample performance data
  const [performanceData, setPerformanceData] = useState<PerformanceData[]>([
    {
      id: 'perf1',
      objective: 'Optimize Resource Allocation',
      perspective: 'financial',
      keyResults: [
        {
          id: 'kr1',
          name: 'Budget execution rate',
          target: 95,
          unit: '%',
          periods: [
            { period: 'Q1 2025', value: 72 },
            { period: 'Q2 2025', value: 78 },
            { period: 'Q3 2025', value: 0 },
            { period: 'Q4 2025', value: 0 }
          ]
        },
        {
          id: 'kr2',
          name: 'Operational cost reduction',
          target: 15,
          unit: '%',
          periods: [
            { period: 'Q1 2025', value: 5 },
            { period: 'Q2 2025', value: 8 },
            { period: 'Q3 2025', value: 0 },
            { period: 'Q4 2025', value: 0 }
          ]
        }
      ]
    },
    {
      id: 'perf2',
      objective: 'Increase Citizen Satisfaction',
      perspective: 'customer',
      keyResults: [
        {
          id: 'kr4',
          name: 'Citizen satisfaction score',
          target: 85,
          unit: '%',
          periods: [
            { period: 'Q1 2025', value: 60 },
            { period: 'Q2 2025', value: 65 },
            { period: 'Q3 2025', value: 0 },
            { period: 'Q4 2025', value: 0 }
          ]
        },
        {
          id: 'kr5',
          name: 'Service response time reduction',
          target: 50,
          unit: '%',
          periods: [
            { period: 'Q1 2025', value: 20 },
            { period: 'Q2 2025', value: 30 },
            { period: 'Q3 2025', value: 0 },
            { period: 'Q4 2025', value: 0 }
          ]
        },
        {
          id: 'kr6',
          name: 'Digital service adoption',
          target: 70,
          unit: '%',
          periods: [
            { period: 'Q1 2025', value: 35 },
            { period: 'Q2 2025', value: 45 },
            { period: 'Q3 2025', value: 0 },
            { period: 'Q4 2025', value: 0 }
          ]
        }
      ]
    },
    {
      id: 'perf3',
      objective: 'Streamline Internal Processes',
      perspective: 'internal',
      keyResults: [
        {
          id: 'kr7',
          name: 'Process cycle time reduction',
          target: 40,
          unit: '%',
          periods: [
            { period: 'Q1 2025', value: 15 },
            { period: 'Q2 2025', value: 25 },
            { period: 'Q3 2025', value: 0 },
            { period: 'Q4 2025', value: 0 }
          ]
        },
        {
          id: 'kr8',
          name: 'Process automation level',
          target: 60,
          unit: '%',
          periods: [
            { period: 'Q1 2025', value: 25 },
            { period: 'Q2 2025', value: 35 },
            { period: 'Q3 2025', value: 0 },
            { period: 'Q4 2025', value: 0 }
          ]
        }
      ]
    },
    {
      id: 'perf4',
      objective: 'Develop Digital Capabilities',
      perspective: 'learning',
      keyResults: [
        {
          id: 'kr10',
          name: 'Staff with digital skills',
          target: 80,
          unit: '%',
          periods: [
            { period: 'Q1 2025', value: 45 },
            { period: 'Q2 2025', value: 55 },
            { period: 'Q3 2025', value: 0 },
            { period: 'Q4 2025', value: 0 }
          ]
        },
        {
          id: 'kr11',
          name: 'Digital training hours per employee',
          target: 40,
          unit: 'hours',
          periods: [
            { period: 'Q1 2025', value: 15 },
            { period: 'Q2 2025', value: 22 },
            { period: 'Q3 2025', value: 0 },
            { period: 'Q4 2025', value: 0 }
          ]
        }
      ]
    }
  ]);

  const handleUpdatePerformance = (
    objectiveId: string, 
    keyResultId: string, 
    period: string, 
    newValue: number
  ) => {
    if (!canEdit) return;
    
    const updatedData = performanceData.map(objective => {
      if (objective.id === objectiveId) {
        const updatedKeyResults = objective.keyResults.map(kr => {
          if (kr.id === keyResultId) {
            const updatedPeriods = kr.periods.map(p => {
              if (p.period === period) {
                return { ...p, value: newValue };
              }
              return p;
            });
            return { ...kr, periods: updatedPeriods };
          }
          return kr;
        });
        return { ...objective, keyResults: updatedKeyResults };
      }
      return objective;
    });
    
    setPerformanceData(updatedData);
  };

  const getPerspectiveColor = (perspective: string) => {
    switch (perspective) {
      case 'financial':
        return {
          bg: 'bg-green-100',
          text: 'text-green-800',
          border: 'border-green-200',
          progress: 'bg-green-500'
        };
      case 'customer':
        return {
          bg: 'bg-blue-100',
          text: 'text-blue-800',
          border: 'border-blue-200',
          progress: 'bg-blue-500'
        };
      case 'internal':
        return {
          bg: 'bg-purple-100',
          text: 'text-purple-800',
          border: 'border-purple-200',
          progress: 'bg-purple-500'
        };
      case 'learning':
        return {
          bg: 'bg-yellow-100',
          text: 'text-yellow-800',
          border: 'border-yellow-200',
          progress: 'bg-yellow-500'
        };
      default:
        return {
          bg: 'bg-gray-100',
          text: 'text-gray-800',
          border: 'border-gray-200',
          progress: 'bg-gray-500'
        };
    }
  };

  const getPerspectiveLabel = (perspective: string) => {
    switch (perspective) {
      case 'financial':
        return 'Financial';
      case 'customer':
        return 'Customer';
      case 'internal':
        return 'Internal Processes';
      case 'learning':
        return 'Learning & Growth';
      default:
        return perspective;
    }
  };

  const calculateProgress = (current: number, target: number) => {
    if (target === 0) return 0;
    const progress = (current / target) * 100;
    return Math.min(100, Math.max(0, progress));
  };

  const getTrendIcon = (keyResult: any) => {
    const currentPeriodIndex = keyResult.periods.findIndex(
      (p: any) => p.period === selectedPeriod
    );
    
    if (currentPeriodIndex <= 0) return <Minus size={16} className="text-gray-500" />;
    
    const currentValue = keyResult.periods[currentPeriodIndex].value;
    const previousValue = keyResult.periods[currentPeriodIndex - 1].value;
    
    if (currentValue > previousValue) {
      return <ArrowUp size={16} className="text-green-500" />;
    } else if (currentValue < previousValue) {
      return <ArrowDown size={16} className="text-red-500" />;
    } else {
      return <Minus size={16} className="text-gray-500" />;
    }
  };

  // Filter data based on selected perspective
  const filteredData = selectedPerspective === 'all' 
    ? performanceData 
    : performanceData.filter(item => item.perspective === selectedPerspective);

  // Prepare data for charts
  const prepareBarChartData = () => {
    const labels: string[] = [];
    const datasets: any[] = [];
    
    // Collect all unique key result names
    filteredData.forEach(objective => {
      objective.keyResults.forEach(kr => {
        if (!labels.includes(kr.name)) {
          labels.push(kr.name);
        }
      });
    });
    
    // Create datasets for current values and targets
    const currentValues = labels.map(label => {
      let value = 0;
      filteredData.forEach(objective => {
        const keyResult = objective.keyResults.find(kr => kr.name === label);
        if (keyResult) {
          const periodData = keyResult.periods.find(p => p.period === selectedPeriod);
          if (periodData) {
            value = periodData.value;
          }
        }
      });
      return value;
    });
    
    const targetValues = labels.map(label => {
      let target = 0;
      filteredData.forEach(objective => {
        const keyResult = objective.keyResults.find(kr => kr.name === label);
        if (keyResult) {
          target = keyResult.target;
        }
      });
      return target;
    });
    
    datasets.push({
      label: 'Current',
      data: currentValues,
      backgroundColor: 'rgba(59, 130, 246, 0.6)',
      borderColor: 'rgb(59, 130, 246)',
      borderWidth: 1
    });
    
    datasets.push({
      label: 'Target',
      data: targetValues,
      backgroundColor: 'rgba(16, 185, 129, 0.2)',
      borderColor: 'rgb(16, 185, 129)',
      borderWidth: 1
    });
    
    return { labels, datasets };
  };

  const prepareLineChartData = () => {
    const datasets: any[] = [];
    const periods = ['Q1 2025', 'Q2 2025', 'Q3 2025', 'Q4 2025'];
    
    // Create a dataset for each key result
    filteredData.forEach(objective => {
      objective.keyResults.forEach(kr => {
        const data = periods.map(period => {
          const periodData = kr.periods.find(p => p.period === period);
          return periodData ? periodData.value : 0;
        });
        
        // Generate a random color
        const r = Math.floor(Math.random() * 200);
        const g = Math.floor(Math.random() * 200);
        const b = Math.floor(Math.random() * 200);
        
        datasets.push({
          label: kr.name,
          data,
          borderColor: `rgb(${r}, ${g}, ${b})`,
          backgroundColor: `rgba(${r}, ${g}, ${b}, 0.1)`,
          tension: 0.3
        });
      });
    });
    
    return { labels: periods, datasets };
  };

  const prepareDoughnutChartData = () => {
    const labels: string[] = [];
    const data: number[] = [];
    const backgroundColor: string[] = [];
    const borderColor: string[] = [];
    
    // Calculate achievement percentage for each objective
    filteredData.forEach(objective => {
      labels.push(objective.objective);
      
      let totalProgress = 0;
      objective.keyResults.forEach(kr => {
        const periodData = kr.periods.find(p => p.period === selectedPeriod);
        if (periodData) {
          totalProgress += calculateProgress(periodData.value, kr.target);
        }
      });
      
      const averageProgress = objective.keyResults.length > 0 
        ? totalProgress / objective.keyResults.length 
        : 0;
      
      data.push(Math.round(averageProgress));
      
      // Set colors based on perspective
      switch (objective.perspective) {
        case 'financial':
          backgroundColor.push('rgba(16, 185, 129, 0.6)');
          borderColor.push('rgb(16, 185, 129)');
          break;
        case 'customer':
          backgroundColor.push('rgba(59, 130, 246, 0.6)');
          borderColor.push('rgb(59, 130, 246)');
          break;
        case 'internal':
          backgroundColor.push('rgba(139, 92, 246, 0.6)');
          borderColor.push('rgb(139, 92, 246)');
          break;
        case 'learning':
          backgroundColor.push('rgba(245, 158, 11, 0.6)');
          borderColor.push('rgb(245, 158, 11)');
          break;
        default:
          backgroundColor.push('rgba(107, 114, 128, 0.6)');
          borderColor.push('rgb(107, 114, 128)');
      }
    });
    
    return {
      labels,
      datasets: [{
        data,
        backgroundColor,
        borderColor,
        borderWidth: 1
      }]
    };
  };

  const barChartData = prepareBarChartData();
  const lineChartData = prepareLineChartData();
  const doughnutChartData = prepareDoughnutChartData();

  return (
    <div className="space-y-6">
      <div>
        <h1 className="text-2xl font-bold text-gray-800">Performance Monitoring</h1>
        <p className="text-gray-600 mt-1">
          Track and update performance against strategic objectives and key results
        </p>
      </div>

      <div className="bg-white rounded-lg shadow p-4">
        <div className="flex flex-wrap items-center justify-between gap-4">
          <div className="flex items-center space-x-4">
            <div className="flex items-center">
              <Filter size={18} className="text-gray-500 mr-2" />
              <select
                value={selectedPerspective}
                onChange={(e) => setSelectedPerspective(e.target.value)}
                className="p-2 border border-gray-300 rounded-md text-sm"
              >
                <option value="all">All Perspectives</option>
                <option value="financial">Financial</option>
                <option value="customer">Customer</option>
                <option value="internal">Internal Processes</option>
                <option value="learning">Learning & Growth</option>
              </select>
            </div>
            
            <div className="flex items-center">
              <Calendar size={18} className="text-gray-500 mr-2" />
              <select
                value={selectedPeriod}
                onChange={(e) => setSelectedPeriod(e.target.value)}
                className="p-2 border border-gray-300 rounded-md text-sm"
              >
                <option value="Q1 2025">Q1 2025</option>
                <option value="Q2 2025">Q2 2025</option>
                <option value="Q3 2025">Q3 2025</option>
                <option value="Q4 2025">Q4 2025</option>
              </select>
            </div>
          </div>
          
          <div className="flex items-center space-x-2">
            <button
              onClick={() => setSelectedView('table')}
              className={`p-2 rounded-md ${
                selectedView === 'table' 
                  ? 'bg-blue-100 text-blue-600' 
                  : 'bg-gray-100 text-gray-600 hover:bg-gray-200'
              }`}
            >
              Table
            </button>
            <button
              onClick={() => setSelectedView('bar')}
              className={`p-2 rounded-md ${
                selectedView === 'bar' 
                  ? 'bg-blue-100 text-blue-600' 
                  : 'bg-gray-100 text-gray-600 hover:bg-gray-200'
              }`}
            >
              <BarChart size={18} />
            </button>
            <button
              onClick={() => setSelectedView('line')}
              className={`p-2 rounded-md ${
                selectedView === 'line' 
                  ? 'bg-blue-100 text-blue-600' 
                  : 'bg-gray-100 text-gray-600 hover:bg-gray-200'
              }`}
            >
              <LineChart size={18} />
            </button>
            <button
              onClick={() => setSelectedView('doughnut')}
              className={`p-2 rounded-md ${
                selectedView === 'doughnut' 
                  ? 'bg-blue-100 text-blue-600' 
                  : 'bg-gray-100 text-gray-600 hover:bg-gray-200'
              }`}
            >
              <PieChart size={18} />
            </button>
            <button
              className="p-2 bg-gray-100 text-gray-600 hover:bg-gray-200 rounded-md"
            >
              <Download size={18} />
            </button>
          </div>
        </div>
      </div>

      {selectedView === 'table' && (
        <div className="space-y-6">
          {filteredData.map((objective) => {
            const colors = getPerspectiveColor(objective.perspective);
            
            return (
              <div 
                key={objective.id} 
                className="bg-white rounded-lg shadow overflow-hidden"
              >
                <div className={`p-4 ${colors.bg} border-b ${colors.border}`}>
                  <div className="flex items-center justify-between">
                    <div>
                      <h2 className="text-lg font-semibold text-gray-800">{objective.objective}</h2>
                      <span className={`text-xs px-2 py-1 rounded-full ${colors.bg} ${colors.text} mt-1 inline-block`}>
                        {getPerspectiveLabel(objective.perspective)}
                      </span>
                    </div>
                    
                    <div className="text-sm text-gray-600">
                      Period: {selectedPeriod}
                    </div>
                  </div>
                </div>
                
                <div className="p-4">
                  <div className="overflow-x-auto">
                    <table className="min-w-full divide-y divide-gray-200">
                      <thead>
                        <tr>
                          <th className="px-4 py-3 text-left text-xs font-medium text-gray-500 uppercase tracking-wider">
                            Key Result
                          </th>
                          <th className="px-4 py-3 text-left text-xs font-medium text-gray-500 uppercase tracking-wider">
                            Target
                          </th>
                          <th className="px-4 py-3 text-left text-xs font-medium text-gray-500 uppercase tracking-wider">
                            Current
                          </th>
                          <th className="px-4 py-3 text-left text-xs font-medium text-gray-500 uppercase tracking-wider">
                            Progress
                          </th>
                          <th className="px-4 py-3 text-left text-xs font-medium text-gray-500 uppercase tracking-wider">
                            Trend
                          </th>
                          {canEdit && (
                            <th className="px-4 py-3 text-left text-xs font-medium text-gray-500 uppercase tracking-wider">
                              Update
                            </th>
                          )}
                        </tr>
                      </thead>
                      <tbody className="bg-white divide-y divide-gray-200">
                        {objective.keyResults.map((kr) => {
                          const periodData = kr.periods.find(p => p.period === selectedPeriod);
                          const currentValue = periodData ? periodData.value : 0;
                          const progress = calculateProgress(currentValue, kr.target);
                          
                          return (
                            <tr key={kr.id}>
                              <td className="px-4 py-4 whitespace-nowrap">
                                <div className="text-sm font-medium text-gray-900">{kr.name}</div>
                              </td>
                              <td className="px-4 py-4 whitespace-nowrap">
                                <div className="text-sm text-gray-900">{kr.target}{kr.unit}</div>
                              </td>
                              <td className="px-4 py-4 whitespace-nowrap">
                                <div className="text-sm text-gray-900">{currentValue}{kr.unit}</div>
                              </td>
                              <td className="px-4 py-4 whitespace-nowrap">
                                <div className="w-full bg-gray-200 rounded-full h-2.5 w-32">
                                  <div 
                                    className={`${colors.progress} h-2.5 rounded-full`} 
                                    style={{ width: `${progress}%` }}
                                  ></div>
                                </div>
                                <div className="text-xs text-gray-500 mt-1">{Math.round(progress)}%</div>
                              </td>
                              <td className="px-4 py-4 whitespace-nowrap">
                                <div className="text-sm text-gray-900">
                                  {getTrendIcon(kr)}
                                </div>
                              </td>
                              {canEdit && (
                                <td className="px-4 py-4 whitespace-nowrap">
                                  <input
                                    type="number"
                                    value={currentValue}
                                    onChange={(e) => handleUpdatePerformance(
                                      objective.id,
                                      kr.id,
                                      selectedPeriod,
                                      Number(e.target.value)
                                    )}
                                    className="w-20 p-1 border border-gray-300 rounded-md text-sm"
                                    min="0"
                                    max={kr.unit === '%' ? 100 : undefined}
                                  />
                                </td>
                              )}
                            </tr>
                          );
                        })}
                      </tbody>
                    </table>
                  </div>
                </div>
              </div>
            );
          })}
        </div>
      )}

      {selectedView === 'bar' && (
        <div className="bg-white rounded-lg shadow p-6">
          <h2 className="text-lg font-semibold text-gray-800 mb-4">
            Current vs Target Performance
          </h2>
          <div className="h-96">
            <Bar 
              data={barChartData} 
              options={{
                responsive: true,
                maintainAspectRatio: false,
                scales: {
                  y: {
                    beginAtZero: true
                  }
                },
                plugins: {
                  legend: {
                    position: 'top',
                  },
                  title: {
                    display: true,
                    text: `Performance for ${selectedPeriod}`
                  }
                }
              }} 
            />
          </div>
        </div>
      )}

      {selectedView === 'line' && (
        <div className="bg-white rounded-lg shadow p-6">
          <h2 className="text-lg font-semibold text-gray-800 mb-4">
            Performance Trends Over Time
          </h2>
          <div className="h-96">
            <Line 
              data={lineChartData} 
              options={{
                responsive: true,
                maintainAspectRatio: false,
                scales: {
                  y: {
                    beginAtZero: true
                  }
                },
                plugins: {
                  legend: {
                    position: 'top',
                  },
                  title: {
                    display: true,
                    text: 'Quarterly Performance Trends'
                  }
                }
              }} 
            />
          </div>
        </div>
      )}

      {selectedView === 'doughnut' && (
        <div className="bg-white rounded-lg shadow p-6">
          <h2 className="text-lg font-semibold text-gray-800 mb-4">
            Objective Achievement Percentage
          </h2>
          <div className="h-96 flex items-center justify-center">
            <div className="w-3/4 h-full">
              <Doughnut 
                data={doughnutChartData} 
                options={{
                  responsive: true,
                  maintainAspectRatio: false,
                  plugins: {
                    legend: {
                      position: 'right',
                    },
                    title: {
                      display: true,
                      text: `Achievement Percentage for ${selectedPeriod}`
                    }
                  }
                }} 
              />
            </div>
          </div>
        </div>
      )}

      <div className="bg-blue-50 border border-blue-200 rounded-lg p-6">
        <h2 className="text-lg font-medium text-blue-800">Performance Monitoring Tips</h2>
        <ul className="mt-3 space-y-2 text-blue-700">
          <li className="flex items-start">
            <span className="mr-2">•</span>
            <span>Update performance data regularly to ensure accurate reporting</span>
          </li>
          <li className="flex items-start">
            <span className="mr-2">•</span>
            <span>Use trend analysis to identify patterns and areas for improvement</span>
          </li>
          <li className="flex items-start">
            <span className="mr-2">•</span>
            <span>Focus on underperforming areas and develop action plans to address them</span>
          </li>
          <li className="flex items-start">
            <span className="mr-2">•</span>
            <span>Share performance data with stakeholders to promote transparency and accountability</span>
          </li>
        </ul>
      </div>
    </div>
  );
};

export default PerformanceMonitoring;