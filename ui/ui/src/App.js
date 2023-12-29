import logo from './logo.svg';
import './App.css';
import React from 'react';
import CpuMonitoringPage from './monitoringPages/CpuMonitoringPage';
import KeyLoggerMonitoringPage from './monitoringPages/KeyLoggerMonitoringPage';
import MouseTrackerMonitoringPage from './monitoringPages/MouseTrackerMonitoringPage';
import MemoryMonitoringPage from './monitoringPages/MemoryMonitoringPage';
import TestMonitoringPage from './monitoringPages/TestMonitoringPage';
import WindowsMonitoringPage from './monitoringPages/WindowsMonitoringPage';
import HomePage from "./page/HomePage";
import {BrowserRouter as Router, Route, Routes} from "react-router-dom";

function App() {
  return (
      <Router>
          <Routes>
              <Route path="/" element={<HomePage/>}/>
              <Route path="/cpuLoad" element={<CpuMonitoringPage />} />
              <Route path="/keyLogger" element={<KeyLoggerMonitoringPage />} />
              <Route path="/mouseTracker" element={<MouseTrackerMonitoringPage />} />
              <Route path="/memory" element={<MemoryMonitoringPage />} />
              <Route path="/windows" element={<WindowsMonitoringPage />} />
              <Route path="/test" element={<TestMonitoringPage />} />
          </Routes>
      </Router>
  );
}

export default App;
