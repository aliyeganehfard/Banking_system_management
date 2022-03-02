package Controller;

import Model.entity.BankBranch;
import Model.entity.BankEmployee;
import Model.repository.BankEmployeeRepository;

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
