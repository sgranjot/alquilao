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

import com.sgranjot.alquilao.backend.alquilao.model.Contrato;
import com.sgranjot.alquilao.backend.alquilao.model.dao.IContratoDao;
import com.sgranjot.alquilao.backend.alquilao.response.ContratoResponseRest;
import com.sgranjot.alquilao.backend.alquilao.service.IContratoService;

@Service
public class ContratoServiceImpl implements IContratoService {

    @Autowired
    private IContratoDao contratoDao;

    private final static Logger log = LoggerFactory.getLogger(ContratoServiceImpl.class);


    @Override
    @Transactional (readOnly = true)
    public ResponseEntity<ContratoResponseRest> buscarContratos() {
        log.info("Inicio del método buscarContratos()");
        ContratoResponseRest response = new ContratoResponseRest();

        try {
            List<Contrato> contratos = (List<Contrato>) contratoDao.findAll();
            response.getContratoResponse().setContratos(contratos);
            response.setMetadata("Respuesta ok", "00", "Respuesta exitosa");
        } catch (Exception e) {
            log.error("Error al buscar contratos", e);
            e.getStackTrace();
            response.setMetadata("Respuesta no ok", "-1", "error al buscar contratos");
            return new ResponseEntity<ContratoResponseRest>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return new ResponseEntity<ContratoResponseRest>(response, HttpStatus.OK);

    }



    @Override
    @Transactional (readOnly = true)
    public ResponseEntity<ContratoResponseRest> buscarPorId(Long id) {
        log.info("Inicio del método buscarPorId()");
        ContratoResponseRest response = new ContratoResponseRest();
        List<Contrato> contratos = new ArrayList<>();

        try {
            Optional<Contrato> contratoOptional = contratoDao.findById(id);
            if (contratoOptional.isPresent()) {
                contratos.add(contratoOptional.get());
                response.getContratoResponse().setContratos(contratos);
                response.setMetadata("Respuesta ok", "00", "respuesta exitosa");
            } else {
                log.error("error al consultar contrato");
                response.setMetadata("Respuesta no ok", "-1", "contrato no encontrado");
                return new ResponseEntity<ContratoResponseRest>(response, HttpStatus.NOT_FOUND);
            }
        } catch (Exception e) {
            log.error("respuesta no ok", e);
            e.getStackTrace();
            response.setMetadata("Respuesta no ok", "-1", "error al consultar el contrato");
            return new ResponseEntity<ContratoResponseRest>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return new ResponseEntity<ContratoResponseRest>(response, HttpStatus.OK);

    }



    @Override
    @Transactional
    public ResponseEntity<ContratoResponseRest> crear(Contrato contrato) {
        log.info("Inicio del método crear()");
        ContratoResponseRest response = new ContratoResponseRest();
        List<Contrato> contratos = new ArrayList<>();

        try {
            Contrato contratoNuevo = contratoDao.save(contrato);
            if (contratoNuevo != null) {
                contratos.add(contratoNuevo);
                response.getContratoResponse().setContratos(contratos);
                response.setMetadata("Respuesta ok", "00", "contrato creado");
            } else {
                log.error("error al crear el contrato");
                response.setMetadata("respuesta no ok", "-1", "no se pudo crear el contrato");
                return new ResponseEntity<ContratoResponseRest>(response, HttpStatus.BAD_REQUEST);
            }
        } catch (Exception e) {
            log.error("Error al crear el contrato", e);
            e.getStackTrace();
            response.setMetadata("Respuesta no ok", "-1", "no se pudo crear el contrato");
            return new ResponseEntity<ContratoResponseRest>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return new ResponseEntity<ContratoResponseRest>(response, HttpStatus.OK);
    }



    @Override
    @Transactional
    public ResponseEntity<ContratoResponseRest> actualizar(Contrato contrato, Long id) {
        log.info("Inicio del método actualizar()");
        ContratoResponseRest response = new ContratoResponseRest();
        List<Contrato> contratos = new ArrayList<>();

        try {
            Optional<Contrato> contratoBuscado = contratoDao.findById(id);
            if (contratoBuscado.isPresent()) {
                contratoBuscado.get().setArrendatario(contrato.getArrendatario());
                contratoBuscado.get().setFechaFin(contrato.getFechaFin());
                contratoBuscado.get().setFechaInicio(contrato.getFechaInicio());
                contratoBuscado.get().setFianza(contrato.getFianza());
                contratoBuscado.get().setMensualidad(contrato.getMensualidad());
                contratoBuscado.get().setMensualidades(contrato.getMensualidades());
                contratoBuscado.get().setPropiedad(contrato.getPropiedad());
                contratoBuscado.get().setUsuario(contrato.getUsuario());
                Contrato contratoActualizar = contratoDao.save(contratoBuscado.get());
                if (contratoActualizar != null) {
                    contratos.add(contratoActualizar);
                    response.getContratoResponse().setContratos(contratos);
                    response.setMetadata("Respuesta ok", "00", "contrato actualizado");
                } else {
                    log.error("Error al actualizar el contrato");
                    response.setMetadata("Respuesta no ok", "-1", "El contrato no se pudo actualizar");
                    return new ResponseEntity<ContratoResponseRest>(response, HttpStatus.BAD_REQUEST);
                }
            } else {
                log.error("Error al actualizar el contrato");
                response.setMetadata("Respuesta no ok", "-1", "El contrato no se pudo actualziar");
                return new ResponseEntity<ContratoResponseRest>(response, HttpStatus.NOT_FOUND);
            }
        } catch (Exception e) {
            log.error("Error al actulizar el contrayto", e);
            e.getStackTrace();
            response.setMetadata("Respuesta no ok", "-1", "El contrato no se pudo actualizar");
            return new ResponseEntity<ContratoResponseRest>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return new ResponseEntity<ContratoResponseRest>(response, HttpStatus.OK);
    }



    @Override
    @Transactional
    public ResponseEntity<ContratoResponseRest> eliminar(Long id) {
        log.info("Inicio del método eliminar()");
        ContratoResponseRest response = new ContratoResponseRest();

        try {
            contratoDao.deleteById(id);
            response.setMetadata("Respuesta ok", "00", "Contrato eliminado");
        } catch (Exception e) {
            log.error("Error al eliminar el contrato", e);
            e.getStackTrace();
            response.setMetadata("Respuesta no ok", "-1", "No se pudo eliminar el contrato");
            return new ResponseEntity<ContratoResponseRest>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return new ResponseEntity<ContratoResponseRest>(response, HttpStatus.OK);
    }

}
