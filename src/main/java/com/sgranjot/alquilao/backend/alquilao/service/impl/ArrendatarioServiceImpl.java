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

import com.sgranjot.alquilao.backend.alquilao.model.Arrendatario;
import com.sgranjot.alquilao.backend.alquilao.model.dao.IArrendatarioDao;
import com.sgranjot.alquilao.backend.alquilao.response.ArrendatarioResponseRest;
import com.sgranjot.alquilao.backend.alquilao.service.IArrendatarioService;

@Service
public class ArrendatarioServiceImpl implements IArrendatarioService {

    @Autowired
    private IArrendatarioDao arrendatarioDao;

    private static final Logger log = LoggerFactory.getLogger(ArrendatarioServiceImpl.class);


    @Override
    @Transactional(readOnly = true)
    public ResponseEntity<ArrendatarioResponseRest> buscarArrendatarios() {
        log.info("inicio del método buscarArrendatarios()");
        ArrendatarioResponseRest response = new ArrendatarioResponseRest();

        try {
            List<Arrendatario> arrendatarios = (List<Arrendatario>) arrendatarioDao.findAll();
            response.getArrendatarioResponse().setArrendatarios(arrendatarios);
            response.setMetadata("Respuesta ok", "00", "Respuesta exitosa");
        } catch (Exception e) {
            response.setMetadata("Respuesta no ok", "-1", "Error al consultar arrendatarios");
            log.error("error al consultar arrendatarios: ", e.getMessage());
            e.getStackTrace();
            return new ResponseEntity<ArrendatarioResponseRest>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return new ResponseEntity<ArrendatarioResponseRest>(response, HttpStatus.OK);

    }


    @Override
    @Transactional(readOnly = true)
    public ResponseEntity<ArrendatarioResponseRest> buscarPorId(Long id) {
        log.info("Inicio del método buscarPorId()");
        ArrendatarioResponseRest response = new ArrendatarioResponseRest();
        List<Arrendatario> arrendatarios = new ArrayList<>();

        try {
            Optional<Arrendatario> arrendatarioOptional = arrendatarioDao.findById(id);
            if (arrendatarioOptional.isPresent()) {
                arrendatarios.add(arrendatarioOptional.get());
                response.getArrendatarioResponse().setArrendatarios(arrendatarios);
                response.setMetadata("Respuesta ok", "00", "respuesta exitosa");
            } else {
                response.setMetadata("Respuesta no ok", "-1", "arrendatario no encontrado");
                log.error("error al consultar el arrendatario");
                return new ResponseEntity<ArrendatarioResponseRest>(response, HttpStatus.NOT_FOUND);
            }
        } catch (Exception e) {
            response.setMetadata("respuesta no ok", "-1", "error al consultar el arrendatario");
            log.error("respuesta no ok", e.getMessage());
            e.getStackTrace();
            return new ResponseEntity<ArrendatarioResponseRest>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return new ResponseEntity<ArrendatarioResponseRest>(response, HttpStatus.OK);
    }


    @Override
    @Transactional
    public ResponseEntity<ArrendatarioResponseRest> crear(Arrendatario arrendatario) {
        log.info("inicio del método crear()");
        ArrendatarioResponseRest response = new ArrendatarioResponseRest();
        List<Arrendatario> arrendatarios = new ArrayList<>();

        try{
            Arrendatario arrendatarioGuardar = arrendatarioDao.save(arrendatario);
            if(arrendatarioGuardar!=null){
                arrendatarios.add(arrendatarioGuardar);
                response.getArrendatarioResponse().setArrendatarios(arrendatarios);
                response.setMetadata("Respuesta ok", "00", "arrendatario creado");
            }else{
                log.error("Error al crear el arrendatario");
                response.setMetadata("Respuesta no ok", "-1", "arrendatario no creado");
                return new ResponseEntity<ArrendatarioResponseRest>(response, HttpStatus.BAD_REQUEST);
            } 
        }catch (Exception e) {
            log.error("error al crear arrendatario", e.getMessage());
            response.setMetadata("Respuesta no ok", "-1", "error al crear el arrendatario");
            e.getStackTrace();
            return new ResponseEntity<ArrendatarioResponseRest>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return new ResponseEntity<ArrendatarioResponseRest>(response, HttpStatus.OK);
    }


    
    @Override
    @Transactional
    public ResponseEntity<ArrendatarioResponseRest> actualizar(Arrendatario arrendatario, Long id) {
        log.info("inicio del método actualizar()");
        ArrendatarioResponseRest response = new ArrendatarioResponseRest();
        List<Arrendatario> arrendatarios = new ArrayList<>();

        try {
            Optional<Arrendatario> arrendatarioBuscado = arrendatarioDao.findById(id);
            if (arrendatarioBuscado.isPresent()){
                arrendatarioBuscado.get().setApellidos(arrendatario.getApellidos());
                arrendatarioBuscado.get().setContratos(arrendatario.getContratos());
                arrendatarioBuscado.get().setDni(arrendatario.getDni());
                arrendatarioBuscado.get().setEmail(arrendatario.getEmail());
                arrendatarioBuscado.get().setNombre(arrendatario.getNombre());
                arrendatarioBuscado.get().setTelefono(arrendatario.getTelefono());
                arrendatarioBuscado.get().setUsuario(arrendatario.getUsuario());
                Arrendatario arrendatarioActualizar = arrendatarioDao.save(arrendatarioBuscado.get());
                
                if (arrendatarioActualizar != null){
                    arrendatarios.add(arrendatarioActualizar);
                    response.getArrendatarioResponse().setArrendatarios(arrendatarios);
                    response.setMetadata("Respuesta ok", "00", "arrendatario actualizado correctamente");
                }else{
                    log.error("error al actualizar el arrendatario");
                    response.setMetadata("Respuesta no ok", "-1", "arrendatario no actualizado");
                    return new ResponseEntity<ArrendatarioResponseRest>(response, HttpStatus.BAD_REQUEST);
                }
            }else{
                log.error("error al actualizar el arrendatario");
                response.setMetadata("Respuesta no ok", "-1", "arrendatario no actualizado");
                return new ResponseEntity<ArrendatarioResponseRest>(response, HttpStatus.NOT_FOUND);
            }
        } catch (Exception e) {
            log.error("error al actualizar el arrendatario", e.getMessage());
            e.getStackTrace();
            response.setMetadata("Respuesta no ok", "-1", "arrendatario no actualizado");
            return new ResponseEntity<ArrendatarioResponseRest>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return new ResponseEntity<ArrendatarioResponseRest>(response, HttpStatus.OK);
    }


 
    @Override
    @Transactional
    public ResponseEntity<ArrendatarioResponseRest> eliminar(Long id) {
        log.info("inicio del método eliminar()");
        ArrendatarioResponseRest response = new ArrendatarioResponseRest();

        try{
            arrendatarioDao.deleteById(id);
            response.setMetadata("Respuesta ok", "00", "arrendatario eliminado");
        }catch (Exception e){
            log.error("error al eliminar arrendatario", e.getMessage());
            e.getStackTrace();
            response.setMetadata("Respuesta no ok", "-1", "arrendatario no eliminado");
            return new ResponseEntity<ArrendatarioResponseRest>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return new ResponseEntity<ArrendatarioResponseRest>(response, HttpStatus.OK);
    }

}
