package br.com.appinfra.appinfra.domain

// Classe de Reclamação e Construtor do Objeto
class Reclamacao(
        val imagem: Int,
        var endereco: String,
        var bairro: String,
        val cidade: String,
        var status: Boolean
    )