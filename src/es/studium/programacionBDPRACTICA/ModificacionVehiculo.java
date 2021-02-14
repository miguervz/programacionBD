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
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
public class ModificacionVehiculo extends Frame implements WindowListener, ActionListener, ItemListener
{
	private static final long serialVersionUID = 1L;
	TextField tipo = new TextField(20);
	TextField lugar = new TextField(20);
	TextField precio = new TextField(20);
	TextField fecha = new TextField(20);
	TextField matricula = new TextField(20);
	TextField bombero = new TextField(20);
	Label lblTipo = new Label("Tipo de vehículo");
	Label lblLugar = new Label("Lugar de estacionamiento");
	Label lblPrecio = new Label("Precio del vehículo");
	Label lblFecha= new Label("Fecha De Compra");
	Label lblMatricula= new Label("Matrícula del vehículo");
	Label lblConductor= new Label("nº id Conductor del vehículo");
	Choice lista = new Choice();
	ResultSet rs = null;
	
	Dialog dConfirmacion= new Dialog(this, "Confirmar", true);
	Label lblConfirmacion = new Label ("¿Seguro que quiere llevar a cabo esta operación?");
	Button btnVolverConfirmacion = new Button("Volver");
	Button btnOkConfirmacion = new Button("Ok");
	
	Dialog dError = new Dialog(this, "Error", true);
	Label lblError = new Label ("Revisa los campos.Usa un id de un bombero existente y una fecha válida.");
	Button btnOkError = new Button("Ok");
	
	Dialog dExito = new Dialog(this, "Operación Inserción", true);
	Label eExito = new Label ("Operación realizada correctamente!");
	Button btnOkExito = new Button("Ok");
	
	String fechaValidaProg;
	Button modificar = new Button("Modificar");
	Button btnVolver = new Button("Volver");
	
