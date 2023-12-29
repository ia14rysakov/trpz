import React, { useState, useEffect } from 'react';
import { useLocation } from 'react-router-dom';
import { Typography, Box } from '@mui/material';

const KeyLoggerMonitoringPage = () => {
    const [keysPressed, setKeysPressed] = useState([]);
    const location = useLocation();

    const handleReportRequest = () => {
        const reportRequestDto = {
            reportType,
            monitoringType: "keyLogger",
            osType,
            dueToTime: reportType === 'ReportByTime' ? dueToTime : undefined,
            isReportGoing: reportType === 'ReportStartStop' ? isReportGoing : undefined,
            scheduleStart: reportType === 'ScheduledReport' ? scheduleStart : undefined,
            scheduleEnd: reportType === 'ScheduledReport' ? scheduleEnd : undefined,
        };

        fetch('http://localhost:8080/report/download', {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json',
            },
            body: JSON.stringify(reportRequestDto),
        })
            .then(response => {
                if (!response.ok) {
                    throw new Error('Network response was not ok');
                }
                return response.blob();
            })
            .then(blob => {
                const url = window.URL.createObjectURL(blob);
                const a = document.createElement('a');
                a.href = url;
                a.download = 'report.pdf';
                document.body.appendChild(a);
                a.click();
                a.remove();
                window.URL.revokeObjectURL(url);
            })
            .catch(error => {
                console.error('Fetch error:', error);
            });
    };


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
                    data: [...prevData.datasets[0].data, point.cpuUsage]
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
    }, []);

    useEffect(() => {
        const queryParams = new URLSearchParams(location.search);
        const osType = queryParams.get('osType');
        const monitoringType = queryParams.get('monitoringType');

        const eventSource = new EventSource(`http://localhost:8080/monitoring/${monitoringType}/${osType}`);

        eventSource.onmessage = (event) => {
            const newKey = JSON.parse(event.data);
            setKeysPressed((prevKeys) => [...prevKeys, newKey.lastKeyPressed]);
        };

        eventSource.onerror = (error) => {
            console.error('EventSource failed:', error);
            eventSource.close();
        };

        return () => {
            eventSource.close();
        };
    }, [location]);

    return (
        <Box sx={{ padding: '20px' }}>
            <Typography variant="h4" gutterBottom>
                KeyLogger Monitoring
            </Typography>
            <Typography variant="subtitle1">
                Keys Pressed:
            </Typography>
            <Box component="ul" sx={{ background: '#eee', padding: '10px', borderRadius: '4px', maxHeight: '300px', overflowY: 'auto' }}>
                {keysPressed.map((key, index) => (
                    <Box component="li" key={index} sx={{ listStyleType: 'none' }}>
                        {key}
                    </Box>
                ))}
            </Box>
        </Box>
    );
};

export default KeyLoggerMonitoringPage;
