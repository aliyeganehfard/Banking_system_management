package Controller;

import Model.entity.Bank;
import Model.entity.BankBranch;
import Model.repository.BankRepository;

import java.util.List;

public class BankService {
    BankRepository bankRepository = new BankRepository();

//    add new bank
    public void insert(Bank bank){

        bankRepository.insert(bank);
    }
//    find all bank
    public List<Bank> findAll(){
        return bankRepository.findAll();
    }

//    find selected bank
    public Bank findById(Integer id){
        return bankRepository.findById(id);
    }

//    find bank branches
    public List<BankBranch> findAllBranch(Bank bank){
        return bankRepository.findAllBranch(bank);
    }

}
