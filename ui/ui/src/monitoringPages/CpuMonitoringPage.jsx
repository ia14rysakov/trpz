import React, { useState, useEffect } from 'react';
import { Line } from 'react-chartjs-2';
import { useLocation } from 'react-router-dom';
import 'chart.js/auto';
import {Box, Button, FormControl, InputLabel, MenuItem, Select, TextField, Typography} from "@mui/material";

const CpuMonitoringPage = () => {
    const [osType, setOsType] = useState('Windows');
    const [reportType, setReportType] = useState('ReportByTime');
    const [dueToTime, setDueToTime] = useState('');
    const [isReportGoing, setIsReportGoing] = useState(false);
    const [scheduleStartTime, setScheduleStart] = useState('');
    const [scheduleEndTime, setScheduleEnd] = useState('');
    const location = useLocation();

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
    const handleReportRequest = () => {
        const reportRequestDto = {
            reportType,
            monitoringType: "cpuLoad",
            osType,
            dueToTime: reportType === 'ReportByTime' ? dueToTime : undefined,
            isReportGoing: reportType === 'ReportStartStop' ? isReportGoing : undefined,
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
    }, []); // Empty dependency array ensures this effect runs once on mount

    return (
    <Box sx={{ padding: '20px' }}>
        <Typography variant="h4" gutterBottom>
            Cpu Monitoring
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

        {/* Existing Test Data Display */}
        <Typography variant="subtitle1" sx={{ marginTop: '20px' }}>
            CPU Usage Graph:
        </Typography>
        <Line data={chartData} />
    </Box>
    );
};

export default CpuMonitoringPage;
