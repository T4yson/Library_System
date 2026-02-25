package com.exemplo.biblioteca.dao;

import com.exemplo.biblioteca.model.Usuario;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UsuarioDAO {

    public void criarUsuario(Connection conn, Usuario usuario) throws SQLException {
        String query = "INSERT INTO (nome, email) VALUES (?, ?)";

        try (PreparedStatement stmt = conn.prepareStatement(query, Statement.RETURN_GENERATED_KEYS)) {
            stmt.setString(1, usuario.getNome());
            stmt.setString(2, usuario.getEmail());
            stmt.executeUpdate();

            ResultSet rs = stmt.getGeneratedKeys();
            if (rs.next()) {
                usuario.setId(rs.getLong(1));
            }
        }
    }

    public Usuario buscarPorId(Connection conn, Long id) throws SQLException {
        String query = "SELECT id, nome, email FROM usuario WHERE id = ?";

        try (PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setLong(1, id);

            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                Usuario usuario = new Usuario();
                usuario.setId(rs.getLong("id"));
                usuario.setNome(rs.getString("nome"));
                usuario.setEmail(rs.getString("email"));
            }
        }
        return null;
    }

    public List<Usuario> buscarTodos(Connection conn) throws SQLException {
        ArrayList<Usuario> usuarios = new ArrayList<>();
        String query = "SELECT id, nome, email FROM usuario";

        try (PreparedStatement stmt = conn.prepareStatement(query);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                Usuario usuario = new Usuario();
                usuario.setId(rs.getLong("id"));
                usuario.setNome(rs.getString("nome"));
                usuario.setEmail(rs.getString("email"));

                usuarios.add(usuario);
            }
        }
        return usuarios;
    }

    public void atualizar(Connection conn, Usuario usuario) throws SQLException {
        String query = "UPDATE livro SET nome = ?, email = ? WHERE id = ? ";

        try (PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setString(1, usuario.getNome());
            stmt.setString(2, usuario.getEmail());
            stmt.setLong(3, usuario.getId());

            stmt.executeUpdate();
        }
    }

    public void deletar(Connection conn, Long id) throws SQLException {
        String query = "DELETE FROM usuario WHERE id = ?";

        try (PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setLong(1, id);
            stmt.executeUpdate();
        }
    }
}