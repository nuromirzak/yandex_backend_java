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
    - docker run -p 8080:8080 yandex-spring-boot-docker.jar