
// Получение прогноза по локации
let xmlHttp = new XMLHttpRequest();
xmlHttp.open( "POST", "http://localhost:8080/WeatherAppDB/getForecast", false );
xmlHttp.setRequestHeader("Content-Type", "application/x-www-form-urlencoded");
xmlHttp.send("location_id="+sessionStorage.getItem('location_id'));
const weather = JSON.parse(xmlHttp.response)

//Получаем нынешнее время
var now = new Date();
var hours = now.getHours().toString().padStart(2, '0');
var minutes = now.getMinutes().toString().padStart(2, '0');
var currentTime = hours + ':' + minutes;

let animation // Переменная, чтобы получать анимацию по картинке

// Получаем поля отображаемой слева карточки
const mainCardSelected = document.querySelector('.card-selected');
const mainWeatherIcon = mainCardSelected.querySelector('.card-selected__weather');
const mainTemperature = mainCardSelected.querySelector('.card-selected__temperature');
const mainDate = mainCardSelected.querySelector('.card-selected__date');
const mainWindSpeed = mainCardSelected.querySelector('.card-selected__wind-speed');
const mainHumidity = mainCardSelected.querySelector('.card-selected__humidity');
const mainLocation = mainCardSelected.querySelector('.card-selected__location');

// Отображение выбранной карточки
mainWeatherIcon.src = getImgByCondition(weather.weather[0].condition);
mainTemperature.textContent = weather.weather[0].celsius + '℃';
mainDate.textContent = 'Сегодня, ' + currentTime;
mainWindSpeed.textContent = ' ' + weather.weather[0].wind_speed + 'м/c';
mainHumidity.textContent = weather.weather[0].humidity + '%';
mainLocation.textContent = weather.location;

// Заполнение остальных карточек дополнительным параметров id
var dayCardFirst = document.getElementById("day-card-first");
dayCardFirst.additionalParameter = "0";

// Card 2
var dayCardSecond = document.getElementById("day-card-second");
dayCardSecond.additionalParameter = "1";

// Card 3
var dayCardThird = document.getElementById("day-card-third");
dayCardThird.additionalParameter = "2";

// Card 4
var dayCardFourth = document.getElementById("day-card-fourth");
dayCardFourth.additionalParameter = "3";

// Card 5
var dayCardFifth = document.getElementById("day-card-fifth");
dayCardFifth.additionalParameter = "4";

// Card 6
var dayCardSixth = document.getElementById("day-card-sixth");
dayCardSixth.additionalParameter = "5";

// Card 7
var dayCardSeventh = document.getElementById("day-card-seventh");
dayCardSeventh.additionalParameter = "6";

// Заполнение дней недели карточек
document.querySelector("#day-card-second .day-card__date").textContent = getDayOfWeek(weather.weather[1].date);
document.querySelector("#day-card-third .day-card__date").textContent = getDayOfWeek(weather.weather[2].date);
document.querySelector("#day-card-fourth .day-card__date").textContent = getDayOfWeek(weather.weather[3].date);
document.querySelector("#day-card-fifth .day-card__date").textContent = getDayOfWeek(weather.weather[4].date);
document.querySelector("#day-card-sixth .day-card__date").textContent = getDayOfWeek(weather.weather[5].date);
document.querySelector("#day-card-seventh .day-card__date").textContent = getDayOfWeek(weather.weather[6].date);

// Заполнение температур карточек
document.querySelector("#day-card-first .day-card__temperature").textContent = weather.weather[0].celsius + '℃';
document.querySelector("#day-card-second .day-card__temperature").textContent = weather.weather[1].celsius + '℃';
document.querySelector("#day-card-third .day-card__temperature").textContent = weather.weather[2].celsius + '℃';
document.querySelector("#day-card-fourth .day-card__temperature").textContent = weather.weather[3].celsius + '℃';
document.querySelector("#day-card-fifth .day-card__temperature").textContent = weather.weather[4].celsius + '℃';
document.querySelector("#day-card-sixth .day-card__temperature").textContent = weather.weather[5].celsius + '℃';
document.querySelector("#day-card-seventh .day-card__temperature").textContent = weather.weather[6].celsius + '℃';

