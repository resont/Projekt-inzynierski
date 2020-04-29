var mainPanel = document.getElementsByClassName("main-panel main");
var questionTypePanel = document.getElementsByClassName("main-panel question-type")
var mainError = document.getElementsByClassName("alert alert-danger main");
var questionType1 = document.getElementsByClassName("question-type-1");
var questionType2 = document.getElementsByClassName("question-type-2");
var questionType3 = document.getElementsByClassName("question-type-3");
var surveyTitlePanel = document.getElementsByClassName("survey-title-panel");
var title = $("#name").val();
var description = $("#description").val();

function add() {

    title = $("#name").val();
    description = $("#description").val();
    if (title && description) {
        mainPanel[0].style.display = 'none';
        questionTypePanel[0].style.display = 'block';
        surveyTitlePanel[0].style.display = 'block';
        surveyTitlePanel[0].children[0].children[0].textContent = title;
        surveyTitlePanel[0].children[1].children[0].textContent = description;
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

var questionType2Answers = document.getElementsByClassName("question-type-2-answers");
var questionType3Answers = document.getElementsByClassName("question-type-3-answers");

function addAnswer2() {
    questionType2Answers[0].innerHTML = questionType2Answers[0].innerHTML + "<input type=\"text\" class=\"form-control mb-2\" placeholder=\"Podaj odpowiedz\" name=\"name\">";
}

function addAnswer3() {
    questionType3Answers[0].innerHTML = questionType3Answers[0].innerHTML + "<input type=\"text\" class=\"form-control mb-2\" placeholder=\"Podaj odpowiedz\" name=\"name\">";
}

function nextQuestion() {

}