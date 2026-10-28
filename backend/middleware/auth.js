const jwt = require('jsonwebtoken');
const { fail } = require('../utils/response');

const SECRET = process.env.JWT_SECRET || 'geotracker-secreto-2026';

// Valida el token del header Authorization: Bearer <token>
function auth(req, res, next) {
  const h = req.headers['authorization'] || '';
  const token = h.startsWith('Bearer ') ? h.slice(7) : null;
  if (!token) return fail(res, 401, 'Token requerido');
  try {
    req.user = jwt.verify(token, SECRET);
    next();
  } catch (e) {
    return fail(res, 401, 'Token inválido o vencido');
  }
}

module.exports = { auth, SECRET };
