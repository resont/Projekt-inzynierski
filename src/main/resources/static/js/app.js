var xhr = new XMLHttpRequest();

xhr.onreadystatechange = function () {
    if (xhr.readyState === XMLHttpRequest.DONE) {
        if (xhr.status === 200) {
            document.body.innerText = 'Response: ' + xhr.responseText;
        } else {
            document.body.innerText = 'Error: ' + xhr.status;
        }
    }
};

var data = '{"email":"xhr-test2","password":"test"}';

xhr.open('POST', 'http://localhost:8080/registration', true);
xhr.setRequestHeader("Content-Type", "application/json; charset=UTF-8");
xhr.send(data);