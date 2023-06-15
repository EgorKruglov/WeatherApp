
// Получение прогноза по локации
let xmlHttp = new XMLHttpRequest();
xmlHttp.open( "GET", "http://localhost:8080/WeatherAppDB/all/tblUsers", false );
xmlHttp.send( null );
const users = JSON.parse(xmlHttp.response)
console.log(users);

// Получение связей пользовтель-локация
let xmlHttp2 = new XMLHttpRequest();
xmlHttp2.open("GET", "http://localhost:8080/WeatherAppDB/all/tblLocationUserRelations", false);
xmlHttp2.send(null);
const usersLocations = JSON.parse(xmlHttp2.responseText).relations;
console.log(usersLocations);



for (let i = 0; i < users.length; i++) {  //цикл по количеству полученных пользователей

    const node = document.createElement("div");  //создаю карточку пользователя
    node.classList.add("user-search__card");

    const node2 = document.createElement("p");
    node2.classList.add("user-search__text");
    node2.id = "search_name";
    node2.appendChild(document.createTextNode(users[i].name + ' ' + users[i].surname));

    const node3 = document.createElement("p");
    node3.classList.add("user-search__text");
    node3.id = "search_id";
    node3.appendChild(document.createTextNode(users[i].user_id));

    const node4 = document.createElement("p");
    node4.classList.add("user-search__text");
    node4.id = "search_location";
    node4.appendChild(document.createTextNode(getLocationsByRequest(usersLocations, users[i].user_id))); // Давай сюда напишем все локации юзера

    node.appendChild(node2);
    node.appendChild(node3);
    node.appendChild(node4);

    document.getElementById("user_cards").appendChild(node);  //добавляю карточку в контейнер с id = "id"


}

function getLocationsByRequest(usersLocations, user_id) {
    let locations = '';
    for (let i = 0; i < usersLocations.length; i++) {
        if (usersLocations[i].user_id == user_id) {
            if (locations != '') {
                locations += ', ';
            }
            locations += usersLocations[i].location_name;

        }
    }
    return locations;
}
