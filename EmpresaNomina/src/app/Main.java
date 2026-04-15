package app;

import model.*;
import javax.swing.JOptionPane;

public class Main {
    public static void main(String[] args) {
        Empresa empresa = new Empresa();
        int opcion = 0;

        String menu = """
                --- MENÚ DE GESTIÓN DE EMPLEADOS ---
                1. Agregar empleado de planta
                2. Agregar empleado de ventas
                3. Agregar empleado temporal
                4. Mostrar todos los empleados
                5. Buscar empleado por documento
                6. Mostrar empleado con mayor salario neto
                7. Mostrar nómina total
                8. Mostrar resumen de pagos
                9. Salir
                Elija una opción:
                """;

        while (opcion != 9) {
            try {
                String input = JOptionPane.showInputDialog(null, menu, "Sistema de Nómina", JOptionPane.QUESTION_MESSAGE);
                if (input == null) {
                    break;
                }
                
                opcion = Integer.parseInt(input.trim());

                switch (opcion) {
                    case 1:
                        agregarEmpleadoPlanta(empresa);
                        break;
                    case 2:
                        agregarEmpleadoVentas(empresa);
                        break;
                    case 3:
                        agregarEmpleadoTemporal(empresa);
                        break;
                    case 4:
                        empresa.mostrarTodosLosEmpleados();
                        break;
                    case 5:
                        String docBusqueda = JOptionPane.showInputDialog(null, "Ingrese el documento a buscar:", "Buscar Empleado", JOptionPane.QUESTION_MESSAGE);
                        if (docBusqueda != null && !docBusqueda.trim().isEmpty()) {
                            Empleado empEncontrado = empresa.buscarEmpleadoPorDocumento(docBusqueda.trim());
                            if (empEncontrado != null) {
                                empEncontrado.mostrarInformacion();
                            } else {
                                JOptionPane.showMessageDialog(null, "No se encontró ningún empleado con el documento " + docBusqueda, "Búsqueda", JOptionPane.WARNING_MESSAGE);
                            }
                        }
                        break;
                    case 6:
                        Empleado empMayor = empresa.obtenerEmpleadoMayorSalarioNeto();
                        if (empMayor != null) {
                            empMayor.mostrarInformacion();
                        } else {
                            JOptionPane.showMessageDialog(null, "No hay empleados registrados.", "Mayor Salario Neto", JOptionPane.WARNING_MESSAGE);
                        }
                        break;
                    case 7:
                        float nomina = empresa.calcularNominaTotal();
                        JOptionPane.showMessageDialog(null, "La nómina total de la empresa es: $" + nomina, "Nómina Total", JOptionPane.INFORMATION_MESSAGE);
                        break;
                    case 8:
                        empresa.mostrarResumenesPago();
                        break;
                    case 9:
                        JOptionPane.showMessageDialog(null, "Saliendo del sistema...", "Salida", JOptionPane.INFORMATION_MESSAGE);
                        break;
                    default:
                        JOptionPane.showMessageDialog(null, "Opción no válida. Intente nuevamente.", "Error", JOptionPane.ERROR_MESSAGE);
                }
            } catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(null, "Por favor ingrese un número válido.", "Error de Formato", JOptionPane.ERROR_MESSAGE);
            } catch (ValidacionException e) {
                JOptionPane.showMessageDialog(null, "Error de validación: " + e.getMessage(), "Validación Fallida", JOptionPane.ERROR_MESSAGE);
            } catch (Exception e) {
                JOptionPane.showMessageDialog(null, "Error inesperado: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    private static void agregarEmpleadoPlanta(Empresa empresa) {
        String nombre = leerString("Nombre del empleado de planta:");
        if (nombre == null) return;
        String documento = leerString("Documento:");
        if (documento == null) return;
        int edad = leerInt("Edad:");
        if (edad == -1) return;
        float salarioBase = leerFloat("Salario Base:");
        if (salarioBase == -1) return;
        CategoriaEmpleado categoria = leerCategoria();
        if (categoria == null) return;
        float descuentoSalud = leerFloat("Descuento Salud (%):");
        if (descuentoSalud == -1) return;
        float descuentoPension = leerFloat("Descuento Pensión (%):");
        if (descuentoPension == -1) return;
        
        String cargo = leerString("Cargo:");
        if (cargo == null) return;
        int horasExtra = leerInt("Horas Extra:");
        if (horasExtra == -1) return;
        float valorHoraExtra = leerFloat("Valor Hora Extra:");
        if (valorHoraExtra == -1) return;
        float auxilioTransporte = leerFloat("Auxilio de Transporte:");
        if (auxilioTransporte == -1) return;

        EmpleadoPlanta ep = new EmpleadoPlanta(nombre, documento, edad, salarioBase, categoria, descuentoSalud, descuentoPension, cargo, horasExtra, valorHoraExtra, auxilioTransporte);
        empresa.agregarEmpleado(ep);
    }

    private static void agregarEmpleadoVentas(Empresa empresa) {
        String nombre = leerString("Nombre del empleado de ventas:");
        if (nombre == null) return;
        String documento = leerString("Documento:");
        if (documento == null) return;
        int edad = leerInt("Edad:");
        if (edad == -1) return;
        float salarioBase = leerFloat("Salario Base:");
        if (salarioBase == -1) return;
        CategoriaEmpleado categoria = leerCategoria();
        if (categoria == null) return;
        float descuentoSalud = leerFloat("Descuento Salud (%):");
        if (descuentoSalud == -1) return;
        float descuentoPension = leerFloat("Descuento Pensión (%):");
        if (descuentoPension == -1) return;
        
        float totalVentas = leerFloat("Total de Ventas:");
        if (totalVentas == -1) return;
        float porcentajeComision = leerFloat("Porcentaje de Comisión:");
        if (porcentajeComision == -1) return;

        EmpleadoVentas ev = new EmpleadoVentas(nombre, documento, edad, salarioBase, categoria, descuentoSalud, descuentoPension, totalVentas, porcentajeComision);
        empresa.agregarEmpleado(ev);
    }

    private static void agregarEmpleadoTemporal(Empresa empresa) {
        String nombre = leerString("Nombre del empleado temporal:");
        if (nombre == null) return;
        String documento = leerString("Documento:");
        if (documento == null) return;
        int edad = leerInt("Edad:");
        if (edad == -1) return;
        float salarioBase = leerFloat("Salario Base (usado para cálculo de bonificación):");
        if (salarioBase == -1) return;
        CategoriaEmpleado categoria = leerCategoria();
        if (categoria == null) return;
        float descuentoSalud = leerFloat("Descuento Salud (%):");
        if (descuentoSalud == -1) return;
        float descuentoPension = leerFloat("Descuento Pensión (%):");
        if (descuentoPension == -1) return;
        
        int diasTrabajados = leerInt("Días Trabajados:");
        if (diasTrabajados == -1) return;
        float valorDia = leerFloat("Valor por Día:");
        if (valorDia == -1) return;

        EmpleadoTemporal et = new EmpleadoTemporal(nombre, documento, edad, salarioBase, categoria, descuentoSalud, descuentoPension, diasTrabajados, valorDia);
        empresa.agregarEmpleado(et);
    }

    private static String leerString(String mensaje) {
        String res = JOptionPane.showInputDialog(null, mensaje, "Entrada de Datos", JOptionPane.QUESTION_MESSAGE);
        return res != null ? res.trim() : null;
    }

    private static int leerInt(String mensaje) {
        while (true) {
            String res = JOptionPane.showInputDialog(null, mensaje, "Entrada Numérica", JOptionPane.QUESTION_MESSAGE);
            if (res == null) return -1; // Canceled
            try {
                return Integer.parseInt(res.trim());
            } catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(null, "Escriba un número entero válido.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    private static float leerFloat(String mensaje) {
        while (true) {
            String res = JOptionPane.showInputDialog(null, mensaje, "Entrada Numérica", JOptionPane.QUESTION_MESSAGE);
            if (res == null) return -1f; // Canceled
            try {
                return Float.parseFloat(res.trim());
            } catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(null, "Escriba un número decimal válido.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    private static CategoriaEmpleado leerCategoria() {
        String[] opciones = {"JUNIOR", "SEMI_SENIOR", "SENIOR"};
        int seleccion = JOptionPane.showOptionDialog(null, "Seleccione la categoría del empleado:", "Categoría", 
                JOptionPane.DEFAULT_OPTION, JOptionPane.QUESTION_MESSAGE, null, opciones, opciones[0]);
                
        switch (seleccion) {
            case 0: return CategoriaEmpleado.JUNIOR;
            case 1: return CategoriaEmpleado.SEMI_SENIOR;
            case 2: return CategoriaEmpleado.SENIOR;
            default: return null;
        }
    }
}
