		String id_proposta = request.getParameter("proposta_edt");
		
		String PRC_DT_PREVISTA_edt = request.getParameter("PRC_DT_PREVISTA_edt2");
		String PRC_VL_FRETE_edt = request.getParameter("PRC_VL_FRETE_edt");
		String PRC_VL_DESCONTO_edt = request.getParameter("PRC_VL_DESCONTO_edt");
		String PRC_DS_OBS_edt = request.getParameter("PRC_DS_OBS_edt");
		
		PRC_VL_FRETE_edt = PRC_VL_FRETE_edt.replace(".", "").replace(".", "").replace(",", ".");
		PRC_VL_DESCONTO_edt = PRC_VL_DESCONTO_edt.replace(".", "").replace(".", "").replace(",", ".");
		
		if(PRC_VL_FRETE_edt.equalsIgnoreCase(""))PRC_VL_FRETE_edt = "0";
		if(PRC_VL_DESCONTO_edt.equalsIgnoreCase(""))PRC_VL_DESCONTO_edt = "0";
		
		
		String sql_aux =  "  ";

		sql_aux += " , PRC_DS_OBS 			= '"+PRC_DS_OBS_edt+"'";
		
		if(PRC_DT_PREVISTA_edt.equalsIgnoreCase("")){ 
			sql_aux += ", PRC_DT_PREVISTA 			= NULL ";
		}else{
			sql_aux += " , PRC_DT_PREVISTA 			= STR_TO_DATE('"+PRC_DT_PREVISTA_edt+"', '%d/%m/%Y') ";
		};
		
		String sql = 
		
		
		"	UPDATE PROPOSTA_COMERCIAL SET 	PRC_VL_FRETE 				= "+PRC_VL_FRETE_edt+", "+
		
										"	PRC_VL_DESCONTO       		= "+PRC_VL_DESCONTO_edt+
										
										sql_aux+										
										
			"	WHERE 	PRC_CD_PROPOSTA_COMERCIAL 	= "+id_proposta ;
		
		
		request.getSession().setAttribute("sql_edt_proposta", sql);
		
		
		response.sendRedirect("editarPropostaComercial.jsp");