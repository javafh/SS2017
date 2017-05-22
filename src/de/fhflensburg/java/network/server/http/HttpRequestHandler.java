package de.fhflensburg.java.network.server.http;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.LineNumberReader;
import java.io.OutputStream;

import de.fhflensburg.java.network.server.RequestHandler;

public class HttpRequestHandler implements RequestHandler
{

	@Override
	public void handleRequest(InputStream rRequestStream,
			OutputStream rResponseStream) throws Exception
	{
		LineNumberReader aRequestReader = new LineNumberReader(
				new InputStreamReader(rRequestStream));

		String sRequestLine = aRequestReader.readLine();
		String[] aRequestElements = sRequestLine.split(" ");

		if (aRequestElements.length != 3)
		{
			throw new HttpStatusException(HttpStatusCode.BAD_REQUEST, "Invalid request");
		}
	}

}
