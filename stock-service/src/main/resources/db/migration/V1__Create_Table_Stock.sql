CREATE TABLE `stock` (
  `product_id` bigint(20) NOT NULL,
  `exit_price` double NOT NULL,
  `price` double NOT NULL,
  `stock_quantity` int(11) NOT NULL,
  PRIMARY KEY (`product_id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;