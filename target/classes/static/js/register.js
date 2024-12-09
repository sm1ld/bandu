//register.js
document.getElementById('registerForm').addEventListener('submit', function(event) {
    event.preventDefault();

    const username = document.getElementById('username').value;
    const password = document.getElementById('password').value;
    const phoneNumber = document.getElementById('phoneNumber').value;
    const email = document.getElementById('email').value;
    const address = document.getElementById('address').value;

    // 至少需要提供电话或者邮箱
    if (!phoneNumber && !email) {
        alert("电话或邮箱必须填写一个！");
        return;
    }

    // 发送注册请求
    fetch('http://localhost:8081/user/register', {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json'
        },
        body: JSON.stringify({ username, password, phoneNumber, email, address })
    })
        .then(response => response.json())
        .then(data => {
            alert(data.msg);
            if (data.code === 1) {
                window.location.href = 'login.html'; // 注册成功后跳转到登录页面
            }
        })
        .catch(error => {
            alert('注册失败：' + error);
        });
});