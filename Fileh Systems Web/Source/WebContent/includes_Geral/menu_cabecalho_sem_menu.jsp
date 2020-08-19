<!-- --------------------------- CABEÇALHO COMUM A TODOS-INICIO------------------------------------ -->
<%@ page errorPage="processoErroAcesso.jsp" %>
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

	
	

<head>

<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1" />
<meta name="keywords"	content="slate, theme, darkcyan, teal color - Fileh Systems - Hileydson Luiz Cogo" />
<meta name="description"	content="Fileh Systems - Hileydson Luiz Cogo" />

<link href="css/templatemo_style.css" rel="stylesheet" type="text/css" />
<link rel="stylesheet" href="css/nivo-slider.css" type="text/css" media="screen" />
<link rel="shortcut icon" href="images/pages_icon.png"/> 
<link rel="stylesheet" type="text/css" href="css/ddsmoothmenu.css" />
<link rel="stylesheet" href="css/jquery-ui.css"/>
<link rel="stylesheet" href="/resources/demos/style.css" /> 


  <style>

  fieldset div {
    margin-bottom: 2em;
  }
  fieldset .help {
    display: inline-block;
  }
  .ui-tooltip {
    width: 210px;
  }
  </style>


  <style>
  	
  	input.uppercase{  
	text-transform: uppercase;  
	}  
	
	input.textarea_limit{  
	text-transform: uppercase;  
	} 
  
  	input, select, textarea {border: 1px solid teal; padding: 0.3em;}
  
    body { font-size: 70.5%; }
    label { display:block; }
    input.text { margin-bottom:12px; width:95%; padding: .4em; }
    fieldset { padding:0; border:0; margin-top:25px; }
    h1 { font-size: 1.2em; margin: .6em 0; }
    div#users-contains { width: 350px; margin: 20px 0; }
    div#users-contains table { margin: 1em 0; border-collapse: collapse; width: 100%;  }
    div#users-contain table td, div#users-contains table th { border: 1px solid #eee; padding: .6em 10px; text-align: left; }
    .ui-dialog .ui-state-error { padding: .3em; }
    .validateTips { border: 1px solid teal; padding: 0.3em; }
	  
  </style>


<!-- -------------------------------------SCRIPTS-INICIO-------------------------------------------- -->

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

	
	function clearText(field) {

		if (field.defaultValue == field.value)
			field.value = '';
		else if (field.value == '')
			field.value = field.defaultValue;

	};

	function chamaServletFrame(form,servlet) {
		document.forms[form].action = servlet;
		document.forms[form].submit();	
	};
	
	
</script>


<!-- -------------------------------------SCRIPTS-FIM-------------------------------------------- -->



<!-- --------------------------- VALIDA USUARIO-INICIO------------------------------------ -->
	<% 
	
	//verifica se é usuario válido

	if( (session.getAttribute("usuario") != null) && (session.getAttribute("usuario").toString().equalsIgnoreCase("erro"))  ){
		session.setAttribute("usuario", "erro");
		session.setAttribute("subusuario", "erro");
		response.sendRedirect("usuarioLogin.jsp");
	};
	
	if(	(session.getAttribute("usuario") == null) || (session.getAttribute("subusuario") == null) ){
		session.setAttribute("usuario", "erro");
		session.setAttribute("subusuario", "erro");
		response.sendRedirect("usuarioLogin.jsp");
	};
	
	%>	
	
	<%!String fl_adm, fl_cliente , fl_venda , fl_financeiro, id_usuario_logado, nm_usuario_logado ;%>	
	<%!SubUsuario subusuario ; Usuario usuario;%>
	
	<%	
		fl_adm = "";
		fl_cliente = "";
		fl_venda = "";
		fl_financeiro = "";
		
		id_usuario_logado = "";
		nm_usuario_logado = "";
		
		if ((session.getAttribute("subusuario") != null) && (!session.getAttribute("subusuario").toString().equalsIgnoreCase("erro"))  ){
			
			subusuario 	= (SubUsuario) 	session.getAttribute("subusuario"); 
			usuario 		= (Usuario) 	session.getAttribute("usuario"); 
			
			if (subusuario.getSbu_fl_modulo_cliente() != null) fl_cliente = subusuario.getSbu_fl_modulo_cliente();
			if (subusuario.getSbu_fl_adm() != null) fl_adm = subusuario.getSbu_fl_adm();
			if (subusuario.getSbu_fl_modulo_venda() != null) fl_venda = subusuario.getSbu_fl_modulo_venda();
			if (subusuario.getSbu_fl_modulo_financeiro() != null) fl_financeiro = subusuario.getSbu_fl_modulo_financeiro();
			
			if (subusuario.getSbu_nm_subusuario() != null) nm_usuario_logado = subusuario.getSbu_nm_subusuario();
		}
	
	%>
	
</head>
<!-- --------------------------- VALIDA USUARIO-FIM------------------------------------ -->






<!-- ------------------------MENU-INICIO--------------------------------- -->	
<title>Fileh Systems</title>
<br>

	
<!-- ------------------------Jquerys-INICIO--------------------------------- -->
<script src="js/jquery-1.10.2.min.js" type="text/javascript"></script>
<script src="js/jquery.maskMoney.min.js" type="text/javascript"></script>
 <script src="js/jquery-ui.js"></script>
<!-- ------------------------Jquerys-FIM--------------------------------- -->
	