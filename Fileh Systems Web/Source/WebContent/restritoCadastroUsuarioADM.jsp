<%@include file="includes_Geral/menu_cabecalho_principal_restrito.jsp"%>


<script type="text/javascript">


	function setAtributoIdCell(ths){
		strValueHiddenText = "";
		$('#id_usu_nm_usuario_edt').val('');
		$('#id_usu_ds_fantasia_edt').val('');
		$('#id_usu_ds_logradouro_edt').val('');
		$('#id_usu_ds_Numero_edt').val('');
		$('#id_usu_ds_bairro_edt').val('');
		$('#id_usu_ds_cidade_edt').val('');
		$('#id_usu_ds_uf_edt').val('');
		$('#id_usu_ds_tel_edt').val('');
		$('#id_usu_ds_subdominio_edt').val('');
		$('#id_usu_nr_cnpj_edt').val('');	
		$('#id_usu_ds_email_edt').val('');
		
		//id cliente
		col = ths.cells[0];
		strValueHiddenText += col.firstChild.nodeValue;
		
		document.getElementById("id_usuario_hidden").setAttribute("Value", strValueHiddenText);
		
		$('#id_usu_cd_usuario').val(strValueHiddenText);
		
		col = ths.cells[1];
		strValueHiddenText = col.firstChild.nodeValue;
		$('#id_usu_nm_usuario_edt').val(strValueHiddenText);
		
		col = ths.cells[2];
		strValueHiddenText = col.firstChild.nodeValue;		
		$('#id_usu_ds_fantasia_edt').val(strValueHiddenText);
		
		col = ths.cells[3];
		strValueHiddenText = col.firstChild.nodeValue;
		$('#id_usu_ds_logradouro_edt').val(strValueHiddenText);
		
		col = ths.cells[4];
		strValueHiddenText = col.firstChild.nodeValue;
		$('#id_usu_ds_Numero_edt').val(strValueHiddenText);
		
		col = ths.cells[5];
		strValueHiddenText = col.firstChild.nodeValue;		
		$('#id_usu_ds_bairro_edt').val(strValueHiddenText);

		col = ths.cells[6];
		strValueHiddenText = col.firstChild.nodeValue;		
		$('#id_usu_ds_cidade_edt').val(strValueHiddenText);
		
		col = ths.cells[7];
		strValueHiddenText = col.firstChild.nodeValue;		
		$('#id_usu_ds_uf_edt').val(strValueHiddenText);
		
		col = ths.cells[8];
		strValueHiddenText = col.firstChild.nodeValue;			
		$('#id_usu_ds_tel_edt').val(strValueHiddenText);	
		
		col = ths.cells[9];
		strValueHiddenText = col.firstChild.nodeValue;		
		$('#id_usu_ds_subdominio_edt').val(strValueHiddenText);
		
		col = ths.cells[10];
		strValueHiddenText = col.firstChild.nodeValue;		
		$('#id_usu_ds_email_edt').val(strValueHiddenText);
		
		col = ths.cells[11];
		strValueHiddenText = col.firstChild.nodeValue;		
		$('#id_usu_nr_cnpj_edt').val(strValueHiddenText);	

		col = ths.cells[12];
		strValueHiddenText = col.firstChild.nodeValue;		
		$('#id_usu_cd_plano_edt').val(strValueHiddenText);
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
		            $( "#id_usu_nm_usuario" ).css("background-color", 'IndianRed');
		        	$( "#id_usu_ds_fantasia" ).css("background-color", 'IndianRed');
		        	$( "#id_usu_ds_logradouro" ).css("background-color", 'IndianRed');
		        	$( "#id_usu_ds_Numero" ).css("background-color", 'IndianRed');
		        	$( "#id_usu_ds_bairro" ).css("background-color", 'IndianRed');
		        	$( "#id_usu_ds_cidade" ).css("background-color", 'IndianRed');
		        	$( "#id_usu_ds_uf" ).css("background-color", 'IndianRed');
		        	$( "#id_usu_ds_tel" ).css("background-color", 'IndianRed');
		        	$( "#id_usu_ds_subdominio" ).css("background-color", 'IndianRed');
		        	$( "#id_usu_nr_cnpj" ).css("background-color", 'IndianRed');
		        	$( "#id_usu_ds_email" ).css("background-color", 'IndianRed');
		        	$( "#id_usu_cd_plano" ).css("background-color", 'IndianRed');
	        	 $( this ).dialog( "close" ); 

	          }
	        }
	      });
	  
	  
	    $( "#id_commit" ).on('click',(function( ){

            if( ($("#id_usu_nm_usuario").val() == '') || ($("#id_usu_ds_fantasia").val() == '') || ($("#id_usu_ds_logradouro").val() == '') ||
	            	($("#id_usu_ds_Numero").val() == '') || ($("#id_usu_ds_bairro").val() == '') || ($("#id_usu_ds_cidade").val() == '') ||
	            	($("#id_usu_ds_uf").val() == '') || ($("#id_usu_ds_tel").val() == '') || ($("#id_usu_ds_subdominio").val() == '') ||
	            	($("#id_usu_ds_email").val() == '') || ($("#id_usu_nr_cnpj").val() == '') || ($("#id_usu_cd_plano").val() == '') ){
            	
				$( "#dialog-form-alerta" ).dialog( "open" );
			}else{
				document.forms['cadastroUsuario'].submit();
			};  	  	    
			
  	    }));
    
	    
	    
	    $( "#dialog-form-alerta-apagar" ).dialog({
	        autoOpen: false,
	        height: 180,
	        width: 320,
	        modal: true,
	        buttons: {
	          Sim: function() {
	        	  chamaServlet('hidden','RestritoApagarUsuarioADM.action');
	        	 $( this ).dialog( "close" ); 

	          },Não: function() {
		        	 $( this ).dialog( "close" ); 
		      }
	        }
	      });
	  
	  
	    $( ".id_apagar" ).on('click',(function( ){
	    	$('#id_p').text( $('#id_usu_nm_usuario_edt').val());
			$( "#dialog-form-alerta-apagar" ).dialog( "open" );
			
  	    }));
	    
	    
	    $( "#dialog-form-editar-usuario" ).dialog({
	        autoOpen: false,
	        height: 700,
	        width: 320,
	        modal: true,
	        dialogClass: 'dlgfixed',
			position: "top",
	        buttons: {
	          Confirmar: function() {
	        	  
	        	
	        	  
	            if( ($("#id_usu_nm_usuario_edt").val() == '') || ($("#id_usu_ds_fantasia_edt").val() == '') || ($("#id_usu_ds_logradouro_edt").val() == '') ||
	            	($("#id_usu_ds_Numero_edt").val() == '') || ($("#id_usu_ds_bairro_edt").val() == '') || ($("#id_usu_ds_cidade_edt").val() == '') ||
	            	($("#id_usu_ds_uf_edt").val() == '') || ($("#id_usu_ds_tel_edt").val() == '') || ($("#id_usu_ds_subdominio_edt").val() == '') ||
	            	($("#id_usu_ds_email_edt").val() == '') || ($("#id_usu_nr_cnpj_edt").val() == '') || ($("#id_usu_cd_plano_edt").val() == '')){
	            	
		            	
			            $('#id_label_msg_editar_usuario').text('Favor preencher todos os campos necessários...');
			            
			            $( "#id_usu_nm_usuario_edt" ).css("background-color", 'IndianRed');
			        	$( "#id_usu_ds_fantasia_edt" ).css("background-color", 'IndianRed');
			        	$( "#id_usu_ds_logradouro_edt" ).css("background-color", 'IndianRed');
			        	$( "#id_usu_ds_Numero_edt" ).css("background-color", 'IndianRed');
			        	$( "#id_usu_ds_bairro_edt" ).css("background-color", 'IndianRed');
			        	$( "#id_usu_ds_cidade_edt" ).css("background-color", 'IndianRed');
			        	$( "#id_usu_ds_uf_edt" ).css("background-color", 'IndianRed');
			        	$( "#id_usu_ds_tel_edt" ).css("background-color", 'IndianRed');
			        	$( "#id_usu_ds_subdominio_edt" ).css("background-color", 'IndianRed');
			        	$( "#id_usu_nr_cnpj_edt" ).css("background-color", 'IndianRed');
			        	$( "#id_usu_ds_email_edt" ).css("background-color", 'IndianRed');
			        	$( "#id_usu_cd_plano_edt" ).css("background-color", 'IndianRed');
		        	
	            }else{
	            	jQuery('.ui-dialog button:nth-child(1)').button('disable');
					chamaServlet('Usuario_edt','RestritoConfirmaEditarUsuarioADM.action');
	            }
	            
	          },          
	          Cancelar: function() {	        	  
	          	$( this ).dialog( "close" ); 
	          }
	        }
	      });
	    
	    
	    $( ".editar_usuario_button_class" ).on('click',(function( ){
	    	//$('#id_usu_cd_usuario').val($('#id_subusuario_hidden').val());
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
  Informe os novos valores...
  <form name="Usuario_edt" action="RestritoConfirmaEditarUsuarioADM.action"  method="post" >
  <div align="left" class="div_editar_subusuario">
  
  	<br> 
    Código
    <br> 
	<input type="text" name="usu_cd_usuario" id="id_usu_cd_usuario" class="produto_escolhido_edt" readonly></input>	
    <br> 
    Nome 
    <br> 
    <input maxlength="45" id="id_usu_nm_usuario_edt" name="usu_nm_usuario_edt" type="text" style=" width : 270px;"></input>
    <br> 
    Fantasia 
    <br> 
    <input maxlength="45" id="id_usu_ds_fantasia_edt" name="usu_ds_fantasia_edt" type="text" style=" width : 270px;"></input>
    <br> 
    Logradouro 
    <br> 
    <input maxlength="45" name="usu_ds_logradouro_edt" id="id_usu_ds_logradouro_edt" type="text" style=" width : 270px;" ></input>
	<br> 
    Numero 
    <br> 
    <input maxlength="45" id="id_usu_ds_Numero_edt" name="usu_ds_Numero_edt" type="text" style=" width : 270px;"></input>
    <br> 
    Bairro 
    <br> 
    <input maxlength="45" name="usu_ds_bairro_edt" id="id_usu_ds_bairro_edt" type="text" style=" width : 270px;" ></input>
	<br> 
    Cidade 
    <br> 
    <input maxlength="45" id="id_usu_ds_cidade_edt" name="usu_ds_cidade_edt" type="text" style=" width : 270px;"></input>
    <br> 
    Uf 
    <br> 
    <input maxlength="45" name="usu_ds_uf_edt" id="id_usu_ds_uf_edt" type="text" style=" width : 270px;" ></input>
	<br> 
    Tel 
    <br> 
    <input maxlength="45" id="id_usu_ds_tel_edt" name="usu_ds_tel_edt" type="text" style=" width : 270px;"></input>
    <br> 
    Subdominio 
    <br> 
    <input maxlength="45" name="usu_ds_subdominio_edt" id="id_usu_ds_subdominio_edt" type="text" style=" width : 270px;" ></input>
	<br> 
    Email 
    <br> 
    <input maxlength="45" id="id_usu_ds_email_edt" name="usu_ds_email_edt" type="text" style=" width : 270px;"></input>
    <br>
    CNPJ 
    <br> 
    <input maxlength="45" id="id_usu_nr_cnpj_edt" name="usu_nr_cnpj_edt" type="text" style=" width : 270px;"></input>
    <br>
    Plano 
    <br> 
		<select name="usu_cd_plano_edt" id="id_usu_cd_plano_edt"		style="width: 270px;" >
		
					<option value="-1" selected="selected">Escolha o Plano...</option>
	        		<option value="6">Plano A+</option>
	        		<option value="5">Plano A</option>
	        		<option value="4">Plano B</option>
	        		<option value="3">Plano C</option>
	        		<option value="2">Plano D</option>
	        		<option value="1">Plano E</option>
	        		
			</select>
        	
        	
	<label id="id_label_msg_editar_usuario" style="color: red;"></label>
	</div>
  </form>
</div> 


<body onload="FocusInput('id_usu_nm_usuario')">


 <div id="dialog-form-alerta" title="Alerta..." class="dialog-position-top">
  <p class="validateTips">Favor preencher todos os campos necessários...</p>
</div>

 <div id="dialog-form-alerta-apagar" title="Alerta..." class="dialog-position-top">
	<p class="validateTips">Deseja relmente remover o Usuário/Empresa abaixo ?</p>
		<label style="font-family: fantasy;font-style: italic;font-size: 16px" id="id_p"></label>
	
</div>


<form  name="cadastroUsuario" action="RestritoCadastrarUsuarioADM.action" method="post"> 
       
		<div  align="center">
		<h6></h6> 			
   <!-- Lista de Produtos Serdo -->
   <h3>Cadastro de Usuarios/Empresas do Sistema</h3>

        <hr></hr>

        <!-- CADASTRO DE Tipos de contas, tais como cheque, duplicata e etc... -->
        
 
        
        <table style="font-size: larger;" >
        
        <tr>
        <td>Nome :</td>
		<td><input maxlength="45" id="id_usu_nm_usuario" name="usu_nm_usuario" type="text" style=" width : 270px;"></input></td>
        </tr>
        
        <tr>
        <td>Fantasia :</td>
		<td><input maxlength="45" name="usu_ds_fantasia" id="id_usu_ds_fantasia" type="text" style=" width : 270px;"></input></td>
        </tr>        
		
        <tr>
        <td>Logradouro :</td>
		<td><input maxlength="45" id="id_usu_ds_logradouro" name="usu_ds_logradouro" type="text" style=" width : 270px;"></input></td>
        </tr>
        
        <tr>
        <td>Numero :</td>
		<td><input maxlength="45" name="usu_ds_Numero" id="id_usu_ds_Numero" type="text" style=" width : 270px;"></input></td>
        </tr>  
        
        <tr>
        <td>Bairro :</td>
		<td><input maxlength="45" id="id_usu_ds_bairro" name="usu_ds_bairro" type="text" style=" width : 270px;"></input></td>
        </tr>
        
        <tr>
        <td>Cidade :</td>
		<td><input maxlength="45" name="usu_ds_cidade" id="id_usu_ds_cidade" type="text" style=" width : 270px;"></input></td>
        </tr>    
        
        <tr>
        <td>Uf :</td>
		<td><input maxlength="45" id="id_usu_ds_uf" name="usu_ds_uf" type="text" style=" width : 270px;"></input></td>
        </tr>
        
        <tr>
        <td>Tel :</td>
		<td><input maxlength="45" name="usu_ds_tel" id="id_usu_ds_tel" type="text" style=" width : 270px;"></input></td>
        </tr>        
		
        <tr>
        <td>Subdominio :</td>
		<td><input maxlength="45" id="id_usu_ds_subdominio" name="usu_ds_subdominio" type="text" style=" width : 270px;"></input></td>
        </tr>
        
        <tr>
        <td>Email :</td>
		<td><input maxlength="45" name="usu_ds_email" id="id_usu_ds_email" type="text" style=" width : 270px;"></input></td>
        </tr>  
        
        <tr>
        <td>CNPJ :</td>
		<td><input maxlength="45" id="id_usu_nr_cnpj" name="usu_nr_cnpj" type="text" style=" width : 270px;"></input></td>
        </tr>
  		
  		<tr>
        <td>Plano :</td>
		<td><select name="usu_cd_plano" id="id_usu_cd_plano"		style="width: 270px;" >
		
					<option value="-1" selected="selected">Escolha o Plano...</option>
	        		<option value="6">Plano A+ (6)</option>
	        		<option value="5">Plano A  (5)</option>
	        		<option value="4">Plano B  (4)</option>
	        		<option value="3">Plano C  (3)</option>
	        		<option value="2">Plano D  (2)</option>
	        		<option value="1">Plano E  (1)</option>
	        		
			</select></td>
		</tr>	
			
			
		<tr >
		<td></td>
		<td align="center" >
		<input type="button" id="id_commit" name="commit" onclick="this.form['commit'].disabled=true ;" value="  Cadastrar  ">
		</td>
		</tr>

				
		</table>
		
		
		<img src="images/pages_icon.png"  hspace="10px" width="20px" alt="" align="left" />
		<div align="left"><label  style="font-size: 14px;font-style: oblique; position: ">**É NECESSÁRIO PREENCHER TODOS OS CAMPOS**</label></div>
		<br></br>
		
		
		<hr></hr>	

		</div>
</form>


<div align="center">
			<table border="1" 
				style="font-size: larger; font: bold;">
				
			<tr bgcolor="#006666">
				<!-- <thead> -->
				<th style="padding: 5px">Código</th>

				<th style="padding: 5px">Nome</th>

				<th style="padding: 5px">Fantasia</th>
				
				<th style="padding: 5px">Logradouro</th>
				
				<th style="padding: 5px">Numero</th>

				<th style="padding: 5px">Bairro</th>
				
				<th style="padding: 5px">Cidade</th>
				
				<th style="padding: 5px">Uf</th>
				 
				<th style="padding: 5px">Tel</th>

				<th style="padding: 5px">Subdominio</th>
				
				<th style="padding: 5px">Email</th>
				
				<th style="padding: 5px">CNPJ</th> 	
							
				<th style="padding: 5px">Plano</th> 
			</tr>


				<%! ArrayList<Usuario> arrayUsuario; %>
				<%  arrayUsuario = (ArrayList<Usuario>) request.getSession().getAttribute("arrayUsuario"); %>
				<%  for(int i = 0; i<arrayUsuario.size();i++){ %>

					<tr onmouseover="setAtributoIdCell(this)">

						<td><c:out value="<%=arrayUsuario.get(i).getUsu_cd_usuario() %>" /></td> 

						<td><c:out value="<%=arrayUsuario.get(i).getUsu_nm_usuario() %>" /></td>

						<td><c:out value="<%=arrayUsuario.get(i).getUsu_ds_fantasia() %>" /></td>

						<td><c:out value="<%=arrayUsuario.get(i).getUsu_ds_logradouro() %>" /></td>
						
						<td><c:out value="<%=arrayUsuario.get(i).getUsu_ds_numero() %>" /></td>
						
						<td><c:out value="<%=arrayUsuario.get(i).getUsu_ds_bairro() %>" /></td>
						
						<td><c:out value="<%=arrayUsuario.get(i).getUsu_ds_cidade() %>" /></td>
						
						<td><c:out value="<%=arrayUsuario.get(i).getUsu_ds_uf() %>" /></td>
						
						<td><c:out value="<%=arrayUsuario.get(i).getUsu_ds_tel() %>" /></td>
						
						<td><c:out value="<%=arrayUsuario.get(i).getUsu_ds_subdominio() %>" /></td>
						
						<td><c:out value="<%=arrayUsuario.get(i).getUsu_ds_email() %>" /></td>
						
						<td><c:out value="<%=arrayUsuario.get(i).getUsu_nr_cnpj() %>" /></td>
						
						<td><c:out value="<%=arrayUsuario.get(i).getUsu_cd_plano() %>" /></td>
						
						<td align="center">
							<input type="button" value="Editar" class="editar_usuario_button_class"/>	
							<input type="button" class="id_apagar" value="Apagar"/>
						</td>
					
					</tr>

				<%}; %>
			</table>
			
			<%@include file="../../includes_Geral/footer_principal.jsp"%>
</div>
				
<form name="hidden" id="id_hidden" method="post">
		<input type="text" name="name_usuario_hidden"
			id="id_usuario_hidden" style="visibility: hidden"></input>
</form>			
			<hr></hr>
						
		
		


</body>
</html>