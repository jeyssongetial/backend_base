package com.lego.backend.domain.ex;

import com.lego.backend.domain.ex.codigos.CodigosErrorTecnico;
import com.lego.backend.domain.ex.codigos.TipoError;
import lombok.Getter;

import java.io.Serial;
import java.util.function.Supplier;

public class TechnicalException extends ApplicationException {

    @Serial
    private static final long serialVersionUID = 1L;

    @Getter
    public enum Type {
        ERROR_BASE (new Error(CodigosErrorTecnico.TECNICO_1, TipoError.TECNICO, "Error Mensaje Corto", "descripcion especifica del error")),
        ERROR_EXCEPCION_RESPUESTA_ESTADO(new Error(CodigosErrorTecnico.TECNICO_2, TipoError.TECNICO, "Error en ResponseStatusException", "Se produjo un error en ResponseStatusException")),
        ERROR_EXCEPCION_GENERICA(new Error(CodigosErrorTecnico.TECNICO_3, TipoError.TECNICO, "Error genérico", "Se produjo un error genérico")),
        ERROR_ANIOS_INVALIDO(new Error(CodigosErrorTecnico.TECNICO_4, TipoError.TECNICO, "Años de servicio inválido", "Los años de servicio no pueden ser negativos")),
        ERROR_USUARIO_NO_ENCONTRADO(new Error(CodigosErrorTecnico.TECNICO_5, TipoError.TECNICO, "Usuario no encontrado", "El usuario solicitado no fue encontrado")),
        ERROR_TOKEN_INVALIDO(new Error(CodigosErrorTecnico.TECNICO_6, TipoError.TECNICO, "Token inválido", "El token proporcionado no es válido")),
        ERROR_INVALID_REFRESH_TOKEN(new Error(CodigosErrorTecnico.TECNICO_7, TipoError.TECNICO, "Refresh token inválido", "El refresh token proporcionado no es válido")),;

        private final Error error;

        public TechnicalException build() {
            return new TechnicalException(this);
        }

        public Supplier<Throwable> defer() {
            return () -> new TechnicalException(this);
        }

        Type(Error error) {
            this.error = error;
        }

    }

    private TechnicalException(Type type) {
        super(type.error);
    }

}
