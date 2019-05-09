package com.tochka.ms.client.common.models;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ResultPagingModel {

    /**
     * Офсет
     */
    private Integer page;

    /**
     * Количество
     */
    private Integer rows;

    /**
     * Общее количество элементов
     */
    private Long count;
}
