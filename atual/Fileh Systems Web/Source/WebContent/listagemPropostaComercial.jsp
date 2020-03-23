<%@include file="includes_Geral/menu_cabecalho.jsp"%>
<%@include file="includes_Geral/campoPesquisa_Nao_Mostrar.jsp"%> 
<%@include file="includes_Geral/dataCalendarFunction.jsp"%>


<script language="javascript" type="text/javascript">


	function setAtributoIdReceitaCell(ths){
		strValueHiddenText = "";

		//id 
		col = ths.cells[0];
		strValueHiddenText += col.firstChild.nodeValue;
		
		document.getElementById("id_proposta_hidden").setAttribute("Value", strValueHiddenText);
	};
	
	
	function checkBoxMudar()
	{
		
		if (document.filtros_propostas.checkbox_aux_situacao_null.checked == true){
			document.filtros_propostas.check_situacao_null.value = '';
		}else{			
			document.filtros_propostas.check_situacao_null.value = 'S';
		}

	};
				
</script>

<script>
$(document).ready(function() {
	
    $( "#dialog-form-alerta" ).dialog({
        autoOpen: false,
        height: 160,
        width: 320,
        modal: true,
        buttons: {
          Sim: function() {
        	  
        	  chamaServlet('hidden','ApagarPropostaComercial.action');

          },Não: function() {
	        	 $( this ).dialog( "close" ); 
	      }
        }
      });
	
	
	$('#id_limpar').on('click', function(){
		
		$( "#datepicker" ).val('');
		$( "#datepicker2" ).val('');
		$( "#id_codigo_cliente" ).val('');
		$( "#id_codigo" ).val('');
		$( "#id_situacao_proposta" ).val('');
		$( "#id_check_situacao_null" ).val('');
		$( "#id_entidade" ).val('-1');
		chamaServlet('filtros_propostas','ListagemPropostasCliente.action');

	});
	
	$('#id_commit').on('click', function(){
		var valor = $('#id_codigo_cliente').val();
		if( valor == 0){
			$('#id_codigo_cliente').val('');
		};
		
		valor = $('#id_codigo').val();
		if( valor == 0){
			$('#id_codigo').val('');
		};
		
        if (	($( "#datepicker" ).val() != '')  || ($( "#datepicker2" ).val() != '')  ||  ($( "#id_codigo_cliente" ).val() != '') || 	($( "#id_codigo" ).val()!= '')){
        	$('#id_commit').prop('disabled', true);
        	chamaServlet('filtros_propostas','ListagemPropostasCliente.action');
        };
	});
	


	$('.editar_proposta').on('click', function(){
		$(this).prop('disabled',true);
		chamaServlet('hidden','EditarPropostaComercial.action');
	});
   
	$('.id_apagar').on('click', function(){
		$('#id_p').text( 'Deseja realmente apagar a proposta ' + $('#id_proposta_hidden').val() + ' ?' );
		$( "#dialog-form-alerta" ).dialog( "open" );
	});	
	
	
    $("#id_filtros_propostas_form").bind("keypress", function(e) {
        if (e.keyCode == 13) {
        	$( "#id_commit" ).click();
        }
     });
	
    //reajusta o modulo em que a pagina se refere...
    $("#id_menu_vendas").addClass('selected');
    $( "#templatemo_menu" ).on('mouseout',(function( ){
    	$("#id_menu_vendas").addClass('selected');
    })); 
    
  });
</script>


<div id="dialog-form-alerta" title="Alerta..." class="dialog-position-top">
	<p class="validateTips">
		<label id="id_p"></label>
	</p>
</div>
<body>

<div>	
		<h6></h6>
				
		<!-- Verifica se veio da tela de cliente para listar propostas de um determinado cliente. -->
		<%!Cliente cli = null; %>
		<%if((request.getSession().getAttribute("obj_cliente_proposta") != null)){  
			cli = (Cliente) request.getSession().getAttribute("obj_cliente_proposta");	
		  };  
		  request.getSession().setAttribute("obj_cliente_proposta",null);
		%>
		
		
		<tr align="center"> 
		
		<td><h3>Proposta Comercial <%if(cli != null){%> - <%=cli.getCli_nm_cliente()%> <%}%></h3></td>


