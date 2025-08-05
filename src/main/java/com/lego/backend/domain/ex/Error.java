package com.lego.backend.domain.ex;

import com.lego.backend.domain.ex.codigos.TipoError;

public record Error(
        String id,
        TipoError tipo,
        String mensaje,
        String detalle) {
}