// Заполнение картинок и анимаций карточек
document.querySelector('#day-card-first img').src = getImgByCondition(weather.weather[0].condition)
document.querySelector('#day-card-first .day-card__weather').classList.add(animation)
document.querySelector('#day-card-second img').src = getImgByCondition(weather.weather[1].condition)
document.querySelector('#day-card-second .day-card__weather').classList.add(animation)
document.querySelector('#day-card-third img').src = getImgByCondition(weather.weather[2].condition)
document.querySelector('#day-card-third .day-card__weather').classList.add(animation)
document.querySelector('#day-card-fourth img').src = getImgByCondition(weather.weather[3].condition)
document.querySelector('#day-card-fourth .day-card__weather').classList.add(animation)
document.querySelector('#day-card-fifth img').src = getImgByCondition(weather.weather[4].condition)
document.querySelector('#day-card-fifth .day-card__weather').classList.add(animation)
document.querySelector('#day-card-sixth img').src = getImgByCondition(weather.weather[5].condition)
document.querySelector('#day-card-sixth .day-card__weather').classList.add(animation)
document.querySelector('#day-card-seventh img').src = getImgByCondition(weather.weather[6].condition)
document.querySelector('#day-card-seventh .day-card__weather').classList.add(animation)

// Создание событий при нажатии на карточки
const dayCards = document.getElementsByClassName("day-card"); // Потом отсюда можно в отдельные переменные карточки забрать, чтобы красиво было
for (let i = 0; i < dayCards.length; i++) {
    dayCards[i].addEventListener("click", function () {
        var id = dayCards[i].additionalParameter;
        mainWeatherIcon.src = getImgByCondition(weather.weather[id].condition);
        mainTemperature.textContent = weather.weather[id].celsius + '℃';
        mainDate.textContent = formatDate(weather.weather[id].date);
        mainWindSpeed.textContent = ' ' + weather.weather[id].wind_speed + 'м/c';
        mainHumidity.textContent = weather.weather[id].humidity + '%';
  });
}

function getImgByCondition(condition) { // Возвращает картинку и анимацию в зависимости от погодного состояния
    if (condition == 'clear' || condition == 'partly-cloudy') {
        animation = 'rotate'
        return 'images/weather_icons/sun_icon.png';
    }
    if (condition == 'cloudy' || condition == 'overcast') {
        animation = 'translate'
        return 'images/weather_icons/cloudy_sun_icon.png';
    }
    if (condition == 'hail' || condition == 'wet-snow') {
        animation = 'scale'
        return 'images/weather_icons/hail_icon.png';
    }
    if (condition == 'thunderstorm-with-hail' || condition == 'thunderstorm-with-rain') {
        animation = 'scale'
        return 'images/weather_icons/lightning_rain_icon.png';
    }
    if (condition == 'thunderstorm') {
        animation = 'scale'
        return 'images/weather_icons/lightning-icon.png';
    }
    if (condition == 'snow-showers' || condition == 'snow' || condition == 'light-snow') {
        animation = 'translate'
        return 'images/weather_icons/snow_icon.png';
    } else {
        animation = 'translate'
        return 'images/weather_icons/cloudy_icon.png';
      }
}

function getDayOfWeek(dateString) { // Эта функция получает день недели из даты
    const date = new Date(dateString);
    const daysOfWeek = ["Вс", "Пн", "Вт", "Ср", "Чт", "Пн", "Сб"];
    const dayOfWeek = date.getDay();

    return daysOfWeek[dayOfWeek];
  }

function formatDate(dateString) { // Форматирует время
  var parts = dateString.split('-');
  var year = parts[0];
  var month = parts[1];
  var day = parts[2].split('T')[0]; // Remove time portion if present

  var formattedDate = day + '.' + month;
  return formattedDate;
}





/*var dateParts = weather.weather[0].date.split('-'); // Конвертируем дату
var transformedDate = dateParts[2] + '.' + dateParts[1];*/