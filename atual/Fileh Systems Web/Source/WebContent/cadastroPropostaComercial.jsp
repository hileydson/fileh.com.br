<%@include file="includes_Geral/campoPesquisa_Nao_Mostrar.jsp"%>
<%@include file="includes_Geral/menu_cabecalho.jsp"%>
<%@include file="includes_Geral/dataCalendarFunction.jsp"%>

<style>
div #class_same_line {
	display: inline!;
}



</style>



<script language="javascript" type="text/javascript">

	
	
	function setAtributoIdProdutoCell(ths){
		strValueHiddenText = "";

		//input_ds_produto
		col = ths.cells[0];
		strValueHiddenText = col.firstChild.nodeValue;
		document.getElementById("produto_escolhido_rmv").setAttribute("Value", strValueHiddenText);
		document.getElementById("produto_escolhido_edt").setAttribute("Value", strValueHiddenText);


		col = ths.cells[1];
		strValueHiddenText = col.firstChild.nodeValue;
		$('#input_ds_produto').val(strValueHiddenText);

		col = ths.cells[2];
		strValueHiddenText = col.firstChild.nodeValue;
		$('#input_qt_produto').val(strValueHiddenText);

		col = ths.cells[3];
		strValueHiddenText = col.firstChild.nodeValue;
		$('#input_un_produto').val(strValueHiddenText);

		col = ths.cells[4];
		strValueHiddenText = col.firstChild.nodeValue;
		$('#input_vd_produto').val(strValueHiddenText);

		col = ths.cells[5];
		strValueHiddenText = col.firstChild.nodeValue;
		$('#input_vu_produto').val(strValueHiddenText);
		


	};
</script>



