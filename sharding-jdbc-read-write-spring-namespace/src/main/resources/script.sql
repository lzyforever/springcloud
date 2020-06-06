CREATE DATABASE `jack_ds_0` CHARACTER SET 'utf8' COLLATE 'utf8_general_ci';
CREATE DATABASE `jack_ds_1` CHARACTER SET 'utf8' COLLATE 'utf8_general_ci';

use jack_ds_0;
CREATE TABLE `user`(
	id bigint(64) not null,
	city varchar(20) not null,
	name varchar(20) not null,
	PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

use jack_ds_1;
CREATE TABLE `user`(
	id bigint(64) not null,
	city varchar(20) not null,
	name varchar(20) not null,
	PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;


INSERT INTO jack_ds_0.user VALUES (1001, '成都', 'jackluo');
INSERT INTO jack_ds_1.user VALUES (1002, '北京', 'lzyforever');
