<%@include file="../../includes_Geral/campoPesquisa_Nao_Mostrar.jsp"%> 
<%@include file="../../includes_Geral/menu_cabecalho.jsp"%>


<script type="text/javascript">


	function setAtributoIdCell(ths){
		strValueHiddenText = "";

		//id cliente
		col = ths.cells[0];
		strValueHiddenText += col.firstChild.nodeValue;
		
		document.getElementById("id_subusuario_hidden").setAttribute("Value", strValueHiddenText);
		
		
		
		col = ths.cells[1];
		strValueHiddenText = col.firstChild.nodeValue;
		$('#id_sbu_nm_subusuario_edt').val(strValueHiddenText);

		col = ths.cells[2];
		strValueHiddenText = col.firstChild.nodeValue;
		$('#id_sbu_ds_login_edt').val(strValueHiddenText);

		col = ths.cells[3];
		strValueHiddenText = col.firstChild.nodeValue;
		$('#id_sbu_fl_adm_edt').val(strValueHiddenText);

		col = ths.cells[4];
		strValueHiddenText = col.firstChild.nodeValue;
		$('#id_sbu_fl_caixa_edt').val(strValueHiddenText);

		col = ths.cells[5];
		strValueHiddenText = col.firstChild.nodeValue;
		$('#id_sbu_fl_cliente_edt').val(strValueHiddenText);
		
		col = ths.cells[6];
		strValueHiddenText = col.firstChild.nodeValue;
		$('#id_sbu_fl_financeiro_edt').val(strValueHiddenText);
		
		col = ths.cells[7];
		strValueHiddenText = col.firstChild.nodeValue;
		$('#id_sbu_fl_vendas_edt').val(strValueHiddenText);		
		
		
	};


	function refreshPageEditar(){	
		document.forms["hidden"].action = "EditarSubUsuarioADM.action";
        document.forms["hidden"].submit();
	};
	

	function FocusInput(id){		
		document.getElementById(id).focus();
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
	          OK: function() {
	        	  $( "#id_commit" ).attr("disabled", false);
	        	  $( "#id_sbu_nm_subusuario" ).css("background-color", 'IndianRed');
	        	  $( "#id_sbu_ds_login" ).css("background-color", 'IndianRed');
	        	 $( this ).dialog( "close" ); 

	          }
	        }
	      });
	  
	  
	    $( "#id_commit" ).on('click',(function( ){

			if (($( "#id_sbu_nm_subusuario" ).val() == '') || ($( "#id_sbu_ds_login" ).val() == '')	){
				$( "#dialog-form-alerta" ).dialog( "open" );
			}else{
				document.forms['cadastroSubUsuario'].submit();
			};  	  	    
			
  	    }));
    
	    
	    
	    $( "#dialog-form-alerta-apagar" ).dialog({
	        autoOpen: false,
	        height: 180,
	        width: 320,
	        modal: true,
	        buttons: {
	          Sim: function() {
	        	  chamaServlet('hidden','ApagarSubUsuarioADM.action');
	        	 $( this ).dialog( "close" ); 

	          },Não: function() {
		        	 $( this ).dialog( "close" ); 
		      }
	        }
	      });
	  
	  
	    $( ".id_apagar" ).on('click',(function( ){
	    	$('#id_p').text( $('#id_sbu_nm_subusuario_edt').val());
			$( "#dialog-form-alerta-apagar" ).dialog( "open" );
			
  	    }));
	    
	    
	    $( "#dialog-form-editar-usuario" ).dialog({
	        autoOpen: false,
	        height: 510,
	        width: 320,
	        modal: true,
	        buttons: {
	          Confirmar: function() {
	        	jQuery('.ui-dialog button:nth-child(1)').button('disable');  
	        	
	        	  
	            if( ($("#id_sbu_nm_subusuario_edt").val() == '') || ($("#id_sbu_ds_login_edt").val() == '') ){
		            $('#id_label_msg_editar_usuario').text('Favor preencher todos os campos necessários...');
		            $( "#id_sbu_nm_subusuario_edt" ).css("background-color", 'IndianRed');
		        	$( "#id_sbu_ds_login_edt" ).css("background-color", 'IndianRed');
	            }else{
					chamaServlet('Usuario_edt','ConfirmaEditarSubUsuarioADM.action');
	            }
	            
	          },          
	          Cancelar: function() {	        	  
	          	$( this ).dialog( "close" ); 
	          }
	        }
	      });
	    
	    
	    $( ".editar_usuario_button_class" ).on('click',(function( ){id_sbu_cd_subusuario
	    	$('#id_sbu_cd_subusuario').val($('#id_subusuario_hidden').val());
	    	$('#id_label_msg_editar_usuario').text('');
			$("#dialog-form-editar-usuario" ).dialog( "open" );
			
  	    }));
	    
	    //reajusta o modulo em que a pagina se refere...
	    $("#id_menu_geral").addClass('selected');
	    $( "#templatemo_menu" ).on('mouseout',(function( ){
	    	$("#id_menu_geral").addClass('selected');
	    })); 
	    
	    
  });


  </script>


