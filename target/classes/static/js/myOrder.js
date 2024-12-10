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
// 创建新订单并传递购物车商品
async function createOrder() {
    try {
        const token = sessionStorage.getItem('token');
        if (!token) {
            alert('请先登录');
            window.location.href = 'login.html';
            return;
        }

        // 获取购物车数据
        const cartResponse = await fetch('http://localhost:8081/cart/view', {
            method: 'GET',
            headers: {
                'Authorization': `Bearer ${token}`, // 添加 Bearer Token 进行认证
            },
        });

        const cartData = await cartResponse.json();
        if (cartData.code !== 1 || !cartData.data || cartData.data.length === 0) {
            alert('购物车为空，无法创建订单');
            return;
        }

        const orderItems = cartData.data.map(cartItem => ({
            itemId: cartItem.items[0].id,
            quantity: cartItem.number,
            price: cartItem.items[0].price,
            subTotal: cartItem.subTotal,
        }));

        const orderResponse = await fetch('http://localhost:8081/order/create', {
            method: 'POST',
            headers: {
                'Authorization': `Bearer ${token}`,
                'Content-Type': 'application/json',
            },
            body: JSON.stringify({ orderItems }),
        });

        const orderData = await orderResponse.json();
        if (orderData.code === 1) {
            alert('订单创建成功');
            fetchOrders(); // 重新加载订单列表
        } else {
            alert('订单创建失败');
        }
    } catch (error) {
        console.error('创建订单失败:', error);
        alert('创建订单失败，请稍后再试');
    }
}
// // 创建新订单
// async function createOrder() {
//     try {
//         const token = sessionStorage.getItem('token');
//         if (!token) {
//             alert('请先登录');
//             window.location.href = 'login.html';
//             return;
//         }
//
//         const response = await fetch('http://localhost:8081/order/create', {
//             method: 'POST',
//             headers: {
//                 'Authorization': `Bearer ${token}`,
//             },
//         });
//
//         const data = await response.json();
//         if (data.code === 1) {
//             alert('订单创建成功');
//             fetchOrders(); // 重新加载订单列表
//         } else {
//             alert('订单创建失败');
//         }
//     } catch (error) {
//         console.error('创建订单失败:', error);
//         alert('创建订单失败，请稍后再试');
//     }
// }

// 事件监听
document.getElementById('createOrderBtn').addEventListener('click', createOrder);

// 初始化页面
document.addEventListener('DOMContentLoaded', async () => {
    await fetchOrders(); // 获取并渲染订单数据
});



