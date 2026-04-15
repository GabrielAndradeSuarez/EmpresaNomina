package model;

public abstract class Empleado {
    protected String nombre;
    protected String documento;
    protected int edad;
    protected float salarioBase;
    protected CategoriaEmpleado categoria;
    protected float descuentoSalud;
    protected float descuentoPension;

    public Empleado(String nombre, String documento, int edad, float salarioBase, CategoriaEmpleado categoria, float descuentoSalud, float descuentoPension) {
        if (salarioBase < 0) throw new ValidacionException("El salario base no puede ser negativo");
        if (descuentoSalud < 0 || descuentoSalud > 100) throw new ValidacionException("El descuento de salud debe estar entre 0 y 100");
        if (descuentoPension < 0 || descuentoPension > 100) throw new ValidacionException("El descuento de pensión debe estar entre 0 y 100");
        
        this.nombre = nombre;
        this.documento = documento;
        this.edad = edad;
        this.salarioBase = salarioBase;
        this.categoria = categoria;
        this.descuentoSalud = descuentoSalud;
        this.descuentoPension = descuentoPension;
    }

    public String getNombre() {
        return nombre;
    }

    public String getDocumento() {
        return documento;
    }

    public float getSalarioBase() {
        return salarioBase;
    }

    public CategoriaEmpleado getCategoria() {
        return categoria;
    }

    public float getDescuentoSalud() {
        return descuentoSalud;
    }

    public float getDescuentoPension() {
        return descuentoPension;
    }

    public abstract float calcularSalarioBruto();

    public float calcularBonificacionCategoria() {
        return switch (categoria) {
            case JUNIOR -> salarioBase * 0.05f;
            case SEMI_SENIOR -> salarioBase * 0.10f;
            case SENIOR -> salarioBase * 0.15f;
        };
    }

    public float calcularDescuentos() {
        float totalDescuentoPorcentaje = descuentoSalud + descuentoPension;
        return salarioBase * (totalDescuentoPorcentaje / 100);
    }

    public float calcularSalarioNeto() {
        return calcularSalarioBruto() - calcularDescuentos();
    }

    public void mostrarInformacion() {
        String info = "Documento: " + documento + "\n" +
                      "Nombre: " + nombre + "\n" +
                      "Salario Base: $" + salarioBase + "\n" +
                      "Categoría: " + categoria + "\n" +
                      "Salario Bruto: $" + calcularSalarioBruto() + "\n" +
                      "Descuentos: $" + calcularDescuentos() + "\n" +
                      "Salario Neto: $" + calcularSalarioNeto();
        javax.swing.JOptionPane.showMessageDialog(null, info, "Información del Empleado", javax.swing.JOptionPane.INFORMATION_MESSAGE);
    }

    public ResumenPago generarResumenPago() {
        return new ResumenPago(
            this.documento,
            this.nombre,
            this.getClass().getSimpleName(),
            this.calcularSalarioBruto(),
            this.calcularDescuentos(),
            this.calcularSalarioNeto()
        );
    }
}
