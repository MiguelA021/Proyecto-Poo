package upm.etsisi.poo.es.User;

import org.apache.commons.csv.CSVPrinter;

import java.io.IOException;

public class CustomerEnterprise extends Customer {
    public CustomerEnterprise(String email, String name, String id, int cashierId) {
        super(email, name, id, cashierId);
    }

    @Override
    public void printCsv(CSVPrinter csvPrinter) throws IOException {
        csvPrinter.printRecord("EnterpriseCustomer", email, name, cashierId, id);
        for (Integer tickid : tickets) {
            csvPrinter.printRecord(id, tickid);
        }
    }

    @Override
    public String toString() {
        return "COMPANY{identifier='" + id + "', name='" + name + "', email='" + email + "', cash=UW" + cashierId + "}";
    }
}
