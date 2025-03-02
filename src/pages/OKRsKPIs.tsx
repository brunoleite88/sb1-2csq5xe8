import React, { useState } from 'react';
import { Save, Edit2, Trash2, PlusCircle, Target, BarChart3 } from 'lucide-react';
import { useAuth } from '../contexts/AuthContext';

interface KeyResult {
  id: string;
  text: string;
  target: number;
  unit: string;
  current: number;
}

interface Initiative {
  id: string;
  text: string;
  status: 'not-started' | 'in-progress' | 'completed' | 'at-risk';
  dueDate: string;
  owner: string;
}

interface OKR {
  id: string;
  objective: string;
  description: string;
  perspective: 'financial' | 'customer' | 'internal' | 'learning';
  keyResults: KeyResult[];
  initiatives: Initiative[];
}

const OKRsKPIs: React.FC = () => {
  const { isManager, isAdmin } = useAuth();
  const canEdit = isManager || isAdmin;
  
  const [okrs, setOkrs] = useState<OKR[]>([
    {
      id: 'okr1',
      objective: 'Optimize Resource Allocation',
      description: 'Improve budget execution and resource allocation to maximize value delivery.',
      perspective: 'financial',
      keyResults: [
        {
          id: 'kr1',
          text: 'Increase budget execution rate',
          target: 95,
          unit: '%',
          current: 78
        },
        {
          id: 'kr2',
          text: 'Reduce operational costs',
          target: 15,
          unit: '%',
          current: 8
        },
        {
          id: 'kr3',
          text: 'Implement zero-based budgeting',
          target: 100,
          unit: '%',
          current: 60
        }
      ],
      initiatives: [
        {
          id: 'in1',
          text: 'Conduct monthly budget reviews',
          status: 'in-progress',
          dueDate: '2025-12-31',
          owner: 'Finance Department'
        },
        {
          id: 'in2',
          text: 'Implement cost-saving measures',
          status: 'in-progress',
          dueDate: '2025-06-30',
          owner: 'All Departments'
        }
      ]
    },
    {
      id: 'okr2',
      objective: 'Increase Citizen Satisfaction',
      description: 'Enhance service quality and accessibility to improve citizen satisfaction.',
      perspective: 'customer',
      keyResults: [
        {
          id: 'kr4',
          text: 'Improve citizen satisfaction score',
          target: 85,
          unit: '%',
          current: 65
        },
        {
          id: 'kr5',
          text: 'Reduce service response time',
          target: 50,
          unit: '%',
          current: 30
        },
        {
          id: 'kr6',
          text: 'Increase digital service adoption',
          target: 70,
          unit: '%',
          current: 45
        }
      ],
      initiatives: [
        {
          id: 'in3',
          text: 'Launch citizen feedback system',
          status: 'completed',
          dueDate: '2025-03-31',
          owner: 'Customer Service'
        },
        {
          id: 'in4',
          text: 'Implement service level agreements',
          status: 'in-progress',
          dueDate: '2025-06-30',
          owner: 'Operations'
        },
        {
          id: 'in5',
          text: 'Develop mobile app for services',
          status: 'not-started',
          dueDate: '2025-12-31',
          owner: 'IT Department'
        }
      ]
    },
    {
      id: 'okr3',
      objective: 'Streamline Internal Processes',
      description: 'Redesign and optimize key processes to improve efficiency and reduce waste.',
      perspective: 'internal',
      keyResults: [
        {
          id: 'kr7',
          text: 'Reduce process cycle time',
          target: 40,
          unit: '%',
          current: 25
        },
        {
          id: 'kr8',
          text: 'Increase process automation',
          target: 60,
          unit: '%',
          current: 35
        },
        {
          id: 'kr9',
          text: 'Reduce error rates in key processes',
          target: 80,
          unit: '%',
          current: 45
        }
      ],
      initiatives: [
        {
          id: 'in6',
          text: 'Process mapping and optimization',
          status: 'in-progress',
          dueDate: '2025-09-30',
          owner: 'Process Improvement Team'
        },
        {
          id: 'in7',
          text: 'Implement workflow automation',
          status: 'at-risk',
          dueDate: '2025-08-31',
          owner: 'IT Department'
        }
      ]
    }
  ]);

  const [editingOKR, setEditingOKR] = useState<OKR | null>(null);
  const [editingKR, setEditingKR] = useState<{
    okrIndex: number;
    krIndex: number;
    keyResult: KeyResult;
  } | null>(null);
  const [editingInitiative, setEditingInitiative] = useState<{
    okrIndex: number;
    initiativeIndex: number;
    initiative: Initiative;
  } | null>(null);
  
  const [isAddingNewOKR, setIsAddingNewOKR] = useState(false);
  const [isAddingNewKR, setIsAddingNewKR] = useState<{
    okrIndex: number;
  } | null>(null);
  const [isAddingNewInitiative, setIsAddingNewInitiative] = useState<{
    okrIndex: number;
  } | null>(null);
  
  const [newOKR, setNewOKR] = useState<Omit<OKR, 'id'>>({
    objective: '',
    description: '',
    perspective: 'financial',
    keyResults: [],
    initiatives: []
  });
  
  const [newKR, setNewKR] = useState<Omit<KeyResult, 'id'>>({
    text: '',
    target: 100,
    unit: '%',
    current: 0
  });
  
  const [newInitiative, setNewInitiative] = useState<Omit<Initiative, 'id'>>({
    text: '',
    status: 'not-started',
    dueDate: new Date().toISOString().split('T')[0],
    owner: ''
  });

  const handleEditOKR = (okr: OKR) => {
    setEditingOKR({...okr});
  };

  const handleSaveOKR = () => {
    if (editingOKR) {
      const updatedOKRs = okrs.map(o => 
        o.id === editingOKR.id ? editingOKR : o
      );
      setOkrs(updatedOKRs);
      setEditingOKR(null);
    }
  };

  const handleDeleteOKR = (id: string) => {
    setOkrs(okrs.filter(o => o.id !== id));
  };

  const handleAddNewOKR = () => {
    setIsAddingNewOKR(true);
  };

  const handleSaveNewOKR = () => {
    if (newOKR.objective.trim()) {
      const newId = `okr${Date.now()}`;
      setOkrs([...okrs, { id: newId, ...newOKR }]);
      setIsAddingNewOKR(false);
      setNewOKR({
        objective: '',
        description: '',
        perspective: 'financial',
        keyResults: [],
        initiatives: []
      });
    }
  };

  const handleCancelNewOKR = () => {
    setIsAddingNewOKR(false);
    setNewOKR({
      objective: '',
      description: '',
      perspective: 'financial',
      keyResults: [],
      initiatives: []
    });
  };

  const handleEditKR = (okrIndex: number, krIndex: number) => {
    setEditingKR({
      okrIndex,
      krIndex,
      keyResult: {...okrs[okrIndex].keyResults[krIndex]}
    });
  };

  const handleSaveKR = () => {
    if (editingKR) {
      const updatedOKRs = [...okrs];
      updatedOKRs[editingKR.okrIndex].keyResults[editingKR.krIndex] = editingKR.keyResult;
      setOkrs(updatedOKRs);
      setEditingKR(null);
    }
  };

  const handleDeleteKR = (okrIndex: number, krIndex: number) => {
    const updatedOKRs = [...okrs];
    updatedOKRs[okrIndex].keyResults.splice(krIndex, 1);
    setOkrs(updatedOKRs);
  };

  const handleAddNewKR = (okrIndex: number) => {
    setIsAddingNewKR({ okrIndex });
    setNewKR({
      text: '',
      target: 100,
      unit: '%',
      current: 0
    });
  };

  const handleSaveNewKR = () => {
    if (isAddingNewKR && newKR.text.trim()) {
      const updatedOKRs = [...okrs];
      updatedOKRs[isAddingNewKR.okrIndex].keyResults.push({
        id: `kr${Date.now()}`,
        ...newKR
      });
      setOkrs(updatedOKRs);
      setIsAddingNewKR(null);
      setNewKR({
        text: '',
        target: 100,
        unit: '%',
        current: 0
      });
    }
  };

  const handleEditInitiative = (okrIndex: number, initiativeIndex: number) => {
    setEditingInitiative({
      okrIndex,
      initiativeIndex,
      initiative: {...okrs[okrIndex].initiatives[initiativeIndex]}
    });
  };

  const handleSaveInitiative = () => {
    if (editingInitiative) {
      const updatedOKRs = [...okrs];
      updatedOKRs[editingInitiative.okrIndex].initiatives[editingInitiative.initiativeIndex] = editingInitiative.initiative;
      setOkrs(updatedOKRs);
      setEditingInitiative(null);
    }
  };

  const handleDeleteInitiative = (okrIndex: number, initiativeIndex: number) => {
    const updatedOKRs = [...okrs];
    updatedOKRs[okrIndex].initiatives.splice(initiativeIndex, 1);
    setOkrs(updatedOKRs);
  };

  const handleAddNewInitiative = (okrIndex: number) => {
    setIsAddingNewInitiative({ okrIndex });
    setNewInitiative({
      text: '',
      status: 'not-started',
      dueDate: new Date().toISOString().split('T')[0],
      owner: ''
    });
  };

  const handleSaveNewInitiative = () => {
    if (isAddingNewInitiative && newInitiative.text.trim()) {
      const updatedOKRs = [...okrs];
      updatedOKRs[isAddingNewInitiative.okrIndex].initiatives.push({
        id: `in${Date.now()}`,
        ...newInitiative
      });
      setOkrs(updatedOKRs);
      setIsAddingNewInitiative(null);
      setNewInitiative({
        text: '',
        status: 'not-started',
        dueDate: new Date().toISOString().split('T')[0],
        owner: ''
      });
    }
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

  const getStatusColor = (status: string) => {
    switch (status) {
      case 'completed':
        return 'bg-green-100 text-green-800';
      case 'in-progress':
        return 'bg-blue-100 text-blue-800';
      case 'not-started':
        return 'bg-gray-100 text-gray-800';
      case 'at-risk':
        return 'bg-red-100 text-red-800';
      default:
        return 'bg-gray-100 text-gray-800';
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

  const renderOKRForm = () => {
    return (
      <div className="bg-white rounded-lg shadow-lg p-6 mb-6 border border-blue-200">
        <h3 className="text-xl font-semibold text-gray-800 mb-4">
          Add New OKR
        </h3>
        
        <div className="space-y-4">
          <div>
            <label className="block text-sm font-medium text-gray-700 mb-1">Objective</label>
            <input
              type="text"
              value={newOKR.objective}
              onChange={(e) => setNewOKR({...newOKR, objective: e.target.value})}
              className="w-full p-2 border border-gray-300 rounded-md"
              placeholder="Enter objective"
            />
          </div>
          
          <div>
            <label className="block text-sm font-medium text-gray-700 mb-1">Description</label>
            <textarea
              value={newOKR.description}
              onChange={(e) => setNewOKR({...newOKR, description: e.target.value})}
              className="w-full p-2 border border-gray-300 rounded-md"
              rows={2}
              placeholder="Describe the objective"
            />
          </div>
          
          <div>
            <label className="block text-sm font-medium text-gray-700 mb-1">BSC Perspective</label>
            <select
              value={newOKR.perspective}
              onChange={(e) => setNewOKR({...newOKR, perspective: e.target.value as any})}
              className="w-full p-2 border border-gray-300 rounded-md"
            >
              <option value="financial">Financial</option>
              <option value="customer">Customer</option>
              <option value="internal">Internal Processes</option>
              <option value="learning">Learning & Growth</option>
            </select>
          </div>
          
          <div className="flex justify-end space-x-3 pt-2">
            <button
              onClick={handleCancelNewOKR}
              className="px-4 py-2 border border-gray-300 rounded-md text-gray-700 hover:bg-gray-50"
            >
              Cancel
            </button>
            <button
              onClick={handleSaveNewOKR}
              className="px-4 py-2 bg-blue-600 text-white rounded-md hover:bg-blue-700"
            >
              Save
            </button>
          </div>
        </div>
      </div>
    );
  };

  const renderKRForm = () => {
    if (!isAddingNewKR) return null;
    
    return (
      <div className="bg-white rounded-lg p-4 border border-blue-200 mb-3">
        <h4 className="text-sm font-medium text-gray-700 mb-2">Add New Key Result</h4>
        
        <div className="space-y-3">
          <div>
            <label className="block text-xs text-gray-600 mb-1">Description</label>
            <input
              type="text"
              value={newKR.text}
              onChange={(e) => setNewKR({...newKR, text: e.target.value})}
              className="w-full p-2 border border-gray-300 rounded-md text-sm"
              placeholder="Enter key result"
            />
          </div>
          
          <div className="grid grid-cols-3 gap-2">
            <div>
              <label className="block text-xs text-gray-600 mb-1">Target</label>
              <input
                type="number"
                value={newKR.target}
                onChange={(e) => setNewKR({...newKR, target: Number(e.target.value)})}
                className="w-full p-2 border border-gray-300 rounded-md text-sm"
              />
            </div>
            
            <div>
              <label className="block text-xs text-gray-600 mb-1">Current</label>
              <input
                type="number"
                value={newKR.current}
                onChange={(e) => setNewKR({...newKR, current: Number(e.target.value)})}
                className="w-full p-2 border border-gray-300 rounded-md text-sm"
              />
            </div>
            
            <div>
              <label className="block text-xs text-gray-600 mb-1">Unit</label>
              <select
                value={newKR.unit}
                onChange={(e) => setNewKR({...newKR, unit: e.target.value})}
                className="w-full p-2 border border-gray-300 rounded-md text-sm"
              >
                <option value="%">%</option>
                <option value="$">$</option>
                <option value="#">#</option>
                <option value="days">days</option>
                <option value="hours">hours</option>
              </select>
            </div>
          </div>
          
          <div className="flex justify-end space-x-2">
            <button
              onClick={() => setIsAddingNewKR(null)}
              className="px-3 py-1 border border-gray-300 rounded-md text-gray-700 hover:bg-gray-50 text-sm"
            >
              Cancel
            </button>
            <button
              onClick={handleSaveNewKR}
              className="px-3 py-1 bg-blue-600 text-white rounded-md hover:bg-blue-700 text-sm"
            >
              Save
            </button>
          </div>
        </div>
      </div>
    );
  };

  const renderInitiativeForm = () => {
    if (!isAddingNewInitiative) return null;
    
    return (
      <div className="bg-white rounded-lg p-4 border border-blue-200 mb-3">
        <h4 className="text-sm font-medium text-gray-700 mb-2">Add New Initiative</h4>
        
        <div className="space-y-3">
          <div>
            <label className="block text-xs text-gray-600 mb-1">Description</label>
            <input
              type="text"
              value={newInitiative.text}
              onChange={(e) => setNewInitiative({...newInitiative, text: e.target.value})}
              className="w-full p-2 border border-gray-300 rounded-md text-sm"
              placeholder="Enter initiative"
            />
          </div>
          
          <div className="grid grid-cols-3 gap-2">
            <div>
              <label className="block text-xs text-gray-600 mb-1">Status</label>
              <select
                value={newInitiative.status}
                onChange={(e) => setNewInitiative({...newInitiative, status: e.target.value as any})}
                className="w-full p-2 border border-gray-300 rounded-md text-sm"
              >
                <option value="not-started">Not Started</option>
                <option value="in-progress">In Progress</option>
                <option value="completed">Completed</option>
                <option value="at-risk">At Risk</option>
              </select>
            </div>
            
            <div>
              <label className="block text-xs text-gray-600 mb-1">Due Date</label>
              <input
                type="date"
                value={newInitiative.dueDate}
                onChange={(e) => setNewInitiative({...newInitiative, dueDate: e.target.value})}
                className="w-full p-2 border border-gray-300 rounded-md text-sm"
              />
            </div>
            
            <div>
              <label className="block text-xs text-gray-600 mb-1">Owner</label>
              <input
                type="text"
                value={newInitiative.owner}
                onChange={(e) => setNewInitiative({...newInitiative, owner: e.target.value})}
                className="w-full p-2 border border-gray-300 rounded-md text-sm"
                placeholder="Owner"
              />
            </div>
          </div>
          
          <div className="flex justify-end space-x-2">
            <button
              onClick={() => setIsAddingNewInitiative(null)}
              className="px-3 py-1 border border-gray-300 rounded-md text-gray-700 hover:bg-gray-50 text-sm"
            >
              Cancel
            </button>
            <button
              onClick={handleSaveNewInitiative}
              className="px-3 py-1 bg-blue-600 text-white rounded-md hover:bg-blue-700 text-sm"
            >
              Save
            </button>
          </div>
        </div>
      </div>
    );
  };

  return (
    <div className="space-y-6">
      <div>
        <h1 className="text-2xl font-bold text-gray-800">OKRs & KPIs</h1>
        <p className="text-gray-600 mt-1">
          Define Objectives and Key Results with Key Performance Indicators
        </p>
      </div>

      {isAddingNewOKR && renderOKRForm()}

      {canEdit && !isAddingNewOKR && (
        <button
          onClick={handleAddNewOKR}
          className="mb-6 px-4 py-2 bg-blue-600 text-white rounded-md hover:bg-blue-700 flex items-center"
        >
          <PlusCircle size={18} className="mr-2" /> Add New OKR
        </button>
      )}

      <div className="space-y-8">
        {okrs.map((okr, okrIndex) => {
          const colors = getPerspectiveColor(okr.perspective);
          
          // Calculate overall progress
          const totalProgress = okr.keyResults.reduce((sum, kr) => {
            return sum + calculateProgress(kr.current, kr.target);
          }, 0);
          const averageProgress = okr.keyResults.length > 0 
            ? totalProgress / okr.keyResults.length 
            : 0;
          
          return (
            <div 
              key={okr.id} 
              className="bg-white rounded-lg shadow overflow-hidden"
            >
              <div className={`p-4 ${colors.bg} border-b ${colors.border}`}>
                <div className="flex justify-between items-start">
                  <div>
                    <div className="flex items-center">
                      <h2 className="text-xl font-semibold text-gray-800">{okr.objective}</h2>
                      <span className={`ml-3 text-xs px-2 py-1 rounded-full ${colors.bg} ${colors.text}`}>
                        {getPerspectiveLabel(okr.perspective)}
                      </span>
                    </div>
                    <p className="text-gray-600 mt-1">{okr.description}</p>
                  </div>
                  
                  {canEdit && (
                    <div className="flex space-x-2">
                      <button
                        onClick={() => handleEditOKR(okr)}
                        className="p-1 text-gray-500 hover:text-blue-600"
                      >
                        <Edit2 size={18} />
                      </button>
                      <button
                        onClick={() => handleDeleteOKR(okr.id)}
                        className="p-1 text-gray-500 hover:text-red-600"
                      >
                        <Trash2 size={18} />
                      </button>
                    </div>
                  )}
                </div>
                
                <div className="mt-3">
                  <div className="flex justify-between text-sm mb-1">
                    <span>Overall Progress</span>
                    <span>{Math.round(averageProgress)}%</span>
                  </div>
                  <div className="w-full bg-gray-200 rounded-full h-2.5">
                    <div 
                      className={`${colors.progress} h-2.5 rounded-full`} 
                      style={{ width: `${averageProgress}%` }}
                    ></div>
                  </div>
                </div>
              </div>
              
              <div className="p-4">
                <div className="mb-6">
                  <div className="flex justify-between items-center mb-3">
                    <h3 className="text-lg font-medium text-gray-800 flex items-center">
                      <Target size={18} className="mr-2 text-blue-600" /> Key Results
                    </h3>
                    {canEdit && (
                      <button
                        onClick={() => handleAddNewKR(okrIndex)}
                        className="text-sm text-blue-600 hover:text-blue-800 flex items-center"
                      >
                        <PlusCircle size={14} className="mr-1" /> Add Key Result
                      </button>
                    )}
                  </div>
                  
                  {isAddingNewKR && isAddingNewKR.okrIndex === okrIndex && renderKRForm()}
                  
                  {okr.keyResults.length === 0 ? (
                    <p className="text-gray-500 text-center py-2">No key results defined</p>
                  ) : (
                    <div className="space-y-3">
                      {okr.keyResults.map((kr, krIndex) => (
                        <div 
                          key={kr.id} 
                          className="p-3 border rounded-lg"
                        >
                          {editingKR && 
                           editingKR.okrIndex === okrIndex && 
                           editingKR.krIndex === krIndex ? (
                            <div className="space-y-3">
                              <div>
                                <label className="block text-xs text-gray-600 mb-1">Description</label>
                                <input
                                  type="text"
                                  value={editingKR.keyResult.text}
                                  onChange={(e) => setEditingKR({
                                    ...editingKR, 
                                    keyResult: {...editingKR.keyResult, text: e.target.value}
                                  })}
                                  className="w-full p-2 border border-gray-300 rounded-md text-sm"
                                />
                              </div>
                              
                              <div className="grid grid-cols-3 gap-2">
                                <div>
                                  <label className="block text-xs text-gray-600 mb-1">Target</label>
                                  <input
                                    type="number"
                                    value={editingKR.keyResult.target}
                                    onChange={(e) => setEditingKR({
                                      ...editingKR, 
                                      keyResult: {...editingKR.keyResult, target: Number(e.target.value)}
                                    })}
                                    className="w-full p-2 border border-gray-300 rounded-md text-sm"
                                  />
                                </div>
                                
                                <div>
                                  <label className="block text-xs text-gray-600 mb-1">Current</label>
                                  <input
                                    type="number"
                                    value={editingKR.keyResult.current}
                                    onChange={(e) => setEditingKR({
                                      ...editingKR, 
                                      keyResult: {...editingKR.keyResult, current: Number(e.target.value)}
                                    })}
                                    className="w-full p-2 border border-gray-300 rounded-md text-sm"
                                  />
                                </div>
                                
                                <div>
                                  <label className="block text-xs text-gray-600 mb-1">Unit</label>
                                  <select
                                    value={editingKR.keyResult.unit}
                                    onChange={(e) => setEditingKR({
                                      ...editingKR, 
                                      keyResult: {...editingKR.keyResult, unit: e.target.value}
                                    })}
                                    className="w-full p-2 border border-gray-300 rounded-md text-sm"
                                  >
                                    <option value="%">%</option>
                                    <option value="$">$</option>
                                    <option value="#">#</option>
                                    <option value="days">days</option>
                                    <option value="hours">hours</option>
                                  </select>
                                </div>
                              </div>
                              
                              <div className="flex justify-end space-x-2">
                                <button
                                  onClick={() => setEditingKR(null)}
                                  className="px-3 py-1 border border-gray-300 rounded-md text-gray-700 hover:bg-gray-50 text-sm"
                                >
                                  Cancel
                                </button>
                                <button
                                  onClick={handleSaveKR}
                                  className="px-3 py-1 bg-blue-600 text-white rounded-md hover:bg-blue-700 text-sm"
                                >
                                  Save
                                </button>
                              </div>
                            </div>
                          ) : (
                            <div>
                              <div className="flex justify-between items-start">
                                <div className="flex-1">
                                  <p className="font-medium text-gray-800">{kr.text}</p>
                                  <div className="flex items-center mt-2 text-sm text-gray-600">
                                    <span>Current: {kr.current}{kr.unit}</span>
                                    <span className="mx-2">|</span>
                                    <span>Target: {kr.target}{kr.unit}</span>
                                  </div>
                                </div>
                                
                                {canEdit && (
                                  <div className="flex space-x-1 ml-2">
                                    <button
                                      onClick={() => handleEditKR(okrIndex, krIndex)}
                                      className="p-1 text-gray-500 hover:text-blue-600"
                                    >
                                      <Edit2 size={16} />
                                    </button>
                                    <button
                                      onClick={() => handleDeleteKR(okrIndex, krIndex)}
                                      className="p-1 text-gray-500 hover:text-red-600"
                                    >
                                      <Trash2 size={16} />
                                    </button>
                                  </div>
                                )}
                              </div>
                              
                              <div className="mt-2">
                                <div className="flex justify-between text-xs mb-1">
                                  <span>Progress</span>
                                  <span>{Math.round(calculateProgress(kr.current, kr.target))}%</span>
                                </div>
                                <div className="w-full bg-gray-200 rounded-full h-1.5">
                                  <div 
                                    className={`${colors.progress} h-1.5 rounded-full`} 
                                    style={{ width: `${calculateProgress(kr.current, kr.target)}%` }}
                                  ></div>
                                </div>
                              </div>
                            </div>
                          )}
                        </div>
                      ))}
                    </div>
                  )}
                </div>
                
                <div>
                  <div className="flex justify-between items-center mb-3">
                    <h3 className="text-lg font-medium text-gray-800 flex items-center">
                      <BarChart3 size={18} className="mr-2 text-blue-600" /> Initiatives
                    </h3>
                    {canEdit && (
                      <button
                        onClick={() => handleAddNewInitiative(okrIndex)}
                        className="text-sm text-blue-600 hover:text-blue-800 flex items-center"
                      >
                        <PlusCircle size={14} className="mr-1" /> Add Initiative
                      </button>
                    )}
                  </div>
                  
                  {isAddingNewInitiative && isAddingNewInitiative.okrIndex === okrIndex && renderInitiativeForm()}
                  
                  {okr.initiatives.length === 0 ? (
                    <p className="text-gray-500 text-center py-2">No initiatives defined</p>
                  ) : (
                    <div className="space-y-3">
                      {okr.initiatives.map((initiative, initiativeIndex) => (
                        <div 
                          key={initiative.id} 
                          className="p-3 border rounded-lg"
                        >
                          {editingInitiative && 
                           editingInitiative.okrIndex === okrIndex && 
                           editingInitiative.initiativeIndex === initiativeIndex ? (
                            <div className="space-y-3">
                              <div>
                                <label className="block text-xs text-gray-600 mb-1">Description</label>
                                <input
                                  type="text"
                                  value={editingInitiative.initiative.text}
                                  onChange={(e) => setEditingInitiative({
                                    ...editingInitiative, 
                                    initiative: {...editingInitiative.initiative, text: e.target.value}
                                  })}
                                  className="w-full p-2 border border-gray-300 rounded-md text-sm"
                                />
                              </div>
                              
                              <div className="grid grid-cols-3 gap-2">
                                <div>
                                  <label className="block text-xs text-gray-600 mb-1">Status</label>
                                  <select
                                    value={editingInitiative.initiative.status}
                                    onChange={(e) => setEditingInitiative({
                                      ...editingInitiative, 
                                      initiative: {...editingInitiative.initiative, status: e.target.value as any}
                                    })}
                                    className="w-full p-2 border border-gray-300 rounded-md text-sm"
                                  >
                                    <option value="not-started">Not Started</option>
                                    <option value="in-progress">In Progress</option>
                                    <option value="completed">Completed</option>
                                    <option value="at-risk">At Risk</option>
                                  </select>
                                </div>
                                
                                <div>
                                  <label className="block text-xs text-gray-600 mb-1">Due Date</label>
                                  <input
                                    type="date"
                                    value={editingInitiative.initiative.dueDate}
                                    onChange={(e) => setEditingInitiative({
                                      ...editingInitiative, 
                                      initiative: {...editingInitiative.initiative, dueDate: e.target.value}
                                    })}
                                    className="w-full p-2 border border-gray-300 rounded-md text-sm"
                                  />
                                </div>
                                
                                <div>
                                  <label className="block text-xs text-gray-600 mb-1">Owner</label>
                                  <input
                                    type="text"
                                    value={editingInitiative.initiative.owner}
                                    onChange={(e) => setEditingInitiative({
                                      ...editingInitiative, 
                                      initiative: {...editingInitiative.initiative, owner: e.target.value}
                                    })}
                                    className="w-full p-2 border border-gray-300 rounded-md text-sm"
                                  />
                                </div>
                              </div>
                              
                              <div className="flex justify-end space-x-2">
                                <button
                                  onClick={() => setEditingInitiative(null)}
                                  className="px-3 py-1 border border-gray-300 rounded-md text-gray-700 hover:bg-gray-50 text-sm"
                                >
                                  Cancel
                                </button>
                                <button
                                  onClick={handleSaveInitiative}
                                  className="px-3 py-1 bg-blue-600 text-white rounded-md hover:bg-blue-700 text-sm"
                                >
                                  Save
                                </button>
                              </div>
                            </div>
                          ) : (
                            <div className="flex justify-between items-start">
                              <div>
                                <p className="font-medium text-gray-800">{initiative.text}</p>
                                <div className="flex flex-wrap items-center mt-2 text-sm">
                                  <span className={`text-xs px-2 py-0.5 rounded-full ${getStatusColor(initiative.status)}`}>
                                    {initiative.status.split('-').map(word => 
                                      word.charAt(0).toUpperCase() + word.slice(1)
                                    ).join(' ')}
                                  </span>
                                  <span className="mx-2 text-gray-400">|</span>
                                  <span className="text-gray-600">Due: {new Date(initiative.dueDate).toLocaleDateString()}</span>
                                  <span className="mx-2 text-gray-400">|</span>
                                  <span className="text-gray-600">Owner: {initiative.owner}</span>
                                </div>
                              </div>
                              
                              {canEdit && (
                                <div className="flex space-x-1 ml-2">
                                  <button
                                    onClick={() => handleEditInitiative(okrIndex, initiativeIndex)}
                                    className="p-1 text-gray-500 hover:text-blue-600"
                                  >
                                    <Edit2 size={16} />
                                  </button>
                                  <button
                                    onClick={() => handleDeleteInitiative(okrIndex, initiativeIndex)}
                                    className="p-1 text-gray-500 hover:text-red-600"
                                  >
                                    <Trash2 size={16} />
                                  </button>
                                </div>
                              )}
                            </div>
                          )}
                        </div>
                      ))}
                    </div>
                  )}
                </div>
              </div>
            </div>
          );
        })}
      </div>

      <div className="bg-blue-50 border border-blue-200 rounded-lg p-6">
        <h2 className="text-lg font-medium text-blue-800">OKRs & KPIs Tips</h2>
        <ul className="mt-3 space-y-2 text-blue-700">
          <li className="flex items-start">
            <span className="mr-2">•</span>
            <span>Keep key results measurable and time-bound</span>
          </li>
          <li className="flex items-start">
            <span className="mr-2">•</span>
            <span>Limit the number of key results per objective (3-5 is ideal)</span>
          </li>
          <li className="flex items-start">
            <span className="mr-2">•</span>
            <span>Ensure initiatives directly contribute to key results</span>
          </li>
          <li className="flex items-start">
            <span className="mr-2">•</span>
            <span>Regularly update progress and review performance</span>
          </li>
        </ul>
      </div>
    </div>
  );
};

export default OKRsKPIs;