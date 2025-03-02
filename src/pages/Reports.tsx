import React, { useState } from 'react';
import { 
  FileText, 
  Download, 
  Calendar, 
  Filter, 
  ChevronDown, 
  ChevronUp,
  Printer
} from 'lucide-react';
import { useAuth } from '../contexts/AuthContext';

interface ReportOption {
  id: string;
  name: string;
  description: string;
  type: 'strategic' | 'performance' | 'operational';
}

const Reports: React.FC = () => {
  const { currentUser } = useAuth();
  
  const [selectedReportType, setSelectedReportType] = useState<string>('all');
  const [selectedPeriod, setSelectedPeriod] = useState<string>('Q2 2025');
  const [expandedReport, setExpandedReport] = useState<string | null>(null);
  
  const reportOptions: ReportOption[] = [
    {
      id: 'report1',
      name: 'Strategic Plan',
      description: 'Complete strategic plan including PESTAL, SWOT, scenarios, objectives, and initiatives.',
      type: 'strategic'
    },
    {
      id: 'report2',
      name: 'SWOT Analysis',
      description: 'Detailed analysis of strengths, weaknesses, opportunities, and threats.',
      type: 'strategic'
    },
    {
      id: 'report3',
      name: 'Balanced Scorecard',
      description: 'Overview of strategic objectives across all four BSC perspectives.',
      type: 'strategic'
    },
    {
      id: 'report4',
      name: 'Quarterly Performance Report',
      description: 'Performance against key results for the selected quarter.',
      type: 'performance'
    },
    {
      id: 'report5',
      name: 'KPI Dashboard',
      description: 'Visual representation of key performance indicators and their status.',
      type: 'performance'
    },
    {
      id: 'report6',
      name: 'Initiative Status Report',
      description: 'Status update on all strategic initiatives and their progress.',
      type: 'operational'
    },
    {
      id: 'report7',
      name: 'Trend Analysis',
      description: 'Analysis of performance trends over multiple periods.',
      type: 'performance'
    },
    {
      id: 'report8',
      name: 'Executive Summary',
      description: 'High-level summary of strategic performance for executive leadership.',
      type: 'strategic'
    }
  ];

  // Filter reports based on selected type
  const filteredReports = selectedReportType === 'all' 
    ? reportOptions 
    : reportOptions.filter(report => report.type === selectedReportType);

  const toggleReportExpansion = (reportId: string) => {
    if (expandedReport === reportId) {
      setExpandedReport(null);
    } else {
      setExpandedReport(reportId);
    }
  };

  const getReportTypeLabel = (type: string) => {
    switch (type) {
      case 'strategic':
        return 'Strategic';
      case 'performance':
        return 'Performance';
      case 'operational':
        return 'Operational';
      default:
        return type;
    }
  };

  const getReportTypeColor = (type: string) => {
    switch (type) {
      case 'strategic':
        return 'bg-blue-100 text-blue-800';
      case 'performance':
        return 'bg-green-100 text-green-800';
      case 'operational':
        return 'bg-purple-100 text-purple-800';
      default:
        return 'bg-gray-100 text-gray-800';
    }
  };

  // Sample report history
  const reportHistory = [
    { id: 'hist1', name: 'Strategic Plan', date: '2025-04-15', user: 'Maria Silva' },
    { id: 'hist2', name: 'Quarterly Performance Report', date: '2025-04-10', user: 'João Santos' },
    { id: 'hist3', name: 'KPI Dashboard', date: '2025-04-05', user: 'Ana Oliveira' },
    { id: 'hist4', name: 'SWOT Analysis', date: '2025-03-28', user: 'Carlos Mendes' },
    { id: 'hist5', name: 'Initiative Status Report', date: '2025-03-20', user: 'Sofia Costa' }
  ];

  return (
    <div className="space-y-6">
      <div>
        <h1 className="text-2xl font-bold text-gray-800">Reports</h1>
        <p className="text-gray-600 mt-1">
          Generate and download reports for your strategic plan and performance data
        </p>
      </div>

      <div className="bg-white rounded-lg shadow p-4">
        <div className="flex flex-wrap items-center justify-between gap-4">
          <div className="flex items-center space-x-4">
            <div className="flex items-center">
              <Filter size={18} className="text-gray-500 mr-2" />
              <select
                value={selectedReportType}
                onChange={(e) => setSelectedReportType(e.target.value)}
                className="p-2 border border-gray-300 rounded-md text-sm"
              >
                <option value="all">All Report Types</option>
                <option value="strategic">Strategic</option>
                <option value="performance">Performance</option>
                <option value="operational">Operational</option>
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
        </div>
      </div>

      <div className="grid grid-cols-1 md:grid-cols-3 gap-6">
        <div className="md:col-span-2 space-y-4">
          <h2 className="text-lg font-semibold text-gray-800">Available Reports</h2>
          
          {filteredReports.map((report) => (
            <div 
              key={report.id} 
              className="bg-white rounded-lg shadow overflow-hidden"
            >
              <div 
                className="p-4 flex justify-between items-center cursor-pointer hover:bg-gray-50"
                onClick={() => toggleReportExpansion(report.id)}
              >
                <div className="flex items-start">
                  <div className="p-2 bg-blue-100 rounded-full text-blue-600 mr-3">
                    <FileText size={18} />
                  </div>
                  <div>
                    <h3 className="font-medium text-gray-800">{report.name}</h3>
                    <p className="text-sm text-gray-600">{report.description}</p>
                  </div>
                </div>
                
                <div className="flex items-center">
                  <span className={`text-xs px-2 py-1 rounded-full mr-3 ${getReportTypeColor(report.type)}`}>
                    {getReportTypeLabel(report.type)}
                  </span>
                  {expandedReport === report.id ? (
                    <ChevronUp size={18} className="text-gray-500" />
                  ) : (
                    <ChevronDown size={18} className="text-gray-500" />
                  )}
                </div>
              </div>
              
              {expandedReport === report.id && (
                <div className="p-4 border-t border-gray-200 bg-gray-50">
                  <div className="space-y-4">
                    <div>
                      <h4 className="text-sm font-medium text-gray-700 mb-2">Report Options</h4>
                      
                      <div className="grid grid-cols-1 md:grid-cols-2 gap-3">
                        <div>
                          <label className="block text-xs text-gray-600 mb-1">Period</label>
                          <select
                            className="w-full p-2 border border-gray-300 rounded-md text-sm"
                            defaultValue={selectedPeriod}
                          >
                            <option value="Q1 2025">Q1 2025</option>
                            <option value="Q2 2025">Q2 2025</option>
                            <option value="Q3 2025">Q3 2025</option>
                            <option value="Q4 2025">Q4 2025</option>
                          </select>
                        </div>
                        
                        <div>
                          <label className="block text-xs text-gray-600 mb-1">Format</label>
                          <select
                            className="w-full p-2 border border-gray-300 rounded-md text-sm"
                            defaultValue="pdf"
                          >
                            <option value="pdf">PDF</option>
                            <option value="excel">Excel</option>
                            <option value="ppt">PowerPoint</option>
                          </select>
                        </div>
                      </div>
                    </div>
                    
                    <div className="flex justify-end space-x-3">
                      <button
                        className="px-4 py-2 border border-gray-300 rounded-md text-gray-700 hover:bg-gray-100 flex items-center"
                      >
                        <Printer size={16} className="mr-2" /> Print
                      </button>
                      <button
                        className="px-4 py-2 bg-blue-600 text-white rounded-md hover:bg-blue-700 flex items-center"
                      >
                        <Download size={16} className="mr-2" /> Generate
                      </button>
                    </div>
                  </div>
                </div>
              )}
            </div>
          ))}
        </div>
        
        <div>
          <h2 className="text-lg font-semibold text-gray-800 mb-4">Recent Reports</h2>
          
          <div className="bg-white rounded-lg shadow overflow-hidden">
            <div className="p-4 border-b border-gray-200">
              <h3 className="font-medium text-gray-800">Report History</h3>
            </div>
            
            <div className="divide-y divide-gray-200">
              {reportHistory.map((history) => (
                <div key={history.id} className="p-3 hover:bg-gray-50">
                  <div className="flex justify-between items-start">
                    <div>
                      <p className="font-medium text-gray-800">{history.name}</p>
                      <p className="text-xs text-gray-500">
                        {new Date(history.date).toLocaleDateString()} • {history.user}
                      </p>
                    </div>
                    <button
                      className="p-1 text-blue-600 hover:text-blue-800"
                    >
                      <Download size={16} />
                    </button>
                  </div>
                </div>
              ))}
            </div>
            
            <div className="p-3 text-center">
              <button
                className="text-sm text-blue-600 hover:text-blue-800"
              >
                View All History
              </button>
            </div>
          </div>
          
          <div className="bg-blue-50 border border-blue-200 rounded-lg p-4 mt-6">
            <h3 className="text-sm font-medium text-blue-800 mb-2">Need a Custom Report?</h3>
            <p className="text-sm text-blue-700 mb-3">
              If you need a specialized report that's not listed here, our team can help create it for you.
            </p>
            <button
              className="w-full px-4 py-2 bg-blue-600 text-white rounded-md hover:bg-blue-700 text-sm"
            >
              Request Custom Report
            </button>
          </div>
        </div>
      </div>

      <div className="bg-blue-50 border border-blue-200 rounded-lg p-6">
        <h2 className="text-lg font-medium text-blue-800">Report Tips</h2>
        <ul className="mt-3 space-y-2 text-blue-700">
          <li className="flex items-start">
            <span className="mr-2">•</span>
            <span>Generate the Strategic Plan report to share your complete strategy with stakeholders</span>
          </li>
          <li className="flex items-start">
            <span className="mr-2">•</span>
            <span>Use the Quarterly Performance Report for regular performance reviews</span>
          </li>
          <li className="flex items-start">
            <span className="mr-2">•</span>
            <span>The Executive Summary is ideal for high-level presentations to leadership</span>
          </li>
          <li className="flex items-start">
            <span className="mr-2">•</span>
            <span>All reports can be customized by selecting different periods and formats</span>
          </li>
        </ul>
      </div>
    </div>
  );
};

export default Reports;