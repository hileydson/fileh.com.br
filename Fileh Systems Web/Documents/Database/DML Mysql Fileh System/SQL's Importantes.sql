
-- ---------------------------------------------------------------------------
-- BUSCA DIAS SEM ENTRADA 'FUNDO DE CAIXA'
-- ---------------------------------------------------------------------------

SELECT date_format(FLU_1.FLU_DT_CADASTRO, '%d/%m/%Y')
FROM FLUXO_CAIXA FLU_1

-- RIMACOL 50(2) SERDO (1) RIMACOL 79(3)
WHERE FLU_1.FLU_CD_ENTIDADE = 2

-- SÓ VEM REGISTROS QUANDO NÃO TIVEREM OUTROS DO MESMO DIA DE CADASTRO COM FORMA DE PAGAMENTO 'FUNDO DE CAIXA' E SEREM ENTIDADE IGUAL
AND NOT EXISTS (SELECT * FROM FLUXO_CAIXA FLU_2 
				WHERE FLU_2.FLU_DT_CADASTRO = FLU_1.FLU_DT_CADASTRO 
				AND FLU_DS_FORMA_PAGAMENTO = 'FUNDO DE CAIXA'
				AND FLU_2.FLU_CD_ENTIDADE = FLU_1.FLU_CD_ENTIDADE )

-- RETIRA MES 01/2015, POIS NESSE MES AINDA NÃO ESTAVA TUDO CORRETO, COMERÇAR A CONTAR CORRETAMENTE NO MES DE FEVEREIRO PRA FRENTE...
AND date_format(FLU_1.FLU_DT_CADASTRO, '%m/%Y') != '01/2015'

GROUP BY date_format(FLU_1.FLU_DT_CADASTRO, '%d/%m/%Y')
;

-- ---------------------------------------------------------------------------
-- ---------------------------------------------------------------------------


