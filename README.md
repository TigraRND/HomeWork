# Обязательные параметры запуска

Для запуска проекта требуются данные авторизации, которые передаются как аргументы командной строки.

mvn clean test -Dlogin="authorization@mail.ru" -Dpassword="46815pass"
На всякий случай рекомендуется экранировать значения в кавычки.

# Необязательный параметр
Есть необязательный параметр -Dbrowser, позволяющий запустить тест в одном из 4-х браузеров.

mvn clean test -Dlogin="authorization@mail.ru" -Dpassword="46815pass" -Dbrowser=fireFOX

Параметр не чувствителен к регистру. Принимаемые значения: CHROME, FIREFOX, OPERA, IE. Если параметр не передан или передан некорректно, то по умолчанию тест будет запущен в Chrome с окном на весь экран.

#Настройка окружения:
Из-за сложной фронтовой логики по добавлению контактов, тест проходит, если на странице не добавлены контакты, кроме почты и телефона.
Тест добавляет два дополнительных контакта, при повторном запуске эти контакты перезаписываются.
