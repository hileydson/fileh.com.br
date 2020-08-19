  
  
<%@page import="entidades.SubUsuario"%>
<%@page import="entidades.Usuario"%>

<script type="text/javascript">$(".real").maskMoney({prefix:'R$ ', allowNegative: true, thousands:'.', decimal:',', affixesStay: false});</script>
<script type="text/javascript">$(".number").maskMoney({prefix:'', allowNegative: true, thousands:'', decimal:'.',affixesStay: false});</script>
<script type="text/javascript">$(".numberOnly").maskMoney({prefix:'', allowNegative: true, thousands:'', decimal:'', affixesStay: false});</script>

<script type="text/javascript">$(".realN").maskMoney({prefix:'R$ ', allowNegative: false, thousands:'.', decimal:',', affixesStay: false});</script>
<script type="text/javascript">$(".numberN").maskMoney({prefix:'', allowNegative: false, thousands:'', decimal:'.',affixesStay: false});</script>
<script type="text/javascript">$(".numberOnlyN").maskMoney({prefix:'', allowNegative: false, thousands:'', decimal:'', affixesStay: false});</script>

  <script>
  $(function() {
	  
    $( ".real" ).blur(
    	function() { if($(this).val() == ''){ $(this).val('0,00');} }
        );
    
    $( ".number" ).blur(
        	function() { if($(this).val() == ''){ $(this).val('0');} }
            );      
    
    $( ".numberOnly" ).blur(
        	function() { if($(this).val() == ''){ $(this).val('0');} }
            );     
    
    $( ".realN" ).blur(
        	function() { if($(this).val() == ''){ $(this).val('0,00');} }
            );
        
     $( ".numberN" ).blur(
         	function() { if($(this).val() == ''){ $(this).val('0');} }
             );      
     
     $( ".numberOnlyN" ).blur(
         	function() { if($(this).val() == ''){ $(this).val('0');} }
             );  
    
  });
  </script>
  
    <script>
  $(function() {
    var tooltips = $( "[title]" ).tooltip({
      position: {
        my: "left top",
        at: "right+5 top-5"
      }
    });
    $( "<button>" )
    .hide()
    .click(function() {
      tooltips.tooltip( "open" );
    })
    .insertAfter( "form" );
});
  </script>

    <div id="templatemo_bottom">
    	<div >
    		<%!SubUsuario subUsuario_footer; String msg; Usuario usuario_footer; String email_usuario_footer;%>
    		<%msg = " ";  email_usuario_footer = " ";%>
    		<%if ((session.getAttribute("subusuario") != null) && (!session.getAttribute("subusuario").toString().equalsIgnoreCase("erro"))  ){
    			subUsuario_footer =  (SubUsuario) session.getAttribute("subusuario"); 
    			usuario_footer =  (Usuario) session.getAttribute("usuario");
    			
    			if (subUsuario_footer.getSbu_ds_msg_footer() == null){ 
    				subUsuario_footer.setSbu_ds_msg_footer(" ");
    			}
    			
    			msg = subUsuario_footer.getSbu_nm_subusuario() + " <br><br> " + subUsuario_footer.getSbu_ds_msg_footer().replaceAll(System.getProperty("line.separator"), "<br>");  
    			email_usuario_footer = usuario_footer.getUsu_ds_email();
    		}%>
        	<h4 style="font-family: serif;"> <%=msg%> </h4>
            <ul class="footer_link">

			</ul>
        </div>
        <div class="cleaner"></div>
    </div>
    
    <div id="templatemo_footer">
    	Todos os direitos reservados © 2013 Fileh Systems Desenvolvido por Hileydson Luiz Cogo.
    </div>




