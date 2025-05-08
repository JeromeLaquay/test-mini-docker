export default function authHeader() {
  try {
    const user = JSON.parse(localStorage.getItem('user'));
    const token = user?.token || user?.accessToken;

    if (!token) {
      console.warn('No authentication token found');
      return {};
    }

    return {
      'Authorization': `Bearer ${token}`,
      'Content-Type': 'application/json',
      'Accept': 'application/json',
      'X-Requested-With': 'XMLHttpRequest'
    };
  } catch (error) {
    console.error('Error parsing user from localStorage:', error);
    return {};
  }
} 