<div id="dialog-form-editar-usuario" title="Editar Usuário" class="dialog-position-top">
  <p class="validateTips">Informe os novos valores...</p>
  <form name="Usuario_edt" action="ConfirmaEditarSubUsuarioADM.action"  method="post" >
  <div align="left" class="div_editar_subusuario">
	<input type="text" name="sbu_cd_subusuario" id="id_sbu_cd_subusuario" class="produto_escolhido_edt" style="visibility: hidden;"></input>	
    <br> 
    Nome 
    <br> 
    <input maxlength="45" id="id_sbu_nm_subusuario_edt" name="sbu_nm_subusuario_edt" type="text" style=" width : 270px;"></input>
    <br> 
    Login 
    <br> 
    <input maxlength="45" name="sbu_ds_login_edt" id="id_sbu_ds_login_edt" type="text" style=" width : 270px;" ></input>
     <br> 
    Administrador
			<select name="sbu_fl_adm_edt" id="id_sbu_fl_adm_edt"	style="width: 270px;" >
        		<option value="N"  >Não</option>
        		<option value="S" >Sim</option>
        	</select>
     <br>   	
    Modulo Caixa
    		<select name="sbu_fl_caixa_edt" id="id_sbu_fl_caixa_edt"	style="width: 270px;" >
        		<option value="N" >Não</option>
        		<option value="S" >Sim</option>
        	</select>
     <br>     	
    Modulo Cliente    		
			<select name="sbu_fl_cliente_edt" id="id_sbu_fl_cliente_edt"	style="width: 270px;" >
        		<option value="N">Não</option>
        		<option value="S" >Sim</option>
        	</select>  
     <br>    	   
    Modulo Financeiro
			<select name="sbu_fl_financeiro_edt" id="id_sbu_fl_financeiro_edt"	style="width: 270px;" >
        		<option value="N"  >Não</option>
        		<option value="S" >Sim</option>
        	</select>
     <br>     	
    Modulo Vendas    		
			<select name="sbu_fl_vendas_edt" id="id_sbu_fl_vendas_edt"	style="width: 270px;" >
        		<option value="N" >Não</option>
        		<option value="S" >Sim</option>
        	</select>
	<label id="id_label_msg_editar_usuario"></label>
	</div>
  </form>
</div> 


<body onload="FocusInput('id_sbu_nm_subusuario')">


 <div id="dialog-form-alerta" title="Alerta..." class="dialog-position-top">
  <p class="validateTips">Favor preencher todos os campos necessários...</p>
</div>

 <div id="dialog-form-alerta-apagar" title="Alerta..." class="dialog-position-top">
	<p class="validateTips">Deseja relmente remover o usuário abaixo ?</p>
		<label style="font-family: fantasy;font-style: italic;font-size: 16px" id="id_p"></label>
	
</div>


