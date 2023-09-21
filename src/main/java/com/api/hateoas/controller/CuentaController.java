package com.api.hateoas.controller;

import com.api.hateoas.model.Cuenta;
import com.api.hateoas.model.Monto;
import com.api.hateoas.service.CuentaService;
import org.springframework.beans.factory.annotation.Autowired;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.IanaLinkRelations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/cuentas")
public class CuentaController {

    @Autowired
    private CuentaService cuentaService;

    @GetMapping
    public ResponseEntity<List<Cuenta>> listarCuentas(){
        List<Cuenta> cuentas = cuentaService.listAll();

        if(cuentas.isEmpty()){
            return ResponseEntity.noContent().build();
        }

        for(Cuenta cuenta : cuentas){
            cuenta.add(linkTo(methodOn(CuentaController.class).listarCuenta(cuenta.getId())).withSelfRel());
            cuenta.add(linkTo(methodOn(CuentaController.class).depositarDinero(cuenta.getId(), null)).withRel("depositos"));
            cuenta.add(linkTo(methodOn(CuentaController.class).listarCuentas()).withRel(IanaLinkRelations.COLLECTION));
        }

        CollectionModel<Cuenta> modelo = CollectionModel.of(cuentas);
        modelo.add(linkTo(methodOn(CuentaController.class).listarCuentas()).withSelfRel());

        return new ResponseEntity<>(cuentas, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Cuenta> listarCuenta(@PathVariable Integer id){
        try {
            Cuenta cuenta = cuentaService.get(id);
            cuenta.add(linkTo(methodOn(CuentaController.class).listarCuenta(cuenta.getId())).withSelfRel());
            cuenta.add(linkTo(methodOn(CuentaController.class).depositarDinero(cuenta.getId(), null)).withRel("depositos"));
            cuenta.add(linkTo(methodOn(CuentaController.class).listarCuentas()).withRel(IanaLinkRelations.COLLECTION));
            return new ResponseEntity<>(cuenta, HttpStatus.OK);
        }catch (Exception exception){
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping
    public ResponseEntity<Cuenta> guardarCuenta(@RequestBody Cuenta cuenta){
        Cuenta newCuenta = cuentaService.save(cuenta);

        newCuenta.add(linkTo(methodOn(CuentaController.class).listarCuenta(newCuenta.getId())).withSelfRel());
        newCuenta.add(linkTo(methodOn(CuentaController.class).depositarDinero(newCuenta.getId(), null)).withRel("depositos"));
        newCuenta.add(linkTo(methodOn(CuentaController.class).listarCuentas()).withRel(IanaLinkRelations.COLLECTION));

        return ResponseEntity.created(linkTo(methodOn(CuentaController.class).listarCuenta(newCuenta.getId())).toUri()).body(newCuenta);
    }

    @PutMapping
    public ResponseEntity<Cuenta> editarCuenta(@RequestBody Cuenta cuenta){
        Cuenta newCuenta = cuentaService.save(cuenta);

        newCuenta.add(linkTo(methodOn(CuentaController.class).listarCuenta(newCuenta.getId())).withSelfRel());
        newCuenta.add(linkTo(methodOn(CuentaController.class).depositarDinero(newCuenta.getId(), null)).withRel("depositos"));
        newCuenta.add(linkTo(methodOn(CuentaController.class).listarCuentas()).withRel(IanaLinkRelations.COLLECTION));

        return new ResponseEntity<>(cuenta, HttpStatus.OK);
    }

    @PatchMapping("/{id}/deposito")
    public ResponseEntity<Cuenta> depositarDinero(@PathVariable Integer id, @RequestBody Monto monto){
        Cuenta newCuenta = cuentaService.depositar(monto.getMonto(), id);

        newCuenta.add(linkTo(methodOn(CuentaController.class).listarCuenta(newCuenta.getId())).withSelfRel());
        newCuenta.add(linkTo(methodOn(CuentaController.class).depositarDinero(newCuenta.getId(), null)).withRel("depositos"));
        newCuenta.add(linkTo(methodOn(CuentaController.class).listarCuentas()).withRel(IanaLinkRelations.COLLECTION));

        return new ResponseEntity<>(newCuenta, HttpStatus.OK);

    }

    @PatchMapping("/{id}/retiro")
    public ResponseEntity<Cuenta> retirarDinero(@PathVariable Integer id, @RequestBody Monto monto){
        Cuenta newCuenta = cuentaService.retirar(monto.getMonto(), id);

        newCuenta.add(linkTo(methodOn(CuentaController.class).listarCuenta(newCuenta.getId())).withSelfRel());
        newCuenta.add(linkTo(methodOn(CuentaController.class).depositarDinero(newCuenta.getId(), null)).withRel("depositos"));
        newCuenta.add(linkTo(methodOn(CuentaController.class).retirarDinero(newCuenta.getId(), null)).withRel("retiros"));
        newCuenta.add(linkTo(methodOn(CuentaController.class).listarCuentas()).withRel(IanaLinkRelations.COLLECTION));

        return new ResponseEntity<>(newCuenta, HttpStatus.OK);

    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> eliminarCuenta(@PathVariable Integer id){
        try {
            cuentaService.delete(id);
            return ResponseEntity.noContent().build();
        }catch (Exception exception){
            return ResponseEntity.notFound().build();
        }
    }


}
