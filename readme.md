# selenoid azk

Проверка работы Selenoid с Selenium 4 и Junit 5   
Тестируемый url   
http://172.24.25.205:7053/gz/   
(стенд на srv-doc-at-null)   

Использование:   

клонировать, ветка selenoid-azk   
git clone https://github.com/vlshestakov/exercise2.git   
cd exercise2   
git checkout selenoid-azk   
cd Ui_test   

запуск

(selenoidUrl по-умолчанию http://172.24.25.205:4444/wd/hub)   
./gradlew clean test   

Заданный selenoidUrl   
./gradlew -DselenoidUrl=http://test:123456@srv-at-selenoid-test6:8888/wd/hub clean test


