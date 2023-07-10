import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

/**
 * Write a description of class Calculator here.
 *
 * @author (your name)
 * @version (a version number or a date)
 */
public class Calculator implements ActionListener, KeyListener, MouseListener {
    private JFrame frame;
    private JLabel label;
    private boolean readyForNewNumber;
    private JButton[] numbersOneToNineButtons;
    private JButton zeroButton, addButton, subtractButton, multiplyButton,
            divideButton, deleteButton, equalsButton, clearButton, plusAndMinusButton, percentButton, squaredButton;

    public JButton makeButton(String buttonText) {
        JButton button = new JButton(buttonText);
        button.setFont(new Font("Times New Roman", Font.BOLD, 20));
        button.setFocusable(false);
        button.addActionListener(this);
        button.addMouseListener(this);
        button.setBackground(new Color(80, 80, 80));
        button.setPreferredSize(new Dimension(20, 20));
        button.setForeground(Color.white);
        return button;
    }

    public Calculator() {
        frame = new JFrame();
        frame.setDefaultCloseOperation(frame.EXIT_ON_CLOSE);
        frame.setSize(390, 600);

        label = new JLabel("0");
        label.setForeground(Color.white);
        label.setFont(new Font("Times New Roman", Font.PLAIN, 70));
        label.setHorizontalAlignment(SwingConstants.RIGHT);

        zeroButton = makeButton("0");

        numbersOneToNineButtons = new JButton[10];
        for (int i = 1; i < 10; i++) {
            numbersOneToNineButtons[i] = makeButton(String.valueOf(i));
            //"String.valueOf(i)" from "https://stackoverflow.com/questions/9814566/array-that-holds-jbutton-objects"
        }

        Color arithmeticOperationsButtonColor = new Color(255, 149, 0);

        clearButton = makeButton("AC");
        clearButton.setForeground(Color.black);
        clearButton.setBackground(new Color(212, 212, 210));

        plusAndMinusButton = makeButton("+/-");
        plusAndMinusButton.setForeground(Color.black);
        plusAndMinusButton.setBackground(new Color(212, 212, 210));

        addButton = makeButton("+");
        addButton.setBackground(arithmeticOperationsButtonColor);

        subtractButton = makeButton("-");
        subtractButton.setBackground(arithmeticOperationsButtonColor);

        multiplyButton = makeButton("x");
        multiplyButton.setBackground(arithmeticOperationsButtonColor);

        divideButton = makeButton("÷");
        divideButton.setBackground(arithmeticOperationsButtonColor);

        deleteButton = makeButton("Del");

        squaredButton = makeButton("x^2");

        percentButton = makeButton("%");
        percentButton.setBackground(new Color(212, 212, 210));
        percentButton.setForeground(Color.black);

        equalsButton = makeButton("=");
        equalsButton.setBackground(arithmeticOperationsButtonColor);
        equalsButton.addKeyListener(this);
        equalsButton.setFocusable(true);
        equalsButton.setFocusPainted(false);

        JPanel buttonsPanel = new JPanel();
        buttonsPanel.setLayout(new GridLayout(5, 4));

        buttonsPanel.add(clearButton);
        buttonsPanel.add(plusAndMinusButton);
        buttonsPanel.add(percentButton);
        buttonsPanel.add(divideButton);
        buttonsPanel.add(numbersOneToNineButtons[7]);
        buttonsPanel.add(numbersOneToNineButtons[8]);
        buttonsPanel.add(numbersOneToNineButtons[9]);
        buttonsPanel.add(multiplyButton);
        buttonsPanel.add(numbersOneToNineButtons[4]);
        buttonsPanel.add(numbersOneToNineButtons[5]);
        buttonsPanel.add(numbersOneToNineButtons[6]);
        buttonsPanel.add(subtractButton);
        buttonsPanel.add(numbersOneToNineButtons[1]);
        buttonsPanel.add(numbersOneToNineButtons[2]);
        buttonsPanel.add(numbersOneToNineButtons[3]);
        buttonsPanel.add(addButton);
        buttonsPanel.add(zeroButton);
        buttonsPanel.add(squaredButton);
        buttonsPanel.add(deleteButton);
        buttonsPanel.add(equalsButton);

        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BorderLayout());

