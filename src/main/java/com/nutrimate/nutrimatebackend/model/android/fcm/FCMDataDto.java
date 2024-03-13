package com.nutrimate.nutrimatebackend.model.android.fcm;

import org.apache.ibatis.type.Alias;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Alias("FCMDataDto")
public class FCMDataDto {
    private Notification notification;
    private Data data;

    private String to;

    @Getter
    @Setter
    public static class Notification {
        private String title;
        private String body;
    }

    @Getter
    @Setter
    public static class Data {
        private String dataTitle;
        private String dataBody;
    }
}
