<%@include file="includes_Geral/menu_cabecalho.jsp"%>


<script language="javascript" type="text/javascript">




	function setAtributoIdReceitaCell(ths){
		strValueHiddenText = "";

		//id 
		col = ths.cells[0];
		strValueHiddenText += col.firstChild.nodeValue;
		
		document.getElementById("id_produto_hidden").setAttribute("Value", strValueHiddenText);
	};
	
	
	function apagarProduto(){
		$('#id_p').text( 'Deseja realmente apagar o Produto ' + $('#id_produto_hidden').val() + ' ?' );
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
	        	  jQuery('.ui-dialog button:nth-child(1)').button('disable');
	        	  chamaServlet('hidden','ApagarProduto.action');
	        	 $( this ).dialog( "close" ); 

	          },Não: function() {
		        	 $( this ).dialog( "close" ); 
		      }
	        }
	      });
	  
	  
	    $( "#id_apagar_button" ).on('click',(function( ){
	    	
			$( "#dialog-form-alerta" ).dialog( "open" );
			
  	    }));
	    
	    //reajusta o modulo em que a pagina se refere...
	    $("#id_menu_vendas").addClass('selected');
	    $( "#templatemo_menu" ).on('mouseout',(function( ){
	    	$("#id_menu_vendas").addClass('selected');
	    })); 
    	  
  });


  </script>


<div id="dialog-form-alerta" title="Alerta...">
	<p class="validateTips">
		<label id="id_p"></label>
	</p>
</div>

<body>

	 <form name="listagemProdutos" action="" method="post"> 

		<!-- CADASTRO DE PRODUTOS -->
		<div >
		<h6></h6> 			
   <h3>Produtos/Serviços</h3>  

        <hr>


        <table border="1" align="center" style="font-size: larger; font: bold;" cellpadding="5" cellspacing="5"  id="templatemo_color_read">

            <!-- <thead> -->
			<tr bgcolor="#006666">
                <th style="padding: 5px">Código</th>
                
                <th style="padding: 5px"><label>Descrição</th>

                <th style="padding: 5px">Unidade</th>
                
                <th style="padding: 5px">Estoque</th>

                <th style="padding: 5px">Preço</th>
			</tr>

	
			<%! ArrayList<Produto> arrayProduto; %>
			<%  arrayProduto = (ArrayList<Produto>) request.getSession().getAttribute("arrayProdutos"); %>
			<%  for(int i = 0; i<arrayProduto.size();i++){ %>

				<tr onmouseover="setAtributoIdReceitaCell(this)">

                    <!--A tag out Ã© responsÃ¡vel por gerar uma String de saÃ­da na tela -->

                    <td align="center"><fmt:formatNumber value="<%= arrayProduto.get(i).getPrd_cd_produto() %>" pattern="00000"/></td>

                    <td><c:out value="<%= arrayProduto.get(i).getPrd_ds_produto() %>"/></td>
                    
                    <td align="center"><c:out value="<%= arrayProduto.get(i).getPrd_ds_unidade() %>"/></td>
                    
                    <td align="center"><c:out value="<%= arrayProduto.get(i).getPrd_nr_estoque() %>"/></td>

					<td align="center"><fmt:formatNumber value="<%= arrayProduto.get(i).getPrd_vl_preco() %>" pattern="#,##0.00"/></td>


						<td align="justify">
							<input type="button" value="Editar" onclick="chamaServlet('hidden','EditarProduto.action')" class="button_disable_click_class"/>	
						</td>
						<td align="justify">						
							<input type="button" id="id_apagar_button" value="Apagar" onclick="apagarProduto()"/>
						</td>
						
                </tr>

            <%}; %>
            
        </table>
        </form>
<hr></hr>


		</div>


		<%@include file="includes_Geral/footer.jsp"%>
	<!-- </form> -->
	<form name="hidden" id="id_hidden" method="post">
		<input type="text" name="name_produto_hidden"
			id="id_produto_hidden" style="visibility: hidden"></input>
	</form>

</body>
</html>