	String driver = "com.mysql.jdbc.Driver";
	String url = "jdbc:mysql://localhost:3306/Bomberos?useSSL=false";
	String login = "root";
	String password = "Studium2019;";
	Connection connection = null;
	Statement statement = null;
	String id;
	public ModificacionVehiculo()
	{
		setLayout(new FlowLayout());
		setSize(250,440);
		setResizable(false);
		setTitle("Modificación");
		add(lista);
		setLocationRelativeTo(null);
		modificar.addActionListener(this);
		btnVolver.addActionListener(this);
		addWindowListener(this);

		dConfirmacion.setLayout(new FlowLayout());
		dConfirmacion.setSize(290,150);
		dConfirmacion.setResizable(false);
		dConfirmacion.addWindowListener(this);
		dConfirmacion.setLocationRelativeTo(null);
		dConfirmacion.add(lblConfirmacion);
		dConfirmacion.add(btnOkConfirmacion);
		btnOkConfirmacion.addActionListener(this);
		dConfirmacion.add(btnVolverConfirmacion);
		btnVolverConfirmacion.addActionListener(this);
	

		dError.setLayout(new FlowLayout());
		dError.setSize(420,150);
		dError.addWindowListener(this);
		dError.setResizable(false);
		dError.setLocationRelativeTo(null);
		dError.add(lblError);
		dError.add(btnOkError);
		btnOkError.addActionListener(this);
		
		dExito.setLayout(new FlowLayout());
		dExito.setSize(250,130);
		dExito.addWindowListener(this);
		dExito.setLocationRelativeTo(null);
		dExito.add(eExito);
		dExito.add(btnOkExito);
		btnOkExito.addActionListener(this);
		lista.addItemListener(this);
		add(lblTipo);
		add(tipo);
		add(lblLugar);
		add(lugar);
		add(lblPrecio);
		add(precio);
		add(lblFecha);
		add(fecha);
		add(lblMatricula);
		add(matricula);
		add(lblConductor);
		add(bombero);
		add(modificar);
		add(btnVolver);
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
			rs=statement.executeQuery("SELECT* FROM vehiculos");
			while(rs.next())
			{
				lista.add(rs.getInt("idVehiculo")+" "+rs.getString("tipoVehiculo")+" de matrícula "+rs.getString("matriculaVehiculo")+"\n");
			}
		}
		catch(SQLException e)
		{
			System.out.println("Error en la sentencia SQL");
		}
		try
		{
			statement =connection.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,ResultSet.CONCUR_READ_ONLY);
			rs=statement.executeQuery("SELECT* FROM vehiculos");
			if(rs.next())
			{
				fechaValidaProg=Metodos.convertirFecha(rs.getString("fechaCompraVehiculo"));
				tipo.setText(rs.getString("tipoVehiculo"));
				lugar.setText(rs.getString("lugarEstacionamientoVehiculo"));
				precio.setText(rs.getString("precioVehiculo"));
				fecha.setText(fechaValidaProg);
				matricula.setText(rs.getString("matriculaVehiculo"));		
				bombero.setText(rs.getString("idBomberoFK"));		
			}}
		catch(SQLException e)
		{
			System.out.println("Error en la sentencia SQL");
		}
		setVisible(true);
	}

	public void windowActivated(WindowEvent windowEvent){}
	public void windowClosed(WindowEvent windowEvent) {}
	public void windowClosing(WindowEvent windowEvent)
	{
		if(this.isActive()) {
			this.setVisible(false);
			}
			if(dExito.isActive())
			{	try
			{
				statement.close();
				connection.close();
			}
			catch(SQLException e)
			{
				System.out.println("Error al cerrar "+e.toString());
			}dispose();
				new ModificacionVehiculo();
				
			}
			if(dConfirmacion.isActive())
			{
				dispose();
				new ModificacionVehiculo();
			}
			if(dError.isActive())
			{
				dispose();
				new ModificacionVehiculo();
			}
			
		}
	public void windowDeactivated(WindowEvent windowEvent) {}
	public void windowDeiconified(WindowEvent windowEvent) {}
	public void windowIconified(WindowEvent windowEvent) {}
	public void windowOpened(WindowEvent windowEvent) {}
	public void itemStateChanged(ItemEvent ie) {
		String nombreItem = new String(lista.getSelectedItem());
		int posicion = nombreItem.indexOf(" ");
		id = nombreItem.substring(0, posicion);
		try
		{
			statement =connection.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,ResultSet.CONCUR_READ_ONLY);
			rs=statement.executeQuery("SELECT* FROM vehiculos WHERE idVehiculo="+id);
			
			if(rs.next())
			{	fechaValidaProg=Metodos.convertirFecha(rs.getString("fechaCompraVehiculo"));
				tipo.setText(rs.getString("tipoVehiculo"));
				lugar.setText(rs.getString("lugarEstacionamientoVehiculo"));
				precio.setText(rs.getString("precioVehiculo"));
				fecha.setText(fechaValidaProg);
				matricula.setText(rs.getString("matriculaVehiculo"));		
				bombero.setText(rs.getString("idBomberoFK"));		
			}}
		catch(SQLException e)
		{
			System.out.println("Error en la sentenciaj SQL");
		}	
	}
	public void actionPerformed(ActionEvent actionEvent)
	{
		if(modificar.equals(actionEvent.getSource()))
		{dConfirmacion.setVisible(true);
		}
		if(btnVolver.equals(actionEvent.getSource())) {
			this.setVisible(false);
		}
		if(btnOkConfirmacion.equals(actionEvent.getSource())) {

			try{
				String nombre1 = new String(lista.getSelectedItem());
				int posicion = nombre1.indexOf(" ");
				id = nombre1.substring(0, posicion);
				
				statement =connection.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,ResultSet.CONCUR_READ_ONLY);
				String fechaValidaBD=Metodos.convertirFechaInversa(fecha.getText());
				statement.executeUpdate("UPDATE vehiculos SET tipoVehiculo='"+tipo.getText()+"',matriculaVehiculo='"+matricula.getText()+"',lugarEstacionamientoVehiculo='"+lugar.getText()+"',fechaCompraVehiculo='"+fechaValidaBD+"',precioVehiculo='"+precio.getText()+"',idBomberoFK='"+bombero.getText()+"' where idVehiculo="+id);
				dExito.setVisible(true);
				try {
					Metodos.InfoLog("Modificación: UPDATE vehiculos SET tipoVehiculo='"+tipo.getText()+"',matriculaVehiculo='"+matricula.getText()+"',lugarEstacionamientoVehiculo='"+lugar.getText()+"',fechaCompraVehiculo='"+fecha.getText()+"',precioVehiculo='"+precio.getText()+"',idBomberoFK='"+bombero.getText()+"' where idVehiculo="+id);
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				if(rs.next())
				{
					tipo.setText(rs.getString("tipoVehiculo"));
					lugar.setText(rs.getString("lugarEstacionamientoVehiculo"));	
					fecha.setText(rs.getString("fechaCompraVehiculo"));
					precio.setText(rs.getString("precioVehiculo"));
					matricula.setText(rs.getString("matriculaVehiculo"));	
					bombero.setText(rs.getString("idBomberoFK"));	
				}}
			catch(SQLException e)
			{try {
				Metodos.InfoLog("[ERROR] OPERACIÓN DENEGADA");
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			dError.setVisible(true);
			}
			catch(StringIndexOutOfBoundsException e)
			{try {
				Metodos.InfoLog("[ERROR] OPERACIÓN DENEGADA");
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			dError.setVisible(true);
			}
			
			
			
		}
		if(btnVolver.equals(actionEvent.getSource())) {
			this.setVisible(false);
		}
		if(btnVolverConfirmacion.equals(actionEvent.getSource())) {
			dConfirmacion.setVisible(false);
		}
		if(btnOkError.equals(actionEvent.getSource()))
		{	this.setVisible(false);
	
		}
		if(btnOkExito.equals(actionEvent.getSource()))
		{	this.setVisible(false);
		new ModificacionVehiculo();
	
		}}
	
}