package br.com.finsavior.events.processor.model.dto;

import br.com.finsavior.events.processor.model.constant.EventTypeEnum;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.Data;

@Data
public class WebhookRequestDTO {

    private String id;

    @JsonProperty("create_time")
    private String createTime;

    @JsonProperty("event_type")
    @Enumerated(EnumType.STRING)
    private EventTypeEnum event_type;

    private String summary;

    private ResourceDTO resource;
}
