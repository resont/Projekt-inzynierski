var mainPanel = document.getElementsByClassName("main-panel main");
var questionTypePanel = document.getElementsByClassName("main-panel question-type")
var mainError = document.getElementsByClassName("alert alert-danger main");
var questionType1 = document.getElementsByClassName("question-type-1");
var questionType2 = document.getElementsByClassName("question-type-2");
var questionType3 = document.getElementsByClassName("question-type-3");
var surveyTitlePanel = document.getElementsByClassName("survey-title-panel");
var title = $("#name").val();
var description = $("#description").val();
var json = {
    questions: []
};
var objectNumber = 0;
var answer2Number = 0;
var answer3Number = 0;

function add() {

    title = $("#name").val();
    description = $("#description").val();
    if (title && description) {
        mainPanel[0].style.display = 'none';
        questionTypePanel[0].style.display = 'block';
        surveyTitlePanel[0].style.display = 'block';
        surveyTitlePanel[0].children[0].children[0].textContent = title;
        surveyTitlePanel[0].children[1].children[0].textContent = description;

        json.title = title;
        json.description = description;

    } else {
        mainError[0].style.display = 'block';
        mainError[0].innerHTML = "Wypełnij pola.";
    }

}

var typeDescription = document.getElementsByClassName("type-description");
var typeDescriptionBorder = document.getElementsByClassName("type-border hide");

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
    var radioOption = document.getElementsByClassName("btn btn-secondary active");
    var radioOptionValue = radioOption[0].children[0].id;
    var confirmPanel = document.getElementsByClassName("confirmation");
    confirmPanel[0].style.display = 'none';

    if (radioOptionValue === "option1") {
        hideQuestionTypePanel();
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

var tab1 = new Array(answer2Number);

function addAnswer2() {
    for (var i = 0; i <= answer2Number; i++) {
        var tempElement = document.getElementById("2answer" + i).value;
        tab1[i] = tempElement;
    }

    var questionType2Answers = document.getElementsByClassName("question-type-2-answers");
    var temp = "2answer" + answer2Number;
    var answer2 = document.getElementById(temp).value;

    if (answer2 !== "" && answer2 !== null) {
        answer2Number++;
        questionType2Answers[0].innerHTML = questionType2Answers[0].innerHTML + "<input type=\"text\" class=\"form-control mb-2\" id=\"" + "2answer" + answer2Number + "\" placeholder=\"Podaj odpowiedz\" name=\"name\">";
    }

    updateContent2();
}

function updateContent2() {
    for (var i = 0; i < answer2Number; i++) {
        var tempElement1 = document.getElementById("2answer" + i);
        tempElement1.value = tab1[i];
    }
}

function nextQuestionRadio() {
    var radioQuestion = document.getElementById("radioQuestion");
    var responseErrorRadio = document.getElementById("radio-question-error");
    var responseSuccessRadio = document.getElementById("radio-question-success");

    if (radioQuestion.value !== "" && radioQuestion.value !== null) {

        var obj2 = [];
        for (var i = 0; i <= answer2Number; i++) {
            var tempElement = document.getElementById("2answer" + i).value;
            tab1[i] = tempElement;
        }

        for (var i = 0; i <= answer2Number; i++) {
            obj2.push(tab1[i]);
        }

        var obj = {};
        obj.type = 2;
        obj.question = radioQuestion.value;
        obj.answers = obj2;

        var property = "objectNumber" + objectNumber++;
        json.questions.push(obj);
        responseErrorRadio.style.display = 'none';
        responseSuccessRadio.style.display = 'block';
        responseSuccessRadio.innerHTML = "Result: " + "Dodano.";
        setTimeout(function () {
            var questionType2Answers = document.getElementsByClassName("question-type-2-answers");
            radioQuestion.value = "";
            answer2Number = 0;
            questionType2Answers[0].innerHTML = "<input type=\"text\" class=\"form-control mb-2\" id=\"2answer0\" placeholder=\"Podaj odpowiedz\" name=\"name\">";
            hideQuestionTypes();
            showQuestionTypePanel();
            responseSuccessRadio.style.display = 'none';
        }, 2000);

    } else {
        responseErrorRadio.innerHTML = "Error: " + "Wypełnij pola.";
        responseErrorRadio.style.display = 'block';
    }
}

var tab2 = new Array(answer3Number);

function addAnswer3() {
    for (var i = 0; i <= answer3Number; i++) {
        var tempElement = document.getElementById("3answer" + i).value;
        tab2[i] = tempElement;
    }

    var questionType3Answers = document.getElementsByClassName("question-type-3-answers");
    var temp = "3answer" + answer3Number;
    var answer3 = document.getElementById(temp).value;

    if (answer3 !== "" && answer3 !== null) {
        answer3Number++;
        questionType3Answers[0].innerHTML = questionType3Answers[0].innerHTML + "<input type=\"text\" class=\"form-control mb-2\" id=\"" + "3answer" + answer3Number + "\" placeholder=\"Podaj odpowiedz\" name=\"name\">";
    }

    updateContent3();
}

function updateContent3() {
    for (var i = 0; i < answer3Number; i++) {
        var tempElement1 = document.getElementById("3answer" + i);
        tempElement1.value = tab2[i];
    }
}

function nextQuestionCheckbox() {
    var checkboxQuestion = document.getElementById("checkboxQuestion");
    var responseErrorCheckbox = document.getElementById("checkbox-question-error");
    var responseSuccessCheckbox = document.getElementById("checkbox-question-success");

    if (checkboxQuestion.value !== "" && checkboxQuestion.value !== null) {

        var obj2 = [];
        for (var i = 0; i <= answer3Number; i++) {
            var tempElement = document.getElementById("3answer" + i).value;
            tab2[i] = tempElement;
        }

        for (var i = 0; i <= answer3Number; i++) {
            obj2.push(tab2[i]);
        }

        var obj = {};
        obj.type = 3;
        obj.question = checkboxQuestion.value;
        obj.answers = obj2;

        var property = "objectNumber" + objectNumber++;
        json.questions.push(obj);
        responseErrorCheckbox.style.display = 'none';
        responseSuccessCheckbox.style.display = 'block';
        responseSuccessCheckbox.innerHTML = "Result: " + "Dodano.";
        setTimeout(function () {
            var questionType3Answers = document.getElementsByClassName("question-type-3-answers");
            checkboxQuestion.value = "";
            answer3Number = 0;
            questionType3Answers[0].innerHTML = "<input type=\"text\" class=\"form-control mb-2\" id=\"3answer0\" placeholder=\"Podaj odpowiedz\" name=\"name\">";
            hideQuestionTypes();
            showQuestionTypePanel();
            responseSuccessCheckbox.style.display = 'none';
        }, 2000);

    } else {
        responseErrorCheckbox.innerHTML = "Error: " + "Wypełnij pola.";
        responseErrorCheckbox.style.display = 'block';
    }
}

function nextQuestionOpen() {
    var openQuestion = document.getElementById("openQuestion").value;
    var responseErrorOpen = document.getElementById("open-question-error");
    var responseSuccessOpen = document.getElementById("open-question-success");

    if (openQuestion !== "" && openQuestion !== null) {

        var obj = {};
        obj.type = 1;
        obj.question = openQuestion;

        var property = "objectNumber" + objectNumber++;
        json.questions.push(obj);

        responseErrorOpen.style.display = 'none';
        responseSuccessOpen.style.display = 'block';
        responseSuccessOpen.innerHTML = "Result: " + "Dodano.";
        setTimeout(function () {
            document.getElementById("openQuestion").value = "";
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
    var confirmPanel = document.getElementsByClassName("confirmation");
    confirmPanel[0].style.display = 'block';
}

function confirm(choice, buttonId = null) {
    if (buttonId !== null) {
        buttonId.disabled = true;
    }

    var confirmationError = document.getElementById("confirm-question-error");
    var confirmationSuccess = document.getElementById("confirm-success");

    if (choice === true) {
        if (json["questions"] !== undefined && json["questions"] !== "" && json["objectNumber0"] !== null) {
            confirmationError.style.display = 'none';
            confirmationSuccess.style.display = 'block';
            confirmationSuccess.innerHTML = "Result: " + "Wysłano.";
            finishSurvey();
        } else {
            confirmationError.style.display = 'block';
            confirmationError.innerHTML = "Error: " + "Ankieta jest pusta.";
        }
    } else {
        var confirmPanel = document.getElementsByClassName("confirmation");
        confirmPanel[0].style.display = 'none';
    }
}

function finishSurvey() {
    var data = JSON.stringify(json);
    var xhr = new XMLHttpRequest();

    xhr.open('POST', 'http://localhost:8080/surveyCreator', true);
    xhr.setRequestHeader("Content-Type", "application/json; charset=UTF-8");
    xhr.send(data);
    xhr.onreadystatechange = function () {
        if (xhr.readyState === XMLHttpRequest.DONE) {
            if (xhr.status === 200) {
                redirectToMain();
            }
        }
    };
}