<%@include file="../../includes_Geral/campoPesquisa_Nao_Mostrar.jsp"%> 
<%@include file="../../includes_Geral/menu_cabecalho.jsp"%>


<script type="text/javascript">


	function setAtributoIdCell(ths){
		strValueHiddenText = "";

		//id cliente
		col = ths.cells[0];
		strValueHiddenText += col.firstChild.nodeValue;
		
		document.getElementById("id_fornecedor_hidden").setAttribute("Value", strValueHiddenText);
	};


	function refreshPageEditar(){	
		document.forms["hidden"].action = "EditarFornecedor.action";
        document.forms["hidden"].submit();
	};
	

	function FocusInput(id){		
		document.getElementById(id).focus();
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
	        	  $( "#ID_FOR_DS_FORNECEDOR" ).css("background-color", 'IndianRed');
	        	 $( this ).dialog( "close" ); 

	          }
	        }
	      });
	  
	  
	    $( "#id_commit" ).on('click',(function( ){

			if ($( "#ID_FOR_DS_FORNECEDOR" ).val() == ''	){
				$( "#dialog-form-alerta" ).dialog( "open" );
			}else{
				document.forms['cadastroFornecedor'].submit();
			};  	  	    
			
  	    }));
    
	    
	    
	    $( "#dialog-form-alerta-apagar" ).dialog({
	        autoOpen: false,
	        height: 160,
	        width: 340,
	        modal: true,
	        buttons: {
	          Sim: function() {
	        	  
	        	  chamaServlet('hidden','ApagarFornecedor.action');
	        	 $( this ).dialog( "close" ); 

	          },Não: function() {
		        	 $( this ).dialog( "close" ); 
		      }
	        }
	      });
	  
	  
	    $( ".id_apagar" ).on('click',(function( ){
	    	$('#id_p').text( 'Deseja realmente apagar o fornecedor ' + $('#id_fornecedor_hidden').val() + ' ?' );
			$( "#dialog-form-alerta-apagar" ).dialog( "open" );
			
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

<body onload="FocusInput('ID_FOR_DS_FORNECEDOR')">


 <div id="dialog-form-alerta" title="Alerta..." class="dialog-position-top">
  <p class="validateTips">Favor preencher todos os campos necessários...</p>
</div>

 <div id="dialog-form-alerta-apagar" title="Alerta..." class="dialog-position-top">
	<p class="validateTips">
		<label id="id_p"></label>
	</p>
</div>


<form  name="cadastroFornecedor" action="CadastrarFornecedor.action" method="post"> 
       
		<div >
		<h6></h6> 			
   <!-- Lista de Produtos Serdo -->
   <h3>Cadastro de Fornecedor</h3>

        <hr></hr>

        <!-- CADASTRO DE Tipos de contas, tais como cheque, duplicata e etc... -->
        
 
        
        <table align="center" cellpadding="5" cellspacing="5"  style="font-size: larger;" >
        <tr>
        <td>Nome :</td>
		<td><input maxlength="45" id="ID_FOR_DS_FORNECEDOR" name="FOR_DS_FORNECEDOR" type="text" style=" width : 270px;" class="uppercase"></input></td>
        </tr>
        
        <tr>
        <td>CPF/CNPJ :</td>
		<td><input maxlength="45" name="FOR_NR_CNPJ" type="text" style=" width : 270px;" class="uppercase"></input></td>
        </tr>        

        <tr>
        <td>Logradouro :</td>
		<td><input maxlength="45" name="FOR_DS_LOGRADOURO" type="text" style=" width : 270px;" class="uppercase"></input></td>
        </tr>        
		
        <tr>
        <td>Bairro :</td>
		<td><input maxlength="45" name="FOR_DS_BAIRRO" type="text" style=" width : 270px;" class="uppercase"></input></td>
        </tr>        

        <tr>
        <td>Cidade :</td>
		<td><input maxlength="45" name="FOR_DS_CIDADE" type="text" style=" width : 270px;" class="uppercase"></input></td>
        </tr>        

        <tr>
        <td>CEP :</td>
		<td><input maxlength="45" name="FOR_NR_CEP" type="text" style=" width : 270px;" class="uppercase"></input></td>
        </tr> 
        
        <tr>
        <td>Contato :</td>
		<td><input maxlength="100" name="FOR_DS_CONTATO" type="text" style=" width : 270px;" class="uppercase"></input></td>
        </tr>         

        <tr>
        <td>Inscrição Estadual :</td>
		<td><input maxlength="45" name="FOR_NR_INSC_ESTADUAL" type="text" style=" width : 270px;" class="uppercase"></input></td>
        </tr>        

        <tr>
        <td>Inscrição Municipal :</td>
		<td><input maxlength="45" name="FOR_NR_INSC_MUNICIPAL" type="text" style=" width : 270px;" class="uppercase"></input></td>
        </tr>        


		<tr >
		<td></td>
		<td align="center" >
		<input type="button" id="id_commit" name="commit" onclick="this.form['commit'].disabled=true ;" value="  Cadastrar  ">
		</td>
		</tr>
				
		</table>
		<br><br>
			<a><img src="images/pages_icon.png"  hspace="10px" width="20px" alt="" align="left" /></a>* Para cadastrar uma conta a pagar ou receber é necessário cadastrar primeiro o fornecedor e o tipo de conta...
		<br><br>		
		<hr></hr>	

		</div>
</form>

<div align="center">
	<form id="live-search" action="" class="styled" method="post">
	    <fieldset>
	    	<h6>Busca dinâmica :
	        <input type="text" title="Escreva para buscar..." class="text-input" id="filter" value="" /></h6>
	        <span id="filter-count"></span>
	    </fieldset>
	</form>
</div>

			<table border="1" align="center"
				style="font-size: larger; font: bold;" cellpadding="8"
				cellspacing="5">

				<!-- <thead> -->
				<tr bgcolor="#006666">

					<th style="padding: 5px">Código</th>
	
					<th style="padding: 5px"><label>Descrição</th>
					
					<th style="padding: 5px"><label>CPF/CNPJ</th>
					
					<th style="padding: 5px"><label>Logradouro</th>
					
					<th style="padding: 5px"><label>Bairro</th>
					
					<th style="padding: 5px"><label>Cidade</th>
					
				</tr>

				<%! ArrayList<Fornecedor> arrayFornecedor; %>
				<%  arrayFornecedor = (ArrayList<Fornecedor>) request.getSession().getAttribute("arrayFornecedor"); %>
				<%  for(int i = 0; i<arrayFornecedor.size();i++){ %>

					<tr onmouseover="setAtributoIdCell(this)">

						<!--A tag out Ã© responsÃ¡vel por gerar uma String de saÃ­da na tela -->

						<td align="center"><fmt:formatNumber value="<%=arrayFornecedor.get(i).getFor_cd_fornecedor() %>" pattern="00000" /></td>

						<td><c:out value="<%=arrayFornecedor.get(i).getFor_ds_fornecedor() %>" /></td>

						<td><c:out value="<%=arrayFornecedor.get(i).getFor_nr_cnpj() %>" /></td>
						
						<td><c:out value="<%=arrayFornecedor.get(i).getFor_ds_logradouro() %>" /></td>

						<td><c:out value="<%=arrayFornecedor.get(i).getFor_ds_bairro() %>" /></td>
												
						<td><c:out value="<%=arrayFornecedor.get(i).getFor_ds_cidade() %>" /></td>
												
						<td align="center">
							<input type="button" title="Vizualizar dados do fornecedor" value="Ver/Editar" onclick="refreshPageEditar()" class="button_disable_click_class"/>	
							<input type="button" class="id_apagar" value="Apagar" "/>
						</td>
					
					</tr>

				<%}; %>
			</table>
<form name="hidden" id="id_hidden" method="post">
		<input type="text" name="name_fornecedor_hidden"
			id="id_fornecedor_hidden" style="visibility: hidden"></input>
</form>			
			<hr></hr>
						
		<%@include file="../../includes_Geral/footer.jsp"%>
		


</body>
</html>