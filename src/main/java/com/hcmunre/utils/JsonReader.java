package com.hcmunre.utils;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.hcmunre.models.UserData;

import java.io.File;
import java.io.IOException;
import java.util.List;

public class JsonReader {
    public static List<UserData> readUsers(String filePath) {
        ObjectMapper mapper = new ObjectMapper();
        try {
            return mapper.readValue(new File(filePath), new TypeReference<List<UserData>>() {
            });
        } catch (IOException e) {
            throw new RuntimeException("Lỗi khi đọc file JSON: " + filePath, e);
        }
    }
}
