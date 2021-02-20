package banco;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

public class AplicacionBanco {

	private static int contCuentas;
	static Map<String, Cliente> clientes;
	static Scanner sc;

	private static void menu1() {
		System.out.println("1. Nuevo Cliente");
		System.out.println("2. Acceso Clientes");
		System.out.println("3. Acceso Admin");
		System.out.println("4. Salir");
	}

	public static void main(String[] args) {
		clientes = new HashMap<>();
		sc = new Scanner(System.in);
		String opcion = "";
		System.out.println("BIENVENIDO A SU BANCO");
		do {
			// Al inicio de cada iteración mostramos el menú, y recogemos la opción
			menu1();
			try {
				opcion = sc.nextLine();

				switch (opcion) {
				case "1":
					nuevoCliente();
					break;
				case "2":
					accesoCliente();
					break;
				case "3":
					accesoAdmin();
					break;

				case "4":
					System.out.println("APAGANDO APP");
					break;
				default:
					System.out.println("Opción no valida. Introduzca una opcion valida, por favor.");
				}

			} catch (NumberFormatException e) {
				System.err.println("Dato introducido no valido, debe ser un numero");
			} catch (NullPointerException e) {
				System.err.println("Cuenta o cliente no existente");
			}

		} while (!opcion.equals("4"));

		sc.close();

	}

	/**
	 * Crea por consola un nuevo cliente para añadirlo al sistema, y lleva a la
	 * vista de creacion y acceso a cuentas.
	 */

	private static void nuevoCliente() {
		if (clientes.size() < 1000) {
			System.out.print("Introduzca su DNI:");
			String dni = sc.nextLine();
			System.out.print("Introduzca su nombre:");
			String nombre = sc.nextLine();
			System.out.print("Introduzca sus apellidos:");
			String apellidos = sc.nextLine();
			System.out.print("Introduzca su telefono:");
			String telefono = sc.nextLine();

			Cliente c = new Cliente(dni, nombre, apellidos, telefono);
			clientes.put(dni, c);
			menuCuentasCliente(c);

		} else {
			System.out.println("Lo sentimos pero no podemos admitir mas clientes en este momento");
		}

	}

	/**
	 * Introduciendo la clave de un cliente que exista en el sistema, le da acceso a
	 * menu de control del cliente.
	 */
	private static void accesoCliente() {

		if (clientes.isEmpty()) {
			System.out.println("No Hay clientes en la base de datos");
			return;
		}

		boolean fail = true;
		do {
			System.out.print("Introduzca su DNI para acceder:");
			String dni = sc.nextLine();// Estaria guay que este bien formado.
			Cliente cliente = clientes.get(dni);
			fail = cliente == null;
			if (!fail) {
				menuCuentasCliente(cliente);
			} else {
				System.out.print("DNI Incorrecto, ");
			}
		} while (fail);
	}

	private static void menu2() {
		System.out.println("1. Nueva Cuenta");
		System.out.println("2. Acceso Cuenta");
		System.out.println("3. Listar Cuentas");
		System.out.println("4. Saldo total");
		System.out.println("5. Volver a vista general");

	}

	/**
	 * Menu para un cliente, para permitirle interactuar con sus cuentas, crearlas,
	 * visualizar su saldo, etc
	 * 
	 * @param cliente
	 */

	private static void menuCuentasCliente(Cliente cliente) {
		String opcion;
		do {
			menu2();
			opcion = sc.nextLine();
			switch (opcion) {
			case "1":
				nuevaCuenta(cliente);
				break;
			case "2":
				accesoCuenta(cliente);
				break;
			case "3":
				listarCuentas(cliente);
				break;
			case "4":
				saldoTotal(cliente);
				break;
			case "5":
				System.out.println("cerrando sesion..");
				break;

			default:
				break;
			}

		} while (!opcion.equals("5"));

	}

