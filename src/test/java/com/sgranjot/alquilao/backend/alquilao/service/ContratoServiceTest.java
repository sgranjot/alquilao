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

import com.sgranjot.alquilao.backend.alquilao.model.Contrato;
import com.sgranjot.alquilao.backend.alquilao.model.dao.IContratoDao;
import com.sgranjot.alquilao.backend.alquilao.response.ContratoResponseRest;
import com.sgranjot.alquilao.backend.alquilao.utils.DataDummy;

@SpringBootTest
@ActiveProfiles("test")
public class ContratoServiceTest {

    @MockBean
    private IContratoDao contratoDaoMock;

    @Autowired
    private IContratoService contratoService;

    private static final Long VALID_ID = 1L;
    private static final Long INVALID_ID = 2L;

    @AfterEach
    void resetMocks(){
        reset(this.contratoDaoMock);   
    }


    @Test
    @DisplayName ("buscarContratos() should work")
    void buscarContratos(){
        when(this.contratoDaoMock.findAll())
            .thenReturn(DataDummy.CONTRATOS);
        ResponseEntity<ContratoResponseRest> responseEntity = this.contratoService.buscarContratos();
        verify(contratoDaoMock, times(1)).findAll();
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        ContratoResponseRest responseBody = responseEntity.getBody();
        assertNotNull(responseBody);
        assertEquals(DataDummy.CONTRATOS, responseBody.getContratoResponse().getContratos());
        assertEquals(2, responseBody.getContratoResponse().getContratos().size());

        when(this.contratoDaoMock.findAll())
            .thenReturn(Collections.emptyList());
        ResponseEntity<ContratoResponseRest> responseEmpty = this.contratoService.buscarContratos();
        verify(contratoDaoMock, times(2)).findAll();
        assertEquals(HttpStatus.OK, responseEmpty.getStatusCode());
        ContratoResponseRest responseEmptyBody = responseEmpty.getBody();
        assertNotNull(responseEmptyBody);
        assertEquals(0, responseEmptyBody.getContratoResponse().getContratos().size());

        when(this.contratoDaoMock.findAll())
            .thenThrow(new RuntimeException("Simulated exception"));
        ResponseEntity<ContratoResponseRest> responseException = this.contratoService.buscarContratos();
        verify(contratoDaoMock, times(3)).findAll();
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, responseException.getStatusCode());
        ContratoResponseRest responseExceptionBody = responseException.getBody();
        assertNotNull(responseExceptionBody);

    }


    @Test
    @DisplayName("buscarPorId() should work")
    void buscarPorId(){
        when(contratoDaoMock.findById(VALID_ID))
            .thenReturn(Optional.of(DataDummy.CONTRATO));
        ResponseEntity<ContratoResponseRest> responseEntity = this.contratoService.buscarPorId(VALID_ID);
        verify(contratoDaoMock, times(1)).findById(VALID_ID);
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        ContratoResponseRest response = responseEntity.getBody();
        assertNotNull(response);
        assertEquals(DataDummy.CONTRATO, response.getContratoResponse().getContratos().get(0));

        when(contratoDaoMock.findById(INVALID_ID))
            .thenReturn(Optional.empty());
        ResponseEntity<ContratoResponseRest> responseEmpty = this.contratoService.buscarPorId(INVALID_ID);
        verify(contratoDaoMock, times(1)).findById(INVALID_ID);
        assertEquals(HttpStatus.NOT_FOUND, responseEmpty.getStatusCode());
        ContratoResponseRest responseEmptyBody = responseEmpty.getBody();
        assertNotNull(responseEmptyBody);

        when(contratoDaoMock.findById(anyLong()))
            .thenThrow(new RuntimeException("Simulated exception"));
        ResponseEntity<ContratoResponseRest> responseException = this.contratoService.buscarPorId(VALID_ID);
        verify(contratoDaoMock, times(2)).findById(VALID_ID);
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, responseException.getStatusCode());
        ContratoResponseRest responseExceptionBody = responseException.getBody();
        assertNotNull(responseExceptionBody);

    }



    @Test
    @DisplayName ("crear() should work")
    void crear(){
        when(contratoDaoMock.save(any(Contrato.class)))
            .thenReturn(DataDummy.CONTRATO);
        ResponseEntity<ContratoResponseRest> response = this.contratoService.crear(DataDummy.CONTRATO);
        verify(contratoDaoMock, times(1)).save(DataDummy.CONTRATO);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        ContratoResponseRest responseBody = response.getBody();
        assertNotNull(responseBody);
        assertEquals(DataDummy.CONTRATO, responseBody.getContratoResponse().getContratos().get(0));

        when(contratoDaoMock.save(any(Contrato.class)))
            .thenReturn(null);
        ResponseEntity<ContratoResponseRest> responseNull = this.contratoService.crear(DataDummy.CONTRATO);
        verify(contratoDaoMock, times(2)).save(DataDummy.CONTRATO);
        assertEquals(HttpStatus.BAD_REQUEST, responseNull.getStatusCode());
        ContratoResponseRest responseNullBody = responseNull.getBody();
        assertNotNull(responseNullBody);

        when(contratoDaoMock.save(any(Contrato.class)))
            .thenThrow(new RuntimeException("Simulated exception"));
        ResponseEntity<ContratoResponseRest> responseException = this.contratoService.crear(DataDummy.CONTRATO);
        verify(contratoDaoMock, times(3)).save(DataDummy.CONTRATO);
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, responseException.getStatusCode());
        ContratoResponseRest responseExceptionBody = responseException.getBody();
        assertNotNull(responseExceptionBody);

    }


    @Test
    @DisplayName("actualizar() should work")
    void actualizar (){
        when(contratoDaoMock.findById(anyLong()))
            .thenReturn(Optional.of(DataDummy.CONTRATO));
        when(contratoDaoMock.save(any(Contrato.class)))
            .thenReturn(DataDummy.CONTRATO);
        ResponseEntity<ContratoResponseRest> response = this.contratoService.actualizar(DataDummy.CONTRATO, VALID_ID);
        verify(contratoDaoMock, times(1)).findById(VALID_ID);
        verify(contratoDaoMock, times(1)).save(DataDummy.CONTRATO);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        ContratoResponseRest responseBody = response.getBody();
        assertNotNull(responseBody);
        assertEquals(DataDummy.CONTRATO, responseBody.getContratoResponse().getContratos().get(0));

        when(contratoDaoMock.findById(anyLong()))
            .thenReturn(Optional.of(DataDummy.CONTRATO));
        when(contratoDaoMock.save(any(Contrato.class)))
            .thenReturn(null);
        ResponseEntity<ContratoResponseRest> responseNull = this.contratoService.actualizar(DataDummy.CONTRATO, VALID_ID);
        verify(contratoDaoMock, times(2)).findById(VALID_ID);
        verify(contratoDaoMock, times(2)).save(DataDummy.CONTRATO);
        assertEquals(HttpStatus.BAD_REQUEST, responseNull.getStatusCode());
        ContratoResponseRest responseNullBody = responseNull.getBody();
        assertNotNull(responseNullBody);

        when(contratoDaoMock.findById(anyLong()))
            .thenReturn(Optional.empty());
        ResponseEntity<ContratoResponseRest> responseEmpty = this.contratoService.actualizar(DataDummy.CONTRATO, INVALID_ID);
        verify(contratoDaoMock, times(1)).findById(INVALID_ID);
        assertEquals(HttpStatus.NOT_FOUND, responseEmpty.getStatusCode());
        ContratoResponseRest responseEmptyBody = responseEmpty.getBody();
        assertNotNull(responseEmptyBody);

        when(contratoDaoMock.findById(anyLong()))
            .thenThrow(new RuntimeException("Simulated exception"));
        ResponseEntity<ContratoResponseRest> responseException = this.contratoService.actualizar(DataDummy.CONTRATO, INVALID_ID);
        verify(contratoDaoMock, times(2)).findById(INVALID_ID);
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, responseException.getStatusCode());
        ContratoResponseRest responseExceptionBody = responseException.getBody();
        assertNotNull(responseExceptionBody);

    }


    @Test
    @DisplayName ("eliminar should work")
    void eliminar (){
        doNothing().when(contratoDaoMock).deleteById(anyLong());
        ResponseEntity<ContratoResponseRest> response = this.contratoService.eliminar(VALID_ID);
        verify(contratoDaoMock, times(1)).deleteById(VALID_ID);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        ContratoResponseRest responseBody = response.getBody();
        assertNotNull(responseBody);

        doThrow(new RuntimeException("Simulated exception")).when(contratoDaoMock).deleteById(anyLong());
        ResponseEntity<ContratoResponseRest> responseException = this.contratoService.eliminar(INVALID_ID);
        verify(contratoDaoMock, times(1)).deleteById(INVALID_ID);
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, responseException.getStatusCode());
        assertNotNull(responseException.getBody());

    }
    
}
