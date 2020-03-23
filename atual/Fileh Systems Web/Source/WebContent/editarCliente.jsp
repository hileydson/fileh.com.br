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

	        	 $( "#id_cli_nm_cliente" ).css("background-color", 'IndianRed');
	        	 $( "#id_cli_ds_uf" ).css("background-color", 'IndianRed');
	        	 $( "#id_cli_ds_entidade" ).css("background-color", 'IndianRed');
	        	  
	        	 $( this ).dialog( "close" ); 

	          }
	        }
	      });
	    $( "#id_commit" ).on('click',(function( ){

			if (	($( "#id_cli_nm_cliente" ).val() == '')||($( "#id_cli_ds_uf" ).val() == '')||($( "#id_cli_ds_entidade" ).val() == '-1')	){
				$( "#dialog-form-alerta" ).dialog( "open" );
			}else{
				document.forms['editarCliente'].submit();
			};  	  	    
			
  	    }));
	    
	    //reajusta o modulo em que a pagina se refere...
	    $("#id_menu_cliente").addClass('selected');
	    $( "#templatemo_menu" ).on('mouseout',(function( ){
	    	$("#id_menu_cliente").addClass('selected');
	    })); 
    	  
  });


  </script>
    
<%!Cliente cliente ;%>
<%cliente = (Cliente) request.getSession().getAttribute("cliente"); %>
    

<body>

 <div id="dialog-form-alerta" title="Alerta...">
  <p class="validateTips">Preencha todos os campos necessários...</p>
</div>


<form  name="editarCliente" action="ConfirmaEditarCliente.action" method="post"> 
       
		<div >
		<h6></h6> 			
   <!-- Lista de Produtos Serdo -->
   <h3>Editar Cliente</h3>

        <hr></hr>

        <!-- CADASTRO DE CLIENTE -->
        
        <table align="center" cellpadding="5" cellspacing="5"  style="font-size: larger;" >
        <tr>
        <td>Nome :</td>
		<td><input value="<%=cliente.getCli_nm_cliente() %>" maxlength="45" name="cli_nm_cliente" id="id_cli_nm_cliente" type="text" style=" width : 270px;"class="uppercase"></input></td>
        </tr>
        
        <tr>
        <td>CPF/CNPJ :</td>
		<td><input value="<%=cliente.getCli_nr_cpf() %>" maxlength="20" name="cli_nr_cpf" type="text" style=" width : 270px;" class="uppercase"></input></td>
        </tr>        
        
        <tr>
        <td>Logradouro :</td>
		<td><input value="<%=cliente.getCli_ds_logradouro() %>" maxlength="45" name="cli_ds_logradouro" type="text" style=" width : 270px;"class="uppercase"></input></td>
		</tr>

        <tr>
        <td>Referência :</td>
		<td><input value="<%=cliente.getCli_ds_referencia() %>" maxlength="45" name="cli_ds_referencia" type="text" style=" width : 270px;"class="uppercase"></input></td>
		</tr>
		
		<tr>
		<td>Bairro :</td>
		<td><input value="<%=cliente.getCli_ds_bairro() %>" maxlength="45" name="cli_ds_bairro" type="text" style=" width : 270px;"class="uppercase"></input></td>
		</tr>
		
		<tr>
		<td>UF :</td>
		<td>
				<select name="cli_ds_uf" id="id_cli_ds_uf"		style="width: 270px;" >
		
					<option value="<%=cliente.getCli_ds_uf() %>" selected="selected"><%=cliente.getCli_ds_uf() %></option>
	        		<option >AC</option>
	        		<option >AL</option>
	        		<option >AP</option>
	        		<option >AM</option>
	        		<option >BA</option>
	        		<option >CE</option>
	        		<option >DF</option>
	        		<option >ES</option>
	        		<option >GO</option>
	        		<option >MA</option>
	        		<option >MT</option>
	        		<option >MS</option>
	        		<option >MG</option>
	        		<option >PA</option>
	        		<option >PB</option>
	        		<option >PR</option>
	        		<option >PE</option>
	        		<option >PI</option>
	        		<option >PJ</option>
	        		<option >RN</option>
	        		<option >RS</option>
	        		<option >RO</option>
	        		<option >RR</option>
	        		<option >SC</option>
	        		<option >SP</option>
	        		<option >SE</option>
	        		<option >TP</option>
	        		
			</select>		
		</td>
		
		</tr>
		
		
		<tr>
		<td>Telefone :</td>
		<td><input value="<%=cliente.getCli_nr_tel() %>" maxlength="45" name="cli_nr_tel" type="text" style=" width : 270px;" class="uppercase"></input></td>
		</tr>
	
		<tr>
		<td>Entidade :</td>		
		<td>
            <select id="id_cli_ds_entidade" name="cli_ds_entidade"   style=" width : 270px;"> 
       			<option value="<%= cliente.getCli_ds_entidade() %>" selected="selected"><%= cliente.getCli_ds_entidade() %></option>      
				<%! ArrayList<String> arrayEntidadesEdit; %>
				<%  arrayEntidadesEdit = (ArrayList<String>) request.getSession().getAttribute("arrayEntidadesEdit"); %>
				<%  for(int i = 0; i<arrayEntidadesEdit.size();i++){ %>
    				<option value="<%=arrayEntidadesEdit.get(i) %>"><%=arrayEntidadesEdit.get(i) %></option>    
 				<%}; %>   
			</select>  
        </td>
		</tr>	
		
		<tr >
			<td>				
			</td>
			<td align="center" >
				<a href="listagemClientes.jsp"><input type="button" value="  Voltar  " ></input></a>
				<!-- disabilita o commit para não clicar mais de 1 vez... -->
				<input type="button" name="commit" id="id_commit" onclick="this.form['commit'].disabled=true ;" value="  Confirmar  " >
			</td>
		</tr>
		
		</table>
<hr></hr>
		</div>
</form>



	<%@include file="includes_Geral/footer.jsp"%>
</body>
</html>