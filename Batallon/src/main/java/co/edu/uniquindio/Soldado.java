package co.edu.uniquindio;

public class Soldado {
    private String id;
    private String nombre;
    private int edad;
    private double disponible;
    private TipoFuncion funcionSoldado;
    private RangoMilitar rangoMilitar;


    public Soldado(String id, String nombre, int edad, double disponible, TipoFuncion funcionSoldado, RangoMilitar rangoMilitar) {
        this.id = id;
        this.nombre = nombre;
        this.edad = edad;
        this.disponible = disponible;
        this.funcionSoldado = funcionSoldado;
        this.rangoMilitar = rangoMilitar;
    }

    public boolean estaDisponible(){
        return disponible==1.0;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public int getEdad() {
        return edad;
    }

    public void setEdad(int edad) {
        this.edad = edad;
    }

    public double getDisponible() {
        return disponible;
    }

    public void setDisponible(double disponible) {
        this.disponible = disponible;
    }

    public TipoFuncion getFuncionSoldado() {
        return funcionSoldado;
    }

    public void setFuncionSoldado(TipoFuncion funcionSoldado) {
        this.funcionSoldado = funcionSoldado;
    }

    public RangoMilitar getRangoMilitar() {
        return rangoMilitar;
    }

    public void setRangoMilitar(RangoMilitar rangoMilitar) {
        this.rangoMilitar = rangoMilitar;
    }
}

