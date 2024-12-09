//profile.js
// user.js
import { displayLoginStatus } from './status.js';

// 解码 JWT token
function decodeJwt(token) {
    if (!token || token.split('.').length !== 3) {
        console.error('Invalid JWT token');
        return null;
    }

    // 获取 payload 部分
    const base64Url = token.split('.')[1];

    // 将 Base64Url 转换为标准 Base64，并补全长度
    const base64 = base64Url.replace(/-/g, '+').replace(/_/g, '/');
    const paddedBase64 = base64.padEnd(Math.ceil(base64.length / 4) * 4, '=');

    try {
        // 解码 Base64 为字符串
        const jsonPayload = decodeURIComponent(atob(paddedBase64).split('').map(function(c) {
            return '%' + ('00' + c.charCodeAt(0).toString(16)).slice(-2);
        }).join(''));

        return JSON.parse(jsonPayload); // 返回解析后的 JSON 对象
    } catch (error) {
        console.error('Failed to decode JWT:', error);
        return null; // 如果解码失败，返回 null
    }
}

// 获取用户的token
const token = sessionStorage.getItem('token');

if (!token) {
    window.location.href = 'login.html';  // 跳转到登录页面
} else {
    displayLoginStatus(); // 调用显示登录状态的函数
    // 解码 token 获取用户 ID
    const decoded = decodeJwt(token);
    if (decoded) {
        const userId = decoded.id;  // 获取用户 ID
        fetch(`http://localhost:8081/user/${userId}`, {
            method: 'GET',
            headers: {
                'Content-Type': 'application/json',
                'Authorization': 'Bearer ' + token // 添加 Authorization 头部
            }
        })
            .then(response => response.json())
            .then(data => {
                console.log('User data:', data);  // 调试输出返回的用户数据
                if (data.code === 1) { // 根据后端返回的代码来判断请求是否成功
                    const user = data.data;
                    document.getElementById('username').textContent = user.username;
                    document.getElementById('userId').textContent = user.id;
                    document.getElementById('email').textContent = user.email;
                    document.getElementById('phoneNumber').textContent = user.phoneNumber;
                    document.getElementById('address').textContent = user.address;
                    document.getElementById('role').textContent = user.role === 0 ? '普通用户' : '管理员';  // 假设 0 是普通用户，1 是管理员
                    document.getElementById('createdAt').textContent = user.createdAt;
                    document.getElementById('updatedAt').textContent = user.updatedAt;
                } else {
                    alert('获取用户信息失败：' + data.msg);
                }
            })
            .catch(error => {
                alert('获取用户信息失败：' + error);
            });
    } else {
        alert('Token 解码失败，请重新登录');
        window.location.href = 'login.html';  // 如果解码失败，跳转到登录页
    }
}

// 退出登录
document.getElementById('logoutBtn').addEventListener('click', function() {
    sessionStorage.removeItem('token');
    window.location.href = 'index.html';
});



