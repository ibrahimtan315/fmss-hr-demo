package com.fmss.hr.services.admin.zoom;


import com.fmss.hr.dto.zoom.MeetingDto;
import com.fmss.hr.dto.zoom.MeetingResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;


@Service
public class ZoomService {

    @Value("${bearer.token}")
    private String bearerToken;
    @Value("${company.mail}")
    private String companyMail;

    private final RestTemplate restTemplate;

    public ZoomService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public MeetingResponse scheduleMeeting(MeetingDto meetingDto){
        String zoomUrl="https://api.zoom.us/v2/users/" + companyMail + "/meetings";
        HttpHeaders headers = new HttpHeaders();
        headers.setBearerAuth(bearerToken);
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<MeetingDto> entity = new HttpEntity<>(meetingDto, headers);

        ResponseEntity<MeetingResponse> response = restTemplate.exchange(zoomUrl, HttpMethod.POST, entity, MeetingResponse.class);
        MeetingResponse meetingResponse = response.getBody();

        return meetingResponse;
    }

    public void deleteMeeting(Long id){
        String zoomUrl="https://api.zoom.us/v2/meetings/"+  id  +"?occurrence_id=occaecat ut";
        HttpHeaders headers = new HttpHeaders();
        headers.setBearerAuth(bearerToken);
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<String> entity = new HttpEntity<>(headers);

        ResponseEntity<String> response = restTemplate.exchange(zoomUrl,HttpMethod.DELETE,entity , String.class);
        }


}
