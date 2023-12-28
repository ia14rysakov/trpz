import React, { useState } from 'react';
import axios from 'axios';

const CpuMonitoringPage = () => {
    const [monitoringType, setMonitoringType] = useState('');
    const [osType, setOsType] = useState('');
    const [monitoringPoints, setMonitoringPoints] = useState([]);

    const handleSubmit = async (event) => {
        event.preventDefault();

        try {
            const response = await axios.post('http://localhost:8080/start', {
                monitoringType: "cpuLoad",
                osType: "Windows"
            });

            setMonitoringPoints(response.data); // assuming the data is an array of MonitoringPoints
        } catch (error) {
            console.error('Error sending monitoring request:', error);
        }
    };

    return (
        <div>
            <form onSubmit={handleSubmit}>
                <label>
                    Monitoring Type:
                    <input type="text" value={monitoringType} onChange={(e) => setMonitoringType(e.target.value)} />
                </label>
                <br />
                <label>
                    OS Type:
                    <input type="text" value={osType} onChange={(e) => setOsType(e.target.value)} />
                </label>
                <br />
                <button type="submit">Start Monitoring</button>
            </form>

            {monitoringPoints.length > 0 && (
                <div>
                    <h3>Monitoring Points:</h3>
                    <ul>
                        {monitoringPoints.map((point, index) => (
                            <li key={index}>{JSON.stringify(point)}</li>
                        ))}
                    </ul>
                </div>
            )}
        </div>
    );
};

export default CpuMonitoringPage;
