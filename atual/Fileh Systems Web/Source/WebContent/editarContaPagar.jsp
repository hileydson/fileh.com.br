<%@include file="includes_Geral/campoPesquisa_Nao_Mostrar.jsp"%> 
<%@include file="includes_Geral/menu_cabecalho.jsp"%>
<%@include file="includes_Geral/dataCalendarFunction.jsp"%>


  <script language="javascript" type="text/javascript">
	function checkBoxParcelado()
	{
		
		if (document.editarContaPagar.COP_FL_PARCELADO.checked == true){
			document.editarContaPagar.COP_NR_PARCELA.disabled=true;
			document.editarContaPagar.COP_NR_PARCELA.value = '0';
		}else{			
			document.editarContaPagar.COP_NR_PARCELA.disabled=false;
			document.editarContaPagar.COP_NR_PARCELA.value = '';
		}

	};
	
	function verificaVazioParcelado(){
		if (document.editarContaPagar.COP_NR_PARCELA.value == '') document.editarContaPagar.COP_NR_PARCELA.value = '0';
		if (document.editarContaPagar.COP_NR_PARCELA.value == '') document.editarContaPagar.COP_NR_PARCELA.value = '0';
		if (document.editarContaPagar.COP_NR_PARCELA.value < 0) document.editarContaPagar.COP_NR_PARCELA.value = '0';
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

	        	 $( "#id_COP_DS_FORNECEDOR" ).css("background-color", 'IndianRed');
	        	 $( "#id_COP_DS_TIPO_CONTA" ).css("background-color", 'IndianRed');
	        	 $( "#id_COP_DS_CONTA_PAGAR" ).css("background-color", 'IndianRed');
	        	 $( "#datepicker" ).css("background-color", 'IndianRed');
	        	 $( "#id_COP_VL_CONTA_PAGAR" ).css("background-color", 'IndianRed');
	        	 $( "#id_COP_NR_PARCELA" ).css("background-color", 'IndianRed');
	        	  
	        	 $( this ).dialog( "close" ); 

	          }
	        }
	      });
	    
	    $( "#id_commit" ).on('click',(function( ){

			if (	($( "#id_COP_DS_FORNECEDOR" ).val() == '')||($( "#id_COP_DS_TIPO_CONTA" ).val() == '')||($( "#id_COP_DS_CONTA_PAGAR" ).val() == '')
					||($( "#id_COP_DT_VENCIMENTO" ).val() == '')||($( "#id_COP_VL_CONTA_PAGAR" ).val() == '0,00')||($( "#id_COP_NR_PARCELA" ).val() == '')	){
				$( "#dialog-form-alerta" ).dialog( "open" );
			}else{
				verificaVazioParcelado();
				document.forms['editarContaPagar'].submit();
			};  	  	    
			
  	    }));
    	  
	    
	    
	    //reajusta o modulo em que a pagina se refere...
	    $("#id_menu_financeiro").addClass('selected');
	    $( "#templatemo_menu" ).on('mouseout',(function( ){
	    	$("#id_menu_financeiro").addClass('selected');
	    })); 
	    
  });


  </script>
    
<%!ContaPagar contaPagar ;%>
<%contaPagar = (ContaPagar) request.getSession().getAttribute("contaPagar"); %>
    
<body>



 <div id="dialog-form-alerta" title="Alerta...">
  <p class="validateTips">Preencha todos os campos necessários...</p>
</div>


