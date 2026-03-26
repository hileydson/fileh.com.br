package br.com.fileh.crm.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

@Entity
@Table(name = "ENTIDADE")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Entidade {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ENT_CD_ENTIDADE")
    private Long id;

    @Column(name = "ENT_CD_USUARIO", nullable = false)
    private Long usuarioId;

    @Column(name = "ENT_NM_ENTIDADE", nullable = false)
    private String nome;
}
