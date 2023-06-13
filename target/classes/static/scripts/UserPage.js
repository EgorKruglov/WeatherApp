const popup = document.querySelector(".popup");  //берём попап
const closeButton = popup.querySelector(".popup__close-button");  //берём кнопку закрытия попапа


var xmlHttp = new XMLHttpRequest();  //объект для запроса
    xmlHttp.open( "GET", "http://localhost:8080/get/locations", false );   //закидиваем параметры запроса (какой запрос, ссылка и ассинхронно ли)
    // false for synchronous request
    
    xmlHttp.send( null );  //ничего не отправляем т.к. GET 
    //в случае с POST надо будет указать: 
    //xmlHttp.setRequestHeader("Content-Type", "application/x-www-form-urlencoded");
    //и отправить:
    //xmlHttp.send( email=email@mail.com&password=pa$$w0rd );
    
    if(xmlHttp.status==200){  //проверяю удачный ли запрос
      console.log(xmlHttp.response);  //вывожу в лог то, что получил

     //мне пришло:     
     //[{"lat":55.754,"lon":37.6204,"location_name":"Moscow"},{"lat":59.5311,"lon":50.9516,"location_name":"Nagorsky"}]


      const Locations = JSON.parse(xmlHttp.response);  //парсим json

      for (let i = 0; i < Locations.length; i++) {  //цикл по количеству полученных локаций
        
        
        const node = document.createElement("div");  //создаю карточку локации
        node.classList.add("user__location");  //класс карточки локации
        const node2 = document.createElement("p");  //поле для текста
        node2.classList.add("user__location-place");  //класс поля для текста

        node2.appendChild(document.createTextNode("lat: "+Locations[i].lat+ "  lan: " + Locations[i].lon+ " location name: " + Locations[i].location_name));  //в поле передаю координаты и название
        node.appendChild(node2);  //добавляю текст в карточку

        document.getElementById("id").appendChild(node);  //добавляю карточку в контейнер с id = "id"
      } //карточек создаётся столько, сколько пришло локаций
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


