<%@include file="includes_Geral/menu_cabecalho_sem_menu.jsp"%>
<%@include file="includes_Geral/campoPesquisa_Nao_Mostrar.jsp"%>

<script language="javascript" type="text/javascript">



	function setAtributoIdReceitaCell(ths){
		strValueHiddenText = ""; 
		//id cliente
		col = ths.cells[0];
		strValueHiddenText += col.firstChild.nodeValue;
		
		document.getElementById("id_cliente_hidden").setAttribute("Value", strValueHiddenText);
		
		
		
		strValueHiddenText = "";
		//nome cliente
		col = ths.cells[1];
		strValueHiddenText += col.firstChild.nodeValue;
		
		document.getElementById("id_nome_completo_cliente_hidden").setAttribute("Value", strValueHiddenText);
		
	};

	
	function limpaFormulario(){		

		$('#id_nome').val('');
		$('#id_cd_cliente').val('');
		
		$( "#id_entidade" ).val('-1');
		

	};

		
</script>


<script>

  
  $(document).ready(function() {
	  
		$('#id_limpar').on('click', function(){
			$('#id_nome').val('');
			$('#id_cd_cliente').val('');
			
			$( "#id_entidade" ).val('-1');
			
			chamaServletFrame('pesquisarCliente','ListagemClientes.action');
		});
	  		    
	    $( ".class_escolher_button" ).on('click',(function( ){

	    	//$(this).attr("disabled", true);
	    	$('#id_p').text($('#id_nome_completo_cliente_hidden').val());
	    	$( "#dialog-form-alerta-escolha" ).dialog( "open" );
	    	
	    		    
  	    }));
	    
	    $("#id_pesquisarClienteForm").bind("keypress", function(e) {
	        if (e.keyCode == 13) {
	           if( ($('#id_nome').val() != '') || ($('#id_cd_cliente').val() != '')){
	        	   $('#id_filtrarButton').click();
	           }
	        }
	     });
	    
	    
	    $( "#dialog-form-alerta-escolha" ).dialog({
	        autoOpen: false,
	        height: 180,
	        width: 300,
	        modal: true,
	        buttons: {
	          Sim: function() {
	        	  //escolhe cliente
		          jQuery('.ui-dialog button:nth-child(1)').button('disable');	
	        	  chamaServletFrame('hidden','ConfirmarInclusaoClienteProposta.action');

	          },
	          Não: function(){
	        	  $( this ).dialog( "close" ); 
	          }
	        }
	      });
    	  
  });


  </script>



<div id="dialog-form-alerta-escolha" title="Escolha do Cliente..." class="dialog-position-top">
	
		<label>Deseja atribuir o cliente abaixo a proposta?</label>
		<br>
		<label id="id_p" style="font-style:oblique; font-size: 15px"></label>
	
</div>


<body onload="$('#id_nome').focus();">
	<form name="pesquisarCliente" id="id_pesquisarClienteForm" action="ListagemClientes.action" method="post" >
		
		<!-- Indicador Frame -->
		<input  type="text" value="S" name="fl_frame" style="visibility: hidden"></input>

		
		<h3 align="center">Clientes</h3>


		<table style="font-size: medium; font: bold;" cellpadding="4"
			cellspacing="3">
			<hr></hr>
			<tr>
			<td>Nome : <input id="id_nome" name="nome" class="class_nome_cliente_busca"
				value="<%= request.getSession().getAttribute("nome").toString() %>"
				type="text" style="width: 200px;" ></input>
			</td>
			<td>Código : <input class="numberOnlyN" id="id_cd_cliente"
				name="cd_cliente"
				value="<%= request.getSession().getAttribute("cd_cliente").toString() %>"
				type="text" style="width: 60px;"></input>
			</td>
			<td>Entidade : <select id="id_entidade" name="entidade"
				style="width: 285px;" maxlength="45">
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
			</td>

			<td></td>

			<td><input type="button" name="filtrarButton" id="id_filtrarButton" value="  Filtrar  " onclick="this.form['filtrarButton'].disabled=true ; chamaServletFrame('pesquisarCliente','ListagemClientes.action');" ></input></td>
			<td><input title="Limpar filtros aplicados..." type="button" value="  Limpar  " id="id_limpar"> </input></td>
		</tr>
		</table>
		<hr></hr>

	</form>

	<table border="1" align="center" style="font-size: medium; font: bold;"
		cellpadding="5" cellspacing="3" id="templatemo_color_read">

		<!-- <thead> -->
		<tr bgcolor="#006666">

			<th style="padding: 3px">Código</th>
	
			<th style="padding: 3px"><label>Nome</th>
	
			<th style="padding: 3px">Logradouro</th>
	
			<th style="padding: 3px">Bairro</th>
	
			<th style="padding: 3px">UF</th>
	
			<th style="padding: 3px">Entidade</th>

		</tr>



		<%! ArrayList<Cliente> arrayClientes; %>
		<%  arrayClientes = (ArrayList<Cliente>) request.getSession().getAttribute("arrayClientes"); %>
		<% if (arrayClientes != null) {%>
		<%  for(int i = 0; i<arrayClientes.size();i++){ %>

		<tr onmouseover="setAtributoIdReceitaCell(this)">

			<!--A tag out Ã© responsÃ¡vel por gerar uma String de saÃ­da na tela -->

			<td align="center"><fmt:formatNumber
					value="<%= arrayClientes.get(i).getCli_cd_cliente() %>"
					pattern="00000" /></td>

			<td><c:out
					value="<%= arrayClientes.get(i).getCli_nm_cliente() %>" /></td>

			<td align="center"><c:out
					value="<%= arrayClientes.get(i).getCli_ds_logradouro() %>" /></td>

			<td align="center"><c:out
					value="<%= arrayClientes.get(i).getCli_ds_bairro() %>" /></td>

			<td align="center"><c:out
					value="<%= arrayClientes.get(i).getCli_ds_uf() %>" /></td>

			<td align="center"><c:out
					value="<%= arrayClientes.get(i).getCli_ds_entidade() %>" /></td>

			<td align="center">
			
				
		  	<input type="button" value="Escolher..." class="class_escolher_button"/>
			</td>

		</tr>
		<%	};%>
		<%}; %>
	</table>

	<hr></hr>
	<%@include file="includes_Geral/footer_Frame.jsp"%>
	<form name="hidden" id="id_hidden" method="post">
	
		<input type="text" name="cliente_hidden"
			id="id_cliente_hidden" style="visibility: hidden"></input>
		<input type="text" name="nome_completo_cliente_hidden"
			id="id_nome_completo_cliente_hidden" style="visibility: hidden"></input>
					
	</form>

</body>
</html>