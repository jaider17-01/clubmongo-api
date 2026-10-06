package com.uts.clubmongo.model;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import java.time.LocalDate;

@Document(collection = "competiciones")
@Data @NoArgsConstructor @AllArgsConstructor @Builder
public class Competicion {
    @Id
    private String id;
    private String nombre;
    private Double montoPremio;
    private LocalDate fechaInicio;
    private LocalDate fechaFin;
}