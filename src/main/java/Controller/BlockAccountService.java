package Controller;

import Model.entity.BankBranch;
import Model.entity.BlockAccount;
import Model.repository.BlockAccountRepository;

import java.util.List;

public class BlockAccountService {
    private BlockAccountRepository blockAccountRepository = new BlockAccountRepository();

    public List<BlockAccount> findAll(BankBranch bankBranch){
        return blockAccountRepository.findAll(bankBranch);
    }

    public void delete(Integer blockId){
        blockAccountRepository.delete(blockId);
    }

}
