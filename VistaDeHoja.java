import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.table.AbstractTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class VistaDeHoja extends JFrame {
    private Libros Libros;
    private JTabbedPane tabbedPane;
    public JTextField formulaField;
    private JTable table;
    private SpreadsheetTableModel tableModel;

    public VistaDeHoja(Libros Libros) {
        this.Libros = Libros;

        setLayout(new BorderLayout());

        // Campo de texto para la fórmula
        formulaField = new JTextField();
        add(formulaField, BorderLayout.NORTH);

        // Pestañas para las hojas
        tabbedPane = new JTabbedPane();
        updateSheetTabs();
        tabbedPane.addChangeListener(new ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent e) {
                int selectedIndex = tabbedPane.getSelectedIndex();
                if (selectedIndex >= 0 && selectedIndex < Libros.getSheetCount()) {
                    tableModel.setSpreadsheet(Libros.getSheet(selectedIndex));
                    table.repaint(); // Asegurarnos de que la tabla se repinta al cambiar de Libros
                }
            }
        });

        // Tabla para mostrar los datos
        if (Libros.getSheetCount() > 0) {
            this.tableModel = new SpreadsheetTableModel(Libros.getSheet(0));
        } else {
            this.tableModel = new SpreadsheetTableModel(new HojaDeCalculo(10, 10));
        }
        this.table = new JTable(tableModel);
        add(new JScrollPane(table), BorderLayout.CENTER);

        // Añadir pestañas al panel principal
        add(tabbedPane, BorderLayout.SOUTH);

        // Botón para añadir nuevas hojas
        JButton addSheetButton = new JButton("+");
        addSheetButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Libros.addSheet(10, 10); // Añadir un Libro nuevo
                updateSheetTabs(); // Actualizar las pestañas de hojas
            }
        });
        add(addSheetButton, BorderLayout.EAST);

        setTitle("Hoja de Cálculo");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);
    }

    public void updateSheetTabs() {
        tabbedPane.removeAll();
        for (int i = 0; i < Libros.getSheetCount(); i++) {
            tabbedPane.addTab("Hoja " + (i + 1), new JPanel());
        }
        // Seleccionar la última hoja añadida
        if (Libros.getSheetCount() > 0) {
            tabbedPane.setSelectedIndex(Libros.getSheetCount() - 1);
        }
    }

    public JTable getTable() {
        return table;
    }

    public JTabbedPane getTabbedPane() {
        return tabbedPane;
    }

    public int getSelectedSheetIndex() {
        return tabbedPane.getSelectedIndex();
    }

    public static class SpreadsheetTableModel extends AbstractTableModel {
        private HojaDeCalculo hojaDeCalculo;

        public SpreadsheetTableModel(HojaDeCalculo hojaDeCalculo) {
            this.hojaDeCalculo = hojaDeCalculo;
        }

        public void setSpreadsheet(HojaDeCalculo hojaDeCalculo) {
            this.hojaDeCalculo = hojaDeCalculo;
            fireTableStructureChanged();
        }

        @Override
        public int getRowCount() {
            return hojaDeCalculo.getfilas();
        }

        @Override
        public int getColumnCount() {
            return hojaDeCalculo.getcolumnas();
        }

        @Override
        public Object getValueAt(int rowIndex, int columnIndex) {
            celdaExcel celdaExcel = hojaDeCalculo.getceldaExcel(rowIndex, columnIndex);
            return celdaExcel.getnumero() == null ? "" : celdaExcel.getnumero();
        }

        @Override
        public void setValueAt(Object aValue, int rowIndex, int columnIndex) {
            hojaDeCalculo.setceldaExcel(rowIndex, columnIndex, aValue);
            fireTableCellUpdated(rowIndex, columnIndex);
        }

        @Override
        public boolean isCellEditable(int rowIndex, int columnIndex) {
            return true;
        }
    }
}
