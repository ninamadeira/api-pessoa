package br.com.madeira.apipessoa.resourcea;

import br.com.madeira.apipessoa.domain.Pessoa;
import br.com.madeira.apipessoa.domain.dto.PessoaDTO;
import br.com.madeira.apipessoa.services.PessoaServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.modelmapper.ModelMapper;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@SpringBootTest
class PessoaResourceTest {

    private static final  Integer ID = 1;
    private static final Integer INDEX   = 0;
    private static final String  NOME = "Maria Silva";
    private static final LocalDate DATANASCIMENTO = LocalDate.of(2013,1,1);
    private static final String LOGRADOURO = "Rua 11 de outubro";
    private static final Integer NUMERO = 11;
    private static final String CEP = "99999111";
    private static final String CIDADE = "Porto Alegre";

    @InjectMocks
    private PessoaResource resource;

    @Mock
    private PessoaServiceImpl service;

    @Mock
    private ModelMapper mapper;

    private Pessoa pessoa;
    private PessoaDTO pessoaDTO;


    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        startPessoa();
    }

    @Test
    void whenFindByIdThenReturnSuccess() {
        when(service.findById(anyInt())).thenReturn(pessoa);
        when(mapper.map(any(), any())).thenReturn(pessoaDTO);

        ResponseEntity<PessoaDTO> response = resource.findById(ID);

        assertNotNull(response);
        assertNotNull(response.getBody());
        assertEquals(ResponseEntity.class, response.getClass());
        assertEquals(PessoaDTO.class, response.getBody().getClass());
        assertEquals(ID, response.getBody().getId());
        assertEquals(NOME, response.getBody().getNome());
        assertEquals(LOGRADOURO, response.getBody().getLogradouro());
        assertEquals(DATANASCIMENTO, response.getBody().getDataNascimento());
        assertEquals(NUMERO, response.getBody().getNumero());
        assertEquals(CEP, response.getBody().getCep());
        assertEquals(CIDADE, response.getBody().getCidade());
    }

    @Test
    void whenFindAllThenReturnAllListOfPessoaDTO() {
        when(service.findAll()).thenReturn(List.of(pessoa));
        when(mapper.map(any(), any())).thenReturn(pessoaDTO);

        ResponseEntity<List<PessoaDTO>> response = resource.findAll();

        assertNotNull(response);
        assertNotNull(response.getBody());
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(ResponseEntity.class, response.getClass());
        assertEquals(ArrayList.class, response.getBody().getClass());
        assertEquals(PessoaDTO.class, response.getBody().get(INDEX).getClass());
        assertEquals(ID, response.getBody().get(INDEX).getId());
        assertEquals(NOME, response.getBody().get(INDEX).getNome());
        assertEquals(LOGRADOURO, response.getBody().get(INDEX).getLogradouro());
        assertEquals(DATANASCIMENTO, response.getBody().get(INDEX).getDataNascimento());
        assertEquals(NUMERO, response.getBody().get(INDEX).getNumero());
        assertEquals(CEP, response.getBody().get(INDEX).getCep());
        assertEquals(CIDADE, response.getBody().get(INDEX).getCidade());
    }

    @Test
    void whenUpdateThenReturnSuccess() {
        when(service.update(pessoaDTO)).thenReturn(pessoa);
        when(mapper.map(any(), any())).thenReturn(pessoaDTO);

        ResponseEntity<PessoaDTO> response = resource.update(ID, pessoaDTO);

        assertNotNull(response);
        assertNotNull(response.getBody());
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(ResponseEntity.class, response.getClass());
        assertEquals(PessoaDTO.class, response.getBody().getClass());

        assertEquals(ID, response.getBody().getId());
        assertEquals(NOME, response.getBody().getNome());
        assertEquals(LOGRADOURO, response.getBody().getLogradouro());
        assertEquals(DATANASCIMENTO, response.getBody().getDataNascimento());
        assertEquals(NUMERO, response.getBody().getNumero());
        assertEquals(CEP, response.getBody().getCep());
        assertEquals(CIDADE, response.getBody().getCidade());
    }

    @Test
    void whenDeleteThenReturnSuccess(){
        doNothing().when(service).delete(anyInt());

        ResponseEntity<PessoaDTO> response = resource.delete(ID);

        assertNotNull(response);
        assertEquals(ResponseEntity.class, response.getClass());
        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
        verify(service, times(1)).delete(anyInt());
    }

    private void startPessoa() {
        pessoa = new Pessoa(ID, NOME, DATANASCIMENTO, LOGRADOURO, NUMERO, CEP, CIDADE);
        pessoaDTO = new PessoaDTO(ID, NOME, DATANASCIMENTO, LOGRADOURO, NUMERO, CEP, CIDADE);
    }
}