<script>
  $(document).ready(function() {

	    var name = $( "#name" ),
	      allFields = $( [] ).add( name );
	 

	    $(".form_editar_item").bind("keypress", function(e) {
	        if (e.keyCode == 13) {
	           return false;
	        }
	     });


	    $( "#dialog-form-editar-item" ).dialog({
	        autoOpen: false,
	        height: 420,
	        width: 340,
	        modal: true,
	        buttons: {
	          Confirmar: function() {
	        	jQuery('.ui-dialog button:nth-child(1)').button('disable');  
	        	  
	            if($("#input_qt_produto").val() == '') $("#input_qt_produto").val("1");
	            if($("#input_un_produto").val() == '') $("#input_un_produto").val("UN");
	            
  	    		//atualiza item proposta
				chamaServlet('item_edt','ConfirmaEditarItemProposta.action');
	          },          
	          Cancelar: function() {	        	  
	          	$( this ).dialog( "close" ); 
	          }
	        },
	        close: function() {
	          allFields.val( "" ).removeClass( "ui-state-error" );
	        }
	      });
	    

	    
	    $( "#dialog-form-remover" ).dialog({
	        autoOpen: false,
	        height: 170,
	        width: 270,
	        modal: true,
	        buttons: {
	          Confirmar: function() {
	            
				//remove produto selecionado	
				jQuery('.ui-dialog button:nth-child(1)').button('disable');
				chamaServlet('produto_proposta_hidden','ApagarItemProposta.action');

	          },          
	          Cancelar: function() {
	          	$( this ).dialog( "close" ); 
	          }
	        },
	        close: function() {
	          allFields.val( "" ).removeClass( "ui-state-error" );
	        }
	      });
	  
	  
	    
		


	    $( ".btn_remover" ).on('click',(function( ){
	    	$('#id_label_remover_produto').text($('#input_ds_produto').val());
	    	$( "#dialog-form-remover" ).dialog( "open" );
	    }));

	    $( ".btn_editar" ).on('click',(function( ){
	    	$("#produto_escolhido_edt").focus();
	    	$( "#dialog-form-editar-item" ).dialog( "open" );
	    }));



		    $( "#dialog-form-editar-proposta" ).dialog({
	  	        autoOpen: false,
	  	        height: 520,
	  	        width: 330,
	  	        modal: true,
	  	        buttons: {
	  	          Confirmar: function() {
	  	        	jQuery('.ui-dialog button:nth-child(1)').button('disable');
	  	        	  
	  	        	$("#id_PRC_DT_CADASTRO_2").removeAttr('disabled');	  	        	  
	  	    		//atualiza dados proposta
					chamaServlet('proposta_edt','ConfirmaEditarPropostaComercial.action');

	  	          },          
	  	          Cancelar: function() {
	  	          	$( this ).dialog( "close" ); 
	  	          }
	  	        },
	  	        close: function() {
	  	          allFields.val( "" ).removeClass( "ui-state-error" );
	  	        }
	  	      });

		      
	  	    $( ".modificar_proposta" ).on('click',(function( ){
	  	    	$("#id_PRC_DT_CADASTRO_2").val($("#id_PRC_DT_CADASTRO_1").val());
	  	  		$(".PRC_DT_PREVISTA_edt2").val($(".PRC_DT_PREVISTA_edt").val());
	  	  		$("#id_PRC_VL_FRETE_edt").val($("#id_PRC_VL_FRETE").val());
	  	  		$("#id_PRC_VL_DESCONTO_edt").val($("#id_PRC_VL_DESCONTO").val());
	  	  		$("#id_PRC_DS_OBS_edt").val($("#id_PRC_DS_OBS").val());
	  	  	    
	  	    	$( "#dialog-form-editar-proposta" ).dialog( "open" );
	  	    }));


	 	    $( ".textarea_limit" ).keydown(function(event){
		  	    if(event.keyCode == 13 && $(this).val().split("\n").length >= ($(this).attr('rows')-1) ) { 
		  	        return false;
		  	    }
	  	    });
	    
	    
	  
	    
	    $( "#proposta_comercial_criada-dialog-message" ).dialog({
	    	open: function() { $(".ui-dialog-titlebar-close").hide(); },
	    	autoOpen : false,
	        modal: true,
	        buttons: {
	          Sim: function() {
	        	  $( this ).dialog( "close" );
	        	  $("#proposta_comercial_criada-dialog-message").parent().css('position', 'center');
	          },
	      Não: function() {
	    	  jQuery('.ui-dialog button:nth-child(2)').button('disable');
	    	  chamaServlet('proposta_cliente','CarregaCadastroPropostaComercialLimpa.action');
	        }    
	      
	        }
	      });

	    
	    $( "#proposta_comercial-dialog-message" ).dialog({
	    	open: function() { $(".ui-dialog-titlebar-close").hide();},
		    autoOpen : false,
	        modal: true,
	        buttons: {
	          Sim: function() {
	        	  jQuery('.ui-dialog button:nth-child(1)').button('disable');
	        	  chamaServlet('proposta_cliente','CadastrarPropostaComercial.action');
	          },
	      Não: function() {
	          window.location.replace("usuarioLogadoInicial.jsp");
	        }    
	      
	        }
	      });
	    


		  
	   //$('.dialog-position-topr').parent().css('position', 'top');
	    $( '#proposta_comercial-dialog-message').dialog({
    		dialogClass: 'dlgfixed',
   			position: "top"
  		});
	    $( '#proposta_comercial_criada-dialog-message').dialog({
    		dialogClass: 'dlgfixed',
   			position: "top"
  		});
	    
	    
	    $( '#id_btn_atualiza_data_cadastro').on('click',(function( ){
	    	var d = new Date();
	    	
	    	var mes = (d.getMonth()+1);
	    	if ( mes < 10 ) {mes = '0'+mes};
	    	
	    	var dia = (d.getDate());
	    	if ( dia < 10 ) {dia = '0'+dia};
	    	
	    	$('#id_PRC_DT_CADASTRO_2').val(dia+'/'+mes+'/'+d.getFullYear());
	    }));
	    
	    
	    //reajusta o modulo em que a pagina se refere...
	    $("#id_menu_vendas").addClass('selected');
	    $( "#templatemo_menu" ).on('mouseout',(function( ){
	    	$("#id_menu_vendas").addClass('selected');
	    })); 
		
  });
  </script>




<%!Cliente cliente;%>
<%!String proposta_situacao; %>
<%!PropostaComercial proposta;%>

