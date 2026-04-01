import { createSlice } from '@reduxjs/toolkit';

const initialState = {
  systemName: 'PGE Strategic Platform',
  systemSlogan: 'Defendendo o Estado, Construindo o Futuro',
  labels: {},
  messages: {},
  workflows: {},
  dashboards: {},
  loading: false
};

export const customizationSlice = createSlice({
  name: 'customization',
  initialState,
  reducers: {
    setCustomizations: (state, action) => {
      state.systemName = action.payload.systemName || state.systemName;
      state.systemSlogan = action.payload.systemSlogan || state.systemSlogan;
      state.labels = action.payload.labels || {};
      state.messages = action.payload.messages || {};
      state.workflows = action.payload.workflows || {};
      state.dashboards = action.payload.dashboards || {};
    },
    updateLabel: (state, action) => {
      const { key, value } = action.payload;
      state.labels[key] = value;
    },
    setLoading: (state, action) => {
      state.loading = action.payload;
    }
  }
});

export const { setCustomizations, updateLabel, setLoading } = customizationSlice.actions;
export default customizationSlice.reducer;
