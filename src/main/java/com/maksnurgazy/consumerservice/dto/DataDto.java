package com.maksnurgazy.consumerservice.dto;


import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;
import com.maksnurgazy.consumerservice.enums.ServiceType;
import lombok.Data;

@Data
@JacksonXmlRootElement(localName = "Data")
public class DataDto {
    @JacksonXmlProperty(localName = "Method")
    private MethodDto method;

    @JacksonXmlProperty(localName = "Process")
    private ProcessDto process;

    @JacksonXmlProperty(localName = "Layer")
    private String layer;

    @JacksonXmlProperty(localName = "Creation")
    private EpochDateDto creation;

    @JacksonXmlProperty(localName = "Type")
    @JsonProperty("Type")
    private ServiceType serviceType;
}
