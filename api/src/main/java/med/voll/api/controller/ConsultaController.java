package med.voll.api.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import med.voll.api.domain.consulta.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

@Controller
@ResponseBody
@RequestMapping("/consultas")
@SecurityRequirement(name = "bearer-key")
@Tag(name = "consulta", description = "")
public class ConsultaController {

    @Autowired
    private AgendaDeConsultaService service;
    @Autowired
    private CancelarConsultaService serviceCancelar;
    @Autowired
    private ConsultaRepository consultaRepository;

    @PostMapping
    @Transactional
    @Operation(
            summary = "registra una consulta en la base de datos",
            description = "",
            tags = { "consulta"/*, "post"*/ })
    public ResponseEntity agendar(@RequestBody @Valid DatosAgendarConsulta datos){
        var response = service.agendar(datos);
        return ResponseEntity.ok(response);
    }
    @GetMapping("/{id}")
    public ResponseEntity<DatosDetalleConsulta> retornaDatosConsulta(@PathVariable Long id){
        Consulta consulta = consultaRepository.getReferenceById(id);
        var datosConsulta = new DatosDetalleConsulta(consulta);

        return ResponseEntity.ok(datosConsulta);
    }
    @GetMapping
    public ResponseEntity<Page<DatosDetalleConsulta>>  listadoConsultas(@PageableDefault(size = 10) Pageable paginacion){
        return ResponseEntity.ok(consultaRepository.findAll(paginacion).map(DatosDetalleConsulta::new));
    }

    @DeleteMapping
    @Transactional
    public ResponseEntity<DatosCancerlaConsulta> actualizarConsulta(@RequestBody @Valid DatosCancerlaConsulta datosCancerlaConsulta){
        return ResponseEntity.ok(serviceCancelar.cancelar(datosCancerlaConsulta));

    }


}
