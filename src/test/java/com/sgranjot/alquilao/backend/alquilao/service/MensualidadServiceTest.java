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

import com.sgranjot.alquilao.backend.alquilao.model.Mensualidad;
import com.sgranjot.alquilao.backend.alquilao.model.dao.IMensualidadDao;
import com.sgranjot.alquilao.backend.alquilao.response.MensualidadResponseRest;
import com.sgranjot.alquilao.backend.alquilao.utils.DataDummy;

@SpringBootTest
@ActiveProfiles ("test")
public class MensualidadServiceTest {

    @MockBean
    private IMensualidadDao mensualidadDaoMock;

    @Autowired
    private IMensualidadService mensualidadService;

    private static final Long VALID_ID = 1L;
    private static final Long INVALID_ID = 2L;


    @AfterEach
    void resetMocks(){
        reset(this.mensualidadDaoMock);
    }


    @Test
    @DisplayName ("buscarMensualidades should work")
    void buscarMensualidades(){
        when(this.mensualidadDaoMock.findAll())
            .thenReturn(DataDummy.MENSUALIDADES);
        ResponseEntity<MensualidadResponseRest> responseEntity = this.mensualidadService.buscarMensualidades();
        verify(mensualidadDaoMock, times(1)).findAll();
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        MensualidadResponseRest responseBody = responseEntity.getBody();
        assertNotNull(responseBody);
        assertEquals(DataDummy.MENSUALIDADES, responseBody.getMensualidadResponse().getMensualidades());
        assertEquals(2, responseBody.getMensualidadResponse().getMensualidades().size());

        when(this.mensualidadDaoMock.findAll())
            .thenReturn(Collections.emptyList());
        ResponseEntity<MensualidadResponseRest> responseEntityEmpty = this.mensualidadService.buscarMensualidades();
        verify(mensualidadDaoMock, times(2)).findAll();
        assertEquals(HttpStatus.OK, responseEntityEmpty.getStatusCode());
        MensualidadResponseRest responseEmptyBody = responseEntityEmpty.getBody();
        assertNotNull(responseEmptyBody);
        assertEquals(0, responseEmptyBody.getMensualidadResponse().getMensualidades().size());

        when(this.mensualidadDaoMock.findAll())
            .thenThrow(new RuntimeException("Simulated exception"));
        ResponseEntity<MensualidadResponseRest> responseEntityException = this.mensualidadService.buscarMensualidades();
        verify(mensualidadDaoMock, times(3)).findAll();
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, responseEntityException.getStatusCode());
        MensualidadResponseRest responseException = responseEntityException.getBody();
        assertNotNull(responseException); 

    }


    @Test
    @DisplayName ("buscarPorId should work")
    void buscarPorId(){
        when(this.mensualidadDaoMock.findById(anyLong()))
            .thenReturn(Optional.of(DataDummy.MENSUALIDAD));
        ResponseEntity<MensualidadResponseRest> responseEntity = this.mensualidadService.buscarPorId(VALID_ID);
        verify(mensualidadDaoMock, times(1)).findById(VALID_ID);
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        MensualidadResponseRest response = responseEntity.getBody();
        assertNotNull(response);
        assertEquals(DataDummy.MENSUALIDAD, response.getMensualidadResponse().getMensualidades().get(0));

        when(this.mensualidadDaoMock.findById(anyLong()))
            .thenReturn(Optional.empty());
        ResponseEntity<MensualidadResponseRest> responseEntityNoValid = this.mensualidadService.buscarPorId(INVALID_ID);
        verify(mensualidadDaoMock, times(1)).findById(INVALID_ID);
        assertEquals(HttpStatus.NOT_FOUND, responseEntityNoValid.getStatusCode());
        MensualidadResponseRest responseNoValid = responseEntityNoValid.getBody();
        assertNotNull(responseNoValid);   
        
        when(mensualidadDaoMock.findById(anyLong()))
            .thenThrow(new RuntimeException("Simulated exception"));
        ResponseEntity<MensualidadResponseRest> responseEntityException = this.mensualidadService.buscarPorId(3L);
        verify(mensualidadDaoMock, times(1)).findById(3L);
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, responseEntityException.getStatusCode());
        MensualidadResponseRest responseExceptionBody = responseEntityException.getBody();
        assertNotNull(responseExceptionBody);

    }


    @Test
    @DisplayName ("crear should work")
    void crear(){
        when(this.mensualidadDaoMock.save(any(Mensualidad.class)))
            .thenReturn(DataDummy.MENSUALIDAD);
        ResponseEntity<MensualidadResponseRest> responseEntity = this.mensualidadService.crear(DataDummy.MENSUALIDAD);
        verify(mensualidadDaoMock, times(1)).save(DataDummy.MENSUALIDAD);
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        MensualidadResponseRest response = responseEntity.getBody();
        assertNotNull(response);
        assertEquals(DataDummy.MENSUALIDAD, response.getMensualidadResponse().getMensualidades().get(0));

        when(this.mensualidadDaoMock.save(any(Mensualidad.class)))
            .thenReturn(null);
        ResponseEntity<MensualidadResponseRest> responseEntityNull = this.mensualidadService.crear(DataDummy.MENSUALIDAD);
        verify(mensualidadDaoMock, times(2)).save(DataDummy.MENSUALIDAD);
        assertEquals(HttpStatus.BAD_REQUEST, responseEntityNull.getStatusCode());
        MensualidadResponseRest responseNullBody = responseEntityNull.getBody();
        assertNotNull(responseNullBody);

        when(this.mensualidadDaoMock.save(any(Mensualidad.class)))
            .thenThrow(new RuntimeException("Simulated exception"));
        ResponseEntity<MensualidadResponseRest> responseEntityException = this.mensualidadService.crear(DataDummy.MENSUALIDAD);
        verify(mensualidadDaoMock, times(3)).save(DataDummy.MENSUALIDAD);
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, responseEntityException.getStatusCode());
        MensualidadResponseRest responseExceptionBody = responseEntityException.getBody();
        assertNotNull(responseExceptionBody);

    }


    @Test
    @DisplayName ("actualizar should work")
    void actualizar (){
        when(this.mensualidadDaoMock.findById(anyLong()))
            .thenReturn(Optional.of(DataDummy.MENSUALIDAD));
        when(this.mensualidadDaoMock.save(any(Mensualidad.class)))
            .thenReturn(DataDummy.MENSUALIDAD);
        ResponseEntity<MensualidadResponseRest> responseEntity = this.mensualidadService.actualizar(DataDummy.MENSUALIDAD, VALID_ID);
        verify(mensualidadDaoMock, times(1)).findById(VALID_ID);
        verify(mensualidadDaoMock, times(1)).save(DataDummy.MENSUALIDAD);
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        MensualidadResponseRest response = responseEntity.getBody();
        assertNotNull(response);
        assertEquals(DataDummy.MENSUALIDAD, response.getMensualidadResponse().getMensualidades().get(0));

        when(this.mensualidadDaoMock.findById(anyLong()))
            .thenReturn(Optional.of(DataDummy.MENSUALIDAD));
        when(this.mensualidadDaoMock.save(any(Mensualidad.class)))
            .thenReturn(null);
        ResponseEntity<MensualidadResponseRest> responseEntityNull = this.mensualidadService.actualizar(DataDummy.MENSUALIDAD, VALID_ID);
        verify(mensualidadDaoMock, times(2)).findById(VALID_ID);
        verify(mensualidadDaoMock, times(2)).save(DataDummy.MENSUALIDAD);
        assertEquals(HttpStatus.BAD_REQUEST, responseEntityNull.getStatusCode());
        MensualidadResponseRest responseNullBody = responseEntityNull.getBody();
        assertNotNull(responseNullBody);

        when(this.mensualidadDaoMock.findById(anyLong()))
            .thenReturn(Optional.empty());
        ResponseEntity<MensualidadResponseRest> responseEntityInvalid = this.mensualidadService.actualizar(DataDummy.MENSUALIDAD, INVALID_ID);
        verify(mensualidadDaoMock, times(1)).findById(INVALID_ID);
        assertEquals(HttpStatus.NOT_FOUND, responseEntityInvalid.getStatusCode());
        MensualidadResponseRest responseInvalidBody = responseEntityInvalid.getBody();
        assertNotNull(responseInvalidBody);

        when(this.mensualidadDaoMock.findById(anyLong()))
            .thenThrow(new RuntimeException("Simulated exception"));
        ResponseEntity<MensualidadResponseRest> responseEntityException = this.mensualidadService.actualizar(DataDummy.MENSUALIDAD, VALID_ID);
        verify(mensualidadDaoMock, times(3)).findById(VALID_ID);
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, responseEntityException.getStatusCode());
        MensualidadResponseRest responseExceptionBody = responseEntityException.getBody();
        assertNotNull(responseExceptionBody);

    }


    @Test
    @DisplayName ("eliminar should work")
    void eliminar(){
        doNothing().when(mensualidadDaoMock).deleteById(anyLong());
        ResponseEntity<MensualidadResponseRest> responseEntity = this.mensualidadService.eliminar(VALID_ID);
        verify(mensualidadDaoMock, times(1)).deleteById(VALID_ID);
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        MensualidadResponseRest response = responseEntity.getBody();
        assertNotNull(response);

        doThrow(new RuntimeException("Simulated exception")).when(mensualidadDaoMock).deleteById(anyLong());
        ResponseEntity<MensualidadResponseRest> responseException = this.mensualidadService.eliminar(INVALID_ID);
        verify(mensualidadDaoMock, times(1)).deleteById(INVALID_ID);
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, responseException.getStatusCode());
        assertNotNull(responseException.getBody());
        
    }
}