<%//verifica se já estava sendo editada alguma proposta anteriormente
	  if ( (request.getSession().getAttribute("proposta_editando") != null)  ){ 
		  proposta = (PropostaComercial) request.getSession().getAttribute("proposta_editando");
		  cliente = (Cliente) request.getSession().getAttribute("proposta_cliente");
		  
		  //evita erro para proposta sem situacao
		  if( proposta.getPrc_ds_situacao() == null) {
			  proposta.setPrc_ds_situacao("");
		  };
		  proposta_situacao = proposta.getPrc_ds_situacao();
		  
		  //evita erro para proposta sem forma de pagamento
		  if( proposta.getPrc_ds_forma_pagamento() == null) {
			  proposta.setPrc_ds_forma_pagamento("");
		  };
	  
	%>

<% if ( cliente != null ){  %>
<c:set var="CLI_CD_CLIENTE" value="<%=cliente.getCli_cd_cliente()%>" />
<c:set var="CLI_DS_UF" value="<%=cliente.getCli_ds_uf()%>" />
<c:set var="CLI_NM_CLIENTE" value="<%=cliente.getCli_nm_cliente()%>" />
<c:set var="CLI_DS_LOGRADOURO"
	value="<%=cliente.getCli_ds_logradouro()%>" />
<c:set var="CLI_DS_BAIRRO" value="<%=cliente.getCli_ds_bairro()%>" />
<c:set var="CLI_NR_TEL" value="<%=cliente.getCli_nr_tel()%>" />
<c:set var="CLI_DS_ENTIDADE" value="<%=cliente.getCli_ds_entidade()%>" />
<c:set var="CLI_DS_REFERENCIA"
	value="<%=cliente.getCli_ds_referencia()%>" />
<% };%>
<c:set var="PRC_VL_DESCONTO" value="<%=proposta.getPrc_vl_desconto()%>" />
<c:set var="PRC_VL_FRETE" value="<%=proposta.getPrc_vl_frete()%>" />
<c:set var="PRC_DT_CADASTRO" value="<%=proposta.getPrc_dt_cadastro()%>" />
<c:set var="PRC_DT_PREVISTA" value="<%=proposta.getPrc_dt_prevista()%>" />
<c:set var="PRC_DS_OBS" value="<%=proposta.getPrc_ds_obs()%>" />
<c:set var="PRC_NM_ATENDENTE"
	value="<%=proposta.getPrc_nm_atendente()%>" />

<%}else{ proposta_situacao = ""; };%>



