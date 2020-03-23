<%@include file="includes_Geral/campoPesquisa_Nao_Mostrar.jsp"%> 
<%@include file="includes_Geral/menu_cabecalho.jsp"%>


<script>		
$(document).ready(function() {
	
    $( "#dialog-form-alerta" ).dialog({
        autoOpen: false,
        height: 160,
        width: 300,
        modal: true,
        buttons: {
          OK: function() {
        	  $( "#id_commit" ).attr("disabled", false);
        	 $( this ).dialog( "close" ); 

          }
        }
      });


    $( "#dialog-form-alerta2" ).dialog({
        autoOpen: false,
        height: 180,
        width: 350,
        modal: true,
        buttons: {
          Sim: function() {
        	  $( "#id_commit" ).attr("disabled", true);
        	  
  			  $('#id_div_loading_animation').dialog( "open" );
			  chamaServlet('formRestaurar','GeralRestaurarSistema.action');

          }, Não: function() {
        	  $( "#id_commit" ).attr("disabled", false);
	        	 $( this ).dialog( "close" ); 

	          }
        }
      });
  
    $( "#id_commit" ).on('click',(function( ){
    	var str = $( "#id_fileName" ).val();
		if (	 ($( "#id_fileName" ).val() != "") && (str.slice(-4) == ".csv")	){	
			$( "#dialog-form-alerta2" ).dialog( "open" );

		}else{
			$( "#dialog-form-alerta" ).dialog( "open" );
		};  	  	    
		
	    }));
    
    
    
    //reajusta o modulo em que a pagina se refere...
    $("#id_menu_geral").addClass('selected');
    $( "#templatemo_menu" ).on('mouseout',(function( ){
    	$("#id_menu_geral").addClass('selected');
    })); 

    
    $( "#dialog-form-msg-restaurar" ).dialog({
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
  <p class="validateTips">Favor escolher um arquivo de backup para restauração...</p>

</div>

 <div id="dialog-form-alerta2" title="Alerta...">
  <p class="validateTips">Esse processo irá restaurar os dados informados no arquivo de backup, deletando os dados existentes no módulo escolhido para backup.<br><br> Deseja continuar ?</p>

</div>


	<%!String msg_restaurar; %>
	<%
	//mensagem padrão de cadastro efetuado com sucesso
	if(request.getSession().getAttribute("msg_restaurar") != null  ){%>
		
		  <script>
		  $(document).ready(function() { 
			  $('#dialog-form-msg-restaurar').css('visibility', 'visible');	
			  $( "#dialog-form-msg-restaurar" ).dialog( "open" );   
		  });
		  </script>
		  

	<%
		msg_restaurar = request.getSession().getAttribute("msg_restaurar").toString();
		request.getSession().setAttribute("msg_restaurar", null);
	}else{
		msg_restaurar =  " ";
	};
	%>
	
  <div id="dialog-form-msg-restaurar" title="Alerta..." style="visibility: hidden;" class="dialog-position-top">
	<label  ><%=msg_restaurar %></label>
  </div>
  
  

	<div id="templatemo_wrapper">
		<div id="templatemo_slider" align="center">

			<form name="formRestaurar" method="post" enctype="multipart/form-data" >
				<br><br> <br>
				<br><br> 

				<h3 align="center" style="font-style: italic; color:blue;">Favor inserir arquivo de restauração de dados...</h3>

				<br>
				<input type="file" title="Escolha o arquivo de restauração do sistema..." name="fileName" id="id_fileName"/>

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
