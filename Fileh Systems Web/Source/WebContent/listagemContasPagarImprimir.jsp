<%@include file="includes_Geral/menu_cabecalho_imprimir.jsp"%>
<%@include file="includes_Geral/campoPesquisa_Nao_Mostrar.jsp"%>

	

<body>

<form  id="Filtros" name="pesquisar" action="" method="post">

<div align="center" style="c">	
		<h6 ></h6>
		<!-- Lista de contas a pagar Serdo -->
				<%if (!request.getSession().getAttribute("fl_situacao_todas").toString().equalsIgnoreCase("")) { request.getSession().setAttribute("fl_situacao_todas", "Todas");  }else{request.getSession().setAttribute("fl_situacao_todas", "");};%> 
				<%if (!request.getSession().getAttribute("fl_situacao_pago").toString().equalsIgnoreCase("")) {request.getSession().setAttribute("fl_situacao_pago", "Pago");  }else{request.getSession().setAttribute("fl_situacao_pago", "");};%> 
				<%if (!request.getSession().getAttribute("fl_situacao_naoPago").toString().equalsIgnoreCase("")) {request.getSession().setAttribute("fl_situacao_naoPago", "Não Pago");  }else{request.getSession().setAttribute("fl_situacao_naoPago", "");};%>

		<tr align="center"> 
		<td><h3 align="center">Relatório de Contas a Pagar</h3></td>
	<table>

		<tr> 
			<td >
				Situação :<input name="situacao"  type="text" style=" width : 80px;" disabled="disabled" 
				value="<%= request.getSession().getAttribute("fl_situacao_todas").toString() + request.getSession().getAttribute("fl_situacao_pago").toString() + request.getSession().getAttribute("fl_situacao_naoPago").toString() %>"></input>
			</td>
			<td></td>
			
		</tr>
		
		<tr>
		<%if (!request.getSession().getAttribute("filtroDataInicio").toString().equalsIgnoreCase("")) { %>
		<td> Data Inicio : <input id="datepicker" value="<%= request.getSession().getAttribute("filtroDataInicio").toString() %>"  name="filtroDataInicio" type="text" style=" width : 80px;" disabled="disabled" ></input></td>
		<%};%>
		<%if (!request.getSession().getAttribute("filtroDataFim").toString().equalsIgnoreCase("")) { %>
		<td> Data Fim :<input id="datepicker2" value="<%= request.getSession().getAttribute("filtroDataFim").toString() %>"   name="filtroDataFim" type="text" style=" width : 80px;" disabled="disabled"></input></td>
		<%};%>
		</tr>
		
		<tr>
		<%if (!request.getSession().getAttribute("doc").toString().equalsIgnoreCase("")) { %>
		<td> Nr Doc : <input name="doc" value="<%= request.getSession().getAttribute("doc").toString() %>" type="text" style=" width : 80px; " disabled="disabled"></input></td>
		<%};%>
		<%if (!request.getSession().getAttribute("codigo_conta").toString().equalsIgnoreCase("0")) { %>
		<td> Código : <input  name="codigo_conta" value="<%= request.getSession().getAttribute("codigo_conta").toString() %>" type="text" style=" width : 200px; " disabled="disabled"></input></td>
		<%};%>
		</tr>
			
		<tr>	
		<%if (!request.getSession().getAttribute("msgFornecedorValue").toString().equalsIgnoreCase("-1")) { %>
		<td> Fornecedor : <input id="id_fornecedor"  name="fornecedor" value="<%= request.getSession().getAttribute("msgFornecedorValue").toString() %>" type="text" style=" width : 270px; " disabled="disabled"></input></td>
		<%};%>			
		<%if (!request.getSession().getAttribute("msgTipoContaValue").toString().equalsIgnoreCase("-1")) { %>
		<td> Tipo de conta : <input id="id_tipoConta"  name="tipoConta" value="<%= request.getSession().getAttribute("msgTipoContaValue").toString() %>" type="text" style=" width : 270px; " disabled="disabled"></input></td>
		<%};%>	
		</tr>
	</table>
</div>		
</form>		

		<hr></hr>

			<table border="1" align="center"
				style="font-size: larger; font: bold; " cellpadding="8"
				cellspacing="5"  id="templatemo_color_read">

				<!-- <thead> -->
			<tr bgcolor="#606060 ">
			
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

						
					</tr>
					<c:set var="vl_pagar" scope="session" value="<%= arrayContasPagar.get(i).getCop_vl_conta_pagar() %>" />
					<c:set var="total" value="${total + vl_pagar}" />  
				<%}; %>
				<tr><td style="visibility: hidden;"></td> <td style="visibility: hidden;"> <td style="visibility: hidden;"></td>  <td style="visibility: hidden;"></td> <td style="visibility: hidden;"></td>
				<td align="center">Soma Total  </td><td> <fmt:formatNumber value="${total}" pattern="############,##0.00"/> </td></tr>
			</table>
	</div>

	<%@include file="includes_Geral/footer.jsp"%>
	<script>window.print();</script>
</body>
</html>