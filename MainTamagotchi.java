import javax.swing.*;
import java.awt.*;

public class MainTamagotchi {
    private JFrame frame;
    private JPanel panel;
    private JLabel nombreLabel;
    private JLabel tipoLabel;
    private JLabel imagenLabel;
    private JProgressBar hambreBar, energiaBar, felicidadBar;
    private JButton alimentarBtn, jugarBtn, dormirBtn;
    private JButton hacerAccionBtn, hacerSonidoBtn;

    private Tamagotchi mascota;

    private Thread hiloDecremento;

    public MainTamagotchi(String tipo, String nombre) {
        frame = new JFrame("Tamagotchi - " + nombre);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(500, 600);

        panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));

        nombreLabel = new JLabel("Nombre: " + nombre);
        tipoLabel = new JLabel("Tipo: " + tipo);
        imagenLabel = new JLabel();

        setMascota(tipo, nombre);
        cargarImagen();

        hambreBar = new JProgressBar(0, 100);
        energiaBar = new JProgressBar(0, 100);
        felicidadBar = new JProgressBar(0, 100);

        hambreBar.setStringPainted(true);
        energiaBar.setStringPainted(true);
        felicidadBar.setStringPainted(true);

        actualizarBarras();

        alimentarBtn = new JButton("Alimentar");
        jugarBtn = new JButton("Jugar");
        dormirBtn = new JButton("Dormir");

        alimentarBtn.addActionListener(e -> realizarAccion("alimentar"));
        jugarBtn.addActionListener(e -> realizarAccion("jugar"));
        dormirBtn.addActionListener(e -> realizarAccion("dormir"));

        hacerAccionBtn = new JButton("Hacer una Acción");
        hacerSonidoBtn = new JButton("Hacer Sonido");

        hacerAccionBtn.addActionListener(e -> {
            if (mascota instanceof TamagotchiActions) {
                ((TamagotchiActions) mascota).hacerUnaAccion();
            } else {
                System.out.println("Esta mascota no puede hacer esa acción.");
            }
        });

        hacerSonidoBtn.addActionListener(e -> {
            if (mascota instanceof TamagotchiActions) {
                ((TamagotchiActions) mascota).hacerSonido();
            } else {
                System.out.println("Esta mascota no puede hacer sonido especial.");
            }
        });

        panel.add(nombreLabel);
        panel.add(tipoLabel);
        panel.add(imagenLabel);

        panel.add(new JLabel("Saciedad"));
        panel.add(hambreBar);
        panel.add(new JLabel("Energía"));
        panel.add(energiaBar);
        panel.add(new JLabel("Felicidad"));
        panel.add(felicidadBar);

        panel.add(alimentarBtn);
        panel.add(jugarBtn);
        panel.add(dormirBtn);

        panel.add(hacerAccionBtn);
        panel.add(hacerSonidoBtn);

        frame.add(panel);
        frame.setVisible(true);
        cargarImagen();

        iniciarHiloDecremento();
    }

    private void iniciarHiloDecremento() {
        hiloDecremento = new Thread(() -> {
            try {
                while (mascota.isAlive()) {
                    Thread.sleep(2_000);
                    mascota.decrementarStats(1);

                    if (mascota.getHambre() <= 10 || mascota.getEnergia() <= 10 || mascota.getFelicidad() <= 10) {
                        mascota.enfermar();
                    }

                    SwingUtilities.invokeLater(() -> {
                        actualizarBarras();
                        mascota.emocion();
                        cargarImagen();
                        if (!mascota.isAlive()) {
                            JOptionPane.showMessageDialog(frame, mascota.getNombre() + " ha muerto. Ya no puedes interactuar.");
                            deshabilitarBotones();
                        }
                    });
                }
            } catch (InterruptedException e) {
                // Puede manejar interrupciones si quieres detener el hilo manualmente
                System.out.println("Hilo de decremento detenido.");
            }
        });
        hiloDecremento.start();

    }

    private void setMascota(String tipo, String nombre) {
        switch (tipo.toLowerCase()) {
            case "cuyo":
                mascota = new Cuyo(50, 50, 50, nombre, 0);
                break;
            case "muñeca":
                mascota = new Muñeca(50, 50, 50, nombre, 0);
                break;
            case "gato":
                mascota = new Gato(50, 50, 50, nombre, 0);
                break;
            case "perro":
                mascota = new Perro(50, 50, 50, nombre,0);
                break;
            default:
                JOptionPane.showMessageDialog(frame, "Tipo no reconocido");
                System.exit(1);
        }
    }

    private void cargarImagen() {
        try {
            ImageIcon iconoOriginal = mascota.getImagenActual();
            Image img = iconoOriginal.getImage().getScaledInstance(150, 150, Image.SCALE_SMOOTH);
            imagenLabel.setIcon(new ImageIcon(img));
        } catch (Exception e) {
            imagenLabel.setText("Imagen no disponible");
        }
    }

    private void realizarAccion(String accion) {
        if (!mascota.isAlive()) {
            JOptionPane.showMessageDialog(frame, mascota.getNombre() + " ha muerto. Ya no puedes interactuar.");
            deshabilitarBotones();
            return;
        }

        switch (accion) {
            case "alimentar":
                mascota.alimentar();
                break;
            case "jugar":
                mascota.Jugar();
                break;
            case "dormir":
                mascota.dormir();
                break;
        }

        if (mascota.getHambre() <= 10 || mascota.getEnergia() <= 10 || mascota.getFelicidad() <= 10) {
            mascota.enfermar();
        }

        actualizarBarras();
        cargarImagen();

        if (!mascota.isAlive()) {
            JOptionPane.showMessageDialog(frame, mascota.getNombre() + " ha muerto. Ya no puedes interactuar.");
            deshabilitarBotones();
        }
    }

    private void actualizarBarras() {
        hambreBar.setValue(mascota.getHambre());
        energiaBar.setValue(mascota.getEnergia());
        felicidadBar.setValue(mascota.getFelicidad());
    }

    private void deshabilitarBotones() {
        alimentarBtn.setEnabled(false);
        jugarBtn.setEnabled(false);
        dormirBtn.setEnabled(false);
        hacerAccionBtn.setEnabled(false);
        hacerSonidoBtn.setEnabled(false);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            String[] tipos = {"Cuyo", "Muñeca", "Gato", "Perro"};
            String tipo = (String) JOptionPane.showInputDialog(null, "Elige un tipo de Tamagotchi:",
                    "Seleccionar", JOptionPane.QUESTION_MESSAGE, null, tipos, tipos[0]);

            if (tipo != null) {
                String nombre = JOptionPane.showInputDialog("Escribe el nombre del Tamagotchi:");
                if (nombre != null && !nombre.trim().isEmpty()) {
                    new MainTamagotchi(tipo, nombre);
                }
            }
        });
    }
}
