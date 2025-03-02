import React, { useState } from 'react';
import { Save, Edit2, Trash2, PlusCircle, ArrowUpRight, ArrowRight, ArrowDownRight } from 'lucide-react';
import { useAuth } from '../contexts/AuthContext';

interface ScenarioItem {
  id: string;
  title: string;
  description: string;
  probability: 'high' | 'medium' | 'low';
  impact: 'positive' | 'negative' | 'neutral';
  keyFactors: string[];
  strategicImplications: string[];
}

const Scenarios: React.FC = () => {
  const { isManager, isAdmin } = useAuth();
  const canEdit = isManager || isAdmin;
  
  const [scenarios, setScenarios] = useState<ScenarioItem[]>([
    {
      id: 'sc1',
      title: 'Digital Transformation Success',
      description: 'The organization successfully implements digital transformation initiatives, leading to improved service delivery and operational efficiency.',
      probability: 'medium',
      impact: 'positive',
      keyFactors: [
        'Adequate funding for IT infrastructure',
        'Staff training and adoption',
        'Effective change management',
        'Supportive leadership'
      ],
      strategicImplications: [
        'Increased service capacity',
        'Reduced operational costs',
        'Improved citizen satisfaction',
        'Enhanced data-driven decision making'
      ]
    },
    {
      id: 'sc2',
      title: 'Budget Constraints',
      description: 'Significant budget cuts affect the organization\'s ability to maintain current service levels and implement new initiatives.',
      probability: 'high',
      impact: 'negative',
      keyFactors: [
        'Economic downturn',
        'Changing government priorities',
        'Reduced tax revenue',
        'Competing public needs'
      ],
      strategicImplications: [
        'Need to prioritize essential services',
        'Delayed implementation of strategic initiatives',
        'Potential staff reductions',
        'Focus on efficiency improvements'
      ]
    },
    {
      id: 'sc3',
      title: 'Regulatory Changes',
      description: 'New regulations require significant changes to organizational processes and compliance measures.',
      probability: 'medium',
      impact: 'neutral',
      keyFactors: [
        'Political landscape shifts',
        'Public sector reform agenda',
        'Transparency requirements',
        'Accountability standards'
      ],
      strategicImplications: [
        'Process redesign needs',
        'Compliance costs',
        'Opportunity to modernize systems',
        'Staff training requirements'
      ]
    }
  ]);

  const [editingScenario, setEditingScenario] = useState<ScenarioItem | null>(null);
  const [isAddingNew, setIsAddingNew] = useState(false);
  const [newScenario, setNewScenario] = useState<Omit<ScenarioItem, 'id'>>({
    title: '',
    description: '',
    probability: 'medium',
    impact: 'neutral',
    keyFactors: [''],
    strategicImplications: ['']
  });

  const handleEditScenario = (scenario: ScenarioItem) => {
    setEditingScenario({...scenario});
  };

  const handleSaveEdit = () => {
    if (editingScenario) {
      const updatedScenarios = scenarios.map(s => 
        s.id === editingScenario.id ? editingScenario : s
      );
      setScenarios(updatedScenarios);
      setEditingScenario(null);
    }
  };

  const handleDeleteScenario = (id: string) => {
    setScenarios(scenarios.filter(s => s.id !== id));
  };

  const handleAddNewScenario = () => {
    setIsAddingNew(true);
  };

  const handleSaveNewScenario = () => {
    if (newScenario.title.trim() && newScenario.description.trim()) {
      const newId = `sc${Date.now()}`;
      setScenarios([...scenarios, { id: newId, ...newScenario }]);
      setIsAddingNew(false);
      setNewScenario({
        title: '',
        description: '',
        probability: 'medium',
        impact: 'neutral',
        keyFactors: [''],
        strategicImplications: ['']
      });
    }
  };

  const handleCancelNewScenario = () => {
    setIsAddingNew(false);
    setNewScenario({
      title: '',
      description: '',
      probability: 'medium',
      impact: 'neutral',
      keyFactors: [''],
      strategicImplications: ['']
    });
  };

  const handleAddKeyFactor = (isEditing: boolean) => {
    if (isEditing && editingScenario) {
      setEditingScenario({
        ...editingScenario,
        keyFactors: [...editingScenario.keyFactors, '']
      });
    } else {
      setNewScenario({
        ...newScenario,
        keyFactors: [...newScenario.keyFactors, '']
      });
    }
  };

  const handleAddImplication = (isEditing: boolean) => {
    if (isEditing && editingScenario) {
      setEditingScenario({
        ...editingScenario,
        strategicImplications: [...editingScenario.strategicImplications, '']
      });
    } else {
      setNewScenario({
        ...newScenario,
        strategicImplications: [...newScenario.strategicImplications, '']
      });
    }
  };

  const handleUpdateKeyFactor = (index: number, value: string, isEditing: boolean) => {
    if (isEditing && editingScenario) {
      const updatedFactors = [...editingScenario.keyFactors];
      updatedFactors[index] = value;
      setEditingScenario({
        ...editingScenario,
        keyFactors: updatedFactors
      });
    } else {
      const updatedFactors = [...newScenario.keyFactors];
      updatedFactors[index] = value;
      setNewScenario({
        ...newScenario,
        keyFactors: updatedFactors
      });
    }
  };

  const handleUpdateImplication = (index: number, value: string, isEditing: boolean) => {
    if (isEditing && editingScenario) {
      const updatedImplications = [...editingScenario.strategicImplications];
      updatedImplications[index] = value;
      setEditingScenario({
        ...editingScenario,
        strategicImplications: updatedImplications
      });
    } else {
      const updatedImplications = [...newScenario.strategicImplications];
      updatedImplications[index] = value;
      setNewScenario({
        ...newScenario,
        strategicImplications: updatedImplications
      });
    }
  };

  const handleRemoveKeyFactor = (index: number, isEditing: boolean) => {
    if (isEditing && editingScenario) {
      const updatedFactors = [...editingScenario.keyFactors];
      updatedFactors.splice(index, 1);
      setEditingScenario({
        ...editingScenario,
        keyFactors: updatedFactors
      });
    } else {
      const updatedFactors = [...newScenario.keyFactors];
      updatedFactors.splice(index, 1);
      setNewScenario({
        ...newScenario,
        keyFactors: updatedFactors
      });
    }
  };

  const handleRemoveImplication = (index: number, isEditing: boolean) => {
    if (isEditing && editingScenario) {
      const updatedImplications = [...editingScenario.strategicImplications];
      updatedImplications.splice(index, 1);
      setEditingScenario({
        ...editingScenario,
        strategicImplications: updatedImplications
      });
    } else {
      const updatedImplications = [...newScenario.strategicImplications];
      updatedImplications.splice(index, 1);
      setNewScenario({
        ...newScenario,
        strategicImplications: updatedImplications
      });
    }
  };

  const getImpactIcon = (impact: string) => {
    switch (impact) {
      case 'positive':
        return <ArrowUpRight className="text-green-600" size={20} />;
      case 'negative':
        return <ArrowDownRight className="text-red-600" size={20} />;
      default:
        return <ArrowRight className="text-gray-600" size={20} />;
    }
  };

  const getProbabilityColor = (probability: string) => {
    switch (probability) {
      case 'high':
        return 'bg-red-100 text-red-800';
      case 'medium':
        return 'bg-yellow-100 text-yellow-800';
      case 'low':
        return 'bg-green-100 text-green-800';
      default:
        return 'bg-gray-100 text-gray-800';
    }
  };

  const getImpactColor = (impact: string) => {
    switch (impact) {
      case 'positive':
        return 'bg-green-100 text-green-800';
      case 'negative':
        return 'bg-red-100 text-red-800';
      default:
        return 'bg-gray-100 text-gray-800';
    }
  };

  const renderScenarioForm = (isEditing: boolean) => {
    const scenario = isEditing ? editingScenario : newScenario;
    if (!scenario) return null;

    return (
      <div className="bg-white rounded-lg shadow-lg p-6 mb-6 border border-blue-200">
        <h3 className="text-xl font-semibold text-gray-800 mb-4">
          {isEditing ? 'Edit Scenario' : 'Add New Scenario'}
        </h3>
        
        <div className="space-y-4">
          <div>
            <label className="block text-sm font-medium text-gray-700 mb-1">Title</label>
            <input
              type="text"
              value={scenario.title}
              onChange={(e) => isEditing 
                ? setEditingScenario({...editingScenario!, title: e.target.value})
                : setNewScenario({...newScenario, title: e.target.value})
              }
              className="w-full p-2 border border-gray-300 rounded-md"
              placeholder="Scenario title"
            />
          </div>
          
          <div>
            <label className="block text-sm font-medium text-gray-700 mb-1">Description</label>
            <textarea
              value={scenario.description}
              onChange={(e) => isEditing 
                ? setEditingScenario({...editingScenario!, description: e.target.value})
                : setNewScenario({...newScenario, description: e.target.value})
              }
              className="w-full p-2 border border-gray-300 rounded-md"
              rows={3}
              placeholder="Describe the scenario"
            />
          </div>
          
          <div className="grid grid-cols-1 md:grid-cols-2 gap-4">
            <div>
              <label className="block text-sm font-medium text-gray-700 mb-1">Probability</label>
              <select
                value={scenario.probability}
                onChange={(e) => isEditing 
                  ? setEditingScenario({...editingScenario!, probability: e.target.value as any})
                  : setNewScenario({...newScenario, probability: e.target.value as any})
                }
                className="w-full p-2 border border-gray-300 rounded-md"
              >
                <option value="high">High</option>
                <option value="medium">Medium</option>
                <option value="low">Low</option>
              </select>
            </div>
            
            <div>
              <label className="block text-sm font-medium text-gray-700 mb-1">Impact</label>
              <select
                value={scenario.impact}
                onChange={(e) => isEditing 
                  ? setEditingScenario({...editingScenario!, impact: e.target.value as any})
                  : setNewScenario({...newScenario, impact: e.target.value as any})
                }
                className="w-full p-2 border border-gray-300 rounded-md"
              >
                <option value="positive">Positive</option>
                <option value="negative">Negative</option>
                <option value="neutral">Neutral</option>
              </select>
            </div>
          </div>
          
          <div>
            <label className="block text-sm font-medium text-gray-700 mb-1">Key Factors</label>
            {scenario.keyFactors.map((factor, index) => (
              <div key={`factor-${index}`} className="flex items-center mb-2">
                <input
                  type="text"
                  value={factor}
                  onChange={(e) => handleUpdateKeyFactor(index, e.target.value, isEditing)}
                  className="flex-1 p-2 border border-gray-300 rounded-md"
                  placeholder="Key factor"
                />
                {scenario.keyFactors.length > 1 && (
                  <button
                    onClick={() => handleRemoveKeyFactor(index, isEditing)}
                    className="ml-2 p-1 text-red-600 hover:bg-red-100 rounded-md"
                  >
                    <Trash2 size={16} />
                  </button>
                )}
              </div>
            ))}
            <button
              onClick={() => handleAddKeyFactor(isEditing)}
              className="mt-1 text-sm text-blue-600 hover:text-blue-800 flex items-center"
            >
              <PlusCircle size={16} className="mr-1" /> Add Factor
            </button>
          </div>
          
          <div>
            <label className="block text-sm font-medium text-gray-700 mb-1">Strategic Implications</label>
            {scenario.strategicImplications.map((implication, index) => (
              <div key={`implication-${index}`} className="flex items-center mb-2">
                <input
                  type="text"
                  value={implication}
                  onChange={(e) => handleUpdateImplication(index, e.target.value, isEditing)}
                  className="flex-1 p-2 border border-gray-300 rounded-md"
                  placeholder="Strategic implication"
                />
                {scenario.strategicImplications.length > 1 && (
                  <button
                    onClick={() => handleRemoveImplication(index, isEditing)}
                    className="ml-2 p-1 text-red-600 hover:bg-red-100 rounded-md"
                  >
                    <Trash2 size={16} />
                  </button>
                )}
              </div>
            ))}
            <button
              onClick={() => handleAddImplication(isEditing)}
              className="mt-1 text-sm text-blue-600 hover:text-blue-800 flex items-center"
            >
              <PlusCircle size={16} className="mr-1" /> Add Implication
            </button>
          </div>
          
          <div className="flex justify-end space-x-3 pt-2">
            <button
              onClick={isEditing ? () => setEditingScenario(null) : handleCancelNewScenario}
              className="px-4 py-2 border border-gray-300 rounded-md text-gray-700 hover:bg-gray-50"
            >
              Cancel
            </button>
            <button
              onClick={isEditing ? handleSaveEdit : handleSaveNewScenario}
              className="px-4 py-2 bg-blue-600 text-white rounded-md hover:bg-blue-700"
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
        <h1 className="text-2xl font-bold text-gray-800">Scenario Planning</h1>
        <p className="text-gray-600 mt-1">
          Develop possible future scenarios based on your SWOT analysis
        </p>
      </div>

      {editingScenario && renderScenarioForm(true)}
      {isAddingNew && renderScenarioForm(false)}

      {canEdit && !isAddingNew && !editingScenario && (
        <button
          onClick={handleAddNewScenario}
          className="mb-6 px-4 py-2 bg-blue-600 text-white rounded-md hover:bg-blue-700 flex items-center"
        >
          <PlusCircle size={18} className="mr-2" /> Add New Scenario
        </button>
      )}

      <div className="space-y-6">
        {scenarios.map((scenario) => (
          <div 
            key={scenario.id} 
            className="bg-white rounded-lg shadow p-6"
          >
            <div className="flex justify-between items-start">
              <div>
                <h2 className="text-xl font-semibold text-gray-800 flex items-center">
                  {scenario.title}
                  <span className="ml-2">{getImpactIcon(scenario.impact)}</span>
                </h2>
                <div className="flex space-x-2 mt-1">
                  <span className={`text-xs px-2 py-1 rounded-full ${getProbabilityColor(scenario.probability)}`}>
                    {scenario.probability.charAt(0).toUpperCase() + scenario.probability.slice(1)} Probability
                  </span>
                  <span className={`text-xs px-2 py-1 rounded-full ${getImpactColor(scenario.impact)}`}>
                    {scenario.impact.charAt(0).toUpperCase() + scenario.impact.slice(1)} Impact
                  </span>
                </div>
              </div>
              
              {canEdit && (
                <div className="flex space-x-2">
                  <button
                    onClick={() => handleEditScenario(scenario)}
                    className="p-1 text-gray-500 hover:text-blue-600"
                  >
                    <Edit2 size={18} />
                  </button>
                  <button
                    onClick={() => handleDeleteScenario(scenario.id)}
                    className="p-1 text-gray-500 hover:text-red-600"
                  >
                    <Trash2 size={18} />
                  </button>
                </div>
              )}
            </div>
            
            <p className="text-gray-600 mt-3">{scenario.description}</p>
            
            <div className="grid grid-cols-1 md:grid-cols-2 gap-6 mt-4">
              <div>
                <h3 className="text-sm font-medium text-gray-700 mb-2">Key Factors</h3>
                <ul className="space-y-1">
                  {scenario.keyFactors.map((factor, index) => (
                    <li key={index} className="text-gray-600 flex items-start">
                      <span className="mr-2">•</span>
                      <span>{factor}</span>
                    </li>
                  ))}
                </ul>
              </div>
              
              <div>
                <h3 className="text-sm font-medium text-gray-700 mb-2">Strategic Implications</h3>
                <ul className="space-y-1">
                  {scenario.strategicImplications.map((implication, index) => (
                    <li key={index} className="text-gray-600 flex items-start">
                      <span className="mr-2">•</span>
                      <span>{implication}</span>
                    </li>
                  ))}
                </ul>
              </div>
            </div>
          </div>
        ))}
      </div>

      <div className="bg-blue-50 border border-blue-200 rounded-lg p-6">
        <h2 className="text-lg font-medium text-blue-800">Scenario Planning Tips</h2>
        <ul className="mt-3 space-y-2 text-blue-700">
          <li className="flex items-start">
            <span className="mr-2">•</span>
            <span>Focus on scenarios that are plausible, even if unlikely</span>
          </li>
          <li className="flex items-start">
            <span className="mr-2">•</span>
            <span>Consider both positive and negative scenarios to prepare for all possibilities</span>
          </li>
          <li className="flex items-start">
            <span className="mr-2">•</span>
            <span>Use scenarios to inform your strategic objectives and risk management plans</span>
          </li>
          <li className="flex items-start">
            <span className="mr-2">•</span>
            <span>Regularly revisit and update scenarios as new information becomes available</span>
          </li>
        </ul>
      </div>
    </div>
  );
};

export default Scenarios;