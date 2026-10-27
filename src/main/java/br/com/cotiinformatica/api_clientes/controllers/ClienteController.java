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
                    request.cpf(), request.planoId());

            var result = Map.of(
                    "mensagem", "Cliente cadastrado com sucesso!",
                    "cliente", response,
                    "dataHoraCadastro", LocalDateTime.now()
            );

            return ResponseEntity.status(201).body(result);

        } catch (Exception e) {
            return ResponseEntity.status(500).body("Falha: " + e.getMessage());
        }
    }

    @PutMapping("{id}")
    public ResponseEntity<?> put(@PathVariable Integer id, @RequestBody ClienteRequest request) {

        var cliente = new Cliente();
        cliente.setId(id);
        cliente.setNome(request.nome());
        cliente.setEmail(request.email());
        cliente.setCpf(request.cpf());
        cliente.setPlanoId(request.planoId());

        try {
            if(clienteRepository.alterar(cliente)) {
                var response = new ClienteResponse(id, request.nome(), request.email(),
                        request.cpf(), request.planoId());

                var result = Map.of(
                        "mensagem", "Cliente atualizado com sucesso!",
                        "cliente", response,
                        "dataHoraAlteração", LocalDateTime.now()
                );

                return ResponseEntity.status(200).body(result);
            }
            else{
                return ResponseEntity.status(404).body("Cliente não encontrado. Verifique o id.");
            }
        } catch (Exception e) {
            return ResponseEntity.status(500).body("Falha: " + e.getMessage());
        }
    }
    @DeleteMapping("{id}")
    public ResponseEntity<?> delete(@PathVariable Integer id) {

        try {
            if(clienteRepository.inativar(id)) {
                var result = Map.of(
                        "mensagem", "Cliente excluido com sucesso!",
                        "id", id,
                        "dataHoraExclusao", LocalDateTime.now()
                );
                return ResponseEntity.status(200).body(result);
            }
            else{
                return ResponseEntity.status(404).body("Cliente não encontrado. Verifique o id.");
            }
        }
        catch (Exception e) {
            return ResponseEntity.status(500).body("Falha: " + e.getMessage());
        }
    }

    @GetMapping
    public ResponseEntity<?> get(@RequestParam String nome) {

        try {

            //Consultando os clientes no banco e retornando uma lista
            var clientes = clienteRepository.obterPorNome(nome);

            //Copiando a lista de clientes para uma lista do DTO
            var response = clientes.stream()
                    .map(cliente -> new ClienteResponse(
                            cliente.getId(),
                            cliente.getNome(),
                            cliente.getEmail(),
                            cliente.getCpf(),
                            cliente.getPlanoId()
                    )).toList();

            //HTTP 200 (OK)
            return ResponseEntity.status(200).body(response);
        }
        catch (Exception e) {
            return ResponseEntity.status(500).body("Falha: " + e.getMessage());
        }
    }

    @GetMapping("{id}")
    public ResponseEntity<?> getById(@PathVariable Integer id) {

        try {

            //Consultando o cliente no banco atraves do id
            var clientes = clienteRepository.obterPorId(id);

            if (clientes != null) {
                var response = new ClienteResponse(
                        clientes.getId(),
                        clientes.getNome(),
                        clientes.getEmail(),
                        clientes.getCpf(),
                        clientes.getPlanoId()
                );
                return ResponseEntity.status(200).body(response);
            }
            else {
                return ResponseEntity.status(404).body("Cliente não encontrado. Verifique o id.");
            }

        }
        catch (Exception e) {
            return ResponseEntity.status(500).body("Falha: " + e.getMessage());
        }
    }
}
