import { configureStore } from '@reduxjs/toolkit';
import authReducer from './authSlice';
import customizationReducer from './customizationSlice';

export const store = configureStore({
  reducer: {
    auth: authReducer,
    customization: customizationReducer
  },
  middleware: (getDefaultMiddleware) =>
    getDefaultMiddleware({
      serializableCheck: {
        ignoredActions: ['persist/PERSIST'],
        ignoredPaths: ['register']
      }
    })
});

export default store;
