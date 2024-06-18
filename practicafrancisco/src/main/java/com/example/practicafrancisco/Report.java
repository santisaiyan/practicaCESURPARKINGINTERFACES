package com.example.practicafrancisco;



import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.export.JRPdfExporter;
import net.sf.jasperreports.export.SimpleExporterInput;
import net.sf.jasperreports.export.SimpleOutputStreamExporterOutput;
import net.sf.jasperreports.export.SimplePdfExporterConfiguration;
import net.sf.jasperreports.swing.JRViewer;

import javax.swing.*;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.HashMap;
public class Report {


//esta es la funcion que nos enseña el reporte que hemos  subido al proyecto , tanto para que este y el de descargar
    //funcione tiene que estar conectada y funcionando la base de datos
    public void generateReport() throws SQLException, JRException {

        Connection c = MySQLConnection.getConnection();
        HashMap hm = new HashMap<>();

        var jasperPrint = JasperFillManager.fillReport("Cochesentrada.jasper",hm,c);

        var visor = new JRViewer(jasperPrint);
        JRViewer viewer = new JRViewer(jasperPrint);

        JFrame frame = new JFrame("Listado de coches");
        frame.getContentPane().add(viewer);
        frame.setExtendedState(JFrame.MAXIMIZED_BOTH);
        frame.pack();
        frame.setVisible(true);

        System.out.print("Done!");


    }



        //este es la funcion que descarga en formato pdf el reporte que hemos subido

    public void descargarreporte() throws SQLException, JRException{
        Connection c = MySQLConnection.getConnection();
        HashMap hm = new HashMap<>();

        var jasperPrint = JasperFillManager.fillReport("Cochesentrada.jasper",hm,c);

        JRPdfExporter exp = new JRPdfExporter();
        exp.setExporterInput(new SimpleExporterInput(jasperPrint));
        exp.setExporterOutput(new SimpleOutputStreamExporterOutput("coches.pdf"));
        exp.setConfiguration(new SimplePdfExporterConfiguration());
        exp.exportReport();

        System.out.print("Done!");

    }

}
