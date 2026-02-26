package com.exemplo.biblioteca.dto_emprestimo;

import java.time.LocalDate;

public record EmprestimoRespostaDTO (
        Long id,
        Long livroId,
        Long usuarioId,
        LocalDate dataEmprestimo,
        LocalDate dataDevolucao
){}
