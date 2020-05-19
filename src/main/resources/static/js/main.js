const mainPanel = document.getElementsByClassName("main-panel main");
const questionTypePanel = document.getElementsByClassName("main-panel question-type");
const mainError = document.getElementsByClassName("alert alert-danger main");
const questionType1 = document.getElementsByClassName("question-type-1");
const questionType2 = document.getElementsByClassName("question-type-2");
const questionType3 = document.getElementsByClassName("question-type-3");
const surveyTitlePanel = document.getElementsByClassName("survey-title-panel");

const json = {
    questions: []
};
let answer2Number = 0;
let answer3Number = 0;

function add() {

    let title = $("#name").val();
    let description = $("#description").val();
    if (title && description) {
        mainPanel[0].style.display = 'none';
        questionTypePanel[0].style.display = 'block';
        surveyTitlePanel[0].style.display = 'block';
        surveyTitlePanel[0].children[0].children[0].textContent += title;
        surveyTitlePanel[0].children[1].children[0].textContent += description;

        json.title = title;
        json.description = description;

    } else {
        mainError[0].style.display = 'block';
        mainError[0].innerHTML = "Wypełnij pola.";
    }

}

const typeDescription = document.getElementsByClassName("type-description");
const typeDescriptionBorder = document.getElementsByClassName("type-border hide");

function show1() {
    typeDescriptionBorder[0].style.display = 'block';
    typeDescription[0].innerHTML = "Respondent może udzielić swobodnej odpowiedzi w formie tekstowej.";
}

function show2() {
    typeDescriptionBorder[0].style.display = 'block';
    typeDescription[0].innerHTML = "Przycisk opcji posiada dwa stany: włączony i wyłączony, a „naciśnięcie” go lewym klawiszem myszy zawsze spowoduje jego włączenie. W momencie aktywacji dezaktywuje on pozostałe przyciski z danej grupy.";
}

function show3() {
    typeDescriptionBorder[0].style.display = 'block';
    typeDescription[0].innerHTML = "Respondent może zaznaczyć więcej niż jedną odpowiedź.";
}

function choseType() {
    const radioOption = document.getElementsByClassName("btn btn-secondary active");
    const radioOptionValue = radioOption[0].children[0].id;
    const confirmPanel = document.getElementsByClassName("confirmation");
    confirmPanel[0].style.display = 'none';

    if (radioOptionValue === "option1") {
        hideQuestionTypePanel();
        $("#openQuestion").val("");
        questionType1[0].style.display = 'block';

    } else if (radioOptionValue === "option2") {
        hideQuestionTypePanel();
        questionType2[0].style.display = 'block';

    } else if (radioOptionValue === "option3") {
        hideQuestionTypePanel();
        questionType3[0].style.display = 'block';

    }
}

function hideSurveyTitlePanel() {
    surveyTitlePanel[0].style.display = 'none';
}

function hideQuestionTypePanel() {
    questionTypePanel[0].style.display = 'none';
}

function showQuestionTypePanel() {
    questionTypePanel[0].style.display = 'block';
}

function hideQuestionTypes() {
    questionType1[0].style.display = 'none';
    questionType2[0].style.display = 'none';
    questionType3[0].style.display = 'none';
}

function backToQuestionType() {
    hideQuestionTypes();
    hideSurveyTitlePanel();
    showQuestionTypePanel();
}

const tab1 = new Array(answer2Number);

function addAnswer2() {
    for (let i = 0; i <= answer2Number; i++) {
        tab1[i] = document.getElementById("2answer" + i).value;
    }

    const questionType2Answers = document.getElementsByClassName("question-type-2-answers");
    const temp = "2answer" + answer2Number;
    const answer2 = document.getElementById(temp).value;

    if (answer2 !== "" && answer2 !== null) {
        answer2Number++;
        questionType2Answers[0].innerHTML = questionType2Answers[0].innerHTML + "<input type=\"text\" class=\"form-control mb-2\" id=\"" + "2answer" + answer2Number + "\" placeholder=\"Podaj odpowiedz\" name=\"name\">";
    }

    updateContent2();
}

