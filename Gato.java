import java.util.Random;
import javax.swing.ImageIcon;
import java.awt.Image;

public class Gato extends Tamagotchi implements TamagotchiActions{
    private ImageIcon imagen;

    public Gato(int hambre, int energia, int felicidad, String nombre) {
        super(hambre, energia, felicidad, nombre);
        imagen = new ImageIcon(getClass().getResource("/gatofeliz.JPG"));
    }

    @Override
    void emocion() {
        int totalStats = felicidad + hambre + energia;

        if (!feliz && totalStats >= 150 && felicidad > 50) {
            System.out.println("El gato comenzó a ronronear de la felicidad");
            feliz = true;
            aburrido = false;
            triste = false;
        } else if (!aburrido && totalStats < 150 && totalStats >= 50 && felicidad > 30) {
            System.out.println("El gatito está aburrido :|");
            feliz = false;
            aburrido = true;
            triste = false;
        } else if (!triste && felicidad <= 30) {
            System.out.println("Tu gatito se siente desanimado :c");
            feliz = false;
            aburrido = false;
            triste = true;
        }
    }


    @Override
    void Jugar() {
        String[] juego = {"jugar con el laser", "acariciarlo", "Cepillar cabello"};
        boolean leGusta = Math.random() < 0.5;
        Random random = new Random();
        int indice = random.nextInt(juego.length);
        String tipoJuego = juego[indice];
        if (leGusta == true){

            switch (tipoJuego.toLowerCase()) {
                case "jugar con el laser":
                    felicidad += 20;
                    energia -= 15;
                    break;
                case "acariciarlo":
                    hambre -= 20;
                    energia += 15;
                    felicidad += 40;

                    if (energia <= 40)
                    {System.out.println("El gatito se quedó dormido sobre tu regazo, recuperando su energia");
                    energia = 100;
                    }
                    break;
                case "cepillar cabello":
                    energia += 10;
                    felicidad += 15;
                    break;
                default:
                    System.out.println("Alimento no válido: " + tipoJuego);
            }
            System.out.println("Alimentado con: " + tipoJuego);
        }
        else {
            System.out.println("El gato jugó " + tipoJuego + " y no lo disfrutó. Ahora está molesto :0");

        }
        emocion();
        validarEstados();
    }

    @Override
    void alimentar() {
        String[] croqueta = {"Whiskas", "Proplan", "Pescado"};
        Random random = new Random();
        int indice = random.nextInt(croqueta.length);
        String alimento = croqueta[indice];

        switch (alimento.toLowerCase()) {
            case "whiskas":
                hambre += 16;
                energia += 8;
                break;
            case "pescado":
                hambre += 5;
                energia += 15;
                break;
            case "proplan":
                hambre += 40;
                energia += 10;
                felicidad += 5;
                break;
            default:
                System.out.println("Alimento no válido: " + alimento);
        }

        System.out.println("El gato ha sido alimentado con: " + alimento);
        emocion();
        validarEstados();
    }


    @Override
    void enfermar() {
        if (hambre <= 20 && energia <= 20 || felicidad <= 0) {
            morir();
        }
    }

    @Override
    void dormir() {
        System.out.println(""+nombre+" Comenzó a dormmir en forma de pan!");
        energia += 40;
        hambre -= 30;
        felicidad -= 14;
        emocion();
        validarEstados();
    }

    @Override
    public void hacerSonido() {
        if (aburrido == true) {
            System.out.println("" + nombre + ": Miau miau!");
        }
        if (feliz == true ) {
            System.out.println("" + nombre + ": comenzó a ronronear!");
        }
        if (triste == true){
            System.out.println("Meow......");
        }
    }

    @Override
    public void hacerUnaAccion() {
        System.out.println(""+nombre+" Comenzó a afilarse las uñas");
    }
    public ImageIcon getImagenActual() {
        if (!isAlive()) {
            return new ImageIcon(getClass().getResource("/muerto.JPG"));
        } else if (triste) {
            return new ImageIcon(getClass().getResource("/gatotriste.JPG"));
        } else if (aburrido) {
            return new ImageIcon(getClass().getResource("/gatoaburrido.JPG"));
        } else {
            return new ImageIcon(getClass().getResource("/gatofeliz.JPG")); // o "cuyo.JPG"
        }
    }

    public ImageIcon getImagen() {
        return imagen;
    }
}
