package br.com.fileh.financial.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
@Table(name = "CONTA_RECEBER")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ContaReceber {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "COR_CD_CONTA_RECEBER")
    private Long id;

    @Column(name = "COR_CD_ENTIDADE", nullable = false)
    private Long entidadeId;

    @Column(name = "COR_DS_CONTA_RECEBER", nullable = false)
    private String descricao;

    @Column(name = "COR_DT_VENCIMENTO", nullable = false)
    private LocalDate dataVencimento;

    @Column(name = "COR_VL_CONTA_RECEBER", nullable = false)
    private BigDecimal valor;

    @Column(name = "COR_DT_CADASTRO")
    private LocalDate dataCadastro;

    @Column(name = "COR_NR_DOCUMENTO")
    private String numeroDocumento;

    @Column(name = "COR_FL_PARCELADO", insertable = false, updatable = false)
    private Boolean parcelado;

    @Column(name = "COR_NR_PARCELA", nullable = false)
    private Integer numeroParcela;

    @Column(name = "COR_DS_FORNECEDOR", nullable = false)
    private String fornecedor; // In legacy it's FORNECEDOR but probably means the origin/payer

    @Column(name = "COR_FL_RECEBIDO")
    private Boolean recebido;

    @Column(name = "COR_CD_TIPO_CONTA")
    private Long tipoContaId;
}
