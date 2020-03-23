<%@include file="includes_Geral/menu_cabecalho.jsp"%>

<script>

  
  $(document).ready(function() {

	    //reajusta o modulo em que a pagina se refere...
	    $("#id_menu_vendas").addClass('selected');
	    $( "#templatemo_menu" ).on('mouseout',(function( ){
	    	$("#id_menu_vendas").addClass('selected');
	    })); 
    	  
  });


  </script>

<body>

<div >

	<!-- ultima data de atualização dos produtos -->	
   	<!-- SELECT PAR_VL_PARAMETRO FROM PARAMETRO WHERE PAR_CD_USUARIO = ID_USUARIO_LOGADO AND PAR_DS_PARAMETRO = 'DATA_ATUALIZACAO_PRODUTOS' -->
   	       
		<h6></h6> 	
   <h3>Lista de Preço</h3>  
<hr></hr>



        <table border="1" align="center" style="font-size: larger; font: bold;" cellpadding="5" cellspacing="5" id="templatemo_color_read">


		<tr bgcolor="#006666">
                <th style="padding: 5px">Código</th>
                
                <th style="padding: 5px">Descrição</th>

                <th style="padding: 5px">Unidade</th>

                <th style="padding: 5px">Preço</th>
		</tr>
	
			<%! ArrayList<Produto> arrayProduto; %>
			<%  arrayProduto = (ArrayList<Produto>) request.getSession().getAttribute("arrayProdutos"); %>
			<%  for(int i = 0; i<arrayProduto.size();i++){ %>

                <tr>

                    <!--A tag out Ã© responsÃ¡vel por gerar uma String de saÃ­da na tela -->

                    <td align="center"><fmt:formatNumber value="<%= arrayProduto.get(i).getPrd_cd_produto() %>" pattern="00000"/></td>

                    <td><c:out value="<%= arrayProduto.get(i).getPrd_ds_produto() %>"/></td>
                    
                    <td align="center"><c:out value="<%= arrayProduto.get(i).getPrd_ds_unidade() %>"/></td>

					<td align="center"><fmt:formatNumber value="<%= arrayProduto.get(i).getPrd_vl_preco() %>" pattern="#,##0.00"/></td>


                </tr>

            <%}; %>

        </table>
        <hr></hr>
        
		<%@include file="includes_Geral/footer.jsp"%>

</body>
</html>
