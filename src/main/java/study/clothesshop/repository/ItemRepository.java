package study.clothesshop.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import org.springframework.stereotype.Repository;
import study.clothesshop.domain.Item;

import java.util.List;
import java.util.Optional;

@Repository
public interface ItemRepository extends JpaRepository<Item, Long> {

   // Optional<Item> findById(Long id);
    Optional<Item> findById(Long id);

    List<Item> findAll();






}
