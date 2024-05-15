공공공데이터를 활용한 주식정보 토론방을 만들어보았습니다(CUI방식)




mysql에서 데이터 베이스 생성

create database sqldb;

create database user;

create table user.usertbl(
			userid VARCHAR(100) PRIMARY key NOT NULL,
			name VARCHAR(100),
			password VARCHAR(100),
			email VARCHAR(100) ,
			phone VARCHAR(100) );
            
create table user.manageruserid(

userid VARCHAR(100) PRIMARY key NOT NULL,

password VARCHAR(100));


CREATE TABLE user.company (num INT PRIMARY key AUTO_INCREMENT NOT NULL,
				company VARCHAR(100),
				share VARCHAR(100),
				Evaluation VARCHAR(100), 
				rate VARCHAR(100));
                
                
CREATE TABLE user.post (num INT PRIMARY key AUTO_INCREMENT NOT NULL,
				id VARCHAR(100),
				tale VARCHAR(100)
				);
                
                
create user 'yun'@'localhost' identified by '1234';

grant insert on user.post to 'yun'@'localhost';

grant select on user.post to 'yun'@'localhost';

grant select on user.company to 'yun'@'localhost';


insert into user.manageruserid (userid,password) value ("kky","1234");

bulidpath 해야되는 내용
json-simple-1.1.1.jar, mysql-connector-j-8.3.0.jar
