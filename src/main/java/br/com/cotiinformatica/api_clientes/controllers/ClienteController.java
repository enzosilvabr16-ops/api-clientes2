package br.com.cotiinformatica.api_clientes.controllers;

import br.com.cotiinformatica.api_clientes.dto.ClienteRequest;
import br.com.cotiinformatica.api_clientes.dto.ClienteResponse;
import br.com.cotiinformatica.api_clientes.entities.Cliente;
import br.com.cotiinformatica.api_clientes.repositories.ClienteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.sql.Date;
import java.time.LocalDateTime;
import java.util.Map;

@RestController
@RequestMapping("/api/clientes")
public class ClienteController {

    @Autowired
    private ClienteRepository clienteRepository;

    @PostMapping
    public ResponseEntity<?> post(@RequestBody ClienteRequest request) {

        var cliente = new Cliente();
        cliente.setNome(request.nome());
        cliente.setEmail(request.email());
        cliente.setCpf(request.cpf());
        cliente.setPlanoId(request.planoId());

        try {
            var id = clienteRepository.inserir(cliente);
            var response = new ClienteResponse(id, request.nome(), request.email(),
                    request.cpf(), request.planoId(), LocalDateTime.now());

            var result = Map.of(
                    "mensagem", "Cliente cadastrado com sucesso!",
                    "cliente", response
            );

            return ResponseEntity.status(201).body(result);

        } catch (Exception e) {
            return ResponseEntity.status(500).body("Falha: " + e.getMessage());
        }

    }
}
