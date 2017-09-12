package br.com.appinfra.appinfra.data
import br.com.appinfra.appinfra.R
import br.com.appinfra.appinfra.domain.Reclamacao


class Reclamacoes {
    companion object{


        // Criação da Lista Estática

        fun generateQuestionList() = listOf<Reclamacao>(
            Reclamacao(R.drawable.buraco1, "Rua General Osório", "Centro", "João Pessoa", false),
            Reclamacao(R.drawable.buraco2, "Rua Araújo Amaral", "Cristo", "João Pessoa", false),
            Reclamacao(R.drawable.buraco3, "Rua Francisco Deodoro", "Centro", "Sapé", false),
            Reclamacao(R.drawable.buraco4, "Rua Leonardo Xavier", "Bancários", "João Pessoa", true),
            Reclamacao(R.drawable.buraco5, "Av. Minas Gerais", "Bairro dos Estados", "João Pessoa", true),
            Reclamacao(R.drawable.buraco6, "Rua da Independência", "Liberdade", "Santa Rita", false),
            Reclamacao(R.drawable.buraco7, "Av. Juarez Távora", "Centro", "Santa Rita", true),
            Reclamacao(R.drawable.buraco8, "Rua Camargo Fonseca", "Centro", "Areia", false)
        )
    }
}