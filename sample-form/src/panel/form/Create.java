package panel.form;

import java.sql.Date;
import com.formdev.flatlaf.FlatClientProperties;
import panel.model.ModelEmployee;
import panel.model.ModelPositions;
import panel.service.ServiceEmployee;
import raven.datetime.component.date.DatePicker;

import javax.swing.*;
import java.awt.*;
import java.text.NumberFormat;
import java.text.ParseException;
import java.util.Locale;
import java.util.Optional;

public class Create extends JPanel {

    private DatePicker datePicker;
    private JTextField txtName;
    private JTextField txtLocation;
    private JComboBox<ModelPositions> comboPosition;
    private JTextArea txtDescription;
    private JFormattedTextField txtSalary, txtDate;
    private JScrollPane jScrollPane1;
    private JLabel jLabel1, jLabel2, jLabel3, jLabel4, jLabel5, jLabel6;

    public Create() {
        setBackground(Color.WHITE);
        initComponents();
        datePicker.setCloseAfterSelected(true);
        datePicker.setEditor(txtDate);
    }

    public void loadData(ServiceEmployee service, ModelEmployee data) {
        try {
            service.getServicePositions().getAll().forEach(pos -> {
                comboPosition.addItem(pos);
                if (data != null && data.getPositions().getPositionsId() == pos.getPositionsId()) {
                    comboPosition.setSelectedItem(pos);
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }

        if (data != null) {
            populateFields(data);
        }
    }

    private void populateFields(ModelEmployee data) {
        txtName.setText(data.getName());
        txtLocation.setText(data.getLocation());
        Optional.ofNullable(data.getDate())
                .ifPresent(date -> datePicker.setSelectedDate(date.toLocalDate()));
        txtSalary.setValue(data.getSalary());
        txtDescription.setText(data.getDescription());
    }

    private void initComponents() {
        datePicker = new DatePicker();

        // Iniciando os componentes
        txtName = new JTextField();
        txtLocation = new JTextField();
        comboPosition = new JComboBox<>();
        txtDescription = new JTextArea(5, 20);
        txtSalary = new JFormattedTextField(NumberFormat.getNumberInstance());
        txtDate = new JFormattedTextField();
        jScrollPane1 = new JScrollPane(txtDescription);
        jLabel1 = new JLabel("Cliente");
        jLabel2 = new JLabel("Endereço");
        jLabel3 = new JLabel("Data");
        jLabel4 = new JLabel("Valor");
        jLabel5 = new JLabel("Vendedor");
        jLabel6 = new JLabel("Produto");

        // Aplicando os estilos FlatLaf
        applyFlatLafStyles();

        // Configurando o layout
        setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 15, 5, 15); // Reduzindo o espaço entre componentes

        // Adicionando componentes com uma função de utilidade
        Dimension inputSize = new Dimension(250, 30);
        configureComponentSize(inputSize);

        // Adicionar componentes ao layout
        addComponentsToLayout(gbc);

        // Definindo tamanhos fixos para o painel
        setPreferredSize(new Dimension(500, 500));
    }

    private void configureComponentSize(Dimension size) {
        txtName.setPreferredSize(size);
        txtLocation.setPreferredSize(size);
        txtSalary.setPreferredSize(size);
        txtDate.setPreferredSize(size);
        comboPosition.setPreferredSize(size);
    }

    private void addComponentsToLayout(GridBagConstraints gbc) {
        addComponent(jLabel1, gbc, 0, 0, 1, 0, GridBagConstraints.HORIZONTAL);
        addComponent(txtName, gbc, 1, 0, 1, 0, GridBagConstraints.HORIZONTAL);

        addComponent(jLabel2, gbc, 0, 1, 1, 0, GridBagConstraints.HORIZONTAL);
        addComponent(txtLocation, gbc, 1, 1, 1, 0, GridBagConstraints.HORIZONTAL);

        addComponent(jLabel3, gbc, 0, 2, 1, 0, GridBagConstraints.HORIZONTAL);
        addComponent(txtDate, gbc, 1, 2, 1, 0, GridBagConstraints.HORIZONTAL);

        addComponent(jLabel4, gbc, 0, 3, 1, 0, GridBagConstraints.HORIZONTAL);
        addComponent(txtSalary, gbc, 1, 3, 1, 0, GridBagConstraints.HORIZONTAL);

        addComponent(jLabel5, gbc, 0, 4, 1, 0, GridBagConstraints.HORIZONTAL);
        addComponent(comboPosition, gbc, 1, 4, 1, 0, GridBagConstraints.HORIZONTAL);

        addComponent(jLabel6, gbc, 0, 5, 1, 0, GridBagConstraints.HORIZONTAL);
        gbc.gridwidth = 2;
        addComponent(jScrollPane1, gbc, 0, 6, 1, 1, GridBagConstraints.BOTH);
    }

    private void addComponent(JComponent comp, GridBagConstraints gbc, int x, int y, double weightx, double weighty, int fill) {
        gbc.gridx = x;
        gbc.gridy = y;
        gbc.weightx = weightx;
        gbc.weighty = weighty;
        gbc.fill = fill;
        add(comp, gbc);
    }

    private void applyFlatLafStyles() {
        String style = "arc:5; borderColor:$Button.borderColor; focusWidth:1; borderWidth:1";
        applyStyleToComponents(style);

        // Adicionando focusWidth nas labels
        applyLabelStyles();
    }

    private void applyStyleToComponents(String style) {
        txtName.putClientProperty(FlatClientProperties.STYLE, style);
        txtLocation.putClientProperty(FlatClientProperties.STYLE, style);
        txtSalary.putClientProperty(FlatClientProperties.STYLE, style);
        txtDate.putClientProperty(FlatClientProperties.STYLE, style);
        comboPosition.putClientProperty(FlatClientProperties.STYLE, style);
        txtDescription.putClientProperty(FlatClientProperties.STYLE, style);
        jScrollPane1.putClientProperty(FlatClientProperties.STYLE, style);
    }

    private void applyLabelStyles() {
        jLabel1.putClientProperty(FlatClientProperties.STYLE, "focusWidth:1; borderWidth:1");
        jLabel2.putClientProperty(FlatClientProperties.STYLE, "focusWidth:1; borderWidth:1");
        jLabel3.putClientProperty(FlatClientProperties.STYLE, "focusWidth:1; borderWidth:1");
        jLabel4.putClientProperty(FlatClientProperties.STYLE, "focusWidth:1; borderWidth:1");
        jLabel5.putClientProperty(FlatClientProperties.STYLE, "focusWidth:1; borderWidth:1");
        jLabel6.putClientProperty(FlatClientProperties.STYLE, "focusWidth:1; borderWidth:1");
    }

    public ModelEmployee getData() {
        String name = txtName.getText().trim();
        String location = txtLocation.getText().trim();
        Date date = datePicker.isDateSelected() ? Date.valueOf(datePicker.getSelectedDate()) : null;

        // Validação de salário com tratamento de erro
        double salary = getValidatedSalary();
        if (salary == -1) return null; // Retorna null em caso de erro na validação

        String description = txtDescription.getText().trim();
        ModelPositions positions = (ModelPositions) comboPosition.getSelectedItem();

        if (positions == null) {
            JOptionPane.showMessageDialog(this, "Selecione um vendedor válido.");
            return null;
        }

        // Criação do ModelEmployee sem atribuição automática do salário
        return new ModelEmployee(0, name, location, date, salary, description, positions);
    }

    private double getValidatedSalary() {
        if (txtSalary.getValue() != null) {
            NumberFormat nf = NumberFormat.getInstance(Locale.getDefault());
            try {
                return nf.parse(txtSalary.getText()).doubleValue();
            } catch (ParseException e) {
                JOptionPane.showMessageDialog(this, "Erro ao formatar o valor do produto.");
                return -1;
            }

        }
        return 0; // Retorna 0 se o campo estiver vazio
    }
}
