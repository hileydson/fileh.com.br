package br.com.fileh.commercial.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

@Entity
@Table(name = "SITUACAO_PROPOSTA")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class SituacaoProposta {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "SIP_CD_SITUACAO_PROPOSTA")
    private Long id;

    @Column(name = "SIP_CD_ENTIDADE", nullable = false)
    private Long entidadeId;

    @Column(name = "SIP_DS_SITUACAO_PROPOSTA", nullable = false)
    private String descricao;
}
