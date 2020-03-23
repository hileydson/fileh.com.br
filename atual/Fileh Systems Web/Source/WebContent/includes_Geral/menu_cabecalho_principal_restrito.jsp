<%@ page language="java" contentType="text/html;" pageEncoding="ISO-8859-1"%>	
<?xml version="1.0" encoding="ISO-8859-1" ?>
<html xmlns="http://www.w3.org/1999/xhtml"/>	

<!-- TAGLIBS -->	
<%@taglib uri='http://java.sun.com/jsp/jstl/core' prefix='c'%>	
<%@taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>


<!-- IMPORTES -->
<%@page import="org.hibernate.Session"%>
<%@page import="entidades.*"%>
<%@page import="java.util.ArrayList"%>

	<% 

	//verifica se é usuario inválido
	if(	(session.getAttribute("restrito") == null) ){
		response.sendRedirect("restrito.jsp");
	};
	
	
	%>
	
	

<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1" />
<meta name="keywords"	content="slate, theme, darkcyan, teal color, free templates, web design, CSS, HTML" />
<meta name="description"	content="Slate Theme is using darkcyan or teal color free template by templatemo.com" />

<link href="css/templatemo_style.css" rel="stylesheet" type="text/css" />
<link rel="stylesheet" href="css/nivo-slider.css" type="text/css" media="screen" />
<link rel="shortcut icon" href="images/pages_icon.png"/> 
<link rel="stylesheet" type="text/css" href="css/ddsmoothmenu.css" />
<link rel="stylesheet" href="css/jquery-ui.css"/>
<link rel="stylesheet" href="/resources/demos/style.css" /> 


<script src="js/jquery.ui.position.js"></script>
<script type="text/javascript" src="js/jquery.min.js"></script>
<script type="text/javascript" src="js/ddsmoothmenu.js"></script>



<script type="text/javascript">
	ddsmoothmenu.init({
		mainmenuid : "templatemo_menu", //menu DIV id
		orientation : 'h', //Horizontal or vertical menu: Set to "h" or "v"
		classname : 'ddsmoothmenu', //class added to menu's outer DIV
		//customtheme: ["#1c5a80", "#18374a"],
		contentsource : "markup" //"markup" or ["container_id", "path_to_menu_file"]
	});
	
	function chamaServlet(form,servlet) {
		document.forms[form].action = servlet;
		document.forms[form].submit();	
	};
</script>

  <style>
  	
  	input.uppercase{  
	text-transform: uppercase;  
	}  
	
	input.textarea_limit{  
	text-transform: uppercase;  
	} 
  
  	input, select, textarea {border: 1px solid teal; padding: 0.3em;}
  
    body { font-size: 70.5%; }
    input.text { margin-bottom:12px; width:95%; padding: .4em; }
    fieldset { padding:0; border:0; margin-top:25px; }
    h1 { font-size: 1.2em; margin: .6em 0; }
    div#users-contains { width: 350px; margin: 20px 0; }
    div#users-contains table { margin: 1em 0; border-collapse: collapse; width: 100%;  }
    div#users-contain table td, div#users-contains table th { border: 1px solid #eee; padding: .6em 10px; text-align: left; }
    .ui-dialog .ui-state-error { padding: .3em; }
    .validateTips { border: 1px solid teal; padding: 0.3em; }
	  
  </style>
</head>	

<title>Fileh Systems</title>
<div id="templatemo_wrapper">
	<div>
		<div id="site_title">
			<h1>
				<a><label style="color: buttonface;">Sistema de Gestão Online</label>
						</a>			
			</h1>
		</div>
		<div class="cleaner"></div>
	</div>
	<!-- end of header -->
<form name="formMenu2" action="CarregarCadastroUsuario.action">
	<!-- IGUAL EM TODOS -->
	<div id="templatemo_menu" class="ddsmoothmenu">
		<ul>
			<li><a href="restritoLogado.jsp">Area Restrita</a></li>	
		</ul>		
	</div>
</form>	
</div>


	
<!-- ------------------------Jquerys-INICIO--------------------------------- -->
<script src="js/jquery-1.10.2.min.js" type="text/javascript"></script>
<script src="js/jquery.maskMoney.min.js" type="text/javascript"></script>
<script src="js/jquery-ui.js"></script>
<!-- ------------------------Jquerys-FIM--------------------------------- -->