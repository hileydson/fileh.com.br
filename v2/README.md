# O que foi desenvolvido

# 1. Backend (Microsserviços)
    API Gateway (api-gateway): Funciona como o único ponto de entrada para o Frontend, roteando as chamadas para os microsserviços correspondentes (usa o Spring Cloud Gateway e o Eureka para registro visual).
    Service Registry (eureka-server): Permite escalabilidade horizontal descobrindo todos os serviços da rede.
    Auth & Identity Service (auth-service):
    Migração de 
    Usuario.hbm.xml
    e 
    SubUsuario.hbm.xml
    para JPA Entities (Hibernate via Spring Data).
    Implementação de Segurança usando JWT (JSON Web Tokens) com Spring Security. A senha é controlada por hash e há validações de controle de acesso (Roles/TenantID).
    Possui os endpoints como /api/auth/signin para gerar os Tokens JWT.
    CRM Service (crm-service):
    Centraliza o controle de 
    Cliente
    , 
    Fornecedor
    e 
    Entidade
    associados ao Tenant (ID do Usuário).
    Fornece CRUD nativo usando Spring RestController.
    Financial & Commercial Services: Estrutura base criada nos módulos caso deseje expandir futuramente de forma paralela ao CRM.

# 2. Frontend (Angular 19 Workspace)
    Setup Moderno: Projeto reconstruído do zero usando Angular e TailwindCSS buscando uma interface limpa, com transparências (glassmorphism) e responsiva.
    Segurança (Guards & Interceptors):
    authGuard
    : Impede que usuários não autenticados acessem a área logada (/clientes, etc.).
    authInterceptor
    : Adiciona automaticamente o cabeçalho Authorization: Bearer <token> em todas as requisições HttpClient.
    Design de Interface (UI):
    Login (app-login): Interface limpa com design glassmorphic e botões responsivos.
    Layout (app-layout): Painel com Sidebar de navegação fixa que engloba as páginas da área logada, aplicando UX moderna na experiência do usuário.
    Clientes (app-cliente-list): Tabela contendo lista de clientes do Tenant atual com um visual elegante, além de um "Modal" fade-in para criação e edição, integrados diretamente ao crm-service via API Gateway.
    Próximos Passos (Para Testar)
    Para ver a aplicação rodando, teremos que iniciar o pool de microsserviços e o Angular em paralelo. Você precisaria rodar:

    Levantar um banco de dados MySQL local ou usar Docker (na porta 3306).
    Executar em ordem:
    O Eureka Server (v2/backend/eureka-server)
    O Auth Service (v2/backend/auth-service)
    O CRM Service (v2/backend/crm-service)
    O API Gateway (v2/backend/api-gateway)
    Executar o frontend via npm (dentro da pasta v2/frontend):
    npm install
    npm run start
    Neste momento, você teria o frontend rodando na porta padrão (4200), chamando o API Gateway na porta 8080.