package banco;

import java.util.ArrayList;
import java.util.List;

public class Cliente {
	
	private String dni, nombre ,apellidos, telefono;
	
	private List<Cuenta> cuentas;
	

	public Cliente(String dni, String nombre, String apellidos, String telefono) {
		super();
		this.dni = dni;
		this.nombre = nombre;
		this.apellidos = apellidos;
		this.telefono = telefono;
		cuentas = new ArrayList<>();
	}

	public String getDni() {
		return dni;
	}

	public void setDni(String dni) {
		this.dni = dni;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getApellidos() {
		return apellidos;
	}

	public void setApellidos(String apellidos) {
		this.apellidos = apellidos;
	}

	public String getTelefono() {
		return telefono;
	}

	public void setTelefono(String telefono) {
		this.telefono = telefono;
	}
	
	public void addCuenta(Cuenta cuenta) {
		cuentas.add(cuenta);
	}

	public List<Cuenta> getCuentas() {
		return cuentas;
	}

	@Override
	public String toString() {
		return dni + ", " + nombre + " " + apellidos + ", telefono:" + telefono;
	}
	
	
	
	
	

}
