window.onload = function () {
    showSurvey();
    showLogoutAndProfile();
};

function showSurvey() {
    var id = sessionStorage.getItem('surveyId');
    var xhr = new XMLHttpRequest();
    var obj;
    var body = "";

    xhr.onreadystatechange = function () {
        if (xhr.readyState === XMLHttpRequest.DONE && xhr.status === 200) {
            obj = JSON.parse(xhr.responseText);
            body = iterateJSON(obj);
            body = body.substring(0, body.length - 24);
            body += "<button id=\"back\" class=\"btn btn-dark mt-2\" onclick=\"instantRedirectToProfile()\">Back</button>";
            body += "<button id=\"send\" class=\"btn btn-dark mt-2\" onclick=\"sendSurvey(send)\">Send</button>";
            body += "<div class=\"request-msg-success mt-2\">\n" +
                "                <output class=\"alert alert-success\" role=\"alert\" id=\"msg-success\" name=\"request-msg\"></output>\n" +
                "            </div>\n" +
                "\n" +
                "            <div class=\"request-msg-error mt-2\">\n" +
                "                <output class=\"alert alert-danger\" role=\"alert\" id=\"msg-error\" name=\"request-msg\"></output>\n" +
                "            </div></div>";
            $("body").append(body);

        }
    };
    xhr.open('GET', 'http://localhost:8080/survey/' + id, true);
    xhr.send(null);

}

var buttonType = 0;
var questionID;
var questionIDArray = [];
var textInputs = [];

function iterateJSON(json) {
    var keys = Object.keys(json);
    var body = "<div>";
    var tempid;
    var borderCreated = false;
    for (var i in json) {

        if (i === "id") {
            tempid = json[i];
            if (!keys.includes("answer")) {
                body += "<div class='main-panel border rounded p-4'>";
                borderCreated = true;
            }
        }

        if (i === "type") {
            buttonType = json[i];
        } else if (keys.includes("question") && i === "id") {
            questionID = json[i];
            questionIDArray.push(questionID);
        }

        if (typeof json[i] === 'object') {

            if (borderCreated === true) {
                if (keys.includes("topic")) {
                    body += "</div>";
                    borderCreated = false;
                    body += iterateJSON(json[i]);
                } else {
                    body += iterateJSON(json[i]);
                    body += "</div>";
                    borderCreated = false;
                }
            } else {
                body += iterateJSON(json[i]);
            }

        } else if (buttonType === 1) {
            textInputs.push({"questionID": questionID.toString(), "answer": ""});
            body += "<input aria-describedby='answerHelp" + questionID + "' type='text' id='textInput" + questionID + "' name='" + questionID + "' class='form-control mr-4' placeholder='Answer' maxlength='250' style='max-width:96%;'>";
            body += "<small id='answerHelp" + questionID + "' class=\"ml-3 form-text text-muted\">Maksymalnie 250 znaków</small>";
            buttonType = 0;
        } else {
            if (i === "answer") {
                if (buttonType === 2) {
                    body += "<input type='radio' name='" + questionID + "' class='" + tempid + "' value='" + json[i] + "'>" +
                        "<label for='" + json[i] + "'>&nbsp;" + json[i] + "</label><br>";
                } else if (buttonType === 3) {
                    body += "<input type='checkbox' name='" + questionID + "' class='" + tempid + "' value='" + json[i] + "'>" +
                        "<label for='" + json[i] + "'>&nbsp;" + json[i] + "</label><br>";
                }


            } else if (keys.includes("id")) {
                if (i === "topic") {
                    body += "<div class='" + i + " lead'><h3>" + json[i] + "</h3></div>";
                } else if (i === "description") {
                    body += "<div class='" + i + " lead'>" + json[i] + "</div>";
                } else if (i === "question") {
                    body += "<div class='" + i + " mb-2'>" + json[i] + "</div>";
                } else {
                    body += "<div class='" + i + "'>" + json[i] + "</div>";

                }
            }
        }

    }
    body += "</div>";
    return body;
}

function sendSurvey(buttonId) {

    var checked = [];
    for (var i in questionIDArray) {
        var buttons = document.getElementsByName(questionIDArray[i]);
        for (var j in buttons) {
            if (buttons[j].checked) {
                checked.push(buttons[j].className);
            }
        }
    }
    var idKey;
    for (var i = 0; i < textInputs.length; i++) {
        //Todo empty value
        var obj = textInputs[i];
        for (var key in obj) {
            var attrName = key;
            if (key === "questionID") {
                idKey = obj[key];
            }
            if (key === "answer") {
                obj[key] = document.getElementById("textInput" + idKey).value;
            }

        }
    }

    var responseError = $(".alert.alert-danger");
    var responseSuccess = $(".alert.alert-success");

    var jsonSend = {
        openQuestions: [],
        closedQuestions: []
    };

    jsonSend.uuid = $.cookie("token");

    for (var i = 0; i < textInputs.length; i++) {
        var obj = {};
        obj.questionId = textInputs[i].questionID;
        obj.answer = textInputs[i].answer;

        jsonSend.openQuestions.push(obj);
    }

    for (var id in checked) {
        var obj = {};
        obj.answerId = checked[id];

        jsonSend.closedQuestions.push(obj);
    }

    var dataOut = JSON.stringify(jsonSend);
    buttonId.disabled = true;
    var xhr = new XMLHttpRequest();
    sendResult(xhr, responseError, responseSuccess);
    xhr.open('POST', 'http://localhost:8080/answers', false);
    xhr.setRequestHeader("Content-Type", "application/json; charset=UTF-8");
    xhr.send(dataOut);
}

function instantRedirectToProfile() {
    window.setTimeout(function () {
        location.href = "profile.html";
    });
}

function sendResult(xhr, responseError, responseSuccess) {
    xhr.onreadystatechange = function () {
        if (xhr.readyState === XMLHttpRequest.DONE) {
            if (xhr.status === 200) {
                var json = JSON.parse(xhr.responseText);

                if (json.error) {
                    responseSuccess.hide();
                    responseError.show();
                    responseError.html("Error: " + json.error);
                } else if (json.result) {
                    responseError.hide();
                    responseSuccess.show();
                    responseSuccess.html("Survey send! Verification key: <b>" + json.result + "</b>");
                    setSurveyAsAnswered();
                }
            }
        }
    };
}

function setSurveyAsAnswered() {
    var xhr = new XMLHttpRequest();
    var token = $.cookie("token");
    var data = '{"token":"' + token + '","surveyId":"' + sessionStorage.getItem("surveyId") + '"}';
    xhr.open('POST', 'http://localhost:8080/con_us_su/', false);
    xhr.setRequestHeader("Content-Type", "application/json; charset=UTF-8");
    xhr.send(data);
}