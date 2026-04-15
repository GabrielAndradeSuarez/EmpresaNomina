package model;

public class EmpleadoPlanta extends Empleado {
    private String cargo;
    private int horasExtra;
    private float valorHoraExtra;
    private float auxilioTransporte;

    public EmpleadoPlanta(String nombre, String documento, int edad, float salarioBase, CategoriaEmpleado categoria, float descuentoSalud, float descuentoPension, String cargo, int horasExtra, float valorHoraExtra, float auxilioTransporte) {
        super(nombre, documento, edad, salarioBase, categoria, descuentoSalud, descuentoPension);
        if (horasExtra < 0) throw new ValidacionException("Las horas extra no pueden ser negativas");
        if (valorHoraExtra < 0) throw new ValidacionException("El valor de la hora extra no puede ser negativo");
        
        this.cargo = cargo;
        this.horasExtra = horasExtra;
        this.valorHoraExtra = valorHoraExtra;
        this.auxilioTransporte = auxilioTransporte;
    }

    @Override
    public float calcularSalarioBruto() {
        return salarioBase + calcularBonificacionCategoria() + (horasExtra * valorHoraExtra) + auxilioTransporte;
    }
}
