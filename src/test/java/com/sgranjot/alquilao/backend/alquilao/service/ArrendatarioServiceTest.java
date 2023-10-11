package com.sgranjot.alquilao.backend.alquilao.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.reset;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Collections;
import java.util.Optional;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ActiveProfiles;

import com.sgranjot.alquilao.backend.alquilao.DataDummy;
import com.sgranjot.alquilao.backend.alquilao.model.Arrendatario;
import com.sgranjot.alquilao.backend.alquilao.model.dao.IArrendatarioDao;
import com.sgranjot.alquilao.backend.alquilao.response.ArrendatarioResponseRest;

@SpringBootTest
@ActiveProfiles("test")
public class ArrendatarioServiceTest {

    @MockBean
    private IArrendatarioDao arrendatarioDaoMock;

    @Autowired
    private IArrendatarioService arrendatarioService;

    private static final Long VALID_ID = 1L;
    private static final Long INVALID_ID = 2L;


   /*  @BeforeEach
    void setMocks(){
        when(this.arrendatarioDaoMock.findById(VALID_ID))
            .thenReturn(Optional.of(DataDummy.ARRENDATARIO));

        when(this.arrendatarioDaoMock.findById(INVALID_ID))
            .thenReturn(Optional.empty());

        when(this.arrendatarioDaoMock.save(any(Arrendatario.class)))
            .thenReturn(DataDummy.ARRENDATARIO);
    }*/

    @AfterEach
    void resetMocks(){
        reset(this.arrendatarioDaoMock);
    }