<body>


	<%
	if((!request.getSession().getAttribute("proposta_comercial_criada").toString().equalsIgnoreCase("S"))  ){	
		
		if ( (request.getSession().getAttribute("proposta_editando") != null)  ){%>

	<script>$(document).ready(function() {   $( "#proposta_comercial_criada-dialog-message" ).dialog( "open" );   });</script>

	<%}else{%>

	<script>$(document).ready(function() { $( "#proposta_comercial-dialog-message" ).dialog( "open" );   });</script>

	<%};
	  
	}else{
		request.getSession().setAttribute("proposta_comercial_criada","N");	
	};%>





	<!-- -------------------------------------MSG's---------------------------------------- -->

	<div id="proposta_comercial_criada-dialog-message"
		title="Proposta Comercial" class="dialog-position-top">
		<span class="ui-icon ui-icon-circle-check"
			style="float: left; margin: 0 7px 50px 0;"></span>
		<p></p>
		<p>
			A proposta comercial código
			<%= request.getSession().getAttribute("proposta_editando_mostrar_codigo").toString() %>
			estava sendo editada, deseja continuar etidando ?
		</p>
		<p></p>
	</div>



	<div id="proposta_comercial-dialog-message" title="Proposta Comercial"
		class="dialog-position-top">
		<p>
			<span class="ui-icon ui-icon-circle-check"
				style="float: left; margin: 0 7px 50px 0;"></span>
		<p></p>
		<p>Deseja criar nova Proposta Comercial ?</p>
		<p></p>
	</div>

	<div id="dialog-form-remover" title="Remover item"
		class="dialog-position-top">
		<p class="validateTips">Deseja relmente remover o item abaixo ?</p>
		<label style="font-family: fantasy; font-style: italic;"
			id="id_label_remover_produto"></label>
	</div>




	<div id="dialog-form-editar-proposta" title="Editar dados proposta"
		class="dialog-position-top">
		<p class="validateTips">Informe os novos valores...</p>
		<form name="proposta_edt" method="post" class="form_editar_proposta">
			<fieldset>
				<p>OBS</p>
				<textarea rows="4" maxlength="150" id="id_PRC_DS_OBS_edt"
					name="PRC_DS_OBS_edt" style="width: 265px;" class="textarea_limit"></textarea>
				<BR></BR> Desconto no total<input
					title="Desconto no valor total da proposta" maxlength="25"
					name="PRC_VL_DESCONTO_edt" class="realN"
					id="id_PRC_VL_DESCONTO_edt" type="text" style="width: 80px;"></input>
				Valor Frete<input maxlength="25" name="PRC_VL_FRETE_edt"
					class="realN" id="id_PRC_VL_FRETE_edt" type="text"
					style="width: 80px;"> Data Prevista<input
					title="Data prevista para efetivação" readonly id="datepicker2"
					name="PRC_DT_PREVISTA_edt2" class="PRC_DT_PREVISTA_edt2"
					type="text" style="width: 80px;" maxlength="11"></input> Data
				Cadastro<br>
				<div class="class_same_line">
					<input class="class_same_line" id="id_PRC_DT_CADASTRO_2" readonly
						disabled="disabled" name="PRC_DT_CADASTRO_2" type="text"
						style="width: 80px;" maxlength="11"></input> <input
						title="Atualizar para a data atual..." class="class_same_line"
						type="button" id="id_btn_atualiza_data_cadastro"
						value="Atualizar data..."></input>
				</div>


				<br>Situação : <br> <select title="Situação da Proposta"
					id="id_situacao_proposta_edt" name="situacaoProposta_edt"
					style="width: 285px;">
					<option value="" selected="selected">Escolha uma
						situação...</option>
					<%if ( (request.getSession().getAttribute("proposta_editando") != null)  ){%>
					<%if (!proposta.getPrc_ds_situacao().equalsIgnoreCase("")) {%>
					<option value="<%=proposta.getPrc_ds_situacao() %>"
						selected="selected"><%=proposta.getPrc_ds_situacao() %></option>
					<%}else{%>
					<script>$( "#id_situacao_proposta" ).val('');</script>
					<%};%>
					<%};%>

					<%! ArrayList<String> arraySituacaoProposta; %>
					<%  arraySituacaoProposta = (ArrayList<String>) request.getSession().getAttribute("listNomesSituacao"); %>
					<%  for(int i = 0; i<arraySituacaoProposta.size();i++){ %>
					<option value="<%=arraySituacaoProposta.get(i) %>"><%=arraySituacaoProposta.get(i) %></option>
					<%}; %>
				</select> <br> <br>Forma de Pagamento :<br> <select
					title="Forma de pagamento" id="id_forma_pagamento_edt"
					name="formaPagamento_edt" style="width: 285px;">
					<option value="" selected="selected">Escolha uma forma de
						pagamento...</option>
					<%if ( (request.getSession().getAttribute("proposta_editando") != null)  ){%>
					<%if (!proposta.getPrc_ds_forma_pagamento().equalsIgnoreCase("")) {%>
					<option value="<%=proposta.getPrc_ds_forma_pagamento() %>"
						selected="selected"><%=proposta.getPrc_ds_forma_pagamento() %></option>
					<%}else{%>
					<script>$( "#id_forma_pagamento_edt" ).val('');</script>
					<%};%>
					<%};%>

					<%! ArrayList<String> arrayFormaPagamento; %>
					<%  arrayFormaPagamento = (ArrayList<String>) request.getSession().getAttribute("listNomesFormaPagamento"); %>
					<%  for(int i = 0; i<arrayFormaPagamento.size();i++){ %>
					<option value="<%=arrayFormaPagamento.get(i) %>"><%=arrayFormaPagamento.get(i) %></option>
					<%}; %>
				</select> <br>

			</fieldset>
		</form>
	</div>



	<div id="dialog-form-editar-item" title="Editar item"
		class="dialog-position-top">
		<p class="validateTips">Informe os novos valores...</p>
		<form name="item_edt" method="post" class="form_editar_item">
			<fieldset>
				<input type="text" name="produto_edt" id="produto_escolhido_edt"
					class="produto_escolhido_edt" style="visibility: hidden;"></input>
				Descrição<input id="input_ds_produto" name="name_input_ds_produto"
					style="width: 290px;" maxlength="45" class="uppercase">
				Quantidade<input id="input_qt_produto" class="numberN"
					name="name_input_qt_produto" style="width: 290px;" maxlength="7">
				Unidade<input id="input_un_produto" name="name_input_un_produto"
					style="width: 290px;" maxlength="2" class="uppercase"> <br></br>
				Valor Desconto<input title="Desconto no total deste item..."
					id="input_vd_produto" name="name_input_vd_produto" class="realN"
					style="width: 200px;" maxlength="25"> Valor Unitário<input
					class="real" id="input_vu_produto" name="name_input_vu_produto"
					style="width: 200px;" maxlength="25">
			</fieldset>
		</form>
	</div>

	<!-- ---------------------------------------------------------------------------------- -->



	<form name="proposta_cliente" action="" method="post">


		<div>
			<h6></h6>
			<!-- Lista de Produtos Serdo -->
			<h3>
				Proposta Comercial -
				<%= request.getSession().getAttribute("proposta_editando_mostrar_codigo").toString() %>
				-
				<%= proposta_situacao %></h3>

			<hr></hr>

			<table>
				<tr>
					<td><label style="font-size: medium; size: 10px">Cliente:</label></td>
					<td><%@include file="incluirClienteProposta.jsp"%>

					</td>
				</tr>
			</table>


			<table align="center" cellpadding="5" cellspacing="5"
				style="font-size: larger;">
				<tr>
					<td>Nome:</td>
					<td><input maxlength="45" disabled="disabled"
						name="PRC_NM_CLIENTE" id="PRC_NM_CLIENTE"
						value="${CLI_NM_CLIENTE}" type="text" style="width: 260px;"></input></td>

					<td>Logradouro:</td>
					<td><input maxlength="45" disabled="disabled"
						name="PRC_DS_LOGRADOURO" id="PRC_DS_LOGRADOURO"
						value="${CLI_DS_LOGRADOURO}" type="text" style="width: 260px;"></input></td>

					<td>Cliente:</td>
					<td><input maxlength="45" disabled="disabled"
						name="PRC_CD_CLIENTE" id="PRC_CD_CLIENTE"
						value="${CLI_CD_CLIENTE}" type="text" style="width: 160px;"></input>

					</td>
				</tr>

				<tr>
					<td>Bairro:</td>
					<td><input disabled="disabled" name="PRC_DS_BAIRRO"
						id="PRC_DS_BAIRRO" value="${CLI_DS_BAIRRO}" type="text"
						style="width: 260px;"></input></td>

					<td>Referência:</td>
					<td><input maxlength="45" disabled="disabled"
						name="PRC_DS_REFERENCIA" id="PRC_DS_REFERENCIA"
						value="${CLI_DS_REFERENCIA}" type="text" style="width: 260px;"></input></td>

					<td>Tel:</td>
					<td><input disabled="disabled" name="PRC_NR_TEL"
						id="PRC_NR_TEL" value="${CLI_NR_TEL}" type="text"
						style="width: 160px;"></input></td>
				</tr>



				<tr>

					<td>Entidade:</td>
					<td><input disabled="disabled" name="PRC_DS_ENTIDADE"
						id="PRC_DS_ENTIDADE" value="${CLI_DS_ENTIDADE}" type="text"
						style="width: 260px;"></input></td>

					<td>UF:</td>
					<td><input maxlength="45" disabled="disabled" name="PRC_DS_UF"
						id="PRC_DS_UF" value="${CLI_DS_UF}" type="text"
						style="width: 160px;"></input></td>

					<td>Atendente:</td>
					<td><input title="Usuário que criou a proposta..."
						maxlength="45" disabled="disabled" name="PRC_NM_ATENDENTE"
						id="PRC_NM_ATENDENTE" value="${PRC_NM_ATENDENTE}" type="text"
						style="width: 160px;"></input></td>

				</tr>


			</table>

		</div>
	</form>

	<hr></hr>





	<%@include file="incluirProdutoProposta.jsp"%>
	<br>
	<div>

		<table border="1" align="center"
			style="font-size: larger; font: bold;" cellpadding="5"
			cellspacing="5" id="templatemo_color_read">
			<tr bgcolor="#006666">
				<th style="visibility: hidden;">id</th>
				<th>Descrição</th>
				<th>Quantidade</th>
				<th>Unidade</th>
				<th>Valor Desconto</th>
				<th>Valor Unitário</th>
				<th>Valor Total</th>

			</tr>

			<c:set var="IPC_VL_ITEM_PROPOSTA_TOTAL" value="0" />
			<c:set var="IPC_VL_DESCONTO_TOTAL" value="0" />
			<%!ArrayList <ItemProposta> itens;%>
			<%  itens = (ArrayList <ItemProposta>) request.getSession().getAttribute("proposta_itens"); %>
			<% if (itens != null) { %>
			<%  for(int i = 0; i<itens.size();i++){ %>
			<tr onmouseover="setAtributoIdProdutoCell(this)"
				class="class_linha_tabela">
				<td style="visibility: hidden;"><c:out
						value="<%= itens.get(i).getIpc_cd_item_proposta() %>" /></td>
				<td><c:out
						value="<%= itens.get(i).getIpc_ds_item_proposta() %>" /></td>
				<td align="center"><fmt:formatNumber
						value="<%= itens.get(i).getIpc_nr_quantidade() %>"
						pattern="##############0.00" /></td>
				<td align="center"><c:out
						value="<%= itens.get(i).getIpc_ds_unidade() %>" /></td>
				<td align="center"><fmt:formatNumber
						value="<%= itens.get(i).getIpc_vl_desconto() %>"
						pattern="############,##0.00" /></td>
				<td align="center"><fmt:formatNumber
						value="<%= itens.get(i).getIpc_vl_item_proposta() %>"
						pattern="############,##0.00" /></td>
				<td align="center"><fmt:formatNumber
						value="<%= (itens.get(i).getIpc_vl_item_proposta() * itens.get(i).getIpc_nr_quantidade()) - itens.get(i).getIpc_vl_desconto() %>"
						pattern="############,##0.00" /></td>
				<td align="center"><input type="button" value="Editar"
					class="btn_editar" /></td>
				<td align="center"><input type="button" value="Remover"
					class="btn_remover" /></td>
			</tr>

			<c:set var="IPC_VL_ITEM_PROPOSTA_REGISTRO"
				value="<%= (itens.get(i).getIpc_vl_item_proposta() * itens.get(i).getIpc_nr_quantidade()) - itens.get(i).getIpc_vl_desconto() %>" />
			<c:set var="IPC_VL_DESCONTO_REGISTRO"
				value="<%= itens.get(i).getIpc_vl_desconto() %>" />

			<c:set var="IPC_VL_ITEM_PROPOSTA_TOTAL"
				value="${IPC_VL_ITEM_PROPOSTA_TOTAL + IPC_VL_ITEM_PROPOSTA_REGISTRO}" />
			<c:set var="IPC_VL_DESCONTO_TOTAL"
				value="${IPC_VL_DESCONTO_TOTAL + IPC_VL_DESCONTO_REGISTRO}" />
			<%}; %>
			<%}; %>

			</tbody>
		</table>

	</div>
	<div align="center">
		<input type="button" id="adicionar_item" value="Adicionar Item..." />

	</div>



	<div width="10px">
		<hr></hr>
	</div>

	<form name="proposta_comercial" action="" method="post">


		<table align="center" cellpadding="5" cellspacing="5"
			style="font-size: larger;">
			<tr>
				<td>Forma de Pagamento :</td>
				<td><input disabled="disabled" style="width: 230px;"
					value="<%=proposta.getPrc_ds_forma_pagamento() %>">
				</option></td>
			</tr>

		</table>





		<table align="center" cellpadding="5" cellspacing="5"
			style="font-size: larger;">


			<tr>
				<td>Data Cadastro :</td>
				<td><input
					title="Data que a proposta foi cadastrada ou atualizada"
					disabled="disabled" name="PRC_DT_CADASTRO"
					id="id_PRC_DT_CADASTRO_1"
					value="<c:out value="${PRC_DT_CADASTRO}" />" type="text"
					style="width: 80px;"></input></td>

				<td>Data Prevista :</td>
				<td><input title="Data prevista para efetivação"
					id="datepicker" class="PRC_DT_PREVISTA_edt" disabled="disabled"
					name="PRC_DT_PREVISTA" value="<c:out value="${PRC_DT_PREVISTA}" />"
					type="text" style="width: 80px;"></input></td>

				<td>Frete :</td>
				<td><input maxlength="45" disabled="disabled"
					name="PRC_VL_FRETE" id="id_PRC_VL_FRETE"
					value="<fmt:formatNumber value="${PRC_VL_FRETE}"  pattern="############,##0.00" />"
					type="text" style="width: 80px;"></input></td>
			</tr>

			<tr>
				<td>Desconto Itens :</td>
				<td><input title="Total de descontos dos itens" maxlength="45"
					disabled="disabled" name="IPC_VL_DESCONTO"
					value="<fmt:formatNumber value="${IPC_VL_DESCONTO_TOTAL}"  pattern="############,##0.00" />"
					type="text" style="width: 80px;"></input></td>

				<td>Desconto no total :</td>
				<td><input title="Desconto efetuado no total da proposta"
					maxlength="45" disabled="disabled" name="PRC_VL_DESCONTO"
					id="id_PRC_VL_DESCONTO"
					value="<fmt:formatNumber value="${PRC_VL_DESCONTO}"  pattern="############,##0.00" />"
					type="text" style="width: 80px;"></input></td>

				<td>Soma Itens :</td>
				<td><input title="Soma total dos itens" maxlength="45"
					disabled="disabled" name="IPC_VL_ITEM_PROPOSTA"
					value="<fmt:formatNumber value="${IPC_VL_ITEM_PROPOSTA_TOTAL}"  pattern="############,##0.00" />"
					type="text" style="width: 80px;"></input></td>
			</tr>



		</table>

		<table align="center" cellpadding="5" cellspacing="5"
			style="font-size: larger;">
			<tr>
				<td>OBS :</td>
				<td><textarea rows="5" disabled="disabled" name="PRC_DS_OBS"
						id="id_PRC_DS_OBS" style="width: 380px; resize: none;" class="uppercase"><c:out
							value="${PRC_DS_OBS}" /></textarea></td>


				<td>Total :</td>
				<td><input maxlength="45" disabled="disabled"
					name="COP_NR_DOCUMENTO"
					value="<fmt:formatNumber value="${(IPC_VL_ITEM_PROPOSTA_TOTAL + PRC_VL_FRETE) - (PRC_VL_DESCONTO)}"  pattern="############,##0.00" />"
					type="text" style="width: 80px;"></input></td>
			</tr>
		</table>

		<div align="center">
			<table>
				<tr>
					<td><input class="modificar_proposta" type="button"
						value="  Editar...  "></td>
					<td><input type="button" value="  Imprimir  "
						onclick="window.open('imprimirPropostaComercial.jsp')"> </input></td>

				</tr>
			</table>


			<hr></hr>


		</div>
		<%@include file="includes_Geral/footer.jsp"%>

	</form>


	<form name="produto_proposta_hidden" id="id_hidden" method="post"
		style="visibility: hidden;">
		<input type="text" name="id_produto" id="id_produto_escolhido"
			class="id_produto_escolhido"></input> <input type="text"
			name="produto_qtd" id="produto_escolhido_qtd"
			class="produto_escolhido_qtd"></input> <input type="text"
			name="produto_rmv" id="produto_escolhido_rmv"
			class="produto_escolhido_rmv"></input> <input type="text"
			name="id_proposta" id="id_proposta_editando"
			class="id_proposta_editando"></input>
	</form>




	<form name="cliente_proposta_hidden" id="id_hidden" method="post"
		style="visibility: hidden;">
		<input type="text" name="id_cliente_proposta"
			id="id_cliente_escolhido" class="id_cliente_escolhido"></input> <input
			type="text" name="id_proposta" id="id_proposta_editando"
			class="id_proposta_editando"></input>
	</form>


</body>
</html>