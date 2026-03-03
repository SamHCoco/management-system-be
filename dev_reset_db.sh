#!/bin/bash

CONTAINER="sandbox-mysql"
DB_NAME="management_system"
MYSQL_USER="root"
MYSQL_PASSWORD="admin"

if docker exec -i "$CONTAINER" mysql -u "$MYSQL_USER" -p"$MYSQL_PASSWORD" -e "DROP DATABASE IF EXISTS $DB_NAME; CREATE DATABASE $DB_NAME;"; then
    echo "Database '$DB_NAME' successfully recreated."
else
    echo "Failed to reset database '$DB_NAME'." >&2
    exit 1
fi