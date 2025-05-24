import javax.swing.*;
import java.util.Random;

public class Perro extends Tamagotchi implements TamagotchiActions {
    private ImageIcon imagen;

    public Perro(int hambre, int energia, int felicidad, String nombre) {
        super(hambre, energia, felicidad, nombre);
        imagen = new ImageIcon(getClass().getResource("/perrofeliz.JPG"));
    }

    @Override
    void emocion() {
        int totalStats = felicidad + hambre + energia;
        if (!feliz && felicidad > 50 && totalStats >= 150) {
            System.out.println("El perrito mueve su cola de felicidad!");
            feliz = true;
            aburrido = false;
            triste = false;
            return;
        }
        if (!aburrido && felicidad <= 50 && felicidad > 30 && totalStats >= 110 && totalStats < 150) {
            System.out.println("El perrito está aburrido :|");
            feliz = false;
            aburrido = true;
            triste = false;
            return;
        }
        if (!triste && felicidad <= 30) {
            System.out.println("Tu perrito se siente bastante triste :c");
            feliz = false;
            aburrido = false;
            triste = true;
        }
    }


    @Override
    void Jugar() {
        String[] juego = {"Aventar la pelota", "Sacarlo a pasear", "Cepillar cabello"};
        Random random = new Random();
        int indice = random.nextInt(juego.length);
        String tipoJuego = juego[indice];

        switch (tipoJuego.toLowerCase()) {
            case "aventar la pelota":
                felicidad += 20;
                energia -= 15;
                System.out.println("¡Jugaron a la pelota toda la tarde!");
                break;
            case "sacarlo a pasear":
                hambre -= 20;
                energia -= 15;
                felicidad += 40;
                System.out.println("¡Le encantó la paseada!");
                break;
            case "cepillar cabello":
                energia -= 20;
                felicidad += 15;
                System.out.println(nombre + " disfrutó mucho que le cepillaras el cabello");
                break;
            default:
                System.out.println("Juego no válido: " + tipoJuego);
        }
        emocion();
        validarEstados();
    }

    @Override
    void alimentar() {
        String[] croqueta = {"Pedigree", "Marca genérica", "Premio"};
        Random random = new Random();
        int indice = random.nextInt(croqueta.length);
        String alimento = croqueta[indice];

        switch (alimento.toLowerCase()) {
            case "pedigree":
                hambre += 20;
                energia += 5;
                break;
            case "marca genérica":
                hambre += 30;
                energia += 15;
                break;
            case "premio":
                hambre += 40;
                energia += 10;
                felicidad += 5;
                break;
            default:
                System.out.println("Alimento no válido: " + alimento);
        }

        System.out.println("Alimentado con: " + alimento);
        emocion();
        validarEstados();
    }

    @Override
    void enfermar() {
        if (hambre <= 0 || energia <= 0 || felicidad <= 0) {
            morir();
        }
    }

    @Override
    void dormir() {
        System.out.println("El perro se acuesta sobre su casita");
        energia += 40;
        hambre -= 30;
        felicidad -= 10;
        validarEstados();
        emocion();
    }

    @Override
    public void hacerSonido() {
        if (feliz) {
            System.out.println(nombre + ": ¡Wof Wof!");
            System.out.println(nombre + " está moviendo su cola!");
        }
        if (triste) {
            System.out.println(nombre + ": Wof.... wof...");
            System.out.println(nombre + " no se ve muy animado");
        }
    }

    @Override
    public void hacerUnaAccion() {
        System.out.println(nombre + " está persiguiendo su cola!");
    }

    // Imagen según estado emocional o de vida
    public ImageIcon getImagenActual() {
        if (!isAlive()) {
            return new ImageIcon(getClass().getResource("muerto.JPG"));
        } else if (triste) {
            return new ImageIcon(getClass().getResource("/perrotriste.JPG"));
        } else if (aburrido) {
            return new ImageIcon(getClass().getResource("/perroaburrido.JPG"));
        } else {
            return new ImageIcon(getClass().getResource("/perrofeliz.JPG"));
        }
    }

    public ImageIcon getImagen() {
        return imagen;
    }
}
