
DROP TABLE IF EXISTS ORDER_TABLE;
CREATE TABLE ORDER_TABLE (
  order_id INTEGER PRIMARY KEY AUTO_INCREMENT ,
  order_name VARCHAR(100) NOT NULL,
  city VARCHAR(100) NOT NULL,
  state VARCHAR(45) NOT NULL ,
  status VARCHAR(20) NOT NULL ,
  order_value DOUBLE NOT NULL
) ;