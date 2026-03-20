package br.com.fileh.financial.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
@Table(name = "CONTA_PAGAR")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ContaPagar {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "COP_CD_CONTA_PAGAR")
    private Long id;

    @Column(name = "COP_CD_ENTIDADE", nullable = false)
    private Long entidadeId;

    @Column(name = "COP_DS_CONTA_PAGAR", nullable = false)
    private String descricao;

    @Column(name = "COP_DT_VENCIMENTO", nullable = false)
    private LocalDate dataVencimento;

    @Column(name = "COP_VL_CONTA_PAGAR", nullable = false)
    private BigDecimal valor;

    @Column(name = "COP_DT_CADASTRO")
    private LocalDate dataCadastro;

    @Column(name = "COP_NR_DOCUMENTO")
    private String numeroDocumento;

    @Column(name = "COP_FL_PARCELADO", insertable = false, updatable = false)
    private Boolean parcelado;

    @Column(name = "COP_NR_PARCELA", nullable = false)
    private Integer numeroParcela;

    @Column(name = "COP_DS_FORNECEDOR", nullable = false)
    private String fornecedor;

    @Column(name = "COP_FL_PAGO")
    private Boolean pago;

    @Column(name = "COP_CD_TIPO_CONTA")
    private Long tipoContaId;
}
