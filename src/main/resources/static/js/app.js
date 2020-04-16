function register() {
    var xhr = new XMLHttpRequest();

    registerResult(xhr);

    var email = $("#email").val();
    var password = $("#password").val();
    var password2 = $("#password2").val();
    const button = document.querySelector('button');

    if (password === password2) {
        var data = '{"email":"' + email + '","password":"' + password + '"}';
        xhr.open('POST', 'http://localhost:8080/registration', true);
        xhr.setRequestHeader("Content-Type", "application/json; charset=UTF-8");
        xhr.send(data);
        button.disabled = true;
    } else {
        var responseError = document.getElementsByClassName("alert alert-danger");
        responseError[0].style.display = 'block';
        responseError[0].innerHTML = "Error: passwords are not the same.";
    }
}

function registerResult(xhr) {
    xhr.onreadystatechange = function () {
        var responseError = document.getElementsByClassName("alert alert-danger");
        var responseSuccess = document.getElementsByClassName("alert alert-success");
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
        if (xhr.readyState === XMLHttpRequest.DONE) {
            if (xhr.status === 200) {
                document.body.innerText = 'Response: ' + xhr.responseText;
            } else {
                document.body.innerText = 'Error: ' + xhr.status;
            }
        }
    };
}