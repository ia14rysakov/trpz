import React, { useState, useEffect } from 'react';
import { useLocation } from 'react-router-dom';
import { Typography, Box } from '@mui/material';

const MouseTrackerMonitoringPage = () => {
    const [mousePositions, setMousePositions] = useState([]);
    const location = useLocation();

    useEffect(() => {
        const queryParams = new URLSearchParams(location.search);
        const osType = queryParams.get('osType');

        // Endpoint URL could be something like '/monitoring/mousetracker'
        const eventSource = new EventSource(`http://localhost:8080/monitoring/mousetracker?osType=${osType}`);

        eventSource.onmessage = (event) => {
            const newPosition = JSON.parse(event.data);
            setMousePositions(prevPositions => [...prevPositions, `<${newPosition.x};${newPosition.y}>`]);
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
                Mouse Tracker Monitoring
            </Typography>
            <Typography variant="subtitle1">
                Mouse Positions:
            </Typography>
            <Box component="ul" sx={{ background: '#eee', padding: '10px', borderRadius: '4px', maxHeight: '300px', overflowY: 'auto' }}>
                {mousePositions.map((position, index) => (
                    <Box component="li" key={index} sx={{ listStyleType: 'none' }}>
                        {position}
                    </Box>
                ))}
            </Box>
        </Box>
    );
};

export default MouseTrackerMonitoringPage;
