package br.com.fileh.crm.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

@Entity
@Table(name = "CLIENTE")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Cliente {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "CLI_CD_CLIENTE")
    private Long id;

    @Column(name = "CLI_CD_ENTIDADE", nullable = false)
    private Long entidadeId;

    @Column(name = "CLI_NM_CLIENTE", nullable = false)
    private String nome;

    @Column(name = "CLI_DS_LOGRADOURO", nullable = false)
    private String logradouro;

    @Column(name = "CLI_DS_BAIRRO", nullable = false)
    private String bairro;

    @Column(name = "CLI_DS_UF", nullable = false)
    private String uf;

    @Column(name = "CLI_NR_TEL", nullable = false)
    private String telefone;

    @Column(name = "CLI_DS_ENTIDADE", nullable = false)
    private String nomeEntidade;

    @Column(name = "CLI_DS_REFERENCIA", nullable = false)
    private String referencia;

    @Column(name = "CLI_NR_CPF", nullable = false)
    private String cpf;
}
