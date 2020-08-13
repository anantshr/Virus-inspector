package com.hdms.antivirus.feature;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public class VirusScanResponse {

    private final int statusCode;
    private final String responseMsg;
}
