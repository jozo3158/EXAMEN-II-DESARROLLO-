const Database = require('better-sqlite3');
const bcrypt = require('bcryptjs');

const db = new Database('geotracker.db');

db.exec(`
CREATE TABLE IF NOT EXISTS users (
  id INTEGER PRIMARY KEY AUTOINCREMENT,
  username TEXT UNIQUE NOT NULL,
  correo TEXT UNIQUE,
  password TEXT NOT NULL
);
CREATE TABLE IF NOT EXISTS locations (
  id INTEGER PRIMARY KEY AUTOINCREMENT,
  user_id INTEGER NOT NULL,
  nombre TEXT NOT NULL,
  descripcion TEXT,
  latitud REAL NOT NULL,
  longitud REAL NOT NULL,
  fecha_creacion TEXT DEFAULT CURRENT_TIMESTAMP,
  FOREIGN KEY (user_id) REFERENCES users(id)
);
`);

// Dos usuarios de prueba para el catedratico
[['usuario1', 'usuario1@geotracker.com'], ['usuario2', 'usuario2@geotracker.com']].forEach(([u, c]) => {
  if (!db.prepare('SELECT id FROM users WHERE username = ?').get(u)) {
    db.prepare('INSERT INTO users (username, correo, password) VALUES (?, ?, ?)')
      .run(u, c, bcrypt.hashSync('Clave123', 10));
  }
});

module.exports = db;
