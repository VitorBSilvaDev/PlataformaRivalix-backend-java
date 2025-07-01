package br.com.plataforma_rivalix_back.plataforma_rivalix.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.plataforma_rivalix_back.plataforma_rivalix.model.Usuario;

public interface IUsuario extends JpaRepository<Usuario, Integer> {
	Optional<Usuario> findByEmail(String email); // Optinal = Pode ou não existir um valor 
    Optional<Usuario> findByNomeUsuario(String nomeUsuario); 
}
