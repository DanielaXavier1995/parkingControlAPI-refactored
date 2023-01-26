package com.api.parkingcontrol.services;

import com.api.parkingcontrol.models.ParkingSpotModel;
import com.api.parkingcontrol.repositories.ParkingSpotRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

//Classes onde ficam armazenadas as regras de negócio e faz a comunicação com a camada de model
@Service
public class ParkingSpotService {
    @Autowired
    ParkingSpotRepository parkingSpotRepository;
     /*Ponto de injeção da camada de repositories na Service, pois essa camada precisa da comunicação com o
    parkingSpotRepository para herdar os métodos prontos do JPA*/

    @Transactional //Utilizado acima de métodos construtios e destrutivos
    public ParkingSpotModel save(ParkingSpotModel parkingSpotModel) {
        return parkingSpotRepository.save(parkingSpotModel);
    }

    //Métodos customizados e declarado no ParkingSpotRepository:
    public boolean existsByLicensePlateCar(String licensePlateCar) {
        //valida se a placa que o usuário deseja salvar já foi salva comparando com as validações realizadas no service
        return parkingSpotRepository.existsByLicensePlateCar(licensePlateCar);
    }
    public boolean existsByParkingSpotNumber(String parkingSpotNumber) {
        return parkingSpotRepository.existsByParkingSpotNumber(parkingSpotNumber);
    }

    public boolean existsByApartmentAndBlock(String apartment, String block) {
        return parkingSpotRepository.existsByApartmentAndBlock(apartment, block);
    }

    public List<ParkingSpotModel> getALL() {
        return parkingSpotRepository.findAll();
    }

    public ParkingSpotModel findById(UUID id) {

        var parkingSpot = parkingSpotRepository.findById(id);
        if(parkingSpot.isEmpty()){
            throw new RuntimeException("Parking Spot not found");
        }
        return parkingSpot.get();
    }
    @Transactional
    public void delete(ParkingSpotModel parkingSpotModel) {
        parkingSpotRepository.delete(parkingSpotModel);
    }

}
