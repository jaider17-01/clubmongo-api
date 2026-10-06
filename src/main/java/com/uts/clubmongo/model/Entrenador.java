package com.uts.clubmongo.model;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "entrenadores")
@Data @NoArgsConstructor @AllArgsConstructor @Builder
public class Entrenador {
    @Id
    private String id;
    private String nombre;
    private String apellido;
    private Integer edad;
    private String nacionalidad;
}