<%@include file="includes_Geral/campoPesquisa_Nao_Mostrar.jsp"%> 
<%@include file="includes_Geral/menu_cabecalho.jsp"%>

  <script>

  
  $(document).ready(function() {
	  
	    $( "#dialog-form-msg-troca-senha-inicial" ).dialog({
	        autoOpen: false,
	        height: 150,
	        width: 300,
	        resizable: false,
	        modal: true,
	        dialogClass: 'noTitleStuff',
	        buttons: {
	          Continuar: function() {
	        	  	  	        	  
	        	  window.location="editarSubUsuario.jsp";

	          }
	        }
	      });
	  
	  
	    //reajusta o modulo em que a pagina se refere...
	    $("#id_menu_geral").addClass('selected');
	    $( "#templatemo_menu" ).on('mouseout',(function( ){
	    	$("#id_menu_geral").addClass('selected');
	    })); 
	    
});


</script>

	<%
	//mensagem para troca de senha inicial... senha inicial : 123
	if(subusuario.getSbu_sh_subusuario().equalsIgnoreCase("123")){%>
		
		  <script>
		  $(document).ready(function() { 
			  $('#dialog-form-msg-troca-senha-inicial').css('visibility', 'visible');	
			  $( '#dialog-form-msg-troca-senha-inicial' ).dialog( "open" );   
		  });
		  </script>
		  

	<%
	};
	%>


  <div id="dialog-form-msg-troca-senha-inicial" title="Alerta..." style="visibility: hidden;">
	<label >A senha 123 n�o � uma senha segura! <br> Favor trocar a senha antes de utilizar o sistema...</label>
  </div>
  
<body>


  <h3 align="center" style="font-style: italic; color: menu; ">Fileh Systems - Dicas</h3>








			<div id="img_center_logado" style="height: 680px">
			
			<br><br>
								<a></a>
				Geral<hr>				
				<a><img src="images/pages_icon.png" hspace="10px" width="20px" alt="" align="left" /></a>* Fa�a o backup dos m�dulos utilizados periodicamente para maior seguran�a de seus dados...
								<br></br>	
				<a><img src="images/pages_icon.png" hspace="10px" width="20px" alt="" align="left" /></a>* Ao restaurar o backup de algum m�dulo todos os dados atuais deste m�dulo ser�o substituidos pelos novos dados...
								<br></br>	
				<a><img src="images/pages_icon.png" hspace="10px"  width="20px" alt="" align="left" /></a>* Toda opera��o efetuada � notificada com uma caixa de dialogo informando que a opera��o foi efetuada com sucesso...
								<br></br>	
								
								<br></br>																					
				Caixa<hr>
				<a><img src="images/pages_icon.png" hspace="10px"  width="20px" alt="" align="left" /></a>* O tipo de pagamento/sa�da � um tipo de entrada ou sa�da para o fluxo de caixa...
								<br><br>				
				<a><img src="images/pages_icon.png" hspace="10px"  width="20px" alt="" align="left" /></a>* Exemplos de tipo de pagamento/sa�da: Cheque, Cart�o 1x, Cart�o 2x, Dinheiro, Sa�da - Conta Energia, Sa�da - Retirada...
								<br><br>	
				<a><img src="images/pages_icon.png" hspace="10px" width="20px" alt="" align="left" /></a>* Para cadastrar um fluxo de caixa � necess�rio primeiro cadastrar a entidade que esse fluxo de caixa pertence, por exemplo a matriz ou filial...
								<br></br>
								
								<br></br>
				Clientes<hr>
				<a><img src="images/pages_icon.png" hspace="10px" width="20px" alt="" align="left" /></a>* Para cadastrar um cliente � necess�rio primeiro cadastrar a entidade que esse cliente pertence, por exemplo a matriz ou filial...
								<br></br>	
				<a><img src="images/pages_icon.png" hspace="10px"  width="20px" alt="" align="left" /></a>* Cada Proposta Comercial pode ter ou n�o uma situa��o, como por exemplo: Or�amento, Finalizado, Pendente...
								<br><br>
								
								<br></br>

				Vendas<hr>	
				<a><img src="images/pages_icon.png" hspace="10px"  width="20px" alt="" align="left" /></a>* A forma de pagamento � um tipo de pagamento para a proposta comercial...
								<br><br>							
				<a><img src="images/pages_icon.png" hspace="10px"  width="20px" alt="" align="left" /></a>* Exemplos de formas de pagamento : Cheque, Cart�o 1x, Cart�o 2x, Dinheiro...
								<br><br>

								<br></br>
				Financeiro<hr>
				<a><img src="images/pages_icon.png"  hspace="10px" width="20px" alt="" align="left" /></a>* Para cadastrar uma conta a pagar ou receber � necess�rio cadastrar primeiro o fornecedor e o tipo de conta...
								<br></br>								
						
			<br>
			</div>



		<%@include file="includes_Geral/footer.jsp"%>

</body>
</html>