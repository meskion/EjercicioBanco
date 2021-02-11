package banco;

public class Cuenta {
	
	private int nCuenta;
	
	private double saldo, comision;
	
	public static final double interes = 0.1;
	private static int contadorCodigos = 0;
	private Cliente cliente;
	

	public Cuenta( double saldo, Cliente cliente) {
		this.setnCuenta(contadorCodigos++);
		this.saldo = saldo;
		this.comision = 0.6;
		this.cliente = cliente;
		
	}
	
	public Cuenta( double saldo, double comision, Cliente cliente) {
		this.setnCuenta(contadorCodigos++);
		this.saldo = saldo;
		this.comision = comision;
		this.cliente = cliente;
		
	}

	public double getSaldo() {
		return saldo;
	}

	public void setSaldo(double saldo) {
		this.saldo = saldo;
	}

	
	public void ingresarDinero(double ingreso) {
		double ingr = Math.abs(ingreso);
		saldo+=ingr;
	}

	public boolean retirarDinero( double retiro) {
		double ret = Math.abs(retiro);
		double proSaldo = saldo - ret;
		if (proSaldo < 0) {
			return false;
		}
		
		saldo = proSaldo;
		return true;
	}
	
	public void actualizarSaldo() {
		
		saldo += saldo * interes - comision;
	}

	public int getnCuenta() {
		return nCuenta;
	}

	public void setnCuenta(int nCuenta) {
		this.nCuenta = nCuenta;
	}
	
	

	public Cliente getCliente() {
		return cliente;
	}

	public void setCliente(Cliente cliente) {
		this.cliente = cliente;
	}

	public double getComision() {
		return comision;
	}

	public void setComision(double comision) {
		this.comision = comision;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		long temp;
		temp = Double.doubleToLongBits(comision);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		result = prime * result + nCuenta;
		temp = Double.doubleToLongBits(saldo);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Cuenta other = (Cuenta) obj;
		if (Double.doubleToLongBits(comision) != Double.doubleToLongBits(other.comision))
			return false;
		if (nCuenta != other.nCuenta)
			return false;
		if (Double.doubleToLongBits(saldo) != Double.doubleToLongBits(other.saldo))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "Cuenta " + nCuenta + ", saldo:" + saldo;
	}
	
	
	
	
	

	
	
}