<form name="filtros_propostas" id="id_filtros_propostas_form"  action="" method="post" >
		
		<%if(cli == null){  %>
				<hr></hr>		
		Código Proposta: <input id="id_codigo" 		value="<%= request.getSession().getAttribute("codigo").toString() %>"  			name="codigo" type="text" style=" width : 80px;" class="numberOnlyN"></input>
		Código Cliente:  <input id="id_codigo_cliente" value="<%= request.getSession().getAttribute("codigo_cliente").toString() %>"  	name="codigo_cliente" type="text" style=" width : 80px;" class="numberOnlyN"></input>
		
		
		Data Cadastro :  <input readonly id="datepicker" 	value="<%= request.getSession().getAttribute("dataCadastro").toString() %>"   	name="dataCadastro" type="text" style=" width : 80px;" ></input>
		Data Prevista :  <input readonly id="datepicker2" 	value="<%= request.getSession().getAttribute("dataPrevista").toString() %>"   	name="dataPrevista" type="text" style=" width : 80px;" ></input>
		
		<br><br>
		
		
       Entidade : <select id="id_entidade" name="entidade"		style="width: 285px;" >
			<option value="-1" selected="selected">Escolha uma entidade...</option>
       		<%if (!request.getSession().getAttribute("entidade_listagem").toString().equalsIgnoreCase("-1")) {%> 
       			<option value="<%=request.getSession().getAttribute("entidade_listagem").toString()  %>" selected="selected"><%=request.getSession().getAttribute("msgEntidade").toString() %></option>      
			<%}else{%>
				<script>$( "#id_entidade" ).val('-1');</script>
			<%}%>
			<%! ArrayList<String> arrayEntidades; %>
			<%  arrayEntidades = (ArrayList<String>) request.getSession().getAttribute("arrayEntidades"); %>
			<%  for(int i = 0; i<arrayEntidades.size();i++){ %>
				<option value="<%=arrayEntidades.get(i) %>"><%=arrayEntidades.get(i) %></option>
			<%}; %>
			</select>
		
		
		Situação : <select title="Escolha uma situação e mais alguma opção para filtrar..." id="id_situacao_proposta" name="situacaoProposta"		style="width: 285px;" >
				<option value="" selected="selected">Escolha uma situação...</option>
        		<%if (!request.getSession().getAttribute("situacaoProposta").toString().equalsIgnoreCase("")) {%> 
        			<option value="<%=request.getSession().getAttribute("situacaoProposta").toString()  %>" selected="selected"><%=request.getSession().getAttribute("situacaoProposta").toString() %></option>      
				<%}else{%>
					<script>$( "#id_situacao_proposta" ).val('');</script>
				<%}%>
				<%! ArrayList<String> arraySituacaoProposta; %>
				<%  arraySituacaoProposta = (ArrayList<String>) request.getSession().getAttribute("listNomesSituacao"); %>
				<%  for(int i = 0; i<arraySituacaoProposta.size();i++){ %>
					<option value="<%=arraySituacaoProposta.get(i) %>"><%=arraySituacaoProposta.get(i) %></option>
				<%}; %>
		</select>
		
		<input  name="checkbox_aux_situacao_null" onmousedown="checkBoxMudar()" title="Trazer na consulta as propostas que ainda não possuem situação..." value="S" type="checkbox" <%= request.getSession().getAttribute("check_situacao_null").toString() %> ></input>Sem situação 
		

		<br>		
		
		<div align="right">
				<input  id="id_check_situacao_null" name="check_situacao_null" style="visibility: hidden" type="text" value="<%= request.getSession().getAttribute("check_situacao_null").toString() %>" ></input>	
				<input  type="button" id="id_commit" name="commit" value="  Filtrar  "></input>
				<input title="Limpar filtros aplicados..."   type="button" id="id_limpar" value="  Limpar  "> </input>
			
		</div>
		<%};
		  //garante no caso de carregar a pagina em outro momento não vá carregar esse cliente.	
		  cli = null;
		%>	
