const id = document.cookie;
console.log(id);

var xmlHttp = new XMLHttpRequest(); 
xmlHttp.open( "POST", "http://localhost:8080/WeatherAppDB/getProfile", false );
xmlHttp.setRequestHeader("Content-Type", "application/x-www-form-urlencoded");
xmlHttp.send(id);

if(xmlHttp.status==200){
    console.log(xmlHttp.response);
    
    
}