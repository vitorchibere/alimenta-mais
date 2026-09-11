package com.alimentamais.controller;

import com.alimentamais.model.Oferta;
import com.alimentamais.model.StatusOferta;
import com.alimentamais.model.Usuario;
import com.alimentamais.service.OfertaService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.ui.Model;

@Controller
public class OfertaController {

    private final OfertaService ofertaService;

    public OfertaController(OfertaService ofertaService) {
        this.ofertaService = ofertaService;
    }

    @GetMapping("/ofertas/nova")
    public String novaOferta() {
        return "oferta-form";
    }

    @GetMapping("/ofertas")
    public String listarOfertas(Model model) {
        model.addAttribute("ofertas", ofertaService.listarDisponiveis());
        return "ofertas";
    }

    @PostMapping("/ofertas")
    public String criarOferta(
            @RequestParam String alimento,
            @RequestParam double quantidade,
            @RequestParam String unidade,
            @RequestParam String descricao,
            @RequestParam String cidade) {

        Usuario doador = new Usuario();
        doador.setNome("Doador de teste");

        Oferta oferta = new Oferta(
                alimento,
                quantidade,
                unidade,
                descricao,
                cidade,
                doador
        );

        ofertaService.criar(oferta);

        return "redirect:/";
    }

    @PostMapping("/ofertas/status")
    public String atualizarStatus(
            @RequestParam String ofertaId,
            @RequestParam StatusOferta status) {

        ofertaService.atualizarStatus(ofertaId, status);

        return "redirect:/ofertas";
    }

    @GetMapping("/ofertas/todas")
    public String listarTodas(Model model) {

        model.addAttribute(
                "ofertas",
                ofertaService.listarTodas()
        );

        return "ofertas-todas";
    }
}