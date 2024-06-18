package com.example.practicafrancisco;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;


//en esta clase tienes que inicializar el @data y los otros dos y
 //poner los parametros que tienen los parametros que use la clase coche


@Data
@AllArgsConstructor
@NoArgsConstructor

public class Coche {
   private Integer id;
    private Date entrega;
    private String matricula;
    private Date salida;
    private Integer coste;
    private String tarifa;
    private String modelo;
    private Cliente cliente;


    private Coche(int id){
        this.id=id;
    }

}
