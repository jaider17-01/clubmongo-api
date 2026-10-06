package com.uts.clubmongo.model;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.util.List;

/**
 * Cuerpo de POST/PUT de clubes. Las relaciones se envían como ids:
 * los documentos referenciados deben existir antes de crear el club.
 */
public record ClubRequest(
        @NotBlank String nombre,
        @NotNull @Valid Entrenador entrenador,
        String asociacionId,
        List<String> jugadoresIds,
        List<String> competicionesIds) {
}
