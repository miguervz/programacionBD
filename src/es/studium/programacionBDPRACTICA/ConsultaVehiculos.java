package es.studium.programacionBDPRACTICA;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import javax.swing.table.DefaultTableModel;

import com.itextpdf.text.Anchor;
import com.itextpdf.text.Chapter;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.Section;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import java.awt.BorderLayout;
import java.awt.Button;
import java.awt.EventQueue;
import java.awt.FlowLayout;
import java.awt.Label;
import java.awt.TextField;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import java.awt.event.ActionListener;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.awt.event.ActionEvent;
import javax.swing.ListSelectionModel;
public class ConsultaVehiculos extends JFrame {
	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					ConsultaVehiculos frame = new ConsultaVehiculos();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
	public ConsultaVehiculos() {
		setTitle("Consulta Vehículos");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 1100, 300);
		setLocationRelativeTo(null);
		setResizable(false);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(20, 20, 20, 0));
		setContentPane(contentPane);
		contentPane.setLayout(new BorderLayout(0, 0));
		JScrollPane scrollPane = new JScrollPane();
		contentPane.add(scrollPane);
		JTable table = new JTable();
		table.setSurrendersFocusOnKeystroke(true);
		table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		scrollPane.setViewportView(table);
		JDialog dNombreArchivo = new JDialog(this, "Introducir nombre", true);
		Label eNombrePDF = new Label ("Escribe el nombre del pdf.");
		TextField nombreArchivo = new TextField(20);
		dNombreArchivo.getContentPane().add(eNombrePDF);
		dNombreArchivo.getContentPane().add(nombreArchivo);
		dNombreArchivo.getContentPane().setLayout(new FlowLayout());
		JDialog dError = new JDialog(this, "Error", true);
		dError.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
		dNombreArchivo.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
		Label eError = new Label ("Comprueba que has introducido un nombre válido.");
		dError.getContentPane().add(eError);
		dError.getContentPane().setLayout(new FlowLayout());
		dError.setSize(300,150);
		dError.setLocationRelativeTo(null);
		JDialog dConfirmacion= new JDialog(this, "Confirmación", true);
		Label eConfirmacion = new Label ("Estás seguro?");
		dConfirmacion.getContentPane().add(eConfirmacion);
		dConfirmacion.getContentPane().setLayout(new FlowLayout());
		dConfirmacion.setSize(230,90);
		dConfirmacion.setLocationRelativeTo(null);
		dConfirmacion.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
		Button bSeguro = new Button("Ok");
		bSeguro.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				dConfirmacion.setVisible(false);
			}
		});
		dConfirmacion.getContentPane().add(bSeguro);
		Button bErrorpdf = new Button("Ok");
		bErrorpdf.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				dError.setVisible(false);	
			}
		});
		dError.getContentPane().add(bErrorpdf);
		Button bAtrasNombrePDF = new Button("Volver");
		bAtrasNombrePDF.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				dNombreArchivo.setVisible(false);
				nombreArchivo.setText("");
			}
		});
		dNombreArchivo.getContentPane().add(bAtrasNombrePDF);
		Button btnNewButton1 = new Button("Convertir a PDF");
		btnNewButton1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				dNombreArchivo.setVisible(true);
			}
		});
		Button okConfirmacion = new Button("ok");
		okConfirmacion.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				dConfirmacion.setVisible(true);}
		});
		Button no = new Button("No");
		no.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				dConfirmacion.setVisible(false);}
		});
		dConfirmacion.add(no);
		bSeguro.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(!nombreArchivo.getText().equals("")) {
					Document document = new Document(); 
					try {
						String nombre = nombreArchivo.getText();
						PdfWriter.getInstance(document, new FileOutputStream(nombre+".pdf"));
						document.open();
						Anchor anchor = new Anchor("VEHÍCULOS");
						anchor.setName("");
						Chapter catPart = new Chapter(new Paragraph(anchor), 1);
						Section subCatPart = catPart;
						subCatPart.add(new Paragraph("\n"));
						PdfPTable tabla = new PdfPTable(table.getColumnCount()); 
						PdfPCell columnHeader;          
						for (int column = 0; column < table.getColumnCount(); column++) {                 
							columnHeader = new PdfPCell(new Phrase(table.getColumnName(column)));
							columnHeader.setHorizontalAlignment(Element.ALIGN_CENTER);
							tabla.addCell(columnHeader);
						}
						tabla.setHeaderRows(1);              
						for (int row = 0; row < table.getRowCount(); row++) {                
							for (int column = 0; column < table.getColumnCount(); column++) { 
								tabla.addCell(table.getValueAt(row, column).toString());
							}
						} 
						subCatPart.add(tabla);
						document.add(catPart);
						document.close();          
					}catch (DocumentException documentException) {
						System.out.println("The file not exists (Se ha producido un error al generar un documento): " + documentException);
					} catch (FileNotFoundException e1) {
						e1.printStackTrace();
					}     
					dNombreArchivo.dispose();
					nombreArchivo.setText("");}
				else{
					dError.setVisible(true);
				}}});
		dNombreArchivo.setSize(300,150);
		dNombreArchivo.setLocationRelativeTo(null);
		dNombreArchivo.getContentPane().add(okConfirmacion);
		String driver = "com.mysql.jdbc.Driver";
		String url = "jdbc:mysql://localhost:3306/Bomberos?useSSL=false";
		String login = "root";
		String password = "Studium2019;";
		String sentencia = "SELECT * FROM vehiculos";
		Connection connection = null;
		Statement statement = null;
		ResultSet rs = null;
		String[] columnas = {"id",
				"Tipo Vehículo",
				"Matrícula",
				"Lugar De Estacionamiento",
				"Fecha de Compra","Precio","id Conductor"};
		table.setColumnSelectionAllowed(true);
		DefaultTableModel tabla = new DefaultTableModel() {
			private static final long serialVersionUID = 1L;
			public boolean isCellEditable(int Fila, int Colum) {
				return false;
			}
		};
		tabla.setColumnIdentifiers(columnas);
		table.setModel(tabla);
		JPanel panel = new JPanel();
		contentPane.add(panel, BorderLayout.EAST);
		panel.add(btnNewButton1);
		Button btnNewButton = new Button("Volver");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				dispose();	
			}});
		panel.add(btnNewButton);
		try
		{
			Class.forName(driver);
		}
		catch(ClassNotFoundException e)
		{
			System.out.println("Se ha producido un error al cargar el Driver");
		}
		try
		{
			connection = DriverManager.getConnection(url, login, password);
		}
		catch(SQLException e)
		{
			System.out.println("Se produjo un error al conectar a la Base de Datos");
		}
		try
		{
			statement =connection.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_READ_ONLY);
			try {
				Metodos.InfoLog("Consulta: SELECT * FROM vehiculos");
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			rs=statement.executeQuery(sentencia);
			while(rs.next())
			{
				columnas[0]=rs.getString("idVehiculo");
				columnas[1]=rs.getString("tipoVehiculo");
				columnas[2]=rs.getString("matriculaVehiculo");
				columnas[3]=rs.getString("lugarEstacionamientoVehiculo");
				columnas[4]=rs.getString("fechaCompraVehiculo");
				columnas[5]=rs.getString("precioVehiculo");
				columnas[6]=rs.getString("idBomberoFK");
				tabla.addRow(columnas);
			}
		}
		catch(SQLException e)
		{try {
			Metodos.InfoLog("[ERROR] OPERACIÓN DENEGADA");
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
			System.out.println("Se produjo un error al conectar a la Base de Datos");
		}
	}
}
