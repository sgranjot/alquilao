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

import com.sgranjot.alquilao.backend.alquilao.model.Gasto;
import com.sgranjot.alquilao.backend.alquilao.model.dao.IGastoDao;
import com.sgranjot.alquilao.backend.alquilao.response.GastoResponseRest;
import com.sgranjot.alquilao.backend.alquilao.utils.DataDummy;

@SpringBootTest
@ActiveProfiles("test")
public class GastoServiceTest {

    @MockBean
    private IGastoDao gastoDaoMock;

    @Autowired
    private IGastoService gastoService;

    private static final Long VALID_ID = 1L;
    private static final Long INVALID_ID = 2L;


    @AfterEach
    void resetMocks(){
        reset(this.gastoDaoMock);
    }


    @Test
    @DisplayName ("buscarGastos should work")
    void buscarGastos(){
        when(gastoDaoMock.findAll())
            .thenReturn(DataDummy.GASTOS);
        ResponseEntity<GastoResponseRest> response = this.gastoService.buscarGastos();
        verify(gastoDaoMock, times(1)).findAll();
        assertEquals(HttpStatus.OK, response.getStatusCode());
        GastoResponseRest responseBody = response.getBody();
        assertNotNull(responseBody);
        assertEquals(DataDummy.GASTOS, responseBody.getGastoResponse().getGastos());
        assertEquals(2, responseBody.getGastoResponse().getGastos().size());

        when(gastoDaoMock.findAll())
            .thenReturn(Collections.emptyList());
        ResponseEntity<GastoResponseRest> responseEmpty = this.gastoService.buscarGastos();
        verify(gastoDaoMock, times(2)).findAll();
        assertEquals(HttpStatus.OK, responseEmpty.getStatusCode());
        GastoResponseRest responseEmptyBody = responseEmpty.getBody();
        assertNotNull(responseEmptyBody);
        assertEquals(0, responseEmptyBody.getGastoResponse().getGastos().size());

        when(gastoDaoMock.findAll())
            .thenThrow(new RuntimeException("Simulated exception"));
        ResponseEntity<GastoResponseRest> responseException = this.gastoService.buscarGastos();
        verify(gastoDaoMock, times(3)).findAll();
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, responseException.getStatusCode());
        GastoResponseRest responseExcptionBody = responseException.getBody();
        assertNotNull(responseExcptionBody);

    }


    @Test
    @DisplayName ("buscarPorId should work")
    void buscarPorId(){
        when(gastoDaoMock.findById(anyLong()))
            .thenReturn(Optional.of(DataDummy.GASTO));
        ResponseEntity<GastoResponseRest> response = this.gastoService.buscarPorId(VALID_ID);
        verify(gastoDaoMock, times(1)).findById(VALID_ID);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        GastoResponseRest responseBody = response.getBody();
        assertNotNull(responseBody);
        assertEquals(DataDummy.GASTO, responseBody.getGastoResponse().getGastos().get(0));

        when(gastoDaoMock.findById(anyLong()))
            .thenReturn(Optional.empty());
        ResponseEntity<GastoResponseRest> responseEmpty = this.gastoService.buscarPorId(INVALID_ID);
        verify(gastoDaoMock, times(1)).findById(INVALID_ID);
        assertEquals(HttpStatus.NOT_FOUND, responseEmpty.getStatusCode());
        GastoResponseRest responseEmptyBody = responseEmpty.getBody();
        assertNotNull(responseEmptyBody);
        
        when(gastoDaoMock.findById(anyLong()))
            .thenThrow(new RuntimeException("Simulated exception"));
        ResponseEntity responseException = this.gastoService.buscarPorId(INVALID_ID);
        verify(gastoDaoMock, times(2)).findById(INVALID_ID);
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, responseException.getStatusCode());
        GastoResponseRest responseExceptionBody = (GastoResponseRest) responseException.getBody();
        assertNotNull(responseExceptionBody);
        
    }


    @Test
    @DisplayName ("crear should work")
    void crear(){
        when(gastoDaoMock.save(any(Gasto.class)))
            .thenReturn(DataDummy.GASTO);
        ResponseEntity<GastoResponseRest>response = this.gastoService.crear(DataDummy.GASTO);
        verify(gastoDaoMock, times(1)).save(DataDummy.GASTO);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        GastoResponseRest responseBody = response.getBody();
        assertNotNull(responseBody);
        assertEquals(DataDummy.GASTO, responseBody.getGastoResponse().getGastos().get(0));

        when(gastoDaoMock.save(any(Gasto.class)))
            .thenReturn(null);
        ResponseEntity<GastoResponseRest> responseNull = this.gastoService.crear(DataDummy.GASTO);
        verify(gastoDaoMock, times(2)).save(DataDummy.GASTO);
        assertEquals(HttpStatus.BAD_REQUEST, responseNull.getStatusCode());
        GastoResponseRest responseNullBody = responseNull.getBody();
        assertNotNull(responseNullBody);

        when(gastoDaoMock.save(any(Gasto.class)))
            .thenThrow(new RuntimeException("Simulated exception"));
        ResponseEntity<GastoResponseRest> responseException = this.gastoService.crear(DataDummy.GASTO);
        verify(gastoDaoMock, times(3)).save(DataDummy.GASTO);
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, responseException.getStatusCode());
        GastoResponseRest responseExceptionBody = responseException.getBody();
        assertNotNull(responseExceptionBody);

    }


    @Test
    @DisplayName ("actualizar should work")
    void actualizar () {
        when(gastoDaoMock.findById(anyLong()))
            .thenReturn(Optional.of(DataDummy.GASTO));
        when(gastoDaoMock.save(any(Gasto.class)))
            .thenReturn(DataDummy.GASTO);
        ResponseEntity response = this.gastoService.actualizar(DataDummy.GASTO, VALID_ID);
        verify(gastoDaoMock, times(1)).findById(VALID_ID);
        verify(gastoDaoMock, times(1)).save(DataDummy.GASTO);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        GastoResponseRest responseBody = (GastoResponseRest) response.getBody();
        assertNotNull(responseBody);
        assertEquals(DataDummy.GASTO, responseBody.getGastoResponse().getGastos().get(0));

        when(gastoDaoMock.findById(anyLong()))
            .thenReturn(Optional.of(DataDummy.GASTO));
        when(gastoDaoMock.save(any(Gasto.class)))
            .thenReturn(null);
        ResponseEntity responseNull = this.gastoService.actualizar(DataDummy.GASTO, VALID_ID);
        verify(gastoDaoMock, times(2)).findById(VALID_ID);
        verify(gastoDaoMock, times(2)).save(DataDummy.GASTO);
        assertEquals(HttpStatus.BAD_REQUEST, responseNull.getStatusCode());
        GastoResponseRest gastoNullBody = (GastoResponseRest) responseNull.getBody();
        assertNotNull(gastoNullBody);
        
        when(gastoDaoMock.findById(anyLong()))
            .thenReturn(Optional.empty());
        ResponseEntity<GastoResponseRest> responseInvalid = this.gastoService.actualizar(DataDummy.GASTO, INVALID_ID);
        verify(gastoDaoMock, times(1)).findById(INVALID_ID);
        assertEquals(HttpStatus.NOT_FOUND, responseInvalid.getStatusCode());
        GastoResponseRest responseInvalidBody = responseInvalid.getBody();
        assertNotNull(responseInvalidBody);

        when(gastoDaoMock.findById(anyLong()))
            .thenThrow(new RuntimeException("Simulated exception"));
        ResponseEntity<GastoResponseRest> responseException = this.gastoService.actualizar(DataDummy.GASTO, INVALID_ID);
        verify(gastoDaoMock, times(2)).findById(INVALID_ID);
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, responseException.getStatusCode());
        GastoResponseRest responseExceptionBody = responseException.getBody();
        assertNotNull(responseExceptionBody);
        
    }


    @Test
    @DisplayName ("eliminar should work")
    void eliminar (){
        doNothing().when(gastoDaoMock).deleteById(anyLong());
        ResponseEntity<GastoResponseRest> response = this.gastoService.eliminar(VALID_ID);
        verify(gastoDaoMock, times(1)).deleteById(VALID_ID);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        GastoResponseRest responseBody = response.getBody();
        assertNotNull(responseBody);

        doThrow(new RuntimeException("Simulated exception")).when(gastoDaoMock).deleteById(anyLong());
        ResponseEntity<GastoResponseRest> responseException = this.gastoService.eliminar(INVALID_ID);
        verify(gastoDaoMock, times(1)).deleteById(INVALID_ID);
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, responseException.getStatusCode());
        GastoResponseRest responseExceptionBody = responseException.getBody();
        assertNotNull(responseExceptionBody);

    }
    
}
