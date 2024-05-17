package com.api.appweb.entity;

import jakarta.persistence.*;
import java.time.LocalDateTime;
import java.util.Map;

@Entity
@Table(name = "ventas")
public class Venta {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_venta")
    private Long idVenta;

    @ManyToOne
    @JoinColumn(name = "id_empleado")
    private Empleado idEmpleado;

    @ElementCollection
    @CollectionTable(name="detalle_venta", joinColumns=@JoinColumn(name="id_venta"))
    @MapKeyJoinColumn(name="id_producto")
    @Column(name="cantidad_producto")
    private Map<Producto, Integer> cantidadesProducto;

    @ElementCollection
    @CollectionTable(name="precios_unitarios", joinColumns=@JoinColumn(name="id_venta"))
    @MapKeyJoinColumn(name="id_producto")
    @Column(name="precio_unitario")
    private Map<Producto, Double> preciosUnitarios;
    @Column(name = "total")
    private double total;
    @Column(name = "fecha_venta")
    private LocalDateTime fechaVenta;

    // Getters y setters

    public Long getIdVenta() {
        return idVenta;
    }

    public void setIdVenta(Long idVenta) {
        this.idVenta = idVenta;
    }

    public Empleado getIdEmpleado() {
        return idEmpleado;
    }

    public void setIdEmpleado(Empleado idEmpleado) {
        this.idEmpleado = idEmpleado;
    }

    public Map<Producto, Integer> getCantidadesProducto() {
        return cantidadesProducto;
    }

    public void setCantidadesProducto(Map<Producto, Integer> cantidadesProducto) {
        this.cantidadesProducto = cantidadesProducto;
    }

    public Map<Producto, Double> getPreciosUnitarios() {
        return preciosUnitarios;
    }

    public void setPreciosUnitarios(Map<Producto, Double> preciosUnitarios) {
        this.preciosUnitarios = preciosUnitarios;
    }

    public double getTotal() {
        return total;
    }

    public void setTotal(double total) {
        this.total = total;
    }

    public LocalDateTime getFechaVenta() {
        return fechaVenta;
    }

    public void setFechaVenta(LocalDateTime fechaVenta) {
        this.fechaVenta = fechaVenta;
    }
}
