package org.example.rest.controller;

import lombok.RequiredArgsConstructor;
import org.example.domain.entity.Usuario;
import org.example.exception.SenhaInvalidaException;
import org.example.rest.dto.CredenciaisDto;
import org.example.rest.dto.TokenDto;
import org.example.security.jwt.JwtService;
import org.example.service.imp.UserDetailsServiceImp;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/usuarios")
@RequiredArgsConstructor
public class UsuarioController {

    private final UserDetailsServiceImp service;
    private final JwtService jwtService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Usuario saveUser(@Valid @RequestBody Usuario usuario) {
        return service.saveNewUser(usuario);
    }

    @PostMapping("/auth")
    @ResponseStatus(HttpStatus.OK)
    public TokenDto getToken(@Valid @RequestBody CredenciaisDto credenciaisDto) {
        try {
            Usuario user = Usuario.builder().login(credenciaisDto.getLogin()).senha(credenciaisDto.getSenha()).build();
            UserDetails usuarioAutenticado = service.autenticar
                    (user);

            String token = jwtService.gerarToken(user);
            return new TokenDto(user.getLogin(), token);
        }catch(UsernameNotFoundException | SenhaInvalidaException e) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, e.getMessage());
        }
    }
}
