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

import com.sgranjot.alquilao.backend.alquilao.model.Direccion;
import com.sgranjot.alquilao.backend.alquilao.model.dao.IDireccionDao;
import com.sgranjot.alquilao.backend.alquilao.response.DireccionResponseRest;
import com.sgranjot.alquilao.backend.alquilao.utils.DataDummy;

@SpringBootTest
@ActiveProfiles("test")
public class DireccionServiceTest {

    @MockBean
    private IDireccionDao direccionDaoMock;

    @Autowired
    private IDireccionService direccionService;
    
    private static final Long VALID_ID = 1L;
    private static final Long INVALID_ID = 2L;


    @AfterEach
    void resetMocks (){
        reset(this.direccionDaoMock);
    }


    @Test
    @DisplayName ("buscarDirecciones should work")
    void buscarDirecciones(){
        when(this.direccionDaoMock.findAll())
            .thenReturn(DataDummy.DIRECCIONES);
        ResponseEntity<DireccionResponseRest> responseEntity = this.direccionService.buscarDirecciones();
        verify(direccionDaoMock, times(1)).findAll();
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        DireccionResponseRest responseBody = responseEntity.getBody();
        assertNotNull(responseBody);
        assertEquals(DataDummy.DIRECCIONES, responseBody.getDireccionResponse().getDirecciones());
        assertEquals(2, responseBody.getDireccionResponse().getDirecciones().size());

        when(this.direccionDaoMock.findAll())
            .thenReturn(Collections.emptyList());
        ResponseEntity<DireccionResponseRest> responseEmpty = this.direccionService.buscarDirecciones();
        verify(direccionDaoMock, times(2)).findAll();
        assertEquals(HttpStatus.OK, responseEmpty.getStatusCode());
        DireccionResponseRest responseEmptyBody = responseEmpty.getBody();
        assertNotNull(responseEmptyBody);
        assertEquals(0, responseEmptyBody.getDireccionResponse().getDirecciones().size());
        assertNotNull(responseEmpty.getBody());

        when(this.direccionDaoMock.findAll())
            .thenThrow(new RuntimeException("Simulated exception"));
        ResponseEntity<DireccionResponseRest> responseException = this.direccionService.buscarDirecciones();
        verify(direccionDaoMock, times(3)).findAll();
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, responseException.getStatusCode());
        assertNotNull(responseException.getBody());

    }


    @Test
    @DisplayName("buscarPorId should work")
    void buscarPorId(){
        when(direccionDaoMock.findById(VALID_ID))
            .thenReturn(Optional.of(DataDummy.DIRECCION));
        ResponseEntity<DireccionResponseRest> responseEntity = this.direccionService.buscarPorId(VALID_ID);
        verify(direccionDaoMock, times(1)).findById(VALID_ID);
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        DireccionResponseRest responseBody = responseEntity.getBody();
        assertNotNull(responseBody);
        assertEquals(DataDummy.DIRECCION, responseBody.getDireccionResponse().getDirecciones().get(0));
        
        when(direccionDaoMock.findById(INVALID_ID))
            .thenReturn(Optional.empty());
        ResponseEntity<DireccionResponseRest> responseEmpty = this.direccionService.buscarPorId(INVALID_ID);
        verify(direccionDaoMock, times(1)).findById(INVALID_ID);
        assertEquals(HttpStatus.NOT_FOUND, responseEmpty.getStatusCode());
        DireccionResponseRest responseEmptyBody = responseEmpty.getBody();
        assertNotNull(responseEmptyBody);

        when(direccionDaoMock.findById(anyLong()))
            .thenThrow(new RuntimeException("Simulated exception"));
        ResponseEntity<DireccionResponseRest> responseException = this.direccionService.buscarPorId(INVALID_ID);
        verify(direccionDaoMock, times(2)).findById(INVALID_ID);
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, responseException.getStatusCode());
        DireccionResponseRest responseExceptionBody = responseException.getBody();
        assertNotNull(responseExceptionBody);

    }


    @Test
    @DisplayName ("crear should work")
    void crear (){
        when(direccionDaoMock.save(any(Direccion.class)))
            .thenReturn(DataDummy.DIRECCION);
        ResponseEntity<DireccionResponseRest> responseEntity = this.direccionService.crear(DataDummy.DIRECCION);
        verify(direccionDaoMock, times(1)).save(DataDummy.DIRECCION);
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        DireccionResponseRest responseBody = responseEntity.getBody();
        assertNotNull(responseBody);
        assertEquals(DataDummy.DIRECCION, responseBody.getDireccionResponse().getDirecciones().get(0));

        when(direccionDaoMock.save(any(Direccion.class)))
            .thenReturn(null);
        ResponseEntity<DireccionResponseRest> responseNull = this.direccionService.crear(DataDummy.DIRECCION);
        verify(direccionDaoMock, times(2)).save(DataDummy.DIRECCION);
        assertEquals(HttpStatus.BAD_REQUEST, responseNull.getStatusCode());
        DireccionResponseRest responseNullBody = responseNull.getBody();
        assertNotNull(responseNullBody);

        when(direccionDaoMock.save(any(Direccion.class)))
            .thenThrow(new RuntimeException("Simulated exception"));
        ResponseEntity<DireccionResponseRest> responseException = this.direccionService.crear(DataDummy.DIRECCION);
        verify(direccionDaoMock, times(3)).save(DataDummy.DIRECCION);
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, responseException.getStatusCode());
        DireccionResponseRest responseExceptionBody = responseException.getBody();
        assertNotNull(responseExceptionBody);

    }


    @Test
    @DisplayName ("actualizar should work")
    void actualizar(){
        when(direccionDaoMock.findById(anyLong()))
            .thenReturn(Optional.of(DataDummy.DIRECCION));
        when(direccionDaoMock.save(any(Direccion.class)))
            .thenReturn(DataDummy.DIRECCION);
        ResponseEntity<DireccionResponseRest> response = this.direccionService.actualizar(DataDummy.DIRECCION, VALID_ID);
        verify(direccionDaoMock, times(1)).findById(VALID_ID);
        verify(direccionDaoMock, times(1)).save(DataDummy.DIRECCION);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        DireccionResponseRest responseBody = response.getBody();
        assertNotNull(responseBody);
        assertEquals(DataDummy.DIRECCION, responseBody.getDireccionResponse().getDirecciones().get(0));

        when(direccionDaoMock.findById(anyLong()))
            .thenReturn(Optional.of(DataDummy.DIRECCION));
        when(direccionDaoMock.save(any(Direccion.class)))
            .thenReturn(null);
        ResponseEntity<DireccionResponseRest> responseNull = this.direccionService.actualizar(DataDummy.DIRECCION, VALID_ID);
        verify(direccionDaoMock, times(2)).findById(VALID_ID);
        verify(direccionDaoMock, times(2)).save(DataDummy.DIRECCION);
        assertEquals(HttpStatus.BAD_REQUEST, responseNull.getStatusCode());
        DireccionResponseRest responseNullBody = responseNull.getBody();
        assertNotNull(responseNullBody);

        when(direccionDaoMock.findById(anyLong()))
            .thenReturn(Optional.empty());
        ResponseEntity<DireccionResponseRest> responseEmpty = this.direccionService.actualizar(DataDummy.DIRECCION, INVALID_ID);
        verify(direccionDaoMock, times(1)).findById(INVALID_ID);
        assertEquals(HttpStatus.NOT_FOUND, responseEmpty.getStatusCode());
        DireccionResponseRest responseEmptyBody = responseEmpty.getBody();
        assertNotNull(responseEmptyBody);

        when(direccionDaoMock.findById(anyLong()))
            .thenThrow(new RuntimeException("Simulated exception"));
        ResponseEntity<DireccionResponseRest> responseException = this.direccionService.actualizar(DataDummy.DIRECCION, INVALID_ID);
        verify(direccionDaoMock, times(2)).findById(INVALID_ID);
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, responseException.getStatusCode());
        DireccionResponseRest responseExceptionBody = responseException.getBody();
        assertNotNull(responseExceptionBody);

    }


    @Test
    @DisplayName ("eliminar should work")
    void eliminar(){
        doNothing().when(direccionDaoMock).deleteById(anyLong());
        ResponseEntity<DireccionResponseRest> response = this.direccionService.eliminar(VALID_ID);
        verify(direccionDaoMock, times(1)).deleteById(VALID_ID);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        DireccionResponseRest responseBody = response.getBody();
        assertNotNull(responseBody);

        doThrow(new RuntimeException("Simulated exception")).when(direccionDaoMock).deleteById(anyLong());
        ResponseEntity<DireccionResponseRest> responseException = this.direccionService.eliminar(INVALID_ID);
        verify(direccionDaoMock, times(1)).deleteById(INVALID_ID);
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, responseException.getStatusCode());
        DireccionResponseRest responseExceptionBody = responseException.getBody();
        assertNotNull(responseExceptionBody);
        
    }


    
}
