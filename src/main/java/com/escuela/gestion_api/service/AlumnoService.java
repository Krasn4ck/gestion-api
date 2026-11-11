package com.escuela.gestion_api.service;

import java.time.Year;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.escuela.gestion_api.dto.AlumnoResponseDTO;
import com.escuela.gestion_api.dto.AlumnoRequestsDTO;
import com.escuela.gestion_api.models.Alumno;
import com.escuela.gestion_api.models.NivelEducativo;
import com.escuela.gestion_api.repository.AlumnoRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AlumnoService {
    private final AlumnoRepository alumnoRepository;

    @Transactional(readOnly = true)
    public List<AlumnoResponseDTO> obtenerTodos() {
        return alumnoRepository.findAll()
                .stream()
                .map(this::mapearADto)
                .toList();
    }

    @Transactional(readOnly = true)
    public AlumnoResponseDTO obtenerPorId(Long id) {
        Alumno alumno = alumnoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Alumno no encontrado con el ID:" + id));
        return mapearADto(alumno);
    }

    @Transactional
    public AlumnoResponseDTO guardar(AlumnoRequestsDTO dto) {
        if (alumnoRepository.existsByEmail(dto.email())) {
            throw new IllegalArgumentException("El email ya esta registrado ");
        }

        String matriculaGenerada = generarMatriculaUnica(dto.nivelEducativo());
        String referenciaGenerada = generarReferenciaBancaria(matriculaGenerada);
        Alumno nuevoAlumno = Alumno.builder()
                .nombre(dto.nombre())
                .apellidoMaterno(dto.ApellidoP())
                .apellidoMaterno(dto.ApellidoM())
                .matricula(matriculaGenerada)
                .email(dto.email())
                .fechaNacimiento(dto.fechaNacimiento())
                .nivelEducativo(dto.nivelEducativo())
                .referenciaBancaria(referenciaGenerada)
                .build();
        Alumno guardado = alumnoRepository.save(nuevoAlumno);
        return mapearADto(guardado);
    }

    @Transactional
    public AlumnoResponseDTO actualizarAlumno(Long id, AlumnoRequestsDTO dto) {
        Alumno alumno = alumnoRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Alumno no encontrado con el Id: " + id));
        alumno.setNombre(dto.nombre());
        alumno.setApellidoPaterno(dto.ApellidoP());
        alumno.setApellidoMaterno(dto.ApellidoM());
        alumno.setEmail(dto.email());
        alumno.setFechaNacimiento(dto.fechaNacimiento());
        alumno.setNivelEducativo(dto.nivelEducativo());

        Alumno actualizado = alumnoRepository.save(alumno);
        return mapearADto(actualizado);
    }

    @Transactional
    public void eliminarAlumno(Long id) {
        if (!alumnoRepository.existsById(id)) {
            throw new IllegalArgumentException("Alumno no encontrado con el Id: " + id);
        }
        alumnoRepository.deleteById(id);
    }

    // metodos privados
    public String generarReferenciaBancaria(String matricula) {
        // extraemos los digitos de la matricula
        String digitosMatricula = matricula.replaceAll("\\D", "");
        // codigo de servicio asignado para la matricula 91
        String codigoServicio = "91";
        // la base de referencia
        String baseReferencia = codigoServicio + digitosMatricula;
        // calculamos usando el modulo 10 el estandar que usan los bancos
        int digitoVerificador = calcularModulo10(baseReferencia);

        return baseReferencia + digitoVerificador;
    }

    // este es el estandar que usan los bancos para el digito verificador de una
    // referencia
    private int calcularModulo10(String cadena) {
        int suma = 0;
        boolean multiplicarPorDos = true;

        // recorremos de derecha -> izquierda
        for (int i = cadena.length() - 1; i >= 0; i--) {
            int digito = Character.getNumericValue(cadena.charAt(i));

            if (multiplicarPorDos) {
                digito *= 2;
                if (digito > 9) {
                    digito -= 9;
                }
            }
            suma += digito;
            multiplicarPorDos = !multiplicarPorDos;
        }
        int residuo = suma % 10;
        return (residuo == 0) ? 0 : (10 - residuo);
    }

    private String generarMatriculaUnica(NivelEducativo nivel) {
        String prefijoNivel = (nivel == NivelEducativo.BACHILLERATO) ? "BAC" : "UNI";
        int añoActual = Year.now().getValue();
        String baseMatricula = prefijoNivel + "-" + añoActual + "-";
        // contamos y agregamos 1
        Long consecutivo = alumnoRepository.count() + 1;
        String matricula;
        do {
            // int numeroAleatorio = ThreadLocalRandom.current().nextInt(1000, 10000);
            matricula = String.format("%s-%04d", baseMatricula, consecutivo);
            consecutivo++;
            // se hara consecutivo el numero hasta que veamos un numero que no este ocupado
            // en dado caso que lo este
        } while (alumnoRepository.existsByMatricula(matricula));
        return matricula;
    }

    private AlumnoResponseDTO mapearADto(Alumno alumno) {
        return new AlumnoResponseDTO(
                alumno.getId(),
                alumno.getNombre(),
                alumno.getApellidoPaterno(),
                alumno.getApellidoMaterno(),
                alumno.getMatricula(),
                alumno.getEmail(),
                alumno.getFechaNacimiento(),
                alumno.getNivelEducativo(),
                alumno.getReferenciaBancaria());
    }
}
