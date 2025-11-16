package com.pruebapaula.pruebapaula.services;

import com.pruebapaula.pruebapaula.dto.empresa.EmpresaResponseDTO;
import com.pruebapaula.pruebapaula.dto.producto.ProductoRequestDTO;
import com.pruebapaula.pruebapaula.dto.producto.ProductoResponseDTO;

import java.util.List;

public interface ProductoService {
    ProductoResponseDTO crearProducto(ProductoRequestDTO dto);
    List<ProductoResponseDTO> listarProductosPorEmpresa(String nit);
    List<ProductoResponseDTO> listar();
    ProductoResponseDTO editarProducto(Long id, ProductoRequestDTO dto);
    void eliminarProducto(Long id);

}
