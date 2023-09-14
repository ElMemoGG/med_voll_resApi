package med.voll.api.domain.consulta;

import jakarta.validation.ValidationException;
import med.voll.api.infra.errores.ValidacionDeIntegridad;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.time.LocalDateTime;

@Service
public class CancelarConsultaService {
    @Autowired
    private ConsultaRepository consultaRepository;

    public DatosCancerlaConsulta cancelar(DatosCancerlaConsulta datosCancerlaConsulta){
        if(!consultaRepository.findById(datosCancerlaConsulta.id()).isPresent()){
            throw new ValidacionDeIntegridad("este id para la consulta no fue encontrado");
        }
        Consulta consulta = consultaRepository.getReferenceById(datosCancerlaConsulta.id());

        var ahora = LocalDateTime.now();
        var horaDeConsulta = consulta.getFecha();
        var diferenciaDe24Horas = Duration.between(ahora, horaDeConsulta).toHours()<24;

        if (diferenciaDe24Horas){
            throw new ValidationException("Para cancelar una consulta se necesita hacerlo con 24 horas de antes de la misma ");
        }

        consulta.cancelarConsulta(datosCancerlaConsulta);

        var bodyData = new DatosCancerlaConsulta(consulta.getId(), consulta.getMotivoCancelamiento());
        return bodyData;
    }
}
