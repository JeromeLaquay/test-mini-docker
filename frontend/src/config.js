const config = {
  API_URL: process.env.NODE_ENV === 'production' 
    ? 'http://localhost:8081/api'  // URL pour Docker
    : 'http://localhost:8081/api'     // URL pour le développement local
};

export default config; 