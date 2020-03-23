<%@include file="../includes_Geral/menu_cabecalho_principal_restrito.jsp"%>

<body>
	<div id="templatemo_wrapper">
	
		
		<h4 align="center" style="color: blue;">
		RESTRITO LOGADO!
		</h4>
				
				<br>
		
		
		<h4 align="center" style="color: graytext;">
		OPÇÕES
		</h4>
				
				<br>
				
				
		<form name="restritoForm" method="post"
		action="RestritoCarregarCadastroUsuario.action" >
		
		<h4 align="center" style="color: graytext;">
			 <input type="submit" maxlength="30" value="Cadastrar nova Empresa/Usuarios"
				style="width: 251px;" />
		</h4>
		</form>
		
		
		
		<form name="restritoForm2" method="post"
		action="RestritoLogoffRestrito.action" >
		<h4 align="center" style="color: graytext;">
			 <input type="submit" maxlength="30" value="Logoff..."
				style="width: 251px;" />
		</h4>
				
		</form>
		
	</div>

</body>

</html>