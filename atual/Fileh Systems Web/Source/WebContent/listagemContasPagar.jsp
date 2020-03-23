<%@include file="includes_Geral/menu_cabecalho.jsp"%>
<%@include file="includes_Geral/campoPesquisa_Nao_Mostrar.jsp"%>
<%@include file="includes_Geral/dataCalendarFunction.jsp"%>



<script language="javascript" type="text/javascript">


	function setAtributoIdReceitaCell(ths){
		strValueHiddenText = "";

		//id 
		col = ths.cells[0];
		strValueHiddenText += col.firstChild.nodeValue;
		
		document.getElementById("id_conta_hidden").setAttribute("Value", strValueHiddenText);
	};


	function refreshPageApagar(){	
		$('.class_linha_tabela').css( "background-color" , '#579c98');	
		
		$('#id_p').text( 'Deseja realmente apagar a conta ' + $('#id_conta_hidden').val() + ' ?' );
		
		$( "#dialog-form-alerta" ).dialog( "open" );
	};	
	
	
</script>

<script>
$(document).ready(function() {
	
	//soma total e qtd no topo da pagina
	$("#id_label_total").text( 'Soma total : R$' + $('#id_soma_total').text() );
	$("#id_label_qtd").text( 'Quantidade : ' + ($('.main_table tr').length - 2) );
	
	$('.class_linha_tabela').css( "background-color" , '#579c98');
	
	var resp = '';
	
	function hexc(colorval) {
	    var parts = colorval.match(/^rgb\((\d+),\s*(\d+),\s*(\d+)\)$/);
	    delete(parts[0]);
	    for (var i = 1; i <= 3; ++i) {
	        parts[i] = parseInt(parts[i]).toString(16);
	        if (parts[i].length == 1) parts[i] = '0' + parts[i];
	    }
	    resp = '#' + parts.join('');
	};
	

	$('#id_limpar').on('click', function(){
        $( "#id_fl_situacao" ).val('T');
        $( "#datepicker" ).val('');
        $( "#datepicker2" ).val('');
        $( "#id_doc" ).val('');
        $( "#id_codigo_conta" ).val('0');
        $( "#id_fornecedor" ).val('-1');
        $( "#id_tipoConta" ).val('-1');
        chamaServlet('pesquisarContaPagar','ListagemContasPagar.action');
	});

	$('.class_linha_tabela').on('click', function(){

		var colorval = $(this).css('backgroundColor');

		hexc(colorval);
	    
		if (resp == '#579c98'){
			$( this ).css( "background-color" , '#825369');
		}else{
			$( this ).css( "background-color" , '#579c98');
		};

	});

	

	$('.selecionar_todas').on('click', function(){
		$('.class_linha_tabela').css( "background-color" , '#825369');
	});
	
	$('.selecionar_nenhuma').on('click', function(){
		$('.class_linha_tabela').css( "background-color" , '#579c98');
	});

	
	$('.marcar_nao_paga').on('click', function(){	
		   $('.hidden_selecionadas').val('');
		   $('.hidden_contas_selecionadas_fl').val('N');
		   
		   $('.class_linha_tabela').each(function(i){ 
            // Aplica a cor de fundo 
				var colorval = $(this).css('backgroundColor');

				hexc(colorval);
			    var separador = '';

				if (resp != '#579c98'){
					if ($('.hidden_selecionadas').val()!= '') {separador = ',';};
					$('.hidden_selecionadas').val(	$(this).find('td')[0].firstChild.nodeValue	+  separador 	+ $('.hidden_selecionadas').val()  	);
				};

        }); 
		   
		if ($('#id_contas_selecionadas_hidden').val() == '') {
			$( "#dialog-form-alerta-selecionar-marcar" ).dialog( "open" ); 
		}else{
			document.forms["hidden"].action = "MarcarContasPagar.action";
	    	document.forms["hidden"].submit();
		}; 
		   
	});  

		
	$('.marcar_paga').on('click', function(){
		   $('.hidden_selecionadas').val('');
		   $('.hidden_contas_selecionadas_fl').val('S');
		   
		   $('.class_linha_tabela').each(function(i){ 
               // Aplica a cor de fundo 
				var colorval = $(this).css('backgroundColor');

				hexc(colorval);
			    var separador = '';

				if (resp != '#579c98'){
					if ($('.hidden_selecionadas').val()!= '') {separador = ',';};
					$('.hidden_selecionadas').val(	$(this).find('td')[0].firstChild.nodeValue	+  separador 	+ $('.hidden_selecionadas').val()  	);
				};

           }); 
		   
			if ($('#id_contas_selecionadas_hidden').val() == '') {
				$( "#dialog-form-alerta-selecionar-marcar" ).dialog( "open" ); 
			}else{
				document.forms["hidden"].action = "MarcarContasPagar.action";
		    	document.forms["hidden"].submit();
			}; 
		   
	});   
	
	
	
    $( "#dialog-form-alerta" ).dialog({
        autoOpen: false,
        height: 160,
        width: 320,
        modal: true,
        buttons: {
          Sim: function() {
        	  jQuery('.ui-dialog button:nth-child(1)').button('disable');
        	  chamaServlet('hidden','ApagarContaPagar.action');
        	 $( this ).dialog( "close" ); 

          },Não: function() {
	        	 $( this ).dialog( "close" ); 
	      }
        }
      });
    
    
    $( "#dialog-form-alerta-selecionar-marcar" ).dialog({
        autoOpen: false,
        height: 160,
        width: 320,
        modal: true,
        buttons: {
          Ok: function() {
        	  
        	 $( this ).dialog( "close" ); 

          }
        }
      });

    
    //reajusta o modulo em que a pagina se refere...
    $("#id_menu_financeiro").addClass('selected');
    $( "#templatemo_menu" ).on('mouseout',(function( ){
    	$("#id_menu_financeiro").addClass('selected');
    })); 
    
   
   
  });
