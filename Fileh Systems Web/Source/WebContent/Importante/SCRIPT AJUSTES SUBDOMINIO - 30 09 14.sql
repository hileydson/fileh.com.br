-- AJUSTES DE TODOS OS BANCOS SUBDOMINIO 

-- 04 09 2014


-- ----------------------------------------------------------------------------------------
-- AJUSTES
-- ----------------------------------------------------------------------------------------





-- FAZER O RESTOURE DA BASE DE USUARIOS PRINCIPAL E INFORMAR O NUMERO DO USUARIO CORRESPONDENTE
INSERT INTO SUBUSUARIO (SBU_NM_SUBUSUARIO, SBU_CD_USUARIO, 
						SBU_DS_LOGIN, SBU_SH_SUBUSUARIO, SBU_FL_MODULO_FINANCEIRO, 
						SBU_FL_MODULO_VENDA, SBU_FL_ADM, SBU_FL_MODULO_CLIENTE,
						SBU_FL_MODULO_CAIXA) 
				values
						("Administrador", "3",
						"adm", "123", "S", 
						"S", "S", "S",
						"S")
 ;
