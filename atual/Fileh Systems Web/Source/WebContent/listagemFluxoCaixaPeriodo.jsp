<%@page import="dao.MetodosComumDAO"%>
<%@include file="../../includes_Geral/campoPesquisa_Nao_Mostrar.jsp"%> 
<%@include file="../../includes_Geral/menu_cabecalho.jsp"%>
<%@include file="includes_Geral/dataCalendarFunction.jsp"%>

<%@page import="dao.FluxoCaixaDAO"%>

<script type="text/javascript">


	function setAtributoIdCell(ths){
		strValueHiddenText = "";

		//id 
		col = ths.cells[0];
		strValueHiddenText += col.firstChild.nodeValue;
		
		document.getElementById("id_fluxo_caixa_hidden").setAttribute("Value", strValueHiddenText);
			
		
		col = ths.cells[2];
		strValueHiddenText = col.firstChild.nodeValue;
		$('#id_p').text( strValueHiddenText);
		
	};


	function refreshPageEditar(){	
		document.forms["hidden"].action = "EditarFluxoCaixa.action";
        document.forms["hidden"].submit();
	};
	

	function FocusInput(id){		
		document.getElementById(id).focus();
	};	
		
</script> 

 <script>

  
  $(document).ready(function() {
	  
	  //atualiza diferenca total
	  $('#id_h3_diferenca').text('R$'+$('#id_soma_total').text());
	  
	//atualiza data cadastro
	//----------------------------
	if($('#datepicker').val() == ""){
	
	  	var d = new Date();
	  	
	  	var mes = (d.getMonth()+1);
	  	if ( mes < 10 ) {mes = '0'+mes};
	  	
	  	var dia = (d.getDate());
	  	if ( dia < 10 ) {dia = '0'+dia};
	  	
	  	$('#id_flu_dt_cadastro').val(dia+'/'+mes+'/'+d.getFullYear());
	}
  	//----------------------------
  	

	    $( "#dialog-form-alerta" ).dialog({
	        autoOpen: false,
	        height: 160,
	        width: 320,
	        modal: true,
	        buttons: {
	          OK: function() {
	        	  $( "#id_commit" ).attr("disabled", false);
	        	  $( "#datepicker" ).css("background-color", 'IndianRed');
	        	  $( "#datepicker2" ).css("background-color", 'IndianRed');
	        	  $( "#id_flu_cd_entidade" ).css("background-color", 'IndianRed');
	        	 $( this ).dialog( "close" ); 

	          }
	        }
	      });
	  
	  
	    $( "#id_commit" ).on('click',(function( ){

			if (($( "#datepicker" ).val() == '') || ($( "#datepicker2" ).val() == '') || ($( "#id_flu_cd_entidade" ).val() == '-1') 	){
				$( "#dialog-form-alerta" ).dialog( "open" );
			}else{
				document.forms['listagemFluxoCaixaPeriodo'].submit();
			};  	  	    
			
  	    }));
    
	    
	    
	    $( "#dialog-form-alerta-apagar" ).dialog({
	        autoOpen: false,
	        height: 180,
	        width: 360,
	        modal: true,
	        buttons: {
	          Sim: function() {
	        	  chamaServlet('hidden','ApagarFluxoCaixa.action');
	        	 $( this ).dialog( "close" ); 

	          },Não: function() {
		        	 $( this ).dialog( "close" ); 
		      }
	        }
	      });
	  
	  
	    $( ".id_apagar" ).on('click',(function( ){
	    	//$('#id_p').text( $('#id_fluxo_caixa_hidden').val());
			$( "#dialog-form-alerta-apagar" ).dialog( "open" );
			
  	    }));
	    
	    
	    $( "#dialog-form-editar-usuario" ).dialog({
	        autoOpen: false,
	        height: 510,
	        width: 320,
	        modal: true,
	        buttons: {
	          Confirmar: function() {
	        	jQuery('.ui-dialog button:nth-child(1)').button('disable');  
	        	
	        	  
	            if( ($("#id_flu_nm_fluxo_caixa_edt").val() == '') || ($("#id_flu_ds_login_edt").val() == '') ){
		            $('#id_label_msg_editar_usuario').text('Favor preencher todos os campos necessários...');
		            $( "#id_flu_nm_fluxo_caixa_edt" ).css("background-color", 'IndianRed');
		        	$( "#id_flu_ds_login_edt" ).css("background-color", 'IndianRed');
	            }else{
					chamaServlet('Usuario_edt','ConfirmaEditarFluxoCaixa.action');
	            }
	            
	          },          
	          Cancelar: function() {	        	  
	          	$( this ).dialog( "close" ); 
	          }
	        }
	      });
	    
	    
	    $( ".editar_usuario_button_class" ).on('click',(function( ){
	    	$('#id_flu_cd_fluxo_caixa').val($('#id_fluxo_caixa_hidden').val());
	    	$('#id_label_msg_editar_usuario').text('');
			$("#dialog-form-editar-usuario" ).dialog( "open" );
			
  	    }));
	    
	    //reajusta o modulo em que a pagina se refere...
	    $("#id_menu_caixa").addClass('selected');
	    $( "#templatemo_menu" ).on('mouseout',(function( ){
	    	$("#id_menu_caixa").addClass('selected');
	    })); 
	    

  });


  </script>



