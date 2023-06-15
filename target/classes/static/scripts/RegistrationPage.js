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

        var reg_body =
        "name=" + document.getElementById("registration_name").value+
        "&surname=" + document.getElementById("registration_last_name").value+
        "&phone_number=" + document.getElementById("registration_number").value+
        "&email=" + document.getElementById("email").value+
        "&password=" + document.getElementById("registration_password").value+
        "&password_repeat=" + document.getElementById("registration_password-repeat").value+
        "&root=";

    if(document.getElementById("admin").checked){
        reg_body+=1;
        const code = "code="+window.prompt("Input any admin's code:");
        let checkCode= new XMLHttpRequest();
        checkCode.open( "POST", "http://localhost:8080/WeatherAppDB/check/code", false );
        checkCode.setRequestHeader("Content-Type", "application/x-www-form-urlencoded");
        checkCode.send(code);
        
        if(checkCode.status==200)
        {
            let xmlHttp = new XMLHttpRequest();
            xmlHttp.open( "POST", "http://localhost:8080/WeatherAppDB/add/user", false );
            xmlHttp.setRequestHeader("Content-Type", "application/x-www-form-urlencoded");
            xmlHttp.send(reg_body);
            if(xmlHttp.status==200)
            {
                 document.cookie = "user_id="+xmlHttp.response;
                 window.location.replace("/index");
            }
        }else{
            window.alert("Admin's code was wrong");
        }
    }

    if(document.getElementById("user").checked){
        reg_body+=0;
        let xmlHttp = new XMLHttpRequest();
        xmlHttp.open( "POST", "http://localhost:8080/WeatherAppDB/add/user", false );
        xmlHttp.setRequestHeader("Content-Type", "application/x-www-form-urlencoded");
        xmlHttp.send(reg_body);
        if(xmlHttp.status==200){
            document.cookie = "user_id="+xmlHttp.response;
            window.location.replace("/index");
        }
    }
    

}