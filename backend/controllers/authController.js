const bcrypt = require('bcryptjs');
const jwt = require('jsonwebtoken');
const userModel = require('../models/userModel');
const { ok, fail } = require('../utils/response');
const { SECRET } = require('../middleware/auth');

const login = (req, res) => {
  const { username, password } = req.body || {};
  if (!username || !password) return fail(res, 400, 'Usuario y contraseña requeridos');

  const user = userModel.findByUsernameOrEmail(username);
  if (!user || !bcrypt.compareSync(password, user.password)) {
    return fail(res, 401, 'Credenciales incorrectas');
  }
  const token = jwt.sign({ id: user.id, username: user.username }, SECRET, { expiresIn: '7d' });
  return ok(res, 'Login exitoso', { token, username: user.username });
};

module.exports = { login };
