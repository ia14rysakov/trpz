import React, { useState } from 'react';

const CpuMonitoringPage = () => {
    const [osType, setOsType] = useState('linux');
    const [reportType, setReportType] = useState('ReportByTime');
    const [dueToTime, setDueToTime] = useState('');
    const [isReportGoing, setIsReportGoing] = useState(false);
    const [scheduleStart, setScheduleStart] = useState('');
    const [scheduleEnd, setScheduleEnd] = useState('');

    const handleReportRequest = () => {
        const reportRequestDto = {
            reportType,
            osType,
            // Add other parameters based on reportType
        };

        // Logic to send request to backend
        console.log('Report Request:', reportRequestDto);
    };

    return (
        <div>
            <h2>CPU Usage Graph</h2>
            {/* Existing Line chart component here */}

            <div>
                <h3>Generate Report</h3>
                <div>
                    <label>OS Type:</label>
                    <select value={osType} onChange={e => setOsType(e.target.value)}>
                        <option value="linux">Linux</option>
                        <option value="windows">Windows</option>
                        <option value="macOs">macOS</option>
                    </select>
                </div>
                <div>
                    <label>Report Type:</label>
                    <select value={reportType} onChange={e => setReportType(e.target.value)}>
                        <option value="ReportByTime">Report by Time</option>
                        <option value="ReportStartStop">Report Start/Stop</option>
                        <option value="ScheduledReport">Scheduled Report</option>
                    </select>
                </div>

                {reportType === 'ReportByTime' && (
                    <div>
                        <label>Due To Time:</label>
                        <input
                            type="datetime-local"
                            value={dueToTime}
                            onChange={e => setDueToTime(e.target.value)}
                        />
                    </div>
                )}

                {reportType === 'ReportStartStop' && (
                    <div>
                        <label>Is Report Going:</label>
                        <input
                            type="checkbox"
                            checked={isReportGoing}
                            onChange={e => setIsReportGoing(e.target.checked)}
                        />
                    </div>
                )}

                {reportType === 'ScheduledReport' && (
                    <div>
                        <label>Start Time:</label>
                        <input
                            type="datetime-local"
                            value={scheduleStart}
                            onChange={e => setScheduleStart(e.target.value)}
                        />
                        <label>End Time:</label>
                        <input
                            type="datetime-local"
                            value={scheduleEnd}
                            onChange={e => setScheduleEnd(e.target.value)}
                        />
                    </div>
                )}

                <button onClick={handleReportRequest}>Generate Report</button>
            </div>
        </div>
    );
};

export default CpuMonitoringPage;
