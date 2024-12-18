import {displayLoginStatus} from './status.js';

// 获取 URL 参数中的订单ID
function getOrderIdFromUrl() {
    const urlParams = new URLSearchParams(window.location.search);
    return urlParams.get('orderId');
}

// 获取订单详情
async function fetchOrderDetails(orderId) {
    try {
        const token = sessionStorage.getItem('token');
        if (!token) {
            alert('请先登录查看订单详情');
            window.location.href = 'login.html'; // 跳转到登录页面
            return;
        }

        const response = await fetch(`http://localhost:8081/order/${orderId}`, {
            method: 'GET',
            headers: {
                'Authorization': `Bearer ${token}`, // 添加 Bearer Token 进行认证
            },
        });

        const data = await response.json();
        if (data.code === 1) {
            renderOrderDetails(data.data);
        } else {
            alert('获取订单详情失败');
        }
    } catch (error) {
        console.error('获取订单详情失败:', error);
        alert('获取订单详情失败，请稍后再试');
    }
}

// 渲染订单详情
function renderOrderDetails(order) {
    const orderDetailsContainer = document.getElementById('order-details');
    orderDetailsContainer.innerHTML = ''; // 清空之前的内容

    const orderElement = document.createElement('div');
    orderElement.classList.add('order-details-item');
    let itemsHtml = '';

    if (order.orderItem && order.orderItem.length > 0) {
        order.orderItem.forEach(item => {
            itemsHtml += `
                <div class="order-item-details">
                    <p>商品: ${item.title}</p>
                    <p>数量: ${item.quantity}</p>
                    <p>小计: ￥${item.subTotal}</p>
                    <img src="${item.imageUrl || 'default-image.jpg'}" alt="${item.title}" />
                </div>
            `;
        });
    } else {
        itemsHtml = '<p>此订单没有商品。</p>';
    }

    orderElement.innerHTML = `
        <h3>订单ID: ${order.orderId}</h3>
        <p>状态: ${order.orderStatus === 0 ? '未支付' : '已支付'}</p>
        <p>总金额: ￥${order.total}</p>
        <p>下单时间: ${new Date(order.createdAt).toLocaleString()}</p>
        <div class="order-items">${itemsHtml}</div>
    `;

    orderDetailsContainer.appendChild(orderElement);
}

// 返回按钮事件
document.getElementById('backBtn').addEventListener('click', () => {
    window.location.href = 'myOrder.html'; // 返回到我的订单页面
});

// 初始化页面
document.addEventListener('DOMContentLoaded', async () => {
    displayLoginStatus();
    const orderId = getOrderIdFromUrl();
    if (orderId) {
        await fetchOrderDetails(orderId);
    } else {
        alert('无效的订单ID');
    }
});
