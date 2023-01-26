package com.api.parkingcontrol.models;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.UUID;

//Classe responsável pela interação de persistência com o banco de dados

@Entity /*informa que a classe é uma entidade, nesse contexto o JPA entende que deverá criar uma tabela (de mesmo nome) no banco de
dados onde as informações do objeto desse tipo serão persistidas.*/
//Mapeamento de tabelas:
@Table(name = "TB_PARKING_SPOT") //Utilizado caso deseje sobrescrever um nome para a tabela
@Getter /*Cria os getteres automático através da depêndencia Lombok, Obs: eviva a repetição de código, também pode se
utilizar o @Data para criar getteres, setteres, métodos equals, hashcodes, entre outros.*/
@Setter //Cria os setteres através da depêndencia Lombok
public class ParkingSpotModel implements Serializable /* implements Serializable = Permite que um objeto de uma classe
possa ter seu estado persistido(transformar seu estado padrão em uma stream de bytes) por uma API*/{
    private static final long serialVersionUID = 1L; /*O atributo serialVersionUID é o identificador de versão universal
    *para uma classe Serializable, permilte que a serialização ocorra corretamente, obs: 1L significa a limitação de tamanho */

    @Id //permite indicar qual atributo é a chave primaria da tabela (OBS: OBRIGATÓRIO)
    @GeneratedValue(strategy = GenerationType.AUTO) /*serve para identificar como a coluna id será gerada, nesse contexto
    *ela é gerada altomaticamente pelo banco de dados*/
    private UUID id; /*UUID ou Identificador Único Universal é um número de 128 btis que contém 36 caracteres,
    *sendo 32 caracteres alfanuméricos e 4 hifens 8–4–4–4–12.*/
    /*@Column = utilizado para especificar detalhes da coluna, vem com valores defaults e para "editar" os seus campos pode
     utilizar (entre outras) dos seguintes atributos:
    * nullable = utilizado para sobrescrever a nulidade da coluna, ou seja, se pode ser nula (true) ou não pode ser nula(false)
    * unique = utilizado para sobrescreve os valores da coluna, se são únicos (true), se podem ser repetidos (false)
    * length = utilizado para sobrescrever o tamanho da coluna*/
    @Column(nullable = false, unique = true, length = 10)
    private String parkingSpotNumber;
    @Column(nullable = false, unique = true, length = 7)
    private String licensePlateCar;
    @Column(nullable = false, length = 70)
    private String brandCar;
    @Column(nullable = false, length = 70)
    private String modelCar;
    @Column(nullable = false, length = 70)
    private String colorCar;
    @Column(nullable = false)
    private LocalDateTime RegistrationDate;
    @Column(nullable = false, length = 130)
    private String responsibleName;
    @Column(nullable = false, length = 30)
    private String apartment;
    @Column(nullable = false, length = 30)
    private String block;

}