function updateContent2() {
    for (let i = 0; i < answer2Number; i++) {
        const tempElement1 = document.getElementById("2answer" + i);
        tempElement1.value = tab1[i];
    }
}

function nextQuestionRadio() {
    const radioQuestion = document.getElementById("radioQuestion");
    const responseErrorRadio = document.getElementById("radio-question-error");
    const responseSuccessRadio = document.getElementById("radio-question-success");
    const btnRadioBack = document.getElementById("btnRadioBack");
    const btnRadioNext = document.getElementById("btnRadioNext");
    const btnRadioAdd = document.getElementById("btnRadioAdd");

    if (radioQuestion.value !== "" && radioQuestion.value !== null) {

        //Display question and push it into JSON object
        let body = "";
        body += "<div id=question" + json.questions.length + " class='main-panel border rounded p-4'>";
        body += "<div class='lead mb-2'>" + radioQuestion.value + "</div>";

        const obj2 = [];
        for (let i = 0; i <= answer2Number; i++) {
            const inputValue = document.getElementById("2answer" + i).value;
            if (inputValue !== null && inputValue !== "" && inputValue !== " " && inputValue !== undefined && inputValue !== "   ") {
                tab1[i] = inputValue;
                body += `<div>
                      <input type="radio" id='a` + i + `' disabled>
                      <label for='a` + i + `'>` + document.getElementById("2answer" + i).value + `</label>
                 </div>`;
                obj2.push(tab1[i]);
            }
        }

        const obj = {};
        obj.type = 2;
        obj.question = radioQuestion.value;
        obj.answers = obj2;

        body += '<button id="chose-type" class="btn btn-dark mb-3 deleteAnswer" onclick="hideQuestion(' + json.questions.length + ')">Usuń</button> </div>';

        if (obj2.length !== 0) {
            $('body').append(body);
            json.questions.push(obj);
            responseErrorRadio.style.display = 'none';
            responseSuccessRadio.style.display = 'block';
            responseSuccessRadio.innerHTML = "Result: " + "Dodano.";
            btnRadioBack.disabled = true;
            btnRadioNext.disabled = true;
            btnRadioAdd.disabled = true;

            setTimeout(function () {
                const questionType2Answers = document.getElementsByClassName("question-type-2-answers");
                radioQuestion.value = "";
                answer2Number = 0;
                btnRadioBack.disabled = false;
                btnRadioNext.disabled = false;
                btnRadioAdd.disabled = false;
                questionType2Answers[0].innerHTML = "<input type=\"text\" class=\"form-control mb-2\" id=\"2answer0\" placeholder=\"Podaj odpowiedz\" name=\"name\">";
                hideQuestionTypes();
                showQuestionTypePanel();
                responseSuccessRadio.style.display = 'none';
            }, 2000);
        } else {
            responseErrorRadio.innerHTML = "Error: " + "Odpowiedzi są puste, wypełnij pola.";
            responseErrorRadio.style.display = 'block';
        }
    } else {
        responseErrorRadio.innerHTML = "Error: " + "Pytanie jest puste, wypełnij pole.";
        responseErrorRadio.style.display = 'block';
    }
}

const tab2 = new Array(answer3Number);

function addAnswer3() {
    for (let i = 0; i <= answer3Number; i++) {
        tab2[i] = document.getElementById("3answer" + i).value;
    }

    const questionType3Answers = document.getElementsByClassName("question-type-3-answers");
    const temp = "3answer" + answer3Number;
    const answer3 = document.getElementById(temp).value;

    if (answer3 !== "" && answer3 !== null) {
        answer3Number++;
        questionType3Answers[0].innerHTML = questionType3Answers[0].innerHTML + "<input type=\"text\" class=\"form-control mb-2\" id=\"" + "3answer" + answer3Number + "\" placeholder=\"Podaj odpowiedz\" name=\"name\">";
    }

    updateContent3();
}

function updateContent3() {
    for (let i = 0; i < answer3Number; i++) {
        const tempElement1 = document.getElementById("3answer" + i);
        tempElement1.value = tab2[i];
    }
}

