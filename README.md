# t1-spring-study-project

command line work from postgres 
https://www.microfocus.com/documentation/idol/IDOL_12_0/MediaServer/Guides/html/English/Content/Getting_Started/Configure/_TRN_Set_up_PostgreSQL.htm

todo list: 
1. Доделать фукнциональность по сбору статистики
2. Написание тестов (unit и integration)
3. Заполнение spring securtity http config-а (разобраться подробнее)
4. Докер (а затем и docker compose)
5. Подтянуть swagger
6. Провести рефактор кода (потом)
7. soft delete сделать
8. подправить операцию добавления приемов - добвлять создаваемый прием в множества приемов врача и пациента


<h1> Запуск приложения при помощи docker network </h1> 
1. Создаем docker network: 
        '''
        docker network create t1-application
        '''
2. Запускаем контейнер postgres:
        docker run -d --network t1-application \
            --network-alias postgres \
            -e POSTGRES_USER=postgres \
            -e POSTGRES_PASSWORD=12345678 \
            postgres:13.1-alpine
3. Подключаемся к контейнеру с базой при помощи docker exec -id $(id контейнера или имя) bash.
Затем необходимо создать там базу study_spring_database.
4. Запускаем контейнейр nicolaka/netshoot в этой сети для организации взаимодействия между контейнерами:
docker run -it --network t1-application nicolaka/netshoot. Убеждаемся, что в сети виден контейнер с базой при помощи команды: dig postgres. 
5. Запускаем наше приложение при помощи команды: 
docker run -dp 8080:8080 --network t1-application -e SPRING_DATASOURCE_URL=jdbc:postgresql://postgres:5432/study_spring_database -e SPRING_DATASOURCE_USERNAME=postgres -e SPRING_DATASOURCE_PASSWORD=12345678 t1-app:latest
//docker network t1-application

<h1> запуск приложения через docker-compose</h1>
docker-compose up

docker run -d -p 5432:5432 -e POSTGRES_USER=postgres -e POSTGRES_PASSWORD=12345678 -e POSTGRES_DB=study_spring_database postgres:13.1-alpine