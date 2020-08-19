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
	        	  
		          $( "#id_cli_nm_cliente" ).css("background-color", 'IndianRed');
		          $( "#id_cli_ds_uf" ).css("background-color", 'IndianRed');
		          $( "#id_CLI_CD_ENTIDADE" ).css("background-color", 'IndianRed');
		        	 
	        	 $( this ).dialog( "close" ); 

	          }
	        }
	      });
	  
	  
	    $( "#id_commit" ).on('click',(function( ){

			if (	($( "#id_cli_nm_cliente" ).val() == '')||($( "#id_cli_ds_uf" ).val() == '')||($( "#id_CLI_CD_ENTIDADE" ).val() == '-1')	){
				$( "#dialog-form-alerta" ).dialog( "open" );
			}else{
				document.forms['cadastroCliente'].submit();
			};  	  	    
			
  	    }));
	    
	    
	    //reajusta o modulo em que a pagina se refere...
	    $("#id_menu_cliente").addClass('selected');
	    $( "#templatemo_menu" ).on('mouseout',(function( ){
	    	$("#id_menu_cliente").addClass('selected');
	    })); 
	    
  });


  </script>



<body onload="$('#id_cli_nm_cliente').focus();">

 <div id="dialog-form-alerta" title="Alerta...">
  <p class="validateTips">Favor preencher todos os campos necessários...</p>
</div>

<form  name="cadastroCliente" action="CadastrarCliente.action" method="post"> 

		<div >
		<h6></h6> 			
   <!-- Lista de Produtos Serdo -->
   <h3>Cadastro de Cliente</h3>

        <hr></hr>

        <!-- CADASTRO DE CLIENTE -->
        
 
        
        <table align="center" cellpadding="5" cellspacing="5"  style="font-size: larger;" >
        <tr>
        <td>Nome :</td>
		<td><input maxlength="45" name="cli_nm_cliente" id="id_cli_nm_cliente" type="text" style=" width : 270px;"class="uppercase"></input></td>
        </tr>

		<tr>
		<td>CPF/CNPJ :</td>
		<td><input maxlength="20" name="cli_nr_cpf" id="id_cli_nr_cpf"  type="text" style=" width : 270px;" class="uppercase"></input></td>
		</tr>	


        <tr>
        <td>Logradouro :</td>
		<td><input maxlength="45" name="cli_ds_logradouro" id="id_cli_ds_logradouro" type="text" style=" width : 270px;"class="uppercase"></input></td>
		</tr>
		
		
        <tr>
        <td>Referência :</td>
		<td><input maxlength="45" name="cli_ds_referencia" id="id_cli_ds_referencia" type="text" style=" width : 270px;"class="uppercase"></input></td>
		</tr>		
		
		
		<tr>
		<td>Bairro :</td>
		<td><input  maxlength="45" name="cli_ds_bairro" id="id_cli_ds_bairro" type="text" style=" width : 270px;"class="uppercase"></input></td>
		</tr>
		
		<tr>
		<td>UF :</td>
		<td>
		<select name="cli_ds_uf" id="id_cli_ds_uf"		style="width: 270px;" >
		
					<option value="-1" selected="selected">Escolha o estado...</option>
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
		<td><input maxlength="45" name="cli_nr_tel" id="id_cli_nr_tel"  type="text" style=" width : 270px;" class="uppercase"></input></td>
		</tr>

		
        <tr>
        <td>Entidade :</td>
        
        <td>
        	<select title="Cada cliente pertence a uma entidade, se não existir cadastre uma, exemplo : Matriz" name="CLI_CD_ENTIDADE" id="id_CLI_CD_ENTIDADE"  style=" width : 270px;" maxlength="45"> 
        		<option value="-1" selected="selected">Escolha uma entidade...</option>      
				<%! ArrayList<Entidade> arrayEntidades; %>
				<%  arrayEntidades = (ArrayList<Entidade>) request.getSession().getAttribute("arrayEntidades"); %>
				<%  for(int i = 0; i<arrayEntidades.size();i++){ %>
    				<option value="<%=arrayEntidades.get(i).getEnt_cd_entidade() %>"><%=arrayEntidades.get(i).getEnt_nm_entidade() %></option>    
 				<%}; %>     
			</select>  
        </td>
        </tr>		
		
		
		<tr >
		<td></td>
		<td align="center" >
			<input  type="button" name="commit" id="id_commit" onclick="this.form['commit'].disabled=true ; " value="  Confirmar  ">
		</td>
		</tr>
		
		
		</table>
		<br><br>
<a><img src="images/pages_icon.png" hspace="10px" width="20px" alt="" align="left" /></a>* Para cadastrar um cliente é necessário primeiro cadastrar a entidade que esse cliente pertence, por exemplo a matriz ou filial...
<br><br>
		<hr></hr>

		</div>
</form>


		<%@include file="includes_Geral/footer.jsp"%>
</body>
</html>