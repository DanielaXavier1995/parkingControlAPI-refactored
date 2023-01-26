package com.api.parkingcontrol.dtos;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter //Cria os getteres através da depêndencia Lombok
@Setter //Cria os setteres através da depêndencia Lombok
public class ParkingSpotDto {

    @NotBlank //Valida se o campo não está nulo ou vazio
    private String parkingSpotNumber;
    @NotBlank //Valida se o campo não está nulo ou vazio
    @Size(max = 7) //Valida se o campo tem tamanho máximo de 7 caracteres
    private String licensePlateCar;
    @NotBlank
    private String brandCar;
    @NotBlank
    private String modelCar;
    @NotBlank
    private String colorCar;
    @NotBlank
    private String responsibleName;
    @NotBlank
    private String apartment;
    @NotBlank
    private String block;
}
