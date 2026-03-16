#!/bin/bash
echo "Iniciando todos os serviços do Fileh Systems V2..."

cd "backend/eureka-server"
./mvnw spring-boot:run > eureka.log 2>&1 &
EUREKA_PID=$!
echo "Eureka Server iniciado (PID $EUREKA_PID). Aguardando 15s..."
sleep 15

cd "../auth-service"
./mvnw spring-boot:run > auth.log 2>&1 &
AUTH_PID=$!
echo "Auth Service iniciado (PID $AUTH_PID). Aguardando 5s..."
sleep 5

cd "../crm-service"
./mvnw spring-boot:run > crm.log 2>&1 &
CRM_PID=$!
echo "CRM Service iniciado (PID $CRM_PID). Aguardando 5s..."
sleep 5

cd "../commercial-service"
./mvnw spring-boot:run > commercial.log 2>&1 &
COMM_PID=$!
echo "Commercial Service iniciado (PID $COMM_PID). Aguardando 5s..."
sleep 5

cd "../financial-service"
./mvnw spring-boot:run > financial.log 2>&1 &
FIN_PID=$!
echo "Financial Service iniciado (PID $FIN_PID). Aguardando 5s..."
sleep 5

cd "../api-gateway"
./mvnw spring-boot:run > gateway.log 2>&1 &
GW_PID=$!
echo "API Gateway iniciado (PID $GW_PID). Aguardando 5s..."
sleep 5

cd "../../frontend"
npm run start > frontend.log 2>&1 &
FRONT_PID=$!
echo "Angular Frontend iniciado (PID $FRONT_PID)."

echo "--------------------------------------------------------"
echo "Todos os módulos foram iniciados em background (segundo plano)."
echo "Você pode checar os logs lendo os arquivos:"
echo "  v2/backend/eureka-server/eureka.log"
echo "  v2/backend/auth-service/auth.log"
echo "  v2/backend/crm-service/crm.log"
echo "  v2/backend/commercial-service/commercial.log"
echo "  v2/backend/financial-service/financial.log"
echo "  v2/backend/api-gateway/gateway.log"
echo "  v2/frontend/frontend.log"
echo "Para matar os processos depois, use 'pkill -f spring-boot' e 'pkill -f ng'"
echo "--------------------------------------------------------"