function nextQuestionCheckbox() {
    const checkboxQuestion = document.getElementById("checkboxQuestion");
    const responseErrorCheckbox = document.getElementById("checkbox-question-error");
    const responseSuccessCheckbox = document.getElementById("checkbox-question-success");
    const btnCheckboxBack = document.getElementById("btnCheckboxBack");
    const btnCheckboxNext = document.getElementById("btnCheckboxNext");
    const btnCheckboxAdd = document.getElementById("btnCheckboxAdd");

    if (checkboxQuestion.value !== "" && checkboxQuestion.value !== null) {

        //Display question and push it into JSON object
        let body = "";
        body += "<div id=question" + json.questions.length + " class='main-panel border rounded p-4'>";
        body += "<div class='lead mb-2'>" + checkboxQuestion.value + "</div>";

        const obj2 = [];
        for (let i = 0; i <= answer3Number; i++) {
            const inputValue = document.getElementById("3answer" + i).value;
            if (inputValue !== null && inputValue !== "" && inputValue !== " " && inputValue !== undefined && inputValue !== "   ") {
                tab2[i] = inputValue;
                body += `<div>
                      <input type="radio" id='a` + i + `' disabled>
                      <label for='a` + i + `'>` + document.getElementById("3answer" + i).value + `</label>
                 </div>`;
                obj2.push(tab2[i]);
            }
        }

        const obj = {};
        obj.type = 3;
        obj.question = checkboxQuestion.value;
        obj.answers = obj2;

        body += '<button id="chose-type" class="btn btn-dark mb-3 deleteAnswer" onclick="hideQuestion(' + json.questions.length + ')">Usuń</button> </div>';

        if (obj2.length !== 0) {
            $('body').append(body);
            json.questions.push(obj);

            responseErrorCheckbox.style.display = 'none';
            responseSuccessCheckbox.style.display = 'block';
            responseSuccessCheckbox.innerHTML = "Result: " + "Dodano.";
            btnCheckboxBack.disabled = true;
            btnCheckboxNext.disabled = true;
            btnCheckboxAdd.disabled = true;

            setTimeout(function () {
                const questionType3Answers = document.getElementsByClassName("question-type-3-answers");
                checkboxQuestion.value = "";
                answer3Number = 0;
                btnCheckboxBack.disabled = false;
                btnCheckboxNext.disabled = false;
                btnCheckboxAdd.disabled = false;
                questionType3Answers[0].innerHTML = "<input type=\"text\" class=\"form-control mb-2\" id=\"3answer0\" placeholder=\"Podaj odpowiedz\" name=\"name\">";
                hideQuestionTypes();
                showQuestionTypePanel();
                responseSuccessCheckbox.style.display = 'none';
            }, 2000);

        } else {
            responseErrorCheckbox.innerHTML = "Error: " + "Odpowiedzi są puste, wypełnij pola.";
            responseErrorCheckbox.style.display = 'block';
        }
    } else {
        responseErrorCheckbox.innerHTML = "Error: " + "Pytanie jest puste, wypełnij pole.";
        responseErrorCheckbox.style.display = 'block';
    }
}

