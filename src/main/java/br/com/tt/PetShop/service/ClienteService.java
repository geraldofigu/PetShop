package br.com.tt.PetShop.service;

import br.com.tt.PetShop.dto.ClienteBuscaCpf;
import br.com.tt.PetShop.dto.ClienteConsulta;
import br.com.tt.PetShop.dto.ClienteCriacao;
import br.com.tt.PetShop.exception.CreditoExcepetion;
import br.com.tt.PetShop.mapper.ClienteMapper;
import br.com.tt.PetShop.model.Cliente;
import br.com.tt.PetShop.repository.ClienteRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ClienteService {

    private final ClienteRepository clienteRepository;

    public ClienteService(ClienteRepository clienteRepository) {
        this.clienteRepository = clienteRepository;
    }

    //criar
    public Long criar(ClienteCriacao criacao) {
        validaCpf(criacao.getCpf());
        Cliente cliente = ClienteMapper.INSTANCE.fromCriar(criacao);
        Cliente clienteSalvo = clienteRepository.save(cliente);
        return clienteSalvo.getId();
    }

    private void validaCpf(String cpf) {
        if(cpf.endsWith("4")) {
            throw new CreditoExcepetion(String.format("CPF com pendencias: %s", cpf));
        }
    }

    //atualizar nome
    public void atualizarNome(Long id, String nome) {
        Cliente cliente = clienteRepository.findById(id).orElseThrow();
        cliente.setNome(nome);
        clienteRepository.save(cliente);
    }
    //listar
    public List<ClienteConsulta> listar() {
        return clienteRepository.findAll().stream()
                .map(ClienteMapper.INSTANCE::converterConsulta)
                .collect(Collectors.toList());
    }
    //buscar por Cpf
    public ClienteBuscaCpf buscarPorCpf(String cpf) {
        Cliente cliente = clienteRepository.findByCpf(cpf);
        return ClienteMapper.INSTANCE.converterConsultaCpf(cliente);
    }
    //apagar
    public void apagar(Long id) {
        clienteRepository.deleteById(id);
    }

    //acharTutorpeloId
    public Cliente acharTutorId(Long id) {
        return clienteRepository.findById(id).orElseThrow();
    }

}
