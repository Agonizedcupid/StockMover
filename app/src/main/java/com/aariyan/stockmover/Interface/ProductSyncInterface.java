package com.aariyan.stockmover.Interface;

import com.aariyan.stockmover.Model.ProductsSyncModel;

import java.util.List;

public interface ProductSyncInterface {

    void productList(List<ProductsSyncModel> list);
    void error(String message);
}
