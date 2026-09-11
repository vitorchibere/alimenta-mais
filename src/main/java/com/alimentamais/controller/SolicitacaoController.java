package com.alimentamais.controller;

import com.alimentamais.model.Oferta;
import com.alimentamais.model.StatusSolicitacao;
import com.alimentamais.model.Usuario;
import com.alimentamais.service.OfertaService;
import com.alimentamais.service.SolicitacaoService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;


@Controller
public class SolicitacaoController {

    private final SolicitacaoService solicitacaoService;
    private final OfertaService ofertaService;

    public SolicitacaoController(
            SolicitacaoService solicitacaoService,
            OfertaService ofertaService) {
        this.solicitacaoService = solicitacaoService;
        this.ofertaService = ofertaService;
    }

    @PostMapping("/solicitacoes")
    public String criarSolicitacao(@RequestParam String ofertaId) {

        System.out.println("1 - OFERTA RECEBIDA: " + ofertaId);

        Oferta oferta = ofertaService.buscarPorId(ofertaId);

        System.out.println("2 - OFERTA ENCONTRADA: " + oferta.getAlimento());

        Usuario beneficiario = new Usuario();
        beneficiario.setNome("Beneficiário de teste");

        solicitacaoService.criar(beneficiario, oferta);

        System.out.println("3 - SOLICITAÇÃO SALVA");

        return "redirect:/ofertas";
    }

    @PostMapping("/solicitacoes/status")
    public String atualizarStatus(
            @RequestParam String solicitacaoId,
            @RequestParam StatusSolicitacao status) {

        System.out.println("SOLICITAÇÃO RECEBIDA: " + solicitacaoId);
        System.out.println("STATUS RECEBIDO: " + status);

        solicitacaoService.atualizarStatus(solicitacaoId, status);

        return "redirect:/solicitacoes";
    }

    @GetMapping("/solicitacoes")
    public String listarSolicitacoes(Model model) {

        model.addAttribute(
                "solicitacoes",
                solicitacaoService.listarTodas()
        );

        return "solicitacoes";
    }
}