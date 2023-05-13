package org.example.jira.impl.model.ticket;

import com.google.gson.annotations.SerializedName;
import org.example.jira.impl.model.ticket.internal.Sections;

public class TicketSearchResponse {
    @SerializedName("sections")
    private Sections[] sections;

    TicketSearchResponse(){}

    public Sections[] getSections() {
        return sections;
    }
}
