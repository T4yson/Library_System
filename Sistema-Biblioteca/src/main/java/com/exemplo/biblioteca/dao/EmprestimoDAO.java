package com.exemplo.biblioteca.dao;

import com.exemplo.biblioteca.model.Emprestimo;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class EmprestimoDAO {

    public Emprestimo criarEmprestimo(Connection conn, Emprestimo emprestimo) throws SQLException {
        String query = "INSERT INTO (livro_id, usuario_id, data_emprestimo, data_devolucao) VALUES (?, ?, ?, ?)";

        try (PreparedStatement stmt = conn.prepareStatement(query, Statement.RETURN_GENERATED_KEYS)) {

            stmt.setLong(1, emprestimo.getLivroId());
            stmt.setLong(2, emprestimo.getUsuarioId());
            stmt.setDate(3, Date.valueOf(emprestimo.getDataEmprestimo()));
            stmt.setDate(4, Date.valueOf(emprestimo.getDataDevolucao()));
            stmt.executeUpdate();

            ResultSet rs = stmt.getGeneratedKeys();
            if (rs.next()) {
                emprestimo.setId(rs.getLong(1));
            }
        }
        return emprestimo;
    }

    public Emprestimo buscarPorId(Connection conn, Long id) throws SQLException {
        String query = "SELECT id, livro_id, usuario_id, data_emprestimo, data_devolucao FROM emprestimo WHERE id = ?";

        try (PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setLong(1, id);

            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                Emprestimo emprestimo = new Emprestimo();
                emprestimo.setId(rs.getLong("id"));
                emprestimo.setLivroId(rs.getLong("livro_id"));
                emprestimo.setUsuarioId(rs.getLong("usuario_id"));
                emprestimo.setDataEmprestimo(rs.getDate("data_emprestimo").toLocalDate());
                emprestimo.setDataDevolucao(rs.getDate("data_devolucao").toLocalDate());
                return emprestimo;

            }
        }
        return null;
    }

    public List<Emprestimo> buscarTodos(Connection conn) throws SQLException {
        ArrayList<Emprestimo> emprestimos = new ArrayList<>();
        String query = "SELECT id, livro_id, usuario_id, data_emprestimo, data_devolucao FROM emprestimo";

        try (PreparedStatement stmt = conn.prepareStatement(query)) {

            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                Emprestimo emprestimo = new Emprestimo();
                emprestimo.setId(rs.getLong("id"));
                emprestimo.setLivroId(rs.getLong("livro_id"));
                emprestimo.setUsuarioId(rs.getLong("usuario_id"));
                emprestimo.setDataEmprestimo(rs.getDate("data_emprestimo").toLocalDate());
                emprestimo.setDataDevolucao(rs.getDate("data_devolucao").toLocalDate());

                emprestimos.add(emprestimo);
            }

        }
        return emprestimos;

    }

    public void atualizar(Connection conn, Emprestimo emprestimo) throws SQLException {
        String query = "UPDATE emprestimo SET livro_id = ?, usuario_id = ?, data_emprestimo = ?, data_devolucao = ? WHERE id = ?";

        try (PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setLong(1, emprestimo.getLivroId());
            stmt.setLong(2, emprestimo.getUsuarioId());
            stmt.setDate(3, Date.valueOf(emprestimo.getDataEmprestimo()));
            stmt.setDate(4, Date.valueOf(emprestimo.getDataDevolucao()));

            stmt.executeUpdate();
        }
    }

    public void deletar(Connection conn, Long id) throws SQLException {
        String query = "DELETE FROM livro WHERE id = ? ";

        try (PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setLong(1, id);
            stmt.executeUpdate();
        }
    }
}