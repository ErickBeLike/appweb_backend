package com.api.appweb.repository;

import com.api.appweb.entity.DetalleVenta;
import com.api.appweb.entity.Venta;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DetalleVentaRepository extends JpaRepository<DetalleVenta, Long> {
    @Transactional
    void deleteByVenta(Venta venta);

}
