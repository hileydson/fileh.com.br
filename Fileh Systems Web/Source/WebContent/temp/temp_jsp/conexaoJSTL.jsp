<!-- setDataSource � uma tag JSTL para configura��o de acesso ao servidor de banco de dados criando um objeto dataSource que ser� consumido 
pelas outras tags SQL, onde devem ser definidos os atributos necess�rios para especificar: o nome da vari�vel dataSource, driver, 
url(caminho do servidor), usu�rio, senha e principalmente o "escopo" de visibilidade do objeto na aplica��o, em nosso caso, definimos 
que  nosso objeto ser� compartilhado por todo o tempo de    vida da sess�o.-->


<!-- BASE DE PRODU��O - KINGHOST - fileh01
<sql:setDataSource var="ds" driver="com.mysql.jdbc.Driver"
	url="jdbc:mysql://mysql03-farm13.kinghost.net:3306/fileh01" user="fileh01"
	password="camila123" scope="session" /> -->
	


<!-- BASE DE PRODU��O - KINGHOST - fileh00 
<sql:setDataSource var="ds" driver="com.mysql.jdbc.Driver"
	url="jdbc:mysql://mysql03-farm13.kinghost.net:3306/fileh" user="fileh"
	password="camila123" scope="session" /> -->
	
	
	
	
	
<!-- BASE DE TESTE - LOCAL -->
<sql:setDataSource var="ds" driver="com.mysql.jdbc.Driver"
	url="jdbc:mysql://localhost:3306/fileh01" user="root"
	password="camila" scope="session" />  