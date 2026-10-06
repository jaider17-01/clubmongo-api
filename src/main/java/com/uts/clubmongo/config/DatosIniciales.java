package com.uts.clubmongo.config;

import com.uts.clubmongo.model.*;
import com.uts.clubmongo.repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

@Component
@RequiredArgsConstructor
public class DatosIniciales implements CommandLineRunner {

    private final ClubRepository clubRepo;
    private final EntrenadorRepository entRepo;
    private final JugadorRepository jugRepo;
    private final AsociacionRepository asocRepo;
    private final CompeticionRepository compRepo;

    @Override
    public void run(String... args) {
        // No borrar datos existentes al reiniciar o desplegar la aplicación.
        if (clubRepo.count() > 0) {
            System.out.println("Base de datos existente detectada; se conservan sus datos.");
            return;
        }
        System.out.println("Base de datos vacía. Cargando datos de ejemplo...");

        Asociacion fcf = asocRepo.save(Asociacion.builder().nombre("Federación Colombiana de Fútbol").pais("Colombia").presidente("Ramón Jesurún").build());
        Asociacion fa = asocRepo.save(Asociacion.builder().nombre("The Football Association").pais("Inglaterra").presidente("Debbie Hewitt").build());
        Asociacion rfef = asocRepo.save(Asociacion.builder().nombre("Real Federación Española").pais("España").presidente("Pedro Rocha").build());
        Asociacion figc = asocRepo.save(Asociacion.builder().nombre("Federación Italiana").pais("Italia").presidente("Gabriele Gravina").build());
        Asociacion dfb = asocRepo.save(Asociacion.builder().nombre("Federación Alemana").pais("Alemania").presidente("Bernd Neuendorf").build());
        Asociacion fff = asocRepo.save(Asociacion.builder().nombre("Federación Francesa").pais("Francia").presidente("Philippe Diallo").build());
        Asociacion cbf = asocRepo.save(Asociacion.builder().nombre("Confederación Brasileña").pais("Brasil").presidente("Ednaldo Rodrigues").build());
        Asociacion afa = asocRepo.save(Asociacion.builder().nombre("Asociación del Fútbol Argentino").pais("Argentina").presidente("Claudio Tapia").build());
        Asociacion fmf = asocRepo.save(Asociacion.builder().nombre("Federación Mexicana").pais("México").presidente("Yon de Luisa").build());

        Competicion ucl = compRepo.save(Competicion.builder().nombre("UEFA Champions League").montoPremio(200000000.0).fechaInicio(LocalDate.of(2024, 9, 17)).fechaFin(LocalDate.of(2025, 5, 31)).build());
        Competicion premier = compRepo.save(Competicion.builder().nombre("Premier League").montoPremio(400000000.0).fechaInicio(LocalDate.of(2024, 8, 16)).fechaFin(LocalDate.of(2025, 5, 25)).build());
        Competicion laliga = compRepo.save(Competicion.builder().nombre("La Liga").montoPremio(150000000.0).fechaInicio(LocalDate.of(2024, 8, 15)).fechaFin(LocalDate.of(2025, 5, 25)).build());
        Competicion libertadores = compRepo.save(Competicion.builder().nombre("Copa Libertadores").montoPremio(23000000.0).fechaInicio(LocalDate.of(2024, 2, 6)).fechaFin(LocalDate.of(2024, 11, 30)).build());
        Competicion betplay = compRepo.save(Competicion.builder().nombre("Liga BetPlay Dimayor").montoPremio(5000000.0).fechaInicio(LocalDate.of(2024, 1, 25)).fechaFin(LocalDate.of(2024, 12, 22)).build());
        Competicion ligamx = compRepo.save(Competicion.builder().nombre("Liga MX").montoPremio(8000000.0).fechaInicio(LocalDate.of(2024, 7, 5)).fechaFin(LocalDate.of(2024, 12, 15)).build());

        Object[][] clubesData = {
            {"Manchester City", "Inglaterra", 1880, "El campeón dominante de la era Guardiola.", "Etihad Stadium", "Celeste y Blanco", new String[]{"Pep", "Guardiola", "53", "Española"}, fa, premier},
            {"Arsenal", "Inglaterra", 1886, "El club del norte de Londres, famoso por su fútbol atractivo.", "Emirates Stadium", "Rojo y Blanco", new String[]{"Mikel", "Arteta", "42", "Española"}, fa, premier},
            {"Liverpool", "Inglaterra", 1892, "Los Reds, con una historia europea gloriosa.", "Anfield", "Rojo", new String[]{"Arne", "Slot", "45", "Holandesa"}, fa, premier},
            {"Real Madrid", "España", 1902, "El rey de Europa, con una historia inigualable.", "Santiago Bernabéu", "Blanco", new String[]{"Carlo", "Ancelotti", "65", "Italiana"}, rfef, laliga},
            {"Barcelona", "España", 1899, "Más que un club, cuna del tiki-taka.", "Spotify Camp Nou", "Grana y Azul", new String[]{"Hansi", "Flick", "59", "Alemana"}, rfef, laliga},
            {"Atletico Madrid", "España", 1903, "Conocidos por su solidez defensiva y espíritu de lucha.", "Cívitas Metropolitano", "Rojo y Blanco", new String[]{"Diego", "Simeone", "54", "Argentina"}, rfef, laliga},
            {"Inter Milan", "Italia", 1908, "El club más exitoso de Italia en la última década.", "San Siro", "Azul y Negro", new String[]{"Simone", "Inzaghi", "48", "Italiana"}, figc, ucl},
            {"Juventus", "Italia", 1897, "La Vecchia Signora, dominadora histórica.", "Allianz Stadium", "Blanco y Negro", new String[]{"Thiago", "Motta", "42", "Italiana"}, figc, ucl},
            {"Bayern Munich", "Alemania", 1900, "El gigante bávaro, sinónimo de eficiencia.", "Allianz Arena", "Rojo", new String[]{"Vincent", "Kompany", "38", "Belga"}, dfb, ucl},
            {"Borussia Dortmund", "Alemania", 1909, "Famosos por su muro amarillo.", "Signal Iduna Park", "Amarillo y Negro", new String[]{"Nuri", "Sahin", "36", "Turca"}, dfb, ucl},
            {"Paris Saint-Germain", "Francia", 1970, "El club de la capital francesa.", "Parc des Princes", "Azul y Rojo", new String[]{"Luis", "Enrique", "54", "Española"}, fff, ucl},
            {"Millonarios", "Colombia", 1946, "El embajador, uno de los más tradicionales de Colombia.", "El Campín", "Azul", new String[]{"Alberto", "Gamero", "58", "Colombiana"}, fcf, betplay},
            {"Atletico Nacional", "Colombia", 1947, "El rey de copas colombiano.", "Atanasio Girardot", "Verde", new String[]{"Pablo", "Larriera", "50", "Uruguaya"}, fcf, betplay},
            {"Flamengo", "Brasil", 1895, "El club con la mayor hinchada del mundo.", "Maracaná", "Rojo y Negro", new String[]{"Filipe", "Luis", "39", "Brasileña"}, cbf, libertadores},
            {"River Plate", "Argentina", 1901, "El millonario, famoso por su estilo de juego.", "Estadio Más Monumental", "Blanco con banda roja", new String[]{"Martin", "Demichelis", "43", "Argentina"}, afa, libertadores},
            {"Boca Juniors", "Argentina", 1905, "La mitad más uno, con una pasión inigualable.", "La Bombonera", "Azul y Oro", new String[]{"Diego", "Martinez", "43", "Argentina"}, afa, libertadores},
            {"Club America", "Mexico", 1916, "Las águilas, el equipo más ganador de México.", "Estadio Azteca", "Azulcrema", new String[]{"Andre", "Jardine", "45", "Brasileña"}, fmf, ligamx},
            {"Chivas", "Mexico", 1906, "El rebaño sagrado, orgulloso de su tradición.", "Estadio Akron", "Rojo y Blanco", new String[]{"Omar", "Arellano", "38", "Mexicana"}, fmf, ligamx}
        };

        Random rand = new Random();
        String[] nombres = {"Carlos", "Luis", "Juan", "Pedro", "Mateo", "Santiago", "David", "Jorge", "Miguel", "Andres", "Lionel", "Kylian", "Erling", "Vinicius", "Jude", "Robert", "Mohamed", "Harry", "Kevin", "Bruno"};
        String[] apellidos = {"Gomez", "Rodriguez", "Martinez", "Lopez", "Perez", "Silva", "Santos", "Garcia", "Fernandez", "Smith", "Mbappe", "Haaland", "Bellingham", "Salah", "Kane", "De Bruyne", "Fernandes"};
        String[] posiciones = {"Portero", "Defensa", "Defensa", "Mediocampista", "Mediocampista", "Mediocampista", "Delantero", "Delantero"};

        int clubCount = 0;
        for (Object[] d : clubesData) {
            guardarClubCompleto(d, rand, nombres, apellidos, posiciones);
            clubCount++;
        }

        String[] paisesExtra = {"Inglaterra", "España", "Italia", "Alemania", "Colombia", "Argentina", "Brasil", "Mexico"};
        String[] nombresExtra = {"United", "City", "FC", "Sporting", "Atlético", "Real", "Deportivo", "Estrella"};
        
        for (int i = 0; i < 32; i++) {
            String nombreClub = "Club " + nombresExtra[rand.nextInt(nombresExtra.length)] + " " + (i + 19);
            String pais = paisesExtra[rand.nextInt(paisesExtra.length)];
            Object[] clubGenerico = {
                nombreClub, pais, 1900 + rand.nextInt(120), 
                "Un club con gran tradición y una hinchada fiel en su región.", 
                "Estadio Municipal " + (i+1), "Blanco y Negro",
                new String[]{"Director", "Tecnico", String.valueOf(40 + rand.nextInt(20)), "Local"},
                fa, premier
            };
            guardarClubCompleto(clubGenerico, rand, nombres, apellidos, posiciones);
            clubCount++;
        }

        System.out.println("✅ ¡Base de datos cargada con " + clubCount + " clubes y ~" + (clubCount * 15) + " jugadores!");
    }

