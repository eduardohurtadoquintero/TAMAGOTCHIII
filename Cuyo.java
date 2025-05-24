import javax.swing.*;
import java.util.Random;

public class Cuyo extends Tamagotchi implements TamagotchiActions{

    private ImageIcon imagen;

    public Cuyo(int hambre, int energia, int felicidad, String nombre) {
        super(hambre, energia, felicidad, nombre);
        imagen = new ImageIcon(getClass().getResource("/cuyofeliz.JPG"));
    }

    @Override
    void emocion() {
        if (!feliz && felicidad >= 50) {
            System.out.println(nombre + " está muy feliz!");
            feliz = true;
            aburrido = false;
            triste = false;
        } else if (!aburrido && felicidad >= 31 && felicidad < 50) {
            System.out.println(nombre + " está aburrido :|");
            feliz = false;
            aburrido = true;
            triste = false;
        } else if (!triste && felicidad <= 30) {
            System.out.println(nombre + " se siente desanimado :c");
            feliz = false;
            aburrido = false;
            triste = true;
        }
    }



    @Override
    void Jugar() {
        String[] juegos = {"escondidas", "carrera de saltos", "cantar canción",};

        Random random = new Random();
        int indice = random.nextInt(juegos.length);
        String tipoJuego = juegos[indice];

        boolean leGusta = Math.random() < 0.6; // 60% chance que le guste

        System.out.println(nombre + " decidió jugar a: " + tipoJuego);

        if (leGusta) {
            switch (tipoJuego) {
                case "escondidas":
                    felicidad += 30;
                    energia -= 15;
                    System.out.println("¡Se divirtió mucho jugando a las escondidas!");
                    break;
                case "carrera de saltos":
                    felicidad += 40;
                    energia -= 25;
                    System.out.println("¡Ganó la carrera de saltos y está súper feliz!");
                    break;
                case "cantar canción":
                    felicidad += 25;
                    energia -= 15;
                    System.out.println("Cantaste una linda canción paraa "+nombre+".");
                    break;
            }
        } else {
            System.out.println(nombre + " no disfrutó jugar a " + tipoJuego + " y se siente un poco molesto.");
            felicidad -= 20;
            energia -= 10;
        }
        emocion();
        validarEstados();
    }


    @Override
    void alimentar() {
        String[] croqueta = {"heno", "lechuga", "Premio"};
        Random random = new Random();
        int indice = random.nextInt(croqueta.length);
        String alimento = croqueta[indice];

        switch (alimento.toLowerCase()) {
            case "heno":
                hambre += 20;
                energia += 5;
                break;
            case "lechuga":
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
            System.out.println(""+nombre+" Comenzó a dormir con una cara muy tierna!");
            energia += 40;
            hambre -= 30;
            felicidad -= 14;
            emocion();
            validarEstados();
    }

    @Override
    public void hacerSonido() {
        if (feliz == true) {
            System.out.println("¡Cuy cuy cuy! (" + nombre + " está contento!)");
        } else if (triste == true) {
            System.out.println("cuuuuy... (" + nombre + " está triste...)");
        } else {
            System.out.println("cuy cuy");
        }
    }


    @Override
    public void hacerUnaAccion() {
    System.out.println("Comenzó a roer la madera!");
    }

    public ImageIcon getImagenActual() {
        if (!isAlive()) {
            return new ImageIcon(getClass().getResource("/muerto.JPG"));
        } else if (triste) {
            return new ImageIcon(getClass().getResource("/cuyotriste.JPG"));
        } else if (aburrido) {
            return new ImageIcon(getClass().getResource("/cuyoaburrido.JPG"));
        } else {
            return new ImageIcon(getClass().getResource("/cuyofeliz.JPG"));
        }
    }

    public ImageIcon getImagen() {
        return imagen;
    }
}
