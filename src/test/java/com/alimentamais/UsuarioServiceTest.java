package com.alimentamais;

import com.alimentamais.model.TipoUsuario;
import com.alimentamais.model.Usuario;
import com.alimentamais.repository.UsuarioRepository;
import com.alimentamais.service.UsuarioService;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

class UsuarioServiceTest {

    @Test
    void deveCriarUsuario() {

        UsuarioRepository repository = mock(UsuarioRepository.class);
        UsuarioService service = new UsuarioService(repository);

        Usuario usuario = new Usuario(
                "João",
                "Maringá",
                TipoUsuario.BENEFICIARIO
        );

        when(repository.save(usuario)).thenReturn(usuario);

        Usuario resultado = service.criar(usuario);

        assertEquals("João", resultado.getNome());
        assertEquals("Maringá", resultado.getCidade());
        assertEquals(
                TipoUsuario.BENEFICIARIO,
                resultado.getTipoUsuario()
        );

        verify(repository).save(usuario);
    }
}