        mainPanel.add(label, BorderLayout.NORTH);
        mainPanel.add(buttonsPanel, BorderLayout.CENTER);
        mainPanel.setBackground(new Color(80, 80, 80));

        frame.add(mainPanel);

        // frame.addWindowFocusListener(new WindowAdapter() {
        // public void windowGainedFocus(WindowEvent e) {
        // equalsButton.requestFocusInWindow();
        // }
        // });
        //from Oracle's website: https://docs.oracle.com/javase/tutorial/uiswing/misc/index.html
        /*I have commented the focus listener out because I found out it wasn't necessary; however, I did not delete it peradventure I need it in the future */

        //frame.setResizable(false);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }

    char operator;
    int secondNumber;
    int firstNumber;

    public int functions() {
        firstNumber = Integer.parseInt(label.getText().trim());
        label.setText(" ");
        return firstNumber;
    }

    public int subtract() {
        operator = '-';
        return functions();
    }

    public int add() {
        operator = '+';
        return functions();
    }

    public int multiply() {
        operator = '*';
        return functions();
    }

    public int divide() {
        operator = '/';
        return functions();
    }

    public void equals() {
        secondNumber = Integer.parseInt(label.getText().trim());
        int answer = 0;
        try {
            if (operator == '/') {
                answer = firstNumber / secondNumber;
                double initialDoubleValue = ((double) firstNumber % (double) secondNumber) / ((double) secondNumber);
                double finalDoubleValue = answer + initialDoubleValue;
                /* if the modulo of the two numbers to be divided is greater than zero, the modulo is divided by the divisor,
                 * converted to decimal and added to the integer value which then makes it a double value. This returns the precise values of
                 * calculations like '3/2' while also returning the actual values of ones like '8/4'.
                 */
                if ((firstNumber % secondNumber) > 0) {
                    label.setText(Double.toString(finalDoubleValue));
                } else {
                    label.setText(Integer.toString(answer));
                }
            }
        } catch (Exception ex) {
            label.setText("Error");
            // Just as would be diplayed in a standard calculator.
        }

        if (operator == '*') {
            answer = firstNumber * secondNumber;
            label.setText(Integer.toString(answer));
        }

        if (operator == '-') {
            answer = firstNumber - secondNumber;
            label.setText(Integer.toString(answer));
        }

        if (operator == '+') {
            answer = firstNumber + secondNumber;
            label.setText(Integer.toString(answer));
        }
        firstNumber = answer;/* This allows chain calculations to be performed*/
        //would've put in the 'label.setText(...answer)' here but that limits the full functionality of the '/' operator.
    }

    public void percent(double number) {
        number = Double.parseDouble(label.getText());
        if (label.getText() != "0") {
            label.setText(Double.toString(number / 100));
        }
        /*Since this is a function in most simple calculators, I figured it'd be a nice idea to add it to this one.
         * Divides any number by 100 and displays its double value.
         */
    }

    public void plusAndMinus(int plusAndMinusSigns, double plusAndMinusSignsDecimal) {
        try {
            if (label.getText() != "0") {
                plusAndMinusSigns = Integer.parseInt(label.getText());
                plusAndMinusSigns *= -1;
                label.setText(Integer.toString(plusAndMinusSigns));
            }
        } catch (NumberFormatException ex) {
            plusAndMinusSignsDecimal = Double.parseDouble(label.getText());
            plusAndMinusSignsDecimal *= -1;
            label.setText(Double.toString(plusAndMinusSignsDecimal));
        }
        //This function can be used in calculating negative integers.
    }

    public void delete() {
        String string = label.getText();
        label.setText("");
        for (int i = 0; i < string.length() - 1; i++) {
            label.setText(label.getText() + string.charAt(i));
            // string.charAt() from>> https://www.w3schools.com/java/ref_string_charat.asp#:~:text=The%20charAt()%20method%20returns,is%201%2C%20and%20so%20on.
        }

        if (string.length() == 1) {
            label.setText("0");
            operator = 0;
        }
        /*Although not specified in the task, the user could delete a value entered unintentionally with this function,
         *instead of clearing the whole screen.
         */
    }

    public void clear() {
        label.setText("0");
        operator = 0;
    }

