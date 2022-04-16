CREATE TABLE `movement` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `amount` int(11) DEFAULT NULL,
  `created_at` datetime DEFAULT NULL,
  `exit_price` double NOT NULL,
  `price` double NOT NULL,
  `product_id` bigint(20) DEFAULT NULL,
  `status` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;