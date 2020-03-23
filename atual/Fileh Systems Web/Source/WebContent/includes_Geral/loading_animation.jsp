
<style type="text/css">


* {margin: 0; padding: 0; outline: none;}

img {border: none;}

a { 
	text-decoration:none; 
	color:#00c6ff;
}

h1 {
	font: 4em normal Arial, Helvetica, sans-serif;
	padding: 20px;	margin: 0;
	text-align:center;
	color:#bbb;
}

h1 small{
	font: 0.2em normal  Arial, Helvetica, sans-serif;
	text-transform:uppercase; letter-spacing: 0.2em; line-height: 5em;
	display: block;
}

.container {width: 350px; margin: 0 auto; overflow: hidden;}
.content {width:100px; margin:0 auto; padding-top:50px;}
.contentBar {width:5px; margin:0 auto; padding-top:10px; padding-bottom:10px;}

/* STOP ANIMATION */

.stop {
	-webkit-animation-play-state:paused;
	-moz-animation-play-state:paused;
}

/* Loading Circle */
.ball {
	background-color: rgba(0,0,0,0);
	border:5px solid rgba(0,183,229,0.9);
	opacity:.9;
	border-top:5px solid rgba(0,0,0,0);
	border-left:5px solid rgba(0,0,0,0);
	border-radius:50px;
	box-shadow: 0 0 35px #2187e7;
	width:50px;
	height:50px;
	margin:0 auto;
	-moz-animation:spin .5s infinite linear;
	-webkit-animation:spin .5s infinite linear;
}

.ball1 {
	background-color: rgba(0,0,0,0);
	border:5px solid rgba(0,183,229,0.9);
	opacity:.9;
	border-top:5px solid rgba(0,0,0,0);
	border-left:5px solid rgba(0,0,0,0);
	border-radius:50px;
	box-shadow: 0 0 15px #2187e7; 
	width:30px;
	height:30px;
	margin:0 auto;
	position:relative;
	top:-50px;
	-moz-animation:spinoff .5s infinite linear;
	-webkit-animation:spinoff .5s infinite linear;
}

@-moz-keyframes spin {
	0% { -moz-transform:rotate(0deg); }
	100% { -moz-transform:rotate(360deg); }
}
@-moz-keyframes spinoff {
	0% { -moz-transform:rotate(0deg); }
	100% { -moz-transform:rotate(-360deg); }
}
@-webkit-keyframes spin {
	0% { -webkit-transform:rotate(0deg); }
	100% { -webkit-transform:rotate(360deg); }
}
@-webkit-keyframes spinoff {
	0% { -webkit-transform:rotate(0deg); }
	100% { -webkit-transform:rotate(-360deg); }
}

/* Second Loadin Circle */

.circle {
	background-color: rgba(0,0,0,0);
	border:5px solid rgba(0,183,229,0.9);
	opacity:.9;
	border-right:5px solid rgba(0,0,0,0);
	border-left:5px solid rgba(0,0,0,0);
	border-radius:50px;
	box-shadow: 0 0 35px #2187e7;
	width:50px;
	height:50px;
	margin:0 auto;
	-moz-animation:spinPulse 1s infinite ease-in-out;
	-webkit-animation:spinPulse 1s infinite linear;
}
.circle1 {
	background-color: rgba(0,0,0,0);
	border:5px solid rgba(0,183,229,0.9);
	opacity:.9;
	border-left:5px solid rgba(0,0,0,0);
	border-right:5px solid rgba(0,0,0,0);
	border-radius:50px;
	box-shadow: 0 0 15px #2187e7; 
	width:30px;
	height:30px;
	margin:0 auto;
	position:relative;
	top:-50px;
	-moz-animation:spinoffPulse 1s infinite linear;
	-webkit-animation:spinoffPulse 1s infinite linear;
}

