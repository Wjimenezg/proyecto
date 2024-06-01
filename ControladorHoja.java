import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ControladorHoja {
    private Libros lib;
    private VistaDeHoja vista;

    public ControladorHoja(Libros lib, VistaDeHoja vista) {
        this.lib = lib;
        this.vista = vista;

        // Acción para aplicar fórmulas
        vista.formulaField.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String formula = vista.formulaField.getText();
                if (formula.startsWith("=")) {
                    // Procesar la fórmula
                    evaluateFormula(formula.substring(1)); // Quitar el signo '='
                }
            }
        });
    }

    private void evaluateFormula(String formula) {
        try {
            String[] tokens = formula.split("[*+/\\-]");
            String operator = formula.replaceAll("[^*+/\\-]", "");

            if (tokens.length == 2) {
                String celdaExcel1 = tokens[0].trim();
                String celdaExcel2 = tokens[1].trim();
                int fila1 = Integer.parseInt(celdaExcel1.substring(1)) - 1;
                int columna1 = celdaExcel1.charAt(0) - 'A';
                int fila2 = Integer.parseInt(celdaExcel2.substring(1)) - 1;
                int columna2 = celdaExcel2.charAt(0) - 'A';

                double numero1 = getNumericceldaExcelnumero(lib.getSheet(vista.getSelectedSheetIndex()).getceldaExcel(fila1, columna1));
                double numero2 = getNumericceldaExcelnumero(lib.getSheet(vista.getSelectedSheetIndex()).getceldaExcel(fila2, columna2));

                double result = 0;
                switch (operator) {
                    case "*":
                        result = numero1 * numero2;
                        break;
                    case "+":
                        result = numero1 + numero2;
                        break;
                    case "-":
                        result = numero1 - numero2;
                        break;
                    case "/":
                        result = numero1 / numero2;
                        break;
                }

                lib.getSheet(vista.getSelectedSheetIndex()).setceldaExcel(fila1, columna1, result);
                vista.getTable().repaint();
            }
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(vista, "Error en la fórmula", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private double getNumericceldaExcelnumero(celdaExcel celdaExcel) {
        Object numero = celdaExcel.getnumero();
        if (numero instanceof Number) {
            return ((Number) numero).doubleValue();
        } else if (numero instanceof String) {
            try {
                return Double.parseDouble((String) numero);
            } catch (NumberFormatException e) {
                return 0;
            }
        }
        return 0;
    }

    public static void main(String[] args) {
        Libros lib = new Libros();
        VistaDeHoja vista = new VistaDeHoja(lib);
        new ControladorHoja(lib, vista);
    }
}
