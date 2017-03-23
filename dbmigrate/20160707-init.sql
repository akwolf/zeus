create table article_info (
	id bigint(20) primary key auto_increment,
	slug varchar(100),
	title varchar(500),
	description varchar(1000),
	cover_img varchar(200),
	content text,
	create_time datetime,
	last_modify_time datetime,
	published tinyint(4) default 0,
	deleted tinyint(4) default 0,
	sequence tinyint(4) default 0
) DEFAULT CHARACTER SET=utf8 ;


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
) DEFAULT CHARACTER SET=utf8;

create table account_permission (
	id bigint(20) primary key auto_increment,
	account_id bigint(20),
	permission_id bigint(20),
	disabled tinyint(4) default 0,
	create_time datetime,
	deleted tinyint(4) default 0,
	last_modify_time datetime
) DEFAULT CHARACTER SET=utf8;

create table role_permission (
	id bigint(20) primary key auto_increment,
	role_id bigint(20),
	permission_id bigint(20),
	disabled tinyint(4) default 0,
	create_time datetime,
	deleted tinyint(4) default 0,
	last_modify_time datetime
) DEFAULT CHARACTER SET=utf8;

create table permission_info (
	id bigint(20) primary key auto_increment,
	permission varchar(200),
	create_time datetime
) DEFAULT CHARACTER SET=utf8;

create table account_role (
	id bigint(20) primary key auto_increment,
	account_id bigint(20),
	role_id bigint(20),
	disabled tinyint(4) default 0,
	create_time datetime,
	deleted tinyint(4) default 0,
	last_modify_time datetime
) DEFAULT CHARACTER SET=utf8;

create table role_info (
	id bigint(20) primary key auto_increment,
	role varchar(200),
	create_time datetime
) DEFAULT CHARACTER SET=utf8;


create table banner_info (
	id bigint(20) primary key auto_increment,
	cover_img varchar(200),
	sequence tinyint(4) default 0,
	deleted tinyint(4) default 0,
	disabled tinyint(4) default 0,
	recommend_url varchar(500),
	create_time datetime
) DEFAULT CHARACTER SET=utf8;

create table company_info (
	id bigint(20) primary key auto_increment,
	title varchar(500),
	address varchar(500),
	mobile varchar(200),
	telphone varchar(200),
	brief varchar(500),
	description text,
	logo_img varchar(500),
	contact varchar(200),
	create_time datetime,
	last_modify_time datetime
) DEFAULT CHARACTER SET=utf8;

create table product_info (
	id bigint(20) primary key auto_increment,
	slug varchar(20),
	name varchar(500),
	brief varchar(500),
	description longtext,
	cover_img varchar(200),
	amount bigint(20) default 0,
	deleted tinyint(4) default 0,
	published tinyint(4) default 0,
	sequence tinyint(4) default 0,
	create_time datetime,
	last_modify_time datetime
) DEFAULT CHARACTER SET=utf8;

create table lesson_video_info (
	id bigint(20) primary key auto_increment,
	fkey varchar(200),
	original_file_name varchar(500),
	hash varchar(200),
	duration bigint(20) default 0,
	size bigint(20) default 0,
	status tinyint(4) default 0,
	cover_img varchar(255),
	m3u8_key varchar(200),
	mobile_m3u8_key varchar(200),
	create_time datetime,
	last_modify_time datetime
) DEFAULT CHARACTER SET=utf8;

create table solution_info (
	id bigint(20) primary key auto_increment,
	slug varchar(20),
	title varchar(255),
	description longtext,
	cover_img varchar(200),
	content longtext,
	technology longtext,
	deleted tinyint(4) default 0,
	published tinyint(4) default 0,
	sequence tinyint(4) default 0,
	create_time datetime,
	last_modify_time datetime
) DEFAULT CHARACTER SET=utf8;

create table slug_info (
	id bigint(20) primary key auto_increment,
	slug varchar(20)
) DEFAULT CHARACTER SET=utf8;

create table payment_info (
	id bigint(20) primary key auto_increment,
	account_id bigint(20),
	out_trade_no varchar(255),
	status int default 0,
	total_fee double default 0,
	create_time datetime,
	last_modify_time datetime
) DEFAULT CHARACTER SET=utf8;

