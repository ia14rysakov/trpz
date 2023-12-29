import React, { useState, useEffect } from 'react';
import { useLocation } from 'react-router-dom';
import {
    Typography, Box, Paper, Table, TableBody, TableCell, TableContainer, TableHead, TableRow,
    Select, MenuItem, FormControl, InputLabel, TextField, Button
} from '@mui/material';

const WindowsMonitoringPage = () => {
    const getCurrentDateTime = () => {
        const now = new Date();
        const year = now.getFullYear();
        const month = now.getMonth() + 1;
        const day = now.getDate();
        const hours = now.getHours();
        const minutes = now.getMinutes();
        return `${year}-${month.toString().padStart(2, '0')}-${day.toString().padStart(2, '0')}T${hours.toString().padStart(2, '0')}:${minutes.toString().padStart(2, '0')}`;
    };

    const [latestWindowsData, setLatestWindowsData] = useState([]);
    const [osType, setOsType] = useState('Windows');
    const [reportType, setReportType] = useState('ReportByTime');
    const [dueToTime, setDueToTime] = useState(getCurrentDateTime());
    const [scheduleStartTime, setScheduleStart] = useState(getCurrentDateTime());
    const [scheduleEndTime, setScheduleEnd] = useState(getCurrentDateTime());

    const location = useLocation();

    useEffect(() => {
        const queryParams = new URLSearchParams(location.search);
        const osType = queryParams.get('osType');
        const monitoringType = queryParams.get('monitoringType');

        const eventSource = new EventSource(`http://localhost:8080/monitoring/${monitoringType}/${osType}`);

        eventSource.onmessage = (event) => {
            const newWindowsPoint = JSON.parse(event.data);
            setLatestWindowsData(newWindowsPoint.windowsPoints);
        };

        eventSource.onerror = (error) => {
            console.error('EventSource failed:', error);
            eventSource.close();
        };

        return () => {
            eventSource.close();
        };
    }, [location]);

    const handleReportRequest = () => {
        const reportRequestDto = {
            reportType,
            monitoringType: "windows",
            osType,
            dueToTime: reportType === 'ReportByTime' ? dueToTime : undefined,
            scheduleStartTime: reportType === 'ScheduledReport' ? scheduleStartTime : undefined,
            scheduleEndTime: reportType === 'ScheduledReport' ? scheduleEndTime : undefined,
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

    return (
        <Box sx={{ padding: '20px' }}>
            <Typography variant="h4" gutterBottom>
                Windows Monitoring
            </Typography>

            {/* Report Generation Form */}
            <Box sx={{ marginTop: '20px' }}>
                <FormControl fullWidth margin="normal">
                    <InputLabel>OS Type</InputLabel>
                    <Select value={osType} label="OS Type" onChange={e => setOsType(e.target.value)}>
                        <MenuItem value="Windows">Windows</MenuItem>
                        <MenuItem value="linux">Linux</MenuItem>
                        <MenuItem value="macOs">macOS</MenuItem>
                    </Select>
                </FormControl>

                <FormControl fullWidth margin="normal">
                    <InputLabel>Report Type</InputLabel>
                    <Select value={reportType} label="Report Type" onChange={e => setReportType(e.target.value)}>
                        <MenuItem value="ReportByTime">Report by Time</MenuItem>
                        <MenuItem value="ScheduledReport">Scheduled Report</MenuItem>
                    </Select>
                </FormControl>

                {reportType === 'ReportByTime' && (
                    <TextField
                        label="Due To Time"
                        type="datetime-local"
                        value={dueToTime}
                        onChange={e => setDueToTime(e.target.value)}
                        fullWidth
                        margin="normal"
                    />
                )}

                {reportType === 'ScheduledReport' && (
                    <>
                        <TextField
                            label="Start Time"
                            type="datetime-local"
                            value={scheduleStartTime}
                            onChange={e => setScheduleStart(e.target.value)}
                            fullWidth
                            margin="normal"
                        />

                        <TextField
                            label="End Time"
                            type="datetime-local"
                            value={scheduleEndTime}
                            onChange={e => setScheduleEnd(e.target.value)}
                            fullWidth
                            margin="normal"
                        />
                    </>
                )}

                <Button variant="contained" onClick={handleReportRequest} sx={{ marginTop: '20px' }}>
                    Generate Report
                </Button>
            </Box>

            {/* Windows Data Display */}
            <TableContainer component={Paper} sx={{ marginTop: '20px' }}>
                <Table sx={{ minWidth: 650 }} aria-label="simple table">
                    <TableHead>
                        <TableRow>
                            <TableCell>Window Name</TableCell>
                            <TableCell align="right">Window Size</TableCell>
                        </TableRow>
                    </TableHead>
                    <TableBody>
                        {latestWindowsData.map((window, index) => (
                            <TableRow key={index}>
                                <TableCell component="th" scope="row">
                                    {window.windowName}
                                </TableCell>
                                <TableCell align="right">{window.windowSize}</TableCell>
                            </TableRow>
                        ))}
                    </TableBody>
                </Table>
            </TableContainer>
        </Box>
    );
};

export default WindowsMonitoringPage;
