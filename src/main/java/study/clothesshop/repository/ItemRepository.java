package study.clothesshop.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import study.clothesshop.domain.Item;

import java.util.List;
import java.util.Optional;

public interface ItemRepository extends JpaRepository<Item, Long> {

    // 상품 조회 단건 조회
    Optional<Item> findById(Long id);

    List<Item> findAll();




}
