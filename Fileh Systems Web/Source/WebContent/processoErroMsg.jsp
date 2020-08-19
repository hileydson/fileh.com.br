<%@include file="includes_Geral/campoPesquisa_Nao_Mostrar.jsp"%> 
<%@include file="includes_Geral/menu_cabecalho.jsp"%>



</head>

<body>

		<!-- CADASTRO DE USUARIOS -->
		<div id="templatemo_slider">
			<br></br> <br> </br>
			<br></br> <br> </br>
			<h3 align="center" style="font-style: italic; color: red;"><%= request.getSession().getAttribute("msg_erro") %></h3>
		</div>

		<!-- JSTL -->


		



		<%@include file="includes_Geral/footer.jsp"%>
<script>sessionStorage.setItem("msg_erro","");</script>	
</body>
</html>