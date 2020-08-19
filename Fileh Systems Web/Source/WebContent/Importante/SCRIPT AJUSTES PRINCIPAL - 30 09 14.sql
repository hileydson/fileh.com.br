-- AJUSTES DO BANCO PRINCIPAL 

-- 04 09 2014


-- ----------------------------------------------------------------------------------------
-- AJUSTES
-- ----------------------------------------------------------------------------------------


-- CORRIGIR ANTES DE APLICAR VALORES SEMPRE DE ACORDO COM A TABELA PRINCIPAL ATUAL
INSERT INTO `PLANO` (`PLA_CD_PLANO`, `PLA_DS_PLANO`, `PLA_NM_PLANO`, `PLA_DS_ESPACO`) VALUES ('1', 'Com 5.000 registro para armazenamento - 2 Usuários - R$60,00/Mensal', 'PLANO E', '5000');
INSERT INTO `PLANO` (`PLA_CD_PLANO`, `PLA_DS_PLANO`, `PLA_NM_PLANO`, `PLA_DS_ESPACO`) VALUES ('2', 'Com 8.000 registro para armazenamento - 4 Usuários - R$90,00/Mensal', 'PLANO D', '8000');
INSERT INTO `PLANO` (`PLA_CD_PLANO`, `PLA_DS_PLANO`, `PLA_NM_PLANO`, `PLA_DS_ESPACO`) VALUES ('3', 'Com 12.000 registro para armazenamento - 6 Usuários - R$120,00/Mensal', 'PLANO C', '12000');
INSERT INTO `PLANO` (`PLA_CD_PLANO`, `PLA_DS_PLANO`, `PLA_NM_PLANO`, `PLA_DS_ESPACO`) VALUES ('4', 'Com 16.000 registro para armazenamento - 10 Usuários - R$150,00/Mensal', 'PLANO B', '16000');
INSERT INTO `PLANO` (`PLA_CD_PLANO`, `PLA_DS_PLANO`, `PLA_NM_PLANO`, `PLA_DS_ESPACO`) VALUES ('5', 'Com 30.000 registro para armazenamento - 16 Usuários - R$270,00/Mensal', 'PLANO A', '30000');
INSERT INTO `PLANO` (`PLA_CD_PLANO`, `PLA_DS_PLANO`, `PLA_NM_PLANO`, `PLA_DS_ESPACO`) VALUES ('6', 'Com 40.000 registro para armazenamento - 24 Usuários - R$380,00/Mensal', 'PLANO A+', '40000');



-- CORRIGIR ANTES DE APLICAR VALORES SEMPRE DE ACORDO COM A TABELA PRINCIPAL ATUAL
INSERT INTO `USUARIO` VALUES 
(1,'Serdo Material de Construção','123.321','serdo@oi.com.br','serdo','Serdo Material de Construção','Rua Castelo Branco','54','Castelo Branco','Cariacica','ES','3316-4563',5),
(2,'Bela Auto Center','998877665544332211','mauro@teste','NAO_CADASTRADO','Bela Auto Center','Rua Fernando Antonio','49','Bela Aurora','Cariacica','ES','3090-4806/99832-3888',1),
(3,'Hileydson Luiz Côgo','1111895656','hileydson@hotmail.com','hileydson','Adminstrador do Sistema','Av. Fernando Antonio','49','Bela Aurora','Cariacica','ES','3316-4563',1),
(4,'Marinho','12332112332','marinho@marinho.com.br','marinho','Marinho Açogue','Rua Castelo Branco','001','Castelo Branco','Cariacica','ES','3316-4563',1);




