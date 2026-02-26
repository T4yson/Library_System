package com.exemplo.biblioteca.dto.Emprestimo;

import java.time.LocalDate;

public record EmprestimoRequisicaoDTO (
        Long livroId,
        Long usuarioId,
        LocalDate dataEmprestimo,
        LocalDate dataDevolucao
){}
