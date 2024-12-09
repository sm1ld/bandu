// login.js
import { displayLoginStatus } from './status.js';

// 登录请求函数
export function login(username, password) {
    return fetch('http://localhost:8081/user/login', {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json'
        },
        body: JSON.stringify({ username, password })
    })
        .then(response => response.json())
        .catch(error => {
            console.error('登录请求失败:', error);
            throw new Error('登录失败，请重试');
        });
}

// 处理登录响应
export function handleLoginResponse(data) {
    if (data.code === 1) {
        const token = data.data; // 假设 token 存储在 `data.data` 中
        sessionStorage.setItem('token', token); // 将 token 存储到 sessionStorage
        // 获取之前访问的页面类型
        const previousPage = sessionStorage.getItem('previousPage');

        // 根据之前的页面跳转
        if (previousPage === 'item') {
            window.location.href = 'item.html?id=' + sessionStorage.getItem('itemId');  // 根据商品ID返回商品详情
        } else if (previousPage === 'profile') {
            window.location.href = 'profile.html';  // 返回个人主页
        } else {
            window.location.href = 'index.html';  // 默认返回主页或其他页面
        }
    } else {
        alert(data.msg || '用户名或密码错误');
    }
}

// 等待 DOM 加载完成后再执行
document.addEventListener('DOMContentLoaded', function () {
    const loginForm = document.getElementById('loginForm');

    if (loginForm) {
        // 监听表单提交事件
        loginForm.addEventListener('submit', function(event) {
            event.preventDefault(); // 阻止表单提交的默认行为

            const username = document.getElementById('loginUsername').value;
            const password = document.getElementById('loginPassword').value;

            // 调用登录函数并处理响应
            login(username, password)
                .then(data => {
                    handleLoginResponse(data); // 处理登录响应
                })
                .catch(error => {
                    console.error('登录请求失败:', error);
                    alert('登录失败，请重试');
                });
        });
    } else {
        console.error('找不到登录表单元素');
    }
});
