window.onload = function () {
    showUsers();
    pickSurvey();
    showLogoutAndProfile();
};

function showUsers() {
    var xhr = new XMLHttpRequest();
    xhr.onreadystatechange = function () {
        if (xhr.readyState === XMLHttpRequest.DONE && xhr.status === 200) {
            var json = JSON.parse(xhr.responseText);

            for (var j = 0; j < json.length; j++) {
                if (json[j].token === $.cookie("token") && json[j].group !== "admin") {
                    window.location.href = "main.html";
                } else {
                    var body = "<table class=\"table\">\n" +
                        "  <thead class = \"thead-dark\">\n" +
                        "    <tr>\n" +
                        "      <th scope=\"col\">#</th>\n" +
                        "      <th scope=\"col\">Email</th>\n" +
                        "      <th scope=\"col\">Survey</th>\n" +
                        "      <th scope=\"col\">Admin</th>\n" +
                        "      <th scope=\"col\">Send</th>\n" +
                        "    </tr>\n" +
                        "  </thead><tbody>";

                    for (var i = 0; i < json.length; i++) {
                        var iter = i + 1;
                        body += "<tr>\n" +
                            "<th scope=\"row\">" + iter + "</th>";
                        var obj = json[i];
                        body += "<td>" + obj["email"] + "</td><td>";
                        body += getAllSurveys(i);
                        body += "</td>";
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
                    $(".main-panel").html(body);
                }
            }
        }
    };
    xhr.open('GET', 'http://localhost:8080/users/all', false);
    xhr.send(null);
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
    var surveyId = $("#sel" + i).children("option:selected").val();

    if (surveyId !== "") {
        addSurveyToUser(id, surveyId);
    }

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
    xhr.open('POST', 'http://localhost:8080/admin', false);
    xhr.setRequestHeader("Content-Type", "application/json; charset=UTF-8");
    xhr.send(body);

}

function addSurveyToUser(uId, sId) {
    var body = '{"uId":"' + uId + '", "sId":"' + sId + '"}';
    var xhr = new XMLHttpRequest();

    xhr.onreadystatechange = function () {
        if (xhr.readyState === XMLHttpRequest.DONE && xhr.status === 200) {
            redirectToAdminPanel();
        }
    };

    xhr.open('POST', 'http://localhost:8080/surveyToUser', false);
    xhr.setRequestHeader("Content-Type", "application/json; charset=UTF-8");
    xhr.send(body);
}

function redirectToAdminPanel() {
    window.setTimeout(function () {
        location.href = "admin.html";
    }, 1000);
}

function pickSurvey() {
    var body = `<div class="main-panel border rounded p-4">
                <label class="lead" for="selSurvey">Select survey</label>`;
    body += getAllSurveys("Survey", "showResults(this)");
    body += "</div>";
    $("body").append(body);
}

var resultBody = "";

function showResults(selectObj) {
    resultBody = "";
    var id = selectObj.value;
    var exists = true;
    if (!$('#results').length) {
        resultBody += `<div id="results" class="main-panel border rounded p-4">`;
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
            resultBody += "&emsp; NO ANSWERS";
        } else {
            //resultBody += '<textarea type="text" class="form-control" rows="1.5" disabled>'+item["answer"]+'</textarea>';
            resultBody += "<div class='border pl-2 pt-2 pr-2 rounded' style='background-color: #eaeaea;'><p>"+item["answer"]+"</p></div>";
        }
    } else {
        resultBody += item["answer"];
        resultBody += `<div class="progress">
                            <div class="progress-bar bg-dark" role="progressbar" style="width: `+percent+`%;" aria-valuenow="`+percent+`" aria-valuemin="0" aria-valuemax="100">`+percent+`%</div>
                       </div>`;
    }
    resultBody += "</div>";
}

function calcAnswersTotal(item, index) {
    answersTotal += item["count"];
}