<!-- ------------------------MSG's-INICIO----------------------------- -->	

  <script>

  
  $(document).ready(function() {
	  //selecionar os textos dos inputs ao clicar
	  $('.seleciocar_texto_click').on('click', (function(){
		  $(this).select();
	  }));	
	  
	    $( "#dialog-form-msg-cadastro" ).dialog({
	        autoOpen: false,
	        height: 320,
	        width: 300,
	        modal: true,
	        buttons: {
	          OK: function() {		        	 
	        	 $( this ).dialog( "close" ); 

	          }
	        }
	      });
	    
		//deixar todas as msgs no topo da pagina
	    $( '.button_disable_click_class').on('click',(function(){
			$(this).prop( "disabled", true );
		}));	
	    
		//deixar todas as msgs no topo da pagina
	    $( '.dialog-position-top').dialog({
			dialogClass: 'dlgfixed',
				position: "top"
		});		
		
		//torna as letras uppercase para o servidor
	    $('.uppercase').keyup(function(){
	    	$(this).val($(this).val().replace(/\+/g, ''));
	    	$(this).val($(this).val().replace(';', ''));
	   		//$(this).val($(this).val().toUpperCase());
	  	});
	    $('.textarea_limit').keyup(function(){
	    	$(this).val($(this).val().replace(/\+/g, ''));
	    	$(this).val($(this).val().replace(';', ''));
	   		//$(this).val($(this).val().toUpperCase());
	  	});
	    
	    
		  $('.trocar_usuario_class').on('click', (function(){
			  $('#dialog-form-trocar-usuario').css('visibility', 'visible');
			  $( "#dialog-form-trocar-usuario" ).dialog( "open" );
		  }));		
		  

	    $( "#dialog-form-trocar-usuario" ).dialog({
	        autoOpen: false,
	        height: 350,
	        width: 300,
	        resizable: false,
	        modal: true,
	        position: "top",
	        buttons: {
	          Confirmar: function() {
	        	jQuery('.ui-dialog button:nth-child(1)').button('disable');
	        	  	  	        	  
	    		//valida e troca de usuario
	    		$("#id_trocar_usuario_email").removeAttr('disabled');
				chamaServlet('trocar_usuario_form','LoginUsuario.action');

	          },          
	          Cancelar: function() {
	        	$('#dialog-form-trocar-usuario').css('visibility', 'invisible');
	          	$( this ).dialog( "close" ); 
	          }
	        }
	      });
	    
	    
	    
		//busca dinamica para listas em tables
	    $("#filter").keyup(function(){
	   	 
	        // Retrieve the input field text and reset the count to zero
	        var filter = $(this).val(), count = 0;
	 
	        // Loop through the table
	        $("table tr").each(function(){
	 
	            // If the list item does not contain the text phrase fade it out
	            if ($(this).text().search(new RegExp(filter, "i")) < 0) {
	                $(this).fadeOut();
	 
	            // Show the list item if the phrase matches and increase the count by 1
	            } else {
	                $(this).show();
	                count++;
	            }
	        });
	 
	        // Update the count
	        if ($(this).val() == ''){count = 0};
	        var numberItems = count;
	        $("#filter-count").text("Palavras encontradas = "+count);
	    });
		
	    $("#filter").bind("keypress", function(e) {
	        if (e.keyCode == 13) {
	           return false;
	        }
	     });
	    

		//descobre se o browser eh o internet explorer e sugere outros...
		if($.browser.msie == true) {
			alert('Internet Explorer Versão:' + $.browser.version + " \n\n O Fileh Systems não funciona corretamente com navegador Internet Explorer... favor instalar outro navegador e entrar novamente...");

		};
	   	  
  });

  </script>

	<%
	//mensagem padrão de cadastro efetuado com sucesso
	if(request.getSession().getAttribute("msg_cadastro") != null  ){%>
	
	<div id="dialog-form-msg-cadastro" title="Alerta..." style="visibility: hidden;" class="dialog-position-top">
		<label id="label_msg_cadastro_hidden" ><%= request.getSession().getAttribute("msg_cadastro").toString() %>  </label>
  	</div>
		
		  <script>
		  $(document).ready(function() { 
			  $('#dialog-form-msg-cadastro').css('visibility', 'visible');	
			  $( "#dialog-form-msg-cadastro" ).dialog( "open" );   
		  });
		  </script>
		  

	<%
		request.getSession().setAttribute("msg_cadastro", null);
	};
	%>
	

	
	

  

	
<div id="dialog-form-trocar-usuario" title="Deseja trocar de usuário ?" align="center" style="visibility: hidden;">
  <form name="trocar_usuario_form"  method="post" >
  
	<br>
	<h4 align="left">Email : </h4><input disabled="disabled" maxlength="30"   name="input_text_email_usuario"  id="id_trocar_usuario_email" class="seleciocar_texto_click" 
									type="text" style=" width : 251px; right: auto; " value="<%= email_usuario_footer %>"></input>
	<br></br>
	<h4 align="left">Login : </h4><input maxlength="30"   name="input_text_login_usuario" value="Login..."  id="id_trocar_usuario_nome" type="text" style=" width : 251px; right: auto;"></input>
	<br></br>
	<h4 align="left">Senha : </h4> <input maxlength="30" name="input_text_senha_usuario" id="id_trocar_usuario_senha" type="password" style=" width : 251px;right: auto;"></input>
	
  </form>
</div> 


	
<!-- ------------------------MSG's-FIM--------------------------------- -->	
