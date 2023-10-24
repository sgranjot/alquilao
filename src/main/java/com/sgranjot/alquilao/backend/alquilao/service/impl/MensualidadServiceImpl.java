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

import com.sgranjot.alquilao.backend.alquilao.model.Mensualidad;
import com.sgranjot.alquilao.backend.alquilao.model.dao.IMensualidadDao;
import com.sgranjot.alquilao.backend.alquilao.response.MensualidadResponseRest;
import com.sgranjot.alquilao.backend.alquilao.service.IMensualidadService;

@Service
public class MensualidadServiceImpl implements IMensualidadService {
    
    @Autowired
    private IMensualidadDao mensualidadDao;

    private static final Logger log = LoggerFactory.getLogger(MensualidadServiceImpl.class);


    @Override
    @Transactional(readOnly = true)
    public ResponseEntity<MensualidadResponseRest> buscarMensualidades() {
        log.info("inicio del método buscarMensualidades");
        MensualidadResponseRest response = new MensualidadResponseRest();

        try {
            List<Mensualidad> mensualidades = (List<Mensualidad>) mensualidadDao.findAll();
            response.getMensualidadResponse().setMensualidades(mensualidades);
            response.setMetadata("Respuesta ok", "00", "Respuesta exitosa");
        } catch (Exception e) {
            response.setMetadata("Respuesta no ok", "-1", "Error al consultar mensualidades");
            log.error("error al consultar mensualidades: ", e);
            e.getStackTrace();
            return new ResponseEntity<MensualidadResponseRest>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return new ResponseEntity<MensualidadResponseRest>(response, HttpStatus.OK);

    }


    @Override
    @Transactional(readOnly = true)
    public ResponseEntity<MensualidadResponseRest> buscarPorId(Long id) {
        log.info("Inicio del método buscarPorId");
        MensualidadResponseRest response = new MensualidadResponseRest();
        List<Mensualidad> mensualidades = new ArrayList<>();

        try {
            Optional<Mensualidad> mensualidadOptional = mensualidadDao.findById(id);
            if (mensualidadOptional.isPresent()) {
                mensualidades.add(mensualidadOptional.get());
                response.getMensualidadResponse().setMensualidades(mensualidades);
                response.setMetadata("Respuesta ok", "00", "respuesta exitosa");
            } else {
                response.setMetadata("Respuesta no ok", "-1", "mensualidad no encontrada");
                log.error("error al consultar la mensualidad");
                return new ResponseEntity<MensualidadResponseRest>(response, HttpStatus.NOT_FOUND);
            }
        } catch (Exception e) {
            response.setMetadata("respuesta no ok", "-1", "error al consultar la mensualidad");
            log.error("respuesta no ok", e);
            e.getStackTrace();
            return new ResponseEntity<MensualidadResponseRest>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return new ResponseEntity<MensualidadResponseRest>(response, HttpStatus.OK);
    }


    @Override
    @Transactional
    public ResponseEntity<MensualidadResponseRest> crear(Mensualidad mensualidad) {
        log.info("inicio del método crear");
        MensualidadResponseRest response = new MensualidadResponseRest();
        List<Mensualidad> mensualidades = new ArrayList<>();

        try{
            Mensualidad mensualidadGuardar = mensualidadDao.save(mensualidad);
            if(mensualidadGuardar != null){
                mensualidades.add(mensualidadGuardar);
                response.getMensualidadResponse().setMensualidades(mensualidades);
                response.setMetadata("Respuesta ok", "00", "mensualidad creada");
            }else{
                log.error("Error al crear la mensualidad");
                response.setMetadata("Respuesta no ok", "-1", "mensualidad no creada");
                return new ResponseEntity<MensualidadResponseRest>(response, HttpStatus.BAD_REQUEST);
            } 
        }catch (Exception e) {
            log.error("error al crear la mensualidad", e);
            response.setMetadata("Respuesta no ok", "-1", "error al crear la mensualidad");
            e.getStackTrace();
            return new ResponseEntity<MensualidadResponseRest>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return new ResponseEntity<MensualidadResponseRest>(response, HttpStatus.OK);
    }


    
    @Override
    @Transactional
    public ResponseEntity<MensualidadResponseRest> actualizar(Mensualidad mensualidad, Long id) {
        log.info("inicio del método actualizar");
        MensualidadResponseRest response = new MensualidadResponseRest();
        List<Mensualidad> mensualidades = new ArrayList<>();

        try {
            Optional<Mensualidad> mensualidadBuscada = mensualidadDao.findById(id);
            if (mensualidadBuscada.isPresent()){
                mensualidadBuscada.get().setContrato(mensualidad.getContrato());
                mensualidadBuscada.get().setEstado(mensualidad.isEstado());
                mensualidadBuscada.get().setFecha(mensualidad.getFecha());
                mensualidadBuscada.get().setImporte(mensualidad.getImporte());
                mensualidadBuscada.get().setUsuario(mensualidad.getUsuario());
                Mensualidad mensualidadActualizar = mensualidadDao.save(mensualidadBuscada.get());
                
                if (mensualidadActualizar != null){
                    mensualidades.add(mensualidadActualizar);
                    response.getMensualidadResponse().setMensualidades(mensualidades);
                    response.setMetadata("Respuesta ok", "00", "mensualidad actualizada correctamente");
                }else{
                    log.error("error al actualizar la mensualidad");
                    response.setMetadata("Respuesta no ok", "-1", "mensualidad no actualizada");
                    return new ResponseEntity<MensualidadResponseRest>(response, HttpStatus.BAD_REQUEST);
                }
            }else{
                log.error("error al actualizar la mensualidad");
                response.setMetadata("Respuesta no ok", "-1", "mensualidad no actualizada");
                return new ResponseEntity<MensualidadResponseRest>(response, HttpStatus.NOT_FOUND);
            }
        } catch (Exception e) {
            log.error("error al actualizar la mensualidad", e);
            e.getStackTrace();
            response.setMetadata("Respuesta no ok", "-1", "mensualidad no actualizada");
            return new ResponseEntity<MensualidadResponseRest>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return new ResponseEntity<MensualidadResponseRest>(response, HttpStatus.OK);
    }


 
    @Override
    @Transactional
    public ResponseEntity<MensualidadResponseRest> eliminar(Long id) {
        log.info("inicio del método eliminar");
        MensualidadResponseRest response = new MensualidadResponseRest();

        try{
            mensualidadDao.deleteById(id);
            response.setMetadata("Respuesta ok", "00", "mensualidad eliminada");
        }catch (Exception e){
            log.error("error al eliminar la mensualidad", e);
            e.getStackTrace();
            response.setMetadata("Respuesta no ok", "-1", "mensualidad no eliminada");
            return new ResponseEntity<MensualidadResponseRest>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return new ResponseEntity<MensualidadResponseRest>(response, HttpStatus.OK);
    }

    
}
