create table product (
	id bigint not null auto_increment,
	name varchar(255),
	unity varchar(255),
	category_id bigint,
	primary key (id)
) engine = InnoDB