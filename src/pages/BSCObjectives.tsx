import React, { useState } from 'react';
import { Save, Edit2, Trash2, PlusCircle, Target } from 'lucide-react';
import { useAuth } from '../contexts/AuthContext';

interface Objective {
  id: string;
  title: string;
  description: string;
  perspective: 'financial' | 'customer' | 'internal' | 'learning';
  status: 'active' | 'completed' | 'on-hold';
}

const BSCObjectives: React.FC = () => {
  const { isManager, isAdmin } = useAuth();
  const canEdit = isManager || isAdmin;
  
  const [objectives, setObjectives] = useState<Objective[]>([
    {
      id: 'obj1',
      title: 'Optimize Resource Allocation',
      description: 'Improve budget execution and resource allocation to maximize value delivery.',
      perspective: 'financial',
      status: 'active'
    },
    {
      id: 'obj2',
      title: 'Increase Citizen Satisfaction',
      description: 'Enhance service quality and accessibility to improve citizen satisfaction.',
      perspective: 'customer',
      status: 'active'
    },
    {
      id: 'obj3',
      title: 'Streamline Internal Processes',
      description: 'Redesign and optimize key processes to improve efficiency and reduce waste.',
      perspective: 'internal',
      status: 'active'
    },
    {
      id: 'obj4',
      title: 'Develop Digital Capabilities',
      description: 'Build digital skills and infrastructure to support organizational transformation.',
      perspective: 'learning',
      status: 'active'
    },
    {
      id: 'obj5',
      title: 'Diversify Funding Sources',
      description: 'Identify and secure alternative funding sources to reduce dependency on a single revenue stream.',
      perspective: 'financial',
      status: 'on-hold'
    },
    {
      id: 'obj6',
      title: 'Enhance Transparency',
      description: 'Improve public reporting and access to information about organizational activities and performance.',
      perspective: 'customer',
      status: 'active'
    },
    {
      id: 'obj7',
      title: 'Strengthen Data Governance',
      description: 'Establish robust data management practices to ensure data quality and security.',
      perspective: 'internal',
      status: 'active'
    },
    {
      id: 'obj8',
      title: 'Foster Innovation Culture',
      description: 'Create an environment that encourages experimentation and continuous improvement.',
      perspective: 'learning',
      status: 'on-hold'
    }
  ]);

  const [editingObjective, setEditingObjective] = useState<Objective | null>(null);
  const [isAddingNew, setIsAddingNew] = useState(false);
  const [newObjective, setNewObjective] = useState<Omit<Objective, 'id'>>({
    title: '',
    description: '',
    perspective: 'financial',
    status: 'active'
  });

  const handleEditObjective = (objective: Objective) => {
    setEditingObjective({...objective});
  };

  const handleSaveEdit = () => {
    if (editingObjective) {
      const updatedObjectives = objectives.map(obj => 
        obj.id === editingObjective.id ? editingObjective : obj
      );
      setObjectives(updatedObjectives);
      setEditingObjective(null);
    }
  };

  const handleDeleteObjective = (id: string) => {
    setObjectives(objectives.filter(obj => obj.id !== id));
  };

  const handleAddNewObjective = () => {
    setIsAddingNew(true);
  };

  const handleSaveNewObjective = () => {
    if (newObjective.title.trim() && newObjective.description.trim()) {
      const newId = `obj${Date.now()}`;
      setObjectives([...objectives, { id: newId, ...newObjective }]);
      setIsAddingNew(false);
      setNewObjective({
        title: '',
        description: '',
        perspective: 'financial',
        status: 'active'
      });
    }
  };

  const handleCancelNewObjective = () => {
    setIsAddingNew(false);
    setNewObjective({
      title: '',
      description: '',
      perspective: 'financial',
      status: 'active'
    });
  };

  const getPerspectiveColor = (perspective: string) => {
    switch (perspective) {
      case 'financial':
        return {
          bg: 'bg-green-100',
          text: 'text-green-800',
          border: 'border-green-200'
        };
      case 'customer':
        return {
          bg: 'bg-blue-100',
          text: 'text-blue-800',
          border: 'border-blue-200'
        };
      case 'internal':
        return {
          bg: 'bg-purple-100',
          text: 'text-purple-800',
          border: 'border-purple-200'
        };
      case 'learning':
        return {
          bg: 'bg-yellow-100',
          text: 'text-yellow-800',
          border: 'border-yellow-200'
        };
      default:
        return {
          bg: 'bg-gray-100',
          text: 'text-gray-800',
          border: 'border-gray-200'
        };
    }
  };

  const getStatusColor = (status: string) => {
    switch (status) {
      case 'active':
        return 'bg-blue-100 text-blue-800';
      case 'completed':
        return 'bg-green-100 text-green-800';
      case 'on-hold':
        return 'bg-yellow-100 text-yellow-800';
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

  const renderObjectiveForm = (isEditing: boolean) => {
    const objective = isEditing ? editingObjective : newObjective;
    if (!objective) return null;

    return (
      <div className="bg-white rounded-lg shadow-lg p-6 mb-6 border border-blue-200">
        <h3 className="text-xl font-semibold text-gray-800 mb-4">
          {isEditing ? 'Edit Strategic Objective' : 'Add New Strategic Objective'}
        </h3>
        
        <div className="space-y-4">
          <div>
            <label className="block text-sm font-medium text-gray-700 mb-1">Title</label>
            <input
              type="text"
              value={objective.title}
              onChange={(e) => isEditing 
                ? setEditingObjective({...editingObjective!, title: e.target.value})
                : setNewObjective({...newObjective, title: e.target.value})
              }
              className="w-full p-2 border border-gray-300 rounded-md"
              placeholder="Objective title"
            />
          </div>
          
          <div>
            <label className="block text-sm font-medium text-gray-700 mb-1">Description</label>
            <textarea
              value={objective.description}
              onChange={(e) => isEditing 
                ? setEditingObjective({...editingObjective!, description: e.target.value})
                : setNewObjective({...newObjective, description: e.target.value})
              }
              className="w-full p-2 border border-gray-300 rounded-md"
              rows={3}
              placeholder="Describe the objective"
            />
          </div>
          
          <div className="grid grid-cols-1 md:grid-cols-2 gap-4">
            <div>
              <label className="block text-sm font-medium text-gray-700 mb-1">BSC Perspective</label>
              <select
                value={objective.perspective}
                onChange={(e) => isEditing 
                  ? setEditingObjective({...editingObjective!, perspective: e.target.value as any})
                  : setNewObjective({...newObjective, perspective: e.target.value as any})
                }
                className="w-full p-2 border border-gray-300 rounded-md"
              >
                <option value="financial">Financial</option>
                <option value="customer">Customer</option>
                <option value="internal">Internal Processes</option>
                <option value="learning">Learning & Growth</option>
              </select>
            </div>
            
            <div>
              <label className="block text-sm font-medium text-gray-700 mb-1">Status</label>
              <select
                value={objective.status}
                onChange={(e) => isEditing 
                  ? setEditingObjective({...editingObjective!, status: e.target.value as any})
                  : setNewObjective({...newObjective, status: e.target.value as any})
                }
                className="w-full p-2 border border-gray-300 rounded-md"
              >
                <option value="active">Active</option>
                <option value="completed">Completed</option>
                <option value="on-hold">On Hold</option>
              </select>
            </div>
          </div>
          
          <div className="flex justify-end space-x-3 pt-2">
            <button
              onClick={isEditing ? () => setEditingObjective(null) : handleCancelNewObjective}
              className="px-4 py-2 border border-gray-300 rounded-md text-gray-700 hover:bg-gray-50"
            >
              Cancel
            </button>
            <button
              onClick={isEditing ? handleSaveEdit : handleSaveNewObjective}
              className="px-4 py-2 bg-blue-600 text-white rounded-md hover:bg-blue-700"
            >
              Save
            </button>
          </div>
        </div>
      </div>
    );
  };

  // Group objectives by perspective
  const objectivesByPerspective = {
    financial: objectives.filter(obj => obj.perspective === 'financial'),
    customer: objectives.filter(obj => obj.perspective === 'customer'),
    internal: objectives.filter(obj => obj.perspective === 'internal'),
    learning: objectives.filter(obj => obj.perspective === 'learning')
  };

  return (
    <div className="space-y-6">
      <div>
        <h1 className="text-2xl font-bold text-gray-800">Balanced Scorecard Objectives</h1>
        <p className="text-gray-600 mt-1">
          Define strategic objectives using the Balanced Scorecard framework
        </p>
      </div>

      {editingObjective && renderObjectiveForm(true)}
      {isAddingNew && renderObjectiveForm(false)}

      {canEdit && !isAddingNew && !editingObjective && (
        <button
          onClick={handleAddNewObjective}
          className="mb-6 px-4 py-2 bg-blue-600 text-white rounded-md hover:bg-blue-700 flex items-center"
        >
          <PlusCircle size={18} className="mr-2" /> Add New Objective
        </button>
      )}

      <div className="grid grid-cols-1 lg:grid-cols-2 gap-6">
        {Object.entries(objectivesByPerspective).map(([perspective, perspectiveObjectives]) => {
          const colors = getPerspectiveColor(perspective);
          
          return (
            <div 
              key={perspective} 
              className={`rounded-lg shadow overflow-hidden border ${colors.border}`}
            >
              <div className={`${colors.bg} p-4`}>
                <h2 className={`text-lg font-semibold ${colors.text}`}>
                  {getPerspectiveLabel(perspective)}
                </h2>
                <p className="text-sm text-gray-600">
                  {perspective === 'financial' && 'How should we appear to our stakeholders?'}
                  {perspective === 'customer' && 'How should we appear to our citizens/customers?'} {perspective === 'internal' && 'What processes must we excel at?'}
                  {perspective === 'learning' && 'How will we sustain our ability to change and improve?'}
                </p>
              </div>
              
              <div className="bg-white p-4">
                {perspectiveObjectives.length === 0 ? (
                  <p className="text-gray-500 text-center py-4">No objectives defined</p>
                ) : (
                  <div className="space-y-3">
                    {perspectiveObjectives.map((objective) => (
                      <div 
                        key={objective.id} 
                        className="p-4 border rounded-lg hover:shadow-sm transition-shadow"
                      >
                        <div className="flex justify-between items-start">
                          <div className="flex items-start">
                            <div className={`p-2 rounded-full ${colors.bg} ${colors.text} mr-3`}>
                              <Target size={16} />
                            </div>
                            <div>
                              <h3 className="font-medium text-gray-800">{objective.title}</h3>
                              <p className="text-sm text-gray-600 mt-1">{objective.description}</p>
                            </div>
                          </div>
                          
                          {canEdit && (
                            <div className="flex space-x-1 ml-2">
                              <button
                                onClick={() => handleEditObjective(objective)}
                                className="p-1 text-gray-500 hover:text-blue-600"
                              >
                                <Edit2 size={16} />
                              </button>
                              <button
                                onClick={() => handleDeleteObjective(objective.id)}
                                className="p-1 text-gray-500 hover:text-red-600"
                              >
                                <Trash2 size={16} />
                              </button>
                            </div>
                          )}
                        </div>
                        
                        <div className="mt-3">
                          <span className={`text-xs px-2 py-1 rounded-full ${getStatusColor(objective.status)}`}>
                            {objective.status.charAt(0).toUpperCase() + objective.status.slice(1)}
                          </span>
                        </div>
                      </div>
                    ))}
                  </div>
                )}
              </div>
            </div>
          );
        })}
      </div>

      <div className="bg-blue-50 border border-blue-200 rounded-lg p-6">
        <h2 className="text-lg font-medium text-blue-800">Balanced Scorecard Tips</h2>
        <ul className="mt-3 space-y-2 text-blue-700">
          <li className="flex items-start">
            <span className="mr-2">•</span>
            <span>Ensure objectives are balanced across all four perspectives</span>
          </li>
          <li className="flex items-start">
            <span className="mr-2">•</span>
            <span>Create a strategy map to visualize cause-and-effect relationships between objectives</span>
          </li>
          <li className="flex items-start">
            <span className="mr-2">•</span>
            <span>Limit the number of objectives to maintain focus (3-5 per perspective is ideal)</span>
          </li>
          <li className="flex items-start">
            <span className="mr-2">•</span>
            <span>Align objectives with your organization's mission and vision</span>
          </li>
        </ul>
      </div>
    </div>
  );
};

export default BSCObjectives;