import React, { useState, useEffect } from 'react';
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

const CpuMonitoringPage = () => {
    const [cpuData, setCpuData] = useState([]);
    const location = useLocation();

    useEffect(() => {
        const queryParams = new URLSearchParams(location.search);
        const osType = queryParams.get('osType');

        const eventSource = new EventSource(`http://localhost:8080/monitoring/start?monitoringType=cpuLoad&osType=${osType}`);

        eventSource.onmessage = (event) => {
            const newCpuPoint = JSON.parse(event.data);
            console.log('New CPU data received:', newCpuPoint); // Log incoming data
            setCpuData((prevData) => [...prevData, newCpuPoint]);
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
        labels: cpuData.map((point) => new Date(point.timestamp).toLocaleTimeString()),
        datasets: [
            {
                label: 'CPU Usage (%)',
                data: cpuData.map((point) => point.cpuUsage),
                fill: false,
                borderColor: 'rgb(75, 192, 192)',
                backgroundColor: 'rgba(75, 192, 192, 0.5)',
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
