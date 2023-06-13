const submit_but = document.querySelector(".login__submit-button");



submit_but.onclick = function Submit(){//window.location.replace("./ProfilePage.html");
    const auth_body =
        "login="+document.getElementById("input_login").value+
        "&password="+document.getElementById("input_password").value
    ; 
    console.log(auth_body);
    
    var xmlHttp = new XMLHttpRequest(); 
    xmlHttp.open( "POST", "http://localhost:8080/WeatherAppDB/auth", false );
    xmlHttp.setRequestHeader("Content-Type", "application/x-www-form-urlencoded");
    xmlHttp.send(auth_body);
    if(xmlHttp.status==200){
        document.cookie = "user_id="+xmlHttp.response;
        
        
    }
    
}