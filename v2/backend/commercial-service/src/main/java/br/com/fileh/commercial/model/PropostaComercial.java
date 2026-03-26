package br.com.fileh.commercial.model;

import jakarta.persistence.*;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "PROPOSTA_COMERCIAL")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class PropostaComercial {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "PRC_CD_PROPOSTA_COMERCIAL")
    private Long id;

    // Entity/Branch ID
    @Column(name = "PRC_CD_ENTIDADE", nullable = false)
    private Long entidadeId;

    @Column(name = "PRC_CD_USUARIO", nullable = false)
    private Long usuarioId;

    @Column(name = "PRC_CD_CLIENTE")
    private Long clienteId;

    @Column(name = "PRC_VL_DESCONTO", nullable = false)
    private BigDecimal valorDesconto;

    @Column(name = "PRC_VL_FRETE", nullable = false)
    private BigDecimal valorFrete;

    @Column(name = "PRC_VL_TOTAL", nullable = false)
    private BigDecimal valorTotal = BigDecimal.ZERO;

    @Column(name = "PRC_DT_CADASTRO")
    @JsonFormat(pattern = "yyyy-MM-dd['T'HH:mm:ss[.SSS][XXX]]")
    private LocalDateTime dataCadastro;

    @Column(name = "PRC_DT_PREVISTA")
    @JsonFormat(pattern = "yyyy-MM-dd['T'HH:mm:ss[.SSS][XXX]]")
    private LocalDateTime dataPrevista;

    @Column(name = "PRC_DS_OBS")
    private String observacao;

    @Column(name = "PRC_NM_ATENDENTE")
    private String atendente;

    @Column(name = "PRC_DS_SITUACAO")
    private String situacao;

    @Column(name = "PRC_DS_FORMA_PAGAMENTO")
    private String formaPagamento;

    @Column(name = "PRC_BT_ATIVO", nullable = false)
    private Boolean ativo = true;

    @PrePersist
    public void prePersist() {
        if (this.ativo == null) {
            this.ativo = true;
        }
    }
}
