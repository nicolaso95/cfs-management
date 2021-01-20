# cfs-management
1. Clone project: https://github.com/nicolaso95/cfs-management.git  <br />
2. Install mysql server: https://dev.mysql.com/downloads/mysql/ <br />
In mysql shell, execute commands to create database table and user <br />
CREATE DATABASE IF NOT EXISTS test; <br />
CREATE USER IF NOT EXISTS 'test'@'%' IDENTIFIED BY '123456'; <br />
GRANT ALL PRIVILEGES ON test.* TO 'test'@'%'; <br />
3. Install gradle: https://gradle.org/install/ <br />
Run project: gradle bootrun <br />
4. Test API <br />
Using swagger: http://127.0.0.1:8080/swagger-ui.html#/ <br />
Using postman: dispatch.postman_collection.json <br />
