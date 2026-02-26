package com.exemplo.biblioteca.service;

import com.exemplo.biblioteca.dto.Usuario.UsuarioRequisicaoDTO;
import com.exemplo.biblioteca.dto.Usuario.UsuarioRespostaDTO;
import com.exemplo.biblioteca.model.Livro;
import com.exemplo.biblioteca.utils.Conexao;
import com.exemplo.biblioteca.dao.UsuarioDAO;
import com.exemplo.biblioteca.model.Usuario;
import org.springframework.stereotype.Service;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@Service
public class UsuarioService {

    UsuarioDAO usuarioDAO = new UsuarioDAO();

    public UsuarioRespostaDTO criarUsuario(UsuarioRespostaDTO dto) {

        try (Connection conn = Conexao.connection()){

            Usuario usuario = new Usuario();
            usuario.setNome(dto.nome());
            usuario.setEmail(dto.email());

            usuarioDAO.criarUsuario(conn, usuario);

            return new UsuarioRespostaDTO(usuario.getId(), usuario.getNome(), usuario.getEmail());

        }catch (SQLException e) {
            throw new RuntimeException("Erro ao cadastrar usuario: " + e.getMessage(), e);
        }
    }
    public List<UsuarioRespostaDTO> buscarTodos() {
        try (Connection conn = Conexao.connection()) {
            List<Usuario> usuarios = usuarioDAO.buscarTodos(conn);
            List<UsuarioRespostaDTO> lista = new ArrayList<>();

            for (Usuario usuario : usuarios) {
                lista.add(new UsuarioRespostaDTO(
                        usuario.getId(),
                        usuario.getNome(),
                        usuario.getEmail()
                ));
            }
            return lista;
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao listar usuário" + e.getMessage(), e);
        }
    }
    public UsuarioRespostaDTO buscarPorId(Long id) {
        try (Connection conn = Conexao.connection()) {
                Usuario usuario = usuarioDAO.buscarPorId(conn, id);

                if (usuario == null) {
                    return null;
                }
                return new UsuarioRespostaDTO(usuario.getId(), usuario.getNome(), usuario.getEmail());
        }catch (SQLException e) {
            throw new RuntimeException("Erro ao buscar por Id" + e.getMessage(), e);
        }
    }

    public UsuarioRespostaDTO atualizar (Long id, UsuarioRequisicaoDTO dto) {
        try (Connection conn = Conexao.connection()) {
            Usuario usuarioExistente = usuarioDAO.buscarPorId(conn, id);

            if (usuarioExistente == null) {
                throw new RuntimeException("Usuário não encontrado!");
            }

            usuarioExistente.setNome(dto.nome());
            usuarioExistente.setEmail(dto.email());

            usuarioDAO.atualizar(conn, usuarioExistente);

            return new UsuarioRespostaDTO(
                    usuarioExistente.getId(),
                    usuarioExistente.getNome(),
                    usuarioExistente.getEmail()
            );
        }catch (SQLException e) {
            throw new RuntimeException("Erro ao atualizar usuário" + e.getMessage(), e);
        }
    }

    public void deletar(Long id) {
        try (Connection con = Conexao.connection()) {
            usuarioDAO.deletar(con, id);
        }catch (SQLException e) {
            throw new RuntimeException("Erro ao deletar" + e.getMessage(), e);
        }
    }
}
