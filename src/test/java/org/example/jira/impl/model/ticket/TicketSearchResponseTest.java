package org.example.jira.impl.model.ticket;

import com.google.gson.Gson;
import org.example.jira.impl.model.ticket.internal.Issue;
import org.example.jira.impl.model.ticket.internal.Sections;
import org.junit.Test;
import retrofit2.converter.gson.GsonConverterFactory;

import static org.junit.Assert.*;

public class TicketSearchResponseTest {
    private final Gson gson = new Gson();

    @Test
    public void json_to_TicketSearchResponse(){
        String json = "{\"sections\":[{\"label\":\"History Search\",\"sub\":\"Showing 1 of 1 matching issues\",\"id\":\"hs\",\"issues\":[{\"id\":10037,\"key\":\"NSU-38\",\"keyHtml\":\"NSU-38\",\"img\":\"/rest/api/2/universal_avatar/view/type/issuetype/avatar/10300?size=medium\",\"summary\":\"DISABLE |USERNAME|RESOURCE\",\"summaryText\":\"DISABLE |USERNAME|RESOURCE\"}]}]}";

        TicketSearchResponse response = gson.fromJson(json, TicketSearchResponse.class);

        Sections[] sections = response.getSections();
        assertNotNull(sections);
        assertEquals(1, sections.length);

        Issue[] issues = sections[0].getIssues();
        assertNotNull(sections);
        assertEquals(1, issues.length);

        Issue issue = issues[0];
        assertEquals("NSU-38", issue.getKey());
        assertEquals("DISABLE |USERNAME|RESOURCE", issue.getSummary());

    }
}