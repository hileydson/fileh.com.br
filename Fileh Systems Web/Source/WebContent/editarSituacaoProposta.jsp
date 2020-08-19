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

	        	 $( "#ID_SIP_DS_SITUACAO_PROPOSTA" ).css("background-color", 'IndianRed');
	        	  
	        	 $( this ).dialog( "close" ); 

	          }
	        }
	      });
	    
	    $( "#id_commit" ).on('click',(function( ){

			if ($( "#ID_SIP_DS_SITUACAO_PROPOSTA" ).val() == ''){
				$( "#dialog-form-alerta" ).dialog( "open" );
			}else{
				document.forms['editarSituacaoProposta'].submit();
			};  	  	    
			
  	    }));
	    
	    
	    $("#ID_SIP_DS_SITUACAO_PROPOSTA").bind("keypress", function(e) {
	        if (e.keyCode == 13) {
	           return false;
	        }
	     });
	    
	    //reajusta o modulo em que a pagina se refere...
	    $("#id_menu_vendas").addClass('selected');
	    $( "#templatemo_menu" ).on('mouseout',(function( ){
	    	$("#id_menu_vendas").addClass('selected');
	    })); 
	    
    	  
  });


  </script>
    
<%!SituacaoProposta situacaoProposta ;%>
<%situacaoProposta = (SituacaoProposta) request.getSession().getAttribute("situacaoProposta"); %>
    
    
<body>

 <div id="dialog-form-alerta" title="Alerta...">
  <p class="validateTips">Favor preencher todos os campos necessários...</p>
</div>

<form  name="editarSituacaoProposta" action="ConfirmaEditarSituacaoProposta.action" method="post"> 
       
		<div >
		<h6></h6> 			
   <!-- Lista de Produtos Serdo -->
   <h3>Editar Situação Proposta</h3>

        <hr></hr>
        
        <table align="center" cellpadding="5" cellspacing="5"  style="font-size: larger;" >
        <tr>
        <td>Descrição :</td>
		<td><input value="<%= situacaoProposta.getSip_ds_situacao_proposta() %>" maxlength="45" id="ID_SIP_DS_SITUACAO_PROPOSTA" name="SIP_DS_SITUACAO_PROPOSTA" type="text" style=" width : 270px;"class="uppercase"></input></td>
        </tr>
        
       
		<tr >
			<td>				
			</td>
			<td align="center" >
				<a href="cadastroSituacaoProposta.jsp"><input type="button" value="  Voltar  " ></input></a>
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