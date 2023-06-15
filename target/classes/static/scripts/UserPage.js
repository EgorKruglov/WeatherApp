const popup = document.querySelector(".popup");  //берём попап
const closeButton = popup.querySelector(".popup__close-button");  //берём кнопку закрытия попапа


let xmlHttp = new XMLHttpRequest();  //объект для запроса
    xmlHttp.open( "GET", "http://localhost:8080/get/locations", false );   //закидиваем параметры запроса (какой запрос, ссылка и ассинхронно ли)
    // false for synchronous request
    
    xmlHttp.send( null );  
    
    if(xmlHttp.status==200){  //проверяю удачный ли запрос
       

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
  document.querySelectorAll('input[type=checkbox]').forEach(el => el.checked = false);
  location_name.value = "";
        temperature_from.value = "";
        temperature_to.value = "";
        wind_from.value = "";
        wind_to.value = "";
        humidity_from.value = "";
        humidity_to.value = "";
        lat.value = "";
        lon.value = "";


  const locationID = location.currentTarget.id;
  if (locationID != "add"){

  let getLocation = new XMLHttpRequest();
  getLocation.open( "POST", "http://localhost:8080/get/location", false );
  getLocation.setRequestHeader("Content-Type", "application/x-www-form-urlencoded");
  getLocation.send("location_id="+locationID);

    if(getLocation.status==200){
      console.log(getLocation.response);
        const location = JSON.parse(getLocation.response);
        const customTrigger = location.custom_triggerID;
        location_name.value = location.location_name;
        temperature_from.value = customTrigger.celsius_min;
        temperature_to.value = customTrigger.celsius_max;
        wind_from.value = customTrigger.wind_speed_min;
        wind_to.value = customTrigger.wind_speed_max;
        humidity_from.value = customTrigger.humidity_min;
        humidity_to.value = customTrigger.humidity_max;
        lat.value = location.lat;
        lon.value = location.lon;
        
        customTrigger.conditions.forEach(condition =>{
          document.getElementById(condition.condition).checked = true;
        });
    }
  }
  sessionStorage.setItem("location",locationID);
  popup.classList.add("popup_opened");
};

const closePopup = () => {
  
  popup.classList.remove("popup_opened");
  document.querySelector(".popup__form").scrollTo(0,0);
  
};

const submitTriggers = () => {
  const location = sessionStorage.getItem("location");
  if (location == "add"){
    var conditions =[];
    var checkedBoxes = document.querySelectorAll('input[name=name]:checked');
    for (i = 0; i < checkedBoxes.length; i++) { 
    conditions.push({
      condition:checkedBoxes[i].id
    });}
    const name = document.getElementById("input_location-name").value;
    const custom = {"conditions" : conditions, "celsius_min": temperature_from.value, "celsius_max": temperature_to.value, "humidity_min": humidity_from.value, "humidity_max": humidity_to.value, "wind_speed_min":wind_from.value, "wind_speed_max":wind_to.value};  
    const defaultT = {"conditions" : conditions, "celsius_min": temperature_from.value, "celsius_max": temperature_to.value, "humidity_min": humidity_from.value, "humidity_max": humidity_to.value, "wind_speed_min":wind_from.value, "wind_speed_max":wind_to.value};  
    const newLocation = JSON.stringify({"lat": lat.value, "lon": lon.value, "location_name": name, "default_triggerID": defaultT, "custom_triggerID": custom});

    let addLocation = new XMLHttpRequest();
    addLocation.open( "POST", "http://localhost:8080/add/location", false );
    addLocation.setRequestHeader("Accept", "application/json");
    addLocation.setRequestHeader("Content-Type", "application/json");
    addLocation.send(newLocation);
    if(addLocation.status==200){
      window.alert("Location was added.");
    }
  }

  else{
  var conditions =[];
  var checkedBoxes = document.querySelectorAll('input[name=name]:checked');
  for (i = 0; i < checkedBoxes.length; i++) { 
    conditions.push({
      condition:checkedBoxes[i].id
    });
  }

const name = "&name="+document.getElementById("input_location-name").value;


  const data2 = JSON.stringify({"custom_trigger_id" : location, "conditions" : conditions, "celsius_min": temperature_from.value, "celsius_max": temperature_to.value, "humidity_min": humidity_from.value, "humidity_max": humidity_to.value, "wind_speed_min":wind_from.value, "wind_speed_max":wind_to.value});

  let sendTriggers = new XMLHttpRequest();
  sendTriggers.open( "POST", "http://localhost:8080/set/custom", false );
  sendTriggers.setRequestHeader("Accept", "application/json");
  sendTriggers.setRequestHeader("Content-Type", "application/json");
  sendTriggers.send(data2);

  let sendLocationName = new XMLHttpRequest();
  sendLocationName.open( "POST", "http://localhost:8080/set/locationName", false );
  sendLocationName.setRequestHeader("Content-Type", "application/x-www-form-urlencoded");
  sendLocationName.send("location_id="+location+name);
}
};

cards.forEach(card => card.addEventListener("click", openPopup));  
closeButton.addEventListener("click", closePopup);  
document.getElementById("submitTriggers").addEventListener("click", submitTriggers);
document.getElementById("add").addEventListener("click", openPopup);
