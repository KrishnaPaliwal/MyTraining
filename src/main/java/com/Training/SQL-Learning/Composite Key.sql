CREATE TABLE Product (Prod_ID int NOT NULL, Name varchar(45), Manufacturer varchar(45),  
PRIMARY KEY(Name, Manufacturer)); 

DESCRIBE PRODUCT;
INSERT INTO Product (Prod_ID, Name, Manufacturer)  VALUES (101, 'Soap', 'Hamam'),  (102, 'Shampoo', 'Teresme'),  (103, 'Oil', 'Daber Almond');  
SELECT * FROM PRODUCT ;
-- will throw Error Code: 1062. Duplicate entry 'Soap-Hamam' for key 'product.PRIMARY'
INSERT INTO Product (Prod_ID, Name, Manufacturer)  VALUES (101, 'Soap', 'Hamam');  
INSERT INTO Product (Prod_ID, Name, Manufacturer)  VALUES (101, 'Soap', 'LUX'); 
