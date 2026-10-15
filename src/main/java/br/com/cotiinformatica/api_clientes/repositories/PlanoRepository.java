package br.com.cotiinformatica.api_clientes.repositories;

import br.com.cotiinformatica.api_clientes.factories.ConnectionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class PlanoRepository {


    @Autowired
    private ConnectionFactory connectionFactory;
}
