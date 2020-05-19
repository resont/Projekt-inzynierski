checkIfValidAdmin();

function checkIfValidAdmin() {
    const xhr = new XMLHttpRequest();
    const token = $.cookie("token");

    const data = '{"token":"' + token + '"}';
    xhr.open('GET', 'http://localhost:8080/users/' + token, true);
    xhr.setRequestHeader("Content-Type", "application/json; charset=UTF-8");
    xhr.send(data);

    xhr.onreadystatechange = function () {
        if (xhr.readyState === XMLHttpRequest.DONE) {
            if (xhr.status === 200) {
                const json = JSON.parse(xhr.responseText);

                if (json.error || json.group !== "admin") {
                    redirectToMain();
                }
            } else {
                redirectToMain();
            }
        }
    };
}