package com.alimentamais;

import com.alimentamais.controller.UsuarioController;
import com.alimentamais.model.TipoUsuario;
import com.alimentamais.model.Usuario;
import com.alimentamais.service.UsuarioService;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

class UsuarioControllerTest {

    @Test
    void deveRetornarFormularioDeUsuario() {

        UsuarioService service = mock(UsuarioService.class);
        UsuarioController controller = new UsuarioController(service);

        String resultado = controller.novoUsuario();

        assertEquals("usuario-form", resultado);
    }

    @Test
    void deveCriarUsuarioERedirecionar() {

        UsuarioService service = mock(UsuarioService.class);
        UsuarioController controller = new UsuarioController(service);

        String resultado = controller.criarUsuario(
                "João",
                "Maringá",
                TipoUsuario.BENEFICIARIO
        );

        assertEquals("redirect:/", resultado);

        verify(service).criar(any(Usuario.class));
    }
}
