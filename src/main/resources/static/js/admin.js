window.onload = function () {
    pickSurvey();
    showLogoutAndProfile();
};

function showUsers() {
    var xhr = new XMLHttpRequest();
    xhr.onreadystatechange = function () {
        if (xhr.readyState === XMLHttpRequest.DONE && xhr.status === 200) {
            var json = JSON.parse(xhr.responseText);

            for (var j = 0; j < json.length; j++) {

                var body = "<table class=\"table\">\n" +
                    "  <thead class = \"thead-dark\">\n" +
                    "    <tr>\n" +
                    "      <th scope=\"col\">#</th>\n" +
                    "      <th scope=\"col\">Email</th>\n" +
                    "      <th scope=\"col\">Admin</th>\n" +
                    "      <th scope=\"col\">Send</th>\n" +
                    "    </tr>\n" +
                    "  </thead><tbody>";

                for (var i = 0; i < json.length; i++) {
                    var iter = i + 1;
                    body += "<tr>\n" +
                        "<th scope=\"row\">" + iter + "</th>";
                    var obj = json[i];
                    body += "<td>" + obj["email"] + "</td>";

                    body += "<td><div class=\"form-check d-flex justify-content-center\">";
                    if (obj["group"] === "admin") {
                        if (obj["uuid"] === $.cookie("token")) {
                            body += "<input id='input" + i + "' class=\"form-check-input\" type=\"checkbox\" value=\"\" checked disabled>";
                        } else {
                            body += "<input id='input" + i + "' class=\"form-check-input\" type=\"checkbox\" value=\"\" checked>";
                        }

                    } else {
                        body += "<input id='input" + i + "' class=\"form-check-input\" type=\"checkbox\" value=\"\">";
                    }
                    body += "</div></td>";
                    body += "<td><button id=\"send\" class=\"btn btn-dark btn-block\" onclick=\"sendForm(" + i + "," + obj["id"] + ")\">Update</button></td>";

                    body += "</tr>";

                }
                body += "</tbody></table>";
                body += `<div class="request-msg-success mt-2">
                        <output class="alert alert-success" role="alert" id="msg-success" name="request-msg"></output>
                    </div>

                    <div class="request-msg-error mt-2">
                        <output class="alert alert-danger" role="alert" id="msg-error" name="request-msg"></output>
                    </div>`;

                const divGroup = $("#usersTable");
                divGroup.html(body);
                $("#resultsSurveyToUsers").hide();
                $("#pick").hide();
                $("#results").hide();

                divGroup.show();

            }
        }
    };
    xhr.open('GET', 'http://localhost:8080/users/all', false);
    xhr.send(null);
}

function attachSurveys() {
    let body = "<label class=\"lead\" for=\"selSurvey\">Select survey</label>";
    body += getAllSurveys("usersSurvey", "showSurveyToUsers(this)");


    const divGroup = $("#usersTable");
    divGroup.html(body);
    divGroup.show();
    $("#resultsSurveyToUsers").hide();
    $("#pick").hide();
    $("#results").hide();


}

let users = [];

function showSurveyToUsers(obj) {
    const id = obj.value;

    var xhr = new XMLHttpRequest();
    xhr.onreadystatechange = function () {
        if (xhr.readyState === XMLHttpRequest.DONE && xhr.status === 200) {
            var json = JSON.parse(xhr.responseText);

            var body = "<table class=\"table mt-2\">\n" +
                "  <thead class = \"thead-dark\">\n" +
                "    <tr>\n" +
                "      <th scope=\"col\">#</th>\n" +
                "      <th scope=\"col\">Email</th>\n" +
                "      <th scope=\"col\">Added to user</th>\n" +
                "    </tr>\n" +
                "  </thead><tbody>";

            for (var i = 0; i < json.length; i++) {
                var iter = i + 1;
                body += "<tr>\n" +
                    "<th scope=\"row\">" + iter + "</th>";
                var obj = json[i];
                body += "<td>" + obj["email"] + "</td>";

                body += "<td><div class=\"form-check d-flex justify-content-center\">";

                if (obj["answer"] === "true") {
                    body += "<input id='input" + obj["id"] + "' class=\"form-check-input\" type=\"checkbox\" checked disabled>";
                } else if (obj["answer"] === "false") {
                    body += "<input id='input" + obj["id"] + "' class=\"form-check-input\" type=\"checkbox\" checked>";
                } else {
                    body += "<input id='input" + obj["id"] + "' class=\"form-check-input\" type=\"checkbox\">";
                }
                users.push(parseInt(obj["id"]));
                body += "</div></td>";

                body += "</tr>";

            }
            body += "</tbody></table>";

            body += "<button id=\"sendSurveyToUsers\" class=\"btn btn-dark\" onclick=\"sendSurveyToUsers(" + id + ")\">Update</button></td>";

            body += `<div class="request-msg-success mt-2">
                        <output class="alert alert-success" role="alert" id="msg-success" name="request-msg"></output>
                    </div>

                    <div class="request-msg-error mt-2">
                        <output class="alert alert-danger" role="alert" id="msg-error" name="request-msg"></output>
                    </div>`;

            const divGroup = $("#resultsSurveyToUsers");
            divGroup.html(body);
            divGroup.show();

        }

    };
    xhr.open('GET', 'http://localhost:8080/surveyToUser/' + id, false);
    xhr.send(null);

}

