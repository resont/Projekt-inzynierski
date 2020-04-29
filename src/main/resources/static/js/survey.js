window.onload = showSurvey;

function showSurvey() {
    var id = 1; //temp
    var xhr = new XMLHttpRequest();
    var obj;
    var body = "";

    xhr.onreadystatechange = function () {
        if (xhr.readyState === XMLHttpRequest.DONE && xhr.status === 200) {
            obj = JSON.parse(xhr.responseText);
            body = iterateJSON(obj);
            body += "<button id=\"send\" class=\"btn btn-dark mb-3\" onclick=\"sendSurvey()\">Login</button>";
            $(".main-panel").html(body);


        }
    };
    xhr.open('GET', 'http://localhost:8080/survey/' + id, true);
    xhr.send(null);

}

var buttonType = 0;
var questionID;
var questionIDArray = [];

function iterateJSON(json) {
    var keys = Object.keys(json);
    if (keys.includes("id")) {
        var body = "<div style=\"border-top:1px solid black;\">";
    } else {
        var body = "<div>";
    }
    var tempid;
    for (var i in json) {

        if (i === "id") {
            tempid = json[i];
        }

        if (i === "type") {
            buttonType = json[i];
        } else if (keys.includes("question") && i === "id") {
            questionID = json[i];
            questionIDArray.push(questionID);
        }

        if (typeof json[i] === 'object') {
            body += iterateJSON(json[i]);
        } else {
            if (i === "answer") {
                if (buttonType === 2) {
                    body += "<input type='radio' name='" + questionID + "' class='" + tempid + "' value='" + json[i] + "'>" +
                        "<label for='" + json[i] + "'>" + json[i] + "</label><br>";
                } else if (buttonType === 3) {
                    body += "<input type='checkbox' name='" + questionID + "' class='" + tempid + "' value='" + json[i] + "'>" +
                        "<label for='" + json[i] + "'>" + json[i] + "</label><br>";
                } else if (buttonType === 1) {
                    body += "<input type='text' class='" + json[i] + "'>" +
                        "<label for='" + json[i] + "'>" + json[i] + "</label><br>";
                }


            } else if (keys.includes("id")) {
                body += "<div class='" + i + "'>" + json[i] + "</div>";
            }
        }
    }
    body += "</div>";
    return body;
}

function sendSurvey() {
    var checked = [];
    for (var i in questionIDArray) {
        var buttons = document.getElementsByName(questionIDArray[i]);
        for (var j in buttons) {
            if (buttons[j].checked) {
                checked.push(buttons[j].className);
            }
        }
    }

    var xhr = new XMLHttpRequest();
    for (var id in checked) {
        xhr.open('POST', 'http://localhost:8080/answer/' + checked[id], true);
        xhr.send(null);
    }
}