import React, { useState } from 'react';
import { Save, Edit2, Trash2 } from 'lucide-react';
import { useAuth } from '../contexts/AuthContext';

interface FactorItem {
  id: string;
  text: string;
  impact: 'positive' | 'negative' | 'neutral';
}

interface PestalCategory {
  name: string;
  description: string;
  factors: FactorItem[];
}

const PestalAnalysis: React.FC = () => {
  const { isManager, isAdmin } = useAuth();
  const canEdit = isManager || isAdmin;
  
  const [pestalData, setPestalData] = useState<PestalCategory[]>([
    {
      name: 'Political',
      description: 'Government policies, political stability, regulations',
      factors: [
        { id: 'p1', text: 'New government administration with focus on public service reform', impact: 'positive' },
        { id: 'p2', text: 'Upcoming elections creating policy uncertainty', impact: 'negative' },
        { id: 'p3', text: 'Increased regulatory oversight on public spending', impact: 'neutral' },
      ]
    },
    {
      name: 'Economic',
      description: 'Economic growth, inflation, interest rates, unemployment',
      factors: [
        { id: 'e1', text: 'Budget constraints due to economic downturn', impact: 'negative' },
        { id: 'e2', text: 'New funding opportunities from federal programs', impact: 'positive' },
        { id: 'e3', text: 'Rising operational costs', impact: 'negative' },
      ]
    },
    {
      name: 'Social',
      description: 'Demographics, cultural trends, education levels, health consciousness',
      factors: [
        { id: 's1', text: 'Aging population requiring more services', impact: 'negative' },
        { id: 's2', text: 'Increased public participation in governance', impact: 'positive' },
        { id: 's3', text: 'Growing demand for digital services', impact: 'positive' },
      ]
    },
    {
      name: 'Technological',
      description: 'Innovations, automation, R&D activity, technological change rate',
      factors: [
        { id: 't1', text: 'Rapid adoption of cloud technologies', impact: 'positive' },
        { id: 't2', text: 'Legacy systems requiring costly upgrades', impact: 'negative' },
        { id: 't3', text: 'Emerging AI and data analytics capabilities', impact: 'positive' },
      ]
    },
    {
      name: 'Environmental',
      description: 'Climate change, environmental regulations, sustainability pressures',
      factors: [
        { id: 'en1', text: 'New environmental compliance requirements', impact: 'negative' },
        { id: 'en2', text: 'Opportunities for green initiatives funding', impact: 'positive' },
        { id: 'en3', text: 'Increasing extreme weather events affecting infrastructure', impact: 'negative' },
      ]
    },
    {
      name: 'Legal',
      description: 'Current and upcoming legislation, industry-specific regulations',
      factors: [
        { id: 'l1', text: 'New data privacy regulations', impact: 'negative' },
        { id: 'l2', text: 'Simplified procurement laws', impact: 'positive' },
        { id: 'l3', text: 'Changes in employment law affecting public sector', impact: 'neutral' },
      ]
    },
  ]);

  const [editingFactor, setEditingFactor] = useState<{
    categoryIndex: number;
    factorIndex: number;
    text: string;
    impact: 'positive' | 'negative' | 'neutral';
  } | null>(null);

  const [newFactor, setNewFactor] = useState<{
    categoryIndex: number;
    text: string;
    impact: 'positive' | 'negative' | 'neutral';
  } | null>(null);

  const handleEditFactor = (categoryIndex: number, factorIndex: number) => {
    const factor = pestalData[categoryIndex].factors[factorIndex];
    setEditingFactor({
      categoryIndex,
      factorIndex,
      text: factor.text,
      impact: factor.impact
    });
  };

  const handleSaveEdit = () => {
    if (editingFactor) {
      const updatedData = [...pestalData];
      updatedData[editingFactor.categoryIndex].factors[editingFactor.factorIndex] = {
        ...updatedData[editingFactor.categoryIndex].factors[editingFactor.factorIndex],
        text: editingFactor.text,
        impact: editingFactor.impact
      };
      setPestalData(updatedData);
      setEditingFactor(null);
    }
  };

  const handleDeleteFactor = (categoryIndex: number, factorIndex: number) => {
    const updatedData = [...pestalData];
    updatedData[categoryIndex].factors.splice(factorIndex, 1);
    setPestalData(updatedData);
  };

  const handleAddNewFactor = (categoryIndex: number) => {
    setNewFactor({
      categoryIndex,
      text: '',
      impact: 'neutral'
    });
  };

  const handleSaveNewFactor = () => {
    if (newFactor && newFactor.text.trim()) {
      const updatedData = [...pestalData];
      updatedData[newFactor.categoryIndex].factors.push({
        id: `${pestalData[newFactor.categoryIndex].name.toLowerCase()[0]}${Date.now()}`,
        text: newFactor.text,
        impact: newFactor.impact
      });
      setPestalData(updatedData);
      setNewFactor(null);
    }
  };

  return (
    <div className="space-y-6">
      <div>
        <h1 className="text-2xl font-bold text-gray-800">PESTAL Analysis</h1>
        <p className="text-gray-600 mt-1">
          Analyze external factors that may impact your organization's strategy
        </p>
      </div>

      <div className="grid grid-cols-1 md:grid-cols-2 gap-6">
        {pestalData.map((category, categoryIndex) => (
          <div key={category.name} className="bg-white rounded-lg shadow p-6">
            <h2 className="text-xl font-semibold text-gray-800">{category.name}</h2>
            <p className="text-sm text-gray-600 mb-4">{category.description}</p>
            
            <div className="space-y-3">
              {category.factors.map((factor, factorIndex) => (
                <div 
                  key={factor.id} 
                  className={`p-3 rounded-md ${
                    factor.impact === 'positive' ? 'bg-green-50 border-l-4 border-green-400' : 
                    factor.impact === 'negative' ? 'bg-red-50 border-l-4 border-red-400' : 
                    'bg-gray-50 border-l-4 border-gray-400'
                  }`}
                >
                  {editingFactor && 
                   editingFactor.categoryIndex === categoryIndex && 
                   editingFactor.factorIndex === factorIndex ? (
                    <div className="space-y-2">
                      <textarea
                        value={editingFactor.text}
                        onChange={(e) => setEditingFactor({...editingFactor, text: e.target.value})}
                        className="w-full p-2 border border-gray-300 rounded-md"
                        rows={2}
                      />
                      <div className="flex items-center">
                        <label className="mr-2 text-sm">Impact:</label>
                        <select
                          value={editingFactor.impact}
                          onChange={(e) => setEditingFactor({
                            ...editingFactor, 
                            impact: e.target.value as 'positive' | 'negative' | 'neutral'
                          })}
                          className="p-1 border border-gray-300 rounded-md text-sm"
                        >
                          <option value="positive">Positive</option>
                          <option value="negative">Negative</option>
                          <option value="neutral">Neutral</option>
                        </select>
                        <button
                          onClick={handleSaveEdit}
                          className="ml-auto p-1 bg-blue-100 text-blue-600 rounded-md"
                        >
                          <Save size={16} />
                        </button>
                      </div>
                    </div>
                  ) : (
                    <div className="flex justify-between items-start">
                      <p className="text-gray-800">{factor.text}</p>
                      {canEdit && (
                        <div className="flex space-x-1 ml-2">
                          <button
                            onClick={() => handleEditFactor(categoryIndex, factorIndex)}
                            className="p-1 text-gray-500 hover:text-blue-600"
                          >
                            <Edit2 size={16} />
                          </button>
                          <button
                            onClick={() => handleDeleteFactor(categoryIndex, factorIndex)}
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

              {newFactor && newFactor.categoryIndex === categoryIndex ? (
                <div className="p-3 bg-blue-50 rounded-md border-l-4 border-blue-400">
                  <div className="space-y-2">
                    <textarea
                      value={newFactor.text}
                      onChange={(e) => setNewFactor({...newFactor, text: e.target.value})}
                      className="w-full p-2 border border-gray-300 rounded-md"
                      placeholder="Enter new factor..."
                      rows={2}
                    />
                    <div className="flex items-center">
                      <label className="mr-2 text-sm">Impact:</label>
                      <select
                        value={newFactor.impact}
                        onChange={(e) => setNewFactor({
                          ...newFactor, 
                          impact: e.target.value as 'positive' | 'negative' | 'neutral'
                        })}
                        className="p-1 border border-gray-300 rounded-md text-sm"
                      >
                        <option value="positive">Positive</option>
                        <option value="negative">Negative</option>
                        <option value="neutral">Neutral</option>
                      </select>
                      <button
                        onClick={handleSaveNewFactor}
                        className="ml-auto p-1 bg-blue-100 text-blue-600 rounded-md"
                      >
                        <Save size={16} />
                      </button>
                    </div>
                  </div>
                </div>
              ) : canEdit && (
                <button
                  onClick={() => handleAddNewFactor(categoryIndex)}
                  className="w-full py-2 text-sm text-blue-600 border border-dashed border-blue-300 rounded-md hover:bg-blue-50"
                >
                  + Add Factor
                </button>
              )}
            </div>
          </div>
        ))}
      </div>

      <div className="bg-blue-50 border border-blue-200 rounded-lg p-6">
        <h2 className="text-lg font-medium text-blue-800">PESTAL Analysis Tips</h2>
        <ul className="mt-3 space-y-2 text-blue-700">
          <li className="flex items-start">
            <span className="mr-2">•</span>
            <span>Consider both short-term and long-term impacts of each factor</span>
          </li>
          <li className="flex items-start">
            <span className="mr-2">•</span>
            <span>Focus on factors that are most relevant to your organization's mission</span>
          </li>
          <li className="flex items-start">
            <span className="mr-2">•</span>
            <span>Update your PESTAL analysis regularly as external conditions change</span>
          </li>
          <li className="flex items-start">
            <span className="mr-2">•</span>
            <span>Use this analysis as input for your SWOT analysis</span>
          </li>
        </ul>
      </div>
    </div>
  );
};

export default PestalAnalysis;