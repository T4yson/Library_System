package com.exemplo.biblioteca.service;

import com.exemplo.biblioteca.Conexao;
import com.exemplo.biblioteca.dao.UsuarioDAO;
import com.exemplo.biblioteca.model.Emprestimo;
import com.exemplo.biblioteca.model.Usuario;

import javax.sql.rowset.CachedRowSet;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

public class UsuarioService {

    UsuarioDAO usuarioDAO = new UsuarioDAO();

    public Usuario criarUsuario(Usuario usuario) {
        if (usuario.getNome() == null || usuario.getNome().trim().isEmpty()) {
            throw new RuntimeException("O nome de Usuário é obrigatório!");
        }

        if (usuario.getEmail() == null || usuario.getEmail().trim().isEmpty()) {
            throw new RuntimeException("O email de usuário é obrigatório!");
        }

        try (Connection conn = Conexao.connection()) {
            usuarioDAO.criarUsuario(conn, usuario);
            System.out.println("Usuário cadastrado com sucesso!");
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao cadastrar usuário", e);
        }
        return usuario;
    }

    public Usuario buscarPorId(Long id) {
        try (Connection conn = Conexao.connection()){
            return usuarioDAO.buscarPorId(conn, id);
        }catch (SQLException e) {
            throw new RuntimeException("Erro ao buscar usuário!", e);
        }
    }

    public List<Usuario> buscarTodos() {
        try (Connection conn = Conexao.connection()){
            return usuarioDAO.buscarTodos(conn);
        }catch (SQLException e) {
            throw new RuntimeException("Erro ao listar", e);
        }
    }
    public void atualizar (Usuario usuario) {
        if (usuario.getId() == null) {
            throw new RuntimeException("O ID do usuário é obrigatório!");
        }
        try (Connection conn = Conexao.connection()) {
            usuarioDAO.atualizar(conn, usuario);
            System.out.println("Usuário atualizado com sucesso!");
        }catch (SQLException e) {
            throw new RuntimeException("Erro ao atualizar usuário", e);
        }
    }
}
