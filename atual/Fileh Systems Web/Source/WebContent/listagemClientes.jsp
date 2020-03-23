<%@include file="includes_Geral/menu_cabecalho.jsp"%>
<%@include file="includes_Geral/campoPesquisa_Nao_Mostrar.jsp"%>

<script language="javascript" type="text/javascript">



	function setAtributoIdReceitaCell(ths){
		strValueHiddenText = ""; 

		//id cliente
		col = ths.cells[0];
		strValueHiddenText += col.firstChild.nodeValue;
		
		document.getElementById("id_cliente_hidden").setAttribute("Value", strValueHiddenText);
	};

	
	function limpaFormulario(){		

		$('#id_nome').val('');
		$('#id_cd_cliente').val('');
		
		$( "#id_entidade" ).val('-1');
		
		chamaServlet('pesquisarCliente','ListagemClientes.action');	
		
	};

	function apagarCliente(){
		$('#id_p').text( 'Deseja realmente apagar o Cliente ' + $('#id_cliente_hidden').val() + ' ?' );
		$( "#dialog-form-alerta" ).dialog( "open" );
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
	        	  
	        	  chamaServlet('hidden','ApagarCliente.action');
	        	 $( this ).dialog( "close" ); 

	          },N�o: function() {
		        	 $( this ).dialog( "close" ); 
		      }
	        }
	      });
	  
	  
	    $( "#id_apagar_button" ).on('click',(function( ){
	    	
			$( "#dialog-form-alerta" ).dialog( "open" );
			
  	    }));



	    $( ".button_propostas" ).on('click',(function( ){

	    	$(this).attr("disabled", true);
			chamaServlet('hidden','ListagemPropostasCliente.action'); 	  	    
			
  	    }));
	    
	    
	    $( "#id_filtro" ).on('click',(function( ){
			
	    	$( this ).attr("disabled", true);
	    	chamaServlet('pesquisarCliente','ListagemClientes.action');	  	 
	    	
  	    }));
	    
	    
	    $("#id_filtros_cliente_form").bind("keypress", function(e) {
	        if (e.keyCode == 13) {
	        	$( "#id_filtro" ).click();
	        }
	     });
    	  
	    
	    //reajusta o modulo em que a pagina se refere...
	    $("#id_menu_cliente").addClass('selected');
	    $( "#templatemo_menu" ).on('mouseout',(function( ){
	    	$("#id_menu_cliente").addClass('selected');
	    })); 
  });


  </script>


<div id="dialog-form-alerta" title="Alerta...">
	<p class="validateTips">
		<label id="id_p"></label>
	</p>
</div>


<body onload="$('#id_nome').focus();">
	<form name="pesquisarCliente" id="id_filtros_cliente_form" action=""
		method="post" onsubmit="">

		<!-- <form name="formServiceDesk" action="LoginUsuario.action" method="post"> -->

		<!-- CADASTRO DE USUARIOS -->

		<!-- Lista de Produtos Serdo -->
		<h3>Clientes</h3>

		<hr></hr>
		<table style="font-size: medium; font: bold;" >
			<tr>
			<td>Nome : <input id="id_nome" name="nome"
				value="<%= request.getSession().getAttribute("nome").toString() %>"
				type="text" style="width: 200px;"></input>
			</td>
			<td>C�digo : <input class="numberOnlyN" id="id_cd_cliente"
				name="cd_cliente"
				value="<%= request.getSession().getAttribute("cd_cliente").toString() %>"
				type="text" style="width: 100px;"></input>
			</td>
			<td>Entidade : <select id="id_entidade" name="entidade"		style="width: 285px;" >
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

			<td><input  type="button" name="filtrarButton" id="id_filtro" value="  Filtrar  " onclick="this.form['filtrarButton'].disabled=true ;"></input></td>
			<td><input title="Limpar filtros aplicados..." type="button" name="limpar" value="  Limpar  "
				onclick="this.form['limpar'].disabled=true ;limpaFormulario()"> </input></td>
		</tr>
		</table>
		<hr></hr>

	</form>

	<!--A tag query é usada para processar uma setença SQL de seleção de registros e gerar um objeto ResultSet internamente na memória, conforme especificado no atributo   var, usando a conexão aberta  chamada "ds", que neste caso está definido no atributo dataDource através do uso de EL(Expression Language). -->

	<table border="1" align="center" style="font-size: larger; font: bold;"
		cellpadding="8" cellspacing="5" id="templatemo_color_read">

		<!-- <thead> -->
		<tr bgcolor="#006666">
			<th style="padding: 5px">C�digo</th>
	
			<th style="padding: 5px"><label>Nome</th>
	
			<th style="padding: 5px">Logradouro</th>
	
			<th style="padding: 5px">Bairro</th>
	
			<th style="padding: 5px">UF</th>
	
			<th style="padding: 5px">Entidade</th>
		</tr>




		<%! ArrayList<Cliente> arrayClientes; %>
		<%  arrayClientes = (ArrayList<Cliente>) request.getSession().getAttribute("arrayClientes"); %>
		<%  for(int i = 0; i<arrayClientes.size();i++){ %>

		<tr onmouseover="setAtributoIdReceitaCell(this)">

			<!--A tag out é responsável por gerar uma String de saída na tela -->

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
			
			<input title="Visualizar dados do cliente..." type="button" value="Editar/Ver" class="button_disable_click_class"
				onclick="chamaServlet('hidden','EditarCliente.action')" /> 
				
		  	<input  type="button" value="Apagar" id="id_apagar_button"
				onclick="apagarCliente()" />
			</td>




			<p></p>
			<td align="justify"><input title="Listar propostas desse cliente..." type="button"
				class="button_propostas" value="Propostas..." /></td>

		</tr>

		<%}; %>
	</table>

	<hr></hr>
	<%@include file="includes_Geral/footer.jsp"%>

	<form name="hidden" id="id_hidden" method="post">
		<input type="text" name="name_cliente_hidden"
			id="id_cliente_hidden" style="visibility: hidden"></input>
	</form>

</body>
</html>