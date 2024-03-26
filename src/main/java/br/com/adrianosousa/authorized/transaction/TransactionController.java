package br.com.adrianosousa.authorized.transaction;

import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("transaction")
public class TransactionController {

    private TransactionService transactionService;

    public TransactionController(TransactionService transactionService){
        this.transactionService = transactionService;
    }

    @PostMapping
    public Transaction authorized(@RequestBody Transaction transaction){
        return transactionService.create(transaction);
    }

    @GetMapping
    public List<Transaction> list(){
        return transactionService.list();
    }
}
