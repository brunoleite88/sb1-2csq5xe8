import React, { useState } from 'react';
import { Save, Edit2, Trash2, PlusCircle } from 'lucide-react';
import { useAuth } from '../contexts/AuthContext';

interface SwotItem {
  id: string;
  text: string;
}

interface SwotCategory {
  name: string;
  description: string;
  items: SwotItem[];
  color: string;
}

const SwotAnalysis: React.FC = () => {
  const { isManager, isAdmin } = useAuth();
  const canEdit = isManager || isAdmin;
  
  const [swotData, setSwotData] = useState<SwotCategory[]>([
    {
      name: 'Strengths',
      description: 'Internal positive attributes that are within your control',
      color: 'green',
      items: [
        { id: 's1', text: 'Experienced and dedicated staff' },
        { id: 's2', text: 'Strong community relationships' },
        { id: 's3', text: 'Established processes and procedures' },
        { id: 's4', text: 'Diverse funding sources' },
      ]
    },
    {
      name: 'Weaknesses',
      description: 'Internal negative attributes that are within your control',
      color: 'red',
      items: [
        { id: 'w1', text: 'Outdated IT infrastructure' },
        { id: 'w2', text: 'Limited budget for new initiatives' },
        { id: 'w3', text: 'Bureaucratic decision-making processes' },
        { id: 'w4', text: 'High staff turnover in key departments' },
      ]
    },
    {
      name: 'Opportunities',
      description: 'External positive factors that you can capitalize on',
      color: 'blue',
      items: [
        { id: 'o1', text: 'New government funding programs' },
        { id: 'o2', text: 'Increasing public interest in transparency' },
        { id: 'o3', text: 'Partnerships with private sector organizations' },
        { id: 'o4', text: 'Emerging technologies for service delivery' },
      ]
    },
    {
      name: 'Threats',
      description: 'External negative factors beyond your control',
      color: 'yellow',
      items: [
        { id: 't1', text: 'Potential budget cuts' },
        { id: 't2', text: 'Changing regulatory requirements' },
        { id: 't3', text: 'Public skepticism of government institutions' },
        { id: 't4', text: 'Competition for skilled workforce' },
      ]
    },
  ]);

  const [editingItem, setEditingItem] = useState<{
    categoryIndex: number;
    itemIndex: number;
    text: string;
  } | null>(null);

  const [newItem, setNewItem] = useState<{
    categoryIndex: number;
    text: string;
  } | null>(null);

  const handleEditItem = (categoryIndex: number, itemIndex: number) => {
    const item = swotData[categoryIndex].items[itemIndex];
    setEditingItem({
      categoryIndex,
      itemIndex,
      text: item.text
    });
  };

  const handleSaveEdit = () => {
    if (editingItem) {
      const updatedData = [...swotData];
      updatedData[editingItem.categoryIndex].items[editingItem.itemIndex] = {
        ...updatedData[editingItem.categoryIndex].items[editingItem.itemIndex],
        text: editingItem.text
      };
      setSwotData(updatedData);
      setEditingItem(null);
    }
  };

  const handleDeleteItem = (categoryIndex: number, itemIndex: number) => {
    const updatedData = [...swotData];
    updatedData[categoryIndex].items.splice(itemIndex, 1);
    setSwotData(updatedData);
  };

  const handleAddNewItem = (categoryIndex: number) => {
    setNewItem({
      categoryIndex,
      text: ''
    });
  };

  const handleSaveNewItem = () => {
    if (newItem && newItem.text.trim()) {
      const updatedData = [...swotData];
      updatedData[newItem.categoryIndex].items.push({
        id: `${swotData[newItem.categoryIndex].name.toLowerCase()[0]}${Date.now()}`,
        text: newItem.text
      });
      setSwotData(updatedData);
      setNewItem(null);
    }
  };

  const getCategoryColor = (color: string) => {
    switch (color) {
      case 'green':
        return {
          bg: 'bg-green-100',
          border: 'border-green-500',
          text: 'text-green-800',
          header: 'bg-green-500'
        };
      case 'red':
        return {
          bg: 'bg-red-100',
          border: 'border-red-500',
          text: 'text-red-800',
          header: 'bg-red-500'
        };
      case 'blue':
        return {
          bg: 'bg-blue-100',
          border: 'border-blue-500',
          text: 'text-blue-800',
          header: 'bg-blue-500'
        };
      case 'yellow':
        return {
          bg: 'bg-yellow-100',
          border: 'border-yellow-500',
          text: 'text-yellow-800',
          header: 'bg-yellow-500'
        };
      default:
        return {
          bg: 'bg-gray-100',
          border: 'border-gray-500',
          text: 'text-gray-800',
          header: 'bg-gray-500'
        };
    }
  };

  return (
    <div className="space-y-6">
      <div>
        <h1 className="text-2xl font-bold text-gray-800">SWOT Analysis</h1>
        <p className="text-gray-600 mt-1">
          Identify your organization's Strengths, Weaknesses, Opportunities, and Threats
        </p>
      </div>

      <div className="grid grid-cols-1 md:grid-cols-2 gap-6">
        {swotData.map((category, categoryIndex) => {
          const colors = getCategoryColor(category.color);
          
          return (
            <div 
              key={category.name} 
              className={`rounded-lg shadow overflow-hidden border ${colors.border}`}
            >
              <div className={`${colors.header} text-white p-4`}>
                <h2 className="text-xl font-semibold">{category.name}</h2>
                <p className="text-sm opacity-90">{category.description}</p>
              </div>
              
              <div className={`${colors.bg} p-4 space-y-3`}>
                {category.items.map((item, itemIndex) => (
                  <div 
                    key={item.id} 
                    className="bg-white p-3 rounded-md shadow-sm"
                  >
                    {editingItem && 
                     editingItem.categoryIndex === categoryIndex && 
                     editingItem.itemIndex === itemIndex ? (
                      <div className="space-y-2">
                        <textarea
                          value={editingItem.text}
                          onChange={(e) => setEditingItem({...editingItem, text: e.target.value})}
                          className="w-full p-2 border border-gray-300 rounded-md"
                          rows={2}
                        />
                        <div className="flex justify-end">
                          <button
                            onClick={handleSaveEdit}
                            className="px-3 py-1 bg-blue-600 text-white rounded-md text-sm flex items-center"
                          >
                            <Save size={14} className="mr-1" /> Save
                          </button>
                        </div>
                      </div>
                    ) : (
                      <div className="flex justify-between items-start">
                        <p className={`${colors.text}`}>{item.text}</p>
                        {canEdit && (
                          <div className="flex space-x-1 ml-2">
                            <button
                              onClick={() => handleEditItem(categoryIndex, itemIndex)}
                              className="p-1 text-gray-500 hover:text-blue-600"
                            >
                              <Edit2 size={16} />
                            </button>
                            <button
                              onClick={() => handleDeleteItem(categoryIndex, itemIndex)}
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

                {newItem && newItem.categoryIndex === categoryIndex ? (
                  <div className="bg-white p-3 rounded-md shadow-sm border border-blue-300">
                    <div className="space-y-2">
                      <textarea
                        value={newItem.text}
                        onChange={(e) => setNewItem({...newItem, text: e.target.value})}
                        className="w-full p-2 border border-gray-300 rounded-md"
                        placeholder={`Add new ${category.name.slice(0, -1)}...`}
                        rows={2}
                      />
                      <div className="flex justify-end">
                        <button
                          onClick={handleSaveNewItem}
                          className="px-3 py-1 bg-blue-600 text-white rounded-md text-sm flex items-center"
                        >
                          <Save size={14} className="mr-1" /> Save
                        </button>
                      </div>
                    </div>
                  </div>
                ) : canEdit && (
                  <button
                    onClick={() => handleAddNewItem(categoryIndex)}
                    className="w-full py-2 flex items-center justify-center text-sm text-blue-600 border border-dashed border-blue-300 rounded-md bg-white hover:bg-blue-50"
                  >
                    <PlusCircle size={16} className="mr-1" /> 
                    Add {category.name.slice(0, -1)}
                  </button>
                )}
              </div>
            </div>
          );
        })}
      </div>

      <div className="bg-blue-50 border border-blue-200 rounded-lg p-6">
        <h2 className="text-lg font-medium text-blue-800">SWOT Analysis Tips</h2>
        <ul className="mt-3 space-y-2 text-blue-700">
          <li className="flex items-start">
            <span className="mr-2">•</span>
            <span>Be honest about your weaknesses - they represent opportunities for improvement</span>
          </li>
          <li className="flex items-start">
            <span className="mr-2">•</span>
            <span>Consider how strengths can be leveraged to capitalize on opportunities</span>
          </li>
          <li className="flex items-start">
            <span className="mr-2">•</span>
            <span>Identify strategies to address weaknesses and mitigate threats</span>
          </li>
          <li className="flex items-start">
            <span className="mr-2">•</span>
            <span>Use your SWOT analysis as the foundation for scenario planning</span>
          </li>
        </ul>
      </div>
    </div>
  );
};

export default SwotAnalysis;