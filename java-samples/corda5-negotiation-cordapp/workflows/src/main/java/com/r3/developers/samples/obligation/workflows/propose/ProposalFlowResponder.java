package com.r3.developers.samples.obligation.workflows.propose;

import com.r3.developers.samples.obligation.states.Proposal;
import net.corda.v5.application.flows.CordaInject;
import net.corda.v5.application.flows.InitiatedBy;
import net.corda.v5.application.flows.ResponderFlow;
import net.corda.v5.application.messaging.FlowSession;
import net.corda.v5.base.annotations.Suspendable;
import net.corda.v5.ledger.utxo.UtxoLedgerService;
import net.corda.v5.ledger.utxo.transaction.UtxoSignedTransaction;
import org.jetbrains.annotations.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@InitiatedBy(protocol = "proposal")
public class ProposalFlowResponder implements ResponderFlow {
    private final static Logger log = LoggerFactory.getLogger(ProposalFlowResponder.class);
    @CordaInject
    private UtxoLedgerService utxoLedgerService;
    @Suspendable
    @Override
    public void call(@NotNull FlowSession session) {
        try {
            UtxoSignedTransaction finalizedSignedTransaction= utxoLedgerService.receiveFinality(session,transaction -> {

            }).getTransaction();

            log.info("Finished modification responder flow - " + finalizedSignedTransaction.getId());

        } catch (Exception e){
            log.warn("Exceptionally finished responder flow", e);

        }
    }
}
