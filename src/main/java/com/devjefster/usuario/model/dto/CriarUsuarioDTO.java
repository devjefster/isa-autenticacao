package com.devjefster.usuario.model.dto;

import com.devjefster.usuario.model.Usuario;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CriarUsuarioDTO {

    @NotBlank(message = "Nome  não pode ser vazio")
    @Size(min = 2, max = 100, message = "Nome de ter mais de 2 digitos ")
    private String nome;

    @NotBlank(message = "Email  não pode ser vazio")
    @Email(message = "Email inválido")
    private String email;

    @NotBlank(message = "Telefone não pode ser vazio")
    private String telefone;

    @NotBlank(message = "Password não pode ser vazio")
    @Size(min = 8, message = "Password deve ter mais de 8 digitos")
    private String password;


    public Usuario map() {
        Usuario usuario = new Usuario();
        usuario.setNome(this.getNome());
        usuario.setTelefone(this.getTelefone());
        usuario.setEmail(this.getEmail());
        return usuario;
    }
}
