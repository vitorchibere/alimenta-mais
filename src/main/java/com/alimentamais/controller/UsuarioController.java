package com.alimentamais.controller;

import com.alimentamais.model.TipoUsuario;
import com.alimentamais.model.Usuario;
import com.alimentamais.service.UsuarioService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class UsuarioController {

    private final UsuarioService usuarioService;

    public UsuarioController(UsuarioService usuarioService) {
        this.usuarioService = usuarioService;
    }

    @GetMapping("/usuarios/novo")
    public String novoUsuario() {
        return "usuario-form";
    }

    @PostMapping("/usuarios")
    public String criarUsuario(
            @RequestParam String nome,
            @RequestParam String cidade,
            @RequestParam TipoUsuario tipoUsuario) {

        Usuario usuario = new Usuario(nome, cidade, tipoUsuario);

        usuarioService.criar(usuario);

        return "redirect:/";
    }
}
