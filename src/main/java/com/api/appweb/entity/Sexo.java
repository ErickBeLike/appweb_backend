package com.api.appweb.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "sexos")
public class Sexo {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_sexo")
    private Long idSexo;
    @Column(name = "sexo")
    private String sexo;

    public Long getIdSexo() {
        return idSexo;
    }

    public void setIdSexo(Long idSexo) {
        this.idSexo = idSexo;
    }

    public String getSexo() {
        return sexo;
    }

    public void setSexo(String sexo) {
        this.sexo = sexo;
    }
}