    @Test
    @DisplayName ("buscarArrendatarios should works")
    void buscarArrendatarios(){
        when(this.arrendatarioDaoMock.findAll())
            .thenReturn(DataDummy.ARRENDATARIOS);
        ResponseEntity<ArrendatarioResponseRest> responseEntity = this.arrendatarioService.buscarArrendatarios();
        verify(arrendatarioDaoMock, times(1)).findAll();
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        ArrendatarioResponseRest responseBody = responseEntity.getBody();
        assertEquals(DataDummy.ARRENDATARIOS, responseBody.getArrendatarioResponse().getArrendatarios());
        assertEquals(2, responseBody.getArrendatarioResponse().getArrendatarios().size());

        when(this.arrendatarioDaoMock.findAll())
            .thenReturn(Collections.EMPTY_LIST);
        ResponseEntity<ArrendatarioResponseRest> responseEntityEmpty = this.arrendatarioService.buscarArrendatarios();
        verify(arrendatarioDaoMock, times(2)).findAll();
        assertEquals(HttpStatus.OK, responseEntityEmpty.getStatusCode());
        ArrendatarioResponseRest responseEmptyBody = responseEntityEmpty.getBody();
        assertEquals(0, responseEmptyBody.getArrendatarioResponse().getArrendatarios().size());

        when(this.arrendatarioDaoMock.findAll())
            .thenThrow(new RuntimeException("Simulated exception"));
        ResponseEntity<ArrendatarioResponseRest> responseEntityException = this.arrendatarioService.buscarArrendatarios();
        verify(arrendatarioDaoMock, times(3)).findAll();
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, responseEntityException.getStatusCode());
        ArrendatarioResponseRest responseException = responseEntityException.getBody();
        assertNotNull(responseException); 

    }


    @Test
    @DisplayName ("buscarPorId should works")
    void buscarPorId(){
        when(this.arrendatarioDaoMock.findById(VALID_ID))
            .thenReturn(Optional.of(DataDummy.ARRENDATARIO));
        ResponseEntity<ArrendatarioResponseRest> responseEntity = this.arrendatarioService.buscarPorId(VALID_ID);
        verify(arrendatarioDaoMock, times(1)).findById(VALID_ID);
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        ArrendatarioResponseRest response = responseEntity.getBody();
        assertNotNull(response);
        assertEquals(DataDummy.ARRENDATARIO, response.getArrendatarioResponse().getArrendatarios().get(0));

        when(this.arrendatarioDaoMock.findById(INVALID_ID))
            .thenReturn(Optional.empty());
        ResponseEntity<ArrendatarioResponseRest> responseEntityNoValid = this.arrendatarioService.buscarPorId(INVALID_ID);
        verify(arrendatarioDaoMock, times(1)).findById(INVALID_ID);
        assertEquals(HttpStatus.NOT_FOUND, responseEntityNoValid.getStatusCode());
        ArrendatarioResponseRest responseNoValid = responseEntityNoValid.getBody();
        assertNotNull(responseNoValid);   
        
        when(arrendatarioDaoMock.findById(anyLong()))
            .thenThrow(new RuntimeException("Simulated exception"));
        ResponseEntity<ArrendatarioResponseRest> responseEntityException = this.arrendatarioService.buscarPorId(3L);
        verify(arrendatarioDaoMock, times(1)).findById(3L);
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, responseEntityException.getStatusCode());
        ArrendatarioResponseRest responseExceptionBody = responseEntityException.getBody();
        assertNotNull(responseExceptionBody);

    }


    @Test
    @DisplayName ("crear should works")
    void crear(){
        when(this.arrendatarioDaoMock.save(any(Arrendatario.class)))
            .thenReturn(DataDummy.ARRENDATARIO);
        ResponseEntity<ArrendatarioResponseRest> responseEntity = this.arrendatarioService.crear(DataDummy.ARRENDATARIO);
        verify(arrendatarioDaoMock, times(1)).save(any(Arrendatario.class));
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        ArrendatarioResponseRest response = responseEntity.getBody();
        assertEquals(DataDummy.ARRENDATARIO, response.getArrendatarioResponse().getArrendatarios().get(0));

        when(this.arrendatarioDaoMock.save(any(Arrendatario.class)))
            .thenReturn(null);
        ResponseEntity<ArrendatarioResponseRest> responseEntityNull = this.arrendatarioService.crear(DataDummy.ARRENDATARIO);
        verify(arrendatarioDaoMock, times(2)).save(any(Arrendatario.class));
        assertEquals(HttpStatus.BAD_REQUEST, responseEntityNull.getStatusCode());
        ArrendatarioResponseRest responseNullBody = responseEntityNull.getBody();
        assertNotNull(responseNullBody);

        when(this.arrendatarioDaoMock.save(any(Arrendatario.class)))
            .thenThrow(new RuntimeException("Simulated exception"));
        ResponseEntity<ArrendatarioResponseRest> responseEntityException = this.arrendatarioService.crear(DataDummy.ARRENDATARIO);
        verify(arrendatarioDaoMock, times(3)).save(any(Arrendatario.class));
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, responseEntityException.getStatusCode());
        ArrendatarioResponseRest responseExceptionBody = responseEntityException.getBody();
        assertNotNull(responseExceptionBody);

    }


    @Test
    @DisplayName ("actualizar should works")
    void actualizar (){
        when(this.arrendatarioDaoMock.findById(VALID_ID))
            .thenReturn(Optional.of(DataDummy.ARRENDATARIO));
        when(this.arrendatarioDaoMock.save(any(Arrendatario.class)))
            .thenReturn(DataDummy.ARRENDATARIO);
        ResponseEntity<ArrendatarioResponseRest> responseEntity = this.arrendatarioService.actualizar(DataDummy.ARRENDATARIO, VALID_ID);
        verify(arrendatarioDaoMock, times(1)).findById(VALID_ID);
        verify(arrendatarioDaoMock, times(1)).save(any(Arrendatario.class));
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        ArrendatarioResponseRest response = responseEntity.getBody();
        assertEquals(DataDummy.ARRENDATARIO, response.getArrendatarioResponse().getArrendatarios().get(0));

        when(this.arrendatarioDaoMock.findById(VALID_ID))
            .thenReturn(Optional.of(DataDummy.ARRENDATARIO));
        when(this.arrendatarioDaoMock.save(any(Arrendatario.class)))
            .thenReturn(null);
        ResponseEntity<ArrendatarioResponseRest> responseEntityNull = this.arrendatarioService.actualizar(DataDummy.ARRENDATARIO, VALID_ID);
        verify(arrendatarioDaoMock, times(2)).findById(VALID_ID);
        verify(arrendatarioDaoMock, times(2)).save(any(Arrendatario.class));
        assertEquals(HttpStatus.BAD_REQUEST, responseEntityNull.getStatusCode());
        ArrendatarioResponseRest responseNullBody = responseEntityNull.getBody();
        assertNotNull(responseNullBody);

        when(this.arrendatarioDaoMock.findById(INVALID_ID))
            .thenReturn(Optional.empty());
        ResponseEntity<ArrendatarioResponseRest> responseEntityInvalid = this.arrendatarioService.actualizar(DataDummy.ARRENDATARIO, INVALID_ID);
        verify(arrendatarioDaoMock, times(1)).findById(INVALID_ID);
        assertEquals(HttpStatus.NOT_FOUND, responseEntityInvalid.getStatusCode());
        ArrendatarioResponseRest responseInvalidBody = responseEntityInvalid.getBody();
        assertNotNull(responseInvalidBody);

        when(this.arrendatarioDaoMock.findById(anyLong()))
            .thenThrow(new RuntimeException("Simulated exception"));
        ResponseEntity<ArrendatarioResponseRest> responseEntityException = this.arrendatarioService.actualizar(DataDummy.ARRENDATARIO, VALID_ID);
        verify(arrendatarioDaoMock, times(3)).findById(VALID_ID);
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, responseEntityException.getStatusCode());
        ArrendatarioResponseRest responseExceptionBody = responseEntityException.getBody();
        assertNotNull(responseExceptionBody);

    }


    @Test
    @DisplayName ("eliminar should works")
    void eliminar(){
        doNothing().when(arrendatarioDaoMock).deleteById(VALID_ID);
        ResponseEntity<ArrendatarioResponseRest> responseEntity = this.arrendatarioService.eliminar(VALID_ID);
        verify(arrendatarioDaoMock, times(1)).deleteById(VALID_ID);
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        ArrendatarioResponseRest response = responseEntity.getBody();
        assertNotNull(response);

        doThrow(new RuntimeException("Simulated exception")).when(arrendatarioDaoMock).deleteById(INVALID_ID);
        ResponseEntity<ArrendatarioResponseRest> responseException = this.arrendatarioService.eliminar(INVALID_ID);
        verify(arrendatarioDaoMock, times(1)).deleteById(INVALID_ID);
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, responseException.getStatusCode());
        assertNotNull(responseException.getBody());
        
    }

}
