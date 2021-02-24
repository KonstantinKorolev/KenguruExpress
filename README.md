# KenguruExpress
My first mobile app on Kotlin
Структура проекта:

1. Adapters – адаптеры AddressAdapter, TarrifsAdapter, используются для вывода данных в activity
2. api – работа c API 2.1 AddressBookApi - Аутентификации пользователя и получения его токена 2.2 GeopgraphyApi – Получение данных о городе, улице, доме 
   2.3 ProductApi – Создание груза/продукта 2.4 UserApi - Создание пользователя, переотправка письма на почту, логин, активация почты, регистрация телефона, активация телефона
3. db – классы связанные с созданием базы данных пользователей 
4. fragments - фрагменты (PurseFragment - расчёт стоимости, Dispatch - данные о грузах, UsersLkFragment - личный кабинет)
5. models - классы - структуры для получения отправки/ответа сервера (contacts - получение информации о пользователе, 
   departure - создание груза, email_activation - активация почты, locality - получения данных о городе, доме, улице, 
   login - вход пользователя, phone_activation - активация телефона, phone_reqistration - подтверждение телефона, products - создание продукта, resend_email - переотправка письма на почту, 
   users - регистрация пользователя, usersMe - изменение информации о пользователе, websocket - ответ от веб-сокеты
Вне папок - AddOrChangeAddressActivity - изменение данных о пользователе, AddressActivityShow - отображение адресов, AuthInterceptor - добавление токена,
MainActiviy - Основное окно переключается на pursefragment, LoginActivity - окно входа, RegisterActivity - окно регистрации,
SettingsActivity - окно смены темы приложения, RetrofitClient - описание ретрофит клиента, 
SessionManager - Диспетчер сеансов для сохранения и извлечения данных из SharedPreferences, ShowTarrifsActivity - окно показа тарифов,
SplashActivity - окно запуска приложения
