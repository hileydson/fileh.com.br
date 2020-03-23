<%@include file="includes_Geral/campoPesquisa_Nao_Mostrar.jsp"%> 
<%@include file="includes_Geral/menu_cabecalho.jsp"%>


  <script>
  $(document).ready(function() {

	  
	    $( "#dialog-form-alerta" ).dialog({
	        autoOpen: false,
	        height: 170,
	        width: 250,
	        modal: true,
	        buttons: {
	          OK: function() {
	        	 $( "#id_commit" ).attr("disabled", false);

	        	 $( "#ID_TCO_DS_TIPO_CONTA" ).css("background-color", 'IndianRed');
	        	  
	        	 $( this ).dialog( "close" ); 

	          }
	        }
	      });
	    
	    $( "#id_commit" ).on('click',(function( ){

			if ($( "#ID_TCO_DS_TIPO_CONTA" ).val() == ''){
				$( "#dialog-form-alerta" ).dialog( "open" );
			}else{
				document.forms['editarTipoConta'].submit();
			};  	  	    
			
  	    }));
	    
	    
	    $("#ID_TCO_DS_TIPO_CONTA").bind("keypress", function(e) {
	        if (e.keyCode == 13) {
	           return false;
	        }
	     });
	    
	    //reajusta o modulo em que a pagina se refere...
	    $("#id_menu_financeiro").addClass('selected');
	    $( "#templatemo_menu" ).on('mouseout',(function( ){
	    	$("#id_menu_financeiro").addClass('selected');
	    })); 
    	  
  });


  </script>
    
<%!TipoConta tipoConta ;%>
<%tipoConta = (TipoConta) request.getSession().getAttribute("tipoConta"); %>
    
    
<body>

 <div id="dialog-form-alerta" title="Alerta...">
  <p class="validateTips">Favor preencher todos os campos necessários...</p>
</div>

<form  name="editarTipoConta" action="ConfirmaEditarTipoConta.action" method="post"> 
       
		<div >
		<h6></h6> 			
   <!-- Lista de Produtos Serdo -->
   <h3>Editar Tipo de Conta</h3>

        <hr></hr>
        
        <table align="center" cellpadding="5" cellspacing="5"  style="font-size: larger;" >
        <tr>
        <td>Descrição :</td>
		<td><input value="<%= tipoConta.getTco_ds_tipo_conta() %>" maxlength="45" id="ID_TCO_DS_TIPO_CONTA" name="TCO_DS_TIPO_CONTA" type="text" style=" width : 270px;"class="uppercase"></input></td>
        </tr>
        
       
		<tr >
			<td>				
			</td>
			<td align="center" >
				<a href="cadastroTipoConta.jsp"><input type="button" value="  Voltar  " ></input></a>
				<!-- disabilita o commit para não clicar mais de 1 vez... -->
				<input type="button" id="id_commit" name="commit" onclick="this.form['commit'].disabled=true ; " value="  Confirmar  " >
			</td>
		</tr>
		
		</table>
<hr></hr>
		</div>
</form>


	<%@include file="includes_Geral/footer.jsp"%>
</body>
</html>