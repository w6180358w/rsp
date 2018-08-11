create table t_category
(
	id bigint auto_increment
		primary key,
	name varchar(255) not null,
	type varchar(255) not null,
	constraint t_category_id_uindex
		unique (id)
)
;

create table t_formula
(
	id bigint auto_increment
		primary key,
	org_id bigint not null,
	sub_category_id bigint not null,
	formula varchar(255) not null,
	constraint t_formula_id_uindex
		unique (id)
)
;

create table t_org
(
	id bigint auto_increment
		primary key,
	name varchar(255) not null,
	`limit` bigint not null,
	term bigint not null,
	interest_rate varchar(255) null,
	requirements varchar(255) null,
	material varchar(255) null,
	logo varchar(255) null,
	`desc` varchar(255) null,
	contacts varchar(255) null,
	phone varchar(255) null,
	strengths varchar(255) null,
	constraint t_org_id_uindex
		unique (id)
)
;

create table t_statistics
(
	id bigint auto_increment
		primary key,
	org_id bigint not null,
	count_time timestamp not null,
	constraint t_statistics_id_uindex
		unique (id)
)
;

create table t_sub_category
(
	id bigint auto_increment
		primary key,
	name varchar(255) not null,
	category_id bigint not null,
	`param_key` varchar(255) not null,
	constraint t_sub_category_id_uindex
		unique (id),
	constraint t_sub_category_param_key_uindex
		unique (param_key)
)
;

