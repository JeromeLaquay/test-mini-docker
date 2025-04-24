export default function authHeader() {
  try {
    let userStr = localStorage.getItem('user');
    if (!userStr) {
      return {
        'Content-Type': 'application/json'
      };
    }

    let user = JSON.parse(userStr);
    
    if (user && user.token) {
      return { 
        'Authorization': 'Bearer ' + user.token,
        'Content-Type': 'application/json'
      };
    } else if (user && user.accessToken) {
      return { 
        'Authorization': 'Bearer ' + user.accessToken,
        'Content-Type': 'application/json'
      };
    } else {
      return {
        'Content-Type': 'application/json'
      };
    }
  } catch (error) {
    console.error('Erreur dans authHeader:', error);
    return {
      'Content-Type': 'application/json'
    };
  }
} 