/**
 * This code was generated by
 * \ / _    _  _|   _  _
 *  | (_)\/(_)(_|\/| |(/_  v1.0.0
 *       /       /
 */

package com.twilio.rest.preview.proxy.service;

import com.twilio.base.Creator;
import com.twilio.exception.ApiConnectionException;
import com.twilio.exception.ApiException;
import com.twilio.exception.RestException;
import com.twilio.http.HttpMethod;
import com.twilio.http.Request;
import com.twilio.http.Response;
import com.twilio.http.TwilioRestClient;
import com.twilio.rest.Domains;

public class PhoneNumberCreator extends Creator<PhoneNumber> {
    private final String pathServiceSid;
    private final String sid;

    /**
     * Construct a new PhoneNumberCreator.
     * 
     * @param pathServiceSid The service_sid
     * @param sid Delete by unique phone-number Sid
     */
    public PhoneNumberCreator(final String pathServiceSid, 
                              final String sid) {
        this.pathServiceSid = pathServiceSid;
        this.sid = sid;
    }

    /**
     * Make the request to the Twilio API to perform the create.
     * 
     * @param client TwilioRestClient with which to make the request
     * @return Created PhoneNumber
     */
    @Override
    @SuppressWarnings("checkstyle:linelength")
    public PhoneNumber create(final TwilioRestClient client) {
        Request request = new Request(
            HttpMethod.POST,
            Domains.PREVIEW.toString(),
            "/Proxy/Services/" + this.pathServiceSid + "/PhoneNumbers",
            client.getRegion()
        );

        addPostParams(request);
        Response response = client.request(request);

        if (response == null) {
            throw new ApiConnectionException("PhoneNumber creation failed: Unable to connect to server");
        } else if (!TwilioRestClient.SUCCESS.apply(response.getStatusCode())) {
            RestException restException = RestException.fromJson(response.getStream(), client.getObjectMapper());
            if (restException == null) {
                throw new ApiException("Server Error, no content");
            }

            throw new ApiException(
                restException.getMessage(),
                restException.getCode(),
                restException.getMoreInfo(),
                restException.getStatus(),
                null
            );
        }

        return PhoneNumber.fromJson(response.getStream(), client.getObjectMapper());
    }

    /**
     * Add the requested post parameters to the Request.
     * 
     * @param request Request to add post params to
     */
    private void addPostParams(final Request request) {
        if (sid != null) {
            request.addPostParam("Sid", sid);
        }
    }
}