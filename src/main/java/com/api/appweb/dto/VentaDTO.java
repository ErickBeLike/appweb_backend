package com.api.appweb.dto;

import java.util.List;

public class VentaDTO {
    private Long idVenta;
    private List<ProductoCantidadDTO> productos;


    public Long getIdVenta() {
        return idVenta;
    }

    public void setIdVenta(Long idVenta) {
        this.idVenta = idVenta;
    }

    public List<ProductoCantidadDTO> getProductos() {
        return productos;
    }

    public void setProductos(List<ProductoCantidadDTO> productos) {
        this.productos = productos;
    }
}
