<%@include file="includes_Geral/menu_cabecalho_sem_menu.jsp"%>
<%@include file="includes_Geral/campoPesquisa_Nao_Mostrar.jsp"%>

<script language="javascript" type="text/javascript">




	function setAtributoIdReceitaCell(ths){
		
		//id 
		strValueHiddenText = "";
		col = ths.cells[0];
		strValueHiddenText += col.firstChild.nodeValue;		
		document.getElementById("id_produto_hidden").setAttribute("Value", strValueHiddenText);
		
		
		//nome
		strValueHiddenText = "";
		col = ths.cells[1];
		strValueHiddenText += col.firstChild.nodeValue;		
		document.getElementById("id_nome_produto_hidden").setAttribute("Value", strValueHiddenText);
	};
	
	

	
	
</script>

<script>

  
  $(document).ready(function() {
	  $("#id_pesquisar").focus();
	  

	    $( "#dialog-form-quantidade" ).dialog({
	        autoOpen: false,
	        height: 200,
	        width: 250,
	        modal: true,
	        buttons: {
	          Confirmar: function() {
	        	 jQuery('.ui-dialog button:nth-child(1)').button('disable');
	        	  
	            $('#id_qtd_produto_hidden').val( $('#id_quantidade_produto').val() );

	          	//adiciona o produto
	        	chamaServletFrame('form_produto_hidden','ConfirmarInclusaoProdutoProposta.action');

	            //$( this ).dialog( "close" );

	          }
	        },Cancelar: function() {
	        	 $( this ).dialog( "close" ); 
		     },
	        close: function() {
	        	$( this ).dialog( "close" ); 
	        }
	      });
	  
	    $(".form_input_qtd").bind("keypress", function(e) {
	        if (e.keyCode == 13) {
	           return false;
	        }
	     });
	  
	    $( "#dialog-form-alerta" ).dialog({
	        autoOpen: false,
	        height: 200,
	        width: 320,
	        modal: true,
	        buttons: {
	          Sim: function() {
	        	
	        	  //chama a quantidade do produto
	        	  $( "#dialog-form-quantidade" ).dialog( "open" );
	        	  $( this ).dialog( "close" );

	          },Não: function() {
		        	 $( this ).dialog( "close" ); 
		      }
	        }
	      });
	  
	  
	    $( ".adicionar_produto" ).on('click',(function( ){
	    	$('#id_p').text($('#id_nome_produto_hidden').val() );
			$( "#dialog-form-alerta" ).dialog( "open" );
  	    }));
	    
	    
	    $( "#id_filtrar" ).on('click',(function( ){
	    	//submit do form que filtra
	    	$( "#id_filtrar" ).attr('disabled', true);
	    	chamaServletFrame('listagemProdutos','ListagemProdutos.action');
	    	
  	    }));
	    
	    $("#id_pesquisar").bind("keypress", function(e) {
	        if (e.keyCode == 13) {
	        	$( "#id_filtrar" ).click();
	        }
	     });
    	  
  });


  </script>


<div id="dialog-form-alerta" title="Adicionar item..." class="dialog-position-top">
		<label> Deseja adicionar o item abaixo ?</label>
		<br>
		<label id="id_p" style="font-style:oblique; font-size: 15px"></label>
</div>

<body onload="$('#id_pesquisar').focus();">

	 <form name="listagemProdutos" action="" method="post"> 

		<!-- CADASTRO DE PRODUTOS -->
		<div >
		<h6></h6> 			
   <h3 align="center">Produtos/Serviços</h3>  

        <hr>
		<table>
		<tr>
			<td> Código/Descrição : <input id="id_pesquisar" name="pesquisar" value="" type="text" style="width: 150px;"></input> </td>
			<td><input id="id_filtrar" value="   filtrar    " type="button" ></input></td>
		</tr>
		</table>
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
							<input type="button" value="Adicionar..." class="adicionar_produto"/>	
						</td>
						
                </tr>

            <%}; %>
            
        </table>
        <input type="text" name="fl_frame" value="S" style="visibility: hidden"></input>
        </form>
<hr></hr>


		</div>


	
	<!-- </form> -->
	<form name="form_produto_hidden"  method="post" class="form_input_qtd">
	
		<input type="text" name="produto_hidden"
			id="id_produto_hidden" style="visibility: hidden"></input>
		<input type="text" name="nome_produto_hidden"
			id="id_nome_produto_hidden" style="visibility: hidden"></input>
		<input type="text" name="qtd_produto_hidden"
			id="id_qtd_produto_hidden" style="visibility: hidden"></input>	
			
	<div id="dialog-form-quantidade" title="Quantidade..." class="dialog-position-top">
	 	Informe a quantidade...
	    <input id="id_quantidade_produto" type="text" name="quantidade_produto" class="numberN"  value="1.00"></input>
	</div>
			
	</form>
<%@include file="includes_Geral/footer_Frame.jsp"%>
</body>
</html>
