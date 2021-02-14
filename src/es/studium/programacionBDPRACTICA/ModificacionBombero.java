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
public class ModificacionBombero extends Frame implements WindowListener, ActionListener, ItemListener
{
	private static final long serialVersionUID = 1L;
	TextField nombre = new TextField(20);
	TextField apellidos = new TextField(20);
	TextField tlf = new TextField(20);
	TextField fechaNacimiento = new TextField(20);
	TextField squad = new TextField(20);
		Label lblNombre = new Label("Nombre");
	Label lblApellidos = new Label("Apellidos");
	Label lblTlf = new Label("Teléfono");
	Label lblFecha= new Label("Fecha De Nacimiento");
	Label lblSquad = new Label("Escuadrilla");
	
	Dialog dConfirmacion= new Dialog(this, "Confirmar", true);
	Label lblConfirmacion = new Label ("¿Seguro que quiere llevar a cabo esta operación?");
	Button btnVolverConfirmacion = new Button("Volver");
	Button btnOkConfirmacion = new Button("Ok");
	
	Dialog dError = new Dialog(this, "Error", true);
	Label lblError = new Label ("Revisa los campos.Usa una escuadrilla existente.");
	Button btnOkError = new Button("Ok");
	
	Dialog dExito = new Dialog(this, "Operación Inserción", true);
	Label eExito = new Label ("Operación realizada correctamente!");
	Button btnOkExito = new Button("Ok");


	Choice lista = new Choice();
	
	String fechaValidaProg;
	Button modificar = new Button("Modificar");
	Button btnVolver = new Button("Volver");


	String driver = "com.mysql.jdbc.Driver";
	String url = "jdbc:mysql://localhost:3306/Bomberos?useSSL=false";
	String login = "root";
	String password = "Studium2019;";
	Connection connection = null;
	Statement statement = null;
	ResultSet rs = null;
	String id;
	public ModificacionBombero()
	{
		
		setLayout(new FlowLayout());
		setSize(216,400);
		setResizable(false);
		setTitle("Modificación");
		add(lista);
		setResizable(false);
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
		dError.setSize(330,150);
		dError.setResizable(false);
		dError.addWindowListener(this);
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
		add(lblNombre);
		add(nombre);
		add(lblApellidos);
		add(apellidos);
		add(lblTlf);
		add(tlf);
		add(lblFecha);
		add(fechaNacimiento);
		add(lblSquad);
		add(squad);
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
			rs=statement.executeQuery("SELECT* FROM bomberos");
			while(rs.next())
			{
				lista.add(rs.getInt("idBombero")+" "+rs.getString("nombreBombero")+" "+rs.getString("apellidosBombero")+"\n");
			}
		}
		catch(SQLException e)
		{
			System.out.println("Error en la sentencia SQL");
		}
		try
		{
			statement =connection.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,ResultSet.CONCUR_READ_ONLY);
			rs=statement.executeQuery("SELECT* FROM bomberos");
			if(rs.next())
			{
				fechaValidaProg =Metodos.convertirFecha(rs.getString("fechaNacimientoBombero"));
				nombre.setText(rs.getString("nombreBombero"));
				apellidos.setText(rs.getString("apellidosBombero"));
				tlf.setText(rs.getString("telefonoBombero"));
				fechaNacimiento.setText(fechaValidaProg);
				squad.setText(rs.getString("idEscuadrillaFK"));	
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
			new ModificacionBombero();
			
		}
		if(dConfirmacion.isActive())
		{
			dispose();
			new ModificacionBombero();
		}
		if(dError.isActive())
		{
			dispose();
			new ModificacionBombero();
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
			rs=statement.executeQuery("SELECT* FROM bomberos WHERE idBombero="+id);
			if(rs.next())
			{
				fechaValidaProg =Metodos.convertirFecha(rs.getString("fechaNacimientoBombero"));
				nombre.setText(rs.getString("nombreBombero"));
				apellidos.setText(rs.getString("apellidosBombero"));
				tlf.setText(rs.getString("telefonoBombero"));
				fechaNacimiento.setText(fechaValidaProg);
				squad.setText(rs.getString("idEscuadrillaFK"));
			}}
		catch(SQLException e)
		{
			System.out.println("Error en la sentencia SQL");
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

			String nombre1 = new String(lista.getSelectedItem());
			int posicion = nombre1.indexOf(" ");
			id = nombre1.substring(0, posicion);

			try
			{	
				statement =connection.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,ResultSet.CONCUR_READ_ONLY);

				String fechaValidaBD =Metodos.convertirFechaInversa(fechaNacimiento.getText());
				statement.executeUpdate("UPDATE bomberos SET nombreBombero='"+nombre.getText()+"',apellidosBombero='"+apellidos.getText()+"',telefonoBombero='"+tlf.getText()+"',fechaNacimientoBombero='"+fechaValidaBD+"',idEscuadrillaFK='"+squad.getText()+"' where idBombero="+id);
				dExito.setVisible(true);
				try {
					Metodos.InfoLog("Modificación: UPDATE bomberos SET nombreBombero='"+nombre.getText()+"',apellidosBombero='"+apellidos.getText()+"',telefonoBombero='"+tlf.getText()+"',fechaNacimientoBombero='"+fechaValidaBD+"',idEscuadrillaFK='"+squad.getText()+"' where idBombero="+id);
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				if(rs.next())
				{	
					nombre.setText(rs.getString("nombreBombero"));
					apellidos.setText(rs.getString("apellidosBombero"));
					tlf.setText(rs.getString("telefonoBombero"));
					fechaNacimiento.setText(fechaValidaBD);
					squad.setText(rs.getString("idEscuadrillaFK"));		
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
		new ModificacionBombero();
	
		}
	}
}