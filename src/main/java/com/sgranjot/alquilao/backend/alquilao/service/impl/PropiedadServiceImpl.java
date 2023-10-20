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

import com.sgranjot.alquilao.backend.alquilao.model.Propiedad;
import com.sgranjot.alquilao.backend.alquilao.model.dao.IPropiedadDao;
import com.sgranjot.alquilao.backend.alquilao.response.PropiedadResponseRest;
import com.sgranjot.alquilao.backend.alquilao.service.IPropiedadService;

@Service
public class PropiedadServiceImpl implements IPropiedadService {

    @Autowired
    private IPropiedadDao propiedadDao;

    private static final Logger log = LoggerFactory.getLogger(ArrendatarioServiceImpl.class);


    @Override
    @Transactional(readOnly = true)
    public ResponseEntity<PropiedadResponseRest> buscarPropiedades() {
        log.info("inicio del método buscarPropiedades()");
        PropiedadResponseRest response = new PropiedadResponseRest();

        try {
            List<Propiedad> propiedades = (List<Propiedad>) propiedadDao.findAll();
            response.getPropiedadResponse().setPropiedades(propiedades);
            response.setMetadata("Respuesta ok", "00", "Respuesta exitosa");
        } catch (Exception e) {
            response.setMetadata("Respuesta no ok", "-1", "Error al consultar propiedades");
            log.error("error al consultar propiedades: ", e);
            e.getStackTrace();
            return new ResponseEntity<PropiedadResponseRest>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return new ResponseEntity<PropiedadResponseRest>(response, HttpStatus.OK);

    }


    @Override
    @Transactional(readOnly = true)
    public ResponseEntity<PropiedadResponseRest> buscarPorId(Long id) {
        log.info("Inicio del método buscarPorId()");
        PropiedadResponseRest response = new PropiedadResponseRest();
        List<Propiedad> propiedades = new ArrayList<>();

        try {
            Optional<Propiedad> propiedadOptional = propiedadDao.findById(id);
            if (propiedadOptional.isPresent()) {
                propiedades.add(propiedadOptional.get());
                response.getPropiedadResponse().setPropiedades(propiedades);
                response.setMetadata("Respuesta ok", "00", "respuesta exitosa");
            } else {
                response.setMetadata("Respuesta no ok", "-1", "propiedad no encontrada");
                log.error("error al consultar la propiedad");
                return new ResponseEntity<PropiedadResponseRest>(response, HttpStatus.NOT_FOUND);
            }
        } catch (Exception e) {
            response.setMetadata("respuesta no ok", "-1", "error al consultar la propiedad");
            log.error("respuesta no ok", e);
            e.getStackTrace();
            return new ResponseEntity<PropiedadResponseRest>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return new ResponseEntity<PropiedadResponseRest>(response, HttpStatus.OK);
    }


    @Override
    @Transactional
    public ResponseEntity<PropiedadResponseRest> crear(Propiedad propiedad) {
        log.info("inicio del método crear()");
        PropiedadResponseRest response = new PropiedadResponseRest();
        List<Propiedad> propiedades = new ArrayList<>();

        try{
            Propiedad propiedadGuardar = propiedadDao.save(propiedad);
            if(propiedadGuardar!=null){
                propiedades.add(propiedadGuardar);
                response.getPropiedadResponse().setPropiedades(propiedades);
                response.setMetadata("Respuesta ok", "00", "propiedad creada");
            }else{
                log.error("Error al crear la propiedad");
                response.setMetadata("Respuesta no ok", "-1", "propiedad no creada");
                return new ResponseEntity<PropiedadResponseRest>(response, HttpStatus.BAD_REQUEST);
            } 
        }catch (Exception e) {
            log.error("error al crear propiedad", e);
            response.setMetadata("Respuesta no ok", "-1", "error al crear la propiedad");
            e.getStackTrace();
            return new ResponseEntity<PropiedadResponseRest>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return new ResponseEntity<PropiedadResponseRest>(response, HttpStatus.OK);
    }


    
    @Override
    @Transactional
    public ResponseEntity<PropiedadResponseRest> actualizar(Propiedad propiedad, Long id) {
        log.info("inicio del método actualizar()");
        PropiedadResponseRest response = new PropiedadResponseRest();
        List<Propiedad> propiedades = new ArrayList<>();

        try {
            Optional<Propiedad> propiedadBuscada = propiedadDao.findById(id);
            if (propiedadBuscada.isPresent()){
                propiedadBuscada.get().setContratos(propiedad.getContratos());
                propiedadBuscada.get().setDireccion(propiedad.getDireccion());
                propiedadBuscada.get().setGastos(propiedad.getGastos());
                propiedadBuscada.get().setLibre(propiedad.isLibre());
                propiedadBuscada.get().setTipo(propiedad.getTipo());
                propiedadBuscada.get().setUsuario(propiedad.getUsuario());
                Propiedad propiedadActualizar = propiedadDao.save(propiedadBuscada.get());
                
                if (propiedadActualizar != null){
                    propiedades.add(propiedadActualizar);
                    response.getPropiedadResponse().setPropiedades(propiedades);
                    response.setMetadata("Respuesta ok", "00", "propiedad actualizada correctamente");
                }else{
                    log.error("error al actualizar la propiedad");
                    response.setMetadata("Respuesta no ok", "-1", "propiedad no actualizada");
                    return new ResponseEntity<PropiedadResponseRest>(response, HttpStatus.BAD_REQUEST);
                }
            }else{
                log.error("error al actualizar la propiedad");
                response.setMetadata("Respuesta no ok", "-1", "propiedad no actualizada");
                return new ResponseEntity<PropiedadResponseRest>(response, HttpStatus.NOT_FOUND);
            }
        } catch (Exception e) {
            log.error("error al actualizar la propiedad", e);
            e.getStackTrace();
            response.setMetadata("Respuesta no ok", "-1", "propiedad no actualizada");
            return new ResponseEntity<PropiedadResponseRest>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return new ResponseEntity<PropiedadResponseRest>(response, HttpStatus.OK);
    }


 
    @Override
    @Transactional
    public ResponseEntity<PropiedadResponseRest> eliminar(Long id) {
        log.info("inicio del método eliminar()");
        PropiedadResponseRest response = new PropiedadResponseRest();

        try{
            propiedadDao.deleteById(id);
            response.setMetadata("Respuesta ok", "00", "propiedad eliminada");
        }catch (Exception e){
            log.error("error al eliminar la propiedad", e.getMessage());
            e.getStackTrace();
            response.setMetadata("Respuesta no ok", "-1", "propiedad no eliminada");
            return new ResponseEntity<PropiedadResponseRest>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return new ResponseEntity<PropiedadResponseRest>(response, HttpStatus.OK);
    }
}
