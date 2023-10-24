package com.sgranjot.alquilao.backend.alquilao.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.sgranjot.alquilao.backend.alquilao.model.Direccion;
import com.sgranjot.alquilao.backend.alquilao.model.dao.IDireccionDao;
import com.sgranjot.alquilao.backend.alquilao.response.DireccionResponseRest;
import com.sgranjot.alquilao.backend.alquilao.service.IDireccionService;

@Service
public class DireccionServiceImpl implements IDireccionService{

    @Autowired
    private IDireccionDao direccionDao;

    private static final Logger log = LoggerFactory.getLogger(DireccionServiceImpl.class);


    @Override
    @Transactional (readOnly= true)
    public ResponseEntity<DireccionResponseRest> buscarDirecciones() {
        log.info("Inicio del método buscarDirecciones()");
        DireccionResponseRest response = new DireccionResponseRest();

        try {
            List<Direccion> direcciones = (List<Direccion>) direccionDao.findAll();
            response.getDireccionResponse().setDirecciones(direcciones);
            response.setMetadata("Respuesta ok", "00", "Respuesta exitosa");
        } catch (Exception e) {
            log.error("error al buscar direcciones", e);
            e.getStackTrace();
            response.setMetadata("Respuesta no ok", "-1", "error al buscar direcciones");
            return new ResponseEntity<DireccionResponseRest>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return new ResponseEntity<DireccionResponseRest>(response, HttpStatus.OK);

    }



    @Override
    @Transactional (readOnly = true)
    public ResponseEntity<DireccionResponseRest> buscarPorId(Long id) {
       log.info("Inicio del método buscarPorId()");
       DireccionResponseRest response = new DireccionResponseRest();
       List<Direccion> direcciones = new ArrayList<>();

       try{
        Optional<Direccion> direccionOptional = direccionDao.findById(id);
        if(direccionOptional.isPresent()){
            direcciones.add(direccionOptional.get());
            response.getDireccionResponse().setDirecciones(direcciones);
            response.setMetadata("Respuesta ok", "00", "Respuesta exitosa");
        }else{
            log.error("error al buscar la dirección");
            response.setMetadata("Respuesta no ok", "-1", "dirección no encontrada");
            return new ResponseEntity<DireccionResponseRest>(response, HttpStatus.NOT_FOUND);
        }
       }catch (Exception e){
            log.error("error al buscar la dirección", e);
            e.getStackTrace();
            response.setMetadata("Respuesta no ok", "-1", "Error al buscar la dirección");
            return new ResponseEntity<DireccionResponseRest>(response, HttpStatus.INTERNAL_SERVER_ERROR);
       }
        return new ResponseEntity<DireccionResponseRest>(response, HttpStatus.OK);
    }



    @Override
    @Transactional
    public ResponseEntity<DireccionResponseRest> crear(Direccion direccion) {
        log.info("Inicio del método crear()");
        DireccionResponseRest response = new DireccionResponseRest();
        List<Direccion> direcciones = new ArrayList<>();

        try {
            Direccion direccionNueva = direccionDao.save(direccion);
            if(direccionNueva != null){
                direcciones.add(direccionNueva);
                response.getDireccionResponse().setDirecciones(direcciones);
                response.setMetadata("Respuesta ok", "00", "respuesta exitosa");
            }else{
                log.error("Error al crear la dirección");
                response.setMetadata("Respuesta no ok", "-1", "Error al crear la dirección");
                return new ResponseEntity<DireccionResponseRest>(response, HttpStatus.BAD_REQUEST);
            }
        } catch (Exception e) {
            log.error("Error al crear la dirección", e);
            e.getStackTrace();
            response.setMetadata("Respuesta no ok", "-1", "Error al crear la dirección");
            return new ResponseEntity<DireccionResponseRest>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return new ResponseEntity<DireccionResponseRest>(response, HttpStatus.OK);
    }



    @Override
    @Transactional
    public ResponseEntity<DireccionResponseRest> actualizar(Direccion direccion, Long id) {
        log.info("Inicio del método actualizar");
        DireccionResponseRest response = new DireccionResponseRest();
        List<Direccion> direcciones = new ArrayList<>();

        try {
            Optional<Direccion> direccionOptional = direccionDao.findById(id);
            if (direccionOptional.isPresent()) {
                direccionOptional.get().setCalle(direccion.getCalle());
                direccionOptional.get().setCodigoPostal(direccion.getCodigoPostal());
                direccionOptional.get().setNumero(direccion.getNumero());
                direccionOptional.get().setNumeroPlaza(direccion.getNumeroPlaza());
                direccionOptional.get().setPais(direccion.getPais());
                direccionOptional.get().setPiso(direccion.getPiso());
                direccionOptional.get().setPoblacion(direccion.getPoblacion());
                direccionOptional.get().setProvincia(direccion.getProvincia());
                direccionOptional.get().setPuerta(direccion.getPuerta());
                direccionOptional.get().setUsuario(direccion.getUsuario());
                Direccion direccionActualizar = direccionDao.save(direccionOptional.get());
                if (direccionActualizar != null) {
                    direcciones.add(direccionActualizar);
                    response.getDireccionResponse().setDirecciones(direcciones);
                    response.setMetadata("Respuesta ok", "00", "dirección actualizada");
                } else {
                    log.error("Error al actualizar");
                    response.setMetadata("Respuesta no ok", "-1", "Error al actualizar la dirección");
                    return new ResponseEntity<DireccionResponseRest>(response, HttpStatus.BAD_REQUEST);
                }
            } else {
                log.error("Error al actualizar");
                response.setMetadata("Respuesta no ok", "-1", "no se encontró la dirección a actualizar");
                return new ResponseEntity<DireccionResponseRest>(response, HttpStatus.NOT_FOUND);
            }
        } catch (Exception e) {
            log.error("Error al actualizar la dirección", e);
            e.getStackTrace();
            response.setMetadata("Respuesta no ok", "-1", "error al actualizar la dirección");
            return new ResponseEntity<DireccionResponseRest>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return new ResponseEntity<DireccionResponseRest>(response, HttpStatus.OK);
    }



    @Override
    @Transactional
    public ResponseEntity<DireccionResponseRest> eliminar(Long id) {
        log.info("Inicio del método eliminar");
        DireccionResponseRest response = new DireccionResponseRest();

        try {
            direccionDao.deleteById(id);
            response.setMetadata("Respuesta ok", "00", "dirección eliminada");
        } catch (Exception e) {
            log.error("Error al eliminar la dirección", e);
            e.getStackTrace();
            response.setMetadata("Respuesta no ok", "-1", "no se pudo eliminar la dirección");
            return new ResponseEntity<DireccionResponseRest>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return new ResponseEntity<DireccionResponseRest>(response, HttpStatus.OK);

    }
    
}
