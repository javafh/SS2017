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

	private void badRequest(String sMessage)
	{
		throw new HttpStatusException(HttpStatusCode.BAD_REQUEST,
				sMessage);
	}

	private void checkHttpVersion(String sVersion)
	{
		if (!"HTTP/1.1".equals(sVersion))
		{
			throw new HttpStatusException(HttpStatusCode.BAD_REQUEST,
					"Unsupported HTTP version");
		}
	}

	private String[] parseRequestLine(String sRequestLine) throws IOException
	{
		String[] aRequestElements = sRequestLine.split(" ");

		if (aRequestElements.length != 3)
		{
			throw new HttpStatusException(HttpStatusCode.BAD_REQUEST,
					"Invalid request");
		}
		return aRequestElements;
	}

	/***************************************
	 * TODO: `Description`
	 *
	 * @param sMethod
	 * @return
	 */
	private HttpRequestMethod parseRequestMethod(String sMethod)
	{
		try
		{
			return HttpRequestMethod.valueOf(sMethod);
		}
		catch (Exception e)
		{
			badRequest("Unknown method: " + sMethod);
		}
	}

	/**
	 * Read and parse all header lines into the headers map.
	 *
	 * @param rRequestReader A reader that is positioned on the request headers
	 */
	private void readHeaders(LineNumberReader rRequestReader) throws IOException
	{
		String sLine;

		// read header lines until CRLF
		while (!(sLine = rRequestReader.readLine()).isEmpty())
		{
			String[] aHeader = sLine.split(":");

			if (aHeader.length != 2 && aHeader[0].length() > 0)
			{
				badRequest("Invalid header property: " + sLine);
			}

		}
	}
}

