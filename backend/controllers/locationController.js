const locationModel = require('../models/locationModel');
const { ok, fail } = require('../utils/response');

const guardar = (req, res) => {
  const { nombre, descripcion, latitud, longitud } = req.body || {};
  if (!nombre || latitud === undefined || longitud === undefined) {
    return fail(res, 400, 'Nombre, latitud y longitud son requeridos');
  }
  const loc = locationModel.create({ userId: req.user.id, nombre, descripcion, latitud, longitud });
  return ok(res, 'Ubicación guardada', loc, 201);
};

const listar = (req, res) => {
  const rows = locationModel.findByUser(req.user.id);
  return ok(res, 'Ubicaciones obtenidas', rows);
};

module.exports = { guardar, listar };
