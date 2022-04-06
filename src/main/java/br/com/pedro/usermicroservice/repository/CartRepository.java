package br.com.pedro.usermicroservice.repository;

import br.com.pedro.usermicroservice.model.Cart;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface CartRepository extends JpaRepository<Cart, Long> {
//    @Query(value = "delete from cart where (user_id=:userId and id_item=:idItem)", nativeQuery = true)
//    void deleteByIdAndIdItem(Long userId, Long idItem);
}
