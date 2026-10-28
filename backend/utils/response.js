// Respuesta estandar: { success, message, data }
const ok = (res, message, data = null, code = 200) =>
  res.status(code).json({ success: true, message, data });

const fail = (res, code, message) =>
  res.status(code).json({ success: false, message, data: null });

module.exports = { ok, fail };
