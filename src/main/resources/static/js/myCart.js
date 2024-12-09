import { displayLoginStatus } from './status.js';
// 获取购物车数据
async function fetchCartItems() {
    try {
        const token = sessionStorage.getItem('token');
        if (!token) {
            alert('请先登录查看购物车');
            window.location.href = 'login.html'; // 跳转到登录页面
            return; // 结束后续执行
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
        } else {
            alert('获取购物车失败');
        }
    } catch (error) {
        console.error('获取购物车数据失败:', error);
        alert('获取购物车数据失败，请稍后再试');
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

        cartItem.innerHTML = `
            <img src="${product.imageUrl}" alt="${product.title}" class="item-image">
            <div class="item-info">
                <div class="item-title">${product.title}</div>
                <div class="item-price">￥${product.price}</div>
                <div class="item-number">数量: ${item.number}</div>
                <div class="item-sellerId">卖家ID: ${product.sellerId}</div>
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
});
