const submitButton = document.querySelector(".registration__submit-button");



// Извлекаем элементы
var registrationForm = document.getElementById('registrationForm');

submitButton.onclick = function Submit() {
    // Проверка на соответствие паролей
    if (document.getElementById("registration_password").value !==
         document.getElementById("registration_password-repeat").value) {
                alert('Passwords do not match. Please try again.');
                return;
            }

    const reg_body =
        "name=" + document.getElementById("registration_name").value+
        "&surname=" + document.getElementById("registration_last_name").value+
        "&phone_number=" + document.getElementById("registration_number").value+
        "&email=" + document.getElementById("email").value+
        "&password=" + document.getElementById("registration_password").value+
        "&password_repeat=" + document.getElementById("registration_password-repeat").value+
        "&root=" + "0"

    console.log(reg_body);


    let xmlHttp = new XMLHttpRequest();
    xmlHttp.open( "POST", "http://localhost:8080/WeatherAppDB/add/user", false );
    xmlHttp.setRequestHeader("Content-Type", "application/x-www-form-urlencoded");
    xmlHttp.send(reg_body);
    if(xmlHttp.status==200){
        document.cookie = "user_id="+xmlHttp.response;
        window.location.replace("http://localhost:8080");
    }

}