    private void guardarClubCompleto(Object[] d, Random rand, String[] nombres, String[] apellidos, String[] posiciones) {
        Entrenador ent = entRepo.save(Entrenador.builder()
            .nombre((String) ((String[]) d[6])[0])
            .apellido((String) ((String[]) d[6])[1])
            .edad(Integer.parseInt(((String[]) d[6])[2]))
            .nacionalidad((String) ((String[]) d[6])[3])
            .build());

        List<Jugador> plantilla = new ArrayList<>();
        for (int i = 1; i <= 15; i++) {
            plantilla.add(jugRepo.save(Jugador.builder()
                .nombre(nombres[rand.nextInt(nombres.length)])
                .apellido(apellidos[rand.nextInt(apellidos.length)])
                .numero(i)
                .posicion(posiciones[rand.nextInt(posiciones.length)])
                .valorMercado(5.0 + (rand.nextDouble() * 100))
                .fechaNacimiento(LocalDate.of(1990 + rand.nextInt(15), 1 + rand.nextInt(12), 1 + rand.nextInt(28)))
                .build()));
        }

        Club club = Club.builder()
            .nombre((String) d[0])
            .pais((String) d[1])
            .anoFundacion((Integer) d[2])
            .descripcion((String) d[3])
            .estadio((String) d[4])
            .colores((String) d[5])
            .entrenador(ent)
            .jugadores(plantilla)
            .asociacion((Asociacion) d[7])
            .competiciones(List.of((Competicion) d[8]))
            .build();
            
        clubRepo.save(club);
    }
}
