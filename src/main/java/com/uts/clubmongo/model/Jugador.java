package com.uts.clubmongo.model;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import java.time.LocalDate;

@Document(collection = "jugadores")
@Data @NoArgsConstructor @AllArgsConstructor @Builder
public class Jugador {
    @Id
    private String id;
    private String nombre;
    private String apellido;
    private Integer numero;
    private String posicion;
    private Double valorMercado; // En millones de USD
    private LocalDate fechaNacimiento;
}