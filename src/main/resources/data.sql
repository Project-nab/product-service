-- Insert some data to brand table
insert into brand(brand_code, address, name) values('ADIDAS', 'GERMANY', 'ADIDAS');
-- insert some data to product catalogue table
insert into product_catalogue(catalogue_code, catalogue_name, catalogue_type) values('ADIDAS_01', 'Fashion', 0);
-- insert some data to product table
insert into product(product_code, color, price, product_name, quantity, brand_code, product_catalogue_code) values
('ADIDAS_TSHIRT_01', 0, 50, 'T-shirt', 100, 'ADIDAS', 'ADIDAS_01');