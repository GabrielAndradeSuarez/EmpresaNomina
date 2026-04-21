package test.java.model;

import model.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class EmpresaTest {

    private Empresa empresa;

    @BeforeEach
    void setUp() {
        empresa = new Empresa();
    }

    // 1. Verificar que el salario bruto de un empleado de ventas se calcule correctamente.
    @Test
    void testSalarioBrutoEmpleadoVentas() {
        EmpleadoVentas ev = new EmpleadoVentas("Juan", "111", 30, 1000f, CategoriaEmpleado.SEMI_SENIOR, 4f, 4f, 5000f, 10f);
        // Base: 1000, Bono Semi-Senior (10%): 100, Comision (10% de 5000): 500. Total Bruto = 1600.
        assertEquals(1600f, ev.calcularSalarioBruto());
    }

    // 2. Verificar que el salario neto de un empleado temporal no sea igual a 0.
    @Test
    void testSalarioNetoEmpleadoTemporalMayorACero() {
        EmpleadoTemporal et = new EmpleadoTemporal("Pedro", "222", 25, 1000f, CategoriaEmpleado.JUNIOR, 4f, 4f, 10, 50f);
        // Base (usada solo referencialmente y validaciones)
        // Bono Junior (5% de 1000): 50
        // Pago días: 10 * 50 = 500
        // Bruto = 550. Descuentos: 80.  Neto = 470.
        float salarioNeto = et.calcularSalarioNeto();
        assertNotEquals(0f, salarioNeto);
        assertTrue(salarioNeto > 0);
    }

    // 3. Verificar que los empleados almacenados en la empresa sean los correctos.
    @Test
    void testEmpleadosAlmacenadosCorrectos() {
        EmpleadoPlanta ep = new EmpleadoPlanta("Ana", "333", 40, 2000f, CategoriaEmpleado.SENIOR, 4f, 4f, "Gerente", 5, 20f, 100f);
        EmpleadoVentas ev = new EmpleadoVentas("Luis", "444", 35, 1500f, CategoriaEmpleado.SEMI_SENIOR, 4f, 4f, 2000f, 5f);
        empresa.agregarEmpleado(ep);
        empresa.agregarEmpleado(ev);

        Empleado e1 = empresa.buscarEmpleadoPorDocumento("333");
        Empleado e2 = empresa.buscarEmpleadoPorDocumento("444");

        assertNotNull(e1);
        assertNotNull(e2);
        assertEquals("Ana", e1.getNombre());
        assertEquals("Luis", e2.getNombre());
    }

    // 4. Verificar que la bonificación de un empleado JUNIOR sea mayor que 0.
    @Test
    void testBonificacionJuniorMayorQueCero() {
        EmpleadoPlanta ep = new EmpleadoPlanta("Carlos", "555", 22, 1000f, CategoriaEmpleado.JUNIOR, 4f, 4f, "Asistente", 0, 0f, 50f);
        assertTrue(ep.calcularBonificacionCategoria() > 0);
        assertEquals(50f, ep.calcularBonificacionCategoria(), 0.01);
    }

    // 5. Verificar que el salario neto nunca sea negativo.
    @Test
    void testSalarioNetoNuncaNegativo() {
        EmpleadoTemporal et = new EmpleadoTemporal("Jose", "666", 28, 0f, CategoriaEmpleado.JUNIOR, 0f, 0f, 0, 0f);
        assertTrue(et.calcularSalarioNeto() >= 0);
    }

    // 6. Verificar que la búsqueda de un empleado inexistente retorne null.
    @Test
    void testBuscarEmpleadoInexistente() {
        assertNull(empresa.buscarEmpleadoPorDocumento("9999"));
    }

    // 7. Verificar que se lance excepción si el salario base es negativo.
    @Test
    void testExcepcionSalarioBaseNegativo() {
        assertThrows(ValidacionException.class, () -> {
            new EmpleadoPlanta("Maria", "777", 30, -500f, CategoriaEmpleado.JUNIOR, 4f, 4f, "Secretaria", 0, 0f, 50f);
        });
    }

    // 8. Verificar que obtenerEmpleadoMayorSalarioNeto(valor) retorne lista de empleados > valor.
    @Test
    void testObtenerEmpleadosMayorSalarioNetoValor() {
        EmpleadoPlanta e1 = new EmpleadoPlanta("Uno", "001", 30, 2000f, CategoriaEmpleado.JUNIOR, 0f, 0f, "Cargo", 0, 0f, 0f); // Bruto: 2100 -> Neto: 2100
        EmpleadoPlanta e2 = new EmpleadoPlanta("Dos", "002", 30, 1000f, CategoriaEmpleado.JUNIOR, 0f, 0f, "Cargo", 0, 0f, 0f); // Bruto: 1050 -> Neto: 1050
        EmpleadoPlanta e3 = new EmpleadoPlanta("Tres", "003", 30, 3000f, CategoriaEmpleado.JUNIOR, 0f, 0f, "Cargo", 0, 0f, 0f); // Bruto: 3150 -> Neto: 3150
        
        empresa.agregarEmpleado(e1);
        empresa.agregarEmpleado(e2);
        empresa.agregarEmpleado(e3);

        List<Empleado> mayores = empresa.obtenerEmpleadosMayorSalarioNeto(2000f);
        
        assertEquals(2, mayores.size());
        assertEquals("001", mayores.get(0).getDocumento()); // Preserva orden
        assertEquals("003", mayores.get(1).getDocumento());
    }

    // 9. Comprobar que buscarEmpleadoPorDocumento() retorne null (reiterativo, pero listado diferente en requerimientos).
    @Test
    void testBuscarEmpleadoPorDocumentoInexistente() {
        assertNull(empresa.buscarEmpleadoPorDocumento("XXXX"));
    }

    // 10. Validar que el sistema no permita agregar empleados duplicados.
    @Test
    void testNoPermitirEmpleadosDuplicados() {
        EmpleadoPlanta ep1 = new EmpleadoPlanta("Jorge", "888", 30, 1000f, CategoriaEmpleado.JUNIOR, 4f, 4f, "Cargo", 0, 0f, 0f);
        EmpleadoPlanta ep2 = new EmpleadoPlanta("Otro", "888", 25, 2000f, CategoriaEmpleado.SENIOR, 4f, 4f, "Cargo 2", 0, 0f, 0f);
        
        empresa.agregarEmpleado(ep1);
        assertThrows(ValidacionException.class, () -> {
            empresa.agregarEmpleado(ep2); // Esto valida el mismo numero de documento '888'
        });
    }

    // 11. Verificar que el método que obtiene el empleado con mayor salario sea correcto.
    @Test
    void testObtenerEmpleadoMayorSalarioNeto() {
        EmpleadoPlanta e1 = new EmpleadoPlanta("Mily", "101", 30, 1000f, CategoriaEmpleado.JUNIOR, 0f, 0f, "Cargo", 0, 0f, 0f);
        EmpleadoPlanta e2 = new EmpleadoPlanta("Gaby", "102", 30, 5000f, CategoriaEmpleado.SENIOR, 0f, 0f, "Cargo", 0, 0f, 0f); // Sueldo significativamente más alto
        
        empresa.agregarEmpleado(e1);
        empresa.agregarEmpleado(e2);
        
        Empleado mayor = empresa.obtenerEmpleadoMayorSalarioNeto();
        assertNotNull(mayor);
        assertEquals("102", mayor.getDocumento());
    }

    // 12. Verificar que el método que obtiene los empleados temporales que han trabajado más de 100 horas es correcto.
    @Test
    void testTemporalesMasDe100Horas() {
        // En Empresa asumimos 8 horas por dia: dias * 8 > 100.
        // 10 dias * 8 = 80 horas (No pasa)
        // 15 dias * 8 = 120 horas (Sí pasa)
        EmpleadoTemporal et1 = new EmpleadoTemporal("T1", "t001", 30, 1000f, CategoriaEmpleado.JUNIOR, 0f, 0f, 10, 50f); 
        EmpleadoTemporal et2 = new EmpleadoTemporal("T2", "t002", 30, 1000f, CategoriaEmpleado.JUNIOR, 0f, 0f, 15, 50f); 
        
        empresa.agregarEmpleado(et1);
        empresa.agregarEmpleado(et2);
        
        List<EmpleadoTemporal> masDe100 = empresa.obtenerEmpleadosTemporalesMasDe100Horas();
        assertEquals(1, masDe100.size());
        assertEquals("t002", masDe100.get(0).getDocumento());
    }

    // 13. Verificar que calcularSalarioBruto() de empleado de planta sea mayor que su salario base.
    @Test
    void testSalarioBrutoPlantaMayorQueBase() {
        EmpleadoPlanta ep = new EmpleadoPlanta("Sofia", "s001", 35, 2000f, CategoriaEmpleado.SEMI_SENIOR, 4f, 4f, "Analista", 2, 20f, 100f);
        // Base: 2000, Bruto = base(2000) + bono10% (200) + extras(40) + transporte(100) = 2340
        assertTrue(ep.calcularSalarioBruto() > ep.getSalarioBase());
    }

    // 14. Verificar que el salario neto de un empleado temporal sea mayor a cero cuando los días y valor son válidos.
    @Test
    void testSalarioNetoTemporalValidoMayorCero() {
        EmpleadoTemporal et = new EmpleadoTemporal("Juanito", "r521", 20, 1000f, CategoriaEmpleado.JUNIOR, 5f, 5f, 5, 100f); // Trabajo real
        assertTrue(et.calcularSalarioNeto() > 0);
    }
}
