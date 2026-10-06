package com.uts.clubmongo.model;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.DocumentReference;
import java.util.List;

@Document(collection = "clubes")
@Data @NoArgsConstructor @AllArgsConstructor @Builder
public class Club {
    @Id
    private String id;
    private String nombre;
    private String pais;
    private Integer anoFundacion;
    private String descripcion;
    private String estadio;
    private String colores;

    @DocumentReference(lazy = true)
    private Entrenador entrenador;

    @DocumentReference(lazy = true)
    private List<Jugador> jugadores;

    @DocumentReference(lazy = true)
    private Asociacion asociacion;

    @DocumentReference(lazy = true)
    private List<Competicion> competiciones;
}