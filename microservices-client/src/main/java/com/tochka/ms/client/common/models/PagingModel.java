package com.tochka.ms.client.common.models;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PagingModel {

    /**
     * Офсет
     */
    private Integer page;

    /**
     * Количество
     */
    private Integer rows;

    public Integer offset(){
        return page * rows;
    }

    public static PagingModel single(){
        return new PagingModel(0, 1);
    }
}
