window.onload = function(){
    showAvailableSurveys();
    showLogoutAndProfile();
};
var json;

function showAvailableSurveys(){

    var xhr = new XMLHttpRequest();
    var token = $.cookie("token");

    xhr.onreadystatechange = function () {
        if (xhr.readyState === XMLHttpRequest.DONE && xhr.status === 200) {
            var surveyIds = xhr.responseText.substring(1, xhr.responseText.length-1);
            surveyIds = surveyIds.split(',').map(x=>+x);
            var html = "<div class=\"main-panel border rounded p-4\">";
            for(var i in surveyIds){
                getTitleAndDescription(surveyIds[i]);
                var topic = json.topic;
                var description = json.description;

                html += "<div class=\"card mr-2 mb-2\" style=\"width: 18.2rem; float: left;\">\n" +
                    "  <div class=\"card-body\">\n" +
                    "    <h5 class=\"card-title\">"+topic+"</h5>\n" +
                    "    <p class=\"card-text\">"+description+"</p>\n" +
                    "    <button class=\"btn btn-dark\" onclick='goToSurvey("+surveyIds[i]+")'>Open survey</button>\n" +
                    "  </div>\n" +
                    "</div>";
            }
            html += "</div>";
            $("body").append(html);

        }
    };

    xhr.open('GET', 'http://localhost:8080/con_us_su/' + token, false);
    xhr.send(null);

}

function goToSurvey(id) {
    sessionStorage.setItem('surveyId',id);
    window.setTimeout(function () {
        location.href = "surveyPage.html";
    }, );
}

function getTitleAndDescription(id){
    var xhr = new XMLHttpRequest();
    xhr.onreadystatechange = function () {
        if (xhr.readyState === XMLHttpRequest.DONE && xhr.status === 200) {
            json = JSON.parse(xhr.responseText);
        }
    };
    xhr.open('GET', 'http://localhost:8080/survey/' + id, false);
    xhr.send(null);
}
