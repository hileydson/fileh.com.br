package br.com.fileh.auth.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import br.com.fileh.auth.model.converter.BooleanToIntegerConverter;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import lombok.ToString;
import lombok.EqualsAndHashCode;

@Entity
@Table(name = "SUBUSUARIO")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class SubUsuario {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "SBU_CD_SUBUSUARIO")
    private Long id;

    @JsonIgnore
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "SBU_CD_USUARIO", nullable = false)
    private Usuario usuario;

    @Column(name = "SBU_CD_ENTIDADE")
    private Long entidadeId;

    @Column(name = "SBU_NM_SUBUSUARIO", nullable = false)
    private String nome;

    @Column(name = "SBU_DS_LOGIN", nullable = false, unique = true)
    private String login;

    @Column(name = "SBU_SH_SUBUSUARIO", nullable = false)
    private String senha;

    @Column(name = "SBU_FL_MODULO_FINANCEIRO", nullable = false)
    @Convert(converter = BooleanToIntegerConverter.class)
    private Boolean moduloFinanceiro = false;

    @Column(name = "SBU_FL_MODULO_VENDA", nullable = false)
    @Convert(converter = BooleanToIntegerConverter.class)
    private Boolean moduloVenda = false;

    @Column(name = "SBU_FL_ADM", nullable = false)
    @Convert(converter = BooleanToIntegerConverter.class)
    private Boolean isAdm = false;

    @Column(name = "SBU_FL_MODULO_CLIENTE", nullable = false)
    @Convert(converter = BooleanToIntegerConverter.class)
    private Boolean moduloCliente = false;

    @Column(name = "SBU_FL_MODULO_CAIXA", nullable = false)
    @Convert(converter = BooleanToIntegerConverter.class)
    private Boolean moduloCaixa = false;

    @Column(name = "SBU_FL_IMPORT_CSV", nullable = false)
    @Convert(converter = BooleanToIntegerConverter.class)
    private Boolean permiteImportarCSV = false;

    @Column(name = "SBU_DS_MSG_FOOTER")
    private String msgFooter;
}
