package br.com.fileh.auth.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

@Entity
@Table(name = "USUARIO")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Usuario {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "USU_CD_USUARIO")
    private Long id;

    @Column(name = "USU_NM_USUARIO", nullable = false)
    private String nome;

    @Column(name = "USU_NR_CNPJ", nullable = false)
    private String cnpj;

    @Column(name = "USU_DS_EMAIL", nullable = false)
    private String email;

    @Column(name = "USU_DS_SUBDOMINIO", nullable = false)
    private String subdominio;

    @Column(name = "USU_DS_FANTASIA")
    private String fantasia;

    @Column(name = "USU_DS_LOGRADOURO")
    private String logradouro;

    @Column(name = "USU_DS_NUMERO")
    private String numero;

    @Column(name = "USU_DS_BAIRRO")
    private String bairro;

    @Column(name = "USU_DS_CIDADE")
    private String cidade;

    @Column(name = "USU_DS_UF")
    private String uf;

    @Column(name = "USU_DS_TEL")
    private String telefone;

    @Column(name = "USU_CD_PLANO", nullable = false)
    private Long planoId;
}
