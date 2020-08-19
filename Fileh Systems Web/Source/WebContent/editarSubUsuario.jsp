<%@include file="includes_Geral/campoPesquisa_Nao_Mostrar.jsp"%> 
<%@include file="includes_Geral/menu_cabecalho.jsp"%>


  <script>
  $(document).ready(function() {
	    $('#ID_SBU_SH_SUBUSUARIO').on('focus',(function(){
	    		$('#ID_SBU_SH_SUBUSUARIO').val('');
	    }));
	  
	    $( "#dialog-form-alerta" ).dialog({
	        autoOpen: false,
	        height: 170,
	        width: 280,
	        modal: true,
	        buttons: {
	          OK: function() {
	        	 $( "#id_commit" ).attr("disabled", false);

	        	 $( "#ID_SBU_NM_SUBUSUARIO" ).css("background-color", 'IndianRed');
	        	 $( "#ID_SBU_DS_LOGIN" ).css("background-color", 'IndianRed');
	        	  
	        	 $( this ).dialog( "close" ); 

	          }
	        }
	      });
	    $( "#id_commit" ).on('click',(function( ){

			if (($( "#ID_SBU_NM_SUBUSUARIO" ).val() == '') || ($( "#ID_SBU_DS_LOGIN" ).val() == '')){
				$( "#dialog-form-alerta" ).dialog( "open" );
			}else{
				
				$( "#dialog-form-confirmar" ).dialog( "open" );
				
			};  	  	    
			
  	    }));
	    

	    $( "#dialog-form-confirmar" ).dialog({
	        autoOpen: false,
	        height: 200,
	        width: 300,
	        resizable: false,
	        modal: true,
	        position: "top",
	        buttons: {
	          Confirmar: function() {	        	  	  	        	  
	    		//valida se a senha atual está correta...
	    		
				if($('#id_senha_atual_confirmar').val() == $('#id_hidden_password').val()){
					if($('#ID_SBU_SH_SUBUSUARIO').val() == ''){
						$('#ID_SBU_SH_SUBUSUARIO').val($('#id_hidden_password').val());
					};
					$('#ID_SBU_SH_SUBUSUARIO').prop('disabled',false);
					document.forms['editarSubUsuario'].submit();
				}else{
					$('#id_label_msg_senha_confirmar').text('Senha atual inválida!');
				}

	          },          
	          Cancelar: function() {
	        	$('#dialog-form-trocar-usuario').css('visibility', 'invisible');
	          	$( this ).dialog( "close" ); 
	          }
	        }
	      });
	    
	    $('#id_modificar_senha_button').on('click', (function(){
	    	$(this).prop('disabled',true);
	    	$('#ID_SBU_SH_SUBUSUARIO').prop('disabled',false);
	    }));
	    
	    //reajusta o modulo em que a pagina se refere...
	    $("#id_menu_usuario").addClass('selected');
	    $( "#templatemo_menu" ).on('mouseout',(function( ){
	    	$("#id_menu_usuario").addClass('selected');
	    })); 
    	  
  });

  </script>
    

    

<body >

 <div id="dialog-form-alerta" title="Alerta...">
  <p class="validateTips">Preencha todos os campos necessários...</p>
</div>


<div id="dialog-form-confirmar" title="Informe a senha atual para continuar..." align="center" >

	<br>
	<h4 align="left">Senha Atual : </h4><input  maxlength="30"   name="name_senha_atual_confirmar"  id="id_senha_atual_confirmar" class="seleciocar_texto_click" 
									type="password" style=" width : 251px; right: auto; " ></input>
									

	<label id="id_label_msg_senha_confirmar" style="color: red;"></label>
	
</div> 

<form  name="editarSubUsuario" action="ConfirmaEditarSubUsuario.action" method="post"> 
       
		<div >
		<h6></h6> 			
   <h3>Editar Usuário</h3>

        <hr></hr>

        
        <table align="center" cellpadding="5" cellspacing="5"  style="font-size: larger;" >
        <tr>
        <td>Nome :</td>
		<td><input value="<%= subusuario.getSbu_nm_subusuario() %>" maxlength="45" id="ID_SBU_NM_SUBUSUARIO" name="SBU_NM_SUBUSUARIO" type="text" style=" width : 270px;"></input></td>
        </tr>
        
        <tr>
        <td>Login :</td>
		<td><input value="<%= subusuario.getSbu_ds_login() %>" maxlength="45"  id="ID_SBU_DS_LOGIN" name="SBU_DS_LOGIN" type="text" style=" width : 270px;"></input></td>
        </tr>
        
        <tr>
        <td>Nova senha :</td>
		<td>
			<input disabled="disabled" maxlength="30"  id="ID_SBU_SH_SUBUSUARIO" name="SBU_SH_SUBUSUARIO" type="password" style="width: 270px" ></input>
			<input  id="id_modificar_senha_button" type="button" value="   Modificar...   " ></input>

		</td>
        </tr>
        
        <tr>
        <td>Mensagem Personalizada :</td>
		<td><textarea maxlength="97" rows="5"  id="ID_SBU_DS_MSG_FOOTER" name="SBU_DS_MSG_FOOTER"  style=" width : 380px;resize:none;"><%= subusuario.getSbu_ds_msg_footer() %></textarea></td>
        </tr>                
        
       
		<tr >
			<td>				
			</td>
			<td align="center" >
				<!-- disabilita o commit para não clicar mais de 1 vez... -->
				<input type="button" id="id_commit" name="commit"  value="  Confirmar  ">
			</td>
		</tr>
		
		</table>
		
		<input  value="<%= subusuario.getSbu_sh_subusuario() %>" disabled="disabled"  id="id_hidden_password" type="password" readonly style=" width: 270px; visibility: hidden;" ></input>
		
<hr></hr>
		</div>
</form>



	<%@include file="includes_Geral/footer.jsp"%>
</body>
</html>