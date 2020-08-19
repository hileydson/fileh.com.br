<%@include file="includes_Geral/menu_cabecalho_imprimir.jsp"%>
<%@include file="includes_Geral/campoPesquisa_Nao_Mostrar.jsp"%>

  
<style type="text/css"> 
textarea {
 font-size: 15px;
 text-transform: uppercase;
}
</style>
  

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
    				<c:set var="CLI_CD_CLIENTE" 	value="<%=cliente.getCli_cd_cliente()%>" /> 
    				<c:set var="CLI_DS_UF" 			value="<%=cliente.getCli_ds_uf()%>" />
    				<c:set var="CLI_NM_CLIENTE" 	value="<%=cliente.getCli_nm_cliente()%>" />
    				<c:set var="CLI_DS_LOGRADOURO" 	value="<%=cliente.getCli_ds_logradouro()%>" />
    				<c:set var="CLI_DS_BAIRRO" 		value="<%=cliente.getCli_ds_bairro()%>" />
    				<c:set var="CLI_NR_TEL" 		value="<%=cliente.getCli_nr_tel()%>" />
    				<c:set var="CLI_DS_ENTIDADE" 	value="<%=cliente.getCli_ds_entidade()%>" />  
    				<c:set var="CLI_DS_REFERENCIA" 	value="<%=cliente.getCli_ds_referencia()%>" />  
	<% };%>    				
    				<c:set var="PRC_VL_DESCONTO" 	value="<%=proposta.getPrc_vl_desconto()%>" />
    				<c:set var="PRC_VL_FRETE" 		value="<%=proposta.getPrc_vl_frete()%>" />
    				<c:set var="PRC_DT_CADASTRO" 	value="<%=proposta.getPrc_dt_cadastro()%>" /> 			
    				<c:set var="PRC_DT_PREVISTA" 	value="<%=proposta.getPrc_dt_prevista()%>" />
    				<c:set var="PRC_DS_OBS" 		value="<%=proposta.getPrc_ds_obs()%>" />     
    				<c:set var="PRC_NM_ATENDENTE"	value="<%=proposta.getPrc_nm_atendente()%>" />   
        
	<%}else{ proposta_situacao = ""; };%>



 <body>  
	
  
 
<form  name="proposta_cliente" action="" method="post">   


		<div >
		<h6></h6> 			
   <!-- Lista de Produtos Serdo -->
   <h3>Proposta Comercial - <%= request.getSession().getAttribute("proposta_editando_mostrar_codigo").toString() %> - <%= proposta_situacao %></h3>

        <hr></hr>
	
	
        <table align="center" cellpadding="5" cellspacing="5"  style="font-size: larger;" >
        <tr>
        <td>Nome:</td>
		<td><input maxlength="45"  disabled="disabled" name="PRC_NM_CLIENTE" id="PRC_NM_CLIENTE" value="${CLI_NM_CLIENTE}" type="text" style=" width : 260px;"></input></td> 
		
		<td>Logradouro:</td>
		<td><input maxlength="45"   disabled="disabled" name="PRC_DS_LOGRADOURO" id="PRC_DS_LOGRADOURO" value="${CLI_DS_LOGRADOURO}" type="text" style=" width : 260px;"></input></td>		
		       
        	<td>Cliente:</td>
			<td>
				<input maxlength="45" disabled="disabled" name="PRC_CD_CLIENTE" id="PRC_CD_CLIENTE" value="${CLI_CD_CLIENTE}" type="text"  style=" width : 160px;"></input>
				
			</td>
		</tr>
		
        <tr>		
        <td>Bairro:</td>
		<td><input  disabled="disabled" name="PRC_DS_BAIRRO" id="PRC_DS_BAIRRO" value="${CLI_DS_BAIRRO}" type="text" style=" width : 260px;" ></input></td>	
		
		<td>Referência:</td>
		<td><input maxlength="45"   disabled="disabled" name="PRC_DS_REFERENCIA" id="PRC_DS_REFERENCIA" value="${CLI_DS_REFERENCIA}" type="text" style=" width : 260px;"></input></td>	
		
		<td>Tel:</td>
		<td><input  disabled="disabled" name="PRC_NR_TEL" id="PRC_NR_TEL" value="${CLI_NR_TEL}" type="text" style=" width : 160px;" ></input></td>
		</tr>		
				
		
		
		<tr>

		<td>Entidade:</td>
		<td><input  disabled="disabled" name="PRC_DS_ENTIDADE" id="PRC_DS_ENTIDADE" value="${CLI_DS_ENTIDADE}" type="text" style=" width : 260px;" ></input></td>
		
		<td>UF:</td>
		<td><input maxlength="45"   disabled="disabled" name="PRC_DS_UF" id="PRC_DS_UF" value="${CLI_DS_UF}" type="text" style=" width : 160px;"></input></td>			
		
		<td>Atendente:</td>
		<td><input maxlength="45"   disabled="disabled" name="PRC_NM_ATENDENTE" id="PRC_NM_ATENDENTE" value="${PRC_NM_ATENDENTE}" type="text" style=" width : 160px;"></input></td>
		
		</tr>	
		
		
		</table> 

		</div>
