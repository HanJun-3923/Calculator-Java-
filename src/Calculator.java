import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;

import javax.swing.*;



public class Calculator extends JFrame {
    private JTextField inputSpace;
    private String num = "";
    private ArrayList<String> equation = new ArrayList<String>();
    // 배열과 유사하지만 크기가 유동적으로 변한다는 장점을 지님.

    public Calculator() {
        setLayout(null);

        inputSpace = new JTextField();
        inputSpace.setEditable(false);
        inputSpace.setBackground(Color.WHITE);
        inputSpace.setHorizontalAlignment(JTextField.RIGHT);
        inputSpace.setFont(new Font("Arial", Font.BOLD, 50));
        inputSpace.setBounds(8, 10, 270, 70);

        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new GridLayout(4, 4, 10, 10));
        buttonPanel.setBounds(8, 90, 270, 235);
        
        String button_names[] = {"C", "÷", "x", "=", "7", "8", "9", "+", "4", "5", "6", "-", "1", "2", "3", "0"};
        JButton buttons[] = new JButton[button_names.length];

        for (int i = 0; i < button_names.length; i++) {
            buttons[i] = new JButton(button_names[i]);
            buttons[i].setFont(new Font("Arial", Font.BOLD, 20));
            
            if(button_names[i] == "C") buttons[i].setBackground(Color.RED);
            else if ((4 <= i && i <= 6) || (8 <= i && i <= 10) || (12 <= i && i <= 14)) buttons[i].setBackground(Color.BLACK);
            else buttons[i].setBackground(Color.GRAY);
            
            buttons[i].setForeground(Color.WHITE);
            buttons[i].setBorderPainted(false);
            buttons[i].setOpaque(true);
            buttons[i].addActionListener(new PadActionListener());
            buttonPanel.add(buttons[i]);
        
        }

        add(inputSpace);
        add(buttonPanel);

        setTitle("Calculator");
        setVisible(true);
        setSize(300, 370);
        setLocationRelativeTo(null);
        setResizable(false);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }
    class PadActionListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            String operation = e.getActionCommand();
            if (operation.equals("C" )) {
                inputSpace.setText("");
            } else if (operation.equals("=")) {
                String result = Double.toString(calculate(inputSpace.getText()));
                inputSpace.setText("" + result);
                num = "";
            } else {
                inputSpace.setText(inputSpace.getText() + e.getActionCommand());
            }
        }
    }

    private void fullTextParsing(String inputText) {
        equation.clear();
        for (int i = 0; i < inputText.length(); i++) {
            char ch = inputText.charAt(i);
            if(ch == '+' || ch == '-' || ch == 'x' || ch == '÷') {
                equation.add(num);
                num = "";
                equation.add(ch + ""); // char -> string 과정을 거치지 않아도 문자열 변환해줌
            } else {
                num = num + ch;
            }
        }
        equation.add(num);
    }

    public double calculate(String inputText) {
        fullTextParsing(inputText);

        double prev = 0;
        double current = 0;
        String mode = "";

        for (String s : equation) {
            if(s.equals("+")) { mode = "add"; }
            else if(s.equals("-")) { mode = "sub"; }
            else if(s.equals("x")) { mode = "mul"; }
            else if(s.equals("÷")) { mode = "div"; }
            else {
                current = Double.parseDouble(s);
                if(mode == "add") { prev += current; }
                else if(mode == "sub") { prev -= current; }
                else if(mode == "mul") { prev *= current; }
                else if(mode == "div") { prev /= current; }
                else { prev = current; } // 첫번째 숫자
            }
        }
        return prev;
    }
    

    public static void main(String[] args) {
        new Calculator();
    }
}
