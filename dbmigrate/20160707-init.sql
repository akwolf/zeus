create table article_info (
	id bigint(20) primary key auto_increment,
	slug varchar(20),
	title varchar(500),
	description varchar(1000),
	content text,
	create_time datetime,
	last_modify_time datetime,
	disabled tinyint(4) default 0,
	deleted tinyint(4) default 0
);


create table account_info (
	id bigint(20) primary key auto_increment,
	slug varchar(20),
	username varchar(200),
	passwd varchar(500),
	gender tinyint(4) default 1,
	mobile varchar(100),
	real_name varchar(100),
	brief varchar(500),
	avatar_img varchar(500),
	open_id varchar(500),
	country varchar(200),
	province varchar(200),
	city varchar(200),
	language varchar(200),
	create_time datetime,
	subscribe_time datetime
);


create table banner_info (
	id bigint(20) primary key auto_increment,
	cover_img varchar(200),
	sequence tinyint(4) default 0,
	deleted tinyint(4) default 0,
	disabled tinyint(4) default 0,
	recommend_url varchar(500),
	create_time datetime
);

create table company_info (
	id bigint(20) primary key auto_increment,
	title varchar(500),
	address varchar(500),
	mobile varchar(200),
	telphone varchar(200),
	brief varchar(500),
	description text,
	logo_img varchar(500),
	contact varchar(200)
);

create table product_info (
	id bigint(20) primary key auto_increment,
	slug varchar(20),
	name varchar(500),
	brief varchar(500),
	description text,
	amount bigint(20) default 0,
	create_time datetime,
	last_modify_time datetime
);


