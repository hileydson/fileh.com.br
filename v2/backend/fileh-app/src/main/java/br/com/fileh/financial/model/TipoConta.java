package br.com.fileh.financial.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

@Entity
@Table(name = "TIPO_CONTA")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class TipoConta {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "TCO_CD_TIPO_CONTA")
    private Long id;

    @Column(name = "TCO_NM_TIPO_CONTA", nullable = false)
    private String nome;

    @Column(name = "TCO_CD_ENTIDADE", nullable = false)
    private Long entidadeId;
}
