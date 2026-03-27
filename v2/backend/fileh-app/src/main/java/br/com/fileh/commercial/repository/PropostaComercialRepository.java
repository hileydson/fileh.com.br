package br.com.fileh.commercial.repository;

import br.com.fileh.commercial.model.PropostaComercial;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Map;

public interface PropostaComercialRepository extends JpaRepository<PropostaComercial, Long> {
    List<PropostaComercial> findByEntidadeId(Long entidadeId);
    List<PropostaComercial> findByEntidadeIdAndAtivo(Long entidadeId, Boolean ativo);

    /**
     * Query otimizada para listagem: usa LEFT JOIN + GROUP BY para calcular o total
     * em uma única query, eliminando o problema N+1 causado pelo @Formula.
     */
    @Query(value = """
        SELECT
            p.prc_cd_proposta_comercial      AS id,
            p.prc_cd_entidade                AS entidadeId,
            p.prc_cd_usuario                 AS usuarioId,
            p.prc_cd_cliente                 AS clienteId,
            p.prc_vl_desconto                AS valorDesconto,
            p.prc_dt_cadastro                AS dataCadastro,
            p.prc_dt_prevista                AS dataPrevista,
            p.prc_ds_obs                     AS observacao,
            p.prc_nm_atendente               AS atendente,
            p.prc_ds_situacao                AS situacao,
            p.prc_ds_forma_pagamento         AS formaPagamento,
            p.prc_bt_ativo                   AS ativo,
            COALESCE(SUM(it.ipc_vl_item_proposta * it.ipc_nr_quantidade), 0)
                - COALESCE(p.prc_vl_desconto, 0) AS valorTotal
        FROM proposta_comercial p
        LEFT JOIN item_proposta it ON it.ipc_cd_proposta_comercial = p.prc_cd_proposta_comercial
        WHERE p.prc_cd_entidade = :entidadeId
          AND p.prc_bt_ativo = :ativo
        GROUP BY p.prc_cd_proposta_comercial
        ORDER BY p.prc_dt_cadastro DESC
        """, nativeQuery = true)
    List<Map<String, Object>> findListByEntidadeIdAndAtivo(@Param("entidadeId") Long entidadeId,
                                                           @Param("ativo") Boolean ativo);

    @Query(value = """
        SELECT
            p.prc_cd_proposta_comercial      AS id,
            p.prc_cd_entidade                AS entidadeId,
            p.prc_cd_usuario                 AS usuarioId,
            p.prc_cd_cliente                 AS clienteId,
            p.prc_vl_desconto                AS valorDesconto,
            p.prc_dt_cadastro                AS dataCadastro,
            p.prc_dt_prevista                AS dataPrevista,
            p.prc_ds_obs                     AS observacao,
            p.prc_nm_atendente               AS atendente,
            p.prc_ds_situacao                AS situacao,
            p.prc_ds_forma_pagamento         AS formaPagamento,
            p.prc_bt_ativo                   AS ativo,
            COALESCE(SUM(it.ipc_vl_item_proposta * it.ipc_nr_quantidade), 0)
                - COALESCE(p.prc_vl_desconto, 0) AS valorTotal
        FROM proposta_comercial p
        LEFT JOIN item_proposta it ON it.ipc_cd_proposta_comercial = p.prc_cd_proposta_comercial
        GROUP BY p.prc_cd_proposta_comercial
        ORDER BY p.prc_dt_cadastro DESC
        """, nativeQuery = true)
    List<Map<String, Object>> findAllList();
}
