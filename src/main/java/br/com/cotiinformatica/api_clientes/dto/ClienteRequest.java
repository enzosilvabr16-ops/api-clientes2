package br.com.cotiinformatica.api_clientes.dto;

public record ClienteRequest(
        String nome,
        String email,
        String cpf,
        Integer planoId
) {
}
