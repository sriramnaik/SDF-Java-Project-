package CalculatorUI;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.geom.Ellipse2D;
import arbitraryarithmetic.*;

public class CalculatorUI {
    private JTextArea primaryArea;
    private JTextArea secondaryArea;
    private JFrame frame;
    private StringBuilder expression = new StringBuilder();
    String operator = "";
    String num1 = "";
    String num2 = "";
    boolean num1dec = false;
    boolean num2dec = false;

    public CalculatorUI() {
        frame = new JFrame("Calculator");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(400, 600);
        frame.setLocationRelativeTo(null);
        frame.getContentPane().setBackground(Color.BLACK);
        frame.setLayout(new BorderLayout());

        secondaryArea = new JTextArea();
        secondaryArea.setEditable(false);
        secondaryArea.setBackground(Color.BLACK);
        secondaryArea.setForeground(Color.LIGHT_GRAY);
        secondaryArea.setFont(new Font("Consolas", Font.PLAIN, 20));
        secondaryArea.setLineWrap(true);
        secondaryArea.setWrapStyleWord(true);
        secondaryArea.setOpaque(true);
        secondaryArea.setBorder(BorderFactory.createEmptyBorder(10, 10, 0, 10));

        primaryArea = new JTextArea();
        primaryArea.setEditable(false);
        primaryArea.setBackground(Color.BLACK);
        primaryArea.setForeground(Color.WHITE);
        primaryArea.setFont(new Font("Consolas", Font.BOLD, 36));
        primaryArea.setLineWrap(true);
        primaryArea.setWrapStyleWord(true);
        primaryArea.setOpaque(true);
        primaryArea.setBorder(BorderFactory.createEmptyBorder(0, 10, 10, 10));
        // primaryArea.setBorder(BorderFactory.createEmptyBorder())

        JPanel displayPanel = new JPanel(new GridLayout(2, 1));
        displayPanel.setBackground(Color.BLACK);
        displayPanel.add(secondaryArea);
        displayPanel.add(primaryArea);
        frame.add(displayPanel, BorderLayout.NORTH);

        String[] buttonLabels = {
            "AC","%","del","/",
            "7", "8", "9", "*",
            "4", "5", "6", "-",
            "1", "2", "3", "+",
            "0", "00", ".", "="
        };

        JPanel buttonPanel = new JPanel(new GridLayout(5, 4, 10, 10));
        buttonPanel.setBackground(new Color(40, 44, 52));
        buttonPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        for (String label : buttonLabels) {
            RoundButton button = new RoundButton(label);
            button.setPreferredSize(new Dimension(60, 60));
            buttonPanel.add(button);
            button.addActionListener(new ButtonClickListener());
        }

        frame.add(buttonPanel, BorderLayout.CENTER);
        frame.setVisible(true);
    }

    private class ButtonClickListener implements ActionListener {
        
        @Override
        public void actionPerformed(ActionEvent e) {
            String cmd = ((JButton) e.getSource()).getText();
            
            switch (cmd) {
                case "AC":
                    expression.setLength(0);
                    secondaryArea.setText("");
                    primaryArea.setText("");
                    break;
                case "del":
                    if (expression.length() > 0) {
                        expression.deleteCharAt(expression.length() - 1);
                        primaryArea.setText(expression.toString());
                    }
                    break;
                case "=":
                    if(num2.equals("")){
                        secondaryArea.setText(expression.toString() + " =");
                        primaryArea.setText(expression.toString() + " =");
                        // expression.setLength(0);
                        // expression.append(result);
                    }
                    else{
                    try {
                        String result  = "";
                        switch (operator) {
                            case "+":
                                if(num1dec || num2dec){
                                    result =  new AFloat(num1).add(new AFloat(num2)).getString();
                                }
                                else{
                                    result = new AInteger(num1).add(new AInteger(num2)).getString();
                                }
                                break;

                            case "-":
                                if(num1dec || num2dec){
                                    result = new AFloat(num1).sub(new AFloat(num2)).getString();
                                }
                                else{
                                    result = new AFloat(num1).sub(new AFloat(num2)).getString();
                                }
                                break;

                            case "*":
                                if(num1dec || num2dec){
                                    result = new AFloat(num1).mul(new AFloat(num2)).getString();
                                }
                                else { 
                                    result = new AInteger(num1).mul(new AInteger(num2)).getString();
                                }
                                break;
                            
                            case "/":
                                if(num1dec || num2dec){
                                    result = new AFloat(num1).div(new AFloat(num2)).getString();
                                }else {
                                    result = new AInteger(num1).div(new AInteger(num2)).getString();
                                }
                                break;
                        
                            default:
                                break;
                        }
                        secondaryArea.setText(expression.toString() + " =");
                        primaryArea.setText(String.valueOf(result));
                        expression.setLength(0);
                        expression.append(result);
                        num1 = result;
                        num2 = "";
                        operator = "";
                    } catch (Exception ex) {
                        primaryArea.setText("Error");
                    }}
                    break;
                case "-":
                    if(expression.length() == 0){
                        expression.append("-");
                        num1 += "-";
                    }
                    else if(!operator.isEmpty() && num2.isEmpty() ) {
                        expression.append(" -");
                        num2 = "-";
                    }
                    else if (operator.isEmpty()){
                        operator = cmd;
                        expression.append(" - ");
                    }
                    break;
                case "+":
                case "*":
                case "/":
                case "%":
                    if (!num1.isEmpty() && operator.isEmpty()) {
                        operator = cmd;
                        expression.append(" ").append(cmd).append(" ");
                    }
                    break;
                case ".":
                    if(operator.isEmpty()){
                       num1dec = true; 
                       num1+=cmd;
                    }
                    else if(!operator.isEmpty()){
                        num2dec = true;
                        num2 += cmd;
                    }
                    expression.append(cmd);
                    break;

                default:
                    if(operator == ""){
                        num1 += cmd;
                    }
                    else { 
                        num2 += cmd;
                    }
                    expression.append(cmd);
                    break;
                }
            primaryArea.setText(expression.toString());
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(CalculatorUI::new);
    }
}

class RoundButton extends JButton {
    public RoundButton(String label) {
        super(label);
        setContentAreaFilled(false);
        setFocusPainted(false);
        setBorderPainted(false);
        setForeground(Color.WHITE);
        setBackground(new Color(75, 110, 175));
        setFont(new Font("Consolas", Font.BOLD, 20));
        setHorizontalAlignment(SwingConstants.CENTER);
        setVerticalAlignment(SwingConstants.CENTER);
    }

    @Override
    protected void paintComponent(Graphics g) {
        Graphics2D g2 = (Graphics2D) g.create();
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2.setColor(getBackground());
        g2.fillOval(0, 0, getWidth(), getHeight());
        super.paintComponent(g);
        g2.dispose();
    }

    @Override
    public void paintBorder(Graphics g) {
        g.setColor(getForeground());
        g.drawOval(0, 0, getWidth() - 1, getHeight() - 1);
    }

    @Override
    public boolean contains(int x, int y) {
        Ellipse2D circle = new Ellipse2D.Float(0, 0, getWidth(), getHeight());
        return circle.contains(x, y);
    }
}
