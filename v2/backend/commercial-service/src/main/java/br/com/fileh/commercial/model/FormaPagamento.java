package br.com.fileh.commercial.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

@Entity
@Table(name = "FORMA_PAGAMENTO")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class FormaPagamento {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "FGP_CD_FORMA_PAGAMENTO")
    private Long id;

    @Column(name = "FGP_CD_ENTIDADE", nullable = false)
    private Long entidadeId;

    @Column(name = "FGP_DS_FORMA_PAGAMENTO", nullable = false)
    private String descricao;
}
