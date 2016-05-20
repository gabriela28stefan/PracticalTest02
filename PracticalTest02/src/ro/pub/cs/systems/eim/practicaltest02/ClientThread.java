package ro.pub.cs.systems.eim.practicaltest02;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;

import android.util.Log;
import android.widget.TextView;


public class ClientThread extends Thread{
	
	private String address;
	private int port;
	private TextView showTimeTextView;
	
	private Socket socket;
	
	public ClientThread(String address,
			int port, 
			TextView showTimeTextView){
		
		this.address = address;
		this.port = port;
		this.showTimeTextView = showTimeTextView;
		
		
	}
	
	
	   @Override
	    public void run() {
	        try {
	            socket = new Socket(address, port);
	            if (socket == null) {
	                Log.e(Constants.TAG, "[CLIENT THREAD] Could not create socket!");
	            }

	            BufferedReader bufferedReader = Utilities.getReader(socket);
	            PrintWriter printWriter = Utilities.getWriter(socket);
	            if (bufferedReader != null && printWriter != null) {
	                //printWriter.println("Get Time!");
	                //printWriter.flush();
	               // printWriter.println(informationType);
	                //printWriter.flush();
	                
	                String timeInformation;
	                while ((timeInformation = bufferedReader.readLine()) != null) {
	                    final String finalizedTimeInformation = timeInformation;
	                    showTimeTextView.post(new Runnable() {
	                        @Override
	                        public void run() {
	                            showTimeTextView.append(finalizedTimeInformation + "\n");
	                        }
	                    });
	                }
	            } else {
	                Log.e(Constants.TAG, "[CLIENT THREAD] BufferedReader / PrintWriter are null!");
	            }
	            socket.close();
	        } catch (IOException ioException) {
	            Log.e(Constants.TAG, "[CLIENT THREAD] An exception has occurred: " + ioException.getMessage());
	            if (Constants.DEBUG) {
	                ioException.printStackTrace();
	            }
	        }
	    }
	
	

}
