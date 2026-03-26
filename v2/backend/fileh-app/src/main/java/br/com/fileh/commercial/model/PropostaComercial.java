package br.com.fileh.commercial.model;

import jakarta.persistence.*;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import java.math.BigDecimal;
import org.hibernate.annotations.Formula;
import java.time.LocalDateTime;

@Entity
@Table(name = "PROPOSTA_COMERCIAL")
@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class PropostaComercial {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "PRC_CD_PROPOSTA_COMERCIAL")
    private Long id;

    @Column(name = "PRC_CD_ENTIDADE", nullable = false)
    private Long entidadeId;

    @Column(name = "PRC_CD_USUARIO", nullable = false)
    private Long usuarioId;

    @Column(name = "PRC_CD_CLIENTE")
    private Long clienteId;

    @Column(name = "PRC_VL_DESCONTO")
    private BigDecimal valorDesconto;

    @Column(name = "PRC_VL_TOTAL")
    @JsonIgnore
    private BigDecimal valorTotal = BigDecimal.ZERO;

    @Formula("(SELECT COALESCE(SUM(it.ipc_vl_item_proposta * it.ipc_nr_quantidade), 0) FROM item_proposta it WHERE it.ipc_cd_proposta_comercial = {alias}.prc_cd_proposta_comercial) - COALESCE({alias}.prc_vl_desconto, 0)")
    @JsonProperty(value = "valorTotal", access = JsonProperty.Access.READ_ONLY)
    private BigDecimal totalCalculado;

    @Column(name = "PRC_VL_FRETE")
    @JsonIgnore
    private BigDecimal valorFrete = BigDecimal.ZERO;

    @Column(name = "PRC_DT_CADASTRO")
    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")
    private LocalDateTime dataCadastro;

    @Column(name = "PRC_DT_PREVISTA")
    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")
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
        if (this.ativo == null) this.ativo = true;
        if (this.valorTotal == null) this.valorTotal = BigDecimal.ZERO;
        if (this.valorFrete == null) this.valorFrete = BigDecimal.ZERO;
        if (this.dataCadastro == null) this.dataCadastro = LocalDateTime.now();
    }
}
