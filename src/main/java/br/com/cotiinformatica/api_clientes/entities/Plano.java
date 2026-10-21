package br.com.cotiinformatica.api_clientes.entities;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class Plano {

    private Integer id;
    private String nome;
    private Double valorMensal;
}
