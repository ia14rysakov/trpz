import React, { useState, useEffect } from 'react';
import { useLocation } from 'react-router-dom';
import { Typography, Box, Paper, Table, TableBody, TableCell, TableContainer, TableHead, TableRow } from '@mui/material';

const WindowsMonitoringPage = () => {
    const [windowsData, setWindowsData] = useState([]);
    const location = useLocation();

    useEffect(() => {
        const queryParams = new URLSearchParams(location.search);
        const osType = queryParams.get('osType');

        const eventSource = new EventSource(`http://localhost:8080/monitoring/windows?osType=${osType}`);

        eventSource.onmessage = (event) => {
            const newWindowsPoint = JSON.parse(event.data);
            setWindowsData(newWindowsPoint.windowsPoints);
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
                Windows Monitoring
            </Typography>
            <TableContainer component={Paper}>
                <Table sx={{ minWidth: 650 }} aria-label="simple table">
                    <TableHead>
                        <TableRow>
                            <TableCell>Window Name</TableCell>
                            <TableCell align="right">Window Size</TableCell>
                            <TableCell align="right">CPU Usage (%)</TableCell>
                            <TableCell align="right">Memory Usage (%)</TableCell>
                        </TableRow>
                    </TableHead>
                    <TableBody>
                        {windowsData.map((window, index) => (
                            <TableRow
                                key={index}
                                sx={{ '&:last-child td, &:last-child th': { border: 0 } }}
                            >
                                <TableCell component="th" scope="row">
                                    {window.windowName}
                                </TableCell>
                                <TableCell align="right">{window.windowSize}</TableCell>
                                <TableCell align="right">{window.cpuUsage.toFixed(2)}</TableCell>
                                <TableCell align="right">{window.memoryUsage.toFixed(2)}</TableCell>
                            </TableRow>
                        ))}
                    </TableBody>
                </Table>
            </TableContainer>
        </Box>
    );
};

export default WindowsMonitoringPage;
