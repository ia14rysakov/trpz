import React, { useState, useEffect } from 'react';
import axios from 'axios';
import { useLocation } from 'react-router-dom';
import {
    Chart as ChartJS,
    CategoryScale,
    LinearScale,
    PointElement,
    LineElement,
    Title,
    Tooltip,
    Legend,
    TimeScale,
} from 'chart.js';
import 'chartjs-adapter-date-fns'; // Import the adapter
import { Line } from 'react-chartjs-2';

// Register the TimeScale for the time axis
ChartJS.register(
    CategoryScale,
    LinearScale,
    PointElement,
    LineElement,
    TimeScale, // Register TimeScale here
    Title,
    Tooltip,
    Legend
);

const CpuMonitoringPage = () => {
    const [cpuData, setCpuData] = useState([]);
    const location = useLocation();

    useEffect(() => {
        const queryParams = new URLSearchParams(location.search);
        const osType = queryParams.get('osType');

        const startMonitoring = async () => {
            try {
                const startResponse = await axios.post('http://localhost:8080/monitoring/start', {
                    monitoringType: 'cpuLoad',
                    osType: osType,
                });
                fetchData();
            } catch (error) {
                console.error('Error starting monitoring:', error);
            }
        };

        const fetchData = async () => {
            try {
                const response = await axios.get('http://localhost:8080/monitoring/test');
                setCpuData(response.data);
            } catch (error) {
                console.error('Error fetching data:', error);
            }
        };

        startMonitoring();
    }, [location]);

    const data = {
        labels: cpuData.map(point => new Date(point.timestamp).toLocaleTimeString()),
        datasets: [
            {
                label: 'CPU Usage (%)',
                data: cpuData.map(point => point.cpuUsage),
                fill: false,
                backgroundColor: 'rgb(75, 192, 192)',
                borderColor: 'rgba(75, 192, 192, 0.2)',
            },
        ],
    };

    const options = {
        scales: {
            x: {
                type: 'time',
                time: {
                    unit: 'second',
                    displayFormats: {
                        second: 'h:mm:ss a',
                    },
                },
            },
            y: {
                beginAtZero: true,
            },
        },
        responsive: true,
        plugins: {
            legend: {
                position: 'top',
            },
            title: {
                display: true,
                text: 'CPU Load Over Time',
            },
        },
    };

    return (
        <div>
            <h1>CPU Monitoring</h1>
            <Line data={data} options={options} />
        </div>
    );
};

export default CpuMonitoringPage;
