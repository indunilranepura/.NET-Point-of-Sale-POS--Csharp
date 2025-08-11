-- Insert default admin user
INSERT INTO users (user_id, first_name, last_name, password, email, age, gender, role, salary, join_date, birth_date, nid, phone, home_town, current_city, division, blood_group, postal_code, is_active, created_at, updated_at)
VALUES 
('U-A-0001', 'Admin', 'User', '$2a$10$92IXUNpkjO0rOQ5byMi.Ye4oKoEa3Ro9llC/.og/at2uheWG/igi.', 'admin@pos.com', 30, 'MALE', 'ADMIN', 50000.0, '2024-01-01', '1994-01-01', '1234567890123', '+8801700000000', 'Dhaka', 'Dhaka', 'Dhaka', 'B+', 1000, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
('U-C-0001', 'Cashier', 'User', '$2a$10$92IXUNpkjO0rOQ5byMi.Ye4oKoEa3Ro9llC/.og/at2uheWG/igi.', 'cashier@pos.com', 25, 'FEMALE', 'CASHIER', 30000.0, '2024-01-01', '1999-01-01', '1234567890124', '+8801700000001', 'Chittagong', 'Chittagong', 'Chittagong', 'A+', 4000, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
('U-S-0001', 'Salesman', 'User', '$2a$10$92IXUNpkjO0rOQ5byMi.Ye4oKoEa3Ro9llC/.og/at2uheWG/igi.', 'salesman@pos.com', 28, 'MALE', 'SALESMAN', 25000.0, '2024-01-01', '1996-01-01', '1234567890125', '+8801700000002', 'Sylhet', 'Sylhet', 'Sylhet', 'O+', 3100, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

-- Insert default main categories
INSERT INTO main_categories (main_category_name, main_category_description, created_at, updated_at)
VALUES 
('Electronics', 'Electronic devices and accessories', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
('Clothing', 'Apparel and fashion items', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
('Food & Beverages', 'Food items and drinks', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
('Books', 'Books and educational materials', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
('Home & Garden', 'Home improvement and garden items', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

-- Insert default second categories
INSERT INTO second_categories (second_category_name, second_category_description, main_category_id, created_at, updated_at)
VALUES 
('Mobile Phones', 'Smartphones and accessories', 1, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
('Computers', 'Laptops, desktops, and accessories', 1, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
('Men''s Clothing', 'Clothing for men', 2, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
('Women''s Clothing', 'Clothing for women', 2, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
('Snacks', 'Snack foods and treats', 3, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

-- Insert default third categories
INSERT INTO third_categories (third_category_name, third_category_description, second_category_id, created_at, updated_at)
VALUES 
('Android Phones', 'Android smartphones', 1, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
('iPhone', 'Apple iPhones', 1, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
('Laptops', 'Portable computers', 2, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
('Shirts', 'Men''s shirts', 3, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
('Dresses', 'Women''s dresses', 4, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

-- Insert default vendors
INSERT INTO vendors (vendor_tag, vendor_name, vendor_description, vendor_status, register_date, third_category_id, created_at, updated_at)
VALUES 
('V-001', 'Samsung Electronics', 'Leading electronics manufacturer', 'Active', '2024-01-01', 1, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
('V-002', 'Apple Inc.', 'Technology company', 'Active', '2024-01-01', 2, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
('V-003', 'Dell Technologies', 'Computer technology company', 'Active', '2024-01-01', 3, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
('V-004', 'Nike', 'Sportswear manufacturer', 'Active', '2024-01-01', 4, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
('V-005', 'Zara', 'Fashion retailer', 'Active', '2024-01-01', 5, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

-- Insert default brands
INSERT INTO brands (brand_tag, brand_name, brand_description, brand_status, vendor_id, created_at, updated_at)
VALUES 
('B-001', 'Samsung Galaxy', 'Samsung smartphone series', 'Active', 1, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
('B-002', 'iPhone', 'Apple smartphone series', 'Active', 2, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
('B-003', 'Dell Inspiron', 'Dell laptop series', 'Active', 3, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
('B-004', 'Nike Air', 'Nike shoe series', 'Active', 4, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
('B-005', 'Zara Basic', 'Zara clothing line', 'Active', 5, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

-- Insert default bar codes
INSERT INTO bar_codes (bar_code, created_at, updated_at)
VALUES 
('1234567890123', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
('1234567890124', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
('1234567890125', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
('1234567890126', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
('1234567890127', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

-- Insert default products
INSERT INTO products (product_id_tag, product_name, product_description, product_quantity_per_unit, product_per_unit_price, product_msrp, product_status, product_discount_rate, product_size, product_color, product_weight, product_unit_stock, brand_id, created_at, updated_at)
VALUES 
('P-000001', 'Samsung Galaxy S24', 'Latest Samsung flagship smartphone', 1.0, 899.99, 999.99, 'Yes', 10.0, 6.2, 'Black', 0.168, 50, 1, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
('P-000002', 'iPhone 15 Pro', 'Latest Apple iPhone', 1.0, 1099.99, 1199.99, 'Yes', 8.0, 6.1, 'Blue', 0.187, 30, 2, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
('P-000003', 'Dell Inspiron 15', 'Dell laptop for everyday use', 1.0, 699.99, 799.99, 'Yes', 12.5, 15.6, 'Silver', 2.1, 25, 3, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
('P-000004', 'Nike Air Max', 'Comfortable running shoes', 1.0, 129.99, 149.99, 'Yes', 13.3, 10.0, 'White', 0.8, 100, 4, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
('P-000005', 'Zara Cotton Shirt', 'Premium cotton shirt', 1.0, 39.99, 49.99, 'Yes', 20.0, 0.0, 'Blue', 0.3, 75, 5, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

-- Insert default expenses
INSERT INTO expenses (expense_name, expense_amount, expense_date, created_at, updated_at)
VALUES 
('Office Rent', 2000.0, '2024-01-01', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
('Electricity Bill', 500.0, '2024-01-01', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
('Internet Bill', 100.0, '2024-01-01', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
('Office Supplies', 300.0, '2024-01-01', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
('Marketing', 1000.0, '2024-01-01', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);