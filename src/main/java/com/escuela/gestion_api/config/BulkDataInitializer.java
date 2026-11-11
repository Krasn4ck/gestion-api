package com.escuela.gestion_api.config;

import com.escuela.gestion_api.dto.*;
import com.escuela.gestion_api.models.EstatusPeriodo;
import com.escuela.gestion_api.models.NivelEducativo;

import com.escuela.gestion_api.repository.*;
import com.escuela.gestion_api.service.*;
import lombok.RequiredArgsConstructor;
import net.datafaker.Faker;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.dao.DataIntegrityViolationException;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Locale;

//@Configuration
@RequiredArgsConstructor
public class BulkDataInitializer {
    private final CarreraService carreraService;
    private final ProfesorService profesorService;
    private final MateriaService materiaService;
    private final PeriodoLectivoService periodoLectivoService;
    private final GrupoService grupoService;
    private final AlumnoService alumnoService;
    private final InscripcionService inscripcionService;

    private final AlumnoRepository alumnoRepository;
    private final CarreraRepository carreraRepository;
    private final MateriaRepository materiaRepository;
    @Bean
    public CommandLineRunner generarDatosMasivos(){
        return args -> {
            if(alumnoRepository.count()<10) {
                Long startTime = System.currentTimeMillis();
                Faker faker = new Faker(Locale.of("es", "MX"));
                //Agregando carreras
                /*
                if(carreraRepository.count()==0) {
                    String[][] datosCarreras = {
                            {"ISC", "Ingeniería en Sistemas Computacionales"},
                            {"ADM", "Administración de Empresas"},
                            {"DER", "Derecho"},
                            {"CP", "Contaduría Pública"},
                            {"ARQ", "Arquitectura"}
                    };
                    for (String[] datos : datosCarreras) {
                        var dto = new CarreraRequestsDTO(datos[0], datos[1], NivelEducativo.UNIVERSIDAD);
                        carreraService.crearCarrera(dto);
                    }
                }
                //List<CarreraResponseDTO> carreras = carreraService.obtenerTodas();

                 */
                // Agregar profesores
                List<ProfesorResponseDTO> profesores = new ArrayList<>();
                for (int i = 0; i < 50; i++) {
                    // Generar un cubículo realista con Faker
                    String cubiculoAleatorio = "Edif. " +
                            faker.options().option("A", "B", "C", "D") +
                            " - " +
                            faker.number().numberBetween(101, 310); // Ej: Edif. B - 204
                    var dto = new ProfesorRequestDTO(
                            faker.name().firstName(),
                            faker.name().lastName(),
                            faker.name().lastName(),
                            cubiculoAleatorio
                    );
                    profesores.add(profesorService.crearProfesor(dto));

                }
                //Agregando materias
                // 1. Definimos una lista fija de materias reales y limpias
                List<String> nombresMaterias = List.of(
                        "Calculo Diferencial",
                        "Programacion Orientada a Objetos",
                        "Bases de Datos Avanzadas",
                        "Estructuras de Datos",
                        "Arquitectura de Computadoras",
                        "Redes de Computadoras",
                        "Sistemas Operativos",
                        "Ingenieria de Software",
                        "Matematicas Discretas",
                        "Inteligencia Artificial"
                );

                List<MateriaResponseDTO> materias = new ArrayList<>();
                // 2. Iteramos sobre nuestra lista fija en lugar del Faker
                for (String nombre : nombresMaterias) {
                    var dto = new MateriaRequestDTO(
                            nombre,
                            // Agrega aquí los demás campos requeridos por tu DTO de materia (créditos, horas, etc.)
                            5 // ejemplo de créditos
                    );

                    // Usamos tu servicio con la lógica de "si ya existe, la recupera; si no, la crea"
                    MateriaResponseDTO materiaDto = materiaService.crearMateria(dto);
                    materias.add(materiaDto);
                }                //Agregando periodos lectivos
                PeriodoLectivoResponseDTO periodo = periodoLectivoService.crearPeriodo(
                        new PeriodoLectivoRequestsDTO(
                                "Semestre 2026-B",
                                2026,
                                LocalDate.of(2026, 8, 1),
                                LocalDate.of(2026, 12, 15),
                                LocalDate.of(2026, 8, 10),
                                EstatusPeriodo.ACTIVO)
                );

                //Agregando Grupos
                // 1. Barajamos las materias aleatoriamente para que el orden sea distinto cada vez
                List<MateriaResponseDTO> materiasDisponibles = new ArrayList<>(materias);
                Collections.shuffle(materiasDisponibles);

                List<GrupoResponseDTO> grupos = new ArrayList<>();
                // 2. Iteramos hasta 20 o hasta el tamaño máximo de materias disponibles
                int totalAGenerar = Math.min(20, materiasDisponibles.size());

                for (int i = 0; i < totalAGenerar; i++) {
                    MateriaResponseDTO materiaAleatoria = materiasDisponibles.get(i); // Sin riesgo de repetir
                    ProfesorResponseDTO profesorAleatorio = profesores.get(faker.number().numberBetween(0, profesores.size())); // (Ojo: usa size sin -1 si quieres incluir el límite superior de forma segura)

                    var dto = new GrupoRequestDTO(
                            "Aula" + faker.number().numberBetween(200, 225),
                            35,
                            materiaAleatoria.id(),
                            profesorAleatorio.id(),
                            periodo.id()
                    );
                    grupos.add(grupoService.crearGrupo(dto));
                }

                //Agregar Alumnos a la base de datos
                List<AlumnoResponseDTO> alumnos = new ArrayList<>();
                for (int i = 0; i < 200; i++) {
                    LocalDate fechaNaciemientoFaker = LocalDate.of(
                            faker.number().numberBetween(2000, 2012),
                            faker.number().numberBetween(1, 12),
                            faker.number().numberBetween(1, 28)
                    );
                    var dto = new AlumnoRequestsDTO(
                            faker.name().firstName(),
                            faker.name().lastName(),
                            faker.name().nameWithMiddle(),
                            faker.internet().emailAddress(),
                            fechaNaciemientoFaker,
                            NivelEducativo.UNIVERSIDAD
                    );
                    alumnos.add(alumnoService.guardar(dto));
                }
                BigDecimal precioFijo = new BigDecimal("2500.00");
                for (int i = 0; i < 2000; i++) {
                    AlumnoResponseDTO alumnoAleatorio = alumnos.get(faker.number().numberBetween(0, alumnos.size() - 1));
                    GrupoResponseDTO grupoAleatorio = grupos.get(faker.number().numberBetween(0, grupos.size() - 1));

                    try {
                        var dto = new InscripcionResquestDTO(
                                alumnoAleatorio.Id(),
                                grupoAleatorio.Id(),
                                precioFijo,
                                periodo.id()

                        );
                        inscripcionService.inscribirAlumno(dto);
                    } catch (DataIntegrityViolationException e) {
                        if (e.getMessage() != null && e.getMessage().toLowerCase().contains("duplicated")) {
                            System.out.println("Alumno ya existente");
                        }
                    }

                }
                Long endTime = System.currentTimeMillis();
                System.out.println(">>> Generacion masiva de datos usando los sevicios completada con exito en: " + (endTime - startTime));
            }
        };
    }
}
