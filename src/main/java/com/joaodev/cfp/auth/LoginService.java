package com.joaodev.cfp.auth;

import com.joaodev.cfp.config.JwtServiceGenerator;
import com.joaodev.cfp.entity.Usuario;
import com.joaodev.cfp.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Service;


@Service
public class LoginService {
	
	@Autowired
	private UsuarioRepository repository;
	@Autowired
	private JwtServiceGenerator jwtService;
	@Autowired
	private AuthenticationManager authenticationManager;


	public String logar(Login login) {

		try {
			authenticationManager.authenticate(
					new UsernamePasswordAuthenticationToken(
							login.getUsername(),
							login.getPassword()
					)
			);
		} catch (Exception e) {
			System.out.println("Erro ao autenticar: " + e.getMessage());
			throw e;
		}

		Usuario user = repository.findByEmail(login.getUsername()).get();

		String jwtToken = jwtService.generateToken(user);

		return jwtToken;
	}



}