	/**
	 * Crea una nueva cuenta para el cliente actual, y le lleva directamente al menu
	 * interno de operaciones en la cuenta
	 * 
	 * @param cliente
	 */

	private static void nuevaCuenta(Cliente cliente) {
		if (contCuentas < 1000) {
			System.out.println("Saldo inicial de la cuenta?");
			double saldo = Double.parseDouble(sc.nextLine());
			cliente.addCuenta(new Cuenta(saldo, cliente));
			contCuentas++;
		} else {
			System.out.println("No esta disponible la creacion de cuentas en este momento.");
		}

	}

	/**
	 * Introduciendo el indice de un cuenta en su lista, da acceso al cliente al
	 * menu de operaciones de cuenta.
	 * 
	 * @param cliente
	 */

	private static void accesoCuenta(Cliente cliente) {

		if (cliente.getCuentas().isEmpty()) {
			System.out.println("El cliente no tiene cuentas asociadas");
			return;
		}

		boolean fail = true;
		do {
			System.out.print("Introduzca la cuenta a acceder:");
			listarCuentas(cliente);
			int index = Integer.parseInt(sc.nextLine());
			fail = index >= cliente.getCuentas().size() || index < 0;

			if (!fail) {
				Cuenta cuenta = cliente.getCuentas().get(index);
				menuCuenta(cuenta);
			} else {
				System.err.println("introduzca un numero de la lista de cuentas");
			}
		} while (fail);

	}

	/**
	 * Muestra las cuentas asociadas al cliente especificado
	 * 
	 * @param cliente
	 */

	private static void listarCuentas(Cliente cliente) {
		if (cliente.getCuentas().isEmpty()){
			System.out.println("No hay cuentas");
			return;
		}
		List<Cuenta> cuentas = cliente.getCuentas();
		for (int i = 0; i < cuentas.size(); i++) {
			System.out.println(i + ": " + cuentas.get(i).toString());
		}

	}

	/**
	 * Muestra el saldo combinado de todas la cuentas del cliente.
	 * 
	 * @param cliente
	 */

	private static void saldoTotal(Cliente cliente) {
		// double saldoTotal = cliente.getCuentas().stream().reduce(0.0,
		// (subtotal, element) -> subtotal + element.getSaldo(), Double::sum);

		double saldoTotal = cliente.getCuentas().stream().map(c -> c.getSaldo()).reduce(0.0, (t, s) -> t + s);

		System.out.println("El saldo total es " + saldoTotal);
	}

	private static void menu3() {
		System.out.println("1: Ingresar Dinero");
		System.out.println("2: Retirar Dinero");
		System.out.println("3: consultar Saldo");
		System.out.println("4: Volver a mis cuentas");

	}

	/**
	 * Menu donde el cliente puede operar con una cuenta especificada, moviendo
	 * dinero o consultando su saldo.
	 * 
	 * @param cuenta
	 */

	private static void menuCuenta(Cuenta cuenta) {
		String opcion;
		do {
			menu3();
			opcion = sc.nextLine();

			switch (opcion) {
			case "1":
				ingresarDinero(cuenta);
				break;
			case "2":
				retirarDinero(cuenta);
				break;

			case "3":
				consultarSaldo(cuenta);
				break;

			case "4":
				System.out.println("Volviendo atras..");
				break;

			default:
				break;
			}

		} while (!opcion.equals("4"));

	}

	/**
	 * Retira dinero de la cuenta, siempre que tenga dinero suficiente.
	 * 
	 * @param cuenta
	 */
	private static void retirarDinero(Cuenta cuenta) {
		System.out.println("Cuanto dinero deseas retirar? saldo: " + cuenta.getSaldo());
		double retiro = Double.parseDouble(sc.nextLine());
		if (cuenta.retirarDinero(retiro)) {
			System.out.println("operacion realizada con exito");
		} else {
			System.out.println("Fondos insuficientes");
		}

	}

