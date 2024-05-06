package com.api.appweb.entity;

import jakarta.persistence.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

@Entity
@Table(name = "ventas")
public class Venta {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id_venta;
    @ManyToOne
    @JoinColumn
    private Empleado id_empleado;
    @ElementCollection
    @CollectionTable(name="detalle_venta", joinColumns=@JoinColumn(name="id_venta"))
    @MapKeyJoinColumn(name="id_producto")
    @Column(name="cantidad_producto")
    private Map<Producto, Integer> cantidadesProducto;

    private double total;
    private LocalDateTime fecha_venta;

    public Long getId_venta() {
        return id_venta;
    }

    public void setId_venta(Long id_venta) {
        this.id_venta = id_venta;
    }

    public Empleado getId_empleado() {
        return id_empleado;
    }

    public void setId_empleado(Empleado id_empleado) {
        this.id_empleado = id_empleado;
    }

    public Map<Producto, Integer> getCantidadesProducto() {
        return cantidadesProducto;
    }

    public void setCantidadesProducto(Map<Producto, Integer> cantidadesProducto) {
        this.cantidadesProducto = cantidadesProducto;
    }

    public double getTotal() {
        return total;
    }

    public void setTotal(double total) {
        this.total = total;
    }

    public LocalDateTime getFecha_venta() {
        return fecha_venta;
    }

    public void setFecha_venta(LocalDateTime fecha_venta) {
        this.fecha_venta = fecha_venta;
    }
}