</script>

<div id="dialog-form-alerta" title="Alerta..." class="dialog-position-top">
	<p class="validateTips">
		<label id="id_p"></label>
	</p>
</div>

<div id="dialog-form-alerta-selecionar-marcar" title="Alerta..." class="dialog-position-top">
	<p class="validateTips">
		<label >Favor Selecionar uma ou mais contas a serem marcadas...</label>
	</p>
</div>

<body>

<form  id="Filtros" name="pesquisarContaPagar" action="" method="post" onsubmit="">


<div>	
		<h6></h6>
		<!-- Lista de contas a pagar Serdo -->
		<div>
	<h3 align="left">Contas a Pagar </h3>
		
		
		<h4 align="right"><label id="id_label_qtd"></label> </h4>
		
		<h4 align="right"><label id="id_label_total"></label></h4>
		
	</div>
	
		<tr align="center"> 

		<hr></hr>
		
			<td>
				<p style="font-size: 16px">Situação :  
				<input id="id_fl_situacao" name="fl_situacao" type="radio" <%=request.getSession().getAttribute("fl_situacao_todas").toString() %> value="T" ></input>Todas
				<input id="id_fl_situacao" name="fl_situacao" type="radio" <%=request.getSession().getAttribute("fl_situacao_pago").toString()%> value="S" ></input>Pago
				<input id="id_fl_situacao" name="fl_situacao" type="radio" <%=request.getSession().getAttribute("fl_situacao_naoPago").toString()%> value="N" ></input>Não pago</p>
			</td>
		
		
		<td> Data Inicio : <input title="Data de vencimento" readonly id="datepicker" value="<%=request.getSession().getAttribute("filtroDataInicio").toString()  %>"  name="filtroDataInicio" type="text" style=" width : 80px;" ></input></td>
		<td> Data Fim :<input title="Data de vencimento" readonly id="datepicker2" value="<%=request.getSession().getAttribute("filtroDataFim").toString()  %>"   name="filtroDataFim" type="text" style=" width : 80px;" ></input></td>
		
		
		
		<td> Documento : <input maxlength="45" id="id_doc" name="doc" value="<%=request.getSession().getAttribute("doc").toString()  %>" type="text" style=" width : 100px;" ></input></td>
		
		<td> Código : <input class="numberOnlyN" maxlength="45" id="id_codigo_conta"  name="codigo_conta" value="<%=request.getSession().getAttribute("codigo_conta").toString()  %>" type="text" style=" width : 100px;" ></input></td>
		
		<br></br>
		

        <td>Fornecedor :
        	<select id="id_fornecedor" name="fornecedor"  style=" width : 270px;" maxlength="45"> 
        		<option value="-1" selected="selected">Escolha um fornecedor...</option>
        		<%if (!request.getSession().getAttribute("msgFornecedorValue").toString().equalsIgnoreCase("-1")) {%> 
        			<option value="<%=request.getSession().getAttribute("msgFornecedorValue").toString()  %>" selected="selected"><%=request.getSession().getAttribute("msgFornecedor").toString() %></option>      
				<%}else{%>
					<script>$( "#id_fornecedor" ).val('-1');</script>
				<%}%>
				<%! ArrayList<String> arrayFornecedor; %>
				<%  arrayFornecedor = (ArrayList<String>) request.getSession().getAttribute("arrayFornecedor"); %>
				<%  for(int i = 0; i<arrayFornecedor.size();i++){ %>
    				<option value="<%=arrayFornecedor.get(i) %>"><%=arrayFornecedor.get(i) %></option>    
 				<%}; %>   
			</select>  
        </td>


        <td>Tipo de conta :
        	<select id="id_tipoConta" name="tipoConta"  style=" width : 270px;" maxlength="45">        		
        		<option value="-1" selected="selected">Escolha o tipo da conta...</option> 
        		<%if (!request.getSession().getAttribute("msgTipoContaValue").toString().equalsIgnoreCase("-1")) {%> 
        			<option value="<%=request.getSession().getAttribute("msgTipoContaValue").toString() %>" selected="selected"><%=request.getSession().getAttribute("msgTipoConta").toString() %></option>      
				<%}else{%>
					<script>$( "#id_tipoConta" ).val('-1');</script>
				<%}%>        					
				<%! ArrayList<String> arrayTipoConta; %>
				<%  arrayTipoConta = (ArrayList<String>) request.getSession().getAttribute("arrayTipoConta"); %>
				<%  for(int i = 0; i<arrayTipoConta.size();i++){ %>
    				<option value="<%=arrayTipoConta.get(i) %>"><%=arrayTipoConta.get(i) %></option>    
 				<%}; %>   
			</select>  
        </td>
		
			<td>
				<input  type="button" name="commit" onclick="this.form['commit'].disabled=true ;  chamaServlet('pesquisarContaPagar','ListagemContasPagar.action')" value="  Filtrar  "></input>
				<input  type="button" title="Limpar filtros aplicados..." id="id_limpar" value="  Limpar  "> </input>
				<a href="listagemContasPagarImprimir.jsp" target="_blank"> <input  type="button" value="  Imprimir  "> </input></a>
			</td>
		</tr>
		
