package com.prm.gsms.services;

import static com.prm.gsms.utils.GsmsUtils.*;

import com.prm.gsms.dtos.Customer;
import com.prm.gsms.utils.GsmsUtils;

import java.io.IOException;

public class CustomerService {
    public Customer getCustomerInfoById(String customerId) throws IOException {
        Customer foundCustomer = null;
        foundCustomer = GsmsUtils.<Customer>fetchData(BASE_URL + "customer/" + customerId, "GET", Customer.class, null, "");

        return foundCustomer;
    }
}
