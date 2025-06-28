package br.com.plataforma_rivalix_back.plataforma_rivalix.service;

import java.util.List;
import java.util.Optional;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import br.com.plataforma_rivalix_back.plataforma_rivalix.model.Usuario;
import br.com.plataforma_rivalix_back.plataforma_rivalix.repository.IUsuario;

@Service
public class UsuarioService {
	
	private final IUsuario repository;
	private final PasswordEncoder passwordEncoder;
	
	public UsuarioService(IUsuario repository, PasswordEncoder passwordEncoder) {
        this.repository = repository;
        this.passwordEncoder = passwordEncoder;
    }	
	
	public List<Usuario> listarUsuario() {
		return repository.findAll();
	}

    // ========================================================================
    // MÉTODO FALTANTE ADICIONADO AQUI
    // ========================================================================
    /**
     * Busca um usuário pelo seu e-mail.
     * @param email O e-mail do usuário a ser buscado.
     * @return O objeto Usuario se encontrado.
     * @throws RuntimeException se nenhum usuário for encontrado com o e-mail fornecido.
     */
    public Usuario buscarPorEmail(String email) {
        return repository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("Usuário não encontrado com o e-mail: " + email));
    }
	
	public Usuario criarUsuario(Usuario usuario) {
        if (repository.findByEmail(usuario.getEmail()).isPresent()) {
            throw new RuntimeException("Este e-mail já está cadastrado.");
        }
        String senhaCriptografada = this.passwordEncoder.encode(usuario.getSenha());
        usuario.setSenha(senhaCriptografada);
        return repository.save(usuario);
    }
	
	public Usuario editarUsuario(Usuario usuario) {
        Usuario usuarioExistente = repository.findById(usuario.getId())
            .orElseThrow(() -> new RuntimeException("Usuário não encontrado para edição. ID: " + usuario.getId()));

        if (usuario.getSenha() != null && !usuario.getSenha().trim().isEmpty()) {
            String senhaCriptografada = this.passwordEncoder.encode(usuario.getSenha());
            usuarioExistente.setSenha(senhaCriptografada);
        }
        
        usuarioExistente.setNome(usuario.getNome());
        usuarioExistente.setNomeUsuario(usuario.getNomeUsuario());

        return repository.save(usuarioExistente);
    }

	public Boolean excluirUsuario(Integer id) {
		repository.deleteById(id);
		return true;
	}

	public Boolean validarSenha(Usuario usuarioParaValidar) {
        Optional<Usuario> usuarioOptional = repository.findByEmail(usuarioParaValidar.getEmail());

        if (usuarioOptional.isEmpty()) {
            return false;
        }

        Usuario usuarioDoBanco = usuarioOptional.get();

        return passwordEncoder.matches(usuarioParaValidar.getSenha(), usuarioDoBanco.getSenha());
    }
}
