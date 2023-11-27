package com.trace.sdkapp;

import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
public class Record_expend {
    String Trace_code;
    String Source;
    String CompoundID;
    String Timestamp;
    String Responsible_person;
    String Operator;
    String Report_hash;
}