	/**
	 * Ingresa dinero en la cuenta especificada
	 * 
	 * @param cuenta
	 */

	private static void ingresarDinero(Cuenta cuenta) {
		System.out.println("Cuanto dinero quiere ingresar?");
		cuenta.ingresarDinero(Double.parseDouble(sc.nextLine()));

	}

	/**
	 * Devuelve el saldo total de la cuenta especificada.
	 * 
	 * @param cuenta
	 */
	private static void consultarSaldo(Cuenta cuenta) {
		System.out.println(cuenta);

	}

	private static void menuAdmin() {
		System.out.println("1. Listar todas las cuentas");
		System.out.println("2. Listar todos los clientes");
		System.out.println("3. Aplicar revision mensual");
		System.out.println("4. Cambiar comision de una cuenta");
		System.out.println("5. Eliminar un cliente");
		System.out.println("4. Eliminar una cuenta");
		System.out.println("7. Volver a menu principal");

	}

	/**
	 * Menu de acceso de operaciones a nivel de administrador, eliminar objetos,
	 * actualizar saldo, cambiar comisiones..
	 */
	private static void accesoAdmin() {

		String opcion;
		do {
			menuAdmin();
			opcion = sc.nextLine();

			switch (opcion) {
			case "1":
				listarCuentas();
				break;
			case "2":
				listarClientes();
				break;

			case "3":
				revisionMensual();
				break;

			case "4":
				cambiarComision();
				break;
			case "5":
				eliminarCliente();
				break;
			case "6":
				eliminarCuenta();
				break;
			case "7":
				System.out.println("cerrando sesion..");
				break;

			default:
				break;
			}

		} while (!opcion.equals("7"));
	}

	/**
	 * Lista todas las cuentas del banco.
	 */
	private static void listarCuentas() {
		clientes.values().stream().flatMap(c -> c.getCuentas().stream()).forEach(d -> System.out.println(d));

	}

	/**
	 * Lista todos los clientes del banco
	 */
	private static void listarClientes() {

		clientes.values().forEach(c -> System.out.println(c));

	}

	/**
	 * Elimina una cuenta del banco, segun su codigo.
	 */
	private static void eliminarCuenta() {
		System.out.println("Codigo de la cuenta a eliminar?");
		int codigo = Integer.parseInt(sc.nextLine());

		clientes.values().stream().flatMap(c -> c.getCuentas().stream()).forEach(d -> {
			if (d.getnCuenta() == codigo) {
				d.getCliente().getCuentas().remove(d);
				--contCuentas;
			}
		});

	}

	/**
	 * Elimina un cliente y todas sus cuentas del banco, segun el dni del cliente.
	 */
	private static void eliminarCliente() {
		System.out.println("DNI del cliente a eliminar?");
		String dni = sc.nextLine();
		if (clientes.get(dni) != null) {
			contCuentas -= clientes.get(dni).getCuentas().size();
			clientes.remove(dni);
		} else
			System.out.println("No existe el cliente");
	}

	/**
	 * Cambia la comision de una cuenta, segun el numero de cuenta
	 */

	private static void cambiarComision() {
		System.out.println("Codigo de cuenta a cambiar?");
		int codigo = Integer.parseInt(sc.nextLine());
		System.out.println("Nueva comision de la cuenta?");
		double comision = Double.parseDouble(sc.nextLine());

		clientes.values().stream()
		.flatMap(c -> c.getCuentas().stream())
		.filter(c -> codigo == c.getnCuenta())
		.findFirst()
		.orElse(null)
		.setComision(comision);

	}
	/**
	 * Aplica la revision mensual a cada cuenta, actualizando su saldo segun su interes y comision.
	 */

	private static void revisionMensual() {

		clientes.values().stream()
		.flatMap(c -> c.getCuentas().stream())
		.parallel()
		.forEach(d -> d.actualizarSaldo());

	}

}
