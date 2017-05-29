package de.fhflensburg.java.network.server;

import java.util.logging.Logger;

public class SimpleServerTest
{

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
}
