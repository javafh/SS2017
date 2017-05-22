package de.fhflensburg.java.network.server;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.CompletableFuture;
import java.util.logging.Logger;

public class Server
{
	private RequestHandler rRequestHandler;
	private int nListeningPort;

	public Server(RequestHandler rRequestHandler, int nListeningPort)
	{
		this.rRequestHandler = rRequestHandler;
		this.nListeningPort = nListeningPort;
	}

	public static void main(String[] rArgs)
	{
		Server aServer = new Server(new PingPongRequestHandler(), 8000);

		try
		{
			aServer.run();
		}
		catch (Exception e)
		{
			Logger.getGlobal().severe(e.getMessage());
		}
	}

	public void run() throws Exception
	{
		try (ServerSocket aServerSocket = new ServerSocket(nListeningPort))
		{
			Logger.getGlobal()
			.info(String.format("Server started, listening on %s:%d",
					InetAddress.getLocalHost(), nListeningPort));

			while (true)
			{
				Socket rClientSocket = aServerSocket.accept();


				CompletableFuture
				.runAsync(() -> handleClientRequest(rClientSocket));
			}

		}
	}

	private void handleClientRequest(Socket rClientSocket)
	{
		try
		{
			Logger.getGlobal().info(
					"Request received from " + rClientSocket.getInetAddress());

			InputStream rInput = rClientSocket.getInputStream();
			OutputStream rOutput = rClientSocket.getOutputStream();

			rRequestHandler.handleRequest(rInput, rOutput);
		}
		catch (Exception e)
		{
			Logger.getGlobal().warning(e.getMessage());
		}
		finally
		{
			try
			{
				rClientSocket.close();
			}
			catch (IOException e)
			{
				Logger.getGlobal().warning(
						"Could not close client socket: " + e.getMessage());
			}
		}
	}

	public static class PingPongRequestHandler implements RequestHandler
	{
		@Override
		public void handleRequest(InputStream rInput, OutputStream rOutput)
				throws IOException
		{
			ByteArrayOutputStream aRequest = new ByteArrayOutputStream();
			int nByte;

			while (aRequest.size() < 4 && (nByte = rInput.read()) > 0)
			{
				aRequest.write(nByte);
			}

			String sRequest = new String(aRequest.toByteArray());

			if ("PING".equals(sRequest))
			{
				rOutput.write("PONG".getBytes());
			}
			else
			{
				rOutput.write(("Unknown request: " + sRequest).getBytes());
				String sLogMessage = String.format(
						"Invalid request: %s", sRequest);
				Logger.getGlobal().warning(sLogMessage);
			}
		}
	}
}
