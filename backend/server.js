const express = require('express');
const cors = require('cors');
const { ok } = require('./utils/response');

require('./database/db');

const app = express();
app.use(cors());
app.use(express.json());

app.use('/api', require('./routes/authRoutes'));
app.use('/api/locations', require('./routes/locationRoutes'));
app.get('/', (req, res) => ok(res, 'GeoTracker API activa'));

app.listen(3000, () => console.log('Servidor en puerto 3000'));
