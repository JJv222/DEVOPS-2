// Użyje zmiennej środowiskowej API_TARGET, a jak brak — domyślnej wartości
const target = process.env.API_URL;

module.exports = {
  "/api": {
    target,
    secure: false,
    changeOrigin: true,
    logLevel: "debug"
  }
};
