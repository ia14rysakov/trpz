import React, { useState } from 'react';
import { useNavigate } from 'react-router-dom';
import { Box, Button, Container, MenuItem, Select, Typography, FormControl, InputLabel } from '@mui/material';

const HomePage = () => {
    const [monitoringType, setMonitoringType] = useState('');
    const [osType, setOsType] = useState('');
    const navigate = useNavigate(); // use `navigate` directly

    const handleMonitoringTypeChange = (event) => {
        setMonitoringType(event.target.value);
    };

    const handleOsTypeChange = (event) => {
        setOsType(event.target.value);
    };

    const navigateToMonitoring = () => {
        navigate(`/${monitoringType}?monitoringType=${monitoringType}&osType=${osType}`);
    };

    return (
        <Box
            sx={{
                display: 'flex',
                flexDirection: 'column',
                minHeight: '100vh',
                alignItems: 'center',
                justifyContent: 'center',
                backgroundColor: '#282c34'
            }}
        >
            <Container maxWidth="sm">
                <Typography variant="h2" component="h1" gutterBottom sx={{ color: 'white' }}>
                    My Monitoring Application
                </Typography>
                <FormControl fullWidth sx={{ mb: 2 }}>
                    <InputLabel id="monitoring-type-label">Monitoring Type</InputLabel>
                    <Select
                        labelId="monitoring-type-label"
                        id="monitoringType"
                        value={monitoringType}
                        label="Monitoring Type"
                        onChange={handleMonitoringTypeChange}
                    >
                        <MenuItem value="cpuLoad">CPU Load</MenuItem>
                        <MenuItem value="keyLogger">Key Logger</MenuItem>
                        <MenuItem value="memory">Memory</MenuItem>
                        <MenuItem value="mouseTracker">Mouse Tracker</MenuItem>
                        <MenuItem value="windows">Windows</MenuItem>
                        <MenuItem value="test">Test</MenuItem>
                    </Select>
                </FormControl>
                <FormControl fullWidth sx={{ mb: 4 }}>
                    <InputLabel id="os-type-label">OS Type</InputLabel>
                    <Select
                        labelId="os-type-label"
                        id="os-type"
                        value={osType}
                        label="OS Type"
                        onChange={handleOsTypeChange}
                    >
                        <MenuItem value="Windows">Windows</MenuItem>
                        <MenuItem value="Linux">Linux</MenuItem>
                        <MenuItem value="macOS">macOS</MenuItem>
                    </Select>
                </FormControl>
                <Button variant="contained" color="primary" onClick={navigateToMonitoring} disableElevation>
                    Start Monitoring
                </Button>
            </Container>
        </Box>
    );
};

export default HomePage;
