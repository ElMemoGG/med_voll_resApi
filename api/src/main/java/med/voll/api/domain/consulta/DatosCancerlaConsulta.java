package med.voll.api.domain.consulta;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record DatosCancerlaConsulta(@NotNull Long id, @NotBlank String motivoCancelamiento) {

}
