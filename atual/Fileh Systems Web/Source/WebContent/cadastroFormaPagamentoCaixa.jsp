<%@include file="includes_Geral/campoPesquisa_Nao_Mostrar.jsp"%> 
<%@include file="includes_Geral/menu_cabecalho.jsp"%>


<script type="text/javascript">

	function setAtributoIdCell(ths){
		strValueHiddenText = "";

		//id cliente
		col = ths.cells[0];
		strValueHiddenText += col.firstChild.nodeValue;
		
		document.getElementById("id_forma_pagamento_hidden").setAttribute("Value", strValueHiddenText);
	};

	
	function refreshPageEditar(){		

		document.forms["hidden"].action = "EditarFormaPagamento.action";
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
	        	  $( "#ID_FOP_DS_FORMA_PAGAMENTO" ).css("background-color", 'IndianRed');
	        	 $( this ).dialog( "close" ); 

	          }
	        }
	      });
	  
	  
	    $( "#id_commit" ).on('click',(function( ){

			if ($( "#ID_FOP_DS_FORMA_PAGAMENTO" ).val() == ''	){
				$( "#dialog-form-alerta" ).dialog( "open" );
			}else{
				document.forms['cadastroFormaPagamento'].submit();
			};  	  	    
			
  	    }));
    
	    
	    
	    $( "#dialog-form-alerta-apagar" ).dialog({
	        autoOpen: false,
	        height: 160,
	        width: 320,
	        modal: true,
	        buttons: {
	          Sim: function() {
	        	  
	        	  chamaServlet('hidden','ApagarFormaPagamento.action');
	        	 $( this ).dialog( "close" ); 

	          },Não: function() {
		        	 $( this ).dialog( "close" ); 
		      }
	        }
	      });
	  
	  
	    $( ".id_apagar" ).on('click',(function( ){
	    	$('#id_p').text( 'Deseja realmente apagar a forma de pagamento ' + $('#id_forma_pagamento_hidden').val() + ' ?' );
			$( "#dialog-form-alerta-apagar" ).dialog( "open" );
			
  	    }));
	    
	    
	    
	    $("#ID_FOP_DS_FORMA_PAGAMENTO").bind("keypress", function(e) {
	        if (e.keyCode == 13) {
	           return false;
	        }
	     });
	    
	    
	    
	    
	    //reajusta o modulo em que a pagina se refere...
	    $("#id_menu_caixa").addClass('selected');
	    $( "#templatemo_menu" ).on('mouseout',(function( ){
	    	$("#id_menu_caixa").addClass('selected');
	    }));
	    
  });


</script>


<body onload="FocusInput('ID_FOP_DS_FORMA_PAGAMENTO')">

 <div id="dialog-form-alerta" title="Alerta..." class="dialog-position-top">
  <p class="validateTips">Favor preencher todos os campos necessários...</p>
</div>

 <div id="dialog-form-alerta-apagar" title="Alerta..." class="dialog-position-top">
	<p class="validateTips">
		<label id="id_p"></label>
	</p>
</div>

<form  name="cadastroFormaPagamento" action="CadastrarFormaPagamento.action" method="post"> 
       
		<div >
		<h6></h6> 			
   <!-- Lista de Produtos Serdo -->
   <h3>Cadastro de Tipo de Pagamento/Saída </h3>

        <hr></hr>

        <br><br>
        
		<input value="CX" id="ID_FOP_FL_TIPO" name="FOP_FL_TIPO" type="text" style="visibility: hidden;"></input>
        
        <table align="center" cellpadding="5" cellspacing="5"  style="font-size: larger;" >
        <tr>
        <td>Descrição :</td>
		<td><input maxlength="45" id="ID_FOP_DS_FORMA_PAGAMENTO" name="FOP_DS_FORMA_PAGAMENTO" type="text" style=" width : 270px;"class="uppercase"></input></td>
        </tr>
        
        
		
		<tr >
		<td></td>
		<td align="center" >
		<input type="button" id="id_commit" name="commit" onclick="this.form['commit'].disabled=true ;" value="  Cadastrar  ">
		</td>
		</tr>
				
		</table>
		<br><br>
		<a><img src="images/pages_icon.png" hspace="10px"  width="20px" alt="" align="left" /></a>* O tipo de pagamento/saída é um tipo de entrada ou saída para o fluxo de caixa...
		<br><br>
		<a><img src="images/pages_icon.png" hspace="10px"  width="20px" alt="" align="left" /></a>* Exemplos de tipo de pagamento/saída: Cheque, Cartão 1x, Cartão 2x, Dinheiro, Saída - Conta Energia, Saída - Retirada...
		<br><br>
		<hr></hr>	

		</div>
</form>


<div align="center">
	<form id="live-search" action="" class="styled" method="post">
	    <fieldset>
	    	<h6>Busca dinâmica :
	        <input title="Escreva para buscar..."  type="text" class="text-input" id="filter" value="" /></h6>
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
			</tr>

				<%! ArrayList<FormaPagamento> arrayFormaPagamento; %>
				<%  arrayFormaPagamento = (ArrayList<FormaPagamento>) request.getSession().getAttribute("arrayFormaPagamento"); %>
				<%  for(int i = 0; i<arrayFormaPagamento.size();i++){ %>

					<tr onmouseover="setAtributoIdCell(this)">

						<!--A tag out Ã© responsÃ¡vel por gerar uma String de saÃ­da na tela -->

						<td align="center"><fmt:formatNumber value="<%=arrayFormaPagamento.get(i).getFop_cd_forma_pagamento() %>" pattern="00000" /></td>

						<td><c:out value="<%=arrayFormaPagamento.get(i).getFop_ds_forma_pagamento() %>" /></td>

						<td align="center">
							<input type="button" value="Editar" onclick="refreshPageEditar()" class="button_disable_click_class"/>	
							<input type="button" class="id_apagar" value="Apagar" />
						</td>
					
					</tr>

				<%}; %>  
			</table>
<form name="hidden" id="id_hidden" method="post">
		<input type="text" name="name_forma_pagamento_hidden"
			id="id_forma_pagamento_hidden" style="visibility: hidden"></input>
</form>			
			<hr></hr>
						
		<%@include file="includes_Geral/footer.jsp"%>
		


</body>
</html>