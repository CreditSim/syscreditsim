package panel.form.popupbar;

import com.formdev.flatlaf.FlatClientProperties;
import raven.popup.component.PopupCallbackAction;
import raven.popup.component.PopupController;
import raven.popup.component.SimplePopupBorder;

import javax.swing.JButton;
import java.awt.*;
import java.util.function.BiConsumer;

public class CustomPopupBorder extends SimplePopupBorder {

    // Construtor da sua nova classe
    public CustomPopupBorder(Component create, String title, String[] actions, BiConsumer<PopupController, Integer> callback) {
        super(create, title, actions, (controller, index) -> {
            // Chama o callback que você passou
            callback.accept(controller, index);
        });
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        Graphics2D g2d = (Graphics2D) g.create();

        // Define a cor de fundo
        g2d.setColor(Color.WHITE);

        // Preenche todo o fundo do componente com a cor de fundo
        g2d.fillRect(0, 0, getWidth(), getHeight());

        // Desenha a borda
        /*g2d.setStroke(new BasicStroke(2));
        g2d.setColor(Color.BLACK);  // Cor da borda
        g2d.drawRect(0, 0, getWidth() - 1, getHeight() - 1);*/

        g2d.dispose();
    }

    // Método para aplicar propriedades personalizadas aos botões
    public void customizeButton(JButton button, boolean isCancel) {
        if (isCancel) {
            // Aplicar propriedades FlatLaf específicas para o botão de cancelar
            button.putClientProperty(FlatClientProperties.BUTTON_TYPE, FlatClientProperties.BUTTON_TYPE_ROUND_RECT);
            button.setBackground(Color.RED);
            button.setForeground(Color.WHITE);
        } else {
            // Propriedades de estilo para o botão "Salvar"
            button.putClientProperty(FlatClientProperties.BUTTON_TYPE, FlatClientProperties.BUTTON_TYPE_SQUARE);
            button.setBackground(Color.GREEN);
            button.setForeground(Color.BLACK);
        }
    }
}
