import React, { useState, useEffect } from 'react';
import { useLocation } from 'react-router-dom';
import { Typography, Box } from '@mui/material';

const TestMonitoringPage = () => {
    const [testData, setTestData] = useState([]);
    const location = useLocation();

    useEffect(() => {
        const queryParams = new URLSearchParams(location.search);
        const osType = queryParams.get('osType');

        // Endpoint URL could be something like '/monitoring/testmonitoring'
        const eventSource = new EventSource(`http://localhost:8080/monitoring/testmonitoring?osType=${osType}`);

        eventSource.onmessage = (event) => {
            const newTestPoint = JSON.parse(event.data);
            setTestData(prevTestPoints => [...prevTestPoints, newTestPoint.test]);
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
                Test Monitoring
            </Typography>
            <Typography variant="subtitle1">
                Test Data:
            </Typography>
            <Box component="ul" sx={{ background: '#eee', padding: '10px', borderRadius: '4px', maxHeight: '300px', overflowY: 'auto' }}>
                {testData.map((test, index) => (
                    <Box component="li" key={index} sx={{ listStyleType: 'none' }}>
                        {test}
                    </Box>
                ))}
            </Box>
        </Box>
    );
};

export default TestMonitoringPage;
