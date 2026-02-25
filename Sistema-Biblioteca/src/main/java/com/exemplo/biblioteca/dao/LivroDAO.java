package com.exemplo.biblioteca.dao;

import com.exemplo.biblioteca.Conexao;
import com.exemplo.biblioteca.model.Livro;

import java.sql.Connection;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class LivroDAO {

    public void criarLivro(Connection conn, Livro livro) throws SQLException {
        String query = "INSERT INTO livro (titulo, autor, ano_publicacao) VALUES (?, ?, ?)";

        try (PreparedStatement stmt = conn.prepareStatement(query, Statement.RETURN_GENERATED_KEYS)) {
            stmt.setString(1, livro.getTitulo());
            stmt.setString(2, livro.getAutor());
            stmt.setInt(3, livro.getAnoPublicacao());
            stmt.executeUpdate();

            ResultSet rs = stmt.getGeneratedKeys();
            if (rs.next()) {
                livro.setId(rs.getLong(1));
            }
        }
    }

    public Livro buscarPorId(Connection conn, Long id) throws SQLException {
        String query = "SELECT id, titulo, autor, ano_publicacao FROM livro WHERE id = ?";

        try (PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setLong(1, id);

            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                Livro livro = new Livro();
                livro.setId(rs.getLong("id"));
                livro.setTitulo(rs.getString("titulo"));
                livro.setAutor(rs.getString("autor"));
                livro.setAnoPublicacao(rs.getInt("ano_publicacao"));
                return livro;

            }
        }

        return null;
    }

    public List<Livro> buscarTodos(Connection conn) throws SQLException {
        ArrayList<Livro> livros = new ArrayList<>();
        String query = "SELECT id, titulo, autor, ano_publicacao FROM livro";

        try (PreparedStatement stmt = conn.prepareStatement(query);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                Livro livro = new Livro();
                livro.setId(rs.getLong("id"));
                livro.setTitulo(rs.getString("titulo"));
                livro.setAutor(rs.getString("autor"));
                livro.setAnoPublicacao(rs.getInt("ano_publicacao"));

                livros.add(livro);
            }

        }
        return livros;

    }

    public void atualizar(Connection conn, Livro livro) throws SQLException {
        String query = "UPDATE livro SET titulo = ?, autor = ?, ano_publicacao = ? WHERE id = ?";

        try (PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setString(1, livro.getTitulo());
            stmt.setString(2, livro.getAutor());
            stmt.setInt(3, livro.getAnoPublicacao());
            stmt.setLong(4, livro.getId());

            stmt.executeUpdate();
        }
    }

    public void deletar(Connection conn, Long id) throws SQLException {
        String query = "DELETE FROM livro WHERE id = ?";

        try (PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setLong(1, id);
            stmt.executeUpdate();
        }
    }
}