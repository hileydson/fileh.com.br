
  <!--   <link rel="stylesheet" href="http://code.jquery.com/ui/1.10.3/themes/smoothness/jquery-ui.css" />
  <script src="http://code.jquery.com/jquery-1.9.1.js"></script>
  <script src="http://code.jquery.com/ui/1.10.3/jquery-ui.js"></script> -->

  <link rel="stylesheet" href="css/jquery-ui.css" />
  <script src="js/jquery-1.9.1.js"></script>
  <script src="js/jquery-ui.js"></script>
  
  <link rel="stylesheet" href="/resources/demos/style.css" />
  <script>
  $(function() {
    $( "#dialog" ).dialog({
    	close: function(event, ui) { location.reload(); }
        });
  });
  </script>
  <div id="dialog" title="Alerta!">
  <p>Não foi possível concluir o processo!</p><p>Favor Preencher os dados necessários para inserção!</p>
</div>