@-moz-keyframes spinPulse {
	0% { -moz-transform:rotate(160deg); opacity:0; box-shadow:0 0 1px #2187e7;}
	50% { -moz-transform:rotate(145deg); opacity:1; }
	100% { -moz-transform:rotate(-320deg); opacity:0; }
}
@-moz-keyframes spinoffPulse {
	0% { -moz-transform:rotate(0deg); }
	100% { -moz-transform:rotate(360deg);  }
}
@-webkit-keyframes spinPulse {
	0% { -webkit-transform:rotate(160deg); opacity:0; box-shadow:0 0 1px #2187e7; }
	50% { -webkit-transform:rotate(145deg); opacity:1;}
	100% { -webkit-transform:rotate(-320deg); opacity:0; }
}
@-webkit-keyframes spinoffPulse {
	0% { -webkit-transform:rotate(0deg); }
	100% { -webkit-transform:rotate(360deg); }
}

/* LITTLE BAR */

.barlittle {
	background-color:#2187e7;  
	background-image: -moz-linear-gradient(45deg, #2187e7 25%, #a0eaff); 
	background-image: -webkit-linear-gradient(45deg, #2187e7 25%, #a0eaff);
	border-left:1px solid #111; border-top:1px solid #111; border-right:1px solid #333; border-bottom:1px solid #333; 
	width:10px;
	height:10px;
	float:left;
	margin-left:5px;
    opacity:0.1;
	-moz-transform:scale(0.7);
	-webkit-transform:scale(0.7);
	-moz-animation:move 1s infinite linear;
	-webkit-animation:move 1s infinite linear;
}
#block_1{
 	-moz-animation-delay: .4s;
	-webkit-animation-delay: .4s;
 }
#block_2{
 	-moz-animation-delay: .3s;
	-webkit-animation-delay: .3s;
}
#block_3{
 	-moz-animation-delay: .2s;
	-webkit-animation-delay: .2s;
}
#block_4{
 	-moz-animation-delay: .3s;
	-webkit-animation-delay: .3s;
}
#block_5{
 	-moz-animation-delay: .4s;
	-webkit-animation-delay: .4s;
}
@-moz-keyframes move{
	0%{-moz-transform: scale(1.2);opacity:1;}
	100%{-moz-transform: scale(0.7);opacity:0.1;}
}
@-webkit-keyframes move{
	0%{-webkit-transform: scale(1.2);opacity:1;}
	100%{-webkit-transform: scale(0.7);opacity:0.1;}
}

/* Trigger button for javascript */

.trigger {
	background: #000000;
	background: -moz-linear-gradient(top, #161616 0%, #000000 100%);
	background: -webkit-linear-gradient(top, #161616 0%,#000000 100%);
	border-left:1px solid #111; border-top:1px solid #111; border-right:1px solid #333; border-bottom:1px solid #333; 
	font-family: Verdana, Geneva, sans-serif;
	font-size: 0.8em;
	text-decoration: none;
	text-transform: lowercase;
	text-align: center;
	color: #fff;
	padding: 10px;
	border-radius: 3px;
	display: block;
	margin: 0 auto;
	width: 140px;
}
		
.trigger:hover {
	background: -moz-linear-gradient(top, #202020 0%, #161616 100%);
	background: -webkit-linear-gradient(top, #202020 0%, #161616 100%);
}

#id_div_loading_animation {
	background-color:#E8E8E8  ;
}
</style>
<!-- <script src="https://ajax.googleapis.com/ajax/libs/jquery/1.6.0/jquery.min.js" type="text/javascript"></script> -->
<script>		
$(document).ready(function() {
	$('.ball, .ball1').removeClass('stop');	    
		$('.trigger').click(function() {
				$('.ball, .ball1').toggleClass('stop');
		});
		

    $( "#id_div_loading_animation" ).dialog({
        autoOpen: false,
        height: 220,
        width: 420,
        resizable: false,
        modal: true,
        dialogClass: 'noTitleStuff',
        create: function (event, ui) {
            $(".ui-widget-header").hide();
        }
      });		

    //previne do esc fechar o loading...
    document.onkeydown = function(evt) {
        evt = evt || window.event;
        if (evt.keyCode == 27) {
        	if($('#id_commit').is(':disabled'))
        	{  
        		$('#id_div_loading_animation').dialog( "open" );  
        	}
        }
    };
    
    
});



</script>

<div id="id_div_loading_animation" align="center" style="">

	<!-- LOOP LOADER -->
	<div class="container">
	<br>
	<label style="font-family: fantasy;font-style: italic;font-size: 18px">Carregando...</label>
		<div class="content">
	    <div class="ball"></div>
	    <div class="ball1"></div>
	    </div>
	</div>

	<!-- END LOOP LOADER -->

</div>
