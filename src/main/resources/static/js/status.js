//status.js
// import jwt_decode from 'jwt-decode'; // 正确的导入方式

// 获取登录状态并显示
export function displayLoginStatus() {
    const token = sessionStorage.getItem('token');
    const loginStatusElement = document.getElementById('loginStatus');

    if (token) {
        const decoded = decodeJwt(token);
        if (decoded && decoded.username) {
            loginStatusElement.textContent = `${decoded.username} 已登录`;
        } else {
            loginStatusElement.textContent = '当前未登录'; // token 无效
        }
    } else {
        loginStatusElement.textContent = '当前未登录'; // 没有 token
    }
}

// 解码 JWT token
function decodeJwt(token) {
    if (!token || token.split('.').length !== 3) {
        console.error('Invalid JWT token');
        return null;
    }

    const base64Url = token.split('.')[1];
    const base64 = base64Url.replace(/-/g, '+').replace(/_/g, '/');
    const paddedBase64 = base64.padEnd(Math.ceil(base64.length / 4) * 4, '=');

    try {
        const jsonPayload = decodeURIComponent(atob(paddedBase64).split('').map(function(c) {
            return '%' + ('00' + c.charCodeAt(0).toString(16)).slice(-2);
        }).join(''));
        return JSON.parse(jsonPayload);
    } catch (error) {
        console.error('Failed to decode JWT:', error);
        return null;
    }
}
