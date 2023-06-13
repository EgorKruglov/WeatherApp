const popup = document.querySelector(".popup");  //берём попап
const closeButton = popup.querySelector(".popup__close-button");  //берём кнопку закрытия попапа


var xmlHttp = new XMLHttpRequest();  //объект для запроса
    xmlHttp.open( "GET", "http://localhost:8080/get/locations", false );   //закидиваем параметры запроса (какой запрос, ссылка и ассинхронно ли)
    // false for synchronous request
    
    xmlHttp.send( null );  
    
    if(xmlHttp.status==200){  //проверяю удачный ли запрос
      console.log(xmlHttp.response);  //вывожу в лог то, что получил

      const Locations = JSON.parse(xmlHttp.response);  //парсим json

      for (let i = 0; i < Locations.length; i++) {  //цикл по количеству полученных локаций
        
        
        const node = document.createElement("div");  //создаю карточку локации
        node.classList.add("user__location");  //класс карточки локации
        const node2 = document.createElement("p");  //поле для текста
        node2.classList.add("user__location-place");  //класс поля для текста

        node2.appendChild(document.createTextNode("lat: "+Locations[i].lat+ "  lan: " + Locations[i].lon));  //в поле передаю координаты и название
        node.appendChild(node2);  //добавляю текст в карточку

        document.getElementById("id").appendChild(node);  //добавляю карточку в контейнер с id = "id"
      } //карточек создаётся столько, сколько пришло локаций
    }
    
    var xmlHttp2 = new XMLHttpRequest();
    xmlHttp2.open( "POST", "http://localhost:8080/WeatherAppDB/getProfile", false );
xmlHttp2.setRequestHeader("Content-Type", "application/x-www-form-urlencoded");
xmlHttp2.send(document.cookie);

if(xmlHttp2.status==200){
    console.log(xmlHttp2.response);
    
    const profile = JSON.parse(xmlHttp2.response);


    document.getElementById("name").innerText = profile.name + " " + profile.surname;
    document.getElementById("userID").innerText = profile.email;
}



const cards = document.querySelectorAll(".user__location");  //нахожу все карточки локаций



const openPopup = () => {
  popup.classList.add("popup_opened");
};

const closePopup = () => {
  popup.classList.remove("popup_opened");
  
};

cards.forEach(card => card.addEventListener("click", openPopup));  //добавляю каждой карточке попап
closeButton.addEventListener("click", closePopup);  // у попапа добавляю закрытие


