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

// 渲染订单列表
function renderOrders(orders) {
    const ordersList = document.getElementById('orders-list');
    ordersList.innerHTML = ''; // 清空之前的订单内容

    orders.forEach(order => {
        const orderElement = document.createElement('div');
        orderElement.classList.add('order-item');
        orderElement.innerHTML = `
            <h3>订单ID: ${order.orderId}</h3>
            <p>状态: ${order.orderStatus === 0 ? '未支付' : '已支付'}</p>
            <p>总金额: ￥${order.total}</p>
            <p>下单时间: ${new Date(order.createdAt).toLocaleString()}</p>
        `;

        // 给每个订单添加点击事件
        orderElement.addEventListener('click', () => {
            window.location.href = `order.html?orderId=${order.orderId}`; // 跳转到订单详情页面
        });

        ordersList.appendChild(orderElement);

    })
}

// 初始化页面
document.addEventListener('DOMContentLoaded', async () => {
        displayLoginStatus();
        await fetchOrders(); // 获取并渲染订单数据
    });


// import {displayLoginStatus} from './status.js';
//
// // 获取当前用户的订单信息
// async function fetchOrders() {
//     try {
//         const token = sessionStorage.getItem('token');
//         if (!token) {
//             alert('请先登录查看订单');
//             window.location.href = 'login.html'; // 跳转到登录页面
//             return;
//         }
//
//         const response = await fetch('http://localhost:8081/order/view', {
//             method: 'GET',
//             headers: {
//                 'Authorization': `Bearer ${token}`, // 添加 Bearer Token 进行认证
//             },
//         });
//
//         const data = await response.json();
//         if (data.code === 1) {
//             renderOrders(data.data);
//         } else {
//             alert('获取订单失败');
//         }
//     } catch (error) {
//         console.error('获取订单数据失败:', error);
//         alert('获取订单数据失败，请稍后再试');
//     }
// }
//
// // 渲染订单列表
// function renderOrders(orders) {
//     const ordersList = document.getElementById('orders-list');
//     ordersList.innerHTML = ''; // 清空之前的订单内容
//
//     orders.forEach(order => {
//         const orderElement = document.createElement('div');
//         orderElement.classList.add('order-item');
//         let itemsHtml = '';
//         orderElement.innerHTML = `
//             <h3>订单ID: ${order.orderId}</h3>
//             <p>状态: ${order.orderStatus === 0 ? '未支付' : '已支付'}</p>
//             <p>总金额: ￥${order.total}</p>
//             <p>下单时间: ${new Date(order.createdAt).toLocaleString()}</p>
//             <div class="order-items">${itemsHtml}</div>
//         `;
//
//         // 给每个订单添加点击事件
//         orderElement.addEventListener('click', async () => {
//             await fetchOrderDetails(order.orderId);
//         });
//
//         ordersList.appendChild(orderElement);
//     });
// }
//
// // 查询订单详情
// async function fetchOrderDetails(orderId) {
//     try {
//         const token = sessionStorage.getItem('token');
//         if (!token) {
//             alert('请先登录查看订单详情');
//             window.location.href = 'login.html'; // 跳转到登录页面
//             return;
//         }
//
//         const response = await fetch(`http://localhost:8081/order/${orderId}`, {
//             method: 'GET',
//             headers: {
//                 'Authorization': `Bearer ${token}`, // 添加 Bearer Token 进行认证
//             },
//         });
//
//         const data = await response.json();
//         if (data.code === 1) {
//             renderOrderDetails(data.data);
//         } else {
//             alert('获取订单详情失败');
//         }
//     } catch (error) {
//         console.error('获取订单详情失败:', error);
//         alert('获取订单详情失败，请稍后再试');
//     }
// }
//
// // 渲染订单详情
// function renderOrderDetails(order) {
//     const orderDetailsContainer = document.getElementById('order-details');
//     orderDetailsContainer.innerHTML = ''; // 清空之前的内容
//
//     const orderElement = document.createElement('div');
//     orderElement.classList.add('order-details-item');
//
//     let itemsHtml = '';
//     if (order.orderItem && order.orderItem.length > 0) {
//         order.orderItem.forEach(item => {
//             itemsHtml += `
//                 <div class="order-item-details">
//                     <p>商品: ${item.title}</p>
//                     <p>数量: ${item.quantity}</p>
//                     <p>小计: ￥${item.subTotal}</p>
//                     <img src="${item.imageUrl || 'default-image.jpg'}" alt="${item.title}" />
//                 </div>
//             `;
//         });
//     } else {
//         itemsHtml = '<p>此订单没有商品。</p>';
//     }
//
//     orderElement.innerHTML = `
//         <h3>订单ID: ${order.orderId}</h3>
//         <p>状态: ${order.orderStatus === 0 ? '未支付' : '已支付'}</p>
//         <p>总金额: ￥${order.total}</p>
//         <p>下单时间: ${new Date(order.createdAt).toLocaleString()}</p>
//         <div class="order-items">${itemsHtml}</div>
//     `;
//
//     orderDetailsContainer.appendChild(orderElement);
// }
//
// // 初始化页面
// document.addEventListener('DOMContentLoaded', async () => {
//     displayLoginStatus();
//     await fetchOrders(); // 获取并渲染订单数据
// });
//
//
//
