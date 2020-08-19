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

	        	 $( "#ID_FOR_DS_FORNECEDOR" ).css("background-color", 'IndianRed');
	        	  
	        	 $( this ).dialog( "close" ); 

	          }
	        }
	      });
	    $( "#id_commit" ).on('click',(function( ){

			if ($( "#ID_FOR_DS_FORNECEDOR" ).val() == ''){
				$( "#dialog-form-alerta" ).dialog( "open" );
			}else{
				document.forms['editarFornecedor'].submit();
			};  	  	    
			
  	    }));
	    
	    $("#ID_FOR_DS_FORNECEDOR").bind("keypress", function(e) {
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
    
<%!Fornecedor fornecedor ;%>
<%fornecedor = (Fornecedor) request.getSession().getAttribute("fornecedor"); %>
    

<body>

 <div id="dialog-form-alerta" title="Alerta...">
  <p class="validateTips">Favor preencher todos os campos necessários...</p>
</div>

<form  name="editarFornecedor" action="ConfirmaEditarFornecedor.action" method="post"> 
       
		<div >
		<h6></h6> 			
   <!-- Lista de Produtos Serdo -->
   <h3>Editar Fornecedor</h3>

        <hr></hr>

        
        <table align="center" cellpadding="5" cellspacing="5"  style="font-size: larger;" >
        <tr>
        <td>Nome :</td>
		<td><input value="<%= fornecedor.getFor_ds_fornecedor() %>" maxlength="45" id="ID_FOR_DS_FORNECEDOR" name="FOR_DS_FORNECEDOR" type="text" style=" width : 270px;"class="uppercase"></input></td>
        </tr>
        
        <tr>
        <td>CNPJ :</td>
		<td><input value="<%= fornecedor.getFor_nr_cnpj() %>" maxlength="45" name="FOR_NR_CNPJ" type="text" style=" width : 270px;" class="uppercase"></input></td>
        </tr>
       
        <tr>
        <td>Logradouro :</td>
		<td><input value="<%= fornecedor.getFor_ds_logradouro() %>" maxlength="45" name="FOR_DS_LOGRADOURO" type="text" style=" width : 270px;" class="uppercase"></input></td>
        </tr>        
		
        <tr>
        <td>Bairro :</td>
		<td><input value="<%= fornecedor.getFor_ds_bairro() %>" maxlength="45" name="FOR_DS_BAIRRO" type="text" style=" width : 270px;" class="uppercase"></input></td>
        </tr>        

        <tr>
        <td>Cidade :</td>
		<td><input value="<%= fornecedor.getFor_ds_cidade() %>" maxlength="45" name="FOR_DS_CIDADE" type="text" style=" width : 270px;" class="uppercase"></input></td>
        </tr>        

        <tr>
        <td>CEP :</td>
		<td><input value="<%= fornecedor.getFor_nr_cep() %>" maxlength="45" name="FOR_NR_CEP" type="text" style=" width : 270px;" class="uppercase"></input></td>
        </tr>        

        <tr>
        <td>Contato :</td>
		<td><input  value="<%= fornecedor.getFor_ds_contato() %>" maxlength="100" name="FOR_DS_CONTATO" type="text" style=" width : 270px;" class="uppercase"></input></td>
        </tr>   

        <tr>
        <td>Inscrição Estadual :</td>
		<td><input value="<%= fornecedor.getFor_nr_insc_estadual() %>" maxlength="45" name="FOR_NR_INSC_ESTADUAL" type="text" style=" width : 270px;" class="uppercase"></input></td>
        </tr>        

        <tr>
        <td>Inscrição Municipal :</td>
		<td><input value="<%= fornecedor.getFor_nr_insc_municipal() %>" maxlength="45" name="FOR_NR_INSC_MUNICIPAL" type="text" style=" width : 270px;" class="uppercase"></input></td>
        </tr>          
       
		<tr >
			<td>				
			</td>
			<td align="center" >
				<a href="cadastroFornecedor.jsp"><input type="button" value="  Voltar  " ></input></a>
				<!-- disabilita o commit para não clicar mais de 1 vez... -->
				<input type="button" id="id_commit" name="commit" onclick="this.form['commit'].disabled=true ;" value="  Confirmar  " >
			</td>
		</tr>
		
		</table>
<hr></hr>
		</div>
</form>



	<%@include file="includes_Geral/footer.jsp"%>
</body>
</html>