#!/usr/bin/env bash
set -euo pipefail

# ==============================
# CONFIGURATION
# ==============================
PASSWORD="changeit"
DAYS=3650

CA_SUBJ="/CN=internal-ca"
GATEWAY_SUBJ="/CN=management-system-gateway"
EMPLOYEE_SUBJ="/CN=employee-service"
PRODUCT_SUBJ="/CN=product-service"

CERTS_DIR="$(cd "$(dirname "$0")" && pwd)/certs"

echo "Using certs directory: $CERTS_DIR"
mkdir -p "$CERTS_DIR"
cd "$CERTS_DIR"

# ==============================
# 1. Internal CA
# ==============================
echo "=== Generating CA key and certificate ==="
openssl genrsa -out ca.key 4096

openssl req -x509 -new -nodes \
  -key ca.key \
  -subj "$CA_SUBJ" \
  -days "$DAYS" \
  -out ca.crt

# ==============================
# 2. Gateway certificate (client cert for mTLS to services)
# ==============================
echo "=== Generating Gateway key and certificate ==="
openssl genrsa -out gateway.key 4096

openssl req -new \
  -key gateway.key \
  -subj "$GATEWAY_SUBJ" \
  -out gateway.csr

openssl x509 -req \
  -in gateway.csr \
  -CA ca.crt -CAkey ca.key -CAcreateserial \
  -days "$DAYS" \
  -out gateway.crt

echo "=== Creating Gateway PKCS12 keystore ==="
openssl pkcs12 -export \
  -in gateway.crt \
  -inkey gateway.key \
  -name "gateway-client" \
  -out gateway-keystore.p12 \
  -passout pass:"$PASSWORD"

# ==============================
# 3. Employee service certificate (server cert)
# ==============================
echo "=== Generating Employee Service key and certificate ==="
openssl genrsa -out employee.key 4096

openssl req -new \
  -key employee.key \Where
  -subj "$EMPLOYEE_SUBJ" \
  -out employee.csr

openssl x509 -req \
  -in employee.csr \
  -CA ca.crt -CAkey ca.key -CAcreateserial \
  -days "$DAYS" \
  -out employee.crt

echo "=== Creating Employee Service PKCS12 keystore ==="
openssl pkcs12 -export \
  -in employee.crt \
  -inkey employee.key \
  -name "employee-server" \
  -out employee-keystore.p12 \
  -passout pass:"$PASSWORD"

# ==============================
# 4. Product service certificate (server cert) - optional but included
# ==============================
echo "=== Generating Product Service key and certificate ==="
openssl genrsa -out product.key 4096

openssl req -new \
  -key product.key \
  -subj "$PRODUCT_SUBJ" \
  -out product.csr

openssl x509 -req \
  -in product.csr \
  -CA ca.crt -CAkey ca.key -CAcreateserial \
  -days "$DAYS" \
  -out product.crt

echo "=== Creating Product Service PKCS12 keystore ==="
openssl pkcs12 -export \
  -in product.crt \
  -inkey product.key \
  -name "product-server" \
  -out product-keystore.p12 \
  -passout pass:"$PASSWORD"

# ==============================
# 5. Truststores (JKS) for services and gateway
#    Both contain the same CA cert.
# ==============================
echo "=== Creating services truststore (trusts CA) ==="
keytool -importcert \
  -alias internal-ca \
  -file ca.crt \
  -keystore services-truststore.jks \
  -storepass "$PASSWORD" \
  -noprompt

echo "=== Creating gateway truststore (trusts CA) ==="
keytool -importcert \
  -alias internal-ca \
  -file ca.crt \
  -keystore gateway-truststore.jks \
  -storepass "$PASSWORD" \
  -noprompt

echo
echo "======================================="
echo "mTLS material generated in: $CERTS_DIR"
echo
echo "Files you will typically use:"
echo "  - Gateway:"
echo "      gateway-keystore.p12"
echo "      gateway-truststore.jks"
echo "  - Employee service:"
echo "      employee-keystore.p12"
echo "      services-truststore.jks"
echo "  - Product service:"
echo "      product-keystore.p12"
echo "      services-truststore.jks"
echo "  - Shared CA (for debugging / curl):"
echo "      ca.crt"
echo "======================================="
