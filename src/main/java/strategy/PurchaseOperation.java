package strategy;

import db.Storage;
import model.FruitTransaction;

public class PurchaseOperation implements OperationHandler {
    @Override
    public void handle(FruitTransaction transaction) {
        String fruit = transaction.getFruit();
        int currentQuantity = Storage.fruits.getOrDefault(fruit, 0);
        int newQuantity = currentQuantity - transaction.getQuantity();

        if (newQuantity < 0) {
            throw new RuntimeException("Not enough fruits in storage for purchase: " + fruit
                    + ". Available: " + currentQuantity + ", requested: "
                    + transaction.getQuantity());
        }
        Storage.fruits.put(fruit, newQuantity);
    }
}

