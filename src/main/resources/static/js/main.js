function add() {
    var title = $("#name").val();
    var description = $("#description").val();
    var mainPanel = document.getElementsByClassName("main-panel main");
    var questionTypePanel = document.getElementsByClassName("main-panel question-type")
    var mainError = document.getElementsByClassName("alert alert-danger main");

    if (title && description) {
        mainPanel[0].style.display = 'none';
        questionTypePanel[0].style.display = 'block';
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

}