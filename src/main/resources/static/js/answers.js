window.onload = function () {
    getAnswers();
};


let answerId = 0;
let answerName = "";
let qIdArr = [];

function getAnswers() {
    let keyId = "key1"; //temp
    let body = "";
    let xhr = new XMLHttpRequest();
    xhr.onreadystatechange = function () {
        if (xhr.readyState === XMLHttpRequest.DONE && xhr.status === 200) {
            let answers = JSON.parse(xhr.responseText);
            for (let aId in answers) {
                answerName = "";
                answerId = parseInt(aId);
                let question = answers[aId];
                let qName = question["question"];
                if (!qIdArr.includes(question["id"])) {
                    qIdArr.push(question["id"]);
                    body += "<div id=" + question["id"] + " class='main-panel border rounded p-4'>";

                    body += "<div class='lead mb-2'>" + qName + "</div>";
                    body += "</div>";
                    $('body').append(body);
                }

                question["answers"].forEach(getAnswerName);
                body = "";
                if (question["type"] === 2) {
                    body += `<div>
                                <input type="radio" id='a` + answerId + `' checked disabled>
                                <label for='a` + answerId + `'>` + answerName + `</label>
                            </div>`;
                } else if (question["type"] === 3) {
                    body += `<div>
                                <input type="checkbox" id='a` + answerId + `' checked disabled>
                                <label for='a` + answerId + `'>` + answerName + `</label>
                            </div>`;
                } else {

                    body += "<div class='border pl-2 pt-2 pr-2 rounded' style='background-color: #eaeaea;'><p>" + answerName + "</p></div>";
                }
                $('#' + question["id"]).append(body);
                body = "";

            }
        }
    };
    xhr.open('POST', 'http://localhost:8080/answerKey/' + keyId, false);
    xhr.send(null);
}

function getAnswerName(item, index) {
    if (item["id"] === answerId) {
        answerName = item["answer"];
    }
}