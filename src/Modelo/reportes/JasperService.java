package Modelo.reportes;

import Modelo.Conexion;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.Date;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.HashMap;
import java.util.Map;
import javax.swing.JOptionPane;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.view.JasperViewer;

public class JasperService {

    private final Conexion conexion;
    private static final DateTimeFormatter DATE_FMT = DateTimeFormatter.ofPattern("yyyy-MM-dd");

    public JasperService() {
        this.conexion = new Conexion();
    }

    public void verReporte(String jrxmlResourcePath, Map<String, Object> parametros) {
        Connection conn = null;
        try {
            conn = conexion.getConxion();
            if (conn == null) {
                JOptionPane.showMessageDialog(null,
                        "No se pudo abrir conexión a la base de datos.\nRevise `Modelo/Conexion.java`.",
                        "Conexión fallida", JOptionPane.ERROR_MESSAGE);
                return;
            }

            InputStream jrxmlStream = getClass().getResourceAsStream(jrxmlResourcePath);
            if (jrxmlStream == null) {
                JOptionPane.showMessageDialog(null,
                        "No se encontró el reporte: " + jrxmlResourcePath,
                        "Reporte no encontrado", JOptionPane.ERROR_MESSAGE);
                return;
            }

            JasperReport report = JasperCompileManager.compileReport(jrxmlStream);
            JasperPrint print = JasperFillManager.fillReport(report,
                    parametros != null ? parametros : new HashMap<>(),
                    conn);

            JasperViewer viewer = new JasperViewer(print, false);
            viewer.setVisible(true);
        } catch (JRException ex) {
            JOptionPane.showMessageDialog(null,
                    "Error al generar reporte Jasper.\n" + ex.getMessage(),
                    "JasperReports", JOptionPane.ERROR_MESSAGE);
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null,
                    "Error inesperado al generar reporte.\n" + ex.getMessage(),
                    "Error", JOptionPane.ERROR_MESSAGE);
        } finally {
            if (conn != null) {
                try {
                    conn.close();
                } catch (Exception ignored) {
                }
            }
        }
    }

    public static Date parseSqlDateOrNull(String value) {
        if (value == null) {
            return null;
        }
        String v = value.trim();
        if (v.isEmpty() || v.equalsIgnoreCase("AAAA-MM-DD")) {
            return null;
        }
        try {
            LocalDate ld = LocalDate.parse(v, DATE_FMT);
            return Date.valueOf(ld);
        } catch (DateTimeParseException ex) {
            return null;
        }
    }

    public static Map<String, Object> params(Object... kvPairs) {
        HashMap<String, Object> map = new HashMap<>();
        if (kvPairs == null) {
            return map;
        }
        for (int i = 0; i + 1 < kvPairs.length; i += 2) {
            Object k = kvPairs[i];
            Object v = kvPairs[i + 1];
            if (k != null) {
                map.put(String.valueOf(k), v);
            }
        }
        return map;
    }
}

