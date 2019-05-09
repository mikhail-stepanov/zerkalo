package com.tochka.ms.client.message.models.transacitons;

import lombok.Data;

import java.text.SimpleDateFormat;
import java.util.Date;

@Data
public class MessageListRequest {

    private String timestamp;

    private String userId;

    private Integer limit;

    public Date getTimestamp() {

        if (timestamp == null) {
            return null;
        }

        SimpleDateFormat formatter = new SimpleDateFormat("dd.MM.yyyy HH:mm:ss");
        try {
            return formatter.parse(timestamp);
        } catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }
    }
}
