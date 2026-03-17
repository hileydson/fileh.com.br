package br.com.fileh.commercial.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import java.math.BigDecimal;

@Entity
@Table(name = "PRODUTO")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Produto {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "PRO_CD_PRODUTO")
    private Long id;

    @Column(name = "PRO_CD_ENTIDADE", nullable = false)
    private Long entidadeId;

    @Column(name = "PRO_DS_PRODUTO", nullable = false)
    private String descricao;

    @Column(name = "PRO_CD_SKU")
    private String sku;

    @Column(name = "PRO_VL_VENDA", nullable = false)
    private BigDecimal valorVenda;

    @Column(name = "PRO_DS_UNIDADE", nullable = false)
    private String unidade;
}
