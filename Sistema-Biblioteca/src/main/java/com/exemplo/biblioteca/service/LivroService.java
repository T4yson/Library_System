package com.exemplo.biblioteca.service;

import com.exemplo.biblioteca.Conexao;
import com.exemplo.biblioteca.dao.LivroDAO;
import com.exemplo.biblioteca.model.Livro;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

public class LivroService {

    LivroDAO livroDAO = new LivroDAO();

    public Livro criarLivro(Livro livro) {

        if (livro.getTitulo() == null || livro.getTitulo().trim().isEmpty()) {
            throw  new RuntimeException("O título do livro é obrigatório");

            }
        if (livro.getAutor() == null || livro.getAutor().trim().isEmpty()) {
            throw new RuntimeException("O autor é obrigatório");
        }
        try (Connection conn = Conexao.connection()) {
            livroDAO.criarLivro(conn, livro);
            System.out.println("Livro cadastrado com sucesso!");
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao salvar livro no banco de dados", e);
        }
        return livro;
    }

    public Livro buscarPorId(Long id) {
           try (Connection conn = Conexao.connection()) {
               return livroDAO.buscarPorId(conn, id);
           } catch (SQLException e) {
               throw new RuntimeException("Erro ao buscar livro", e);
           }
    }
    public List<Livro> buscarTodos() {
        try (Connection conn = Conexao.connection()) {
            return livroDAO.buscarTodos(conn);
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao listar", e);
        }
    }

    public void atualizar (Livro livro) throws SQLException {
        if (livro.getId() == null) {
            throw new RuntimeException("O ID do livro é obrigatório");
        }
        try (Connection conn = Conexao.connection()) {
            livroDAO.atualizar(conn, livro);
            System.out.println("Livro Atualizado com sucesso!");
        }catch (SQLException e) {
            throw new RuntimeException("Erro ao atualizar livro", e);
        }
    }

    public void deletar (Long id) throws SQLException {
        try (Connection conn = Conexao.connection()) {
            livroDAO.deletar(conn, id);
            System.out.println("Livro deletado com sucesso!");
        }catch (SQLException e) {
            throw new RuntimeException ("Erro ao deletar livro", e);
        }
    }
}
