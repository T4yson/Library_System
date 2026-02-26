package com.exemplo.biblioteca.dto_livro;

public record LivroRequisicaoDTO (
            Long id,
            String titulo,
            String autor,
            int anoPublicacao
){}
