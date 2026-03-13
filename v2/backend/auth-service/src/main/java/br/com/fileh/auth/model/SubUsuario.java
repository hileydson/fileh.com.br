package br.com.fileh.auth.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

@Entity
@Table(name = "SUBUSUARIO")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class SubUsuario {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "SBU_CD_SUBUSUARIO")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "SBU_CD_USUARIO", nullable = false)
    private Usuario usuario; // The tenant/company this user belongs to

    @Column(name = "SBU_NM_SUBUSUARIO", nullable = false)
    private String nome;

    @Column(name = "SBU_DS_LOGIN", nullable = false, unique = true)
    private String login;

    @Column(name = "SBU_SH_SUBUSUARIO", nullable = false)
    private String senha;

    // Permissions/Roles
    @Column(name = "SBU_FL_MODULO_FINANCEIRO", nullable = false)
    private Boolean moduloFinanceiro = false;

    @Column(name = "SBU_FL_MODULO_VENDA", nullable = false)
    private Boolean moduloVenda = false;

    @Column(name = "SBU_FL_ADM", nullable = false)
    private Boolean isAdm = false;

    @Column(name = "SBU_FL_MODULO_CLIENTE", nullable = false)
    private Boolean moduloCliente = false;

    @Column(name = "SBU_FL_MODULO_CAIXA", nullable = false)
    private Boolean moduloCaixa = false;

    @Column(name = "SBU_DS_MSG_FOOTER")
    private String msgFooter;
}
