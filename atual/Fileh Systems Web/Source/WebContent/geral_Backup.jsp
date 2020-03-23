<%@page import="action.GeralDownloadCompleteBackupAction"%>
<%@include file="includes_Geral/campoPesquisa_Nao_Mostrar.jsp"%> 
<%@include file="includes_Geral/menu_cabecalho.jsp"%>


<script>		
$(document).ready(function() {
	
    $( "#id_botao_comfirmar" ).on('click',(function( ){

    	$('#id_div_loading_animation').dialog( "open" );
    	chamaServlet('formLoginUsuario','LoginUsuario.action');
	}));
	

    
});

</script>



  <script>

  
  $(document).ready(function() {

	    $( "#dialog-form-alerta" ).dialog({
	        autoOpen: false,
	        height: 160,
	        width: 300,
	        modal: true,
	        buttons: {
	          Sim: function() {
	        	  $( "#id_commit" ).attr("disabled", true);
	        	  
	        	  $('#id_div_loading_animation').dialog( "open" );
				  chamaServlet('formBackup','GeralBackupSistema.action');

	          }, Não: function() {
	        	  $( "#id_commit" ).attr("disabled", false);
		        	 $( this ).dialog( "close" ); 

		          }
	        }
	      });
	  
	  
	    $( "#id_commit" ).on('click',(function( ){
	      	  var myRadio = $('input[name=radio_backup]');
	    	  $('#id_label_msg').text('- '+myRadio.filter(':checked').val());
	    	  
			$( "#dialog-form-alerta" ).dialog( "open" );
  	    }));
	    
	    
	    //reajusta o modulo em que a pagina se refere...
	    $("#id_menu_geral").addClass('selected');
	    $( "#templatemo_menu" ).on('mouseout',(function( ){
	    	$("#id_menu_geral").addClass('selected');
	    })); 

	    $( "#dialog-form-msg-backup" ).dialog({
	        autoOpen: false,
	        height: 300,
	        width: 400,
	        modal: true,
	        buttons: {
	          OK: function() {		        	 
	        	 $( this ).dialog( "close" ); 

	          }
	        }
	      });
	    
  });


  </script>


<body >
 <div id="dialog-form-alerta" title="Alerta...">
  <p class="validateTips">Deseja realmente fazer o backup da opção abaixo ?</p>

  <label id="id_label_msg"  style="font-family: fantasy;font-style: italic; font-size: 20px"></label>
</div>

	<%!String msg_backup; %>
	<%
	//mensagem padrão de cadastro efetuado com sucesso
	if(request.getSession().getAttribute("msg_backup") != null  ){%>
		
		  <script>
		  $(document).ready(function() { 
			  $('#dialog-form-msg-backup').css('visibility', 'visible');	
			  $( "#dialog-form-msg-backup" ).dialog( "open" ); 
			  chamaServlet('formBackup','GeralDownloadCompleteBackup.action');
		  });
		  </script>
		  

	<%
		msg_backup = request.getSession().getAttribute("msg_backup").toString();
		request.getSession().setAttribute("msg_backup", null);
	}else{
		msg_backup =  " ";
	};
	%>
	
  <div id="dialog-form-msg-backup" title="Alerta..." style="visibility: hidden;" class="dialog-position-top">
	<label  ><%=msg_backup %></label>
  </div>
  

	<div id="templatemo_wrapper">
		<div id="templatemo_slider" align="center">

			<form name="formBackup" id="id_formBackup" method="post" >
				<br>
				<br><br> 

				<h3 align="center" style="font-style: italic; color:blue;">Escolha uma opção de backup...</h3>
				<br><br> 
				<table>
					<tr>
						<td width="280px"><h5><input type="radio" checked="checked" value="Fluxo de Caixa" name="radio_backup"  id="id_radio_backup"></input>Fluxo de Caixa</h5></td>
						<td ><h5><input title="Será feito também o backup de Entidades" type="radio" value="Clientes" name="radio_backup" id="id_radio_backup" ></input>Clientes</h5></td>
					</tr>
					<tr>
						<td><h5><input type="radio" value="Produtos" name="radio_backup" id="id_radio_backup" ></input>Produtos</h5></td>
						<td><h5><input title="Será feito também o backup de Formas de pagamento/Saída, Itens das Propostas e Situação Proposta" type="radio" value="Propostas Comercias" name="radio_backup" id="id_radio_backup" ></input>Propostas Comercias</h5></td>
					</tr>				
					<tr>
						<td><h5><input type="radio" value="Fornecedores" name="radio_backup" id="id_radio_backup"></input>Fornecedores</h5></td>
						<td><h5><input type="radio" value="Tipo de Contas" name="radio_backup" id="id_radio_backup"></input>Tipo de Contas</h5></td>
					</tr>
					<tr>
						<td><h5><input type="radio" value="Contas a Pagar" name="radio_backup" id="id_radio_backup"></input>Contas a Pagar</h5></td>
						<td><h5><input type="radio" value="Contas a Receber" name="radio_backup" id="id_radio_backup"></input>Contas a Receber</h5></td>
					</tr>								
				</table>
				
				<br><br> 


				<h4 align="center" style="color: graytext;">
					<input value="    Confirmar    "	id="id_commit" name="commit" onclick="this.form['commit'].disabled=true ; "  type="button" ></input>
				</h4>
				

				
				<div align="center">
				* Esse processo pode levar alguns minutos dependendo da quantidade de dados...
				</div>
				<%@include file="includes_Geral/loading_animation.jsp"%>
			</form>

		</div>
		<br>
		
		
		<%@include file="includes_Geral/footer.jsp"%>
	</div>

</body>

</html>

