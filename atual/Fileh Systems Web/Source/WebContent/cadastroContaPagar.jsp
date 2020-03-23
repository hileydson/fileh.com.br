<%@include file="includes_Geral/campoPesquisa_Nao_Mostrar.jsp"%> 
<%@include file="includes_Geral/menu_cabecalho.jsp"%>
<%@include file="includes_Geral/dataCalendarFunction.jsp"%>


<script language="javascript" type="text/javascript">

	function checkBoxParcelado()
	{
		
		if (document.cadastroContaPagar.COP_FL_PARCELADO.checked == false){
			document.cadastroContaPagar.COP_NR_PARCELA.disabled=true;
			document.cadastroContaPagar.COP_NR_PARCELA.value = '0';
		}else{			
			document.cadastroContaPagar.COP_NR_PARCELA.disabled=false;
			document.cadastroContaPagar.COP_NR_PARCELA.value = '';
			document.cadastroContaPagar.COP_NR_PARCELA.focus();
		}

	};
	
</script>


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
	        	  
		          $( "#id_COP_DS_FORNECEDOR" ).css("background-color", 'IndianRed');
		          $( "#id_COP_DS_TIPO_CONTA" ).css("background-color", 'IndianRed');
		          $( "#datepicker" ).css("background-color", 'IndianRed');
		          $( "#id_COP_DS_CONTA_PAGAR" ).css("background-color", 'IndianRed');
		          $( "#id_COP_VL_CONTA_PAGAR" ).css("background-color", 'IndianRed');
		          $( "#id_COP_FL_PARCELADO" ).css("background-color", 'IndianRed');
		          $( "#id_COP_NR_PARCELA" ).css("background-color", 'IndianRed');
		          
	        	 $( this ).dialog( "close" ); 

	          }
	        }
	      });
	  
	  
	    $( "#id_commit" ).on('click',(function( ){

			if (	($( "#id_COP_DS_FORNECEDOR" ).val() == '-1')||($( "#id_COP_DS_TIPO_CONTA" ).val() == '-1')	
					||($( "#datepicker" ).val() == '')||($( "#id_COP_VL_CONTA_PAGAR" ).val() == '0,00') ||($( "#id_COP_DS_CONTA_PAGAR" ).val() == '')
					||($( "#id_COP_FL_PARCELADO" ).val() == '')||($( "#id_COP_NR_PARCELA" ).val() == '')){
				
				$( "#dialog-form-alerta" ).dialog( "open" );
				
			}else{
				document.forms['cadastroContaPagar'].submit();
			};  	  	    
			
  	    }));
	    
	    
	    //reajusta o modulo em que a pagina se refere...
	    $("#id_menu_financeiro").addClass('selected');
	    $( "#templatemo_menu" ).on('mouseout',(function( ){
	    	$("#id_menu_financeiro").addClass('selected');
	    })); 
	    
    	  
  });


  </script>


<body>


<div id="dialog-form-alerta" title="Alerta...">
  <p class="validateTips">Preencha todos os campos necessários...</p>
</div>


<form  name="cadastroContaPagar" action="CadastrarContaPagar.action" method="post"> 

           
		<div >
		<h6></h6> 			
   <!-- Lista de Produtos Serdo -->
   <h3>Cadastro de Contas a pagar</h3>

        <hr></hr>

        <table align="center" cellpadding="5" cellspacing="5"  style="font-size: larger;" >
        <tr>
        <td>Fornecedor :</td>
        
        <td>
        	<select id="id_COP_DS_FORNECEDOR" name="COP_DS_FORNECEDOR"  style=" width : 270px;" maxlength="45"> 
        		<option value="-1" selected="selected">Escolha um fornecedor...</option>      
  				<%! ArrayList<String> arrayNomesFornecedor; %>
				<%  arrayNomesFornecedor = (ArrayList<String>) request.getSession().getAttribute("arrayNomesFornecedor"); %>
				<%  for(int i = 0; i<arrayNomesFornecedor.size();i++){ %> 
    				<option value="<%=arrayNomesFornecedor.get(i) %>"><%=arrayNomesFornecedor.get(i) %></option>    
 				<%}; %>    
			</select>  
        </td>
        </tr>
        
        <tr>
        <td>Tipo de Conta :</td>
        <td>
        	<select id="id_COP_DS_TIPO_CONTA" name="COP_DS_TIPO_CONTA" style=" width : 270px;" maxlength="45" >    
        		<option value="-1" selected="selected">Escolha o tipo da conta ...</option> 
  				<%! ArrayList<String> arrayNomesTipoConta; %>
				<%  arrayNomesTipoConta = (ArrayList<String>) request.getSession().getAttribute("arrayNomesTipoConta"); %>
				<%  for(int i = 0; i<arrayNomesTipoConta.size();i++){ %>  
    				<option value="<%=arrayNomesTipoConta.get(i) %>"><%=arrayNomesTipoConta.get(i) %></option>     
 				<%}; %>  
			</select>  
        </td>        
        </tr>
        
        <tr>
        <td>Numero Documento :</td>
		<td><input maxlength="45" id="id_COP_NR_DOCUMENTO" name="COP_NR_DOCUMENTO" type="text" style=" width : 270px;" class="uppercase"></input></td>
		</tr>
		
        <tr>
        <td>Descrição :</td>
		<td><input maxlength="45" id="id_COP_DS_CONTA_PAGAR" name="COP_DS_CONTA_PAGAR" type="text" style=" width : 270px;"class="uppercase"></input></td>
        </tr>		
		
		<tr>
		<td>Vencimento :</td>
		<td><input title="Clique para escolher..." readonly id="datepicker"  name="COP_DT_VENCIMENTO" type="text" style=" width : 80px;" ></input></td>
		</tr>
		
		<tr>
		<td>Valor :
		<td><input title="O valor não pode ser R$0,00" id="id_COP_VL_CONTA_PAGAR" name="COP_VL_CONTA_PAGAR" type="text" class="realN"  style=" width : 150px;" ></input></td>
		</tr>
		

		<tr>
		
		<td>Parcela : ----<input title="Marque se for parcelado" id="id_COP_FL_PARCELADO" name="COP_FL_PARCELADO" type="checkbox" align="bottom" value="S" onchange="checkBoxParcelado()" ></input>----</td>
		<td><input title="Informe o numero da parcela..." maxlength="4" class="numberOnly" disabled="disabled" id="id_COP_NR_PARCELA" name="COP_NR_PARCELA" value="0" type="text" style=" width : 35px;"></input></td>
		</tr>
	
		
		<tr >
		<td></td>
		<td align="center" >
		<input  type="button" id="id_commit" name="commit" onclick="this.form['commit'].disabled=true" value="  Confirmar  ">
		</td>
		</tr>
		
		
		</table>
		
		<br>
		<a><img src="images/pages_icon.png"  hspace="10px" width="20px" alt="" align="left" /></a>* Para cadastrar uma conta a pagar ou receber é necessário cadastrar primeiro o fornecedor e o tipo de conta...
		<br><br>
		<hr></hr>


		</div>
</form>

	<%@include file="includes_Geral/footer.jsp"%>

</body>
</html>