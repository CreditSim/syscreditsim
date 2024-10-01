package loading;

import javax.swing.*;
import com.formdev.flatlaf.FlatLightLaf;
import config.Config;
import panel.connection.DatabaseConnection;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.awt.geom.RoundRectangle2D;
import java.awt.Image;
import java.io.IOException;
import java.sql.Connection;
import java.util.concurrent.CountDownLatch;
import javax.imageio.ImageIO;

public class SplashScreen extends JDialog {

    private final int totalTarefas = 5;
    private final int progressoPorTarefa = 100 / totalTarefas;

    private ProgressBarCustom progressBar;
    private JLabel statusLabel;
    private int progressoAtual = 0; // Variável para acompanhar o progresso atual

    private CountDownLatch latch;

    public SplashScreen(CountDownLatch latch) {
        super(); // Chama o construtor padrão de JDialog
        this.latch = latch;

        FlatLightLaf.setup();

        // Configurações do painel de conteúdo
        JPanel content = new JPanel();
        content.setBackground(Color.WHITE);
        content.setLayout(null);

        // Imagem do logo
        ImageIcon logoIcon = resizeImage("/loading/icon/logo.png", 104, 103);
        JLabel logoLabel = new JLabel(logoIcon);
        logoLabel.setBounds(223, 81, 104, 103);
        content.add(logoLabel);

        // Nome do aplicativo
        JLabel nameLabel = new JLabel("CreditSim");
        nameLabel.setFont(new Font("Roboto", Font.BOLD, 20));
        nameLabel.setBounds(200, 201, 150, 25);
        nameLabel.setHorizontalAlignment(SwingConstants.CENTER);
        content.add(nameLabel);

        // Versão do aplicativo
        JLabel versionLabel = new JLabel("© System CreditSim, v" + Config.VERSION);
        versionLabel.setFont(new Font("Arial", Font.PLAIN, 12));
        versionLabel.setForeground(Color.decode("#90908C"));
        versionLabel.setBounds(150, 304, 250, 15);
        versionLabel.setHorizontalAlignment(SwingConstants.CENTER);
        content.add(versionLabel);

        // Configuração da barra de progresso
        progressBar = new ProgressBarCustom();
        progressBar.setBounds(100, 250, 350, 5); // Ajuste da altura da barra de progresso
        content.add(progressBar);

        // Label para exibir o status da tarefa
        statusLabel = new JLabel("Loading...");
        statusLabel.setFont(new Font("Arial", Font.PLAIN, 12));
        statusLabel.setForeground(Color.decode("#90908C"));
        statusLabel.setBounds(200, 270, 150, 15); // Ajuste a posição da label do status
        statusLabel.setHorizontalAlignment(SwingConstants.CENTER);
        content.add(statusLabel);

        setContentPane(content);
        setSize(551, 332);
        setLocationRelativeTo(null); // Centraliza a tela em relação à tela principal
        setUndecorated(true);
        setShape(new RoundRectangle2D.Double(0, 0, getWidth(), getHeight(), 8, 8));

        // Iniciar a tarefa de carregamento
        startLoadingTasks();
    }

    private ImageIcon resizeImage(String path, int width, int height) {
        try {
            BufferedImage originalImage = ImageIO.read(getClass().getResource(path));
            Image scaledImage = originalImage.getScaledInstance(width, height, Image.SCALE_SMOOTH);
            return new ImageIcon(scaledImage);
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    private void startLoadingTasks() {
        new Thread(() -> {
            try {
                // Tarefa 1: Conectando ao banco de dados
                conectarAoBancoDeDados();
                updateProgress("Connecting to database...");

                // Tarefa 2: Carregando dados
                carregarDados();
                updateProgress("Loading data...");

                // Tarefa 3: Inicializando componentes
                inicializarComponentes();
                updateProgress("Initializing components...");

                // Tarefa 4: Configurando a interface do usuário
                configurarInterface();
                updateProgress("Setting up user interface...");

                // Tarefa 5: Finalizando
                updateProgress("Finalizing...");

                // Fecha a tela de splash
                dispose();

                latch.countDown();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }).start();
    }

    private void updateProgress(String status) throws InterruptedException {
        progressoAtual += progressoPorTarefa; // Incrementa o progresso atual
        if (progressoAtual > 100) {
            progressoAtual = 100; // Garante que o progresso não exceda 100
        }

        SwingUtilities.invokeLater(() -> {
            statusLabel.setText(status);
            progressBar.setValue(progressoAtual); // Atualiza a barra de progresso
        });

        Thread.sleep(1000); // Simula a duração da tarefa
    }

    // Função simulada para conectar ao banco de dados usando a classe DatabaseConnection
    private void conectarAoBancoDeDados() throws InterruptedException {
        Connection dbConnection = null;
        try {
            dbConnection = DatabaseConnection.getInstance().createConnection();
            System.out.println("Conexão ao banco de dados estabelecida.");
        } catch (Exception e) {
            e.printStackTrace();
        }
        Thread.sleep(1500); // Simulando o tempo de conexão
    }

    // Função simulada para carregar dados
    private void carregarDados() throws InterruptedException {
        // Simula o carregamento de dados, substitua pela lógica real
        Thread.sleep(1500);  // Simulando o tempo de carregamento de dados
        System.out.println("Dados carregados.");
    }

    // Função simulada para inicializar componentes
    private void inicializarComponentes() throws InterruptedException {
        // Simula a inicialização de componentes, substitua pela lógica real
        Thread.sleep(1000);  // Simulando o tempo de inicialização dos componentes
        System.out.println("Componentes inicializados.");
    }

    // Função simulada para configurar a interface do usuário
    private void configurarInterface() throws InterruptedException {
        // Simula a configuração da interface do usuário, substitua pela lógica real
        Thread.sleep(800);  // Simulando o tempo de configuração da interface
        System.out.println("Interface do usuário configurada.");
    }
}
