package com.maksnurgazy.consumerservice.dto;


import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import lombok.Data;

@Data
public class MethodDto {
    @JacksonXmlProperty(localName = "Name")
    public String name;

    @JacksonXmlProperty(localName = "Type")
    public String type;

    @JacksonXmlProperty(localName = "Assembly")
    public String assembly;
}
