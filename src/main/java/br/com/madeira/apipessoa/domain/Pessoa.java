package br.com.madeira.apipessoa.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.NotBlank;
import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Pessoa {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @NotBlank(message = "Nome é obrigatório")
    private String nome;
    private LocalDate dataNascimento;
    @NotBlank(message = "Logradouro é obrigatório")
    private String logradouro;
    private Integer numero;
    @NotBlank(message = "Cep é obrigatório")
    private String cep;
    @NotBlank(message = "Cidade é obrigatório")
    private String cidade;

}