    public void xSquared(int number) {
        if (label.getText() != "0") {
            number = Integer.parseInt(label.getText());
            number *= number;
            label.setText(Integer.toString(number));
        }
        //readyForNewNumber = true;
        /*This additional function gives user the square of any value. This could be more handy than explicitly finding the square
         * of the number by multiplying the number by itself.
         */
    }

    public void actionPerformed(ActionEvent e) {
        try {
            for (int i = 1; i < 10; i++) {
                if (e.getSource() == numbersOneToNineButtons[i]) {
                    clearButton.setText("C");
                    if (label.getText() == "0") {
                        label.setText(String.valueOf(i));
                    } else if (label.getText() != "0") {
                        label.setText(label.getText().trim() + String.valueOf(i));
                    }
                }
            }
            if (e.getSource() == zeroButton) {
                if (label.getText() == "0") {
                    label.setText("0");
                } else if (e.getSource() != "0") {
                    label.setText(label.getText() + zeroButton.getText());
                }
            } else if (e.getSource() == clearButton) {
                clear();
                clearButton.setText("AC");
                //You can also press the 'C' button on keyboard to clear
            } else if (e.getSource() == squaredButton) {
                xSquared(0);
            } else if (e.getSource() == percentButton) {
                percent(0.0);
            } else if (e.getSource() == deleteButton) {
                delete();
                //You can press backspace key on the keyboard to perform same function
            } else if (e.getSource() == plusAndMinusButton) {
                plusAndMinus(0, 0.0);
            } else if (e.getSource() == subtractButton) {
                subtract();
                //the minus key in the keyboard performs the same function
            } else if (e.getSource() == addButton) {
                add();
                //the plus key in the keyboard performs the same function
            } else if (e.getSource() == multiplyButton) {
                multiply();
                //the asterisk (multiply) key in the keyboard performs the same function
            } else if (e.getSource() == divideButton) {
                divide();
                //the divide key in the keyboard performs the same function
            }
            if (e.getSource() == equalsButton && operator != 0) {
                    equals();
                    operator = 0;
                    /*Intentionally set the operator to 0 so it doesn't perform more calculations after the equals button is pressed.
                     *Reasons are because: 1. It doesn't return same values as the standard calculator
                     *2. I couldn't get it to work same time with the 'readyForNewNumber' after the equals button is pressed.
                     */
                    //You can press the enter key to carry out the same function.
                    readyForNewNumber = true;
            }


        } catch (NumberFormatException ex) {
            //JOptionPane.showMessageDialog(frame, "Please enter a valid number", "Error", JOptionPane.INFORMATION_MESSAGE);
        }
    }

    //Key Listener from Oracle's website: https://docs.oracle.com/javase/tutorial/uiswing/events/keylistener.html
    public void keyPressed(KeyEvent e) {
        if (e.getKeyCode() == 10 && operator != 0) {
            try {
                equals();
                operator = 0;
                readyForNewNumber = true;
            } catch (NumberFormatException ex) {

            }
        } else if (e.getKeyCode() == 8) {
            delete();
        }
    }

    public void keyTyped(KeyEvent e) {
        if (e.getKeyChar() == 'c') {
            clear();
            clearButton.setText("AC");
        }
        if (e.getKeyChar() == '+') {
            add();
        }
        if (e.getKeyChar() == '-') {
            subtract();
        }
        if (e.getKeyChar() == '*') {
            multiply();
        }
        if (e.getKeyChar() == '/') {
            divide();
        }
    }

    public void keyReleased(KeyEvent e) {

    }

    public void mouseClicked(MouseEvent e) {

    }

    public void mousePressed(MouseEvent e) {
        if (readyForNewNumber && e.getSource()!=plusAndMinusButton&&e.getSource()!=percentButton&&e.getSource()!=squaredButton) {
            label.setText(" ");
            readyForNewNumber = false;
        }
        //from: https://stackoverflow.com/questions/49499573/calculator-clear-result-when-pressing-a-new-number
    }

    public void mouseReleased(MouseEvent e) {

    }

    public void mouseEntered(MouseEvent e) {

    }

    public void mouseExited(MouseEvent e) {

    }

    public static void main(String[] args) {
        new Calculator();
    }
}
