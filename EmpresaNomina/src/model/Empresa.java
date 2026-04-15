package model;

import javax.swing.JOptionPane;
import java.util.ArrayList;
import java.util.List;

public class Empresa {
    private List<Empleado> empleados;

    public Empresa() {
        this.empleados = new ArrayList<>();
    }

    public void agregarEmpleado(Empleado empleado) {
        if (empleado != null) {
            empleados.add(empleado);
            JOptionPane.showMessageDialog(null, "Empleado agregado exitosamente.", "Éxito", JOptionPane.INFORMATION_MESSAGE);
        }
    }

    public void mostrarTodosLosEmpleados() {
        if (empleados.isEmpty()) {
            JOptionPane.showMessageDialog(null, "No hay empleados registrados.", "Lista de Empleados", JOptionPane.WARNING_MESSAGE);
            return;
        }
        
        StringBuilder sb = new StringBuilder();
        for (Empleado empleado : empleados) {
            sb.append("Documento: ").append(empleado.getDocumento())
              .append(" | Nombre: ").append(empleado.getNombre())
              .append("\nCategoría: ").append(empleado.getCategoria())
              .append(" | Salario Neto: $").append(empleado.calcularSalarioNeto())
              .append("\n-------------------------------------------------\n");
        }
        JOptionPane.showMessageDialog(null, sb.toString(), "Lista de Empleados", JOptionPane.INFORMATION_MESSAGE);
    }

    public Empleado buscarEmpleadoPorDocumento(String documento) {
        for (Empleado empleado : empleados) {
            if (empleado.getDocumento().equals(documento)) {
                return empleado;
            }
        }
        return null;
    }

    public Empleado obtenerEmpleadoMayorSalarioNeto() {
        if (empleados.isEmpty()) return null;
        
        Empleado mayorSalario = empleados.get(0);
        for (Empleado empleado : empleados) {
            if (empleado.calcularSalarioNeto() > mayorSalario.calcularSalarioNeto()) {
                mayorSalario = empleado;
            }
        }
        return mayorSalario;
    }

    public float calcularNominaTotal() {
        float nominaTotal = 0;
        for (Empleado empleado : empleados) {
            nominaTotal += empleado.calcularSalarioNeto();
        }
        return nominaTotal;
    }

    public void mostrarResumenesPago() {
        if (empleados.isEmpty()) {
            JOptionPane.showMessageDialog(null, "No hay empleados registrados para mostrar resúmenes.", "Resúmenes de Pago", JOptionPane.WARNING_MESSAGE);
            return;
        }
        
        StringBuilder sb = new StringBuilder();
        for (Empleado empleado : empleados) {
            ResumenPago resumen = empleado.generarResumenPago();
            sb.append(resumen.toString()).append("\n\n");
        }
        JOptionPane.showMessageDialog(null, sb.toString(), "Resúmenes de Pago", JOptionPane.INFORMATION_MESSAGE);
    }
}
