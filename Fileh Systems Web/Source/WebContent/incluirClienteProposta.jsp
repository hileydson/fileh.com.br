

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
    .validateTipss { border: 1px solid transparent; padding: 0.3em; }
	    
 </style>


<script>
$(document).ready(function() {
	document.forms["cliente_proposta_hidden"].action = "";
	$('.div_cliente').toggle(  function() {
	    // Animation complete.
	    $('.frame_cliente').css('width','900px');
	    $('.frame_cliente').css('height','600px');
	    
	  });
	$('.cliente').on('click', function(){
		$('.div_cliente').fadeToggle();
		if ($('.cliente').val() == ' Escolher... '){
			$('.cliente').val(' Fechar... ');
			$('.frame_cliente').attr('src',$('.frame_cliente').attr('src'));
		}else{
			$('.cliente').val(' Escolher... ');
			
		}

	});
	
   
  });
  
</script>

<!-- style="border: double;border-bottom-width: 8px ;border-color: black; " -->
<input id="escolher-cliente" class="cliente" type="button" value=" Escolher... " ></input>
<div  class="div_cliente" id="id_div_cliente" align="center" style="border: double;border-bottom-width: 8px ;" >
	<iframe class="frame_cliente" width="0px" height="0px" src="incluirClientePropostaFrame.jsp"></iframe>

</div>










