package br.com.fileh.financial.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
@Table(name = "FLUXO_CAIXA")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class FluxoCaixa {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "FLU_CD_FLUXO_CAIXA")
    private Long id;

    @Column(name = "FLU_DS_FLUXO_CAIXA", nullable = false)
    private String descricao;

    @Column(name = "FLU_CD_ENTIDADE", nullable = false)
    private Long entidadeId;

    @Column(name = "FLU_VL_FLUXO_CAIXA", nullable = false)
    private BigDecimal valor;

    @Column(name = "FLU_DT_CADASTRO", nullable = false)
    private LocalDate dataCadastro;

    @Column(name = "FLU_FL_TIPO", nullable = false)
    private Character tipo; // Usually 'E' for Entrada or 'S' for Saida

    @Column(name = "FLU_DS_FORMA_PAGAMENTO")
    private String formaPagamento;
}
