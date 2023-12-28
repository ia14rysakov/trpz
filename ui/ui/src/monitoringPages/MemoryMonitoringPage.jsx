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
import 'chartjs-adapter-date-fns';
import { Line } from 'react-chartjs-2';

ChartJS.register(
    CategoryScale,
    LinearScale,
    PointElement,
    LineElement,
    TimeScale,
    Title,
    Tooltip,
    Legend
);

const MemoryMonitoringPage = () => {
    const [memoryData, setMemoryData] = useState([]);
    const location = useLocation();

    useEffect(() => {
        const queryParams = new URLSearchParams(location.search);
        const osType = queryParams.get('osType');

        const eventSource = new EventSource(`http://localhost:8080/monitoring/memory?osType=${osType}`);

        eventSource.onmessage = (event) => {
            const newMemoryPoint = JSON.parse(event.data);
            const newEntry = {
                timestamp: new Date().toISOString(),
                memoryUsage: newMemoryPoint.memoryUsage
            };
            setMemoryData((prevData) => [...prevData, newEntry]);
        };

        eventSource.onerror = (error) => {
            console.error('EventSource failed:', error);
            eventSource.close();
        };

        return () => {
            eventSource.close();
        };
    }, [location]);

    const data = {
        labels: memoryData.map((point) => point.timestamp),
        datasets: [
            {
                label: 'Memory Usage (%)',
                data: memoryData.map((point) => point.memoryUsage),
                fill: false,
                borderColor: 'rgb(255, 99, 132)',
                backgroundColor: 'rgba(255, 99, 132, 0.5)',
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
                        second: 'h:mm:ss a'
                    }
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
                text: 'Memory Usage Over Time',
            },
        },
    };

    return (
        <div>
            <h1>Memory Monitoring</h1>
            <Line data={data} options={options} />
        </div>
    );
};

export default MemoryMonitoringPage;
