<%@include file="includes_Geral/campoPesquisa_Nao_Mostrar.jsp"%> 
<%@include file="includes_Geral/menu_cabecalho.jsp"%>


<script type="text/javascript">

	function setAtributoIdCell(ths){
		strValueHiddenText = "";

		//id cliente
		col = ths.cells[0];
		strValueHiddenText += col.firstChild.nodeValue;
		
		document.getElementById("id_tipo_conta_hidden").setAttribute("Value", strValueHiddenText);
	};

	
	function refreshPageEditar(){		

		document.forms["hidden"].action = "EditarTipoConta.action";
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
	        	  $( "#ID_TCO_DS_TIPO_CONTA" ).css("background-color", 'IndianRed');
	        	 $( this ).dialog( "close" ); 

	          }
	        }
	      });
	  
	  
	    $( "#id_commit" ).on('click',(function( ){

			if ($( "#ID_TCO_DS_TIPO_CONTA" ).val() == ''	){
				$( "#dialog-form-alerta" ).dialog( "open" );
			}else{
				document.forms['cadastroTipoConta'].submit();
			};  	  	    
			
  	    }));
    
	    
	    
	    $( "#dialog-form-alerta-apagar" ).dialog({
	        autoOpen: false,
	        height: 160,
	        width: 360,
	        modal: true,
	        buttons: {
	          Sim: function() {
	        	  
	        	  chamaServlet('hidden','ApagarTipoConta.action');
	        	 $( this ).dialog( "close" ); 

	          },Não: function() {
		        	 $( this ).dialog( "close" ); 
		      }
	        }
	      });
	  
	  
	    $( ".id_apagar" ).on('click',(function( ){
	    	$('#id_p').text( 'Deseja realmente apagar o Tipo de Conta ' + $('#id_tipo_conta_hidden').val() + ' ?' );
			$( "#dialog-form-alerta-apagar" ).dialog( "open" );
			
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


<body onload="FocusInput('ID_TCO_DS_TIPO_CONTA')">

 <div id="dialog-form-alerta" title="Alerta..." class="dialog-position-top">
  <p class="validateTips">Favor preencher todos os campos necessários...</p>
</div>

 <div id="dialog-form-alerta-apagar" title="Alerta..." class="dialog-position-top">
	<p class="validateTips">
		<label id="id_p"></label>
	</p>
</div>

<form  name="cadastroTipoConta" action="CadastrarTipoConta.action" method="post"> 
       
		<div >
		<h6></h6> 			
   <!-- Lista de Produtos Serdo -->
   <h3>Cadastro Tipos de Conta</h3>

        <hr></hr>

        <!-- CADASTRO DE Tipos de contas, tais como cheque, duplicata e etc... -->
        
 
        
        <table align="center" cellpadding="5" cellspacing="5"  style="font-size: larger;" >
        <tr>
        <td>Descrição :</td>
		<td><input title="Exemplo: Cheque, Boleto, Energia, Gastos Internos..." maxlength="45" id="ID_TCO_DS_TIPO_CONTA" name="TCO_DS_TIPO_CONTA" type="text" style=" width : 270px;"class="uppercase"></input></td>
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
			</tr>

				<%! ArrayList<TipoConta> arrayTipoConta; %>
				<%  arrayTipoConta = (ArrayList<TipoConta>) request.getSession().getAttribute("arrayTipoConta"); %>
				<%  for(int i = 0; i<arrayTipoConta.size();i++){ %>

					<tr onmouseover="setAtributoIdCell(this)">

						<!--A tag out Ã© responsÃ¡vel por gerar uma String de saÃ­da na tela -->

						<td align="center"><fmt:formatNumber value="<%=arrayTipoConta.get(i).getTco_cd_tipo_conta() %>" pattern="00000" /></td>

						<td><c:out value="<%=arrayTipoConta.get(i).getTco_ds_tipo_conta() %>" /></td>

						<td align="center">
							<input type="button"  value="Editar" onclick="refreshPageEditar()" class="button_disable_click_class"/>	
							<input type="button" class="id_apagar" value="Apagar" />
						</td>
					
					</tr>

				<%}; %>  
			</table>
<form name="hidden" id="id_hidden" method="post">
		<input type="text" name="name_tipo_conta_hidden"
			id="id_tipo_conta_hidden" style="visibility: hidden"></input>
</form>			
			<hr></hr>
						
		<%@include file="includes_Geral/footer.jsp"%>
		


</body>
</html>