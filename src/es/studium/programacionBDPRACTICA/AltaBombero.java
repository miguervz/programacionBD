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
public class AltaBombero extends Frame implements WindowListener, ActionListener
{
	private static final long serialVersionUID = 1L;

	//Declaramos las variables de la frame
	TextField nombre = new TextField(20);
	TextField apellidos = new TextField(20);
	TextField tlf = new TextField(20);
	TextField fechaNacimiento = new TextField(20);
	Label lblNombre = new Label("Nombre");
	Label lblApellidos = new Label("Apellidos");
	Label lblTlf = new Label("Teléfono");
	Label lblFecha= new Label("Fecha Nacimiento (DD-MM-AAAA)");
	Label lblSquad = new Label("Escuadrilla");
	Choice squad = new Choice();
	Button btnVolver = new Button("Volver");
	Button btnInsertar = new Button("Insertar");
	Button btnBorrar = new Button("Borrar");

	//Declaramos las variables del diálogo de Error
	Dialog dError = new Dialog(this, "Error", true);
	Label lblError = new Label ("Revisa que has introducido los campos correctamente. Formato fecha (DD-MM-AA)");
	Button btnOkError = new Button("Ok");


	//Declaramos las variables del diálogo de Éxito
	Dialog dExito = new Dialog(this, "Operación Inserción", true);
	Label lblExito = new Label ("Operación realizada correctamente!");
	Button btnOkExito = new Button("Ok");

	//Declaramos las variables de la conexión a la base de datos
	String driver = "com.mysql.jdbc.Driver";
	String url = "jdbc:mysql://localhost:3306/Bomberos?useSSL=false";
	String login = "root";
	String password = "Studium2019;";
	Connection connection = null;
	Statement statement = null;
	ResultSet rs = null;

	public AltaBombero()
	{
		//Añadimos los componentes a la Frame y a los diálogos y establecemos sus valores
		setTitle("Alta");
		setLayout(new FlowLayout());
		setSize(210,360);
		setResizable(false);
		setLocationRelativeTo(null);
		addWindowListener(this);
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
		add(btnInsertar);
		add(btnBorrar);
		add(btnVolver);
		btnVolver.addActionListener(this);
		btnOkExito.addActionListener(this);
		btnOkError.addActionListener(this);
		btnInsertar.addActionListener(this);
		btnBorrar.addActionListener(this);
		
		//Establecemos los valores del diálogo de error
		dError.setLayout(new FlowLayout());
		dError.add(lblError);
		dError.setSize(500,150);
		dError.add(btnOkError);
		dError.addWindowListener(this);
		dError.setLocationRelativeTo(null);
		dError.setResizable(false);
		
		//Establecemos los valores del diálogo de éxito
		dExito.setLayout(new FlowLayout());
		dExito.add(lblExito);
		dExito.setSize(300,150);
		dExito.addWindowListener(this);
		dExito.setLocationRelativeTo(null);
		dExito.setResizable(false);
		dExito.add(btnOkExito);

		//Establecemos conexión a la base de datos
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

		//Hacemos una consulta donde rellenamos el choice con las escuadrillas existentes
		try
		{
			statement =connection.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,ResultSet.CONCUR_READ_ONLY);
			rs=statement.executeQuery("SELECT* FROM Escuadrillas");
			while(rs.next())
			{
				String asd =new String(rs.getString("idEscuadrilla")+". nº "+rs.getString("numeroEscuadrilla"));
				squad.add(asd);	
			}
		}
		catch(SQLException e)
		{
			System.out.println("Error en la sentencia SQL");
		}
		setVisible(true);
	}


	public void windowActivated(WindowEvent windowEvent){}
	public void windowClosed(WindowEvent windowEvent) {}

	//Establecemos la funcionalidad del cierre de la ventana y los diálogos
	public void windowClosing(WindowEvent windowEvent)
	{
		if(this.isActive()) {
			this.setVisible(false);
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
		if(dExito.isActive())
		{
			dExito.setVisible(false);
		}
		if(dError.isActive())
		{
			dError.setVisible(false);
		}
		
	}
	public void windowDeactivated(WindowEvent windowEvent) {}
	public void windowDeiconified(WindowEvent windowEvent) {}
	public void windowIconified(WindowEvent windowEvent) {}
	public void windowOpened(WindowEvent windowEvent) {}

	//Le damos funcionalidad a los botones
	public void actionPerformed(ActionEvent actionEvent)
	{
		//Aquí vamos a obtener el valor del id de el escuadrón seleccionado al que hemos llamado en la consulta anterior
		String escuadron = new String(squad.getSelectedItem());
		int a = escuadron.indexOf('.');
		String nEscuadron = escuadron.substring(0,a);

		//Botón insertar
		if(btnInsertar.equals(actionEvent.getSource()))
		{
			try
			{//Usamos el método convertirFecha
				String fechaValida=Metodos.convertirFecha(fechaNacimiento.getText());

				//Ejecutamos la inserción

				statement.executeUpdate("INSERT INTO Bomberos VALUES (null,'"+nombre.getText()+"','"+apellidos.getText()+"','"+tlf.getText()+"','"+fechaValida+"','"+nEscuadron+"')");
				try {
					Metodos.InfoLog("Alta: INSERT INTO Bomberos VALUES (null,'"+nombre.getText()+"','"+apellidos.getText()+"','"+tlf.getText()+"','"+fechaValida+"','"+nEscuadron+"')");
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				apellidos.setText("");
				tlf.setText("");
				fechaNacimiento.setText("");
				nombre.setText("");
				dExito.setVisible(true);

			}
			catch(SQLException se)
			{
				try {
					Metodos.InfoLog("[ERROR] OPERACIÓN DENEGADA");
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				dError.setVisible(true);}
			catch(StringIndexOutOfBoundsException se)
			{
				try {
					Metodos.InfoLog("[ERROR] OPERACIÓN DENEGADA");
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				dError.setVisible(true);		}
		}

		//Botón para limpiar los campos
		if(btnBorrar.equals(actionEvent.getSource()))
		{
			nombre.getText();
			nombre.setText("");
			tlf.getText();
			tlf.setText("");
			apellidos.getText();
			apellidos.setText("");
			fechaNacimiento.getText();
			fechaNacimiento.setText("");
		}

		//Botón para volver atrás
		if(btnVolver.equals(actionEvent.getSource()))
		{
			this.setVisible(false);
		}
		//Botones 'Ok' de los diálogos
		if(btnOkExito.equals(actionEvent.getSource()))
		{
			dExito.setVisible(false);
		}
		if(btnOkError.equals(actionEvent.getSource()))
		{
			dError.setVisible(false);
		}
	}

}