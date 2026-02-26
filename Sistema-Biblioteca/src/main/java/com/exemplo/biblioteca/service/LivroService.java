package com.exemplo.biblioteca.service;

import com.exemplo.biblioteca.dto.Livro.LivroRequisicaoDTO;
import com.exemplo.biblioteca.dto.Livro.LivroRespostaDTO;
import com.exemplo.biblioteca.utils.Conexao;
import com.exemplo.biblioteca.dao.LivroDAO;
import com.exemplo.biblioteca.model.Livro;
import org.springframework.stereotype.Service;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@Service
public class LivroService {

    LivroDAO livroDAO = new LivroDAO();

    public LivroRespostaDTO criarLivro(LivroRespostaDTO dto) {

        try (Connection conn = Conexao.connection()) {

            Livro livro = new Livro();
            livro.setTitulo(dto.titulo());
            livro.setAutor(dto.autor());
            livro.setAnoPublicacao(dto.anoPublicacao());

            livroDAO.criarLivro(conn, livro);

            return new LivroRespostaDTO(livro.getId(), livro.getTitulo(), livro.getAutor(), livro.getAnoPublicacao());

        } catch (SQLException e) {
            throw new RuntimeException("Erro ao cadastrar livro: " + e.getMessage(), e);
        }
    }

    public List<LivroRespostaDTO> buscarTodos() {
        try (Connection conn = Conexao.connection()) {
            List<Livro> livros = livroDAO.buscarTodos(conn);
            List<LivroRespostaDTO> lista = new ArrayList<>();

            for (Livro livro : livros) {
                lista.add(new LivroRespostaDTO(
                        livro.getId(),
                        livro.getTitulo(),
                        livro.getAutor(),
                        livro.getAnoPublicacao()
                ));
            }
            return lista;
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao listar livros" + e.getMessage(), e);
        }
    }

    public LivroRespostaDTO buscarPorId(Long id) {
        try (Connection conn = Conexao.connection()) {
            Livro livro = livroDAO.buscarPorId(conn, id);

            if (livro == null) {
                return null;
            }
            return new LivroRespostaDTO(livro.getId(), livro.getTitulo(), livro.getAutor(), livro.getAnoPublicacao());
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao buscar por Id" + e.getMessage(), e);
        }
    }

    public LivroRespostaDTO atualizar(Long id, LivroRequisicaoDTO dto) {
        try (Connection conn = Conexao.connection()) {
            Livro livroExistente = livroDAO.buscarPorId(conn, id);

            if (livroExistente == null) {
                throw new RuntimeException("Livro não encontrado!");
            }

            livroExistente.setTitulo(dto.titulo());
            livroExistente.setAutor(dto.autor());
            livroExistente.setAnoPublicacao(dto.anoPublicacao());

            livroDAO.atualizar(conn, livroExistente);

            return new LivroRespostaDTO(
                    livroExistente.getId(),
                    livroExistente.getTitulo(),
                    livroExistente.getAutor(),
                    livroExistente.getAnoPublicacao()
            );
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao atualizar livro:" + e.getMessage(), e);
        }
    }

    public void deletar(Long id) {
        try (Connection conn = Conexao.connection()) {
            livroDAO.deletar(conn, id);
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao deletar: " + e.getMessage(), e);
        }
    }
}