<form  name="cadastroSubUsuario" action="CadastrarSubUsuarioADM.action" method="post"> 
       
		<div >
		<h6></h6> 			
   <!-- Lista de Produtos Serdo -->
   <h3>Cadastro de Usuarios</h3>

        <hr></hr>

        <!-- CADASTRO DE Tipos de contas, tais como cheque, duplicata e etc... -->

        
        <table align="center" cellpadding="5" cellspacing="5"  style="font-size: larger;" >
        
        <tr>
        <td>Nome :</td>
		<td><input maxlength="45" id="id_sbu_nm_subusuario" name="sbu_nm_subusuario" type="text" style=" width : 270px;"></input></td>
        </tr>
        
        <tr>
        <td>Login :</td>
		<td><input maxlength="45" name="sbu_ds_login" id="id_sbu_ds_login" type="text" style=" width : 270px;"></input></td>
        </tr>        
		
		<tr>
		<td>Administrador</td>
		<td>
			<select name="sbu_fl_adm" id="id_sbu_fl_adm"	style="width: 270px;" >
        		<option value="N" selected="selected" >Não</option>
        		<option value="S" >Sim</option>
        	</select>
		</td>
		</tr>
		
		<tr>
		<td>Modulo Caixa</td>
		<td>
			<select name="sbu_fl_caixa" id="id_sbu_fl_caixa"	style="width: 270px;" >
        		<option value="N" selected="selected" >Não</option>
        		<option value="S" >Sim</option>
        	</select>
		</td>
		</tr>

		<tr>
		<td>Modulo Cliente</td>
		<td>
			<select name="sbu_fl_cliente" id="id_sbu_fl_cliente"	style="width: 270px;" >
        		<option value="N" selected="selected" >Não</option>
        		<option value="S" >Sim</option>
        	</select>
		</td>
		</tr>

		<tr>
		<td>Modulo Financeiro</td>
		<td>
			<select name="sbu_fl_financeiro" id="id_sbu_fl_financeiro"	style="width: 270px;" >
        		<option value="N" selected="selected" >Não</option>
        		<option value="S" >Sim</option>
        	</select>
		</td>
		</tr>

		<tr>
		<td>Modulo Vendas</td>
		<td>
			<select name="sbu_fl_vendas" id="id_sbu_fl_vendas"	style="width: 270px;" >
        		<option value="N" selected="selected" >Não</option>
        		<option value="S" >Sim</option>
        	</select>
		</td>
		</tr>

		<tr >
		<td></td>
		<td align="center" >
		<input type="button" id="id_commit" name="commit" onclick="this.form['commit'].disabled=true ;" value="  Cadastrar  ">
		</td>
		</tr>

				
		</table>
		
		
		<img src="images/pages_icon.png"  hspace="10px" width="20px" alt="" align="left" />
		<label style="font-size: 14px;font-style: oblique;">* Todo usuário novo tem a senha padrão 123</label>
		<br></br>
		
		
		<hr></hr>	

		</div>
</form>


<div align="center">
	<form id="live-search" action="" class="styled" method="post">
	    <fieldset>
	    	<h6>Busca dinâmica :
	        <input type="text" title="Escreva para buscar..."  class="text-input" id="filter" value="" /></h6>
	        <span id="filter-count"></span>
	    </fieldset>
	</form>
</div>
			<table border="1" align="center"
				style="font-size: larger; font: bold;" cellpadding="8"
				cellspacing="5">
				
			<tr bgcolor="#006666">
				<!-- <thead> -->
				<th style="visibility: hidden;"></th>

				<th style="padding: 5px">Nome</th>

				<th style="padding: 5px"><label>Login</th>
				
				<th style="padding: 5px"><label>Administrador</th>
				
				<th style="padding: 5px">Caixa</th>

				<th style="padding: 5px"><label>Cliente</th>
				
				<th style="padding: 5px"><label>Financeiro</th>
				
				<th style="padding: 5px">Venda</th>
				
			</tr>


				<%! ArrayList<SubUsuario> arraySubUsuario; %>
				<%  arraySubUsuario = (ArrayList<SubUsuario>) request.getSession().getAttribute("arraySubUsuario"); %>
				<%  for(int i = 0; i<arraySubUsuario.size();i++){ %>

					<tr onmouseover="setAtributoIdCell(this)">

						<td style="visibility: hidden;"><c:out value="<%=arraySubUsuario.get(i).getSbu_cd_subusuario() %>" /></td>

						<td><c:out value="<%=arraySubUsuario.get(i).getSbu_nm_subusuario() %>" /></td>

						<td><c:out value="<%=arraySubUsuario.get(i).getSbu_ds_login() %>" /></td>

						<td align="center"><c:out value="<%=arraySubUsuario.get(i).getSbu_fl_adm() %>" /></td>
						
						<td align="center"><c:out value="<%=arraySubUsuario.get(i).getSbu_fl_modulo_caixa() %>" /></td>
						
						<td align="center"><c:out value="<%=arraySubUsuario.get(i).getSbu_fl_modulo_cliente() %>" /></td>
						
						<td align="center"><c:out value="<%=arraySubUsuario.get(i).getSbu_fl_modulo_financeiro() %>" /></td>
						
						<td align="center"><c:out value="<%=arraySubUsuario.get(i).getSbu_fl_modulo_venda() %>" /></td>
						
						<td align="center">
							<input type="button" value="Editar" class="editar_usuario_button_class"/>	
							<input type="button" class="id_apagar" value="Apagar"/>
						</td>
					
					</tr>

				<%}; %>
			</table>

				
<form name="hidden" id="id_hidden" method="post">
		<input type="text" name="name_subusuario_hidden"
			id="id_subusuario_hidden" style="visibility: hidden"></input>
</form>			
			<hr></hr>
						
		<%@include file="../../includes_Geral/footer.jsp"%>
		


</body>
</html>