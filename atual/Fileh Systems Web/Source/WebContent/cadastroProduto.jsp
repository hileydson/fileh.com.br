<%@include file="includes_Geral/campoPesquisa_Nao_Mostrar.jsp"%> 
<%@include file="includes_Geral/menu_cabecalho.jsp"%>

  <script>

  
  $(document).ready(function() {

	    $( "#dialog-form-alerta" ).dialog({
	        autoOpen: false,
	        height: 160,
	        width: 300,
	        modal: true,
	        buttons: {
	          OK: function() {
	        	  $( "#id_commit" ).attr("disabled", false);
	        	  
		          $( "#id_PRD_DS_PRODUTO" ).css("background-color", 'IndianRed');
		          $( "#id_PRD_DS_UNIDADE" ).css("background-color", 'IndianRed');
		          $( "#id_PRD_NR_ESTOQUE" ).css("background-color", 'IndianRed');
		          $( "#id_PRD_VL_PRECO" ).css("background-color", 'IndianRed');
		        	 
	        	 $( this ).dialog( "close" ); 

	          }
	        }
	      });
	  
	  
	    $( "#id_commit" ).on('click',(function( ){

			if (	($( "#id_PRD_DS_PRODUTO" ).val() == '')||($( "#id_PRD_DS_UNIDADE" ).val() == '-1')
					||($( "#id_PRD_VL_PRECO" ).val() <= 0) ||($( "#id_PRD_VL_PRECO" ).val() == '0,00')	){
				
				$( "#dialog-form-alerta" ).dialog( "open" );
			
			}else{
				document.forms['cadastroProduto'].submit();
			};  	  	    
			
  	    }));
	    
	    //reajusta o modulo em que a pagina se refere...
	    $("#id_menu_vendas").addClass('selected');
	    $( "#templatemo_menu" ).on('mouseout',(function( ){
	    	$("#id_menu_vendas").addClass('selected');
	    })); 
    	  
  });


  </script>



<body onload="$('#id_PRD_DS_PRODUTO').focus();">

 <div id="dialog-form-alerta" title="Alerta...">
  <p class="validateTips">Favor preencher todos os campos necessários...</p>
</div>

<form  name="cadastroProduto" action="CadastrarProduto.action" method="post"> 

           
		<div >
		<h6></h6> 			
   <!-- Lista de Produtos Serdo -->
   <h3>Cadastro de Produto/Serviço</h3>

        <hr></hr>



        <table align="center" cellpadding="5" cellspacing="5"  style="font-size: larger;" >
        
        <tr>
        <td>Descrição :</td>
		<td><input maxlength="45" id="id_PRD_DS_PRODUTO" name="PRD_DS_PRODUTO" type="text" style=" width : 270px;"class="uppercase"></input></td>
		</tr>
		
        <tr>
        <td>Unidade :</td>
		
		
		<td>
				<select title="Para Serviço escolha 'OS' " id="id_PRD_DS_UNIDADE" name="PRD_DS_UNIDADE"		style="width: 270px;" >
		
					<option value="-1" selected="selected">Escolha a unidade de medida...</option>
					
	        		<option >BD</option>
	        		<option >CE</option>
	        		<option >CH</option>
	        		<option >CJ</option>
	        		<option >CT</option>
	        		<option >CX</option>
	        		<option >DZ</option>
	        		<option >ED</option>
	        		<option >FL</option>
	        		<option >GL</option>
	        		<option >JG</option>
	        		<option >K</option>
	        		<option >KG</option>
	        		<option >KI</option>
	        		<option >LA</option>
	        		<option >LT</option>
	        		<option >M</option>
	        		<option >MT</option>
	        		<option >MC</option>
	        		<option >ME</option>
	        		<option >ML</option>
	        		<option >M2</option>
	        		<option >M3</option>
	        		<option >ML</option>
	        		<option >OS</option>
	        		<option >UM</option>
	        		<option >UN</option>	
	        		<option >UU</option>        		
	        		<option >PA</option>
	        		<option >PC</option>
	        		<option >PR</option>
	        		<option >PT</option>
	        		<option >RL</option>
	        		<option >SC</option>
	        		<option >T</option>
	        		<option >TB</option>
	        		<option >TN</option>
	        		<option >VR</option>
	        		<option >YN</option>
	        		
			</select>
		
		</td>
		
		
		
        </tr>		
		
		<tr>
		<td>Estoque :</td>
		<td><input title="Caso seja um serviço, pode-se manter zero..." maxlength="6" id="id_PRD_NR_ESTOQUE" name="PRD_NR_ESTOQUE" value="0" type="text" style=" width : 80px;"class="numberOnlyN"  ></input></td>
		</tr>
		
		<tr>
		<td>Valor :</td>
		<td><input maxlength="21" id="id_PRD_VL_PRECO" name="PRD_VL_PRECO" value="0,00" type="text" style=" width : 80px;" class="realN" ></input></td>
		</tr>
		

		
		<tr>
		<td></td>
		<td align="center" >
		<input  type="button" id="id_commit" name="commit" onclick="this.form['commit'].disabled=true ; " value="  Confirmar  ">
		</td>
		</tr>
		
		
		</table>
		

		<hr></hr>
	</div>
</form>


	<%@include file="includes_Geral/footer.jsp"%>
</body>
</html>