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

import com.sgranjot.alquilao.backend.alquilao.model.Gasto;
import com.sgranjot.alquilao.backend.alquilao.model.dao.IGastoDao;
import com.sgranjot.alquilao.backend.alquilao.response.GastoResponseRest;
import com.sgranjot.alquilao.backend.alquilao.service.IGastoService;

@Service
public class GastoServiceImpl implements IGastoService{

    @Autowired
    private IGastoDao gastoDao;

    private static final Logger log = LoggerFactory.getLogger(GastoServiceImpl.class);


    @Override
    @Transactional (readOnly = true)
    public ResponseEntity<GastoResponseRest> buscarGastos() {
        log.info("Inicio del método buscarGastos");
        GastoResponseRest response = new GastoResponseRest();

        try {
            List<Gasto> gastos = (List<Gasto>) this.gastoDao.findAll();
            response.getGastoResponse().setGastos(gastos);
            response.setMetadata("Respuesta ok", "00", "Gastos encontrados");
        } catch (Exception e) {
            log.error("Error al consultar los gastos", e);
            e.getStackTrace();
            response.setMetadata("Respuesta no ok", "-1", "no se encontraron los gastos");
            return new ResponseEntity<GastoResponseRest>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return new ResponseEntity<GastoResponseRest>(response, HttpStatus.OK);     
     
    }


    @Override
    @Transactional (readOnly = true)
    public ResponseEntity<GastoResponseRest> buscarPorId(Long id) {
        log.info("Inicio del método buscarPorId");
        GastoResponseRest response = new GastoResponseRest();
        List<Gasto> gastos = new ArrayList<>();

        try {
            Optional<Gasto> gastoOptional = this.gastoDao.findById(id);
            if(gastoOptional.isPresent()){
                gastos.add(gastoOptional.get());
                response.getGastoResponse().setGastos(gastos);
                response.setMetadata("Respuesta ok", "00", "Gasto encontrado");
            }else{
                log.error("Error al buscar el gasto");
                response.setMetadata("Respuesta no ok", "-1", "Gasto no encontrado");
                return new ResponseEntity<GastoResponseRest>(response, HttpStatus.NOT_FOUND);
            }
        } catch (Exception e) {
            log.error("Error al buscar el gasto", e);
            e.getStackTrace();
            response.setMetadata("Respuesta no ok", "-1", "No se puedo buscar el gasto");
            return new ResponseEntity<GastoResponseRest>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return new ResponseEntity<GastoResponseRest>(response, HttpStatus.OK);
    }


    @Override
    @Transactional
    public ResponseEntity<GastoResponseRest> crear(Gasto gasto) {
        log.info("Inicio del método crear");
        GastoResponseRest response = new GastoResponseRest();
        List<Gasto> gastos = new ArrayList<>();

        try {
            Gasto gastoNuevo = this.gastoDao.save(gasto);
            if (gastoNuevo != null) {
                gastos.add(gastoNuevo);
                response.getGastoResponse().setGastos(gastos);
                response.setMetadata("Respuesta ok", "00", "Nuevo gasto creado");
            } else {
                log.error("Error al crear nuevo gasto");
                response.setMetadata("Respuesta no ok", "-1", "No se pudo crear el nuevo gasto");
                return new ResponseEntity<GastoResponseRest>(response, HttpStatus.BAD_REQUEST);
            }
        } catch (Exception e) {
            log.error("Error al crear nuevo gasto", e);
            e.getStackTrace();
            response.setMetadata("Respuesta no ok", "-1", "No se pudo crear el nuevo gasto");
            return new ResponseEntity<GastoResponseRest>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return new ResponseEntity<GastoResponseRest>(response, HttpStatus.OK);
    }


    @Override
    @Transactional
    public ResponseEntity<GastoResponseRest> actualizar(Gasto gasto, Long id) {
        log.info("Inicio del método actualizar");
        GastoResponseRest response = new GastoResponseRest();
        List<Gasto> gastos = new ArrayList<>();

        try {
            Optional<Gasto> gastoOptional = this.gastoDao.findById(id);
            if (gastoOptional.isPresent()){
                gastoOptional.get().setDescripcion(gasto.getDescripcion());
                gastoOptional.get().setFecha(gasto.getFecha());
                gastoOptional.get().setImporte(gasto.getImporte());
                gastoOptional.get().setPropiedad(gasto.getPropiedad());
                gastoOptional.get().setTipo(gasto.getTipo());
                gastoOptional.get().setUsuario(gasto.getUsuario());
                Gasto gastoActualizar = gastoDao.save(gastoOptional.get());

                if (gastoActualizar != null) {
                    gastos.add(gastoActualizar);
                    response.getGastoResponse().setGastos(gastos);
                    response.setMetadata("Respuesta ok", "00", "Gasto actualizado");
                } else {
                    log.error("Error al actualizar el gasto");
                    response.setMetadata("Respuesta no ok", "-1", "No se puedo actualizar el gasto");
                    return new ResponseEntity<GastoResponseRest>(response, HttpStatus.BAD_REQUEST);
                }
            } else {
                log.error("Error al actualizar el gasto");
                response.setMetadata("Respuesta no ok", "-1", "No se encontró el gasto a actualizar");
                return new ResponseEntity<GastoResponseRest>(response, HttpStatus.NOT_FOUND);
            }
        } catch (Exception e) {
            log.error("Error al actualizar el gasto", e);
            e.getStackTrace();
            response.setMetadata("Respuesta no ok", "-1", "No se pudo actualizar el gasto");
            return new ResponseEntity<GastoResponseRest>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return new ResponseEntity<GastoResponseRest>(response, HttpStatus.OK);
    }


    @Override
    @Transactional
    public ResponseEntity<GastoResponseRest> eliminar(Long id) {
        log.info("Inicio del método eliminar");
        GastoResponseRest response = new GastoResponseRest();

        try {
            this.gastoDao.deleteById(id);
            response.setMetadata("Respuesta ok", "00", "Gasto eliminado");
        } catch (Exception e) {
            log.error("Error al eliminar el gasto", e);
            e.getStackTrace();
            response.setMetadata("Respuesta no ok", "-1", "gasto no eliminado");
            return new ResponseEntity<GastoResponseRest>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return new ResponseEntity<GastoResponseRest>(response, HttpStatus.OK);
        
    }
    
}
