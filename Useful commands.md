# Useful commands

- Create a user in MySQL:
    - CREATE USER 'bestuser'@'localhost' IDENTIFIED BY 'bestuser';
    - GRANT ALL PRIVILEGES ON *.* TO 'bestuser'@'localhost';
- Use openvpn to connect to the server:
    - sudo openvpn --config example.ovpn
- Use ssh to connect to the server:
    - ssh ubuntu@10.21.2.45
- Copy local files to the server:
    - scp file1 file2 file3 ubuntu@10.21.2.45:/userfiles/
- Install MySQL on the server:
    - sudo apt-get install mysql-server
    - sudo mysql_secure_installation
    - sudo service mysql restart
    - sudo mysql -u root -p
    - CREATE DATABASE my_db;
- Deploy JAR file on the server:
    - nohup java -jar example.jar --spring.config.location=file:application.properties >dev/null 2>&1
- Build the JAR file:
    - mvn clean package
- Build the Docker image:
    - docker build -t yandex-spring-boot-docker.jar .
    - nohup docker run -p 80:80 -d yandex-spring-boot-docker.jar --restart=always
- Install Java 17 on the server:
    - sudo apt-get install openjdk-17-jre
    - sudo apt-get install openjdk-17-jdk
- Build the Docker mysql image:
    - docker build -t mysql-docker .
    - docker run -p 3306:3306 -e MYSQL_ROOT_PASSWORD=root -e MYSQL_DATABASE=my_db -e MYSQL_USER=bestuser -e
      MYSQL_PASSWORD=bestuser mysql-docker
- Docker auto start:
    - sudo systemctl enable docker
    - sudo systemctl enable docker.service
    - sudo systemctl start docker
    - docker ps -a
- MySQL Image run
    - docker run --name mysql-standalone --network springmysql-net -e MYSQL_ROOT_PASSWORD=password -e MYSQL_DATABASE=my_db -e MYSQL_USER=bestuser -e MYSQL_PASSWORD=bestuser -d mysql:5.7
    - docker logs -f mysql-standalone
    - docker exec -it <container_id> bash
    - mysql -u <username> -p
    - docker run --network springmysql-net --name backend-container -p 80:80 -d yandex-spring-boot-docker.jar
- Create tables
- CREATE TABLE `items`
  (
  `id`       VARCHAR(36) NOT NULL,
  `date`     DATETIME DEFAULT NULL,
  `name`     VARCHAR(45) DEFAULT NULL,
  `parentid` VARCHAR(36) DEFAULT NULL,
  `price`    INT DEFAULT NULL,
  `quantity` INT DEFAULT NULL,
  `sum`      INT DEFAULT NULL,
  `type`     VARCHAR(15) DEFAULT NULL,
  PRIMARY KEY (`id`)
  )
  engine=innodb
  DEFAULT charset=utf8
- CREATE TABLE `items_history`
  (
  `revnum`   INT NOT NULL auto_increment,
  `id`       VARCHAR(36) DEFAULT NULL,
  `date`     DATETIME DEFAULT NULL,
  `parentid` VARCHAR(36) DEFAULT NULL,
  `price`    INT DEFAULT NULL,
  `quantity` INT DEFAULT NULL,
  `sum`      INT DEFAULT NULL,
  `type`     VARCHAR(15) DEFAULT NULL,
  PRIMARY KEY (`revnum`)
  )
  engine=innodb
  DEFAULT charset=utf8