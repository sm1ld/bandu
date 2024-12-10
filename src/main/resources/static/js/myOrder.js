import {displayLoginStatus} from './status.js';

// 获取当前用户的订单信息
async function fetchOrders() {
    try {
        const token = sessionStorage.getItem('token');
        if (!token) {
            alert('请先登录查看订单');
            window.location.href = 'login.html'; // 跳转到登录页面
            return;
        }

        const response = await fetch('http://localhost:8081/order/view', {
            method: 'GET',
            headers: {
                'Authorization': `Bearer ${token}`, // 添加 Bearer Token 进行认证
            },
        });

        const data = await response.json();
        if (data.code === 1) {
            renderOrders(data.data);
        } else {
            alert('获取订单失败');
        }
    } catch (error) {
        console.error('获取订单数据失败:', error);
        alert('获取订单数据失败，请稍后再试');
    }
}

// 渲染当前订单列表
function renderOrders(orders) {
    const ordersList = document.getElementById('orders-list');
    ordersList.innerHTML = ''; // 清空之前的订单内容

    orders.forEach(order => {
        const orderElement = document.createElement('div');
        orderElement.classList.add('order-item');

        let itemsHtml = '';
        order.orderItem.forEach(item => {
            itemsHtml += `
                <div class="order-item-details">
                    <p>商品: ${item.title}</p>
                    <p>数量: ${item.number}</p>
                    <p>小计: ￥${item.subTotal}</p>
                </div>
            `;
        });

        orderElement.innerHTML = `
            <h3>订单ID: ${order.orderId}</h3>
            <p>状态: ${order.orderStatus === 0 ? '未支付' : '已支付'}</p>
            <p>总金额: ￥${order.total}</p>
            <p>下单时间: ${new Date(order.createdAt).toLocaleString()}</p>
            <div class="order-items">${itemsHtml}</div>
        `;
        ordersList.appendChild(orderElement);
    });
}


// 初始化页面
document.addEventListener('DOMContentLoaded', async () => {
    displayLoginStatus();
    await fetchOrders(); // 获取并渲染订单数据
});



