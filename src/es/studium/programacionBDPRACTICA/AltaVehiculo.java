package es.studium.programacionBDPRACTICA;

import java.awt.Button;
import java.awt.Choice;
import java.awt.Dialog;
import java.awt.FlowLayout;
import java.awt.Frame;
import java.awt.Label;
import java.awt.TextField;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
public class AltaVehiculo extends Frame implements WindowListener, ActionListener
{
	private static final long serialVersionUID = 1L;
	TextField tipo = new TextField(20);
	TextField lugar = new TextField(20);
	TextField fecha = new TextField(20);
	TextField precio = new TextField(20);
	TextField matricula = new TextField(20);
	Label lblConductor = new Label("Conductor");
	Label lbltipo = new Label("Tipo de Vehículo");
	Label lbllugar = new Label("Lugar de Estacionamiento");
	Label lblfecha = new Label("Fecha de Compra");
	Label lblprecio= new Label("Precio de Vehículo");
	Label lblmatricula= new Label("Matrícula del Vehículo");
	Choice choiceConductor=new Choice();
	Button ok=new Button("Ok");
	Button ok2 = new Button("Ok");
	Dialog f = new Dialog(this, "Error", true);
	Label e1 = new Label ("Revisa que has introducido los campos correctamente.");
	Button insertar = new Button("Insertar");
	Button borrar = new Button("Borrar");
	Button volver=new Button("Volver");
	Dialog d = new Dialog(this, "Operación Inserción", true);
	Label e = new Label ("Operación realizada correctamente!");
	String driver = "com.mysql.jdbc.Driver";
	String url = "jdbc:mysql://localhost:3306/Bomberos?useSSL=false";
	String login = "root";
	String password = "Studium2019;";
	Connection connection = null;
	Statement statement = null;
	ResultSet rs= null;
	public AltaVehiculo()
	{
		setTitle("Alta");
		setLayout(new FlowLayout());
		setSize(230,420);
		setResizable(false);
		add(lbltipo);
		add(tipo);
		add(lbllugar);
		add(lugar);
		add(lblfecha);
		add(fecha);
		add(lblprecio);
		add(precio);
		add(lblmatricula);
		add(matricula);
		add(lblConductor);
		add(choiceConductor);
		add(insertar);
		add(borrar);
		add(volver);
		
		setLocationRelativeTo(null);
		volver.addActionListener(this);
		ok.addActionListener(this);
		ok2.addActionListener(this);
		insertar.addActionListener(this);
		borrar.addActionListener(this);
		addWindowListener(this);
		f.setLayout(new FlowLayout());
		f.add(e1);
		f.setSize(320,150);
		f.add(ok2);
		f.addWindowListener(this);
		f.setLocationRelativeTo(null);
		d.setLayout(new FlowLayout());
		d.add(e);
		d.setSize(300,150);
		d.addWindowListener(this);
		d.setLocationRelativeTo(null);
		d.add(ok);
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
			statement =connection.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,ResultSet.CONCUR_READ_ONLY);
			rs=statement.executeQuery("SELECT* FROM  Bomberos");
			while(rs.next())
			{
				String asd =new String(rs.getString("idBombero")+". "+rs.getString("nombreBombero")+" "+rs.getString("apellidosBombero"));
				choiceConductor.add(asd);	
			}
		}
		catch(SQLException e)
		{
			System.out.println("Error en la sentencia SQL");
		}
		try
		{
			statement =
					connection.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,ResultSet.CONCUR_READ_ONLY);
		}
		catch(SQLException e)
		{
			System.out.println("Error en la sentencia SQL");
		}
		setVisible(true);
	}
	
	public void windowActivated(WindowEvent windowEvent){}
	public void windowClosed(WindowEvent windowEvent) {}
	public void windowClosing(WindowEvent windowEvent)
	{if(this.isActive()) {
		this.setVisible(false);
	}
	if(d.isActive())
	{
		d.setVisible(false);
	}
	if(f.isActive())
	{
		f.setVisible(false);
	}
	else
	{
		try
		{
			statement.close();
			connection.close();
		}
		catch(SQLException e)
		{
			System.out.println("Error al cerrar "+e.toString());
		}
	}
	}
	public void windowDeactivated(WindowEvent windowEvent) {}
	public void windowDeiconified(WindowEvent windowEvent) {}
	public void windowIconified(WindowEvent windowEvent) {}
	public void windowOpened(WindowEvent windowEvent) {}
	public void actionPerformed(ActionEvent actionEvent)
	{
		if(insertar.equals(actionEvent.getSource()))
		{
				
				int a = choiceConductor.getSelectedItem().toString().indexOf('.');
				String idBombero = choiceConductor.getSelectedItem().toString().substring(0,a);
			try
			{
				String fechaValida=Metodos.convertirFecha(fecha.getText());

				statement.executeUpdate("INSERT INTO Vehiculos VALUES (null,'"+tipo.getText()+"','"+matricula.getText()+"','"+lugar.getText()+"','"+fechaValida+"','"+precio.getText()+"','"+idBombero+"')");
				try {
					Metodos.InfoLog("Alta: INSERT INTO Vehiculos VALUES (null,'"+tipo.getText()+"','"+matricula.getText()+"','"+lugar.getText()+"','"+fechaValida+"','"+precio.getText()+"','"+idBombero+"')");
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				lugar.setText("");
				fecha.setText("");
				matricula.setText("");
				precio.setText("");
				tipo.setText("");
				d.setVisible(true);
			}
			catch(StringIndexOutOfBoundsException se)
			{
				try {
					Metodos.InfoLog("[ERROR] OPERACIÓN DENEGADA");
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				f.setVisible(true);		}
			catch(SQLException se)
			{
				try {
					Metodos.InfoLog("[ERROR] OPERACIÓN DENEGADA");
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				f.setVisible(true);
			}
		}
		else
		{
			tipo.getText();
			tipo.setText("");
			fecha.getText();
			fecha.setText("");
			lugar.getText();
			lugar.setText("");
			precio.getText();
			precio.setText("");
			matricula.getText();
			matricula.setText("");
		}
		if(ok.equals(actionEvent.getSource()))
		{d.setVisible(false);}
		if(ok2.equals(actionEvent.getSource()))
		{f.setVisible(false);}
		if(volver.equals(actionEvent.getSource()))
		{this.setVisible(false);
		}
	}
}