function sendSurveyToUsers(id) {
    let obj = {data: []};
    for (var i = 0; i < users.length; i++) {
        let answered;
        const checkbox = document.getElementById('input' + users[i]);

        if (checkbox.checked) {
            if (checkbox.disabled) {
                answered = "true";
            } else {
                answered = "false";
            }
        } else {
            answered = "no data";
        }

        obj.data.push({
            "userId": users[i],
            "surveyId": id,
            "answered": answered
        });
    }

    const responseError = $(".alert.alert-danger");
    const responseSuccess = $(".alert.alert-success");

    let xhr = new XMLHttpRequest();

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
                    responseSuccess.html(json.result);
                }
            }
        }
    };

    let body = JSON.stringify(obj);
    xhr.open('POST', 'http://localhost:8080/updateSurveyToUser', false);
    xhr.setRequestHeader("Content-Type", "application/json; charset=UTF-8");
    xhr.send(body);

}

function getAllSurveys(i, action = "") {
    var body = "";
    var xhr = new XMLHttpRequest();
    xhr.onreadystatechange = function () {
        if (xhr.readyState === XMLHttpRequest.DONE && xhr.status === 200) {
            var surveys = JSON.parse(xhr.responseText);
            body += "<select id=\"sel" + i + "\" class=\"form-control\" onchange=" + action + ">";
            body += "<option></option>";
            for (var s in surveys) {
                var survey = surveys[s];
                body += "<option value=\"" + survey["id"] + "\">" + survey["topic"] + "</option>";
            }
            body += "</select>";
        }
    };
    xhr.open('GET', 'http://localhost:8080/survey/all', false);
    xhr.send(null);
    return body;
}

function sendForm(i, id) {
    var group;
    if ($("#input" + i).prop("checked")) {
        group = "admin";
    } else {
        group = "user";
    }
    var body = '{"id":"' + id + '", "group":"' + group + '"}';
    updateAdmin(body);
}

function updateAdmin(body) {
    var xhr = new XMLHttpRequest();

    const responseError = $(".alert.alert-danger");
    const responseSuccess = $(".alert.alert-success");

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
                    responseSuccess.html(json.result);
                }
            }
        }
    };

    xhr.open('POST', 'http://localhost:8080/admin', false);
    xhr.setRequestHeader("Content-Type", "application/json; charset=UTF-8");
    xhr.send(body);

}

function pickSurvey() {
    var body = `<div id="pick" class="main-panel border rounded p-4 hide">
                <label class="lead" for="selSurvey">Select survey</label>`;
    body += getAllSurveys("Survey", "showResults(this)");
    body += "</div>";
    $("body").append(body);
}

function showPick() {
    $("#usersTable").hide();
    $("#resultsSurveyToUsers").hide();
    $("#pick").show();
    $("#results").show();
}

var resultBody = "";

function showResults(selectObj) {

    resultBody = "";
    var id = selectObj.value;
    var exists = true;
    if (!$('#results').length) {
        resultBody += `<div id="results" class="main-panel border rounded p-4 hide">`;
        exists = false;
    }
    var xhr = new XMLHttpRequest();

    xhr.onreadystatechange = function () {
        if (xhr.readyState === XMLHttpRequest.DONE && xhr.status === 200) {
            var obj = JSON.parse(xhr.responseText);
            obj["questions"].forEach(showQuestions);
            if (exists === false) {
                resultBody += "</div>";
                $("body").append(resultBody);
            } else {
                $("#results").html(resultBody);

            }
            $("#usersTable").hide();
            $("#resultsSurveyToUsers").hide();
            $("#results").show();

        }
    };
    xhr.open('GET', 'http://localhost:8080/survey/' + id, false);
    xhr.send(null);
}

var answersTotal = 0;
var type = 0;

function showQuestions(item, index) {
    answersTotal = 0;
    type = 0;
    resultBody += '<div class="lead">' + item["question"] + '</div>';
    if (parseInt(item["type"]) !== 1) {
        item["answers"].forEach(calcAnswersTotal);
    } else {
        type = 1;
    }
    item["answers"].forEach(showAnswers);
}

function showAnswers(item, index) {
    var percent = Math.round(parseInt(item["count"]) / answersTotal * 100);
    resultBody += "<div class='font-weight-normal ml-4 mb-2'>";

    if (answersTotal === 0) {
        if (type !== 1) {
            resultBody += item["answer"];
            resultBody += "&emsp; <div style='color: red;'>NO ANSWERS</div>";
        } else {
            resultBody += "<div class='border pl-2 pt-2 pr-2 rounded' style='background-color: #eaeaea;'><p>" + item["answer"] + "</p></div>";
        }
    } else {
        resultBody += item["answer"];
        resultBody += `<div class="progress">
                            <div class="progress-bar bg-dark" role="progressbar" style="width: ` + percent + `%;" aria-valuenow="` + percent + `" aria-valuemin="0" aria-valuemax="100">` + percent + `%</div>
                       </div>`;
    }
    resultBody += "</div>";
}

function calcAnswersTotal(item, index) {
    answersTotal += item["count"];
}