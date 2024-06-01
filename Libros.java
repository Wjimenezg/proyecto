import java.util.ArrayList;
import java.util.List;

public class Libros {
    private List<HojaDeCalculo> sheets;

    public Libros() {
        sheets = new ArrayList<>();
        addSheet(10, 10); // Añadir la primera hoja por defecto
    }

    public void addSheet(int filas, int columnas) {
        sheets.add(new HojaDeCalculo(filas, columnas));
    }

    public HojaDeCalculo getSheet(int index) {
        if (index >= 0 && index < sheets.size()) {
            return sheets.get(index);
        } else {
            throw new IndexOutOfBoundsException("Sheet index out of range.");
        }
    }

    public int getSheetCount() {
        return sheets.size();
    }
}
