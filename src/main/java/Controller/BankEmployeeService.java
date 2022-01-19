package Controller;

import Model.BankBranch;
import Model.BankEmployee;
import Model.BankEmployeeRepository;

import java.util.List;

public class BankEmployeeService {
    BankEmployeeRepository bankEmployeeRepository = new BankEmployeeRepository();
//    insert
    public void insert(BankEmployee bankEmployee){
        bankEmployeeRepository.insert(bankEmployee);
    }
//    show all bank branch employee
    public List<BankEmployee> findAll(BankBranch bankBranch){
        return bankEmployeeRepository.findAll(bankBranch);
    }

}
