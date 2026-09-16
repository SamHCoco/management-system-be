#!/bin/bash

# Redis Configuration
export REDIS_PASSWORD=admin
export REDIS_NODE_1_HOST=localhost
export REDIS_NODE_1_PORT=6379
export REDIS_NODE_2_HOST=localhost
export REDIS_NODE_2_PORT=6380
export REDIS_NODE_3_HOST=localhost
export REDIS_NODE_3_PORT=6381
export REDIS_NODE_4_HOST=localhost
export REDIS_NODE_4_PORT=6382

# Database Configuration
export DB_URL=localhost
export DB_USER=root
export DB_PASSWORD=admin

# Keycloak Configuration
export KEYCLOAK_URL=http://localhost:8080
export KEYCLOAK_REALM=management_system
export KEYCLOAK_CLIENT=management_system
export KEYCLOAK_SECRET=kTbik8NhghmWlbkPFj60MJ79b0uq1IrZ

# Eureka Configuration
export EUREKA_URL=http://localhost

echo "All environment variables have been exported to the current shell session"
echo "To verify, run: env | grep -E '(REDIS|DB|KEYCLOAK|EUREKA)'"