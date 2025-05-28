import javax.swing.*;

public class Muñeca extends Tamagotchi implements TamagotchiActions {
    private ImageIcon imagen;

    public Muñeca(int hambre, int energia, int felicidad, String nombre, int comidasconsecutivas) {
        super(hambre, energia, felicidad, nombre,comidasconsecutivas);
        imagen = new ImageIcon(getClass().getResource("/muniecafeliz.JPG"));
    }


    @Override
    void emocion() {
        int totalStats = felicidad + hambre + energia;

        if (!feliz && totalStats >= 210) {
            System.out.println("¡Me siento muy feliz!");
            feliz = true;
            aburrido = false;
            triste = false;
        } else if (!aburrido && totalStats >= 160 && totalStats < 210) {
            System.out.println("Estoy aburrida...");
            feliz = false;
            aburrido = true;
            triste = false;
        } else if (!triste && totalStats < 160) {
            System.out.println("La muñeca se puso a llorar :<");
            feliz = false;
            aburrido = false;
            triste = true;
        }
    }


    @Override
    void Jugar() {
        System.out.println("Jugaron con otras muñecas!");
        boolean leGusta = Math.random() < 0.5;
        if (leGusta) {
            System.out.println("La pasó muy bien!");
            energia -= 10;
            felicidad += 40;
        } else {
            System.out.println("Se aburrió algo rápido");
            energia -= 20;
        }
        comidasconsecutivas=0;
        emocion();
        validarEstados();
        actualizarEstadoEmocional();
    }

    @Override
    void alimentar() {
        String[] comida = { "manzana", "ensalada", "tomar el té" };

        for (String alimento : comida) {
            switch (alimento.toLowerCase()) {
                case "manzana":
                    hambre += 20;
                    energia += 5;
                    break;
                case "ensalada":
                    hambre += 30;
                    energia += 15;
                    break;
                case "tomar el té":
                    hambre += 10;
                    energia += 10;
                    felicidad += 35;
                    break;
                default:
                    System.out.println("Alimento no válido: " + alimento);
            }
            System.out.println("Alimentado con: " + alimento);
        }
        comidasconsecutivas++;
        verifyDiet();
        emocion();
        validarEstados();
        actualizarEstadoEmocional();
    }

    @Override
    void enfermar() {
        if (hambre <= 0 || energia <= 0 || felicidad <= 0) {
            morir();
        }
    }

    @Override
    void dormir() {
        energia += 60;
        felicidad -= 15;
        hambre -= 20;
        System.out.println("La muñeca duerme pacíficamente");
        comidasconsecutivas = 0;
        verifyDiet();
        validarEstados();
        emocion();
    }

    @Override
    public void hacerSonido() {
        if (triste == true) {
            System.out.println(nombre+": buaaa Buaaa!");
            System.out.println("Oh no! tu muñeca está tan triste que se echó a llorar");
        } else if (feliz == true) {
            System.out.println(nombre + " La la la la, estoy muy feliz!");
        } else if (aburrido == true) {
            System.out.println(nombre+": juguemos a algo :(");
        }
    }

    @Override
    public void hacerUnaAccion() {
        System.out.println(nombre + " ha comenzado a cantar");
    }
    private void actualizarEstadoEmocional() {
        if (felicidad <= 40) {
            triste = true;
            aburrido = false;
            feliz = false;
        } else if (energia >= 60 && felicidad > 40 && felicidad <= 60) {
            triste = false;
            aburrido = true;
            feliz = false;
        } else if (felicidad > 60) {
            triste = false;
            aburrido = false;
            feliz = true;
        } else {
            triste = false;
            aburrido = false;
            feliz = false;
        }
    }
    public ImageIcon getImagenActual() {
        if (!isAlive()) {
            return new ImageIcon(getClass().getResource("/muerto.JPG"));
        } else if (triste) {
            return new ImageIcon(getClass().getResource("/muniecatriste.JPG"));
        } else if (aburrido) {
            return new ImageIcon(getClass().getResource("/muniecaaburrida.JPG"));
        } else {
            return new ImageIcon(getClass().getResource("/muniecafeliz.JPG"));
        }
    }

    public ImageIcon getImagen() {
        return imagen;
    }
}
