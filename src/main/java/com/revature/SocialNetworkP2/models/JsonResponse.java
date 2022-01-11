package com.revature.SocialNetworkP2.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class JsonResponse {
    private String message;
    private Object data;
}
