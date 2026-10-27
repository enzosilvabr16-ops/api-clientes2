package br.com.cotiinformatica.api_clientes.repositories;

import br.com.cotiinformatica.api_clientes.factories.ConnectionFactory;
import br.com.cotiinformatica.api_clientes.entities.Cliente;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

@Repository
public class ClienteRepository {

    @Autowired
    private ConnectionFactory connectionFactory;

    public Integer inserir(Cliente cliente) throws Exception {

        try(var connection = connectionFactory.createConnection()) {
            var statement = connection.prepareStatement(
                     "insert into clientes(nome, email, cpf, plano_id) values(?, ?, ?, ?)",
                    Statement.RETURN_GENERATED_KEYS
            );

            statement.setString(1, cliente.getNome());
            statement.setString(2, cliente.getEmail());
            statement.setString(3, cliente.getCpf());
            statement.setInt(4, cliente.getPlanoId());
            statement.execute();


            var result = statement.getGeneratedKeys();
            if (result.next()) {
                return result.getInt(1);
            }
            else {
                throw new SQLException("Não foi possivel obter o id do cliente");

            }
        }
    }

    public boolean alterar(Cliente cliente) throws Exception {

        try(var connection = connectionFactory.createConnection()) {
            var statement = connection.prepareStatement("""
        update clientes 
        set nome=?, email=?, cpf=?, plano_id=?
        where id=? and ativo=1
        """);
            statement.setString(1, cliente.getNome());
            statement.setString(2, cliente.getEmail());
            statement.setString(3, cliente.getCpf());
            statement.setInt(4, cliente.getPlanoId());
            statement.setInt(5, cliente.getId());
            return statement.executeUpdate() > 0;
        }
    }

    public boolean inativar(Integer id) throws Exception {
        try(var connection = connectionFactory.createConnection()) {
            var statement = connection.prepareStatement("""
            update clientes
            set ativo=0
            where id=? and ativo=1
            """);
            statement.setInt(1, id);
            return statement.executeUpdate() > 0;
        }
    }

    public List<Cliente> obterPorNome(String nome) throws Exception {
        try (var connection = connectionFactory.createConnection()) {
            var statement = connection.prepareStatement("""
        
                    select * from clientes
        where ativo = 1
        and nome like ?
        """);
            statement.setString(1, "%" + nome + "%");
            var result = statement.executeQuery();

            var lista = new ArrayList<Cliente>();

            while(result.next()) {
                var cliente = new Cliente();

                cliente.setId(result.getInt("id"));
                cliente.setNome(result.getString("nome"));
                cliente.setCpf(result.getString("cpf"));
                cliente.setEmail(result.getString("email"));
                cliente.setPlanoId(result.getInt("plano_id"));

                lista.add(cliente);
            }
            return lista;
        }
    }

    public Cliente obterPorId(Integer id) throws Exception {
        try (var connection = connectionFactory.createConnection()) {
            var statement = connection.prepareStatement("""
        
                    select * from clientes
        where ativo = 1
        and id = ?
        """);
            statement.setInt(1, id);
            var result = statement.executeQuery();

            var lista = new ArrayList<Cliente>();

            if(result.next()) {
                var cliente = new Cliente();

                cliente.setId(result.getInt("id"));
                cliente.setNome(result.getString("nome"));
                cliente.setCpf(result.getString("cpf"));
                cliente.setEmail(result.getString("email"));
                cliente.setPlanoId(result.getInt("plano_id"));

                return cliente;
            }
            else{
                return null;
            }
        }
    }

}
