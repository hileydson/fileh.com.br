package br.com.fileh.commercial.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import java.math.BigDecimal;

@Entity
@Table(name = "ITEM_PROPOSTA")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ItemProposta {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "IPC_CD_ITEM_PROPOSTA")
    private Long id;

    @Column(name = "IPC_CD_PROPOSTA_COMERCIAL", nullable = false)
    private Long propostaId;

    @Column(name = "IPC_DS_ITEM_PROPOSTA", nullable = false)
    private String descricao;

    @Column(name = "IPC_VL_ITEM_PROPOSTA", nullable = false)
    private BigDecimal valor;

    @Column(name = "IPC_NR_QUANTIDADE", nullable = false)
    private BigDecimal quantidade;

    @Column(name = "IPC_VL_DESCONTO", nullable = false)
    private BigDecimal valorDesconto;

    @Column(name = "IPC_DS_UNIDADE", nullable = false)
    private String unidade;
}
