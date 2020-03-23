<!-- setDataSource é uma tag JSTL para configuração de acesso ao servidor de banco de dados criando um objeto dataSource que será consumido 
pelas outras tags SQL, onde devem ser definidos os atributos necessários para especificar: o nome da variável dataSource, driver, 
url(caminho do servidor), usuário, senha e principalmente o "escopo" de visibilidade do objeto na aplicação, em nosso caso, definimos 
que  nosso objeto será compartilhado por todo o tempo de    vida da sessão.-->


<!-- BASE DE PRODUÇÃO - KINGHOST - fileh01
<sql:setDataSource var="ds" driver="com.mysql.jdbc.Driver"
	url="jdbc:mysql://mysql03-farm13.kinghost.net:3306/fileh01" user="fileh01"
	password="camila123" scope="session" /> -->
	


<!-- BASE DE PRODUÇÃO - KINGHOST - fileh00 
<sql:setDataSource var="ds" driver="com.mysql.jdbc.Driver"
	url="jdbc:mysql://mysql03-farm13.kinghost.net:3306/fileh" user="fileh"
	password="camila123" scope="session" /> -->
	
	
	
	
	
<!-- BASE DE TESTE - LOCAL -->
<sql:setDataSource var="ds" driver="com.mysql.jdbc.Driver"
	url="jdbc:mysql://localhost:3306/fileh01" user="root"
	password="camila" scope="session" />  