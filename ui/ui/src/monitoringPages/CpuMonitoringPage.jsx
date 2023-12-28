import React, { useState, useEffect } from 'react';
import axios from 'axios';
import { Line } from 'react-chartjs-2';
import 'chart.js/auto';

const CpuMonitoringPage = () => {
    const [chartData, setChartData] = useState({
        labels: [],
        datasets: [
            {
                label: 'CPU Usage',
                data: [],
                borderColor: 'rgba(75, 192, 192, 1)',
                borderWidth: 1,
            },
        ],
    });

    useEffect(() => {
        const fetchData = async () => {
            try {
                const response = await axios.post('http://localhost:8080/monitoring/start', {
                    monitoringType: "cpuLoad",
                    osType: "Windows"
                });

                // Adjusting for CPUMonitoringPoint structure
                const labels = response.data.map(point =>
                    new Date(point.timestamp).toLocaleTimeString()
                );
                const data = response.data.map(point => point.cpuUsage);

                setChartData({
                    ...chartData,
                    labels,
                    datasets: [{ ...chartData.datasets[0], data }]
                });
            } catch (error) {
                console.error('Error sending monitoring request:', error);
            }
        };

        fetchData();
    }, []); // Empty dependency array ensures this effect runs once on mount

    return (
        <div>
            <h2>CPU Usage Graph</h2>
            <Line data={chartData} />
        </div>
    );
};

export default CpuMonitoringPage;