function nextQuestionOpen() {
    const openQuestion = document.getElementById("openQuestion").value;
    const responseErrorOpen = document.getElementById("open-question-error");
    const responseSuccessOpen = document.getElementById("open-question-success");
    const btnOpenBack = document.getElementById("btnOpenBack");
    const btnOpenNext = document.getElementById("btnOpenNext");

    if (openQuestion !== "" && openQuestion !== null) {

        //Display question and push it into JSON object
        let body = "";
        body += "<div id=question" + json.questions.length + " class='main-panel border rounded p-4'>";
        body += "<div class='border pl-2 pt-2 pr-2 rounded' style='background-color: #eaeaea;'><p>" + openQuestion + "</p></div>";
        body += '<button id="chose-type" class="btn btn-dark mb-1 mt-2 deleteAnswer" onclick="hideQuestion(' + json.questions.length + ')">Usuń</button> </div>';
        body += "</div>";
        $('body').append(body);

        const obj = {};
        obj.type = 1;
        obj.question = openQuestion;

        json.questions.push(obj);

        responseErrorOpen.style.display = 'none';
        responseSuccessOpen.style.display = 'block';
        responseSuccessOpen.innerHTML = "Result: " + "Dodano.";
        btnOpenBack.disabled = true;
        btnOpenNext.disabled = true;

        setTimeout(function () {
            document.getElementById("openQuestion").value = "";
            btnOpenBack.disabled = false;
            btnOpenNext.disabled = false;
            hideQuestionTypes();
            showQuestionTypePanel();
            responseSuccessOpen.style.display = 'none';
        }, 2000);

    } else {
        responseErrorOpen.innerHTML = "Error: " + "Wypełnij pola.";
        responseErrorOpen.style.display = 'block';
    }
}

function showConfirm() {
    const confirmPanel = document.getElementsByClassName("confirmation");
    confirmPanel[0].style.display = 'block';
}

function hideConfirm() {
    const confirmPanel = document.getElementsByClassName("confirmation");
    confirmPanel[0].style.display = 'none';
}

function confirm(choice, buttonId = null) {
    if (buttonId !== null) {
        buttonId.disabled = true;
    }

    const confirmationError = document.getElementById("confirm-question-error");
    const confirmationSuccess = document.getElementById("confirm-success");

    if (choice === true) {
        if (json["questions"] !== undefined && json["questions"] !== "" && json["questions"].length !== 0 && json["objectNumber0"] !== null) {
            confirmationError.style.display = 'none';
            confirmationSuccess.style.display = 'block';
            confirmationSuccess.innerHTML = "Result: " + "Wysłano.";
            window.setTimeout(function () {
                finishSurvey(buttonId);
            }, 2000);
        } else {
            confirmationError.style.display = 'block';
            confirmationError.innerHTML = "Error: " + "Ankieta jest pusta.";
            window.setTimeout(function () {
                confirmationError.style.display = 'none';
                buttonId.disabled = false;
                hideConfirm();
            }, 3000);
        }
    } else {
        const confirmPanel = document.getElementsByClassName("confirmation");
        confirmPanel[0].style.display = 'none';
    }
}

function finishSurvey(buttonId) {
    const data = JSON.stringify(json);
    console.log(json);
    const xhr = new XMLHttpRequest();

    const confirmationError = document.getElementById("confirm-question-error");
    const confirmationSuccess = document.getElementById("confirm-success");

    xhr.open('POST', 'http://localhost:8080/surveyCreator', true);
    xhr.setRequestHeader("Content-Type", "application/json; charset=UTF-8");
    xhr.send(data);
    xhr.onreadystatechange = function () {
        if (xhr.readyState === XMLHttpRequest.DONE) {
            if (xhr.status === 200) {
                const deleteAnswer = $(".deleteAnswer");
                deleteAnswer.attr("disabled", true);

                const jsonResult = JSON.parse(xhr.responseText);

                if (jsonResult.error) {
                    confirmationSuccess.style.display = 'none';
                    confirmationError.style.display = 'block';
                    confirmationError.innerHTML = "Error: " + jsonResult.error;
                    deleteAnswer.attr("disabled", false);
                    window.setTimeout(function () {
                        confirmationError.style.display = 'none';
                        buttonId.disabled = false;
                        hideConfirm();
                    }, 3000);
                } else if (jsonResult.result) {
                    confirmationError.style.display = 'none';
                    confirmationSuccess.style.display = 'block';
                    confirmationSuccess.innerHTML = "Result: " + jsonResult.result;
                    waitAndRedirectToMain();
                }
            }
        }
    };
}

function hideQuestion(id) {
    delete json.questions[id];
    const questionDiv = document.getElementById("question" + id);
    questionDiv.style.display = 'none';
}

function waitAndRedirectToMain() {
    window.setTimeout(function () {
        location.href = "main.html";
    }, 3000);
}