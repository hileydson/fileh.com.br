
  <link rel="stylesheet" href="css/jquery-ui.css">

  <link rel="stylesheet" href="/resources/demos/style.css">
  <style>
    body { font-size: 62.5%; }
    label, input { display:block; }
    input.text { margin-bottom:12px; width:95%; padding: .4em; }
    fieldset { padding:0; border:0; margin-top:25px; }
    h1 { font-size: 1.2em; margin: .6em 0; }
    div#users-contains { width: 350px; margin: 20px 0; }
    div#users-contains table { margin: 1em 0; border-collapse: collapse; width: 100%;  }
    div#users-contain table td, div#users-contains table th { border: 1px solid #eee; padding: .6em 10px; text-align: left; }
    .ui-dialog .ui-state-error { padding: .3em; }
    .validateTips { border: 1px solid transparent; padding: 0.3em; }
	  
  </style>

  <script>

  $(document).ready(function() {

    $( "#dialog-form-incluir-item" ).dialog({
        autoOpen: false,
        height: 600,
        width: 950,
        modal: true,
        buttons: {

          Fechar: function() {
          	$( this ).dialog( "close" ); 
          }
        },
        close: function() {
          //allFields.val( "" ).removeClass( "ui-state-error" );
        }
      });
    
    
    
    $( "#adicionar_item" ).click(function() {
    	$('#id_frame_produto').attr('src',$('#id_frame_produto').attr('src'));
        $( "#dialog-form-incluir-item" ).dialog( "open" );        
      });
    
    	  
  });

  </script>

 
<div id="dialog-form-incluir-item" title="Adicionar novo item" class="dialog-position-top">
  <p class="validateTips">Escolha o item a ser adicionado...</p>
 
  <form>
  <fieldset>
    <iframe id="id_frame_produto" width="900px" height="438px" src="incluirProdutoPropostaFrame.jsp" ></iframe>
  </fieldset>
  </form>
</div>
      