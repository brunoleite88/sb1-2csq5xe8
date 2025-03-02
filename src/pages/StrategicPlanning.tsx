import React from 'react';
import { Link } from 'react-router-dom';
import { 
  BarChart, 
  PieChart, 
  TrendingUp, 
  Target, 
  Activity,
  FileText,
  ArrowRight,
  CheckCircle
} from 'lucide-react';

const StrategicPlanning: React.FC = () => {
  // Sample data for the strategic planning process
  const planningSteps = [
    { 
      id: 1, 
      name: 'PESTAL Analysis', 
      description: 'Analyze Political, Economic, Social, Technological, Environmental, and Legal factors',
      icon: <BarChart size={24} />,
      path: '/pestalanalysis',
      status: 'completed'
    },
    { 
      id: 2, 
      name: 'SWOT Analysis', 
      description: 'Identify Strengths, Weaknesses, Opportunities, and Threats',
      icon: <PieChart size={24} />,
      path: '/swotanalysis',
      status: 'completed'
    },
    { 
      id: 3, 
      name: 'Scenario Planning', 
      description: 'Develop possible future scenarios based on SWOT analysis',
      icon: <TrendingUp size={24} />,
      path: '/scenarios',
      status: 'in-progress'
    },
    { 
      id: 4, 
      name: 'BSC Objectives', 
      description: 'Define strategic objectives using the Balanced Scorecard framework',
      icon: <Target size={24} />,
      path: '/bscobjectives',
      status: 'not-started'
    },
    { 
      id: 5, 
      name: 'OKRs & KPIs', 
      description: 'Set Objectives and Key Results with Key Performance Indicators',
      icon: <Activity size={24} />,
      path: '/okrskpis',
      status: 'not-started'
    },
    { 
      id: 6, 
      name: 'Generate Strategic Plan', 
      description: 'Export the complete strategic plan as a PDF document',
      icon: <FileText size={24} />,
      path: '/reports',
      status: 'not-started'
    }
  ];

  return (
    <div className="space-y-6">
      <div>
        <h1 className="text-2xl font-bold text-gray-800">Strategic Planning Process</h1>
        <p className="text-gray-600 mt-1">
          Follow these steps to create a comprehensive strategic plan for your organization
        </p>
      </div>

      <div className="bg-white rounded-lg shadow p-6">
        <div className="mb-6">
          <h2 className="text-lg font-medium text-gray-800">Current Plan</h2>
          <div className="mt-2 flex items-center">
            <div className="w-full bg-gray-200 rounded-full h-2.5">
              <div className="bg-blue-600 h-2.5 rounded-full" style={{ width: '30%' }}></div>
            </div>
            <span className="ml-4 text-sm font-medium text-gray-600">30% Complete</span>
          </div>
        </div>

        <div className="space-y-6">
          {planningSteps.map((step) => (
            <div 
              key={step.id} 
              className={`border rounded-lg p-4 ${
                step.status === 'completed' ? 'border-green-200 bg-green-50' : 
                step.status === 'in-progress' ? 'border-blue-200 bg-blue-50' : 
                'border-gray-200'
              }`}
            >
              <div className="flex items-start">
                <div className={`p-2 rounded-full mr-4 ${
                  step.status === 'completed' ? 'bg-green-100 text-green-600' : 
                  step.status === 'in-progress' ? 'bg-blue-100 text-blue-600' : 
                  'bg-gray-100 text-gray-600'
                }`}>
                  {step.icon}
                </div>
                <div className="flex-1">
                  <div className="flex justify-between items-center">
                    <h3 className="text-lg font-medium text-gray-800">{step.name}</h3>
                    <div className="flex items-center">
                      {step.status === 'completed' && (
                        <span className="flex items-center text-sm text-green-600 mr-2">
                          <CheckCircle size={16} className="mr-1" /> Completed
                        </span>
                      )}
                      {step.status === 'in-progress' && (
                        <span className="text-sm text-blue-600 mr-2">In Progress</span>
                      )}
                      <Link 
                        to={step.path}
                        className={`inline-flex items-center px-3 py-1.5 rounded-md text-sm font-medium ${
                          step.status === 'not-started' && step.id > 3 
                            ? 'bg-gray-100 text-gray-400 cursor-not-allowed' 
                            : 'bg-blue-100 text-blue-600 hover:bg-blue-200'
                        }`}
                        onClick={(e) => {
                          if (step.status === 'not-started' && step.id > 3) {
                            e.preventDefault();
                          }
                        }}
                      >
                        {step.status === 'completed' ? 'Review' : 
                         step.status === 'in-progress' ? 'Continue' : 'Start'}
                        <ArrowRight size={16} className="ml-1" />
                      </Link>
                    </div>
                  </div>
                  <p className="text-gray-600 mt-1">{step.description}</p>
                </div>
              </div>
            </div>
          ))}
        </div>
      </div>

      <div className="bg-blue-50 border border-blue-200 rounded-lg p-6">
        <h2 className="text-lg font-medium text-blue-800">Strategic Planning Tips</h2>
        <ul className="mt-3 space-y-2 text-blue-700">
          <li className="flex items-start">
            <span className="mr-2">•</span>
            <span>Involve key stakeholders from different departments in the planning process</span>
          </li>
          <li className="flex items-start">
            <span className="mr-2">•</span>
            <span>Ensure your objectives are SMART: Specific, Measurable, Achievable, Relevant, and Time-bound</span>
          </li>
          <li className="flex items-start">
            <span className="mr-2">•</span>
            <span>Regularly review and update your strategic plan as conditions change</span>
          </li>
          <li className="flex items-start">
            <span className="mr-2">•</span>
            <span>Align your strategic objectives with your organization's mission and vision</span>
          </li>
        </ul>
      </div>
    </div>
  );
};

export default StrategicPlanning;