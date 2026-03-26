package br.com.fileh.crm.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

@Entity
@Table(name = "FORNECEDOR")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Fornecedor {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "FOR_CD_FORNECEDOR")
    private Long id;

    @Column(name = "FOR_CD_ENTIDADE", nullable = false)
    private Long entidadeId;

    @Column(name = "FOR_DS_FORNECEDOR", nullable = false)
    private String nome;

    @Column(name = "FOR_NR_CNPJ")
    private String cnpj;

    @Column(name = "FOR_DS_LOGRADOURO")
    private String logradouro;

    @Column(name = "FOR_DS_BAIRRO")
    private String bairro;

    @Column(name = "FOR_DS_CIDADE")
    private String cidade;

    @Column(name = "FOR_NR_CEP")
    private String cep;

    @Column(name = "FOR_NR_INSC_ESTADUAL")
    private String inscricaoEstadual;

    @Column(name = "FOR_NR_INSC_MUNICIPAL")
    private String inscricaoMunicipal;

    @Column(name = "FOR_DS_CONTATO")
    private String contato;
}
