#!/bin/bash

CONTAINER="sandbox-mysql"
DB_NAME="management_system"
MYSQL_USER="root"
MYSQL_PASSWORD="admin"

SQL_INSERT="
USE $DB_NAME;

INSERT INTO employee_department (name, created_at, last_modified_at) VALUES
('Engineering', NOW(), NOW()),
('Sales', NOW(), NOW()),
('Human Resources', NOW(), NOW());
"

if docker exec -i "$CONTAINER" mysql -u "$MYSQL_USER" -p"$MYSQL_PASSWORD" -e "$SQL_INSERT"; then
    echo "Test data inserted into 'employee_department'."
else
    echo "Failed to insert test data into '$DB_NAME'." >&2
    exit 1
fi