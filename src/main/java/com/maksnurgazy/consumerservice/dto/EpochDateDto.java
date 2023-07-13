package com.maksnurgazy.consumerservice.dto;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import lombok.Data;

import java.util.Date;

@Data
public class EpochDateDto {
    @JacksonXmlProperty(localName = "Epoch")
    public double epoch;

    @JacksonXmlProperty(localName = "Date")
    public Date date;
}
