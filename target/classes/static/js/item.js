//item.js
import {displayLoginStatus} from './status.js';

// 在商品详情页加载时，从 sessionStorage 获取 itemId
const itemId = sessionStorage.getItem('itemId');
if (!itemId) {
    alert('商品 ID 不存在');
    // 处理无商品ID的情况，可能是跳转回首页等
} else {
    // 使用 itemId 请求商品详情
    loadItemDetails(itemId);
}

// 记录当前访问的页面是商品详情页面
sessionStorage.setItem('previousPage', 'item');

// 获取 token，如果没有登录则跳转到登录页
function loadItemDetails(itemId) {
    const token = sessionStorage.getItem('token');
    if (!token) {
        alert('请先登录查看商品详情');
        window.location.href = 'login.html'; // 直接跳转到登录页面
        return; // 结束执行后续代码
    }

    // 请求商品详情数据
    fetch(`http://localhost:8081/item/${itemId}`, {
        method: 'GET',
        headers: {
            'Authorization': `Bearer ${token}` // 添加 Bearer Token 进行认证
        }
    })
        .then(response => response.json())
        .then(data => {
            if (data.code === 1) {
                const item = data.data; // 获取商品数据
                // 获取页面容器元素
                const itemDetail = document.getElementById('item-detail');

                // 填充商品信息
                itemDetail.innerHTML = `
                    <h3>${item.title}</h3>
                    <img src="${item.imageUrl}" alt="${item.title}" width="300" />
                    <p><strong>描述：</strong>${item.description}</p>
                    <p><strong>价格：</strong>${item.price} 元</p>
                    <p><strong>库存：</strong>${item.quantity} 件</p>
                    <p><strong>分类 ID：</strong>${item.categoryId}</p>
                    <p><strong>卖家 ID：</strong>${item.sellerId}</p>
                    <p><strong>商品状态：</strong>${item.status === 1 ? '在售' : '已售出'}</p>
                    <p><strong>创建时间：</strong>${item.createdAt}</p>
                    <p><strong>更新时间：</strong>${item.updatedAt}</p>
                    <div>
                        <label for="number">购买数量:</label>
                        <input type="number" id="number" name="number" min="1" max="${item.quantity}" value="1">
                        <button id="addToCartBtn">加入购物车</button>
                    </div>
                `;

                // 绑定加入购物车按钮的事件
                const addToCartBtn = document.getElementById('addToCartBtn');
                addToCartBtn.addEventListener('click', function () {
                    addToCart(itemId); // 调用添加到购物车的函数
                });
            } else {
                alert('获取商品信息失败');
            }
        })
        .catch(error => {
            console.error('Error fetching item details:', error);
            alert('商品信息加载失败，请稍后再试');
        });
}

// 将商品加入购物车
function addToCart(itemId) {
    const token = sessionStorage.getItem('token');
    if (!token) {
        alert('请先登录');
        window.location.href = 'login.html';
        return;
    }

    const number = document.getElementById('number').value;

    if (!number || number <= 0) {
        alert('请选择购买数量');
        return;
    }

    // 发送请求将商品加入购物车
    fetch(`http://localhost:8081/cart/${itemId}?number=${number}`, {
        method: 'POST',
        headers: {
            'Authorization': `Bearer ${token}`,
            'Content-Type': 'application/json'
        }
    })
        .then(response => response.json())
        .then(data => {
            if (data.code === 1) {
                alert('成功加入购物车');
            } else {
                alert('加入购物车失败：' + data.msg);
            }
        })
        .catch(error => {
            console.error('Error adding item to cart:', error);
            alert('加入购物车失败，请稍后再试');
        });
}
// 跳转到评论页面
window.goToCommentPage = function(){
    const itemId = sessionStorage.getItem('itemId');
    if (itemId) {
        window.location.href = `comment.html?itemId=${itemId}`; // 使用商品ID跳转到评论页面
    } else {
        alert('商品ID缺失');
    }
}
// 在页面加载完成时显示登录状态
document.addEventListener('DOMContentLoaded', function () {
    displayLoginStatus(); // 调用显示登录状态的函数
});

