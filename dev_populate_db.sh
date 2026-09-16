#!/bin/bash

CONTAINER="cockroachdb-dev"
DB_NAME="management_system"

PRODUCT_EXISTS=$(docker exec -i "$CONTAINER" ./cockroach sql --insecure \
--host=localhost:26257 --database=$DB_NAME \
-e "SELECT COUNT(*) FROM product;" --format=csv | tail -n 1)

if [[ "$PRODUCT_EXISTS" -eq 0 ]]; then

echo "=== Departments ==="
docker exec -i "$CONTAINER" ./cockroach sql --insecure \
--host=localhost:26257 --database=$DB_NAME \
--format=tsv -e "
INSERT INTO employee_department (\"name\",\"created_at\",\"last_modified_at\",\"last_modified_by\",\"deleted\") VALUES
('Engineering', NOW(), NOW(), 'system', false),
('Sales', NOW(), NOW(), 'system', false),
('Human Resources', NOW(), NOW(), 'system', false)
RETURNING id, name;
" | column -t

echo ""
echo "=== Products ==="
docker exec -i "$CONTAINER" ./cockroach sql --insecure \
--host=localhost:26257 --database=$DB_NAME \
--format=tsv -e "
INSERT INTO product (\"name\",\"price\",\"created_at\",\"last_modified_at\",\"last_modified_by\",\"deleted\")
SELECT
  'Product-' || i,
  (10 + random()*90)::decimal(9,2),
  NOW(), NOW(), 'system', false
FROM generate_series(1,150) AS i
RETURNING id, name, '£' || price AS price;
" | column -t

echo ""
echo "=== Inventory ==="
docker exec -i "$CONTAINER" ./cockroach sql --insecure \
--host=localhost:26257 --database=$DB_NAME \
--format=tsv -e "
INSERT INTO product_inventory (\"product_id\",\"stock\",\"low_stock_threshold\",\"created_at\",\"last_modified_at\",\"last_modified_by\",\"deleted\")
SELECT
  id,
  (5 + floor(random()*45))::int,
  5,
  NOW(), NOW(), 'system', false
FROM product
RETURNING product_id, stock;
" | column -t

else
    echo "Products already exist. Skipping data population."
fi