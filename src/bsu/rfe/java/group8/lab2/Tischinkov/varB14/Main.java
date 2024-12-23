package bsu.rfe.java.group8.lab2.Tischinkov.varB14;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Toolkit;
import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JRadioButton;
import javax.swing.JTextField;

class MainFrame extends JFrame {
    private static final int WIDTH = 900;
    private static final int HEIGHT = 820;

    private JTextField textFieldX;
    private JTextField textFieldY;
    private JTextField textFieldZ;
    private JTextField textFieldResult;
    private final ButtonGroup radioButtons = new ButtonGroup();
    private final Box hboxFormulaType = Box.createHorizontalBox();
    private int formulaId = 1;
    private Double sum = 0.0; // Переменная для накопления суммы

    public Double calculate1(Double x, Double y, Double z) {
        double numerator = 1 / Math.sqrt(x) + Math.cos(Math.exp(y)) + Math.cos(Math.pow(z, 2));
        double denominator = Math.cbrt(Math.pow(Math.log(1 + z), 2) + Math.sqrt(Math.exp(Math.cos(y))) + Math.pow(Math.sin(Math.PI * x), 2));
        return numerator / denominator;
    }

    public Double calculate2(Double x, Double y, Double z) {
        return Math.pow(y + Math.pow(x, 3) / Math.log(z), 1 / z);
    }

    private void addRadioButton(String buttonName, final int formulaId) {
        JRadioButton button = new JRadioButton(buttonName);
        button.addActionListener(ev -> MainFrame.this.formulaId = formulaId);
        radioButtons.add(button);
        hboxFormulaType.add(button);
    }

    public MainFrame() {
        super("Вычисление формулы");
        setSize(WIDTH, HEIGHT);
        Toolkit kit = Toolkit.getDefaultToolkit();
        setLocation((kit.getScreenSize().width - WIDTH) / 2,
                (kit.getScreenSize().height - HEIGHT) / 2);

        hboxFormulaType.add(Box.createHorizontalGlue());
        addRadioButton("Формула 1", 1);
        addRadioButton("Формула 2", 2);
        radioButtons.setSelected(
                radioButtons.getElements().nextElement().getModel(), true);
        hboxFormulaType.add(Box.createHorizontalGlue());
        hboxFormulaType.setBorder(
                BorderFactory.createLineBorder(Color.YELLOW));

        JLabel labelForX = new JLabel("X:");
        textFieldX = new JTextField("0", 10);
        textFieldX.setMaximumSize(textFieldX.getPreferredSize());
        JLabel labelForY = new JLabel("Y:");
        textFieldY = new JTextField("0", 10);
        textFieldY.setMaximumSize(textFieldY.getPreferredSize());
        JLabel labelForZ = new JLabel("Z:");
        textFieldZ = new JTextField("0", 10);
        textFieldZ.setMaximumSize(textFieldZ.getPreferredSize());

        Box hboxVariables = Box.createHorizontalBox();
        hboxVariables.setBorder(BorderFactory.createLineBorder(Color.RED));

        hboxVariables.add(Box.createHorizontalStrut(30)); // Пробел перед Z
        hboxVariables.add(labelForZ);
        hboxVariables.add(Box.createHorizontalStrut(10));
        hboxVariables.add(textFieldZ);
        hboxVariables.add(Box.createHorizontalGlue()); // Пробел между Z и Y
        hboxVariables.add(labelForY);
        hboxVariables.add(Box.createHorizontalStrut(10));
        hboxVariables.add(textFieldY);
        hboxVariables.add(Box.createHorizontalGlue()); // Пробел между Y и X
        hboxVariables.add(labelForX);
        hboxVariables.add(Box.createHorizontalStrut(10));
        hboxVariables.add(textFieldX);
        hboxVariables.add(Box.createHorizontalStrut(30)); // Пробел после X

        JLabel labelForResult = new JLabel("Результат:");
        textFieldResult = new JTextField("0", 20); // Увеличиваем ширину поля результата
        textFieldResult.setMaximumSize(textFieldResult.getPreferredSize());
        Box hboxResult = Box.createHorizontalBox();
        hboxResult.add(Box.createHorizontalGlue()); // клей для результата <
        hboxResult.add(labelForResult);
        hboxResult.add(Box.createHorizontalStrut(10));
        hboxResult.add(textFieldResult);
        hboxResult.add(Box.createHorizontalGlue()); //клей для результата >
        hboxResult.setBorder(BorderFactory.createLineBorder(Color.BLUE));

        JButton buttonCalc = createCalcButton();
        JButton buttonReset = createResetButton();
        JButton buttonMC = createMCButton();
        JButton buttonMPlus = createMPlusButton();

        // Размещение кнопок в горизонтальном контейнере
        Box hboxButtons = Box.createHorizontalBox();
        hboxButtons.add(Box.createHorizontalGlue()); // клей для кнопок <
        hboxButtons.add(buttonCalc);
        hboxButtons.add(Box.createHorizontalStrut(30));
        hboxButtons.add(buttonReset);
        hboxButtons.add(Box.createHorizontalStrut(30));
        hboxButtons.add(buttonMC);
        hboxButtons.add(Box.createHorizontalStrut(30));
        hboxButtons.add(buttonMPlus);
        hboxButtons.add(Box.createHorizontalGlue()); // клей для кнопок >
        hboxButtons.setBorder(BorderFactory.createLineBorder(Color.GREEN));

        // Объединение всех компонентов в вертикальном контейнере
        Box contentBox = Box.createVerticalBox();
        contentBox.add(Box.createVerticalGlue());
        contentBox.add(hboxFormulaType);
        contentBox.add(hboxVariables);
        contentBox.add(hboxResult);
        contentBox.add(hboxButtons);
        contentBox.add(Box.createVerticalGlue());
        getContentPane().add(contentBox, BorderLayout.CENTER);
    }

