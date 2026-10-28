const db = require('../database/db');

const findByUsernameOrEmail = (valor) =>
  db.prepare('SELECT * FROM users WHERE username = ? OR correo = ?').get(valor, valor);

module.exports = { findByUsernameOrEmail };
