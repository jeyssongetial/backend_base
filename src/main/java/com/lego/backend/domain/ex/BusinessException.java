package com.lego.backend.domain.ex;

import com.lego.backend.domain.ex.codigos.CodigosErrorNegocio;
import com.lego.backend.domain.ex.codigos.TipoError;

import java.io.Serial;
import java.util.function.Supplier;

public class BusinessException extends ApplicationException {

    @Serial
    private static final long serialVersionUID = 1L;

    public enum Type {
        ERROR_BASE(new Error(CodigosErrorNegocio.NEGOCIO_1, TipoError.NEGOCIO, "Error Mensaje Corto", "descripcion especifica del error")),
        ERROR_RANGO_INVALIDO(new Error(CodigosErrorNegocio.NEGOCIO_2, TipoError.NEGOCIO, "Rango inválido", "El rango proporcionado no es válido")),
        ERROR_CONSULTANTE_NULL(new Error(CodigosErrorNegocio.NEGOCIO_3, TipoError.NEGOCIO, "Consultante nulo", "El consultante no puede ser nulo")),
        ERROR_CONSULTA_INVALIDA(new Error(CodigosErrorNegocio.NEGOCIO_4, TipoError.NEGOCIO, "Consulta inválida", "La consulta realizada no es válida")),
        ERROR_USUARIO_NO_ENCONTRADO(new Error(CodigosErrorNegocio.NEGOCIO_5, TipoError.NEGOCIO, "Usuario no encontrado", "No se encontró el usuario con la cédula proporcionada"));

        private final Error error;

        public BusinessException build() {
            return new BusinessException(this);
        }

        public Supplier<Throwable> defer() {
            return () -> new BusinessException(this);
        }

        Type(Error error) {
            this.error = error;
        }

    }

    public BusinessException(Type type) {
        super(type.error);
    }

}
