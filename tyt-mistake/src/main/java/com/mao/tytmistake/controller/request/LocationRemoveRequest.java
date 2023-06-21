package com.mao.tytmistake.controller.request;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

/**
 * This class is used to remove query location
 * This class is POJO class that contains location ids.
 * The class uses the Lombok annotations @Getter, @Setter to automatically generate getters and setters
 */
@Getter
@Setter
public class LocationRemoveRequest {

    private List<Long> locationIds;
}
