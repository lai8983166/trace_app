package com.trace.sdkapp;

import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
public class Record_transport {
    String Trace_code;
    String Source;
    String CompoundID;
    String Timestamp;
    String Destination;
    String Responsible_person;
    String Report_hash;
}