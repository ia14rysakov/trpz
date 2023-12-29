import React, { useState, useEffect } from 'react';
import { Line } from 'react-chartjs-2';
import { useLocation } from 'react-router-dom';

import 'chart.js/auto';

const MemoryMonitoringPage = () => {
    const [chartData, setChartData] = useState({
        labels: [],
        datasets: [
            {
                label: 'Memory Usage',
                data: [],
                borderColor: 'rgba(75, 192, 192, 1)',
                borderWidth: 1,
            },
        ],
    });
    const location = useLocation();


    useEffect(() => {
        const queryParams = new URLSearchParams(location.search);
        const osType = queryParams.get('osType');
        const monitoringType = queryParams.get('monitoringType');

        const eventSource = new EventSource(`http://localhost:8080/monitoring/${monitoringType}/${osType}`);
        eventSource.onmessage = (event) => {
            const point = JSON.parse(event.data);

            console.log('Incoming point:', point); // Log each incoming point

            // Append to chart data
            setChartData(prevData => ({
                labels: [...prevData.labels, new Date(point.timestamp).toLocaleTimeString()],
                datasets: [{
                    ...prevData.datasets[0],
                    data: [...prevData.datasets[0].data, point.memoryUsage]
                }]
            }));
        };

        eventSource.onerror = (error) => {
            console.error('EventSource failed:', error);
            eventSource.close();
        };

        return () => {
            eventSource.close();
        };
    }, []); // Empty dependency array ensures this effect runs once on mount

    return (
        <div>
            <h2>Memory Usage Graph</h2>
            <Line data={chartData} />
        </div>
    );
};

export default MemoryMonitoringPage;
