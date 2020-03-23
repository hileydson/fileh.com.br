<%@include file="includes_Geral/campoPesquisa_Nao_Mostrar.jsp"%> 
<%@include file="includes_Geral/menu_cabecalho.jsp"%>



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

	        	 $( "#ID_ENT_NM_ENTIDADE" ).css("background-color", 'IndianRed');
	        	  
	        	 $( this ).dialog( "close" ); 

	          }
	        }
	      });
	    $( "#id_commit" ).on('click',(function( ){

			if ($( "#ID_ENT_NM_ENTIDADE" ).val() == ''){
				$( "#dialog-form-alerta" ).dialog( "open" );
			}else{
				document.forms['editarEntidade'].submit();
			};  	  	    
			
  	    }));
	    
	    $("#ID_ENT_NM_ENTIDADE").bind("keypress", function(e) {
	        if (e.keyCode == 13) {
	           return false;
	        }
	     });
	    
	    
	    //reajusta o modulo em que a pagina se refere...
	    $("#id_menu_cliente").addClass('selected');
	    $( "#templatemo_menu" ).on('mouseout',(function( ){
	    	$("#id_menu_cliente").addClass('selected');
	    })); 
    	  
  });

  </script>
    
<%!Entidade entidade ;%>
<%entidade = (Entidade) request.getSession().getAttribute("entidade"); %>
    
    
    
<body>

 <div id="dialog-form-alerta" title="Alerta...">
  <p class="validateTips">Favor preencher todos os campos necessários...</p>
</div>

<form  name="editarEntidade" action="ConfirmaEditarEntidade.action" method="post"> 
       
		<div >
		<h6></h6> 			
   <!-- Lista de Produtos Serdo -->
   <h3>Editar Entidade</h3>

        <hr></hr>
        
        <table align="center" cellpadding="5" cellspacing="5"  style="font-size: larger;" >
        <tr>
        <td>Descrição :</td>
		<td><input value="<%= entidade.getEnt_nm_entidade() %>" maxlength="45" id="ID_ENT_NM_ENTIDADE" name="ENT_NM_ENTIDADE" type="text" style=" width : 270px;"class="uppercase"></input></td>
        </tr>
        
       
		<tr >
			<td>				
			</td>
			<td align="center" >
				<a href="cadastroEntidade.jsp"><input type="button" value="  Voltar  " ></input></a>
				<!-- disabilita o commit para não clicar mais de 1 vez... -->
				<input type="button" id="id_commit" name="commit" onclick="this.form['commit'].disabled=true ;" value="  Confirmar  " >
			</td>
		</tr>
		
		</table>
<hr></hr>
		</div>
</form>

	<%@include file="includes_Geral/footer.jsp"%>
</body>
</html>