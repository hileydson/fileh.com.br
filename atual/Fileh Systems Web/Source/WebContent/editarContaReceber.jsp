<%@include file="includes_Geral/campoPesquisa_Nao_Mostrar.jsp"%> 
<%@include file="includes_Geral/menu_cabecalho.jsp"%>
<%@include file="includes_Geral/dataCalendarFunction.jsp"%>


  <script language="javascript" type="text/javascript">
	function checkBoxParcelado()
	{
		
		if (document.editarContaReceber.COR_FL_PARCELADO.checked == true){
			document.editarContaReceber.COR_NR_PARCELA.disabled=true;
			document.editarContaReceber.COR_NR_PARCELA.value = '0';
		}else{			
			document.editarContaReceber.COR_NR_PARCELA.disabled=false;
			document.editarContaReceber.COR_NR_PARCELA.value = '';
		}

	};
	
	function verificaVazioParcelado(){
		if (document.editarContaReceber.COR_NR_PARCELA.value == '') document.editarContaReceber.COR_NR_PARCELA.value = '0';
		if (document.editarContaReceber.COR_NR_PARCELA.value == '') document.editarContaReceber.COR_NR_PARCELA.value = '0';
		if (document.editarContaReceber.COR_NR_PARCELA.value < 0) document.editarContaReceber.COR_NR_PARCELA.value = '0';
	}
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

	        	 $( "#id_COR_DS_FORNECEDOR" ).css("background-color", 'IndianRed');
	        	 $( "#id_COR_DS_TIPO_CONTA" ).css("background-color", 'IndianRed');
	        	 $( "#id_COR_DS_CONTA_RECEBER" ).css("background-color", 'IndianRed');
	        	 $( "#datepicker" ).css("background-color", 'IndianRed');
	        	 $( "#id_COR_VL_CONTA_RECEBER" ).css("background-color", 'IndianRed');
	        	 $( "#id_COR_NR_PARCELA" ).css("background-color", 'IndianRed');
	        	  
	        	 $( this ).dialog( "close" ); 

	          }
	        }
	      });
	    
	    $( "#id_commit" ).on('click',(function( ){

			if (	($( "#id_COR_DS_FORNECEDOR" ).val() == '')||($( "#id_COR_DS_TIPO_CONTA" ).val() == '')||($( "#id_COR_DS_CONTA_RECEBER" ).val() == '')
					||($( "#id_COR_DT_VENCIMENTO" ).val() == '')||($( "#id_COR_VL_CONTA_RECEBER" ).val() == '0,00')||($( "#id_COR_NR_PARCELA" ).val() == '')	){
				$( "#dialog-form-alerta" ).dialog( "open" );
			}else{
				verificaVazioParcelado();
				document.forms['editarContaReceber'].submit();
			};  	  	    
			
  	    }));
	    
	    
	    
	    //reajusta o modulo em que a pagina se refere...
	    $("#id_menu_financeiro").addClass('selected');
	    $( "#templatemo_menu" ).on('mouseout',(function( ){
	    	$("#id_menu_financeiro").addClass('selected');
	    })); 
    	  
  });


  </script>
    
<%!ContaReceber contaReceber ;%>
<%contaReceber = (ContaReceber) request.getSession().getAttribute("contaReceber"); %>
    
<body>



 <div id="dialog-form-alerta" title="Alerta...">
  <p class="validateTips">Preencha todos os campos necessários...</p>
</div>


