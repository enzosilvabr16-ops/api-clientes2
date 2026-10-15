package br.com.cotiinformatica.api_clientes.entities;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Setter
@Getter
public class Cliente {

    private Integer id;
    private String nome;
    private String email;
    private String cpf;
    private LocalDateTime dataHoraCadastro;
    private Integer ativo;
    private Integer planoId;
}
