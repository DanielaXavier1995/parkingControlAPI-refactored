package com.api.parkingcontrol.services;

import com.api.parkingcontrol.dtos.ParkingSpotDto;
import com.api.parkingcontrol.models.ParkingSpotModel;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.UUID;

@Service
public class ParkingSpotValidationService {
    @Autowired //Anotação que indica a criação de uma instância do Service dentro do Controller
    ParkingSpotService parkingSpotService;


    public void validate(ParkingSpotDto parkingSpotDto){
        //Validações:
        if (parkingSpotService.existsByLicensePlateCar(parkingSpotDto.getLicensePlateCar())) {
            throw new RuntimeException("Conflict: License Plate Car is already in use!");
        }
        if (parkingSpotService.existsByParkingSpotNumber(parkingSpotDto.getParkingSpotNumber())) {
            throw new RuntimeException("Conflict: Parking Spot is already in use!");
        }
        if (parkingSpotService.existsByApartmentAndBlock(parkingSpotDto.getApartment(), parkingSpotDto.getBlock())) {
            throw new RuntimeException("Conflict: Parking Spot is already registered for this apartment/block!");
        }
    }
    public ParkingSpotModel createParkingSpot(ParkingSpotDto parkingSpotDto){
        var parkingSpotModel = new ParkingSpotModel();
        BeanUtils.copyProperties(parkingSpotDto, parkingSpotModel);
        parkingSpotModel.setRegistrationDate(LocalDateTime.now(ZoneId.of("UTC")));
        return parkingSpotService.save(parkingSpotModel);
    }

    public ParkingSpotModel updateParkingSpot(@PathVariable(value = "id") UUID id,ParkingSpotDto parkingSpotDto) {
        ParkingSpotModel parkingSpotModel = parkingSpotService.findById(id);

        //1° Opção:
        parkingSpotModel.setLicensePlateCar(parkingSpotDto.getLicensePlateCar());
        parkingSpotModel.setParkingSpotNumber(parkingSpotDto.getParkingSpotNumber());
        parkingSpotModel.setBrandCar(parkingSpotDto.getBrandCar());
        parkingSpotModel.setModelCar(parkingSpotDto.getModelCar());
        parkingSpotModel.setColorCar(parkingSpotDto.getColorCar());
        parkingSpotModel.setResponsibleName(parkingSpotDto.getResponsibleName());
        parkingSpotModel.setApartment(parkingSpotDto.getApartment());
        parkingSpotModel.setBlock(parkingSpotDto.getBlock());

        //2° Opção:
        /*parkingSpotModel.setId(parkingSpotModel.getId());
        parkingSpotModel.setRegistrationDate(parkingSpotModel.getRegistrationDate());*/

        return parkingSpotService.save(parkingSpotModel);
    }
}
