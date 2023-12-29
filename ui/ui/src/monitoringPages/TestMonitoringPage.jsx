import React, { useState, useEffect } from 'react';
import { useLocation } from 'react-router-dom';
import { Typography, Box, Select, MenuItem, FormControl, InputLabel, TextField, Button } from '@mui/material';

const TestMonitoringPage = () => {
    const [testData, setTestData] = useState([]);
    const [osType, setOsType] = useState('Windows');
    const [reportType, setReportType] = useState('ReportByTime');
    const [dueToTime, setDueToTime] = useState('');
    const [isReportGoing, setIsReportGoing] = useState(false);
    const [scheduleStart, setScheduleStart] = useState('');
    const [scheduleEnd, setScheduleEnd] = useState('');
    const location = useLocation();

    useEffect(() => {
        const eventSource = new EventSource(`http://localhost:8080/monitoring/test`);
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

    const handleReportRequest = () => {
        const reportRequestDto = {
            reportType,
            monitoringType: "test",
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

    return (
        <Box sx={{ padding: '20px' }}>
            <Typography variant="h4" gutterBottom>
                Test Monitoring
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
                        <MenuItem value="ReportStartStop">Report Start/Stop</MenuItem>
                        <MenuItem value="ScheduledReport">Scheduled Report</MenuItem>
                    </Select>
                </FormControl>

                {/* Conditional Inputs */}
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

                {reportType === 'ReportStartStop' && (
                    <FormControl fullWidth margin="normal">
                        <InputLabel>Is Report Going</InputLabel>
                        <Select value={isReportGoing} label="Is Report Going" onChange={e => setIsReportGoing(e.target.value)}>
                            <MenuItem value={true}>Yes</MenuItem>
                            <MenuItem value={false}>No</MenuItem>
                        </Select>
                    </FormControl>
                )}

                {reportType === 'ScheduledReport' && (
                    <>
                        <TextField
                            label="Start Time"
                            type="datetime-local"
                            value={scheduleStart}
                            onChange={e => setScheduleStart(e.target.value)}
                            fullWidth
                            margin="normal"
                        />
                        <TextField
                            label="End Time"
                            type="datetime-local"
                            value={scheduleEnd}
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

            {/* Existing Test Data Display */}
            <Typography variant="subtitle1" sx={{ marginTop: '20px' }}>
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
