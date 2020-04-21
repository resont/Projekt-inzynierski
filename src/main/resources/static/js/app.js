function register() {
    var xhr = new XMLHttpRequest();

    registerResult(xhr);

    var email = $("#email").val();
    var password = $("#password").val();
    var password2 = $("#password2").val();

    if (password === password2) {
        var data = '{"email":"' + email + '","password":"' + password + '"}';
        xhr.open('POST', 'http://localhost:8080/registration', true);
        xhr.setRequestHeader("Content-Type", "application/json; charset=UTF-8");
        xhr.send(data);
    } else {
        var responseError = document.getElementsByClassName("alert alert-danger");
        responseError[0].innerHTML = "Error: passwords are not the same.";
    }
}

function registerResult(xhr) {
    xhr.onreadystatechange = function () {
        var responseError = document.getElementsByClassName("alert alert-danger");
        var responseSuccess = document.getElementsByClassName("alert alert-success");
        const button = document.querySelector('button');
        if (xhr.readyState === XMLHttpRequest.DONE) {
            if (xhr.status === 200) {
                var json = JSON.parse(xhr.responseText);

                if (json.error) {
                    responseSuccess[0].style.display = 'none';
                    responseError[0].style.display = 'block';
                    responseError[0].innerHTML = "Error: " + json.error;
                } else if (json.result) {
                    responseError[0].style.display = 'none';
                    responseSuccess[0].style.display = 'block';
                    responseSuccess[0].innerHTML = "User: " + json.email + " - " + json.result;
                    button.disabled = true;
                    //redirectToMain();
                }
            }
        }
    };
}

function login() {
    var xhr = new XMLHttpRequest();

    loginResult(xhr);

    var email = $("#email").val();
    var password = $("#password").val();

    var data = '{"email":"' + email + '","password":"' + password + '"}';
    xhr.open('POST', 'http://localhost:8080/login', true);
    xhr.setRequestHeader("Content-Type", "application/json; charset=UTF-8");
    xhr.send(data);
}

function loginResult(xhr) {
    xhr.onreadystatechange = function () {
        var responseError = document.getElementsByClassName("alert alert-danger");
        var responseSuccess = document.getElementsByClassName("alert alert-success");
        const button = document.querySelector('button');
        if (xhr.readyState === XMLHttpRequest.DONE) {
            if (xhr.status === 200) {
                var json = JSON.parse(xhr.responseText);

                if (json.error) {
                    responseSuccess[0].style.display = 'none';
                    responseError[0].style.display = 'block';
                    responseError[0].innerHTML = "Error: " + json.error;
                } else if (json.result) {
                    responseError[0].style.display = 'none';
                    responseSuccess[0].style.display = 'block';
                    responseSuccess[0].innerHTML = "Login successful.";
                    button.disabled = true;
                    setCookie("token", json.result, 1, true);
                    redirectToMain();
                }
            }
        }
    }
}

function setCookie(name, val, days, secure) {
    if (navigator.cookieEnabled) {
        const cookieName = encodeURIComponent(name);
        const cookieVal = encodeURIComponent(val);
        let cookieText = cookieName + "=" + cookieVal;

        if (typeof days === "number") {
            const data = new Date();
            data.setTime(data.getTime() + (days * 24 * 60 * 60 * 1000));
            cookieText += "; expires=" + data.toGMTString();
        }
        //todo https handler
        // if (secure) {
        //     cookieText += "; secure";
        // }

        document.cookie = cookieText;
    }
}

function redirectToMain() {
    window.setTimeout(function () {
        location.href = "../main.html";
    }, 2000);
}

window.onload = showLogoutButton;

function showLogoutButton() {
    const cookies = document.cookie.split(/; */);
    if (cookies[0].split("=")[0] === "token") {
        $("#logout").css("display","block");
    }
}

function logout() {
    const cookies = document.cookie.split(/; */);
    if (cookies[0].split("=")[0] === "token") {
        deleteCookie(cookies[0].split("=")[0]);
    }
}

function deleteCookie(cookieName) {
    document.cookie = cookieName + "=; expires=Thu, 01 Jan 1970 00:00:01 GMT;";
}