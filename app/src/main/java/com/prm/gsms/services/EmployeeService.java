package com.prm.gsms.services;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.prm.gsms.dtos.Employee;
import com.prm.gsms.dtos.ImportOrder;
import com.prm.gsms.utils.GsmsUtils;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class EmployeeService {
    public static Employee getEmployee(String employeeJson){
        Gson gson = GsmsUtils.createGson();
        Employee employee = null;
        Type type = new TypeToken<Employee>(){}.getType();
        employee = gson.fromJson(employeeJson, type);
        return employee;
    }
}
