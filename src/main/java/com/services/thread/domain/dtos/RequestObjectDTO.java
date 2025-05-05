package com.services.thread.domain.dtos;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder(toBuilder = true)
@NoArgsConstructor
@AllArgsConstructor
public class RequestObjectDTO {

    private String url_start;

    private int id_user;

    private int max_archives;

    private int max_pages;

}