<form  name="editarContaPagar" action="ConfirmarEditarContaPagar.action" method="post"> 

		<div >
		<h6></h6>
		
   <h3>Editar Conta</h3>

        <hr></hr>

        
        <table align="center" cellpadding="5" cellspacing="5"  style="font-size: larger;" >
        <tr>
        <td>Fornecedor :</td>
		<td>
            <select id="id_COP_DS_FORNECEDOR" name="COP_DS_FORNECEDOR"   style=" width : 270px;"> 
       			<option value="<%= contaPagar.getCop_ds_fornecedor() %>" selected="selected"><%= contaPagar.getCop_ds_fornecedor() %></option>      
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
            <select id="id_COP_DS_TIPO_CONTA" name="COP_DS_TIPO_CONTA"  style=" width : 270px;"> 
       			<option value="<%= contaPagar.getCop_ds_tipo_conta() %>" selected="selected"><%= contaPagar.getCop_ds_tipo_conta() %></option>      
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
		<td><input maxlength="45" id="id_COP_NR_DOCUMENTO" name="COP_NR_DOCUMENTO" value="<%=contaPagar.getCop_nr_documento() %>" type="text" style=" width : 270px;" class="uppercase"></input></td>
		</tr>
		
		<tr>
        <td>Descrição :</td>
		<td><input maxlength="45" id="id_COP_DS_CONTA_PAGAR" name="COP_DS_CONTA_PAGAR" value="<%=contaPagar.getCop_ds_conta_pagar() %>" type="text" style=" width : 270px;"class="uppercase"></input></td>
        </tr>		
		
		<tr>
		<td>Vencimento :</td>
		<td><input readonly id="datepicker" name="COP_DT_VENCIMENTO" value="<%=contaPagar.getCop_dt_vencimento() %>" type="text" style=" width : 80px;" ></input></td>
		</tr>
		
		<tr>
		<td>Valor :</td>
		<td><input class="real"  id="id_COP_VL_CONTA_PAGAR" name="COP_VL_CONTA_PAGAR" value="<fmt:formatNumber value="<%=contaPagar.getCop_vl_conta_pagar() %>"  pattern="############,##0.00" />" type="text"  style=" width : 150px;" ></input></td>
		</tr>
		
		
		
		
		<tr>
		<% if (contaPagar.getCop_nr_parcela() != 0){ %>
		
		<td>Parcela : ----<input checked="checked" id="id_COP_FL_PARCELADO" name="COP_FL_PARCELADO" type="checkbox" align="bottom" value="S" onmousedown="checkBoxParcelado()" ></input>----</td>
		<td><input maxlength="4" class="numberOnly" id="id_COP_NR_PARCELA" name="COP_NR_PARCELA" value="<%=contaPagar.getCop_nr_parcela() %>" type="text" style=" width : 35px;" onblur="verificaVazioParcelado()"></input></td>
		
		<%}else{ %>
		
		<td>Parcela : ----<input id="id_COP_FL_PARCELADO" name="COP_FL_PARCELADO" type="checkbox" align="bottom" value="S" onmousedown="checkBoxParcelado()" ></input>----</td>
		<td><input maxlength="4" class="numberOnly" disabled="disabled" id="id_COP_NR_PARCELA" name="COP_NR_PARCELA" value="0" type="text" style=" width : 35px;" onblur="verificaVazioParcelado()"></input></td>
		
		<%}; %>
		</tr>
		
		
		<tr>
		<td>Pago :</td>
		<td>
			<%! String fl_sim, fl_nao; %>
			<c:set var="fl_Sim" scope="session" value="<%=contaPagar.getCop_fl_pago() %>" /> 
					<c:choose>
						<c:when test="${fl_Sim == 'S'}">
							<%fl_sim = "checked='checked'"; %>
						</c:when>
						<c:otherwise>
							<%fl_sim = ""; %>
						</c:otherwise>
					</c:choose>	 
			<c:set var="fl_Nao" scope="session" value="<%=contaPagar.getCop_fl_pago() %>" /> 
					<c:choose>
						<c:when test="${fl_Nao != 'S'}">
							<%fl_nao = "checked='checked'"; %>
						</c:when>
						<c:otherwise>
							<%fl_nao = ""; %>
						</c:otherwise>
					</c:choose>	 
										
			<h4> <input id="id_COP_FL_PAGO" name="COP_FL_PAGO" type="radio"  <%=fl_sim %>  value="S" >Sim</input>
			<input id="id_COP_FL_PAGO" name="COP_FL_PAGO" type="radio" <%=fl_nao %> value="N" >Não</input></h4>
		</td>
		</tr>		
		
		
		
		<tr >
		<td></td>
		<td align="center" >
		<a href="listagemContasPagar.jsp"><input type="button" onclick=" chamaServlet('editarContaPagar','ListagemContasPagar.action')" value="  Voltar  " ></input></a>
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