package de.fhflensburg.java.network.server.http;

/********************************************************************
 * An unchecked exception to report exceptional HTTP responses.
 *
 * @author eso
 */
public class HttpStatusException extends RuntimeException
{
	private static final long serialVersionUID = 1L;

	private final HttpStatusCode eStatusCode;

	/***************************************
	 * Creates a new instance with a status code and causing exception.
	 *
	 * @param eStatusCode The status code
	 * @param eCause The causing exception
	 */
	public HttpStatusException(HttpStatusCode eStatusCode, Exception eCause)
	{
		super(eCause);

		this.eStatusCode = eStatusCode;
	}

	/***************************************
	 * Creates a new instance with a status code and message.
	 *
	 * @param eStatusCode The status code
	 * @param sMessage The error message
	 * @param rResponseHeaders An optional array of headers to be set on the
	 *            response
	 */
	public HttpStatusException(HttpStatusCode eStatusCode, String sMessage)
	{
		super(sMessage);

		this.eStatusCode = eStatusCode;
	}

	/***************************************
	 * Creates a new instance with a status code, message, and causing
	 * exception.
	 *
	 * @param eStatusCode The status code
	 * @param sMessage The error message
	 * @param eCause The causing exception
	 */
	public HttpStatusException(HttpStatusCode eStatusCode, String sMessage,
			Exception eCause)
	{
		super(sMessage, eCause);

		this.eStatusCode = eStatusCode;
	}

	/***************************************
	 * Returns the HTTP status code.
	 *
	 * @return The status code
	 */
	public final HttpStatusCode getStatusCode()
	{
		return eStatusCode;
	}

}
