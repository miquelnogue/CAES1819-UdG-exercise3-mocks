package org.udg.caes.account;

import mockit.*;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;


import static org.junit.jupiter.api.Assertions.assertEquals;

public class TestAccountService {

  Account senderAccount;
  Account beneficiaryAccount;


  // EL BEFOREEACH NO FARIA FALTA, PERO ES PER PROVAR COM FUNCIONA
  @BeforeEach
  public void initialize(){
    senderAccount = new Account("1", 200);
    beneficiaryAccount = new Account("2", 100);
  }


  @Test
  void testTransfer(@Tested AccountService as, @Mocked AccountManager am)  {

    new Expectations() {{
      am.findAccount("Cowboy Surrell"); result = senderAccount;
      am.findAccount("Poli Retirat"); result = beneficiaryAccount;
    }};

    as.setAccountManager(am);

    as.transfer("Cowboy Surrell", "Poli Retirat", 100);

    assertEquals(100, senderAccount.getBalance());
    assertEquals(200, beneficiaryAccount.getBalance());

    new Verifications()
    {{
      am.updateAccount(senderAccount); times = 1;
      am.updateAccount(beneficiaryAccount); times = 1;
    }};
  }
}