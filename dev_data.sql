INSERT INTO `employee_department` (`name`, `created_at`, `last_modified_at`) VALUES
('Engineering', NOW(), NOW()),
('Sales', NOW(), NOW()),
('Human Resources', NOW(), NOW());

INSERT INTO `employee` (
    `first_name`,
    `middle_names`,
    `last_name`,
    `department_id`,
    `email`,
    `phone`,
    `address_first_line`,
    `address_second_line`,
    `address_city`,
    `address_post_code`,
    `created_at`,
    `last_modified_at`
) VALUES
(
    'James',
    'William',
    'Smith',
    1,
    'james.smith@company.co.uk',
    '+44-20-7946-0958',
    '123 Oxford Street',
    'Flat 4B',
    'London',
    'W1D 1NU',
    NOW(),
    NOW()
),
(
    'Emma',
    'Louise',
    'Johnson',
    2,
    'emma.johnson@company.co.uk',
    '+44-20-7946-0959',
    '456 Victoria Road',
    NULL,
    'Manchester',
    'M1 1AB',
    NOW(),
    NOW()
),
(
    'Mohammed',
    NULL,
    'Ahmed',
    3,
    'mohammed.ahmed@company.co.uk',
    '+44-20-7946-0960',
    '789 Kings Road',
    'Suite 5',
    'Birmingham',
    'B1 1BB',
    NOW(),
    NOW()
),
(
    'Sophie',
    'Grace',
    'Williams',
    1,
    'sophie.williams@company.co.uk',
    '+44-20-7946-0961',
    '321 Park Lane',
    'First Floor',
    'Leeds',
    'LS1 1AA',
    NOW(),
    NOW()
),
(
    'Thomas',
    'Robert',
    'Brown',
    2,
    'thomas.brown@company.co.uk',
    '+44-20-7946-0962',
    '654 High Street',
    NULL,
    'Liverpool',
    'L1 1AB',
    NOW(),
    NOW()
);

INSERT INTO product (name, price, deleted, created_at, last_modified_at)
VALUES
  ('Wireless Mouse', 24.99, false, NOW(), NOW()),
  ('Mechanical Keyboard', 129.50, false, NOW(), NOW()),
  ('27-inch Monitor', 299.99, false, NOW(), NOW()),
  ('USB-C Docking Station', 89.00, false, NOW(), NOW()),
  ('Noise Cancelling Headphones', 199.95, true, NOW(), NOW());

INSERT INTO product_inventory (
    product_id,
    stock,
    low_stock_threshold,
    low_stock_alerted,
    deleted,
    created_at,
    last_modified_at
)
VALUES
  (1, 25000, 5000, false, false, NOW(), NOW()),  -- Wireless Mouse
  (2, 18000, 3000, false, false, NOW(), NOW()),  -- Mechanical Keyboard
  (3, 12000, 2000, false, false, NOW(), NOW()),  -- 27-inch Monitor
  (4, 30000, 4000, false, false, NOW(), NOW()),  -- USB-C Docking Station
  (5, 15000, 2500, false, true,  NOW(), NOW());  -- Noise Cancelling Headphones

