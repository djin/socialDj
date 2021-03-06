package app.tumpi.cliente.lista.android.conexion;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.ArrayList;

import android.util.Log;

/**
 *
 * @author 66785270
 */
public class SocketConnector {
	Socket socket_cliente = null;
	// Activity act=null;
	// Intent intent_service=null;
	String ip_servidor = null;
	int puerto_servidor = 0;
	private Thread thread_server = null;
	private DataInputStream input = null;
	private DataOutputStream output = null;
	private String servidor = "";
	private ArrayList<ServerMessageListener> message_listeners = new ArrayList();

	public SocketConnector(String ip, int port) {
		ip_servidor = ip;
		puerto_servidor = port;
	}

	public void conectar() throws UnknownHostException, IOException {
		if (socket_cliente == null || !isConnected()) {
			socket_cliente = new Socket(ip_servidor, puerto_servidor);
			input = new DataInputStream(socket_cliente.getInputStream());
			output = new DataOutputStream(socket_cliente.getOutputStream());
		}
	}

	public void enviarMensaje(String mensaje) throws Exception {
		if (socket_cliente.isConnected() && !socket_cliente.isClosed()) {
			output.writeUTF("s:" + servidor + "|" + mensaje);
			output.flush();
		} else
			throw new Exception("No esta conectado...");
	}

	public boolean logBridge(String server, String uuid) {
		try {
			output.writeUTF("c:log|" + server + "|" + uuid);
			String resp = input.readUTF();
			System.out.println("Server: " + resp);
			if ("b:log|1".equals(resp)) {
				servidor = server;
				return true;
			}
			return false;
		} catch (IOException ex) {
			System.out.println("Error al mandar peticion login: "
					+ ex.toString());
			return false;
		}
	}

	public boolean isConnected() {
		return socket_cliente.isConnected();
	}

	public void startListeningServer() {
		thread_server = new Thread() {
			@Override
			public void run() {
				while (thread_server != null && socket_cliente.isConnected()) {
					String mensaje = "";
					try {
						mensaje = input.readUTF();
						fireMessageEvent(mensaje);
					} catch (IOException ex) {
						try {
							cerrarConexion();
							fireMessageEvent("exit");
						} catch (Exception ex1) {
							Log.e("Conexion", "Error al cerrar la conexion: "
									+ ex);
						}
						thread_server = null;
					}
				}
			}
		};
		thread_server.start();

	}

	public void stopListeningServer() {
		thread_server = null;
	}

	public void cerrarConexion() throws IOException, Exception {
		if (isConnected()) {
			stopListeningServer();
			input.close();
			output.close();
			socket_cliente.close();
		} else
			throw new Exception("No esta conectado...");
	}

	public void addServerMessageListener(ServerMessageListener listener) {
		message_listeners.add(listener);
	}

	public void removeServerMessageListener(ServerMessageListener listener) {
		message_listeners.remove(listener);
	}

	public void fireMessageEvent(String message) {
		for (ServerMessageListener listener : message_listeners) {
			listener.onMessageReceive(message);
		}
	}
}
