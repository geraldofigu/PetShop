package br.com.tt.PetShop.service;

import br.com.tt.PetShop.dto.AnimalConsulta;
import br.com.tt.PetShop.dto.AnimalCriacao;
import br.com.tt.PetShop.mapper.AnimalMapper;
import br.com.tt.PetShop.model.Animal;
import br.com.tt.PetShop.model.Cliente;
import br.com.tt.PetShop.repository.AnimalRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class AnimalService {

    private final AnimalRepository animalRepository;

    private final ClienteService clienteService;

    public AnimalService(AnimalRepository animalRepository, ClienteService clienteService) {
        this.animalRepository = animalRepository;
        this.clienteService = clienteService;
    }

    //criar
    public Long criar(AnimalCriacao criacao) {
        Cliente cliente = clienteService.acharTutorId(criacao.getTutorId());
        Animal animal = AnimalMapper.INSTANCE.fromCriar(criacao);
        animal.setTutor(cliente);
        Animal animalSalvo = animalRepository.save(animal);
        return animalSalvo.getId();
    }

    //atualizar apelido
    public void atualizarApelido(Long id, String novoApelido) {
        Animal animal = animalRepository.findById(id).orElseThrow();
        animal.setApelido(novoApelido);
        animalRepository.save(animal);
    }

    //atualizar nascimento
    public void atualizarNascimento(Long id, LocalDate nascimento) {
        Animal animal = animalRepository.findById(id).orElseThrow();
        animal.setNascimento(nascimento);
        animalRepository.save(animal);
    }

    //listar
    public List<AnimalConsulta> listar() {
        return animalRepository.findAll().stream()
                .map(AnimalMapper.INSTANCE::converterConsulta)
                .collect(Collectors.toList());
    }
    //buscarPorTutor
    public List<AnimalConsulta> buscaPorTutor(String cpf) {
        return animalRepository.findByTutorCpf(cpf).stream()
                .map(AnimalMapper.INSTANCE::converterConsulta)
                .collect(Collectors.toList());
    }

//    //buscarPorId
//    public Animal buscaPorId(Long id) {
//        return animalRepository.findById(id).get();
//    }

    //apagar
    public void apagar(Long id) {
        Animal animal = animalRepository.findById(id).orElseThrow();
        if(animal != null) {
        animalRepository.deleteById(id);
        }
    }

}
