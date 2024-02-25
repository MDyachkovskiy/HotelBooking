<h1 align="center" id="title">Гостиничное приложение</h1>

<p id="description">
Приложение для бронирования номеров в гостинице, разработанное в рамках технического задания для собеседования. Приложение облегчает процесс бронирования номеров в определенном отеле, демонстрируя основные функциональные возможности, типичные для онлайн-систем бронирования.
</p>
<h2>Скриншоты экранов</h2>

<img src="https://gdurl.com/0c-g" alt="project-screenshot" width="200" height="400/"> <img src="https://gdurl.com/KkP8" alt="project-screenshot" width="200" height="400/"> <img src="https://gdurl.com/eHLm" alt="project-screenshot" width="200" height="400/">
<img src="https://gdurl.com/7TwZ" alt="project-screenshot" width="200" height="400/"> <img src="https://gdurl.com/a008" alt="project-screenshot" width="200" height="400/">

<h2>Экран Отеля</h2> 

<table>
  <tr>
    <td width="400" height="650">
      <img src="https://gdurl.com/TNTj" width="250" height="520" />
    </td>
    <td width="800">
      <p>
        Начальный экран отображает выбранный отель, предлагая карусель фотографий отеля. Пользователи могут перейти на экран выбора номера с помощью специальной кнопки.
      </p>
    </td>
  </tr>
</table>
<h2>Экран Выбора Номера</h2> 
<table>
  <tr>
    <td width="400" height="650">
      <img src="https://gdurl.com/ytiZ" width="250" height="520" />
    </td>
    <td width="800">
      <p>
        Доступен с экрана Отеля, этот экран перечисляет доступные номера. Каждая запись о номере включает в себя основную информацию, но опция "Больше Деталей" не функциональна. Выбор             номера ведет к экрану Бронирования.
      </p>
    </td>
  </tr>
</table>
  
<h2>Экран Бронирования</h2> 

<table>
  <tr>
    <td width="400" height="650">
      <img src="https://gdurl.com/EAFd" width="250" height="520" />
      <img src="https://gdurl.com/taa8" width="250" height="520" />
    </td>
    <td width="800">
      <p> На этот экран попадают после выбора номера, он включает:</p>
      <p><ul>
      <li>Детали бронирования: Города отправления и прибытия, даты бронирования, количество ночей, названия отеля и номера, тип питания (данные получены через API).</li>
      <li>Информация о клиенте: Поля для номера телефона (с автоматической маской для форматирования) и электронной почты (со стандартной валидацией).</li>
      <li>Информация о ценообразовании: Отображает цену тура, топливный сбор, сервисный сбор и общую стоимость, рассчитанные из данных API.</li>
      </ul>
        </p>
    </td>
  </tr>
</table>
<table>
  <tr>
    <td width="400" height="650">
      <img src="https://gdurl.com/5ueW" width="250" height="520" />
    </td>
    <td width="800">
      <p>Информация о туристе: Изначально показывает вкладку для первого туриста с опцией добавления дополнительных туристов. Каждое добавление создает новую вкладку, как "Второй                   Турист", "Третий Турист" и т.д. Эти вкладки могут быть развернуты или свернуты. </p>   
    </td>
  </tr>
</table>

<h2>Структура архитектуры</h2>
<img src="https://gdurl.com/KntY" alt="project-screenshot" >
<p>
Архитектура приложения разделена на модули для обеспечения чистоты кода и удобства разработки: <strong>сommon</strong> содержит общие утилиты и ресурсы; <strong>core</strong> включает доменные классы и репозитории, формирующие бизнес-логику; <strong>remote_data</strong> отвечает за сетевое взаимодействие и получение данных; каждый экран или функциональный блок выделен в отдельные <strong>feature</strong> модули, содержащий все необходимые компоненты для его реализации. Эта структура способствует модульности, упрощает тестирование и поддержку кода, а также облегчает масштабирование приложения.
</p>

<h2>💻 Используемый стек технологий:</h2>

*   Retrofit
*   Koin
*   Coroutines
*   Flow
*   Архитектура: MVVM
*   Многомодульность
*   ViewPager2
*   CircleIndicator3
