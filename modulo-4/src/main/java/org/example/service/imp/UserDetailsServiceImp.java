package org.example.service.imp;

import org.example.domain.entity.Usuario;
import org.example.domain.repositorio.UsuarioRepository;
import org.example.exception.SenhaInvalidaException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class UserDetailsServiceImp implements UserDetailsService {

    @Autowired
    private PasswordEncoder encoder;

    @Autowired
    private UsuarioRepository repository;

    public UserDetails autenticar(Usuario usuario) {
        UserDetails userDetails = loadUserByUsername(usuario.getLogin());
        boolean senhaOk = encoder.matches(usuario.getSenha(), userDetails.getPassword());

        if(senhaOk) {
            return userDetails;
        }

        throw new SenhaInvalidaException();
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Usuario usuario = repository
                .findByLogin(username)
                .orElseThrow(() -> new UsernameNotFoundException("Usuario não encontrado na base de dados"));

        String[] roles = usuario.getAdmin() ? new String[]{"ADMIN", "USER"} : new String[]{"USER"};

        return User
                .builder()
                .username(usuario.getLogin())
                .password(usuario.getSenha())
                .roles(roles)
                .build();
    }

    @Transactional
    public Usuario saveNewUser(Usuario usuario) {
        usuario.setSenha(encoder.encode(usuario.getSenha()));

        return repository.save(usuario);
    }
}
