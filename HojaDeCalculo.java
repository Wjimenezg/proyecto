import java.util.ArrayList;
import java.util.List;

public class HojaDeCalculo {
    private List<List<celdaExcel>> celdaExcels;

    public HojaDeCalculo(int filas, int columnas) {
        celdaExcels = new ArrayList<>();
        for (int i = 0; i < filas; i++) {
            List<celdaExcel> row = new ArrayList<>();
            for (int j = 0; j < columnas; j++) {
                row.add(new celdaExcel());
            }
            celdaExcels.add(row);
        }
    }

    public celdaExcel getceldaExcel(int row, int column) {
        return celdaExcels.get(row).get(column);
    }

    public void setceldaExcel(int row, int column, Object value) {
        celdaExcels.get(row).get(column).setnumero(value);
    }

    public int getfilas() {
        return celdaExcels.size();
    }

    public int getcolumnas() {
        return celdaExcels.get(0).size();
    }
}
