const id = document.cookie;
console.log(id);

var xmlHttp = new XMLHttpRequest(); 
xmlHttp.open( "POST", "http://localhost:8080/WeatherAppDB/getProfile", false );
xmlHttp.setRequestHeader("Content-Type", "application/x-www-form-urlencoded");
xmlHttp.send(id);

if(xmlHttp.status==200){
    console.log(xmlHttp.response);
    
    const profile = JSON.parse(xmlHttp.response);

if(profile.root == 1){
    window.location.replace("http://localhost:8080/UserPage");
}else{

    document.getElementById("name").innerText = profile.name + " " + profile.surname;
    document.getElementById("email").innerText = profile.email;
    document.getElementById("phone").innerText = profile.phone_number;

    for (let i = 0; i < profile.locations.length; i++) {
        const node = document.createElement("div");  //создаю карточку локации
        node.classList.add("profile__location");  //класс карточки локации
        node.setAttribute("id", profile.locations[i].location_id);

        const node2 = document.createElement("p");  //поле для текста
        node2.classList.add("profile__location-place");  //класс поля для текста

        node2.appendChild(document.createTextNode(profile.locations[i].location_name));  //в поле передаю координаты и название
        node.appendChild(node2);  //добавляю текст в карточку

        document.getElementById("container").appendChild(node); 
    }
}

    
}