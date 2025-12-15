package com.marotech.vending.api;

import com.marotech.vending.util.Page;
import lombok.Data;

@Data
public class ProductsRequest extends BaseRequest{
    private Page page;
}