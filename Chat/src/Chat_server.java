
import java.io.*;
import java.net.*;
import java.util.ArrayList;
import java.util.Scanner;
import javax.swing.JOptionPane;

public class Chat_server {
	
	public static ArrayList<Socket>connectionArray = new ArrayList<Socket>();
	public static ArrayList<String>Currentusers =new ArrayList<String>();

	public static void main(String[] args)throws IOException 
	{
		// TODO Auto-generated method stub
		try
		{
			final int port= 5000;
			ServerSocket server=new ServerSocket(port);
			System.out.println("Waiting for Clients.... ");
			
			while(true)
			{
				Socket sock=server.accept();
				connectionArray.add(sock);
				
				System.out.println("Clients connected from : " +sock.getLocalAddress().getHostName());
				 
				AddUserName(sock);
				
				Chat_server_return chat=new Chat_server_return(sock);
				Thread x= new Thread(chat);
				x.start();
			}
		}
		 catch(Exception x)
		{
			 System.out.println(x);
		}
	}
	
	//--------------
	
	public static void AddUserName(Socket x)throws IOException
	{
		Scanner input=new Scanner(x.getInputStream());
		String userName=input.nextLine();
		Currentusers.add(userName);
		
		for(int i=1; i<=Chat_server.connectionArray.size();i++)
		{
			Socket temp_socket= (Socket)Chat_server.connectionArray.get(i-1);
			PrintWriter out=new PrintWriter(temp_socket.getOutputStream());
			out.println("#?!"+ Currentusers);
			out.flush();
		}
		
	}
}
