create table stock (
  product_id bigint,
  exit_price double precision,
  price double precision,
  stock_quantity integer,
  primary key (product_id)
) engine = InnoDB