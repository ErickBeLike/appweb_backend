package com.api.appweb.dto;

import java.util.List;

public class VentaDTO {
    private Long idVenta;
    private Long idEmpleado;
    private List<ProductoCantidadDTO> productos;
    private List<ProductoDetalleDTO> productosDetalle;


    public Long getIdVenta() {
        return idVenta;
    }

    public void setIdVenta(Long idVenta) {
        this.idVenta = idVenta;
    }

    public Long getIdEmpleado() {
        return idEmpleado;
    }

    public void setIdEmpleado(Long idEmpleado) {
        this.idEmpleado = idEmpleado;
    }

    public List<ProductoCantidadDTO> getProductos() {
        return productos;
    }

    public void setProductos(List<ProductoCantidadDTO> productos) {
        this.productos = productos;
    }

    public List<ProductoDetalleDTO> getProductosDetalle() {
        return productosDetalle;
    }

    public void setProductosDetalle(List<ProductoDetalleDTO> productosDetalle) {
        this.productosDetalle = productosDetalle;
    }
}
