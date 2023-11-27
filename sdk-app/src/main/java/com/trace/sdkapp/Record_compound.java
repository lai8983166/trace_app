package com.trace.sdkapp;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Accessors(chain = true)
public class Record_compound {
    private String traceCode;
    private String compoundID;
    private String timestamp;
    private String manufacturer;
    private String responsiblePerson;
    private String reportHash;
}