<body onload="FocusInput('id_flu_ds_fluxo_caixa')" id="id_body">


 <div id="dialog-form-alerta" title="Alerta..." class="dialog-position-top">
  <p class="validateTips">Favor preencher todos os campos necessários...</p>
</div>




       
		<div >
		<h6></h6> 			
<div>
   	<div align="right">
   		<h3 id="id_h3_diferenca">----</h3>
   	</div>
   	
	<div align="left">
   		<h3>Relatório Sintético - Fluxo por período </h3>
   	</div>
</div>
		<%! String msg_data;  %>
		<% if (request.getSession().getAttribute("data_escolhida_fluxo_caixa1") != null){ msg_data = request.getSession().getAttribute("data_escolhida_fluxo_caixa1").toString();}else{msg_data = "";}; %>
		<%! String msg_data2;  %>
		<% if (request.getSession().getAttribute("data_escolhida_fluxo_caixa2") != null){ msg_data2 = request.getSession().getAttribute("data_escolhida_fluxo_caixa2").toString();}else{msg_data2 = "";}; %>
		
		<form name="listagemFluxoCaixaPeriodo" action="ListagemFluxoCaixaPeriodo.action" method="post">
		<div align="left">
		    
		    Data inicio:
			<input readonly maxlength="45" value="<%= msg_data %>" title="Clique para escolher uma data..."  id="datepicker" name="flu_dt_cadastro" type="text" style=" width : 80px;"></input>
			
			Data fim:
			<input readonly maxlength="45" value="<%= msg_data2 %>" title="Clique para escolher uma data..."  id="datepicker2" name="flu_dt_cadastro2" type="text" style=" width : 80px;"></input>
		    
		    <td> Descrição : <input maxlength="100" id="id_descricao" name="descricao" value="<%=request.getSession().getAttribute("descricao").toString()  %>" type="text" style=" width : 200px;" ></input></td>
		    
		    <br><br>
		</div>
		<div align="right">
		    Pagamento/Saída :
			<select title="Forma de pagamento/saída" id="id_flu_ds_forma_pagamento" name="flu_ds_forma_pagamento"		style="width: 295px;" >
					<option value="-1" selected="selected">Escolha uma forma de pagamento/saída...</option>
					<%if (!request.getSession().getAttribute("forma_pagamento_fluxo_caixa").toString().equalsIgnoreCase("-1")) {%> 
			       		<%if (!request.getSession().getAttribute("forma_pagamento_fluxo_caixa").toString().equalsIgnoreCase("-1")) {%> 
			       			<option value="<%=request.getSession().getAttribute("forma_pagamento_fluxo_caixa").toString()  %>" selected="selected"><%=request.getSession().getAttribute("forma_pagamento_fluxo_caixa_msg").toString() %></option>      
						<%}else{%>
							<script>$( "#id_flu_ds_forma_pagamento" ).val('-1');</script>
						<%};%>
					<%};%>
					
					<%! ArrayList<FormaPagamento> arrayFormaPagamento; %>
					<%  arrayFormaPagamento = (ArrayList<FormaPagamento>) request.getSession().getAttribute("arrayFormaPagamentoFluxoCaixa"); %>
					<%  for(int i = 0; i<arrayFormaPagamento.size();i++){ %>
						<option value="<%=arrayFormaPagamento.get(i).getFop_cd_forma_pagamento() %>"><%=arrayFormaPagamento.get(i).getFop_ds_forma_pagamento() %></option>
					<%}; %>
			</select>				
			
			Entidade:
			<select title="Escolha a entidade referente ao fluxo de caixa" name="flu_cd_entidade" id="id_flu_cd_entidade"  style=" width : 295px;" maxlength="45"> 
					<option value="-1" selected="selected">Escolha uma entidade...</option>
	        		<%if (!request.getSession().getAttribute("entidade_fluxo_caixa").toString().equalsIgnoreCase("-1")) {%> 
	        			<option value="<%=request.getSession().getAttribute("entidade_fluxo_caixa").toString()  %>" selected="selected"><%=request.getSession().getAttribute("entidade_fluxo_caixa_msg").toString() %></option>      
					<%}else{%>
						<script>$( "#id_entidade" ).val('-1');</script>
					<%}%>
				<%! ArrayList<Entidade> arrayEntidades; %>
				<%  arrayEntidades = (ArrayList<Entidade>) request.getSession().getAttribute("arrayEntidadesFluxoCaixa"); %>
				<%  for(int i = 0; i<arrayEntidades.size();i++){ %>
	   				<option value="<%=arrayEntidades.get(i).getEnt_cd_entidade() %>"><%=arrayEntidades.get(i).getEnt_nm_entidade() %></option>    
					<%}; %>    
			</select>
			
			<input type="button" id="id_commit" name="commit" onclick="this.form['commit'].disabled=true ;" value="  Filtrar  ">		
		</div>
		</form>
        <hr></hr>


		</div>

			<%//modificar formatos de dados do mysql para mostrar %>
			<%!FluxoCaixaDAO fluDAO; %>
			<%fluDAO = new FluxoCaixaDAO(usuario.getUsu_ds_subdominio()); %>
			<c:set var="total_entradas" value="0" /> 
			
			Entradas
			<hr></hr>
			<table border="1" align="center"
				style="font-size: larger; font: bold;" cellpadding="8"
				cellspacing="5">
				
			<tr bgcolor="#006666">
				<!-- <thead> -->
				<th style="visibility: hidden;"></th>

				<th style="padding: 5px">Data</th>
				
				<th style="padding: 5px"><label>Tipo</th>

				<th style="padding: 5px"><label>Descrição</th>
				
				<th style="padding: 5px"><label>Valor</th>
				
			</tr>


				<%! ArrayList<FluxoCaixa> listEntrada; %>
				<%  listEntrada = (ArrayList<FluxoCaixa>) request.getSession().getAttribute("listEntrada"); %>
				<%if(listEntrada != null){ %>
					<%  for(int i = 0; i<listEntrada.size();i++){ %>
	
						<tr onmouseover="setAtributoIdCell(this)">
	
							<td style="visibility: hidden;"><c:out value="<%=listEntrada.get(i).getFlu_cd_fluxo_caixa() %>" /></td>
	
							<td><c:out value="<%=fluDAO.converteDataParaMostrar(listEntrada.get(i).getFlu_dt_cadastro())  %>" /></td>
							
							<td><c:out value="<%=listEntrada.get(i).getFlu_ds_forma_pagamento() %>" /></td>
	
							<td><c:out value="<%=listEntrada.get(i).getFlu_ds_fluxo_caixa() %>" /></td>
	
							<td  align="center"><fmt:formatNumber value="<%=listEntrada.get(i).getFlu_vl_fluxo_caixa() %>"  pattern="############,##0.00" /></td>
							
						
						</tr>
												
						<c:set var="vl_pagar" scope="session" value="<%= listEntrada.get(i).getFlu_vl_fluxo_caixa() %>" />
					
						<c:set var="total_entradas" value="${total_entradas + vl_pagar}" /> 							
	
						<%}; %>
					<%}else{ %>
					
					<tr>
					
					<td style="visibility: hidden;"><c:out value="------" /></td>

					<td><c:out value="------" /></td>

					<td><c:out value="------" /></td>

					<td  align="center"><c:out value="------" /></td>
					
					<td align="center">
						<c:out value="------" />
					</td>
				
				</tr>
				
				<% }; %>
										
					<tr> <td style="visibility: hidden;"></td>   <td style="visibility: hidden;"></td>   <td style="visibility: hidden;"></td>  
					<td align="right">Total </td><td id="id_soma_total_entradas"> <fmt:formatNumber value="${total_entradas}" pattern="############,##0.00"/> </td></tr>
			</table>


			<c:set var="total_saidas" value="0" /> 
			Saídas
			<hr></hr>
			<table border="1" align="center"
				style="font-size: larger; font: bold;" cellpadding="8"
				cellspacing="5">
				
			<tr bgcolor="#006666">
				<!-- <thead> -->
				<th style="visibility: hidden;"></th>

				<th style="padding: 5px">Data</th>
				
				<th style="padding: 5px"><label>Tipo</th>

				<th style="padding: 5px"><label>Descrição</th>
				
				<th style="padding: 5px"><label>Valor</th>
				
			</tr>


				<%! ArrayList<FluxoCaixa> listSaida; %>
				<%  listSaida = (ArrayList<FluxoCaixa>) request.getSession().getAttribute("listSaida"); %>
				<%if(listSaida != null){ %>
					<%  for(int i = 0; i<listSaida.size();i++){ %>
	
						<tr onmouseover="setAtributoIdCell(this)">
	
							<td style="visibility: hidden;"><c:out value="<%=listSaida.get(i).getFlu_cd_fluxo_caixa() %>" /></td>
	
							<td><c:out value="<%=fluDAO.converteDataParaMostrar(listSaida.get(i).getFlu_dt_cadastro())  %>" /></td>
							
							<td><c:out value="<%=listSaida.get(i).getFlu_ds_forma_pagamento() %>" /></td>
	
							<td><c:out value="<%=listSaida.get(i).getFlu_ds_fluxo_caixa() %>" /></td>
	
							<td  align="center"><fmt:formatNumber value="<%=listSaida.get(i).getFlu_vl_fluxo_caixa() %>"  pattern="############,##0.00" /></td>
							
						
						</tr>						
								
						<c:set var="vl_pagar" scope="session" value="<%= listSaida.get(i).getFlu_vl_fluxo_caixa() %>" />
					
						<c:set var="total_saidas" value="${total_saidas + vl_pagar}" /> 					
	
					<%}; %>
				<%}else{ %>
						<tr>
	
							<td style="visibility: hidden;"><c:out value="------" /></td>
	
							<td><c:out value="------" /></td>
	
							<td><c:out value="------" /></td>
	
							<td  align="center"><c:out value="------" /></td>
							
							<td align="center">
								<c:out value="------" />
							</td>
						
						</tr>
						
				<% }; %>
				
						<tr> <td style="visibility: hidden;"></td>   <td style="visibility: hidden;"></td>   <td style="visibility: hidden;"></td>  
						<td align="right">Total </td><td id="id_soma_total_saidas"> <fmt:formatNumber value="${total_saidas}" pattern="############,##0.00"/> </td></tr>		
							
								
			</table>
			
			<table><tr style="visibility: hidden;"><td id="id_soma_total"> <fmt:formatNumber value="${total_entradas + total_saidas}" pattern="############,##0.00"/> </td></tr></table>

				
<form name="hidden" id="id_hidden" method="post">
		<input type="text" name="name_fluxo_caixa_hidden"
			id="id_fluxo_caixa_hidden" style="visibility: hidden"></input>
</form>			
			<hr></hr>
						
		<%@include file="../../includes_Geral/footer.jsp"%>
		


</body>
</html>