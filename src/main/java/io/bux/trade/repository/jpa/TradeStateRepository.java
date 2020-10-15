package io.bux.trade.repository.jpa;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import io.bux.trade.entity.TradeState;

/**
 * 
 * Interface for generic CRUD operations on a repository for a
 * {@link TradeState}.
 * 
 * @author mlazic
 * @since 1.0
 * 
 */
@Repository
public interface TradeStateRepository extends JpaRepository<TradeState, String> {

}
