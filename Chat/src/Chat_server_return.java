import java.io.*;
import java.net.*;
import java.util.Scanner;
public class Chat_server_return implements Runnable {

	Socket Sock;
	private Scanner input;
	private PrintWriter out;
	String message=" ";

//-----------------
	public Chat_server_return(Socket x)
	{
		this.Sock=x;
	}	
//-----------------------
	public void checkConnection() throws IOException
	{
		if(!Sock.isConnected())
		{
			for(int i=1 ; i<=Chat_server.connectionArray.size() ; i++)
			{
				if(Chat_server.connectionArray.get(i) == Sock) //get object
				{
					Chat_server.connectionArray.remove(i); //remove the socket from arraylist
				}
			}
			for(int i=1 ; i<=Chat_server.connectionArray.size() ; i++)
			{	
				Socket temp_Socket= (Socket)Chat_server.connectionArray.get(i-1);
				PrintWriter temp_out=new PrintWriter(temp_Socket.getOutputStream());
				temp_out.println(temp_Socket.getLocalAddress().getHostName()+" disconnected: ");
				temp_out.flush();
				//shoe disconnection at server
				System.out.println(temp_Socket.getLocalAddress().getHostName()+" disconnected");
				}
		}
	}
	
	//------------------------
	public void run()
	{
		try
		{
			try
			{
				input=new Scanner(Sock.getInputStream());//incoming from server
				out=new PrintWriter(Sock.getOutputStream());//outgoing to server
				
				while(true)
				{
					checkConnection();
					
					if(!input.hasNext()) 
					{
						return ;
					}
					message =input.nextLine();//read the message
					
					System.out.println("Client said: "+ message);
					
					for(int i=1 ; i<=Chat_server.connectionArray.size() ; i++)
					{
						Socket temp_Socket=(Socket)Chat_server.connectionArray.get(i-1);
						PrintWriter temp_out=new PrintWriter(temp_Socket.getOutputStream());
						temp_out.println(message);
						temp_out.flush();
						System.out.println("sent to:  " +temp_Socket.getLocalAddress().getHostName());
					}
				}
			}
			finally
			{
				Sock.close();
			}
		}
		catch(Exception x)
		{
			System.out.print(x);
			}
		}
	}