</form>		

		<hr></hr>

			<table border="1" align="center"
				style="font-size: larger; font: bold; " cellpadding="8"
				cellspacing="5"  id="templatemo_color_read">

				<!-- <thead> -->
			<tr bgcolor="#006666">				

				<th style="padding: 5px">Código</th>

				<th style="padding: 5px">Cliente</th>

				<th style="padding: 5px"><label>Cadastro</th>
				
				<th style="padding: 5px"><label>Data Prevista</th>
				
				<th style="padding: 5px"><label>Situação</th>
				
				<th style="padding: 5px"><label>Frete</th>
				
				<th style="padding: 5px">Total</th>

				<!-- <th style="padding: 5px">Entidade</th> -->

				<!-- <th style="padding: 5px">Editar/Apagar</th> -->
			</tr>

				<!--</thead> -->
				<!--forEach, implementa um laÃ§o  para fazer a interaÃ§Ã£o no ResultSet  gerado pela tag query  conforme o atributo items.-->
				<c:set var="total" value="0" /> 
				
		<%! ArrayList<PropostaComercial> arrayPropostas; %>
		<%! ArrayList<String> arrayClientesNames; %>
		<%! ArrayList<Double> arrayTotalProposta; %>
		
		<%  arrayPropostas = (ArrayList<PropostaComercial>) request.getSession().getAttribute("listPropostas"); %>
		<%  arrayTotalProposta = (ArrayList<Double>) request.getSession().getAttribute("listTotalProposta"); %>
		<%  arrayClientesNames = (ArrayList<String>) request.getSession().getAttribute("listClientesNames"); %>
		
		<%  for(int i = 0; i<arrayPropostas.size();i++){ %>
				
				
					<tr onmouseover="setAtributoIdReceitaCell(this)">

						<!--A tag out Ã© responsÃ¡vel por gerar uma String de saÃ­da na tela -->
						
						<td align="center"><c:out value="<%= arrayPropostas.get(i).getPrc_cd_proposta_comercial().toString() %>" /></td>

						<td align="center"><c:out value="<%= arrayClientesNames.get(i) %>" /></td>

						<td><c:out value="<%= arrayPropostas.get(i).getPrc_dt_cadastro() %>" /></td>
						
						<td><c:out value="<%= arrayPropostas.get(i).getPrc_dt_prevista() %>" /></td>
						
						<td><c:out value="<%= arrayPropostas.get(i).getPrc_ds_situacao() %>" /></td>

						<td align="center"><fmt:formatNumber value="<%= arrayPropostas.get(i).getPrc_vl_frete().toString() %>"  pattern="############,##0.00" /></td>

						<td align="center"><fmt:formatNumber value="<%= arrayTotalProposta.get(i).toString() %>"  pattern="############,##0.00" /></td>


						<td align="justify">
							<input title="Visualizar dados da proposta..." type="button" value="Editar/Ver" class="editar_proposta" />	
						</td>
						<td align="justify">						
							<input type="button" value="Apagar" class="id_apagar"/>
						</td>
						
					</tr>
					<c:set var="vl_pagar" scope="session" value="<%= arrayTotalProposta.get(i).toString() %>" />
					<c:set var="total" value="${total + vl_pagar}" />  
		<%}; %>
				<tr> <td style="visibility: hidden;"></td>  <td style="visibility: hidden;"></td> <td style="visibility: hidden;"></td>  <td style="visibility: hidden;"></td>  <td style="visibility: hidden;"></td> 
				<td align="center">Total </td><td> <fmt:formatNumber value="${total}" pattern="############,##0.00"/> </td></tr>
			</table>
			<hr></hr>
	</div>
	<form name="hidden" id="id_hidden" method="post">
		<input type="text" name="id_name_proposta_hidden"
			id="id_proposta_hidden" style="visibility: hidden"></input>
	</form>
	<%@include file="includes_Geral/footer.jsp"%>
</body>
</html>