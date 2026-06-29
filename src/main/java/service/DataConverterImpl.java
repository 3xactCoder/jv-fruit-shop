package service.impl;

import java.util.ArrayList;
import java.util.List;
import model.FruitTransaction;
import service.DataConverter;

public class DataConverterImpl implements DataConverter {
    private static final int TYPE_INDEX = 0;
    private static final int FRUIT_INDEX = 1;
    private static final int QUANTITY_INDEX = 2;
    private static final String CSV_SEPARATOR = ",";
    private static final String HEADER_FIRST_WORD = "type";

    @Override
    public List<FruitTransaction> convertToTransaction(List<String> lines) {
        List<FruitTransaction> transactions = new ArrayList<>();
        for (String line : lines) {
            if (line.startsWith(HEADER_FIRST_WORD) || line.isBlank()) {
                continue;
            }
            String[] textParts = line.split(CSV_SEPARATOR);
            String operationCode = textParts[TYPE_INDEX].trim();
            String fruit = textParts[FRUIT_INDEX].trim();
            int quantity = Integer.parseInt(textParts[QUANTITY_INDEX].trim());

            if (quantity < 0) {
                throw new RuntimeException("Quantity cannot be negative for fruit: "
                        + fruit);
            }

            FruitTransaction.Operation operation = FruitTransaction
                    .Operation.getByCode(operationCode);
            transactions.add(new FruitTransaction(operation, fruit, quantity));
        }
        return transactions;
    }
}