</form>

   		<hr></hr>  
   
   		<br>
		<div >
		
		<table border="1" align="center" style="font-size: larger; font: bold;" cellpadding="5" cellspacing="5"  id="templatemo_color_read">
			<tr bgcolor="#606060 ">
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
			  		 	<tr onmouseover="setAtributoIdProdutoCell(this)" class="class_linha_tabela">
				  		 	<td style="visibility: hidden;"><c:out value="<%= itens.get(i).getIpc_cd_item_proposta() %>" /></td>
				    		<td><c:out value="<%= itens.get(i).getIpc_ds_item_proposta() %>" /></td>
					        <td align="center"><fmt:formatNumber value="<%= itens.get(i).getIpc_nr_quantidade() %>"  pattern="##############0.00" /></td>
					        <td align="center"><c:out value="<%= itens.get(i).getIpc_ds_unidade() %>" /></td>
					        <td align="center"><fmt:formatNumber value="<%= itens.get(i).getIpc_vl_desconto() %>"  pattern="############,##0.00" /></td>
					        <td align="center"><fmt:formatNumber value="<%= itens.get(i).getIpc_vl_item_proposta() %>"  pattern="############,##0.00" /></td>
					        <td align="center"><fmt:formatNumber value="<%= (itens.get(i).getIpc_vl_item_proposta() * itens.get(i).getIpc_nr_quantidade()) - itens.get(i).getIpc_vl_desconto() %>"  pattern="############,##0.00" /></td> 
					    </tr>
				       	
				       	<c:set var="IPC_VL_ITEM_PROPOSTA_REGISTRO" value="<%= (itens.get(i).getIpc_vl_item_proposta() * itens.get(i).getIpc_nr_quantidade()) - itens.get(i).getIpc_vl_desconto() %>" /> 
    					<c:set var="IPC_VL_DESCONTO_REGISTRO" value="<%= itens.get(i).getIpc_vl_desconto() %>" /> 
    					
    					<c:set var="IPC_VL_ITEM_PROPOSTA_TOTAL" value="${IPC_VL_ITEM_PROPOSTA_TOTAL + IPC_VL_ITEM_PROPOSTA_REGISTRO}" />  
    					<c:set var="IPC_VL_DESCONTO_TOTAL" value="${IPC_VL_DESCONTO_TOTAL + IPC_VL_DESCONTO_REGISTRO}" />   
				    <%}; %>       
			 	<%}; %>           
		     
		    </tbody>
		  </table>
		
		</div>	   		
   		
   		
   		<div width="10px">
   		<hr ></hr>  </div>    

<form  name="proposta_comercial" action="" method="post">      

		<table align="center" cellpadding="5" cellspacing="5"  style="font-size: larger;" >
        <tr>
        <td>Forma de Pagamento :</td>
		<td>

			<input disabled="disabled"  style=" width : 230px;" value="<%=proposta.getPrc_ds_forma_pagamento() %>" ></option>      

		</td>
		</tr>

		</table>

        <table align="center" cellpadding="5" cellspacing="5"  style="font-size: larger;" >
                <tr>
        <td>Data Cadastro :</td>
		<td><input  disabled="disabled"  name="PRC_DT_CADASTRO" value="<c:out value="${PRC_DT_CADASTRO}" />" type="text" style=" width : 80px;" ></input></td>

		<td>Data Prevista :</td>
		<td><input id="datepicker" class="PRC_DT_PREVISTA_edt" disabled="disabled" name="PRC_DT_PREVISTA" value="<c:out value="${PRC_DT_PREVISTA}" />" type="text" style=" width : 80px;" ></input></td>
		
        <td>Frete :</td>
		<td><input maxlength="45"  disabled="disabled" name="PRC_VL_FRETE" id="id_PRC_VL_FRETE" value="<fmt:formatNumber value="${PRC_VL_FRETE}"  pattern="############,##0.00" />" type="text" style=" width : 80px;"></input></td>		
		</tr>

        <tr>
        <td>Desconto Itens :</td>
		<td><input maxlength="45" disabled="disabled" name="IPC_VL_DESCONTO" value="<fmt:formatNumber value="${IPC_VL_DESCONTO_TOTAL}"  pattern="############,##0.00" />" type="text" style=" width : 80px;"></input></td>	

       	<td>Desconto no total :</td>
		<td>
			<input maxlength="45"  disabled="disabled"   name="PRC_VL_DESCONTO" id="id_PRC_VL_DESCONTO" value="<fmt:formatNumber value="${PRC_VL_DESCONTO}"  pattern="############,##0.00" />" type="text" style=" width : 80px;"></input>
		</td>

        <td>Soma Itens :</td>
		<td><input maxlength="45"  disabled="disabled" name="IPC_VL_ITEM_PROPOSTA" value="<fmt:formatNumber value="${IPC_VL_ITEM_PROPOSTA_TOTAL}"  pattern="############,##0.00" />" type="text" style="width : 80px;"></input></td>
		</tr>
		

		
		</table>
		
		<table align="center" cellpadding="5" cellspacing="5"  style="font-size: larger;" >
        <tr>
       	<td>OBS :</td>
		<td>
			<textarea rows="5" disabled="disabled"  name="PRC_DS_OBS" id="id_PRC_DS_OBS"  style=" width : 380px;resize:none;" class="uppercase"><c:out value="${PRC_DS_OBS}" /></textarea>
		</td>	


        <td>Total :</td>
		<td><input maxlength="45"  disabled="disabled" name="COP_NR_DOCUMENTO" value="<fmt:formatNumber value="${(IPC_VL_ITEM_PROPOSTA_TOTAL + PRC_VL_FRETE) - (PRC_VL_DESCONTO)}"  pattern="############,##0.00" />" type="text" style=" width : 80px;"></input></td>
		</tr>
		</table>	
        
        <div align="center">

	   			
 			
   			<hr></hr>		

   			
   		</div>
		<%@include file="includes_Geral/footer.jsp"%> 

 </form>   
<script>window.print();</script>    
</body>
</html>