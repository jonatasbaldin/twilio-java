/**
 * This code was generated by
 * \ / _    _  _|   _  _
 *  | (_)\/(_)(_|\/| |(/_  v1.0.0
 *       /       /
 */

package com.twilio.rest.preview.proxy.service;

import com.twilio.base.Updater;
import com.twilio.converter.Promoter;
import com.twilio.exception.ApiConnectionException;
import com.twilio.exception.ApiException;
import com.twilio.exception.RestException;
import com.twilio.http.HttpMethod;
import com.twilio.http.Request;
import com.twilio.http.Response;
import com.twilio.http.TwilioRestClient;
import com.twilio.rest.Domains;

import java.util.List;

public class SessionUpdater extends Updater<Session> {
    private final String pathServiceSid;
    private final String pathSid;
    private String uniqueName;
    private Integer ttl;
    private Session.Status status;
    private List<String> participants;

    /**
     * Construct a new SessionUpdater.
     * 
     * @param pathServiceSid Service Sid.
     * @param pathSid A string that uniquely identifies this Session.
     */
    public SessionUpdater(final String pathServiceSid, 
                          final String pathSid) {
        this.pathServiceSid = pathServiceSid;
        this.pathSid = pathSid;
    }

    /**
     * Provides a unique and addressable name to be assigned to this Session,
     * assigned by the developer, to be optionally used in addition to SID..
     * 
     * @param uniqueName A unique, developer assigned name of this Session.
     * @return this
     */
    public SessionUpdater setUniqueName(final String uniqueName) {
        this.uniqueName = uniqueName;
        return this;
    }

    /**
     * How long will this session stay open, in seconds. Each new interaction resets
     * this timer..
     * 
     * @param ttl How long will this session stay open, in seconds.
     * @return this
     */
    public SessionUpdater setTtl(final Integer ttl) {
        this.ttl = ttl;
        return this;
    }

    /**
     * The Status of this Session. One of `in-progess` or `completed`..
     * 
     * @param status The Status of this Session
     * @return this
     */
    public SessionUpdater setStatus(final Session.Status status) {
        this.status = status;
        return this;
    }

    /**
     * The participants.
     * 
     * @param participants The participants
     * @return this
     */
    public SessionUpdater setParticipants(final List<String> participants) {
        this.participants = participants;
        return this;
    }

    /**
     * The participants.
     * 
     * @param participants The participants
     * @return this
     */
    public SessionUpdater setParticipants(final String participants) {
        return setParticipants(Promoter.listOfOne(participants));
    }

    /**
     * Make the request to the Twilio API to perform the update.
     * 
     * @param client TwilioRestClient with which to make the request
     * @return Updated Session
     */
    @Override
    @SuppressWarnings("checkstyle:linelength")
    public Session update(final TwilioRestClient client) {
        Request request = new Request(
            HttpMethod.POST,
            Domains.PREVIEW.toString(),
            "/Proxy/Services/" + this.pathServiceSid + "/Sessions/" + this.pathSid + "",
            client.getRegion()
        );

        addPostParams(request);
        Response response = client.request(request);

        if (response == null) {
            throw new ApiConnectionException("Session update failed: Unable to connect to server");
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

        return Session.fromJson(response.getStream(), client.getObjectMapper());
    }

    /**
     * Add the requested post parameters to the Request.
     * 
     * @param request Request to add post params to
     */
    private void addPostParams(final Request request) {
        if (uniqueName != null) {
            request.addPostParam("UniqueName", uniqueName);
        }

        if (ttl != null) {
            request.addPostParam("Ttl", ttl.toString());
        }

        if (status != null) {
            request.addPostParam("Status", status.toString());
        }

        if (participants != null) {
            for (String prop : participants) {
                request.addPostParam("Participants", prop);
            }
        }
    }
}