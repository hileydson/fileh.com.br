  

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
$(document).ready(function() {
	//deixar as msgs no topo da pagina
    $( '.dialog-position-top').dialog({
		dialogClass: 'dlgfixed',
			position: "top"
		});		

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
