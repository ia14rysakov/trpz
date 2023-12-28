import React, { useState, useEffect } from 'react';
import { useLocation } from 'react-router-dom';
import { Typography, Box } from '@mui/material';

const KeyLoggerMonitoringPage = () => {
    const [keysPressed, setKeysPressed] = useState([]);
    const location = useLocation();

    useEffect(() => {
        const queryParams = new URLSearchParams(location.search);
        const osType = queryParams.get('osType');

        // Endpoint URL could be something like '/monitoring/keylogger'
        const eventSource = new EventSource(`http://localhost:8080/monitoring/keylogger?osType=${osType}`);

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
