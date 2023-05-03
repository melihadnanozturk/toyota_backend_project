package com.mao.tytmistake.controller.request;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class LocationRemoveRequest {

    private List<Long> locationIds;
}