<form  name="editarContaReceber" action="ConfirmarEditarContaReceber.action" method="post"> 

		<div >
		<h6></h6>
		
   <h3>Editar Conta</h3>

        <hr></hr>

        
        <table align="center" cellpadding="5" cellspacing="5"  style="font-size: larger;" >
        <tr>
        <td>Fornecedor :</td>
		<td>
            <select id="id_COR_DS_FORNECEDOR" name="COR_DS_FORNECEDOR"   style=" width : 270px;"> 
       			<option value="<%= contaReceber.getCor_ds_fornecedor() %>" selected="selected"><%= contaReceber.getCor_ds_fornecedor() %></option>      
				<%! ArrayList<String> arrayFornecedor; %>
				<%  arrayFornecedor = (ArrayList<String>) request.getSession().getAttribute("arrayFornecedor"); %>
				<%  for(int i = 0; i<arrayFornecedor.size();i++){ %>
    				<option value="<%=arrayFornecedor.get(i) %>"><%=arrayFornecedor.get(i) %></option>    
 				<%}; %>   
			</select>  
        </td>
        </tr>
        
        
        <tr>
        <td>Tipo de conta :</td>
		<td>
            <select id="id_COR_DS_TIPO_CONTA" name="COR_DS_TIPO_CONTA"  style=" width : 270px;"> 
       			<option value="<%= contaReceber.getCor_ds_tipo_conta() %>" selected="selected"><%= contaReceber.getCor_ds_tipo_conta() %></option>      
				<%! ArrayList<String> arrayTipoConta; %>
				<%  arrayTipoConta = (ArrayList<String>) request.getSession().getAttribute("arrayTipoConta"); %>
				<%  for(int i = 0; i<arrayTipoConta.size();i++){ %>
    				<option value="<%=arrayTipoConta.get(i) %>"><%=arrayTipoConta.get(i) %></option>    
 				<%}; %>  
			</select>  
        </td>
        </tr>
        
        <tr>
        <td>Numero Documento :</td>
		<td><input maxlength="45" id="id_COR_NR_DOCUMENTO" name="COR_NR_DOCUMENTO" value="<%=contaReceber.getCor_nr_documento() %>" type="text" style=" width : 270px;" class="uppercase"></input></td>
		</tr>
		
        <tr>
        <td>Descrição :</td>
		<td><input maxlength="45" id="id_COR_DS_CONTA_RECEBER" name="COR_DS_CONTA_RECEBER" value="<%=contaReceber.getCor_ds_conta_receber() %>" type="text" style=" width : 270px;"class="uppercase"></input></td>
        </tr>		
		
		<tr>
		<td>Vencimento :</td>
		<td><input readonly id="datepicker" name="COR_DT_VENCIMENTO" value="<%=contaReceber.getCor_dt_vencimento() %>" type="text" style=" width : 80px;" ></input></td>
		</tr>
		
		<tr>
		<td>Valor :</td>
		<td><input class="real"  id="id_COR_VL_CONTA_RECEBER" name="COR_VL_CONTA_RECEBER" value="<fmt:formatNumber value="<%=contaReceber.getCor_vl_conta_receber() %>"  pattern="############,##0.00" />" type="text"  style=" width : 150px;" ></input></td>
		</tr>
		
		
		
		
		<tr>
		<% if (contaReceber.getCor_nr_parcela() != 0){ %>
		
		<td>Parcela : ----<input checked="checked" id="id_COR_FL_PARCELADO" name="COR_FL_PARCELADO" type="checkbox" align="bottom" value="S" onmousedown="checkBoxParcelado()" ></input>----</td>
		<td><input maxlength="4" class="numberOnly" id="id_COR_NR_PARCELA" name="COR_NR_PARCELA" value="<%=contaReceber.getCor_nr_parcela() %>" type="text" style=" width : 35px;" onblur="verificaVazioParcelado()"></input></td>
		
		<%}else{ %>
		
		<td>Parcela : ----<input id="id_COR_FL_PARCELADO" name="COR_FL_PARCELADO" type="checkbox" align="bottom" value="S" onmousedown="checkBoxParcelado()" ></input>----</td>
		<td><input maxlength="4" class="numberOnly" disabled="disabled" id="id_COR_NR_PARCELA" name="COR_NR_PARCELA" value="0" type="text" style=" width : 35px;" onblur="verificaVazioParcelado()"></input></td>
		
		<%}; %>
		</tr>
		
		
		<tr>
		<td>Recebido :</td>
		<td>
			<%! String fl_sim, fl_nao; %>
			<c:set var="fl_Sim" scope="session" value="<%=contaReceber.getCor_fl_recebido() %>" /> 
					<c:choose>
						<c:when test="${fl_Sim == 'S'}">
							<%fl_sim = "checked='checked'"; %>
						</c:when>
						<c:otherwise>
							<%fl_sim = ""; %>
						</c:otherwise>
					</c:choose>	 
			<c:set var="fl_Nao" scope="session" value="<%=contaReceber.getCor_fl_recebido() %>" /> 
					<c:choose>
						<c:when test="${fl_Nao != 'S'}">
							<%fl_nao = "checked='checked'"; %>
						</c:when>
						<c:otherwise>
							<%fl_nao = ""; %>
						</c:otherwise>
					</c:choose>	 
										
			<h4> <input id="id_COR_FL_RECEBIDO" name="COR_FL_RECEBIDO" type="radio"  <%=fl_sim %>  value="S" >Sim</input>
			<input id="id_COR_FL_RECEBIDO" name="COR_FL_RECEBIDO" type="radio" <%=fl_nao %> value="N" >Não</input></h4>
		</td>
		</tr>		
		
		
		
		<tr >
		<td></td>
		<td align="center" >
		<a href="listagemContasReceber.jsp"><input type="button" onclick=" chamaServlet('editarContaReceber','ListagemContasReceber.action')" value="  Voltar  " ></input></a>
		<input type="button" id="id_commit" name="commit" onclick="this.form['commit'].disabled=true" value="  Confirmar  " >
		</td>
		</tr>

		</table>
		
		<hr></hr>

		</div>
</form>

	<%@include file="includes_Geral/footer.jsp"%>
</body>
</html>