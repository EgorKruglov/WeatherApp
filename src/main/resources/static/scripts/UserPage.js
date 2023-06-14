const popup = document.querySelector(".popup");  //берём попап
const closeButton = popup.querySelector(".popup__close-button");  //берём кнопку закрытия попапа


let xmlHttp = new XMLHttpRequest();  //объект для запроса
    xmlHttp.open( "GET", "http://localhost:8080/get/locations", false );   //закидиваем параметры запроса (какой запрос, ссылка и ассинхронно ли)
    // false for synchronous request
    
    xmlHttp.send( null );  
    
    if(xmlHttp.status==200){  //проверяю удачный ли запрос
      console.log(xmlHttp.response);  //вывожу в лог то, что получил

      const Locations = JSON.parse(xmlHttp.response);  //парсим json

      for (let i = 0; i < Locations.length; i++) {  //цикл по количеству полученных локаций
        
        
        const node = document.createElement("div");  //создаю карточку локации
        node.classList.add("user__location");  //класс карточки локации
        
        node.id = Locations[i].location_id;
        const node2 = document.createElement("p");  //поле для текста
        node2.classList.add("user__location-place");  //класс поля для текста

        node2.appendChild(document.createTextNode("lat: "+Locations[i].lat+ "  lon: " + Locations[i].lon));  //в поле передаю координаты и название
        node.appendChild(node2);  //добавляю текст в карточку

        document.getElementById("id").appendChild(node);  //добавляю карточку в контейнер с id = "id"
      } //карточек создаётся столько, сколько пришло локаций
    }
    
    let xmlHttp2 = new XMLHttpRequest();
    xmlHttp2.open( "POST", "http://localhost:8080/WeatherAppDB/getProfile", false );
xmlHttp2.setRequestHeader("Content-Type", "application/x-www-form-urlencoded");
xmlHttp2.send(document.cookie);

if(xmlHttp2.status==200){
    console.log(xmlHttp2.response);
    
    const profile = JSON.parse(xmlHttp2.response);


    document.getElementById("name").innerText = profile.name + " " + profile.surname;
    document.getElementById("userID").innerText = profile.user_id;
}



const cards = document.querySelectorAll(".user__location");  //нахожу все карточки локаций

const location_name = document.getElementById("input_location-name");
const temperature_from = document.getElementById("input_temperature_from");
const temperature_to = document.getElementById("input_temperature_to");
const wind_from = document.getElementById("input_wind_from");
const wind_to = document.getElementById("input_wind_to");
const humidity_from = document.getElementById("input_humidity_from");
const humidity_to = document.getElementById("input_humidity_to");
const lat = document.getElementById("lat");
const lon = document.getElementById("lon");

const openPopup = (location) => {
  location_name.value=location.currentTarget.id;
  popup.classList.add("popup_opened");
};

const closePopup = () => {
  
  popup.classList.remove("popup_opened");
  document.querySelector(".popup__form").scrollTo(0,0);
  location_name.value="";
  temperature_from.value="";
  temperature_to.value="";
  wind_from.value="";
  wind_to.value="";
  humidity_from.value="";
  humidity_to.value="";
  lat.value="";
  lon.value="";
};

cards.forEach(card => card.addEventListener("click", openPopup));  
closeButton.addEventListener("click", closePopup);  


