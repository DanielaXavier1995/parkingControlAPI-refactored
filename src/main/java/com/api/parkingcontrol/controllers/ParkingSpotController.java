package com.api.parkingcontrol.controllers;

import com.api.parkingcontrol.dtos.ParkingSpotDto;
import com.api.parkingcontrol.models.ParkingSpotModel;
import com.api.parkingcontrol.services.ParkingSpotService;
import com.api.parkingcontrol.services.ParkingSpotValidationService;
import jakarta.validation.Valid;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

//Camada responsável pela comunicação entre o Model e o Service
@RestController //Permite requisições específicas como com o protocolo HTTP
@CrossOrigin(origins = "*", maxAge = 3600)
//permite todas as origens e os métodos HTTP especificados na @RequestMappinganotação
//maxAge = 3600 = é um atributo do CROS e indica que todas as repostas serão armazenadas em cache por 60 min.
@RequestMapping("/parking-spot")/*Usada para mapear solicitações web para classes e/ou métodos manipuladores, quando
utilizada a nível de classe cria uma URI base, pela qual todos os métodos do controller serão usados*/
public class ParkingSpotController {

    @Autowired //Anotação que indica a criação de uma instância do Service dentro do Controller
    ParkingSpotService parkingSpotService;
    @Autowired //Anotação que indica a criação de uma instância do Service dentro do Controller
    ParkingSpotValidationService parkingSpotValidationService;

    /*Injeção da classe de serviço para que o controller possa realizar as requisiçoes necessárias*/

    @PostMapping//Lida com solicitações HTTP POST (salvar)
    //ResponseEntity<Object> elemento de resposta que retorna um objeto com status e corpo(body)
    /* @RequestBody = para indicar ao Spring que um recurso não será enviado ou recebido por meio de uma
     página da Web, permite o envio/recebimento de dados em formato JSON por meio do POSTMAN por exemplo. */
    //@Valid = utilizado para que as validações do ParkingSpotDto possa ser utilizadas

    public ResponseEntity<Object> saveParkingSpot(@RequestBody @Valid ParkingSpotDto parkingSpotDto) {
        try {
            //Validação:
            parkingSpotValidationService.validate(parkingSpotDto);
            //criação:
            var response = parkingSpotValidationService.createParkingSpot(parkingSpotDto);
            return ResponseEntity.status(HttpStatus.CREATED).body(response);
        } catch (RuntimeException runtimeException) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(runtimeException.getMessage());
        }
    }

    @GetMapping//Lida com solicitações HTTP GET (Retornar tudo)
    public ResponseEntity<List<ParkingSpotModel>> getAllParkingSpots() {
        //O método getALL tem como retorno um ResponseEntity que é uma lista do ParkingSpotModel
        return ResponseEntity.status(HttpStatus.OK).body(parkingSpotService.getALL());
         //O corpo dessa resposta chama a camada o método findALL() na camada de serviço;
    }

    @GetMapping("/{id}")//Lida com solicitações HTTP GET (Retornar um objeto pelo id)
    //@PathVariable(value = "id") = anotação utilizada para adicionar o id a URI definida no @RequestMapping
    public ResponseEntity<Object> getOneParkingSpot(@PathVariable(value = "id") UUID id) {

        try {
            ParkingSpotModel parkingSpotModel = parkingSpotService.findById(id);
            return ResponseEntity.status(HttpStatus.OK).body(parkingSpotModel);
        } catch (RuntimeException runtimeException) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(runtimeException.getMessage());
        }
    }

    @DeleteMapping("/{id}")//Lida com solicitações HTTP DELETE (deletar)
    public ResponseEntity<Object> deleteParkingSpot(@PathVariable(value = "id") UUID id) {
        try {
            ParkingSpotModel parkingSpotModel = parkingSpotService.findById(id);
            parkingSpotService.delete(parkingSpotModel);
            return ResponseEntity.status(HttpStatus.OK).body("Parking Spot deleted successfully.");
        } catch (RuntimeException runtimeException) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Parking Spot not found");
        }
    }

    @PutMapping("/{id}")//Lida com solicitações HTTP PUT (atualizar)
    public ResponseEntity<Object> updateParkingSpot(@PathVariable(value = "id") UUID id,
                                                    @RequestBody @Valid ParkingSpotDto parkingSpotDto) {

        try {
            var response = parkingSpotValidationService.updateParkingSpot(id, parkingSpotDto);
            return ResponseEntity.status(HttpStatus.OK).body(response);

        } catch (RuntimeException runtimeException) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(runtimeException.getMessage());
        }
    }
}

