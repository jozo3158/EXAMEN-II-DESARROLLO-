const db = require('../database/db');

const create = ({ userId, nombre, descripcion, latitud, longitud }) => {
  const r = db.prepare(
    'INSERT INTO locations (user_id, nombre, descripcion, latitud, longitud) VALUES (?, ?, ?, ?, ?)'
  ).run(userId, nombre, descripcion || '', latitud, longitud);
  return db.prepare('SELECT * FROM locations WHERE id = ?').get(r.lastInsertRowid);
};

const findByUser = (userId) =>
  db.prepare('SELECT * FROM locations WHERE user_id = ? ORDER BY id DESC').all(userId);

module.exports = { create, findByUser };
