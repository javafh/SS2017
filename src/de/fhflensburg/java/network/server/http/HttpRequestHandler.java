package de.fhflensburg.java.network.server.http;

import java.io.InputStream;
import java.io.OutputStream;

import de.fhflensburg.java.network.server.RequestHandler;

public class HttpRequestHandler implements RequestHandler
{
	@Override
	public void handleRequest(InputStream rRequestStream,
			OutputStream rResponseStream) throws Exception
	{
		HttpRequest aRequest = new HttpRequest(rRequestStream);

		handleRequestMethod(aRequest, rRequestStream, rResponseStream);
	}

	private void handleRequestMethod(HttpRequest rRequest,
			InputStream rRequestStream, OutputStream rResponseStream)
	{
		switch (rRequest.getRequestMethod())
		{
			case DELETE :
				break;
			case GET :
				break;
			case POST :
				break;
			case PUT :
				break;

		}
	}

}
