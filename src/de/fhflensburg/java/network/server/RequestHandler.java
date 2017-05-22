package de.fhflensburg.java.network.server;

import java.io.InputStream;
import java.io.OutputStream;

@FunctionalInterface
public interface RequestHandler
{
	public void handleRequest(InputStream rRequestStream,
			OutputStream rResponseStream) throws Exception;
}
