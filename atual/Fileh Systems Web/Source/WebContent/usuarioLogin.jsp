<%@include file="includes_Geral/menu_cabecalho_principal.jsp"%>



<script>		
$(document).ready(function() {
	
    $( "#id_botao_comfirmar" ).on('click',(function( ){

    	$('#id_div_loading_animation').dialog( "open" );
    	chamaServlet('formLoginUsuario','LoginUsuario.action');
	}));
	
    $("#id_body").bind("keypress", function(e) {
        if (e.keyCode == 13) {
        	$( "#id_botao_comfirmar" ).click();
        }
     });
    
});

</script>


<body onload="$('#id_input_text_email_usuario').focus();" id="id_body">

	<div id="templatemo_wrapper">
		<div id="templatemo_slider">

			<form name="formLoginUsuario" method="post"
				action="LoginUsuario.action" >
				<br><br> <br>
				<br><br> 

				<h4 align="center" style="color: graytext;">
					Email : <input maxlength="30" align="middle"
						name="input_text_email_usuario" id="id_input_text_email_usuario"
						style="width: 251px;" />
				</h4>

				<h4 align="center" style="color: graytext;">
					Login : <input maxlength="30" align="middle"
						name="input_text_login_usuario" id="id_input_text_login_usuario"
						style="width: 251px;" />
				</h4>



				<h4 align="center" style="color: graytext;">
					Senha : <input maxlength="30" align="middle" type="password"
						name="input_text_senha_usuario" size="10"
						id="id_input_text_senha_usuario" style="width: 251px;" />
				</h4>


				<h4 align="center" style="color: graytext;">
					<input value="    Confirmar    "	id="id_botao_comfirmar" name="btn_confirmar" type="button" ></input>
				</h4>
				
				<h5 align="center">
					<%
						if ((session.getAttribute("usuario") != null)
								&& session.getAttribute("usuario").toString()
										.equalsIgnoreCase("erro")) {
					%>
					<label id="id_label_resposta_cadastro_usuario_erro"
						style="color: red; margin-bottom: 10"> Senha ou usuario
						invalido, nao foi possivel fazer o Login! </label>
					<%
						};
					%>
				</h5>
				
				<div align="center">
				* Informe o email, login e senha para acessar a área restrita...
				</div>
				<%@include file="includes_Geral/loading_animation.jsp"%>
			</form>

		</div>
		<br>
		
		
		<%@include file="includes_Geral/footer_principal.jsp"%>
	</div>

</body>

</html>

