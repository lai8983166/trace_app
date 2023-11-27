package com.trace.sdkapp;

import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
public class Record_storage {
    String Trace_code;
    String Location;
    String Source;
    String CompoundID;
    String Timestamp;
    String Responsible_person;
    String Report_hash;
}
