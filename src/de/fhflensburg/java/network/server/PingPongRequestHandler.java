package de.fhflensburg.java.network.server;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.logging.Logger;

public class PingPongRequestHandler implements RequestHandler
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