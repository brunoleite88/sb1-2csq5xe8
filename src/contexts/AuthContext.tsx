import React, { createContext, useState, useContext, useEffect } from 'react';

// Define user roles
export type UserRole = 'collaborator' | 'manager' | 'admin';

// Define user type
export interface User {
  id: string;
  name: string;
  email: string;
  role: UserRole;
}

// Define context type
interface AuthContextType {
  currentUser: User | null;
  login: (email: string, password: string) => Promise<void>;
  logout: () => void;
  isAuthenticated: boolean;
  isAdmin: boolean;
  isManager: boolean;
}

// Create context
const AuthContext = createContext<AuthContextType | undefined>(undefined);

// Sample users for demo
const sampleUsers: User[] = [
  { id: '1', name: 'Admin User', email: 'admin@example.com', role: 'admin' },
  { id: '2', name: 'Manager User', email: 'manager@example.com', role: 'manager' },
  { id: '3', name: 'Collaborator User', email: 'collaborator@example.com', role: 'collaborator' },
];

export const AuthProvider: React.FC<{ children: React.ReactNode }> = ({ children }) => {
  const [currentUser, setCurrentUser] = useState<User | null>(null);
  const [isAuthenticated, setIsAuthenticated] = useState<boolean>(false);

  // Check for saved user on mount
  useEffect(() => {
    const savedUser = localStorage.getItem('currentUser');
    if (savedUser) {
      setCurrentUser(JSON.parse(savedUser));
      setIsAuthenticated(true);
    }
  }, []);

  // Login function
  const login = async (email: string, password: string): Promise<void> => {
    // In a real app, this would be an API call
    // For demo purposes, we'll use the sample users
    const user = sampleUsers.find(u => u.email === email);
    
    if (user) {
      setCurrentUser(user);
      setIsAuthenticated(true);
      localStorage.setItem('currentUser', JSON.stringify(user));
      return Promise.resolve();
    } else {
      return Promise.reject(new Error('Invalid credentials'));
    }
  };

  // Logout function
  const logout = () => {
    setCurrentUser(null);
    setIsAuthenticated(false);
    localStorage.removeItem('currentUser');
  };

  // Check if user is admin
  const isAdmin = currentUser?.role === 'admin';
  
  // Check if user is manager or admin
  const isManager = currentUser?.role === 'manager' || currentUser?.role === 'admin';

  return (
    <AuthContext.Provider value={{ 
      currentUser, 
      login, 
      logout, 
      isAuthenticated,
      isAdmin,
      isManager
    }}>
      {children}
    </AuthContext.Provider>
  );
};

// Custom hook to use auth context
export const useAuth = () => {
  const context = useContext(AuthContext);
  if (context === undefined) {
    throw new Error('useAuth must be used within an AuthProvider');
  }
  return context;
};