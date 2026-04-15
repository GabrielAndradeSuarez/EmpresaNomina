package model;

public class EmpleadoTemporal extends Empleado {
    private int diasTrabajados;
    private float valorDia;

    public EmpleadoTemporal(String nombre, String documento, int edad, float salarioBase, CategoriaEmpleado categoria, float descuentoSalud, float descuentoPension, int diasTrabajados, float valorDia) {
        super(nombre, documento, edad, salarioBase, categoria, descuentoSalud, descuentoPension);
        if (diasTrabajados < 0) throw new ValidacionException("Los días trabajados no pueden ser negativos");
        if (valorDia < 0) throw new ValidacionException("El valor por día no puede ser negativo");
        
        this.diasTrabajados = diasTrabajados;
        this.valorDia = valorDia;
    }

    @Override
    public float calcularSalarioBruto() {
        float pagoDiasTrabajados = diasTrabajados * valorDia;
        return pagoDiasTrabajados + calcularBonificacionCategoria();
    }
}
