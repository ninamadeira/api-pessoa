package br.com.madeira.apipessoa.services;

import br.com.madeira.apipessoa.domain.Pessoa;
import br.com.madeira.apipessoa.domain.dto.PessoaDTO;
import br.com.madeira.apipessoa.repositories.PessoaRepository;
import br.com.madeira.apipessoa.services.exceptions.ObjectNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.modelmapper.ModelMapper;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@SpringBootTest
class PessoaServiceImplTest {

    private static final Integer ID = 1;
    private static final Integer INDEX   = 0;
    private static final String  NOME = "Maria Silva";
    private static final LocalDate DATANASCIMENTO = LocalDate.of(2013,1,1);
    private static final String LOGRADOURO = "Rua 11 de outubro";
    private static final Integer NUMERO = 11;
    private static final String CEP = "99999111";
    private static final String CIDADE = "Porto Alegre";

    private static final String OBJETO_NAO_ENCONTRADO = "Objeto não encontrado";

    @InjectMocks
    private PessoaServiceImpl service;

    @Mock
    private PessoaRepository repository;

    @Mock
    private ModelMapper mapper;

    private Pessoa pessoa;
    private PessoaDTO pessoaDTO;

    private Optional<Pessoa> optionalPessoa;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        startPessoa();
    }
    @Test
    void whenFindByIdThenReturnPessoaInstance() {
        when(repository.findById(anyInt())).thenReturn(optionalPessoa);

        Pessoa response = service.findById(ID);

        assertNotNull(response);
        assertEquals(Pessoa.class, response.getClass());
        assertEquals(ID, response.getId());
        assertEquals(NOME, response.getNome());
        assertEquals(LOGRADOURO, response.getLogradouro());
        assertEquals(DATANASCIMENTO, response.getDataNascimento());
        assertEquals(NUMERO, response.getNumero());
        assertEquals(CEP, response.getCep());
        assertEquals(CIDADE, response.getCidade());
    }

    @Test
    void whenFindByIdThenReturnAnObjectNotFoundException(){
        when(repository.findById((anyInt())))
                .thenThrow(new ObjectNotFoundException(OBJETO_NAO_ENCONTRADO));

        try{
            service.findById(ID);
        } catch (Exception ex){
            assertEquals(ObjectNotFoundException.class, ex.getClass());
            assertEquals(OBJETO_NAO_ENCONTRADO, ex.getMessage());
        }
    }

    @Test
    void whenFindAllThenReturnAnListOfPessoas() {
        when(repository.findAll()).thenReturn(List.of(pessoa));

        List<Pessoa> response = service.findAll();

        assertNotNull(response);
        assertEquals(1, response.size());
        assertEquals(Pessoa.class, response.get(INDEX).getClass());

        assertEquals(ID, response.get(INDEX).getId());
        assertEquals(NOME, response.get(INDEX).getNome());
        assertEquals(LOGRADOURO, response.get(INDEX).getLogradouro());
        assertEquals(DATANASCIMENTO, response.get(INDEX).getDataNascimento());
        assertEquals(NUMERO, response.get(INDEX).getNumero());
        assertEquals(CEP, response.get(INDEX).getCep());
        assertEquals(CIDADE, response.get(INDEX).getCidade());
    }

    @Test
    void whenCreateThenReturnSuccess() {
        when(repository.save(any())).thenReturn(pessoa);

        Pessoa response = service.create(pessoaDTO);

        assertNotNull(response);
        assertEquals(Pessoa.class ,response.getClass());
        assertEquals(ID, response.getId());
        assertEquals(NOME, response.getNome());
        assertEquals(LOGRADOURO, response.getLogradouro());
        assertEquals(DATANASCIMENTO, response.getDataNascimento());
        assertEquals(NUMERO, response.getNumero());
        assertEquals(CEP, response.getCep());
        assertEquals(CIDADE, response.getCidade());
    }

    @Test
    void whenUpdateThenSuccess() {
        when(repository.save((any()))).thenReturn(pessoa);

        Pessoa response = service.update(pessoaDTO);

        assertNotNull(response);
        assertEquals(Pessoa.class, response.getClass());
        assertEquals(ID, response.getId());
        assertEquals(NOME, response.getNome());
        assertEquals(LOGRADOURO, response.getLogradouro());
        assertEquals(DATANASCIMENTO, response.getDataNascimento());
        assertEquals(NUMERO, response.getNumero());
        assertEquals(CEP, response.getCep());
        assertEquals(CIDADE, response.getCidade());
    }

    @Test
    void deleteWithSuccess() {
        when(repository.findById(anyInt())).thenReturn(optionalPessoa);
        doNothing().when(repository).deleteById(anyInt());
        service.delete(ID);
        verify(repository, times(1)).deleteById(anyInt());
    }

    @Test
    void whenDeleteThenReturnObjectNotFoundException() {
        when(repository.findById(anyInt()))
                .thenThrow(new ObjectNotFoundException(OBJETO_NAO_ENCONTRADO));
        try {
            service.delete(ID);
        } catch (Exception ex) {
            assertEquals(ObjectNotFoundException.class, ex.getClass());
            assertEquals(OBJETO_NAO_ENCONTRADO, ex.getMessage());
        }
    }

    private void startPessoa() {
        pessoa = new Pessoa(ID, NOME, DATANASCIMENTO, LOGRADOURO, NUMERO, CEP, CIDADE);
        pessoaDTO = new PessoaDTO(ID, NOME, DATANASCIMENTO, LOGRADOURO, NUMERO, CEP, CIDADE);
        optionalPessoa= Optional.of(new Pessoa(ID, NOME, DATANASCIMENTO, LOGRADOURO, NUMERO, CEP, CIDADE));
    }
}