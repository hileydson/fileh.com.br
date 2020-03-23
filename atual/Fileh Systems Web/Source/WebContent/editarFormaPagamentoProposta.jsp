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

	        	 $( "#ID_FOP_DS_FORMA_PAGAMENTO" ).css("background-color", 'IndianRed');
	        	  
	        	 $( this ).dialog( "close" ); 

	          }
	        }
	      });
	    
	    $( "#id_commit" ).on('click',(function( ){

			if ($( "#ID_FOP_DS_FORMA_PAGAMENTO" ).val() == ''){
				$( "#dialog-form-alerta" ).dialog( "open" );
			}else{
				document.forms['editarFormaPagamento'].submit();
			};  	  	    
			
  	    }));
	    
	    
	    $("#ID_FOP_DS_FORMA_PAGAMENTO").bind("keypress", function(e) {
	        if (e.keyCode == 13) {
	           return false;
	        }
	     });
	    
	    //reajusta o modulo em que a pagina se refere...
	    $("#id_menu_vendas").addClass('selected');
	    $("#id_menu_caixa").addClass('selected');
	    $( "#templatemo_menu" ).on('mouseout',(function( ){
	    	$("#id_menu_vendas").addClass('selected');
	    	$("#id_menu_caixa").addClass('selected');
	    })); 
	    
    	  
  });


  </script>
    
<%!FormaPagamento formaPagamento ;%>
<%formaPagamento = (FormaPagamento) request.getSession().getAttribute("formaPagamento"); %>
    
    
<body>

 <div id="dialog-form-alerta" title="Alerta...">
  <p class="validateTips">Favor preencher todos os campos necessários...</p>
</div>

<form  name="editarFormaPagamento" action="ConfirmaEditarFormaPagamento.action" method="post"> 
       
		<div >
		<h6></h6> 			
   <!-- Lista de Produtos Serdo -->
   <h3>Editar Forma de Pagamento</h3>

        <hr></hr>
        
        <table align="center" cellpadding="5" cellspacing="5"  style="font-size: larger;" >
        <tr>
        <td>Descrição :</td>
		<td><input value="<%= formaPagamento.getFop_ds_forma_pagamento() %>" maxlength="45" id="ID_FOP_DS_FORMA_PAGAMENTO" name="FOP_DS_FORMA_PAGAMENTO" type="text" style=" width : 270px;"class="uppercase"></input></td>
        </tr>
        
       
		<tr >
			<td>				
			</td>
			<td align="center" >
			<%if (formaPagamento.getFop_fl_tipo().equalsIgnoreCase("CX")){ %>
				<a href="cadastroFormaPagamentoCaixa.jsp"><input type="button" value="  Voltar  " ></input></a>
			<%}else{ %>
				<a href="cadastroFormaPagamento.jsp"><input type="button" value="  Voltar  " ></input></a>
			<%}; %>
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