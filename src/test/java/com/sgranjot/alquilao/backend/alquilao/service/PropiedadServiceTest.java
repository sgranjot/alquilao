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

import com.sgranjot.alquilao.backend.alquilao.model.Propiedad;
import com.sgranjot.alquilao.backend.alquilao.model.dao.IPropiedadDao;
import com.sgranjot.alquilao.backend.alquilao.response.PropiedadResponseRest;
import com.sgranjot.alquilao.backend.alquilao.utils.DataDummy;

@SpringBootTest
@ActiveProfiles ("test")
public class PropiedadServiceTest {
    
    @MockBean
    private IPropiedadDao propiedadDaoMock;

    @Autowired
    private IPropiedadService propiedadService;

    private static final Long VALID_ID = 1L;
    private static final Long INVALID_ID = 2L;



    @AfterEach
    void resetMocks(){
        reset(this.propiedadDaoMock);
    }


    @Test
    @DisplayName ("buscarPropiedades should work")
    void buscarPropiedades(){
        when(this.propiedadDaoMock.findAll())
            .thenReturn(DataDummy.PROPIEDADES);
        ResponseEntity<PropiedadResponseRest> responseEntity = this.propiedadService.buscarPropiedades();
        verify(propiedadDaoMock, times(1)).findAll();
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        PropiedadResponseRest responseBody = responseEntity.getBody();
        assertNotNull(responseBody);
        assertEquals(DataDummy.PROPIEDADES, responseBody.getPropiedadResponse().getPropiedades());
        assertEquals(2, responseBody.getPropiedadResponse().getPropiedades().size());

        when(this.propiedadDaoMock.findAll())
            .thenReturn(Collections.emptyList());
        ResponseEntity<PropiedadResponseRest> responseEntityEmpty = this.propiedadService.buscarPropiedades();
        verify(propiedadDaoMock, times(2)).findAll();
        assertEquals(HttpStatus.OK, responseEntityEmpty.getStatusCode());
        PropiedadResponseRest responseEmptyBody = responseEntityEmpty.getBody();
        assertNotNull(responseEmptyBody);
        assertEquals(0, responseEmptyBody.getPropiedadResponse().getPropiedades().size());

        when(this.propiedadDaoMock.findAll())
            .thenThrow(new RuntimeException("Simulated exception"));
        ResponseEntity<PropiedadResponseRest> responseEntityException = this.propiedadService.buscarPropiedades();
        verify(propiedadDaoMock, times(3)).findAll();
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, responseEntityException.getStatusCode());
        PropiedadResponseRest responseException = responseEntityException.getBody();
        assertNotNull(responseException); 

    }


    @Test
    @DisplayName ("buscarPorId should work")
    void buscarPorId(){
        when(this.propiedadDaoMock.findById(anyLong()))
            .thenReturn(Optional.of(DataDummy.PROPIEDAD));
        ResponseEntity<PropiedadResponseRest> responseEntity = this.propiedadService.buscarPorId(VALID_ID);
        verify(propiedadDaoMock, times(1)).findById(VALID_ID);
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        PropiedadResponseRest response = responseEntity.getBody();
        assertNotNull(response);
        assertEquals(DataDummy.PROPIEDAD, response.getPropiedadResponse().getPropiedades().get(0));

        when(this.propiedadDaoMock.findById(anyLong()))
            .thenReturn(Optional.empty());
        ResponseEntity<PropiedadResponseRest> responseEntityNoValid = this.propiedadService.buscarPorId(INVALID_ID);
        verify(propiedadDaoMock, times(1)).findById(INVALID_ID);
        assertEquals(HttpStatus.NOT_FOUND, responseEntityNoValid.getStatusCode());
        PropiedadResponseRest responseNoValid = responseEntityNoValid.getBody();
        assertNotNull(responseNoValid);   
        
        when(propiedadDaoMock.findById(anyLong()))
            .thenThrow(new RuntimeException("Simulated exception"));
        ResponseEntity<PropiedadResponseRest> responseEntityException = this.propiedadService.buscarPorId(3L);
        verify(propiedadDaoMock, times(1)).findById(3L);
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, responseEntityException.getStatusCode());
        PropiedadResponseRest responseExceptionBody = responseEntityException.getBody();
        assertNotNull(responseExceptionBody);

    }


    @Test
    @DisplayName ("crear should work")
    void crear(){
        when(this.propiedadDaoMock.save(any(Propiedad.class)))
            .thenReturn(DataDummy.PROPIEDAD);
        ResponseEntity<PropiedadResponseRest> responseEntity = this.propiedadService.crear(DataDummy.PROPIEDAD);
        verify(propiedadDaoMock, times(1)).save(DataDummy.PROPIEDAD);
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        PropiedadResponseRest response = responseEntity.getBody();
        assertNotNull(response);
        assertEquals(DataDummy.PROPIEDAD, response.getPropiedadResponse().getPropiedades().get(0));

        when(this.propiedadDaoMock.save(any(Propiedad.class)))
            .thenReturn(null);
        ResponseEntity<PropiedadResponseRest> responseEntityNull = this.propiedadService.crear(DataDummy.PROPIEDAD);
        verify(propiedadDaoMock, times(2)).save(DataDummy.PROPIEDAD);
        assertEquals(HttpStatus.BAD_REQUEST, responseEntityNull.getStatusCode());
        PropiedadResponseRest responseNullBody = responseEntityNull.getBody();
        assertNotNull(responseNullBody);

        when(this.propiedadDaoMock.save(any(Propiedad.class)))
            .thenThrow(new RuntimeException("Simulated exception"));
        ResponseEntity<PropiedadResponseRest> responseEntityException = this.propiedadService.crear(DataDummy.PROPIEDAD);
        verify(propiedadDaoMock, times(3)).save(DataDummy.PROPIEDAD);
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, responseEntityException.getStatusCode());
        PropiedadResponseRest responseExceptionBody = responseEntityException.getBody();
        assertNotNull(responseExceptionBody);

    }


    @Test
    @DisplayName ("actualizar should work")
    void actualizar (){
        when(this.propiedadDaoMock.findById(anyLong()))
            .thenReturn(Optional.of(DataDummy.PROPIEDAD));
        when(this.propiedadDaoMock.save(any(Propiedad.class)))
            .thenReturn(DataDummy.PROPIEDAD);
        ResponseEntity<PropiedadResponseRest> responseEntity = this.propiedadService.actualizar(DataDummy.PROPIEDAD, VALID_ID);
        verify(propiedadDaoMock, times(1)).findById(VALID_ID);
        verify(propiedadDaoMock, times(1)).save(DataDummy.PROPIEDAD);
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        PropiedadResponseRest response = responseEntity.getBody();
        assertNotNull(response);
        assertEquals(DataDummy.PROPIEDAD, response.getPropiedadResponse().getPropiedades().get(0));

        when(this.propiedadDaoMock.findById(anyLong()))
            .thenReturn(Optional.of(DataDummy.PROPIEDAD));
        when(this.propiedadDaoMock.save(any(Propiedad.class)))
            .thenReturn(null);
        ResponseEntity<PropiedadResponseRest> responseEntityNull = this.propiedadService.actualizar(DataDummy.PROPIEDAD, VALID_ID);
        verify(propiedadDaoMock, times(2)).findById(VALID_ID);
        verify(propiedadDaoMock, times(2)).save(DataDummy.PROPIEDAD);
        assertEquals(HttpStatus.BAD_REQUEST, responseEntityNull.getStatusCode());
        PropiedadResponseRest responseNullBody = responseEntityNull.getBody();
        assertNotNull(responseNullBody);

        when(this.propiedadDaoMock.findById(anyLong()))
            .thenReturn(Optional.empty());
        ResponseEntity<PropiedadResponseRest> responseEntityInvalid = this.propiedadService.actualizar(DataDummy.PROPIEDAD, INVALID_ID);
        verify(propiedadDaoMock, times(1)).findById(INVALID_ID);
        assertEquals(HttpStatus.NOT_FOUND, responseEntityInvalid.getStatusCode());
        PropiedadResponseRest responseInvalidBody = responseEntityInvalid.getBody();
        assertNotNull(responseInvalidBody);

        when(this.propiedadDaoMock.findById(anyLong()))
            .thenThrow(new RuntimeException("Simulated exception"));
        ResponseEntity<PropiedadResponseRest> responseEntityException = this.propiedadService.actualizar(DataDummy.PROPIEDAD, VALID_ID);
        verify(propiedadDaoMock, times(3)).findById(VALID_ID);
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, responseEntityException.getStatusCode());
        PropiedadResponseRest responseExceptionBody = responseEntityException.getBody();
        assertNotNull(responseExceptionBody);

    }


    @Test
    @DisplayName ("eliminar should work")
    void eliminar(){
        doNothing().when(propiedadDaoMock).deleteById(anyLong());
        ResponseEntity<PropiedadResponseRest> responseEntity = this.propiedadService.eliminar(VALID_ID);
        verify(propiedadDaoMock, times(1)).deleteById(VALID_ID);
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        PropiedadResponseRest response = responseEntity.getBody();
        assertNotNull(response);

        doThrow(new RuntimeException("Simulated exception")).when(propiedadDaoMock).deleteById(anyLong());
        ResponseEntity<PropiedadResponseRest> responseException = this.propiedadService.eliminar(INVALID_ID);
        verify(propiedadDaoMock, times(1)).deleteById(INVALID_ID);
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, responseException.getStatusCode());
        assertNotNull(responseException.getBody());
        
    }

}
