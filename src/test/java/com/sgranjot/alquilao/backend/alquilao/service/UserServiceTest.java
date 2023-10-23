package com.sgranjot.alquilao.backend.alquilao.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.reset;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.test.context.ActiveProfiles;

import com.sgranjot.alquilao.backend.alquilao.model.dao.IUsuarioDao;
import com.sgranjot.alquilao.backend.alquilao.service.impl.UsuarioService;
import com.sgranjot.alquilao.backend.alquilao.utils.DataDummy;

@SpringBootTest
@ActiveProfiles ("test")
public class UserServiceTest {

    @MockBean
    private IUsuarioDao usuarioDao;

    @Autowired
    private UsuarioService usuarioService;

    @AfterEach
    void resetMocks(){
        reset(this.usuarioDao);
    }



    @Test
    @DisplayName ("loadUserByUsername should work")
    void loadUserByUsername (){

        when(usuarioDao.findByNombre(anyString()))
            .thenReturn(DataDummy.USUARIO);
        UserDetails userDetails = this.usuarioService.loadUserByUsername("UsuarioTest");
        assertNotNull(userDetails);
        assertEquals("UsuarioTest", userDetails.getUsername());
        assertEquals("passwordTest", userDetails.getPassword());
        assertTrue(userDetails.isEnabled());
        assertTrue(userDetails.isAccountNonExpired());
        assertTrue(userDetails.isAccountNonLocked());
        assertTrue(userDetails.isCredentialsNonExpired());
        List<GrantedAuthority> authorities = new ArrayList<>(userDetails.getAuthorities());
        assertNotNull(authorities);
        assertEquals(1, authorities.size());
        assertEquals("Usuario", authorities.get(0).getAuthority());
        verify(usuarioDao, times(1)).findByNombre("UsuarioTest");

        when(usuarioDao.findByNombre(anyString()))
            .thenThrow(new UsernameNotFoundException("Simulated Exception"));
        assertThrows(UsernameNotFoundException.class, () -> this.usuarioService.loadUserByUsername("userName"));
        verify(usuarioDao, times(1)).findByNombre("userName");
        
    }
    
}
