package de.fhflensburg.java.network.server.http;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.LineNumberReader;
import java.util.LinkedHashMap;
import java.util.Map;

public class HttpRequest
{
	private final HttpRequestMethod eRequestMethod;

	private final String sResource;

	private final Map<String, String> aHeaders = new LinkedHashMap<>();

	private final InputStream rDataStream;

	public HttpRequest(InputStream rRequestStream) throws IOException
	{
		LineNumberReader aRequestReader = new LineNumberReader(
				new InputStreamReader(rRequestStream));

		String[] aRequestElements = parseRequestLine(aRequestReader.readLine());

		eRequestMethod = parseRequestMethod(aRequestElements[0]);
		sResource = aRequestElements[1];

		checkHttpVersion(aRequestElements[2]);

		readHeaders(aRequestReader);

		rDataStream = rRequestStream;
	}
	public InputStream getDataStream()
	{
		return rDataStream;
	}
	public Map<String, String> getHeaders()
	{
		return aHeaders;
	}
	public HttpRequestMethod getRequestMethod()
	{
		return eRequestMethod;
	}

	public String getResource()
	{
		return sResource;
	}

	private void checkHttpVersion(String sVersion)
	{
		if (!"HTTP/1.1".equals(sVersion))
		{
			throw new HttpStatusException(HttpStatusCode.BAD_REQUEST,
					"Unsupported HTTP version");
		}
	}

	private String[] parseRequestLine(String sRequestLine)
			throws IOException
	{
		String[] aRequestElements = sRequestLine.split(" ");

		if (aRequestElements.length != 3)
		{
			throw new HttpStatusException(HttpStatusCode.BAD_REQUEST,
					"Invalid request");
		}
		return aRequestElements;
	}

	private HttpRequestMethod parseRequestMethod(String sMethod)
	{
		try
		{
			return HttpRequestMethod.valueOf(sMethod);
		}
		catch (Exception e)
		{
			throw new HttpStatusException(HttpStatusCode.BAD_REQUEST,
					"Unknown method: " + sMethod);
		}
	}

	private void readHeaders(LineNumberReader rRequestReader)
	{
		// TODO Add method code here

	}
}
