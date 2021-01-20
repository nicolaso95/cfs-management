# cfs-management
1. Clone project: https://github.com/nicolaso95/cfs-management.git 
2. Install mysql server: https://dev.mysql.com/downloads/mysql/ 
In mysql shell, execute commands to create database table and user 
CREATE DATABASE IF NOT EXISTS test; 
CREATE USER IF NOT EXISTS 'test'@'%' IDENTIFIED BY '123456'; 
GRANT ALL PRIVILEGES ON test.* TO 'test'@'%'; 
3. Install gradle: https://gradle.org/install/ 
Run project: gradle bootrun 
4. Test API 
Using swagger: http://127.0.0.1:8080/swagger-ui.html#/ 
Using postman 
