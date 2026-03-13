# Application Rewrite Plan: Fileh Systems

    The legacy application currently uses a monolithic architecture with JSP for the frontend, a custom Java Servlet/Action approach for routing (*.action), and Hibernate with XML configurations (
    .hbm.xml
    ) for database mapping using MySQL.

    We will rewrite this to a Modern Microservices Architecture using Java (Spring Boot) for the backend and Angular for the frontend.

    User Review Required
    IMPORTANT

    This is a complete rewrite and architectural shift. Please confirm:

    Which Java version do you prefer? (Java 17 or Java 21 are recommended).
    Do you want to keep using MySQL as the primary database for all microservices, or split into different databases/schemas per microservice?
    Does the system need an API Gateway (e.g., Spring Cloud Gateway) for routing the Angular frontend to the various services?
    Are there any specific modern design patterns (like CQRS, Event-Driven) you want implemented, or a standard RESTful architecture with JWT authentication?


# Proposed Architecture & Changes
The monolithic application will be broken down into the following components:

    1. Frontend: Angular Workspace
    Framework: Angular (latest version).
    Styling: CSS/SCSS (TailwindCSS or Angular Material could be introduced if desired for modern aesthetics).
    Communication: HttpClient to consume the REST APIs.
    Security: JWT Interceptors and Auth Guards.
    Replaces: WebContent/*.jsp, custom CSS in 
    WebContent/css
    .

    2. Microservices Architecture (Backend)
    We will transition the src/entidades and src/action directly into distinct bounded contexts.

        a. Gateway and Registry (Optional but recommended)
        Spring Cloud Gateway: Central entry point for the Angular app. Routes requests to their responsive microservices.
        Eureka Server: Service registry for horizontal scalability.
        b. Auth & Identity Service (Microservice 1)
        Responsibilities: User authentication, authorization, JWT token generation, Tenant management.
        Entities Migrated: Usuario, SubUsuario, Plano, Parametro.
        Frameworks: Spring Boot, Spring Security, Spring Data JPA.
        c. Financial Service (Microservice 2)
        Responsibilities: Financial records, receivables, payables, and cash flow.
        Entities Migrated: ContaPagar, ContaReceber, FluxoCaixa, FormaPagamento, TipoConta.
        d. CRM & Entities Service (Microservice 3)
        Responsibilities: Customer and supplier management.
        Entities Migrated: Cliente, Fornecedor, Entidade.
        e. Commercial & Sales Service (Microservice 4)
        Responsibilities: Products and proposals.
        Entities Migrated: Produto, PropostaComercial, ItemProposta, SituacaoProposta.
        Verification Plan
        Automated Tests
        Backend:
        Implementation of JUnit 5 / Mockito for unit testing domain logic.
        Using Testcontainers with MySQL for integration testing of Spring Data JPA repositories.
        Frontend:
        Jasmine/Karma or Jest for component unit testing.
        Manual Verification
        Deploying the frontend locally.
        Authenticating via the UI to receive a JWT.
        Testing CRUD operations (e.g., CadastrarCliente) through the Angular UI, which routes to the API Gateway, and subsequently to the CRM Microservice, validating the data in the local MySQL instance.




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