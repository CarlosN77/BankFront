package com.projeto.bankapp.repositories;

import com.projeto.bankapp.entities.DebitCardEntity;
import org.hibernate.sql.ast.tree.expression.JdbcParameter;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DebitCardRepository extends JpaRepository<DebitCardEntity, Integer> {
}
