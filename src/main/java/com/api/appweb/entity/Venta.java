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

    private double total;
    private LocalDateTime fecha_venta;

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
