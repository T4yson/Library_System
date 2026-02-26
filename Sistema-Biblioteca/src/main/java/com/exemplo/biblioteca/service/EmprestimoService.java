package com.exemplo.biblioteca.service;

import com.exemplo.biblioteca.dao.EmprestimoDAO;
import com.exemplo.biblioteca.dto.Emprestimo.EmprestimoRespostaDTO;
import com.exemplo.biblioteca.dto.Livro.LivroRespostaDTO;
import com.exemplo.biblioteca.model.Emprestimo;
import com.exemplo.biblioteca.utils.Conexao;
import org.springframework.stereotype.Service;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@Service
public class EmprestimoService {

    EmprestimoDAO emprestimoDAO = new EmprestimoDAO();

    public EmprestimoRespostaDTO criarEmprestimo(EmprestimoRespostaDTO dto) {

        try (Connection conn = Conexao.connection()) {

            Emprestimo emprestimo = new Emprestimo();
            emprestimo.setId(dto.id());
            emprestimo.setLivroId(dto.livroId());
            emprestimo.setUsuarioId(dto.usuarioId());
            emprestimo.setDataEmprestimo(dto.dataEmprestimo());
            emprestimo.setDataDevolucao(dto.dataDevolucao());

            emprestimoDAO.criarEmprestimo(conn, emprestimo);

            return new EmprestimoRespostaDTO(emprestimo.getId(), emprestimo.getLivroId(), emprestimo.getUsuarioId(), emprestimo.getDataEmprestimo(), emprestimo.getDataDevolucao());

        } catch (SQLException e) {
            throw new RuntimeException("Erro ao criar emprestimo:" + e.getMessage(), e);

        }
    }

    public List<EmprestimoRespostaDTO> buscarTodos() {
        try(Connection conn = Conexao.connection()) {
            List<Emprestimo> emprestimos = emprestimoDAO.buscarTodos(conn);
            List<EmprestimoRespostaDTO> lista = new ArrayList<>();

            for (Emprestimo emprestimo : emprestimos) {
                lista.add(new EmprestimoRespostaDTO(
                        emprestimo.getId(),
                        emprestimo.getLivroId(),
                        emprestimo.getUsuarioId(),
                        emprestimo.getDataEmprestimo(),
                        emprestimo.getDataDevolucao()
                ));
            }
            return lista;
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao listar emprestimos" + e.getMessage(), e);
        }
    }

    public EmprestimoRespostaDTO buscarPorId(Long id) {
        try (Connection conn = Conexao.connection()) {
            Emprestimo emprestimo = emprestimoDAO.buscarPorId(conn, id);

            if (emprestimo == null) {
                return null;
            }
            return new EmprestimoRespostaDTO(emprestimo.getId(), emprestimo.getLivroId(), emprestimo.getUsuarioId(), emprestimo.getDataEmprestimo(), emprestimo.getDataDevolucao());

        }catch (SQLException e) {
            throw new RuntimeException("Erro ao buscar por Id: " + e.getMessage(), e);
        }
    }

    public EmprestimoRespostaDTO atualizar(Long id, EmprestimoRespostaDTO dto) {
        try (Connection conn = Conexao.connection()) {
            Emprestimo emprestimoExistente = emprestimoDAO.buscarPorId(conn, id);

            if (emprestimoExistente == null) {
                throw new RuntimeException("Emprestimo não encontrado!");
            }

            emprestimoExistente.setLivroId(dto.livroId());
            emprestimoExistente.setUsuarioId(dto.usuarioId());
            emprestimoExistente.setDataEmprestimo(dto.dataEmprestimo());
            emprestimoExistente.setDataDevolucao(dto.dataDevolucao());

            emprestimoDAO.atualizar(conn, emprestimoExistente);

            return new EmprestimoRespostaDTO(
                    emprestimoExistente.getId(),
                    emprestimoExistente.getLivroId(),
                    emprestimoExistente.getUsuarioId(),
                    emprestimoExistente.getDataEmprestimo(),
                    emprestimoExistente.getDataDevolucao()

            );
        }catch (SQLException e) {
            throw new RuntimeException("Erro ao atualizar livro: " + e.getMessage(), e);
        }
    }

    public void deletar(Long id) {
        try (Connection conn = Conexao.connection()) {
            emprestimoDAO.deletar(conn, id);
        }catch (SQLException e) {
            throw new RuntimeException("Erro ao deletar: " + e.getMessage(), e);
        }
    }
}
