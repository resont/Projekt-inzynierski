function register() {
    var xhr = new XMLHttpRequest();

    result(xhr);

    var email = $("#email").val();
    var password = $("#password").val();
    var data = '{"email":"'+email+'","password":"'+password+'"}';
    xhr.open('POST', 'http://localhost:8080/registration', true);
    xhr.setRequestHeader("Content-Type", "application/json; charset=UTF-8");
    xhr.send(data);
}

function login() {
    var xhr = new XMLHttpRequest();

    result(xhr);

    var email = $("#email").val();
    var password = $("#password").val();
    var data = '{"email":"'+email+'","password":"'+password+'"}';
    xhr.open('POST', 'http://localhost:8080/login', true);
    xhr.setRequestHeader("Content-Type", "application/json; charset=UTF-8");
    xhr.send(data);
}

function result(xhr){
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