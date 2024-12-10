import {displayLoginStatus} from './status.js';

// 获取购物车数据
async function fetchCartItems() {
    try {
        const token = sessionStorage.getItem('token');
        if (!token) {
            alert('请先登录查看购物车');
            window.location.href = 'login.html'; // 跳转到登录页面
            return []; // 结束后续执行
        }
        displayLoginStatus();
        const response = await fetch('http://localhost:8081/cart/view', {
            method: 'GET',
            headers: {
                'Authorization': `Bearer ${token}`, // 添加 Bearer Token 进行认证
            },
        });

        const data = await response.json();
        if (data.code === 1) {
            renderCartItems(data.data);
            return data.data;
        } else {
            alert('获取购物车失败');
            return [];
        }
    } catch (error) {
        console.error('获取购物车数据失败:', error);
        alert('获取购物车数据失败，请稍后再试');
        return [];
    }
}

// 渲染购物车商品列表
function renderCartItems(cartItems) {
    const cartItemsList = document.getElementById('cart-items-list');
    cartItemsList.innerHTML = ''; // 清空之前的内容

    cartItems.forEach(item => {
        const cartItem = document.createElement('div');
        cartItem.classList.add('cart-item');

        const product = item.items[0]; // 获取商品的详细信息
        const isChecked = itemCheckedStatus[product.id] || false; // 获取勾选状态

        cartItem.innerHTML = `
            <input type="checkbox" class="cart-item-checkbox" data-item-id="${product.id}" ${isChecked ? 'checked' : ''}>
            <img src="${product.imageUrl}" alt="${product.title}" class="item-image">
            <div class="item-info">
                <div class="item-title">${product.title}</div>
                <div class="item-price">￥${product.price}</div>
                <div class="item-sellerId">卖家ID: ${product.sellerId}</div>
                <div class="item-number">数量: ${item.number}</div>
                <div class="item-subTotal">小计: ￥${item.subTotal}</div>
            </div>
            <div class="item-actions">
                <button class="update-number-btn" data-item-id="${product.id}">更新数量</button>
                <button class="remove-item-btn" data-item-id="${product.id}">移除</button>
            </div>
        `;
        cartItemsList.appendChild(cartItem);
    });
}

// 移除购物车商品
async function removeItemFromCart(itemId) {
    try {
        const token = sessionStorage.getItem('token');
        if (!token) {
            alert('请先登录');
            window.location.href = 'login.html';
            return;
        }

        const response = await fetch(`http://localhost:8081/cart/${itemId}`, {
            method: 'DELETE',
            headers: {
                'Authorization': `Bearer ${token}`,
            },
        });

        const data = await response.json();
        if (data.code === 1) {
            alert('商品已移除');
            await fetchCartItems(); // 重新加载购物车
        } else {
            alert('移除商品失败');
        }
    } catch (error) {
        console.error('移除商品失败:', error);
        alert('移除商品失败，请稍后再试');
    }
}

// 更新购物车商品数量
async function updateItemNumber(itemId, number) {
    try {
        const token = sessionStorage.getItem('token');
        if (!token) {
            alert('请先登录');
            window.location.href = 'login.html';
            return;
        }

        const response = await fetch(`http://localhost:8081/cart/${itemId}?number=${number}`, {
            method: 'PUT',
            headers: {
                'Authorization': `Bearer ${token}`,
            },
        });

        const data = await response.json();
        if (data.code === 1) {
            alert('数量更新成功');
            await fetchCartItems(); // 重新加载购物车
        } else {
            alert('更新数量失败');
        }
    } catch (error) {
        console.error('更新数量失败:', error);
        alert('更新数量失败，请稍后再试');
    }
}

// 打开更新数量模态框
function openUpdateNumberModal(itemId) {
    const modal = document.getElementById('updateNumberModal');
    const numberInput = document.getElementById('number');
    modal.style.display = 'block';

    // 提交更新数量
    document.getElementById('updateNumberForm').onsubmit = async (e) => {
        e.preventDefault();
        const number = parseInt(numberInput.value);
        if (isNaN(number) || number <= 0) {
            alert('请输入有效的数量');
            return;
        }
        await updateItemNumber(itemId, number);
        modal.style.display = 'none';
    };
}

// 假设这里是保存勾选状态的对象，key 是商品的 ID，value 是勾选状态（true 或 false）
let itemCheckedStatus = {};

// 创建订单
async function createOrder() {
    try {
        const token = sessionStorage.getItem('token');
        if (!token) {
            alert('请先登录');
            window.location.href = 'login.html'; // 跳转到登录页面
            return;
        }
        // 获取购物车数据
        const cartItems = await fetchCartItems();
        let total = 0;
        // 只计算勾选的商品总价
        cartItems.forEach(item => {
            const product = item.items[0];
            if (itemCheckedStatus[product.id]) { // 只计算被勾选的商品
                total += item.subTotal; // 加上每个商品的小计
            }
        });
        if (total <= 0) {
            alert('请选择商品后再创建订单');
            return;
        }
        // 创建订单请求
        const response = await fetch('http://localhost:8081/order/create', {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json', // 确保发送 JSON 数据
                'Authorization': `Bearer ${token}`,
            },
            body: JSON.stringify({
                total: total, // 传递总价
            })
        });

        const data = await response.json();
        if (data.code === 1) {
            alert('订单创建成功');
            window.location.href = 'myOrder.html'; // 跳转到订单页面
        } else {
            alert('订单创建失败');

        }
    } catch (error) {
        console.error('创建订单失败:', error);
        alert('创建订单失败，请稍后再试');
    }
}

// 关闭模态框
document.getElementById('closeModal').onclick = () => {
    document.getElementById('updateNumberModal').style.display = 'none';
};

// 事件代理处理更新数量和移除商品按钮点击事件
document.getElementById('cart-items-list').addEventListener('click', (e) => {
    const itemId = e.target.dataset.itemId;
    if (e.target.classList.contains('update-number-btn')) {
        openUpdateNumberModal(itemId);
    } else if (e.target.classList.contains('remove-item-btn')) {
        removeItemFromCart(itemId);
    }
});

// 初始化页面
document.addEventListener('DOMContentLoaded', async () => {
    await fetchCartItems(); // 获取并渲染购物车数据
    document.getElementById('createOrderBtn').addEventListener('click', createOrder); // 绑定创建订单按钮
});


// 监听勾选框变化
document.getElementById('cart-items-list').addEventListener('change', (e) => {
    if (e.target.classList.contains('cart-item-checkbox')) {
        const itemId = e.target.dataset.itemId;
        const isChecked = e.target.checked;

        // 更新勾选状态
        itemCheckedStatus[itemId] = isChecked;
    }
});
