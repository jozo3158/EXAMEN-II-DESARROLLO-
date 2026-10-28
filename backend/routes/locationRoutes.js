const router = require('express').Router();
const { auth } = require('../middleware/auth');
const { guardar, listar } = require('../controllers/locationController');

router.post('/', auth, guardar);
router.get('/', auth, listar);

module.exports = router;
