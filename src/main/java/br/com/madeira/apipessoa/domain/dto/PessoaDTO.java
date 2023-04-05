package br.com.madeira.apipessoa.domain.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class PessoaDTO {
    private Integer id;
    private String nome;
    @JsonFormat(pattern="dd/MM/yyyy")
    private LocalDate dataNascimento;
    private String logradouro;
    private Integer numero;
    private String cep;
    private String cidade;

}