</form>		

		<hr></hr>
       
			<table title="Para marcar mais de uma conta como paga ou não, clique nas celulas para seleciona-las..." border="1" align="center" class="main_table"
				style="font-size: larger; font: bold; " cellpadding="8"
				cellspacing="5"  id="templatemo_color_read">

				<!-- <thead> -->
				<tr bgcolor="#006666">

					<th >Código</th>
		
					<th style="padding: 5px">Fornecedor</th>
		
					<th style="padding: 5px"><label>Doc</th>
					
					<th style="padding: 5px"><label>Tipo Conta</th>
					
					<th style="padding: 5px"><label>Descrição</th>
					
					<th style="padding: 5px">Venc.</th>
		
					<th style="padding: 5px">Valor</th>
		
					<th style="padding: 5px">Parc.</th>
					
					<th style="padding: 5px">Pago</th>

				</tr>


				<c:set var="total" value="0" /> 
				

				<%! ArrayList<ContaPagar> arrayContasPagar; %>
				<%  arrayContasPagar = (ArrayList<ContaPagar>) request.getSession().getAttribute("arrayContasPagar"); %>
				<%  for(int i = 0; i<arrayContasPagar.size();i++){ %>
						
				
					<tr onmouseover="setAtributoIdReceitaCell(this)" class="class_linha_tabela">
						
						<td><c:out value="<%= arrayContasPagar.get(i).getCop_cd_conta_pagar() %>" /></td>

						<td align="center"><c:out value="<%= arrayContasPagar.get(i).getCop_ds_fornecedor() %>" /></td>

						<td><c:out value="<%= arrayContasPagar.get(i).getCop_nr_documento() %>" /></td>
						
						<td><c:out value="<%= arrayContasPagar.get(i).getCop_ds_tipo_conta() %>" /></td>
						
						<td><c:out value="<%= arrayContasPagar.get(i).getCop_ds_conta_pagar() %>" /></td>

						<td align="center"><c:out value="<%= arrayContasPagar.get(i).getCop_dt_vencimento() %>" /></td>

						<td align="center"><fmt:formatNumber value="<%= arrayContasPagar.get(i).getCop_vl_conta_pagar() %>"  pattern="############,##0.00" /></td>

						<td align="center"><c:out value="<%= arrayContasPagar.get(i).getCop_nr_parcela() %>" /></td>


						<c:set var="fl" scope="session" value="<%= arrayContasPagar.get(i).getCop_fl_pago() %>" />
						<c:choose>
							<c:when test="${fl == 'S'}">
								<td align="center" style="color: blue;">Sim</td>
							</c:when>
							<c:otherwise>
								<td align="center" style="color: red;">Não</td>
							</c:otherwise>
						</c:choose>



						<td align="justify">
							<input type="button" value="Editar/Ver" onclick="chamaServlet('hidden','EditarContaPagar.action')" class="button_disable_click_class"/>	
						</td>
						<td align="justify">						
							<input type="button" value="Apagar" id="id_apagar_button" onclick="refreshPageApagar()"/>
						</td>
						
					</tr>
					<c:set var="vl_pagar" scope="session" value="<%= arrayContasPagar.get(i).getCop_vl_conta_pagar() %>" />
					<c:set var="total" value="${total + vl_pagar}" />  
				<%}; %>
				
				
				<tr><td style="visibility: hidden;"></td> <td style="visibility: hidden;"></td>   <td style="visibility: hidden;"></td>  <td style="visibility: hidden;"></td> <td style="visibility: hidden;"></td>
				<td align="center">Total </td><td id="id_soma_total"> <fmt:formatNumber value="${total}" pattern="############,##0.00"/> </td></tr>
				
			</table>
			
			<div align="center">
				<input type="button" value="Selecionar todas" class="selecionar_todas"/>
				<input type="button" value="Limpar seleção" class="selecionar_nenhuma"/>
				<input type="button" value="Marcar como Não paga(s)" class="marcar_nao_paga" />
				<input type="button" value="Marcar como paga(s)" class="marcar_paga" />
			</div>
			<hr></hr>
	</div>
	<form name="hidden" method="post">
	
		<input type="text" name="conta_hidden"
			id="id_conta_hidden" style="visibility: hidden"></input>
			
		<input type="text" name="contas_selecionadas_hidden"
			id="id_contas_selecionadas_hidden" class="hidden_selecionadas" style="visibility: hidden"></input>	
				
		<input type="text" name="contas_selecionadas_fl_hidden"
			id="id_contas_selecionadas_fl_hidden" class="hidden_contas_selecionadas_fl" style="visibility: hidden"></input>		
						
	</form>
	<%@include file="includes_Geral/footer.jsp"%>

</body>
</html>