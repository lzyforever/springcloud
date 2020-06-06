CREATE DATABASE `jack_ds_0` CHARACTER SET 'utf8' COLLATE 'utf8_general_ci';
CREATE DATABASE `jack_ds_1` CHARACTER SET 'utf8' COLLATE 'utf8_general_ci';
CREATE DATABASE `jack_ds_0_slave` CHARACTER SET 'utf8' COLLATE 'utf8_general_ci';
CREATE DATABASE `jack_ds_1_slave` CHARACTER SET 'utf8' COLLATE 'utf8_general_ci';

use jack_ds_0;
CREATE TABLE `user`(
	id bigint(64) not null,
	city varchar(20) not null,
	name varchar(20) not null,
	PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

use jack_ds_0_slave;
CREATE TABLE `user`(
	id bigint(64) not null,
	city varchar(20) not null,
	name varchar(20) not null,
	PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

use jack_ds_1;
CREATE TABLE `loudong` (
  `id` varchar(20) NOT NULL,
  `city` varchar(20) NOT NULL,
  `region` varchar(20) NOT NULL,
  `name` varchar(20) NOT NULL,
  `ld_num` varchar(10) NOT NULL,
  `unit_num` varchar(10) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

use jack_ds_1_slave;
CREATE TABLE `loudong` (
  `id` varchar(20) NOT NULL,
  `city` varchar(20) NOT NULL,
  `region` varchar(20) NOT NULL,
  `name` varchar(20) NOT NULL,
  `ld_num` varchar(10) NOT NULL,
  `unit_num` varchar(10) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;