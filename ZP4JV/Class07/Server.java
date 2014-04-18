package Class07;

import java.net.Socket;
import java.net.ServerSocket;
import java.io.IOException;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.File;
import java.io.FileReader;

public class Server {

	private static String directory;
	private static boolean stop = false;

	public static void main(String[] args) throws IOException {
		
		try { 
			directory = args[0];
		}
		catch (Exception e) {
			directory = "/";
		}
		
		ServerSocket serverSocket = new ServerSocket(4321);	// port
		System.out.println("Waiting ...");
		
		while (!stop) {
			Socket clientSocket = serverSocket.accept();
			new MyThread(clientSocket).start();
		}

		System.out.println("Server socket closing.");
		serverSocket.close();
	}

	private static class MyThread extends Thread {
		private Socket socket;
		
		public MyThread(Socket socket) {
			this.socket = socket;
		}
		
		public void run() {
			try {
				System.out.println("Client connected.");
				
				InputStream is = socket.getInputStream();
				OutputStream os = socket.getOutputStream();
				BufferedWriter wr = new BufferedWriter(new OutputStreamWriter(os));
				BufferedReader rd = new BufferedReader(new InputStreamReader(is));
				
				while (true) {
					String[] args = rd.readLine().split(" ");
					
					switch (args[0]) {
					case "q":
						System.out.println("- quit\nClient connection ternimated.");
						is.close();
						os.close();
						socket.close();
						return;
					case "dir":
						System.out.println("- dir");
						StringBuilder result = new StringBuilder();

						File dir = new File(directory);
						for (File f : dir.listFiles())
							if (f.isFile())
								result.append(f.getName() + "\n");
						wr.write(result.toString());
						wr.flush();
						break;
					case "get":
						try {
							System.out.println("- get " + args[1]);
							try {
								wr.write(readFile(directory + args[1]));
							} catch (Exception e) {
								wr.write("Fail. File not found.");
								wr.newLine();
							}
						} catch (Exception e) {
							System.out.println("- get");
							wr.write("Fail. File name missing.");
							wr.newLine();
						}

						wr.flush();
						break;
					default:
						System.out.println("- unknown command " + args[0]);
						wr.write("Fail. Command '" + args[0] + "' not found.");
						wr.newLine();
						wr.flush();
					}
				}
			} catch (Exception e) {
				e.printStackTrace();
			} finally {
				try {
					socket.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}

	private static String readFile(String name) throws IOException {
		StringBuilder result = new StringBuilder();
		BufferedReader br = new BufferedReader(new FileReader(name));

		String line = null;
		while ((line = br.readLine()) != null)
			result.append(line + "\n");

		br.close();
		return result.toString();
	}
}