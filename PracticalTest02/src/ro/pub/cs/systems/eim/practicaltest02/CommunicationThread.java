package ro.pub.cs.systems.eim.practicaltest02;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.HashMap;

import org.apache.http.client.HttpClient;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.BasicResponseHandler;
import org.apache.http.impl.client.DefaultHttpClient;

import android.util.Log;

public class CommunicationThread extends Thread{

	private ServerThread serverThread = null;
	private Socket socket = null;
	
	public CommunicationThread(ServerThread serverThread, Socket socket){
		this.serverThread = serverThread;
		this.socket = socket;
	}
	

    @Override
    public void run() {
        if (socket != null) {
            try {
                BufferedReader bufferedReader = Utilities.getReader(socket);
                PrintWriter printWriter = Utilities.getWriter(socket);
                
                HttpClient httpClient = new DefaultHttpClient();
                
                HttpGet httpGet = new HttpGet(Constants.WEB_SERVICE_ADDRESS);
                
                ResponseHandler<String> responseHandler = new BasicResponseHandler();
                String pageSourceCode = httpClient.execute(httpGet, responseHandler);
                
                
                
                if (bufferedReader != null && printWriter != null) {
                    Log.i(Constants.TAG, "[COMMUNICATION THREAD] Waiting for parameters from client (city / information type)!");

                    HashMap<String, TimeInformation> data = serverThread.getData();
                    TimeInformation timeInformation = null;
                    
                    String clientIP = socket.getInetAddress().toString();
                    
                    if (data.containsKey(clientIP)) {
                        Log.i(Constants.TAG, "[COMMUNICATION THREAD] Getting the information from the cache...");
                   
                    	/*if (Integer.parseInt(timeInformation.getMinute()) - Integer.parseInt(data.get(clientIP).getMinute()) < 1) {
                		Log.e(Constants.TAG, "[COMMUNICATION THREAD] Time not exceeded!");*/ 
                        
                        timeInformation = data.get(clientIP);
                    } else {

                        
                        if(pageSourceCode != null){

        
                        	timeInformation = new TimeInformation(pageSourceCode);

                        	serverThread.setData(clientIP, timeInformation);
                        } else {
                        	 Log.e(Constants.TAG, "[COMMUNICATION THREAD] Error getting the information from the webservice!");
                        }

                    }
                    
                	/*if (Integer.parseInt(timeInformation.getMinute()) - Integer.parseInt(data.get(clientIP).getMinute()) < 1) {
                		Log.e(Constants.TAG, "[COMMUNICATION THREAD] Time not exceeded!");
                	*/ 
                	
                	if (timeInformation != null) {
                    	String result = timeInformation.getTime();
                        printWriter.println(result);
                        printWriter.flush();
                    }
                    
                    
                    
     
                } else {
                    Log.e(Constants.TAG, "[COMMUNICATION THREAD] BufferedReader / PrintWriter are null!");
                }
                socket.close();
            } catch (IOException ioException) {
                Log.e(Constants.TAG, "[COMMUNICATION THREAD] An exception has occurred: " + ioException.getMessage());
                if (Constants.DEBUG) {
                    ioException.printStackTrace();
                }
            }
        } else {
            Log.e(Constants.TAG, "[COMMUNICATION THREAD] Socket is null!");
        }
    }

	
	
	
}