    private JButton createCalcButton() {
        JButton buttonCalc = new JButton("Вычислить");
        buttonCalc.addActionListener(ev -> {
            try {
                Double x = Double.parseDouble(textFieldX.getText());
                Double y = Double.parseDouble(textFieldY.getText());
                Double z = Double.parseDouble(textFieldZ.getText());
                Double result;
                if (formulaId == 1)
                    result = calculate1(x, y, z);
                else
                    result = calculate2(x, y, z);
                textFieldResult.setText(result.toString());
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(MainFrame.this,
                        "Ошибка в формате записи числа с плавающей точкой",
                        "Ошибочный формат числа", JOptionPane.WARNING_MESSAGE);
            }
        });
        return buttonCalc;
    }

    private JButton createResetButton() {
        JButton buttonReset = new JButton("Очистить поля");
        buttonReset.addActionListener(ev -> {
            textFieldX.setText("0");
            textFieldY.setText("0");
            textFieldZ.setText("0");
            textFieldResult.setText("0");
        });
        return buttonReset;
    }

    private JButton createMCButton() {
        JButton buttonMC = new JButton("MC");
        buttonMC.addActionListener(ev -> {
            sum = 0.0;
            textFieldResult.setText(sum.toString());
        });
        return buttonMC;
    }

    private JButton createMPlusButton() {
        JButton buttonMPlus = new JButton("M+");
        buttonMPlus.addActionListener(ev -> {
            try {
                Double x = Double.parseDouble(textFieldX.getText());
                Double y = Double.parseDouble(textFieldY.getText());
                Double z = Double.parseDouble(textFieldZ.getText());
                Double result;
                if (formulaId == 1)
                    result = calculate1(x, y, z);
                else
                    result = calculate2(x, y, z);
                // Сложение результата с переменной sum
                sum += result;
                // Обновление текстового поля результата
                textFieldResult.setText(sum.toString());
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(MainFrame.this,
                        "Ошибка в формате записи числа с плавающей точкой",
                        "Ошибочный формат числа", JOptionPane.WARNING_MESSAGE);
            }
        });
        return buttonMPlus;
    }

    public static void main(String[] args) {
        MainFrame frame = new MainFrame();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
    }
}
