# exercise2

Selenium 4, Selenoid, Junit 5

клонировать, ветка selenoid-azk
git clone https://github.com/vlshestakov/exercise2.git
cd exercise2
git checkout selenoid-azk
cd Ui_test

запуск

selenoidUrl по-умолчанию http://172.24.25.205:4444/wd/hub
./gradlew clean test   

Заданный selenoidUrl   
./gradlew -DselenoidUrl=http://test:123456@srv-at-selenoid-test6:8888/wd/hub clean test


