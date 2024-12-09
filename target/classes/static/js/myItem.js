// item.js
import { displayLoginStatus } from './status.js';
// 解码 JWT token
// item.js

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
document.addEventListener('DOMContentLoaded', function() {
    // 这里的代码会在DOM完全加载后执行

    const token = sessionStorage.getItem('token');

    if (!token) {
        window.location.href = 'login.html';  // 跳转到登录页面
    } else {
        displayLoginStatus(); // 调用显示登录状态的函数
        // 解码 token 获取用户 ID
        const decoded = decodeJwt(token);
        if (decoded) {
            const sellerId = decoded.id;  // 获取当前用户ID，用作 sellerId
            fetch(`http://localhost:8081/item/user/${sellerId}`, {
                method: 'GET',
                headers: {
                    'Content-Type': 'application/json',
                    'Authorization': 'Bearer ' + token // 添加 Authorization 头部
                }
            })
                .then(response => response.json())
                .then(data => {
                    console.log('Item data:', data);  // 调试输出返回的商品数据
                    if (data.code === 1) { // 根据后端返回的代码来判断请求是否成功
                        const items = data.data; // 假设返回的商品是一个数组
                        console.log(data.data)
                        const itemListElement = document.getElementById('item-list');
                        itemListElement.innerHTML = '';  // 清空已有的商品信息

                        // 遍历商品列表，动态生成商品信息
                        items.forEach(item => {
                            const itemElement = document.createElement('div');
                            itemElement.classList.add('item');  // 添加一个类名用于样式

                            itemElement.innerHTML = `
                                <h2>${item.title}</h2>
                                <p>描述: ${item.description}</p>
                                <p>价格: ${item.price}</p>
                                <p>卖家: ${sellerId}</p>
                                <p>库存: ${item.quantity}</p>
                                <p>状态: ${item.status === 1 ? '在售' : '已下架'}</p>
                                <p>创建时间: ${item.createdAt}</p>
                                <p>更新时间: ${item.updatedAt}</p>
                                <img src="${item.imageUrl}" alt="${item.title}" />
                            `;
                            itemListElement.appendChild(itemElement);
                        });
                    } else {
                        alert('获取商品信息失败：' + data.msg);
                    }
                })
                .catch(error => {
                    alert('获取商品信息失败：' + error);
                });
        } else {
            alert('Token 解码失败，请重新登录');
            window.location.href = 'login.html';  // 如果解码失败，跳转到登录页
        }
    }
});




// 添加商品功能
// 获取元素
const addItemBtn = document.getElementById('addItemBtn');
const addItemModal = document.getElementById('addItemModal');
const closeModal = document.getElementById('closeModal');
const addItemForm = document.getElementById('addItemForm');

// 显示添加商品模态框
addItemBtn.addEventListener('click', function() {
    addItemModal.style.display = 'block'; // 显示模态框
});

// 关闭模态框
closeModal.addEventListener('click', function() {
    addItemModal.style.display = 'none'; // 隐藏模态框
});

// 点击模态框外部关闭模态框
window.addEventListener('click', function(event) {
    if (event.target === addItemModal) {
        addItemModal.style.display = 'none';
    }
});

// 处理添加商品表单提交
addItemForm.addEventListener('submit', function(event) {
    event.preventDefault(); // 阻止默认提交

    // 获取表单数据
    const title = document.getElementById('itemTitle').value;
    const description = document.getElementById('itemDescription').value;
    const price = document.getElementById('itemPrice').value;
    const quantity = document.getElementById('itemQuantity').value;
    const categoryId = document.getElementById('itemCategoryId').value;
    const imageUrl = document.getElementById('itemImage').value;

    // 发送请求到后端添加商品
    fetch('http://localhost:8081/item/add', {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json',
            'Authorization': 'Bearer ' + sessionStorage.getItem('token') // 添加 Authorization 头部
        },
        body: JSON.stringify({
            title,
            description,
            price,
            quantity,
            categoryId,
            imageUrl
        })
    })
        .then(response => response.json())
        .then(data => {
            if (data.code === 1) {
                alert('商品添加成功');
                window.location.reload(); // 刷新页面显示新增商品
            } else {
                alert('商品添加失败：' + data.msg);
            }
        })
        .catch(error => {
            alert('商品添加失败：' + error);
        });

    // 关闭模态框
    addItemModal.style.display = 'none';
});