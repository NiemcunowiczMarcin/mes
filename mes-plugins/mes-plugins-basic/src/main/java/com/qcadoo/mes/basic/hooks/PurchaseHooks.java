package com.qcadoo.mes.basic.hooks;

import org.springframework.stereotype.Service;

import com.qcadoo.mes.basic.constants.ProductFields;
import com.qcadoo.mes.basic.constants.PurchaseFields;
import com.qcadoo.model.api.DataDefinition;
import com.qcadoo.model.api.Entity;
import com.qcadoo.model.api.search.JoinType;
import com.qcadoo.model.api.search.SearchRestrictions;
import com.qcadoo.model.api.search.SearchResult;

@Service
public class PurchaseHooks {

    public boolean checkIfSimilarPurchaseExists(final DataDefinition dataDefinition, final Entity purchase) {
        // SearchResult purchases = dataDefinition.find("where "+PurchaseFields.PRODUCT+"=:"+).list();
        SearchResult purchases = dataDefinition
                .find()
                .createAlias(PurchaseFields.PRODUCT, "product", JoinType.INNER)
                .add(SearchRestrictions.eq("product." + ProductFields.NAME, purchase.getBelongsToField(PurchaseFields.PRODUCT)
                        .getStringField(ProductFields.NAME)))
                .add(SearchRestrictions.eq(PurchaseFields.QUANTITY, purchase.getIntegerField(PurchaseFields.QUANTITY)))
                .add(SearchRestrictions.eq(PurchaseFields.PRICE, purchase.getDecimalField(PurchaseFields.PRICE))).list();
        if (purchases.getTotalNumberOfEntities() > 0) {
            return false;
        } else {
            return true;
        }
    }
}
