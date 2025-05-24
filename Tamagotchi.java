import javax.swing.*;

public abstract class Tamagotchi {
    protected int hambre;
    protected int energia;
    protected int felicidad;
    protected boolean sano;
    protected String nombre;
    protected boolean vivo;
    boolean triste = false;
    boolean aburrido = false;
    boolean feliz = true;

    public Tamagotchi(int hambre, int energia, int felicidad, String nombre) {
        this.hambre = hambre;
        this.energia = energia;
        this.felicidad = felicidad;
        this.sano = true;
        this.vivo = true;  // <<--- Inicializa vivo en true
        this.nombre = nombre;
    }
    abstract void emocion();
    abstract void Jugar();
    abstract void alimentar();
    abstract void enfermar();
    abstract void dormir();
    public abstract ImageIcon getImagenActual();



    protected void validarEstados() {
        hambre = Math.max(0, Math.min(hambre, 100));
        energia = Math.max(0, Math.min(energia, 100));
        felicidad = Math.max(0, Math.min(felicidad, 100));
    }

    public boolean isAlive() {
        return vivo;
    }

    protected void morir() {
        vivo = false;
        System.out.println(nombre + " ha muerto :(");
    }


    public void decrementarStats(int i) {
        energia -= i;
        hambre -= i;
        felicidad -= i;
        validarEstados();
        if (hambre == 0 || energia == 0 || felicidad == 0) {
            morir();
        }
    }
    public int getEnergia() {
        return energia;
    }

    public void setEnergia(int energia) {
        this.energia = energia;
    }

    public int getFelicidad() {
        return felicidad;
    }

    public int getHambre() {
        return hambre;
    }

    public String getNombre() {
        return nombre;
    }

    }
