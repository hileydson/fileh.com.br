<%@include file="includes_Geral/campoPesquisa_Nao_Mostrar.jsp"%> 
<%@include file="includes_Geral/menu_cabecalho.jsp"%>
  
  
  <style>
    #div_contador{  
	 left: 20px;
	 top: 20px;
	}  
  
  </style>


<%!Plano plano; %>
<% plano = (Plano) request.getSession().getAttribute("planoUsuario"); %>

<!-- ------------------------Jquerys-INICIO--------------------------------- -->
<link rel="stylesheet" href="css/jquery.percentageloader-0.1.css"/>
<script src="js/jquery.percentageloader-0.1.js" type="text/javascript"></script>
<script src="js/jquery.percentageloader-0.1.min.js" type="text/javascript"></script>
<!-- ------------------------Jquerys-FIM--------------------------------- -->
  <script>

  
  $(document).ready(function() {
	//carrega contator do sistema
	$("#div_contador").percentageLoader({
    width : 200, height : 200, progress : ($('#contadorUsoSistema').val()/$('#contadorTotalEspacoSistema').val()), value : $('#contadorTotalEspacoSistema').val() });
	

    //reajusta o modulo em que a pagina se refere...
    $("#id_menu_geral").addClass('selected');
    $( "#templatemo_menu" ).on('mouseout',(function( ){
    	$("#id_menu_geral").addClass('selected');
    })); 
	    
  });


  </script>



<body>
   <h3>Dados Cadastro</h3>
<div align="center" >
		<div  >
		<h6></h6> 			
   <!-- Lista de Produtos Serdo -->


        <hr></hr>
 
        <table  style="font-size: larger;" >
        <tr>
        <td>Nome :</td>
		<td><input value="<%= usuario.getUsu_nm_usuario() %>" disabled="disabled" maxlength="45" type="text" style=" width : 270px;"class="uppercase"></input></td>
        </tr>
        
        <tr>
        <td>Fantasia :</td>
		<td><input value="<%= usuario.getUsu_ds_fantasia()  %>" disabled="disabled" maxlength="45" type="text" style=" width : 270px;"class="uppercase"></input></td>
        </tr>
		
		<tr>
		<td>CPF/CNPJ :</td>
		<td><input value="<%= usuario.getUsu_nr_cnpj()  %>" disabled="disabled" maxlength="20"  type="text" style=" width : 270px;"></input></td>
		</tr>	


        <tr>
        <td>Logradouro :</td>
		<td><input value="<%= usuario.getUsu_ds_logradouro()  %>" disabled="disabled" maxlength="45"  type="text" style=" width : 270px;"class="uppercase"></input></td>
		</tr>
		
		
        <tr>
        <td>Numero :</td>
		<td><input value="<%= usuario.getUsu_ds_numero()  %>" disabled="disabled" maxlength="45"  type="text" style=" width : 270px;"class="uppercase"></input></td>
		</tr>		
		
		
		<tr>
		<td>Bairro :</td>
		<td><input value="<%= usuario.getUsu_ds_bairro()  %>" disabled="disabled" maxlength="45"  type="text" style=" width : 270px;"class="uppercase"></input></td>
		</tr>
		
		
		<tr>
		<td>Cidade :</td>
		<td><input value="<%= usuario.getUsu_ds_cidade()  %>" disabled="disabled" maxlength="45"  type="text" style=" width : 270px;"class="uppercase"></input></td>
		</tr>

		
		<tr>
		<td>Telefone :</td>
		<td><input value="<%= usuario.getUsu_ds_tel()  %>" disabled="disabled" maxlength="45"  type="text" style=" width : 270px;"></input></td>
		</tr>

		
		
		<tr>
		<td>Email :</td>
		<td><input readonly value="<%= usuario.getUsu_ds_email()  %>" disabled="disabled" maxlength="45"  type="text" style=" width : 270px;"></input></td>
		</tr>	
		
		
		
		</table>
		
		<hr></hr>
	</div>

</div>

<br>
<br>


	<h3>Plano</h3>
	<hr></hr>
	<div align="center"  >
	<table>
		<tr><td>Plano </td><td>: <%= plano.getPla_nm_plano() %></td></tr>
		<tr><td>Descrição </td><td>: <%= plano.getPla_ds_plano() %></td></tr>
		<tr><td>Espaço </td><td>: <%= plano.getPla_ds_espaco() %></td></tr>
	</table>
		<br>
		<br>
		<div align="center" id="div_contador" >Uso do Sistema</div>
	</div>
	<hr></hr>

<br>
<br>

	<h3>Contato Suporte</h3>
	<hr></hr>
	<div align="center"  >
	<table>
		<tr><td>Email </td><td>: filehsystems@fileh.com.br</td></tr>
		<tr><td>Telefone </td><td>: (27) 98868-3637</td></tr>
	</table>
	</div>
	<hr></hr>

	
	
	
<div align="center" style="visibility: hidden" >
			<input id="contadorUsoSistema" type="text" value="<%= request.getSession().getAttribute("contadorUsoSistema").toString() %>" ></input>
			<input id="contadorTotalEspacoSistema" type="text" value="<%= plano.getPla_ds_espaco() %>" ></input>
</div>




		<%@include file="includes_Geral/footer.jsp"%>
</body>
</html>