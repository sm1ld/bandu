//app.js
import { displayLoginStatus } from './status.js';


// 当页面加载时，显示登录状态,查看商品详情
document.addEventListener("DOMContentLoaded", function () {
    fetchItems(); // 加载商品列表
    displayLoginStatus(); // 调用显示登录状态的函数
});


function viewItem(itemId) {
    sessionStorage.setItem('itemId', itemId);
    window.location.href = 'item.html';
}

// 获取商品列表
function fetchItems() {
    fetch('http://localhost:8081/item/list?page=1&size=20', {
        method: 'GET',
        headers: {
            'Content-Type': 'application/json',
        }
    })
        .then(response => response.json())
        .then(data => {
            const itemList = document.getElementById('item-list');
            itemList.innerHTML = ''; // 清空列表

            data.data.forEach(item => {
                const itemElement = document.createElement('section');
                itemElement.classList.add('item');
                itemElement.innerHTML = `
                    <h3>${item.title}</h3>
                    <p>${item.description}</p>
                    <p>价格: ${item.price} 元</p>
                    <button>查看详情</button>
                `;
                const button = itemElement.querySelector('button');
                button.addEventListener('click', function() {
                    viewItem(item.id);  // 传递正确的 item.id
                });
                itemList.appendChild(itemElement);
            });

        })
        .catch(error => console.error('Error fetching items:', error));
}


