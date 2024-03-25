package br.com.adrianosousa.authorized.transaction;

import br.com.adrianosousa.authorized.authorization.AuthorizationService;
import br.com.adrianosousa.authorized.notification.NotificationService;
import br.com.adrianosousa.authorized.wallet.Wallet;
import br.com.adrianosousa.authorized.wallet.WalletRepository;
import br.com.adrianosousa.authorized.wallet.WalletType;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class TransactionService {
    private final TransactionRepository transactionRepository;
    private final WalletRepository walletRepository;
    private final AuthorizationService authorizationService;
    private final NotificationService notificationService;

    public TransactionService(TransactionRepository transactionRepository, WalletRepository walletRepository
            ,AuthorizationService authorizationService,NotificationService notificationService) {
        this.transactionRepository = transactionRepository;
        this.walletRepository = walletRepository;
        this.authorizationService = authorizationService;
        this.notificationService=notificationService;
    }

    @Transactional
    public Transaction create(Transaction transaction) {
        // 1 - validar
        validate(transaction);

        // 2 - criar transação
        var newTransaction = transactionRepository.save(transaction);

        // 3 - debitar da carteira
        var wallet = walletRepository.findById(transaction.payee()).get();
        walletRepository.save(wallet.debit(transaction.value()));

        // 4 - chamar serviços externo
        //authorize
        authorizationService.authorized(transaction);
        //notify
        notificationService.notify(transaction);


        return newTransaction;
    }

    /*
     * 1 - payer has a common wallet
     * 2 - payer has emough balace
     * 3 - payer is not the payer
     */
    private void validate(Transaction transaction) {
        walletRepository.findById(transaction.payee())
                .map(payee -> walletRepository.findById(transaction.payer())
                        .map(payer -> isTransactionValid(transaction, payer) ? true : null)
                        .orElseThrow(() -> new InvalidTransactionException("Invalid Transaction - " + transaction)))
                .orElseThrow(() -> new InvalidTransactionException(
                        "Invalid transaction - " + transaction));


    }

    private static boolean isTransactionValid(Transaction transaction, Wallet payer) {
        return payer.type() == WalletType.COMUM.getValue()
                && payer.balance().compareTo(transaction.value()) >= 0
                && !payer.id().equals(transaction.payee());
    }
}
