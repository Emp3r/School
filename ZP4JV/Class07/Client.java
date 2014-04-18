package Class07;

import java.net.Socket;
import java.io.IOException;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.Scanner;

public class Client {

	public static void main(String[] args) throws IOException {

		Scanner scan = new Scanner(System.in);
		Socket socket = new Socket("127.0.0.1", 4321);  // port

		InputStream is = socket.getInputStream();
		OutputStream os = socket.getOutputStream();
		BufferedWriter wr = new BufferedWriter(new OutputStreamWriter(os));
		BufferedReader rd = new BufferedReader(new InputStreamReader(is));

		while (true) {
			System.out.print("> ");
			String line = scan.nextLine();
			wr.write(line + "\n");
			wr.flush();

			String output = null;
			while ((output = rd.readLine()) != null) {
				System.out.println(output);
				if (!rd.ready())
					break;
			}

			if(line.equals("q") || line.equals("stop"))
				break;
		}
		
		System.out.println("Client terminating.");
		
		scan.close();
		is.close();
		os.close();
		socket.close();
	}
}